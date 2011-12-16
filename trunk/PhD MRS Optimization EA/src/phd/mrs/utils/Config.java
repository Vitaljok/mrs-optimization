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
package phd.mrs.utils;

/**
 *
 * @author Vitaljok
 */
public class Config {

    public static Integer NUM_ISLANDS = 1;
    public static Integer DEVICE_LIMIT = 10;
    public static Integer POPULATION_SIZE = 100;
    public static Integer GENERATIONS_LIMIT = 500;
    public static Integer GENERATIONS_STEP = 100;
    /**
     * Family of the component.
     */
    public static String propCompFamily = "family";
    /**
     * Investment costs of component (absolute). 
     */
    public static String propInvestmentCosts = "investmentCosts";
    /**
     * Investment costs of component (absolute). 
     */
    public static String propRunningCosts = "runningCosts";
    /**
     * Complexity of of component (relative). Increases design price of device. 
     * Default value is 1.0
     */
    public static String propComplexity = "complexity";
    /**
     * Exponential coefficient in device production price calculation.
     * price coefficient = c_lin * EXP(c_exp * price);
     */
    public static Double C_DEVICE_PRODUCTION_EXP = 0.05d;
    /**
     * Linear coefficient in device production price calculation.
     * price coefficient = c_lin * EXP(c_exp * price);
     */
    public static Double C_DEVICE_PRODUCTION_LIN = 1.0d;
}
