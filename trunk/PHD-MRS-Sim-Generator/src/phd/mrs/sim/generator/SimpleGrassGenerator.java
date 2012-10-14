/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phd.mrs.sim.generator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 *
 * @author Vitaljok
 */
public class SimpleGrassGenerator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Double x1 = -35d;
        Double x2 = 35d;
        Double y1 = -40d;
        Double y2 = 40d;
        Integer num = 2000;

        List<Area> houses = new ArrayList<>();

        houses.add(new Area(27.5d, -34d, 15d+5, 12d+5));
        houses.add(new Area(-10d, 15d, 6d+5, 18d+5));

        // Grass
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("..\\robot-sim\\configs\\grass_simple.inc"));

            Integer accepted = 0;
            Integer rejected = 0;

            Random rnd = new Random();

            while (accepted < num) {
                Double x = (x2 - x1) * rnd.nextDouble() + x1;
                Double y = (y2 - y1) * rnd.nextDouble() + y1;

                boolean ok = true;

                for (Area area : houses) {
                    ok = ok && !area.isInside(x, y);
                }

                if (ok) {
                    out.write(String.format(Locale.US, "grass ( pose [ %8.3f %8.3f 0 0 ] name \"grass%d\" fiducial_return %d)",
                            x,
                            y,
                            accepted + 1,
                            accepted + 1));
                    out.newLine();
                    accepted++;
                } else {
                    rejected++;
                }

            }

            out.close();
            System.out.println("Objects writen: " + accepted + "\tRejected: " + rejected);
        } catch (IOException ex) {
            System.err.println("Error writing grass file.");
            System.err.println(ex);
        }
    }
}

class Area {

    private Double x1;
    private Double x2;
    private Double y1;
    private Double y2;

    public Area(Double x, Double y, Double w, Double h) {
        this.x1 = x - w / 2;
        this.x2 = x + w / 2;
        this.y1 = y - h / 2;
        this.y2 = y + h / 2;
    }

    public boolean isInside(Double x, Double y) {
        if (x1 <= x && x <= x2
                && y1 <= y && y <= y2) {
            return true;
        } else {
            return false;
        }
    }
}