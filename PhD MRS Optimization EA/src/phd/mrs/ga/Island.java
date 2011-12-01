/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phd.mrs.ga;

import phd.mrs.utils.Config;
import java.util.ArrayList;
import java.util.List;
import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.DefaultConfiguration;
import phd.mrs.entity.Device;
import phd.mrs.entity.Project;
import phd.mrs.ga.fitness.MrsSimpleFitnessFunction;

/**
 *
 * @author Vitaljok
 */
public class Island extends Thread {

    Project project;
    Configuration configuration;
    Genotype genotype;

    public Island(String name, Project project) throws InvalidConfigurationException {
        super(name);
        System.out.println("Creating island: " + this.getName() + "@" + Thread.currentThread().getId());
        this.project = project;

        this.configuration = new DefaultConfiguration(name, name);

        List<DeviceGene> genes = new ArrayList<DeviceGene>();
        for (Device dev : project.getDevices()) {
            genes.add(new DeviceGene(this.configuration, 0, Config.DEVICE_LIMIT, dev));
        }

        Chromosome sampleChromosome = new Chromosome(this.configuration, genes.toArray(new DeviceGene[0]));
        this.configuration.setSampleChromosome(sampleChromosome);

        this.configuration.setFitnessFunction(new MrsSimpleFitnessFunction(project.getComponents()));

        this.configuration.setPopulationSize(Config.POPULATION_SIZE);             

        this.genotype = Genotype.randomInitialGenotype(this.configuration);
    }

    @Override
    public void run() {
        System.out.println("Starting evalution: " + this.getName() + "@" + Thread.currentThread().getId());

        Integer popNum = 0;

        while (popNum < Config.GENERATIONS_LIMIT) {
            genotype.evolve(Config.GENERATIONS_STEP);
            popNum += Config.GENERATIONS_STEP;

            IChromosome best = genotype.getFittestChromosome();
            System.out.println(this.getName() + ":\t" + popNum + "\t" + best.getFitnessValue());
        }

        IChromosome solution = genotype.getFittestChromosome();
        
        solution.getFitnessValue();

        for (Gene gene : solution.getGenes()) {
            DeviceGene dev = (DeviceGene) gene;
            if (dev.getInstances() > 0) {
                System.out.println(dev.getInstances() + " x "+dev.getDevice().getInvestmentCosts());
                dev.getDevice().print();
            }
        }
    }
}
