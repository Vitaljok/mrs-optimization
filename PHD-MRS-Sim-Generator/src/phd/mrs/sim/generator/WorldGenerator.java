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
public class WorldGenerator {

    Integer sizeX = 120;
    Integer sizeY = 150;
    Integer roadSize = 6;
    Integer centerSize = 20;
    Integer sF = 6;
    BufferedImage image;
    Graphics2D gra;

    public Integer getsF() {
        return sF;
    }

    public Integer getSizeX() {
        return sizeX;
    }

    public Integer getSizeY() {
        return sizeY;
    }

    public WorldGenerator() {
        this.image = new BufferedImage(sizeX * sF, sizeY * sF, BufferedImage.TYPE_INT_RGB);
        this.gra = image.createGraphics();

        gra.setColor(Color.black);
        gra.fillRect(0, 0, sizeX * sF, sizeY * sF);

    }

    public void drawRoads() {
        Stroke roadStroke = new BasicStroke(roadSize * sF);

        gra.setColor(Color.white);
        gra.setStroke(roadStroke);
        //left
        gra.drawLine((roadSize / 2) * sF, 0, (roadSize / 2) * sF, sizeY * sF);
        //right
        gra.drawLine((sizeX - roadSize / 2) * sF, 0, (sizeX - roadSize / 2) * sF, sizeY * sF);
        //top
        gra.drawLine(0, (roadSize / 2) * sF, sizeX * sF, (roadSize / 2) * sF);
        //bottom
        gra.drawLine(0, (sizeY - roadSize / 2) * sF, sizeX * sF, (sizeY - roadSize / 2) * sF);
        //cross
        Integer sizeMin = Math.min(sizeX, sizeY);
        gra.drawLine(0, 0, sizeMin / 2 * sF, sizeMin / 2 * sF);
        gra.drawLine(sizeX * sF, 0, sizeMin / 2 * sF, sizeMin / 2 * sF);
        gra.drawLine(0, sizeY * sF, sizeMin / 2 * sF, (sizeY - sizeMin / 2) * sF);
        gra.drawLine(sizeX * sF, sizeY * sF, sizeMin / 2 * sF, (sizeY - sizeMin / 2) * sF);

        // center
        Integer diff = Math.abs(sizeX - sizeY);
        gra.fillRoundRect((sizeX / 2 - centerSize / 2) * sF,
                (sizeY / 2 - centerSize / 2 - diff / 2) * sF,
                centerSize * sF,
                (centerSize + diff) * sF,
                10 * sF, 10 * sF);
    }

    public void drawObject(SimObject obj) {
        gra.setColor(Color.white);

        Integer x = Math.round((obj.getPoseX() + this.sizeX / 2) * this.sF);
        Integer y = Math.round((obj.getPoseY() + this.sizeY / 2) * this.sF);
        Integer s = Math.round(obj.size * this.sF);

        gra.fillOval(x - s / 2, y - s / 2, s, s);

    }

    public BufferedImage getImage() {
        return image;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        WorldGenerator gen = new WorldGenerator();

        gen.drawRoads();

        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("objects.inc"));

            List<SimObject> objects = new ArrayList<SimObject>();

            objects.add(new SimObjectTree(-40.0f, 65.0f));
            objects.add(new SimObjectTree(-22.5f, 47.5f));
            objects.add(new SimObjectTree(-5.0f, 30.0f));
            objects.add(new SimObjectTree(-50.0f, 55.0f));
            objects.add(new SimObjectTree(-32.5f, 37.5f));
            objects.add(new SimObjectTree(-15.0f, 20.0f));

            objects.add(new SimObjectTree(40.0f, 65.0f));
            objects.add(new SimObjectTree(22.5f, 47.5f));
            objects.add(new SimObjectTree(5.0f, 30.0f));
            objects.add(new SimObjectTree(50.0f, 55.0f));
            objects.add(new SimObjectTree(32.5f, 37.5f));
            objects.add(new SimObjectTree(15.0f, 20.0f));

            objects.add(new SimObjectTree(-40.0f, -65.0f));
            objects.add(new SimObjectTree(-22.5f, -47.5f));
            objects.add(new SimObjectTree(-5.0f, -30.0f));
            objects.add(new SimObjectTree(-50.0f, -55.0f));
            objects.add(new SimObjectTree(-32.5f, -37.5f));
            objects.add(new SimObjectTree(-15.0f, -20.0f));

            objects.add(new SimObjectTree(40.0f, -65.0f));
            objects.add(new SimObjectTree(22.5f, -47.5f));
            objects.add(new SimObjectTree(5.0f, -30.0f));
            objects.add(new SimObjectTree(50.0f, -55.0f));
            objects.add(new SimObjectTree(32.5f, -37.5f));
            objects.add(new SimObjectTree(15.0f, -20.0f));


            objects.add(new SimObjectBrush(-41f, 46f));
            objects.add(new SimObjectBrush(-24f, 29f));
            objects.add(new SimObjectBrush(-31f, 56f));
            objects.add(new SimObjectBrush(-14f, 39f));

            objects.add(new SimObjectBrush(41f, 46f));
            objects.add(new SimObjectBrush(24f, 29f));
            objects.add(new SimObjectBrush(31f, 56f));
            objects.add(new SimObjectBrush(14f, 39f));

            objects.add(new SimObjectBrush(-41f, -46f));
            objects.add(new SimObjectBrush(-24f, -29f));
            objects.add(new SimObjectBrush(-31f, -56f));
            objects.add(new SimObjectBrush(-14f, -39f));

            objects.add(new SimObjectBrush(41f, -46f));
            objects.add(new SimObjectBrush(24f, -29f));
            objects.add(new SimObjectBrush(31f, -56f));
            objects.add(new SimObjectBrush(14f, -39f));


            objects.add(new SimObjectFlowerbed(-15f, 7f));
            objects.add(new SimObjectFlowerbed(-15f, -7f));
            objects.add(new SimObjectFlowerbed(15f, 7f));
            objects.add(new SimObjectFlowerbed(15f, -7f));

            objects.add(new SimObjectFlowerbed(-50f, 20f));
            objects.add(new SimObjectFlowerbed(-50f, -20f));
            objects.add(new SimObjectFlowerbed(50f, 20f));
            objects.add(new SimObjectFlowerbed(50f, -20f));

            objects.add(new SimObjectFlowerbed(0f, 65f));
            objects.add(new SimObjectFlowerbed(0f, -65f));

            for (SimObject o : objects) {
                out.write(o.getCode());
                out.newLine();
                gen.drawObject(o);
            }

            out.close();

        } catch (IOException ex) {
            System.err.println("Error writing text file.");
        }

        BufferedImage lawnImage = gen.getImage();

        // Grass
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("grass.inc"));

            Integer accepted = 0;
            Integer rejected = 0;

            Random rnd = new Random();

            while (accepted < 5000) {
                Integer x = rnd.nextInt(gen.getSizeX() * gen.getsF());
                Integer y = rnd.nextInt(gen.getSizeY() * gen.getsF());

                try {

                    if (lawnImage.getRGB(x, y) == Color.black.getRGB()) {
                        
                        out.write(String.format(Locale.US, "grass ( pose [ %8.3f %8.3f 0 0 ] name \"grass%d\" fiducial_return %d)",
                                1.0 * x / gen.getsF() - gen.getSizeX() / 2,
                                1.0 * y / gen.getsF() - gen.getSizeY() / 2,
                                accepted+1,
                                accepted+1));
                        out.newLine();
                        accepted++;
                    } else {
                        rejected++;
                    }

                } catch (ArrayIndexOutOfBoundsException ex) {
                    System.err.println("X: " + x + "\tY: " + y
                            + "\tImage: " + lawnImage.getWidth() + " X " + lawnImage.getHeight());
                }
            }

            out.close();
            System.out.println("Objects writen: " + accepted + "\tRejected: " + rejected);
        } catch (IOException ex) {
            System.err.println("Error writing grass file.");
        }



        try {
            File outputfile = new File("lawn.png");
            ImageIO.write(lawnImage, "png", outputfile);
        } catch (IOException ex) {
            System.err.println("Error writing image.");
        }
    }
}
