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

import java.util.LinkedList;
import java.util.List;
import phd.mrs.heuristic.entity.Agent;
import phd.mrs.heuristic.entity.Project;
import phd.mrs.heuristic.mission.Mission;
import phd.mrs.heuristic.utils.Config;
import phd.mrs.heuristic.utils.Pair;

/**
 *
 * @author Vitaljok
 */
public class EvenUpDownOpCostCalculator extends AbstractOpCostCalculator {

    public EvenUpDownOpCostCalculator(Project project, Integer scale) {
        super(project, scale);
    }

    public Double calcOpCosts(List<Agent> mrs, Double Qinv) {

        List<Agent> agents = mrs;
        List<Mission> missions = this.project.getMissions();

        AgentMissionMatrix timeMatrix = new AgentMissionMatrix(agents, missions);

        // fill all work amount on cheapest agent
        for (Mission mis : this.project.getMissions()) {
            double minCost = Config.INFINITE_COSTS;
            Agent minAgent = null;
            for (Agent agent : mrs) {
                double currCost = agent.getOperatingEnergy() / mis.getAgentPerformance(agent);

                if (minCost > currCost) {
                    minAgent = agent;
                    minCost = currCost;
                }
            }

            timeMatrix.setValue(minAgent, mis, roundUp(mis.getAmountOfWork() / mis.getAgentPerformance(minAgent)));
        }


        // event values among other cheapest agents        
        double result = 0d;
        double new_result = 0d;

        boolean changed = false;

        do {
            changed = false;
            // search for agent with greatest wotking time                               
            List<Pair<Agent, Mission>> maxCells = new LinkedList<>();
            double maxValue = 0d;

            for (Agent agent : agents) {
                double currValue = timeMatrix.getSum(agent);
                if (currValue > maxValue) {
                    maxCells.clear();
                    maxValue = currValue;
                }

                if (maxValue == currValue) {
                    maxCells.add(new Pair<>(agent, timeMatrix.getMaxCost(agent)));
                }
            }

            // find most expensive cell (to decrease)
            double maxCost = 0d;
            Pair<Agent, Mission> maxPair = null;

            for (Pair<Agent, Mission> pair : maxCells) {
                double currCost = pair.getValue1().getOperatingEnergy() / pair.getValue2().getAgentPerformance(pair.getValue1());

                if (maxCost < currCost) {
                    maxPair = pair;
                    maxCost = currCost;
                }
            }

            // amount of work to be switched among agents on particular mission
            double deltaWork = maxPair.getValue2().getAgentPerformance(maxPair.getValue1()) - (timeMatrix.getWorkDone(maxPair.getValue2()) - maxPair.getValue2().getAmountOfWork());

            // work around for case when time of agent can be decreased for free (without increasing other agent)
            if (deltaWork == 0) {
                changed = true;
            }

            while (deltaWork > 0) {//-Config.NEAR_ZERO) {
                // find cheapest agent to increase 
                Agent minAgent = null;
                double minCost = Config.INFINITE_COSTS;

                for (Agent agent : agents) {
                    if (agent != maxPair.getValue1()
                            && maxPair.getValue2().getAgentPerformance(agent) > 0
                            && timeMatrix.getSum(agent) + valueStep <= maxValue - valueStep) {

                        double currCost = agent.getOperatingEnergy() / maxPair.getValue2().getAgentPerformance(agent);
                        if (minCost > currCost) {
                            minCost = currCost;
                            minAgent = agent;
                        }
                    }
                }

                // no agent found for increasing (end processing)
                if (minAgent == null) {
                    changed = false;
                    break;
                } else {
                    //increase value
                    timeMatrix.incValue(minAgent, maxPair.getValue2(), +valueStep);
                    deltaWork -= maxPair.getValue2().getAgentPerformance(minAgent);
                    changed = true;
                }

                //timeMatrix.print();
                //System.out.println("Delta: " + deltaWork + "\tChanged: " + changed);
            }

            if (changed) {
                timeMatrix.incValue(maxPair.getValue1(), maxPair.getValue2(), -valueStep);
                result = timeMatrix.getCosts(Qinv);
            }

        } while (changed);
        
        return result;
    }
}
