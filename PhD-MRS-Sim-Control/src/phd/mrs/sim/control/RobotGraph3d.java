/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phd.mrs.sim.control;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaclient3.FiducialInterface;
import javaclient3.Graphics2DInterface;
import javaclient3.Graphics3DInterface;
import javaclient3.PlayerClient;
import javaclient3.Position2DInterface;
import javaclient3.structures.PlayerColor;
import javaclient3.structures.PlayerConstants;
import javaclient3.structures.PlayerPoint2d;
import javaclient3.structures.PlayerPoint3d;
import javaclient3.structures.graphics3d.PlayerGraphics3dCmdDraw;

/**
 *
 * @author Vitaljok
 */
public class RobotGraph3d extends Thread {

    PlayerClient client;
    Graphics3DInterface gra;
    Random rnd;

    public RobotGraph3d(String host, Integer port) {
        super("robot@" + host + ":" + port);

        rnd = new Random();

        client = new PlayerClient(host, port);
        gra = client.requestInterfaceGraphics3D(0, PlayerConstants.PLAYER_OPEN_MODE);

    }

    @Override
    public synchronized void start() {
        client.runThreaded(-1, -1);
        super.start();
    }

    @Override
    public void run() {

        //drawing 3D objects

        while (true) {
            try {
                gra.clearScreen();

                PlayerGraphics3dCmdDraw cmd = new PlayerGraphics3dCmdDraw();

                PlayerColor redColor = new PlayerColor();
                redColor.setRed(255);
                redColor.setGreen(0);

                cmd.setColor(redColor);

                cmd.setDraw_mode(9);//PlayerConstants.PLAYER_GRAPHICS3D_MODE_LINES);
                cmd.setCount(4);

                PlayerPoint3d[] points = new PlayerPoint3d[4];
                points[0] = new PlayerPoint3d();
                points[0].setPx(1);
                points[0].setPy(0);
                points[0].setPz(1);

                points[1] = new PlayerPoint3d();
                points[1].setPx(-1);
                points[1].setPy(0);
                points[1].setPz(1);

                points[2] = new PlayerPoint3d();
                points[2].setPx(0);
                points[2].setPy(1);
                points[2].setPz(2);
                
                points[3] = new PlayerPoint3d();
                points[3].setPx(2);
                points[3].setPy(2);
                points[3].setPz(2);

                cmd.setPoints(points);

                gra.draw(cmd);
                
                cmd.setColor( new PlayerColor());
                
                cmd.setDraw_mode(3);
                gra.draw(cmd);
                
                System.out.println("Loop");

                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(RobotGraph3d.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
