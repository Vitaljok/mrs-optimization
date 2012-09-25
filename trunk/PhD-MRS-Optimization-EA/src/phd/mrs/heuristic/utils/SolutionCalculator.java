/*
 * Copyright (C) 2012 Vitaljok
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package phd.mrs.heuristic.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;
import phd.mrs.heuristic.object.Agent;
import phd.mrs.heuristic.object.Project;
import phd.mrs.heuristic.ga.AgentGene;
import phd.mrs.heuristic.ga.fitness.opcost.AgentMissionMatrix;
import phd.mrs.heuristic.mission.Mission;

/**
 *
 * @author Vitaljok
 */
public class SolutionCalculator {

    List<AgentGene> solution;
    Project project;
    double Qinv = 0d;
    double Qsys_design = 0d;
    double Qdesign = 0d;
    double Qprod = 0d;
    double Qassy = 0d;
    double Qoper = 0d;
    double Qmaint = 0d;
    double Qrepl = 0d;

    public SolutionCalculator(List<AgentGene> solution, Project project) {
        this.solution = solution;
        this.project = project;
    }

    public void process() {
        List<Agent> opAgentList = new LinkedList<>();

        for (AgentGene agentGene : this.solution) {
            if (agentGene.getInstances() > 0) {
                Qdesign += agentGene.getAgent().getDesignCosts();
                Qprod += agentGene.getAgent().getProductionCosts() * agentGene.getInstances();

                double maxComplexity = 0d;

                // sum of components
                for (phd.mrs.heuristic.object.Component comp : agentGene.getAgent().getComponents()) {
                    maxComplexity = Math.max(maxComplexity, comp.getComplexity());
                }

                // add assembly costs
                //Qassy += CostModel.calcAssembly(agentGene.getAgent().getComponents().size(), maxComplexity) * agentGene.getInstances();

                //store agents for Op cost calcualtion
                for (int i = 0; i < agentGene.getInstances(); i++) {
                    opAgentList.add(agentGene.getAgent().clone());
                }
            }
        }

        // add system design costs (use only active)
        //Qsys_design = CostModel.calcSysDesign(opAgentList.size());

        Qinv = Qsys_design + Qdesign + Qprod;


        List<Agent> agents = opAgentList;
        List<Mission> missions = this.project.getMissions();

        AgentMissionMatrix timeMatrix = new AgentMissionMatrix(agents, null);

        // fill initial work amount
        for (Mission mis : this.project.getMissions()) {

            // find total performance
            double totalPerf = 0d;
            for (Agent agent : agents) {
                totalPerf += mis.getAgentPerformance(agent);
            }

            // set equal amount of work for agents
            for (Agent agent : agents) {
                if (mis.getAgentPerformance(agent) > 0) {
                    timeMatrix.setValue(agent, mis, roundUp(mis.getAmountOfWork() / totalPerf));
                }
            }
        }

        Qoper = timeMatrix.getCosts(Qinv);

        //Qmaint = CostModel.calcSysMaint(agents.size()) * timeMatrix.getSysTime();
        Qrepl = project.costModel.systemReplRate * timeMatrix.getSysTime() * Qinv;

        timeMatrix.print();

    }

    private Double roundUp(Double value) {
        return (new BigDecimal(value)).setScale(0, RoundingMode.UP).doubleValue();
    }

    public double getQassy() {
        return Qassy;
    }

    public double getQdesign() {
        return Qdesign;
    }

    public double getQinv() {
        return Qinv;
    }

    public double getQoper() {
        return Qoper;
    }

    public double getQprod() {
        return Qprod;
    }

    public double getQsys_design() {
        return Qsys_design;
    }

    public double getQmaint() {
        return Qmaint;
    }

    public double getQrepl() {
        return Qrepl;
    }
}
