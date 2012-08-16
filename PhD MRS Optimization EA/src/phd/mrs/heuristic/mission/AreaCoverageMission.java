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
package phd.mrs.heuristic.mission;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import phd.mrs.heuristic.entity.Component;
import phd.mrs.heuristic.entity.Agent;
import phd.mrs.heuristic.utils.CachedProperty;
import phd.mrs.heuristic.utils.Config;

/**
 *
 * @author Vitaljok
 */
public class AreaCoverageMission extends AbstractMission {

    List<Component> locomotionComponents = new ArrayList<Component>();
    List<Component> navigationComponents = new ArrayList<Component>();
    Map<Agent, Integer> solution;
    Integer sizeX;
    Integer sizeY;
    CachedProperty<Double> locomotionPrice = new CachedProperty<Double>() {

        @Override
        protected Double calculateValue() {
            Double price = 0d;
            if (locomotionComponents.isEmpty()) {
                throw new RuntimeException("Locomotion components are not defined!");
            }
            for (Component comp : locomotionComponents) {
                price += comp.getDoubleProperty(Config.Prop.operatingCosts);
            }
            return price;
        }
    };
    CachedProperty<Double> navigationPrice = new CachedProperty<Double>() {

        @Override
        protected Double calculateValue() {
            Double price = 0d;
            if (navigationComponents.isEmpty()) {
                throw new RuntimeException("Navigation components are not defined!");
            }
            for (Component comp : navigationComponents) {
                price += comp.getDoubleProperty(Config.Prop.operatingCosts);
            }
            return price;
        }
    };

    public AreaCoverageMission(Properties properties, Map<Agent, Integer> solution) {
        super(properties);
        try {
            sizeX = Integer.valueOf(properties.getProperty(Config.Prop.missionSizeX));
        } catch (Exception ex) {
            throw new RuntimeException(
                    MessageFormat.format("AreaCoverageMission does not have valid \"{0}\" property!",
                    Config.Prop.missionSizeX));
        }

        try {
            sizeY = Integer.valueOf(properties.getProperty(Config.Prop.missionSizeY));
        } catch (Exception ex) {
            throw new RuntimeException(
                    MessageFormat.format("AreaCoverageMission does not have valid \"{0}\" property!",
                    Config.Prop.missionSizeY));
        }

        this.solution = solution;
    }

    public List<Component> getLocomotionComponents() {
        return locomotionComponents;
    }

    public List<Component> getNavigationComponents() {
        return navigationComponents;
    }

    public Map<Agent, Integer> getSolution() {
        return solution;
    }

    public void setSolution(Map<Agent, Integer> solution) {
        this.solution = solution;
    }

    public void process() {
        Double costs = 0d;
        Integer ops = sizeX * sizeY;
        Integer devs = 0;

        for (Agent dev : solution.keySet()) {
            if (dev.getComponents().containsAll(locomotionComponents)) {
                devs += solution.get(dev);
            }
        }
        
        // now I have ops * price formula. 
        // TODO: implement advanced time assignment model for different devices
        costs = Math.ceil(1.0d * ops / devs) * locomotionPrice.getValue() * devs;        

        System.out.println("Operating costs: " + costs+"\t"+devs);
    }

    @Override
    public Double getTime() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
