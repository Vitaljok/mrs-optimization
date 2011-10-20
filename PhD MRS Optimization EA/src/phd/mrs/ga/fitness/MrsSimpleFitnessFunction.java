/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phd.mrs.ga.fitness;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;
import phd.mrs.entity.Component;
import phd.mrs.entity.Device;
import phd.mrs.utils.Config;
import phd.mrs.ga.DeviceGene;

/**
 * Calculates fitness value of SolutionChromosome in terms of costs.
 * @author Vitaljok
 */
public class MrsSimpleFitnessFunction extends FitnessFunction {

    List<Component> systemComponents;

    public MrsSimpleFitnessFunction(List<Component> systemComponents) {
        this.systemComponents = systemComponents;
    }

    @Override
    protected double evaluate(IChromosome a_subject) {

        Double value = 0d;
        Set<Component> comps = new HashSet<Component>();

        Double staticPrice = 0d;

        Gene[] genes = a_subject.getGenes();

        for (Gene gene : genes) {
            DeviceGene dev = (DeviceGene) gene;
            if (dev.getInstances() > 0) {
                staticPrice += dev.getDevice().getStaticPrice() * dev.getInstances();
                for (Component comp : dev.getDevice().getComponents()) {
                    comps.add(comp);
                }
            }
        }

        if (!comps.containsAll(systemComponents)) {
            return (1d * comps.size()) / (1d * systemComponents.size()) * 100;
        }

        value = 10000000d - staticPrice;

        return value;
    }
}
