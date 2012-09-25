/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phd.mrs.sim.control;

import java.util.Random;
import javaclient3.Graphics2DInterface;
import javaclient3.PlayerClient;
import javaclient3.structures.PlayerColor;
import javaclient3.structures.PlayerConstants;
import javaclient3.structures.PlayerPoint2d;
import javaclient3.structures.graphics2d.PlayerGraphics2dCmdPolygon;

/**
 *
 * @author Vitaljok
 */
public class RobotGraph2d extends Thread {

    PlayerClient client;
    Graphics2DInterface gra;
    
    Random rnd;
    
    
    public RobotGraph2d(String host, Integer port) {
        super("robot@"+host+":"+port);
        
        rnd = new Random();
        
        client = new PlayerClient(host, port);       
        gra = client.requestInterfaceGraphics2D(0, PlayerConstants.PLAYER_OPEN_MODE);        
    }

    @Override
    public synchronized void start() {
        client.runThreaded(-1, -1);
        super.start();
    }

    @Override
    public void run() {
        
        //drawing 2D graphics
        
        gra.clearScreen();
        
        PlayerGraphics2dCmdPolygon poly = new PlayerGraphics2dCmdPolygon();

        PlayerColor redColor = new PlayerColor();
        redColor.setRed(255);
        redColor.setGreen(0);
        PlayerColor blueColor = new PlayerColor();
        blueColor.setBlue(255);
        redColor.setGreen(0);
        
        poly.setColor(redColor);
        poly.setFill_color(blueColor);
        poly.setFilled(1);
        poly.setCount(3);
        
        PlayerPoint2d[] points = new PlayerPoint2d[3];
        points[0] = new PlayerPoint2d();
        points[0].setPx(-5);
        
        points[1] = new PlayerPoint2d();
        points[1].setPx(5);
        
        points[2] = new PlayerPoint2d();
        points[2].setPy(10);       
        
        poly.setPoints(points);
        
        gra.drawPolygon(poly);              
    }
}
