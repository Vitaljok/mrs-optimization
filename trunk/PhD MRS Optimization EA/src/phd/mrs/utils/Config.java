/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phd.mrs.utils;

/**
 *
 * @author Vitaljok
 */
public class Config {
    public static Integer NUM_ISLANDS = 3;
    public static Integer DEVICE_LIMIT = 10;
    public static Integer POPULATION_SIZE = 100;
    public static Integer GENERATIONS_LIMIT = 500;
    public static Integer GENERATIONS_STEP = 100;
    
    /**
     * Investment costs of component (absolute). 
     */
    public static String propInvestmentCosts = "investmentCosts";
    
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
