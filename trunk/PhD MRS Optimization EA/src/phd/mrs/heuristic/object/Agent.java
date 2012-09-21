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
package phd.mrs.heuristic.object;

import java.util.ArrayList;
import java.util.List;
import phd.mrs.heuristic.utils.CachedProperty;
import phd.mrs.heuristic.utils.Debug;

/**
 *
 * @author Vitaljok
 */
public class Agent implements Cloneable {

    private Project project;
    private List<Component> components = new ArrayList<>();
    private CachedProperty<Double> designCosts = new CachedProperty<Double>() {

        @Override
        protected Double calculateValue() {
            Debug.log.fine("Calculated cached desing costs");
            return project.costModel.calcDesign(getComponents().size());
        }
    };
    private CachedProperty<Double> productionCosts = new CachedProperty<Double>() {

        @Override
        protected Double calculateValue() {
            Debug.log.fine("Calculated cached production costs");
            double costs = 0d;
            double maxComplexity = 0d;

            // sum of components
            for (Component comp : getComponents()) {
                double compPrice = comp.getInvestmentCosts();
                maxComplexity = Math.max(maxComplexity, comp.getComplexity());
                costs += compPrice;
            }

            // add assembly costs
            costs += project.costModel.calcAssembly(getComponents().size(), maxComplexity);
            return costs;
        }
    };
    private CachedProperty<Double> operatingEnergy = new CachedProperty<Double>() {

        @Override
        protected Double calculateValue() {
            Debug.log.fine("Calculated cached operating energy");

            double energy = 0d;
            for (Component comp : components) {
                energy += comp.getOperatingPower();
            }

            // add energy loss
            energy += project.costModel.calcEnergyLoss(getComponents().size());
            return energy;
        }
    };

    public Double getOperatingEnergy() {
        return operatingEnergy.getValue();
    }

    public Agent(Project project) {
        this.project = project;
        Debug.log.finest("New agent created");
    }   

    public List<Component> getComponents() {
        return components;
    }

    public Double getProductionCosts() {
        return this.productionCosts.getValue();
    }

    public Double getDesignCosts() {
        return this.designCosts.getValue();
    }

    @Override
    public Agent clone() {
        Agent new_agent = new Agent(project);
        new_agent.components = this.components;
        new_agent.operatingEnergy = this.operatingEnergy;
        new_agent.productionCosts = this.productionCosts;
        new_agent.designCosts = this.designCosts;

        return new_agent;
    }
}
