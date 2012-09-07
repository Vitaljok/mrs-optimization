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

import phd.mrs.heuristic.ga.fitness.opcost.AverageOpCostCalculator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;
import phd.mrs.heuristic.entity.Agent;
import phd.mrs.heuristic.entity.Component;
import phd.mrs.heuristic.entity.Project;
import phd.mrs.heuristic.ga.AgentGene;
import phd.mrs.heuristic.ga.fitness.opcost.AbstractOpCostCalculator;
import phd.mrs.heuristic.utils.Debug;

/**
 *
 * @author Vitaljok
 */
public class MrsTotalCostFitnessFunction extends FitnessFunction {

    Project project;
    Map<Component, Integer> compsMap;
    AbstractOpCostCalculator opCostCalc;

    public MrsTotalCostFitnessFunction(Project project) {
        this.project = project;        
        
        this.compsMap = new HashMap<Component, Integer>();
        for (Component c : this.project.getComponents()) {
            this.compsMap.put(c, 0);
        }
        
        this.opCostCalc = new AverageOpCostCalculator(this.project, 0);
    }    

    @Override
    protected double evaluate(IChromosome a_subject) {

        double Qinv = 0d;

        Map<Component, Integer> comps = new HashMap<>(compsMap);

        // loop through components and sum-product costs
        Gene[] genes = a_subject.getGenes();
        List<Agent> opAgentList = new LinkedList<>();

        for (Gene gene : genes) {
            AgentGene agentGene = (AgentGene) gene;
            if (agentGene.getInstances() > 0) {
                // QDesign
                Qinv += agentGene.getAgent().getDesignCosts();
                
                // Qprod * Ninst
                Qinv += agentGene.getAgent().getProductionCosts() * agentGene.getInstances();                

                // store used components
                for (Component comp : agentGene.getAgent().getComponents()) {
                    comps.put(comp, comps.get(comp) + 1);
                }
                
                //store agents for Op cost calcualtion
                for (int i = 0; i < agentGene.getInstances(); i++) {
                    opAgentList.add(agentGene.getAgent().clone());                    
                }
            }
        }
        
        // add system design costs (use only active)
        Qinv +=  project.costModel.calcSysDesign(opAgentList.size());
        

        // check if all components are used in solution
        if (comps.containsValue(0)) {
            Debug.log.fine("Found non-complete solution, ignoring");

            int num = 0;

            for (int v : comps.values()) {
                if (v == 0) {
                    num++;
                }
            }

            return num * this.project.config.nearInfinity;
        }
        
        // TODO: check is mrs able to perform mission???
                 
        // calculate operational costs
        double Qoper = opCostCalc.calcOpCosts(opAgentList, Qinv);

        //System.out.println("Inv: "+Qinv+ "\tOper: "+Qoper);
        return Qinv + Qoper;
    }
}
