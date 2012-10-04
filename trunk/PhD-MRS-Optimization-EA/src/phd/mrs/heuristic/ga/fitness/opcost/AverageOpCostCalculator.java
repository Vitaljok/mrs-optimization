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
package phd.mrs.heuristic.ga.fitness.opcost;

import java.util.List;
import phd.mrs.heuristic.object.Agent;
import phd.mrs.heuristic.object.Component;
import phd.mrs.heuristic.object.Project;
import phd.mrs.heuristic.mission.Mission;


/**
 *
 * @author Vitaljok
 */
public class AverageOpCostCalculator extends AbstractOpCostCalculator {

    public AverageOpCostCalculator(Project project, Integer scale) {
        super(project, scale);
    }

    public Double calcOpCosts(List<Agent> mrs, Double Qinv) {

        List<Mission> missions = this.project.getMissions();

        AgentMissionMatrix timeMatrix = new AgentMissionMatrix(mrs, project);

        // fill initial work amount
        for (Mission mis : this.project.getMissions()) {

            // find total performance
            double totalPerf = 0d;
            for (Agent agent : mrs) {
                totalPerf += mis.getAgentPerformance(agent);
            }
            
            if (totalPerf == 0d) {
                return this.project.getConfig().getNearInfinity();
            }

            // set equal amount of work for agents
            for (Agent agent : mrs) {
                if (mis.getAgentPerformance(agent) > 0) {
                    timeMatrix.setValue(agent, mis, roundUp(mis.getAmountOfWork() / totalPerf));
                }
            }
        }

        //timeMatrix.print();
        //System.out.println("Costs: " + timeMatrix.getCosts(Qinv));

        return timeMatrix.getCosts(Qinv);
    }

    public static void main(String[] args) throws Exception {

        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }

        Project project = new Project();
        project.setName("test");
        project.getMissions().add(new MissionA());
        project.getMissions().add(new MissionB());

        project.getAgents().add(new AgentA());
        project.getAgents().add(new AgentA());
        project.getAgents().add(new AgentB());
        project.getAgents().add(new AgentAB());
        project.getAgents().add(new AgentAB());
        project.getAgents().add(new AgentAB());

        AverageOpCostCalculator calc = new AverageOpCostCalculator(project, 0);

        Double res = calc.calcOpCosts(project.getAgents(), 30d);

        System.out.println("Result: " + res);

    }
}
class AgentA extends Agent {

    public AgentA() {
        super(null);
        this.getComponents().add(new Component("compA", "compA"));
    }

    @Override
    public Double getOperatingEnergy() {
        return 1d;
    }
}

class AgentB extends Agent {

    public AgentB() {
        super(null);
        this.getComponents().add(new Component("compB", "compB"));
    }

    @Override
    public Double getOperatingEnergy() {
        return 2d;
    }
}

class AgentAB extends Agent {

    public AgentAB() {
        super(null);
        this.getComponents().add(new Component("compA", "compA"));
        this.getComponents().add(new Component("compB", "compB"));
    }

    @Override
    public Double getOperatingEnergy() {
        return 2.5d;
    }
}

class MissionA extends Mission {

    @Override
    public Double getAgentPerformance(Agent agent) {
        if (agent.getComponents().size() > 1) {
            // AB agent
            return 1.5;
        } else {
            if (agent.getComponents().get(0).getName().equals("compA")) {
                return 1d;
            } else {
                return 0d;
            }
        }
    }

    @Override
    public Double getAmountOfWork() {
        return 23d;
    }

    @Override
    public Integer getMaxTimeEstimation() {
        return 0;
    }
}

class MissionB extends Mission {

    @Override
    public Double getAgentPerformance(Agent agent) {
        if (agent.getComponents().size() > 1) {
            // AB agent
            return 2.8;
        } else {
            if (agent.getComponents().get(0).getName().equals("compA")) {
                return 0d;
            } else {
                return 1.2d;
            }
        }
    }

    @Override
    public Double getAmountOfWork() {
        return 19d;
    }

    @Override
    public Integer getMaxTimeEstimation() {
        return 0;
    }
}