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
package phd.mrs.heuristic.ga;

import phd.mrs.heuristic.entity.config.Config;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import phd.mrs.heuristic.ChromosomeTestFrame;
import phd.mrs.heuristic.entity.Agent;
import phd.mrs.heuristic.entity.Project;
import phd.mrs.heuristic.utils.Debug;

/**
 *
 * @author Vitaljok
 */
public class Island extends Thread {

    Project project;
    Configuration configuration;
    Genotype genotype;

    public Island(String name, Configuration configuration, Project project) throws InvalidConfigurationException {
        super(name);
        Debug.log.info("Populating island");
        this.configuration = configuration;
        this.genotype = Genotype.randomInitialGenotype(this.configuration);
        this.project = project;
    }

    @Override
    public void run() {
        Debug.log.info("Starting evalution");

        int genNum = 0;
        int lastChangeGen = 0;
        double lastFitValue = -1d;

        while (genNum < project.config.generationsLimit) {
            genotype.evolve(project.config.generationsStep);
            genNum += project.config.generationsStep;

            IChromosome best = genotype.getFittestChromosome();
            if (lastFitValue != best.getFitnessValue()) {
                lastChangeGen = genNum;
                lastFitValue = best.getFitnessValue();
            }
            Debug.log.info(genNum + "\t~"+lastChangeGen +"\t"+ best.getFitnessValue() );
        }

        new ChromosomeTestFrame(genotype.getFittestChromosome(), project).setVisible(true);
    }

    public void printSolution() {
        IChromosome solution = genotype.getFittestChromosome();

        for (Gene gene : solution.getGenes()) {
            AgentGene agentGene = (AgentGene) gene;
            if (agentGene.getInstances() > 0) {

                Agent agent = agentGene.getAgent();

                System.out.println(agentGene.getInstances() + " x " + agent.getComponents());

                //Debug.log.info(agentGene.getInstances() + " x "+agentGene.getAgent().getProductionCosts());
                //agentGene.getAgent().print();
            }
        }
    }
}
