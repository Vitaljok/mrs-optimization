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
import phd.mrs.entity.Device;

/**
 *
 * @author Vitaljok
 */
public class DeviceGene extends IntegerGene {

    Device device;

    public DeviceGene(Configuration a_config, int a_lowerBounds, int a_upperBounds, Device device) throws InvalidConfigurationException {
        super(a_config, a_lowerBounds, a_upperBounds);
        this.device = device;
    }

    public Device getDevice() {
        return device;
    }
    
    public Integer getInstances(){
        return (Integer) this.getAllele();
    }

    @Override
    protected Gene newGeneInternal() {
        try {
            return new DeviceGene(this.getConfiguration(), this.getLowerBounds(),
                    this.getUpperBounds(), device);
        } catch (InvalidConfigurationException ex) {
            Logger.getLogger(DeviceGene.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
