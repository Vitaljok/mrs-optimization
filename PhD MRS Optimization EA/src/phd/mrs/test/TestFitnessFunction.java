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

import java.awt.Component;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;
import phd.mrs.ga.AgentGene;

/**
 *
 * @author Vitaljok
 */
public class TestFitnessFunction extends FitnessFunction {

    @Override
    protected double evaluate(IChromosome a_subject) {

        Double costs = 0d;
        Double workA = 0d;
        Double workB = 0d;
        Gene[] genes = a_subject.getGenes();

        Integer maxTime = 0;

        for (Gene gene : genes) {
            MachineGene g = (MachineGene) gene;
            Integer v = (Integer) g.getAllele();

            costs += v * g.getMachine().getCost();
            if (g.getMachine().getWork().equals("A")) {
                workA += v * g.getMachine().getPerf();
            } else {
                workB += v * g.getMachine().getPerf();
            }

            if (v > maxTime) {
                maxTime = v;
            }
        }

        Double penalty = 0d;

        if (workA < 23) {
            penalty += (23 - workA) * 30;
        }

        if (workB < 19) {
            penalty += (19 - workB) * 30;
        }
        
        costs += maxTime * 10;
        
        return 10000 - costs - penalty;
    }
}
