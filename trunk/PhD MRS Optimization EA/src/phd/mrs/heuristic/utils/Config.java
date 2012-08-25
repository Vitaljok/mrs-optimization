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

    public static Integer NUM_ISLANDS = 1;
    public static Integer DEVICE_LIMIT = 3;
    public static Integer POPULATION_SIZE = 20;
    public static Integer GENERATIONS_LIMIT = 100;
    public static Integer GENERATIONS_STEP = 10;
    public static Double INFINITE_COSTS = 1000000000d;

    static public class Coef {

        /**
         * Exponential coefficient for agent assembly price calculation.
         * price = c_lin * EXP(c_exp * Ncomp);
         */
        public static Double agentAssyExp = 0.15d;
        /**
         * Linear coefficient for agent assembly price calculation.
         * price = c_lin * EXP(c_exp * Ncomp);
         */
        public static Double agentAssyLin = 20.0d;
        /**
         * Exponential coefficient for agent design price calculation.
         * price = c_lin * EXP(c_exp * Ncomp);
         */
        public static Double agentDesignExp = 0.2d;
        /**
         * Linear coefficient for agent design price calculation.
         * price = c_lin * EXP(c_exp * Ncomp);
         */
        public static Double agentDesingLin = 140.0d;
        /**
         * System design coefficient         
         */
        public static Double systemDesign = 0.4d;
        /**
         * Exponential coefficient for system maintenence calculation.
         * price = c_lin * EXP(c_exp * Nagent);
         */
        public static Double systemMaintExp = 0.2d;
        /**
         * Linear coefficient for system maintenence calculation.
         * price = c_lin * EXP(c_exp * Nagent);
         */
        public static Double systemMaintLin = 20.0d;
        
        /**
         * Eventual replacement rate
         */
        public static Double systemReplRate = 0.05d;
        
        /**
         * Exponential coefficient for agent energy loss calculation.
         * price = c_lin * EXP(c_exp * Ncomp);
         */
        public static Double agentEnergyLossExp = 0.2d;
        /**
         * Linear coefficient for agent energy loss calculation.
         * price = c_lin * EXP(c_exp * Ncomp);
         */
        public static Double agentEnergyLossLin = 140.0d;
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
         * Operating power of component (absolute) per time unit. 
         */
        public static String operatingPower = "operatingPower";
        /**
         * Complexity of of component (relative). Increases design price of device. 
         * Default value is 1.0
         */
        public static String complexity = "complexity";
        /**
         * Mission size on X axis.
         */
        @Deprecated
        public static String missionSizeX = "missionSizeX";
        /**
         * Mission size on Y axis.
         */
        @Deprecated
        public static String missionSizeY = "missionSizeY";
        
        /**
         * The width of work device used in @AreaCoverageMission calculations
         */
        public static String workDeviceWidth = "workDeviceWidth";
        /**
         * The width of work device used in @AreaCoverageMission calculations
         */
        public static String speed = "speed";
    }
}
