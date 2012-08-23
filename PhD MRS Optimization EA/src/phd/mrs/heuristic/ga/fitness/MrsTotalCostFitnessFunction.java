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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;
import phd.mrs.heuristic.entity.Component;
import phd.mrs.heuristic.ga.AgentGene;
import phd.mrs.heuristic.utils.Config;
import phd.mrs.heuristic.utils.Debug;

/**
 *
 * @author Vitaljok
 */
public class MrsTotalCostFitnessFunction extends FitnessFunction {

    List<Component> systemComponents;
    Map<Component, Integer> compsMap;

    public MrsTotalCostFitnessFunction(List<Component> systemComponents) {
        this.systemComponents = systemComponents;
        this.compsMap = new HashMap<Component, Integer>();

        for (Component c : systemComponents) {
            this.compsMap.put(c, 0);
        }
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

        
        // operating costs calculation
        double Qoper = 0d;
        double Tsys = 5d; //TODO: = max(Tagent)       
        double Tagent = 5d; //TODO: estimate operation time       
        
        int Nagents = 0;
        double Qenergy = 0d;
        
        for (Gene gene : genes) {
            AgentGene agentGene = (AgentGene) gene;
            if (agentGene.getInstances() > 0) {
                Qenergy += agentGene.getAgent().getOperatingEnergy() * Tagent;                               
                Nagents += agentGene.getInstances();
            }
        }
        
        double Qmaint = Config.Coef.systemMaintLin * Math.exp(Config.Coef.systemMaintExp * Nagents) * Tsys;
        double Qrepl = Qinv * Config.Coef.systemReplRate * Tsys;
        
        Qoper = Qmaint + Qrepl + Qenergy;
        
        return Qmaint;//Qinv + Qoper;
    }
}
