/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rkv.agents.utils;

import java.awt.geom.Point2D;

/**
 *
 * @author Vitaljok
 */
public class Utils {

    public static double angleR(Point2D.Double a, Point2D.Double b, Point2D.Double c) {
        Point2D.Double AB = new Point2D.Double(b.x - a.x, b.y - a.y);
        Point2D.Double AC = new Point2D.Double(c.x - a.x, c.y - a.y);

        double cosA = (AB.x * AC.x + AB.y * AC.y) / (a.distance(b) * a.distance(c));

        return Math.acos(cosA);
    }

    public static double angle(Point2D.Double a, Point2D.Double b, Point2D.Double c) {
        return Math.toDegrees(angleR(a, b, c));
    }

    public static double dirrection(Point2D.Double a, Point2D.Double b, Point2D.Double target) {
        return ((b.x - a.x) * (target.y - a.y) - (b.y - a.y) * (target.x - a.x));
    }

    public static Boolean isLeft(Point2D.Double a, Point2D.Double b, Point2D.Double target) {
        return dirrection(a, b, target) > 0;
    }

    public static Boolean isRight(Point2D.Double a, Point2D.Double b, Point2D.Double target) {
        return dirrection(a, b, target) < 0;
    }

    public static Double limit(final Double value, Double lower, Double upper) {

        if (value > upper) {
            return upper;
        }

        if (value < lower) {
            return lower;
        }

        return value;
    }
}
