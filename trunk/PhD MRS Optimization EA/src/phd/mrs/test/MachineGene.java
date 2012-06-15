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
package phd.mrs.test;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.IntegerGene;

/**
 *
 * @author Vitaljok
 */
public class MachineGene extends IntegerGene {

    MachineCell machine;

    public MachineGene(MachineCell machine, Configuration a_config, int a_lowerBounds, int a_upperBounds) throws InvalidConfigurationException {
        super(a_config, a_lowerBounds, a_upperBounds);
        this.machine = machine;
    }

    public MachineCell getMachine() {
        return machine;
    }

    @Override
    protected Gene newGeneInternal() {
        try {
            return new MachineGene(machine, this.getConfiguration(), this.getLowerBounds(), this.getUpperBounds());
        } catch (InvalidConfigurationException ex) {
            Logger.getLogger(MachineGene.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public String toString() {
        return "dev"+this.machine.getMachine()+"\twork"+this.machine.getWork()+"\t-> "+(Integer)this.getAllele();
    }
    
    
}
