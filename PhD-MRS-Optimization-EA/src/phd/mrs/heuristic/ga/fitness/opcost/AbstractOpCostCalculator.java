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
package phd.mrs.heuristic.ga.fitness.opcost;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import phd.mrs.heuristic.object.Agent;
import phd.mrs.heuristic.object.Project;
import phd.mrs.heuristic.object.config.Config;

/**
 *
 * @author Vitaljok
 */
public abstract class AbstractOpCostCalculator {
    Project project;
    Integer scale;
    Double valueStep;

    public AbstractOpCostCalculator(Project project, Integer scale) {
        
        this.project = project;
        this.scale = scale;
        
        this.valueStep = roundUp(project.getConfig().getNearZero());
    }

    public abstract Double calcOpCosts(List<Agent> mrs, Double Qinv);

    final Double roundUp(Double value) {
        return (new BigDecimal(value)).setScale(this.scale, RoundingMode.UP).doubleValue();
    }
    
    
}
