/*
 * Copyright (C) 2011 Vitaljok
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
package phd.mrs.ga;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.IntegerGene;
import phd.mrs.entity.Agent;

/**
 *
 * @author Vitaljok
 */
public class AgentGene extends IntegerGene {

    Agent agent;

    public AgentGene(Configuration a_config, int a_lowerBounds, int a_upperBounds, Agent agent) throws InvalidConfigurationException {
        super(a_config, a_lowerBounds, a_upperBounds);
        this.agent = agent;
    }

    public Agent getAgent() {
        return agent;
    }
    
    public Integer getInstances(){
        return (Integer) this.getAllele();
    }

    @Override
    protected Gene newGeneInternal() {
        try {
            return new AgentGene(this.getConfiguration(), this.getLowerBounds(),
                    this.getUpperBounds(), agent);
        } catch (InvalidConfigurationException ex) {
            Logger.getLogger(AgentGene.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
