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
package phd.mrs.heuristic.ga.fitness;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;
import phd.mrs.heuristic.entity.Agent;
import phd.mrs.heuristic.entity.Component;
import phd.mrs.heuristic.entity.Project;
import phd.mrs.heuristic.mission.Mission;
import phd.mrs.heuristic.utils.Config;
import phd.mrs.heuristic.utils.Pair;


/**
 *
 * @author Vitaljok
 */
public class GreedyOpCostCalculator {

    Project project;
    Integer scale;
    Double valueStep;

    private Double roundUp(Double value) {
        return (new BigDecimal(value)).setScale(this.scale, RoundingMode.UP).doubleValue();
    }

    public GreedyOpCostCalculator(Project project, Integer scale) {
        this.project = project;
        this.scale = scale;

        this.valueStep = roundUp(Config.NEAR_ZERO);
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

            timeMatrix.print();
            System.out.println("Result: " + result);
        } while (changed);


        return result;
    }

    public Double calcOpCosts1(List<Agent> mrs, Double Qinv) {
        AgentMissionMatrix timeMatrix = new AgentMissionMatrix(mrs, this.project.getMissions());

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
        double result;
        double new_result;// = Config.INFINITE_COSTS;

        boolean changed = false;

        do {
            changed = false;
            timeMatrix.print();
            result = timeMatrix.getCosts(Qinv);
            System.out.println("Result: " + result);

            //search for most profitable value to decrese
            double delta = 0d;
            Agent decAgent = null;
            Mission sMission = null;

            for (Agent agent : mrs) {
                for (Mission mis : this.project.getMissions()) {
                    double currValue = timeMatrix.getValue(agent, mis);
                    if (currValue > 0) {
                        // try to decrease value
                        timeMatrix.incValue(agent, mis, -valueStep);

                        // evaluate change
                        double newDelta = result - timeMatrix.getCosts(Qinv);

                        if (newDelta > delta) {
                            delta = newDelta;
                            decAgent = agent;
                            sMission = mis;
                        }

                        // restore old value
                        timeMatrix.setValue(agent, mis, currValue);
                    }
                }
            }
            // decrease most profitable value
            timeMatrix.incValue(decAgent, sMission, -valueStep);

            delta = 0d;
            Agent incAgent = null;
            // search for most profitavle value to increase for found mission
            for (Agent agent : mrs) {
                if (!agent.equals(decAgent)
                        && sMission.getAgentPerformance(agent) > 0) {
                    // try to increase value
                    timeMatrix.incValue(agent, sMission, +valueStep);
                    // evaluate change
                    double newDelta = result - timeMatrix.getCosts(Qinv);

                    if (newDelta > delta) {
                        delta = newDelta;
                        incAgent = agent;
                    }
                    // restore old value
                    timeMatrix.incValue(agent, sMission, -valueStep);
                }
            }

            if (incAgent != null) {
                // increase most profitable value
                timeMatrix.incValue(incAgent, sMission, +valueStep);
                changed = true;
            } else {
                // agent for increasing was not found 
                // restore original value
                timeMatrix.incValue(decAgent, sMission, +valueStep);
            }

            new_result = timeMatrix.getCosts(Qinv);
            System.out.println("New result: " + new_result);
        } while (new_result < result || changed);

        timeMatrix.print();

        return result;
    }

    public static void main(String[] args) throws Exception {

        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }

        Project project = new Project("test");
        project.getMissions().add(new MissionA());
        project.getMissions().add(new MissionB());

        project.getAgents().add(new AgentA());
        project.getAgents().add(new AgentA());
        project.getAgents().add(new AgentB());
        project.getAgents().add(new AgentAB());
        project.getAgents().add(new AgentAB());
        project.getAgents().add(new AgentAB());

        GreedyOpCostCalculator calc = new GreedyOpCostCalculator(project, 0);

        Double res = calc.calcOpCosts(project.getAgents(), 30d);

        System.out.println("Result: " + res);

    }
}
class AgentA extends Agent {

    public AgentA() {
        this.getComponents().add(new Component("compA", "compA", null));
    }

    @Override
    public Double getOperatingEnergy() {
        return 1d;
    }
}

class AgentB extends Agent {

    public AgentB() {
        this.getComponents().add(new Component("compB", "compB", null));
    }

    @Override
    public Double getOperatingEnergy() {
        return 2d;
    }
}

class AgentAB extends Agent {

    public AgentAB() {
        this.getComponents().add(new Component("compA", "compA", null));
        this.getComponents().add(new Component("compB", "compB", null));
    }

    @Override
    public Double getOperatingEnergy() {
        return 2.5d;
    }
}

class MissionA implements Mission {

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

class MissionB implements Mission {

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