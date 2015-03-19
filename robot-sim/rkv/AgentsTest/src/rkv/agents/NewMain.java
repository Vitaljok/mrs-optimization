/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rkv.agents;

import java.awt.geom.Point2D;
import rkv.agents.utils.Utils;

/**
 *
 * @author Vitaljok
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println(Utils.isLeft(
                new Point2D.Double(3d, 3d),
                new Point2D.Double(2d, 5d),
                new Point2D.Double(-12d, 1d)));
    }
}
