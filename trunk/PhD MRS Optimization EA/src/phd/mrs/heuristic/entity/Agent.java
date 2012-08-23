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
package phd.mrs.heuristic.entity;

import java.util.ArrayList;
import java.util.List;
import phd.mrs.heuristic.utils.CachedProperty;
import phd.mrs.heuristic.utils.Config;
import phd.mrs.heuristic.utils.Debug;

/**
 *
 * @author Vitaljok
 */
public class Agent {

    private List<Component> components = new ArrayList<Component>();
    private CachedProperty<Double> designCosts = new CachedProperty<Double>() {

        @Override
        protected Double calculateValue() {
            Debug.log.fine("Calculated cached desing costs");
            return Config.Coef.agentDesingLin * Math.exp(Config.Coef.agentDesignExp * getComponents().size());
        }
    };
    private CachedProperty<Double> productionCosts = new CachedProperty<Double>() {

        @Override
        protected Double calculateValue() {
            double costs = 0d;
            double maxComplexity = 0d;

            // sum of components
            for (Component comp : getComponents()) {
                double compPrice = comp.getDoubleProperty(Config.Prop.investmentCosts);
                maxComplexity = Math.max(maxComplexity, comp.getDoubleProperty(Config.Prop.complexity, 1.0));
                costs += compPrice;
            }

            // add assembly costs
            costs += Config.Coef.agentAssyLin * Math.exp(Config.Coef.agentAssyExp * getComponents().size()) * maxComplexity;

            Debug.log.fine("Calculated cached production costs");

            return costs;
        }
    };
    private CachedProperty<Double> operatingEnergy = new CachedProperty<Double>() {

        @Override
        protected Double calculateValue() {
            Debug.log.fine("Calculated cached operating energy");

            double energy = 0d;
            for (Component comp : components) {
                energy += comp.getDoubleProperty(Config.Prop.operatingPower);
            }

            return energy + Config.Coef.agentEnergyLossLin * Math.exp(Config.Coef.agentEnergyLossExp * getComponents().size());
        }
    };

    public Double getOperatingEnergy() {
        return operatingEnergy.getValue();
    }    

    protected Agent() {
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
}
