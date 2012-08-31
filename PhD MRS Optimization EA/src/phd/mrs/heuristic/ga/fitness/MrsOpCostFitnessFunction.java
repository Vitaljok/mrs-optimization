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
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;
import phd.mrs.heuristic.entity.Project;
import phd.mrs.heuristic.ga.OpTimeGene;
import phd.mrs.heuristic.mission.Mission;
import phd.mrs.heuristic.utils.Config;

/**
 *
 * @author Vitaljok
 */
public class MrsOpCostFitnessFunction extends FitnessFunction {

    Project project;
    Double qInv;
    Map<Mission, Double> missionMap;

    public MrsOpCostFitnessFunction(Project project, Double qInv) {
        this.project = project;
        this.qInv = qInv;

        this.missionMap = new HashMap<Mission, Double>();
        for (Mission m : this.project.getMissions()) {
            this.missionMap.put(m, 0d);
        }

    }

    @Override
    protected double evaluate(IChromosome a_subject) {

        // map to hold amount of performed work
        Map<Mission, Double> mMap = new HashMap<Mission, Double>(missionMap);
        // total operating costs
        double Qenergy = 0d;
        int Tsys = 0;

        for (Gene gene : a_subject.getGenes()) {
            OpTimeGene opGene = (OpTimeGene) gene;
            
            
            Tsys = Math.max(Tsys, opGene.getTime());

            // calculate cost
            Qenergy += opGene.getAgent().getOperatingEnergy() * opGene.getTime();

            //calculate work done
            mMap.put(opGene.getMission(), mMap.get(opGene.getMission())
                    + opGene.getMission().getAgentPerformance(opGene.getAgent()) * opGene.getTime());
        }
        
        double Qmaint = Config.CostModel.getSysMaint(a_subject.getGenes().length) * Tsys;
        double Qrepl = this.qInv * Config.Coef.systemReplRate * Tsys;

        // evaluate results (add penalties for uncomplete work)
        double penalty = 0d;
        
        for (Map.Entry<Mission, Double> en : mMap.entrySet()) {
            if (en.getKey().getAmountOfWork() > en.getValue())
            {
                penalty += en.getKey().getAmountOfWork() - en.getValue();
            }
        }


        return Qmaint + Qrepl + Qenergy + penalty * 1000;
    }
}
