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

import java.util.Iterator;
import java.util.List;
import phd.mrs.heuristic.entity.Agent;
import phd.mrs.heuristic.mission.Mission;
import phd.mrs.heuristic.utils.Pair;

/**
 *
 * @author Vitaljok
 */
public class AgentMissionMatrix {

    List<Agent> agents;
    List<Mission> missions;
    double[][] data;

    public AgentMissionMatrix(List<Agent> agents, List<Mission> missions) {
        this.agents = agents;
        this.missions = missions;

        data = new double[this.agents.size()][this.missions.size()];
    }

    public Double getSum(Mission mission) {
        int k = this.missions.indexOf(mission);

        double res = 0;

        for (int i = 0; i < data.length; i++) {
            res += data[i][k];
        }

        return res;
    }

    public Double getSum(Agent agent) {
        int k = this.agents.indexOf(agent);

        double res = 0;

        for (int i = 0; i < data[k].length; i++) {
            res += data[k][i];
        }

        return res;
    }

    public Double getWorkDone(Mission mission) {
        double result = 0d;

        for (Agent agent : agents) {
            result += this.getValue(agent, mission) * mission.getAgentPerformance(agent);
        }

        return result;
    }

    public Double getCosts(Double sysCosts) {
        double maxValue = 0d;
        double result = 0d;

        for (Agent agent : agents) {
            double agentValue = 0d;
            for (Mission mis : this.missions) {
                double currValue = this.getValue(agent, mis);
                result += agent.getOperatingEnergy() * currValue;
                agentValue += currValue;
            }

            maxValue = Math.max(agentValue, maxValue);
        }

        return result + maxValue * sysCosts;

    }

    public void incValue(Agent agent, Mission mission, Double delta) {
        this.setValue(agent, mission, this.getValue(agent, mission) + delta);
    }

    public void setValue(Agent agent, Mission mission, Double value) {
        this.data[this.agents.indexOf(agent)][this.missions.indexOf(mission)] = value > 0 ? value : 0;
    }

    public Double getValue(Agent agent, Mission mission) {
        return this.data[this.agents.indexOf(agent)][this.missions.indexOf(mission)];
    }

    public void print() {
        System.out.print("Mis. \\ Agent\t");
        for (Agent agent : agents) {
            System.out.print(agent.getClass().getSimpleName() + "\t");
        }
        System.out.println();

        for (Mission mission : this.missions) {
            System.out.print(mission.getClass().getSimpleName() + "\t");

            for (Agent agent : agents) {
                System.out.print(this.getValue(agent, mission) + "\t");
            }
            System.out.println();
        }
    }

    public Mission getMaxCost(Agent agent) {
        Mission maxMis = null;
        double maxVal = 0d;

        for (Mission m : this.missions) {
            if (m.getAgentPerformance(agent) > 0 
                    && this.getValue(agent, m) > 0) {
                double val = agent.getOperatingEnergy() / m.getAgentPerformance(agent);

                if (maxVal < val) {
                    maxVal = val;
                    maxMis = m;
                }
            }
        }

        return maxMis;
    }
}
