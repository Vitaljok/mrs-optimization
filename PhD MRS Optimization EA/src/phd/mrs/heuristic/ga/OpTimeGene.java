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
package phd.mrs.heuristic.ga;

import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.IntegerGene;
import phd.mrs.heuristic.entity.Agent;
import phd.mrs.heuristic.mission.Mission;
import phd.mrs.heuristic.utils.Debug;

/**
 *
 * @author Vitaljok
 */
public class OpTimeGene extends IntegerGene {

    Mission mission;
    Agent agent;

    public OpTimeGene(Mission mission, Agent agent, Configuration a_config, int a_lowerBounds, int a_upperBounds) throws InvalidConfigurationException {
        super(a_config, a_lowerBounds, a_upperBounds);
        this.mission = mission;
        this.agent = agent;
    }

    public Agent getAgent() {
        return agent;
    }

    public Mission getMission() {
        return mission;
    }

    public Integer getTime() {
        return (Integer) this.getAllele();
    }

    @Override
    protected Gene newGeneInternal() {
        try {
            return new OpTimeGene(this.mission, this.agent,
                    this.getConfiguration(), this.getLowerBounds(), this.getUpperBounds());
        } catch (InvalidConfigurationException ex) {
            Debug.log.severe("Error creating new AgentGene");
            ex.printStackTrace();
            return null;
        }
    }
}
