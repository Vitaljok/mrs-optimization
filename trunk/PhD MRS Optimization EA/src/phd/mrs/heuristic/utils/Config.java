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
    public static Integer DEVICE_LIMIT = 10;
    public static Integer POPULATION_SIZE = 200;
    public static Integer GENERATIONS_LIMIT = 1000000;
    public static Integer GENERATIONS_STEP = 100;
    public static Double INFINITE_COSTS = 1000000000000d;
    public static Double NEAR_ZERO = 0.0000000001;

    static public class CostModel {

        private static double calcFunction(double b0, double b1, double b2, double k, double x) {
            return b0 + b1 * x + b2 * Math.pow(x, k);
        }

        /**
         * 
         * @param N number of components in agent
         * @return Agent assembly costs
         */
        public static double getAssembly(int N, double cmplx) {
            return calcFunction(
                    Coef.Assembly.b0,
                    Coef.Assembly.b1,
                    Coef.Assembly.b2,
                    Coef.Assembly.k,
                    N) * cmplx;
        }

        /**
         * 
         * @param N number of components in agent
         * @return Agent design costs
         */
        public static double getDesign(int N) {
            return calcFunction(
                    Coef.Design.b0,
                    Coef.Design.b1,
                    Coef.Design.b2,
                    Coef.Design.k,
                    N);
        }

        /**
         * 
         * @param N number of components in agent
         * @return Agent energy costs
         */
        public static double getEnergyLoss(int N) {
            return calcFunction(
                    Coef.EnergyLoss.b0,
                    Coef.EnergyLoss.b1,
                    Coef.EnergyLoss.b2,
                    Coef.EnergyLoss.k,
                    N);
        }

        /**
         * 
         * @param N total number of agents in system
         * @return system design costs
         */
        public static double getSysDesign(int N) {
            return calcFunction(
                    Coef.SysDesign.b0,
                    Coef.SysDesign.b1,
                    Coef.SysDesign.b2,
                    Coef.SysDesign.k,
                    N);
        }

        /**
         * 
         * @param N total number of agents in system
         * @return system maintenence costs
         */
        public static double getSysMaint(int N) {
            return calcFunction(
                    Coef.SysMaint.b0,
                    Coef.SysMaint.b1,
                    Coef.SysMaint.b2,
                    Coef.SysMaint.k,
                    N);
        }
    }

    /**
     * Coefficient for cost estimation model. 
     * Calculations are based on polynomial regression:
     *  f(x) = b0 + b1*x + b2*x^k    
     */
    static public class Coef {

        /**
         * Agent design cost coefficients
         */
        static public class Design {

            public static double b0 = 40;
            public static double b1 = 10;
            public static double b2 = 0.5;
            public static double k = 2;
        }

        /**
         * Agent assembly cost coefficients
         */
        static public class Assembly {

            public static double b0 = 10;
            public static double b1 = 5;
            public static double b2 = 0.02;
            public static double k = 3;
        }
        
        /**
         * System design coefficients
         */
        static public class SysDesign {

            public static double b0 = 280;
            public static double b1 = 20;
            public static double b2 = 2;
            public static double k = 2;
        }


        /**
         * Agent energy loss coefficients
         */
        static public class EnergyLoss {

            public static double b0 = 0;
            public static double b1 = 1;
            public static double b2 = 0.01;
            public static double k = 2;
        }

        
        /**
         * System maintenance coefficients
         */
        static public class SysMaint {

            public static double b0 = 8;
            public static double b1 = 2;
            public static double b2 = 0.1;
            public static double k = 2;
        }
        /**
         * Eventual replacement rate
         */
        public static Double systemReplRate = 0.005d;
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
