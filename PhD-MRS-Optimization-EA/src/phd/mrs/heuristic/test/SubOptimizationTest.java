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
package phd.mrs.heuristic.test;

import java.util.ArrayList;
import java.util.List;
import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.DefaultConfiguration;

/**
 *
 * @author Vitaljok
 */
public class SubOptimizationTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InvalidConfigurationException {
        Configuration config = new DefaultConfiguration();

        List<MachineGene> genes = new ArrayList<MachineGene>();

        Integer N = 50;

        genes.add(new MachineGene(new MachineCell("A1", "A", 2.0, 1.0), config, 0, N));
        genes.add(new MachineGene(new MachineCell("A1", "B", 0.0, 10.0), config, 0, N));
        genes.add(new MachineGene(new MachineCell("A2", "A", 2.0, 1.0), config, 0, N));
        genes.add(new MachineGene(new MachineCell("A2", "B", 0.0, 10.0), config, 0, N));
        genes.add(new MachineGene(new MachineCell("B1", "A", 0.0, 10.0), config, 0, N));
        genes.add(new MachineGene(new MachineCell("B1", "B", 1.0, 2.0), config, 0, N));
        genes.add(new MachineGene(new MachineCell("AB1", "A", 1.5, 1.2), config, 0, N));
        genes.add(new MachineGene(new MachineCell("AB1", "B", 1.0, 2.2), config, 0, N));
        genes.add(new MachineGene(new MachineCell("AB2", "A", 1.5, 1.2), config, 0, N));
        genes.add(new MachineGene(new MachineCell("AB2", "B", 1.0, 2.2), config, 0, N));
        genes.add(new MachineGene(new MachineCell("AB3", "A", 1.5, 1.2), config, 0, N));
        genes.add(new MachineGene(new MachineCell("AB3", "B", 1.0, 2.2), config, 0, N));

        Chromosome sampleChromosome = new Chromosome(config, genes.toArray(new MachineGene[0]));
        config.setSampleChromosome(sampleChromosome);

        config.setFitnessFunction(new TestFitnessFunction());

        config.setPopulationSize(200);

        Genotype genotype = Genotype.randomInitialGenotype(config);


//        for (int i = 0; i < 500; i++) {
//            System.out.println(i + "\tvalue: " + (10000 - genotype.getFittestChromosome().getFitnessValue()));
//            genotype.evolve(10);
//        }
        genotype.evolve(5000);


        IChromosome best = best = genotype.getFittestChromosome();
        System.out.println("Costs: " + (10000 - best.getFitnessValue()));
        for (Gene gene : best.getGenes()) {
            MachineGene g = (MachineGene) gene;
            System.out.println(g);
        }
    }
}
