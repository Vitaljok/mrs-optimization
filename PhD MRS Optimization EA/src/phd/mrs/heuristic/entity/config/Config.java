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
package phd.mrs.heuristic.entity.config;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Vitaljok
 */
@XmlRootElement
public class Config {

    @Deprecated
    public static Integer NUM_ISLANDS = 1;    
    public Integer agentInstanceLimit = 10;
    public Integer populationSize = 200;
    public Integer generationsLimit = 1000000;
    public Integer generationsStep = 100;
    
    
    
    public int minimumPopSizePercent = 0;
    public double selectFromPrevGen = 0.95;
    public boolean keepPopulationSizeConstant = true;
    public double selectorOriginalRate = 0.90;
    public boolean doubletteChromosomesAllowed = true;
    public double crossoverRate = 0.35;
    public int mutationRate = 15;        
    
    public Double nearInfinity = 1.0E12;
    public Double nearZero = 1.0E-12;
}
