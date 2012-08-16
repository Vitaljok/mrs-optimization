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
package phd.mrs.heuristic.ga.fitness;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;
import phd.mrs.heuristic.entity.Component;
import phd.mrs.heuristic.ga.AgentGene;

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

        Double investmentCosts = 0d;

        Gene[] genes = a_subject.getGenes();

        for (Gene gene : genes) {
            AgentGene agent = (AgentGene) gene;
            if (agent.getInstances() > 0) {
                investmentCosts += agent.getAgent().getInvestmentCosts() * agent.getInstances();
                for (Component comp : agent.getAgent().getComponents()) {
                    comps.add(comp);
                }
            }
        }

        // check if all components are presented in solution
        if (!comps.containsAll(systemComponents)) {
            return (1d * comps.size()) / (1d * systemComponents.size()) * 100;
        }

        value = 10000000d - investmentCosts;

        return value;
    }
}
