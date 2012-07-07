/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phd.mrs.sim.generator;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author Vitaljok
 */
public class SimpleGrassGenerator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Double x1 = 0d;
        Double x2 = 30d;
        Double y1 = 0d;
        Double y2 = 30d;
        Integer num = 500;

        // Grass
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("C:\\Users\\Vitaljok\\Documents\\Dropbox\\robot\\configs\\grass_simple.inc"));

            Integer accepted = 0;
            Integer rejected = 0;

            Random rnd = new Random();

            while (accepted < num) {
                Double x = (x2 - x1) * rnd.nextDouble() + x1;
                Double y = (y2 - y1) * rnd.nextDouble() + y1;

                out.write(String.format(Locale.US, "grass ( pose [ %8.3f %8.3f 0 0 ] name \"grass%d\" fiducial_return %d)",
                        x,
                        y,
                        accepted + 1,
                        accepted + 1));
                out.newLine();
                accepted++;
            }

            out.close();
            System.out.println("Objects writen: " + accepted + "\tRejected: " + rejected);
        } catch (IOException ex) {
            System.err.println("Error writing grass file.");
            System.err.println(ex);
        }
    }
}
