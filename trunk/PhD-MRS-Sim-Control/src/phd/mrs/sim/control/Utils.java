/*
 * Licensed under the Academic Free License version 3.0
 */
package phd.mrs.sim.control;

/**
 *
 * @author Vitaljok
 */
public class Utils {

    static public Double scale(Double value, Double valueMin, Double valueMax,
            Double targetMin, Double targetMax) {
        return (value - valueMin) * (targetMax - targetMin) / (valueMax - valueMin) + targetMin;
    }

    static public Double limit(Double value, Double valueMin, Double valueMax) {
        return Math.max(Math.min(value, valueMax), valueMin);
    }

    static public Double scaleLimit(Double value, Double valueMin, Double valueMax, Double targetMin, Double targetMax) {
        return limit(scale(value, valueMin, valueMax, targetMin, targetMax), targetMin, targetMax);
    }
}
