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

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.event.EventManager;
import org.jgap.impl.BestChromosomesSelector;
import org.jgap.impl.ChromosomePool;
import org.jgap.impl.CrossoverOperator;
import org.jgap.impl.GABreeder;
import org.jgap.impl.MutationOperator;
import org.jgap.impl.StockRandomGenerator;
import phd.mrs.heuristic.entity.Project;
import phd.mrs.heuristic.ga.AgentGene;
import phd.mrs.heuristic.ga.InverseFitnessEvaluator;
import phd.mrs.heuristic.ga.OpTimeGene;
import phd.mrs.heuristic.mission.Mission;
import phd.mrs.heuristic.utils.Debug;

/**
 *
 * @author Vitaljok
 */
public class OperatingCostCalculator implements Runnable {

    Project project;
    IChromosome mrs;
    Double qInv;
    Configuration config;
    Double estimation;

    public OperatingCostCalculator(Project project, IChromosome mrs, Double qInv) {
        this.project = project;
        this.mrs = mrs;
        this.qInv = qInv;
    }

    public Double getEstimation() {
        return estimation;
    }
    
    @Override
    public void run() {

        try {
                       
            Configuration.reset("opCosts");
            
            this.config = new Configuration("opCosts", "OperationalCostSubProblem");

            this.config.setBreeder(new GABreeder());
            this.config.setRandomGenerator(new StockRandomGenerator());
            this.config.setEventManager(new EventManager());
            this.config.setMinimumPopSizePercent(0);
            this.config.setSelectFromPrevGen(0.95d);
            this.config.setKeepPopulationSizeConstant(true);
            this.config.setChromosomePool(new ChromosomePool());
            this.config.setPopulationSize(100);

            // fitness function
            this.config.setFitnessEvaluator(new InverseFitnessEvaluator());
            this.config.setFitnessFunction(new MrsOpCostFitnessFunction(project, qInv));


            // genetic operations            
            BestChromosomesSelector bestChromsSelector = new BestChromosomesSelector(
                    this.config, 0.90d);
            bestChromsSelector.setDoubletteChromosomesAllowed(true);
            this.config.addNaturalSelector(bestChromsSelector, false);
            this.config.addGeneticOperator(new CrossoverOperator(this.config, 0.35d));
            this.config.addGeneticOperator(new MutationOperator(this.config, 12));

            /**
             * Sample chromosome
             * contains all [agent instance] - [mission] combinations
             */
            List<OpTimeGene> genes = new LinkedList<OpTimeGene>();

            for (Mission mission : project.getMissions()) {
                for (Gene gene : mrs.getGenes()) {
                    AgentGene agentGene = (AgentGene) gene;
                    for (int i = 0; i < agentGene.getInstances(); i++) {
                        genes.add(new OpTimeGene(mission, agentGene.getAgent(), config, 0, mission.getMaxTimeEstimation()));
                    }
                }
            }

            Chromosome sample = new Chromosome(config, genes.toArray(new OpTimeGene[0]));

            this.config.setSampleChromosome(sample);


        } catch (InvalidConfigurationException ex) {
            Debug.log.severe("Error creating operating cost estimation configuration");
        }

        try {
            Genotype genotype = Genotype.randomInitialGenotype(this.config);
            genotype.evolve(100);
            
            
//            for (int i = 0; i < 50; i++) {
//                genotype.evolve(10);
//
//                System.out.println(i + "\t"
//                        + genotype.getFittestChromosome().getFitnessValue());
//            }

            this.estimation = genotype.getFittestChromosome().getFitnessValue();
        } catch (InvalidConfigurationException ex) {
            Debug.log.severe("Error while estimating operating cost");
            ex.printStackTrace();
        }
    }
}
