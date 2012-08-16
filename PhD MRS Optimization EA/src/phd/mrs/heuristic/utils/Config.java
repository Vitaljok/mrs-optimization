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
package phd.mrs.heuristic.utils;

/**
 *
 * @author Vitaljok
 */
public class Config {

    public static Integer NUM_ISLANDS = 5;
    public static Integer DEVICE_LIMIT = 10;
    public static Integer POPULATION_SIZE = 200;
    public static Integer GENERATIONS_LIMIT = 5000;
    public static Integer GENERATIONS_STEP = 100;

    static public class Coef {

        /**
         * Exponential coefficient in device production price calculation.
         * price coefficient = c_lin * EXP(c_exp * price);
         */
        public static Double agentProductionExp = 0.05d;
        /**
         * Linear coefficient in device production price calculation.
         * price coefficient = c_lin * EXP(c_exp * price);
         */
        public static Double agentProductionLin = 1.0d;
        /**
         * Exponential coefficient in device operating price calculation.
         * price coefficient = c_lin * EXP(c_exp * price);
         */
        public static Double agentOperatingExp = 0.05d;
        /**
         * Linear coefficient in device operating price calculation.
         * price coefficient = c_lin * EXP(c_exp * price);
         */
        public static Double agentOperatingLin = 1.0d;
    }

    static public class Prop {

        /**
         * Family of the component.
         */
        public static String compFamily = "family";
        /**
         * Investment costs of component (absolute). 
         */
        public static String investmentCosts = "investmentCosts";
        /**
         * Operating costs of component (absolute) per time unit. 
         */
        public static String operatingCosts = "operatingCosts";
        /**
         * Complexity of of component (relative). Increases design price of device. 
         * Default value is 1.0
         */
        public static String complexity = "complexity";
        /**
         * Mission size on X axis.
         */
        public static String missionSizeX = "missionSizeX";
        /**
         * Mission size on Y axis.
         */
        public static String missionSizeY = "missionSizeY";
    }
}
