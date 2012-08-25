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

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;
import phd.mrs.heuristic.entity.Component;
import phd.mrs.heuristic.entity.Project;
import phd.mrs.heuristic.ga.AgentGene;
import phd.mrs.heuristic.utils.Config;
import phd.mrs.heuristic.utils.Debug;

/**
 *
 * @author Vitaljok
 */
public class MrsTotalCostFitnessFunction extends FitnessFunction {

    Project project;
    Map<Component, Integer> compsMap;

    public MrsTotalCostFitnessFunction(Project project) {
        this.project = project;
        this.compsMap = new HashMap<Component, Integer>();

        for (Component c : this.project.getComponents()) {
            this.compsMap.put(c, 0);
        }
    }

    private double estimateWorkTime(IChromosome solution) {




        return 0d;
    }

    @Override
    protected double evaluate(IChromosome a_subject) {

        double Qinv = 0d;

        Map<Component, Integer> comps = new HashMap<Component, Integer>(compsMap);

        // loop through components and sum-product costs
        Gene[] genes = a_subject.getGenes();

        for (Gene gene : genes) {
            AgentGene agentGene = (AgentGene) gene;
            if (agentGene.getInstances() > 0) {
                // Qprod * Ninst
                Qinv += agentGene.getAgent().getProductionCosts() * agentGene.getInstances();
                // QDesign
                Qinv += agentGene.getAgent().getDesignCosts();

                for (Component comp : agentGene.getAgent().getComponents()) {
                    comps.put(comp, comps.get(comp) + 1);
                }
            }
        }

        // check if all components are used in solution
        if (comps.containsValue(0)) {
            Debug.log.fine("Found non-complete solution, ignoring");

            int num = 0;

            for (int v : comps.values()) {
                if (v == 0) {
                    num++;
                }
            }

            return num * Config.INFINITE_COSTS;
        }

        // apply system design coefficient (Csys_design)
        Qinv *= Config.Coef.systemDesign;


        OperatingCostCalculator calc = new OperatingCostCalculator(project, a_subject, 0d);

        Thread sub = new Thread(calc, "OpCostSubThread");
        try {
            sub.start();
            sub.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(MrsTotalCostFitnessFunction.class.getName()).log(Level.SEVERE, null, ex);
        }

        Double Qoper = calc.getEstimation();


        return Qinv + Qoper;
    }
}
