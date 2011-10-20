/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
