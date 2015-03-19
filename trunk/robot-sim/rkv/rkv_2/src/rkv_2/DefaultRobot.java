/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rkv_2;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaclient3.PlayerClient;
import javaclient3.Position2DInterface;
import javaclient3.RangerInterface;
import javaclient3.structures.PlayerConstants;

/**
 *
 * @author vitaljok
 */
public class DefaultRobot implements Runnable {

    PlayerClient client;
    Position2DInterface pos;
    RangerInterface ranger;
    Random rnd;
    Double dir = 0.0;
    Double speed = 0.0;
    Boolean runThreaded = false;

    public DefaultRobot(String host, Integer port) {
        client = new PlayerClient(host, port);

        pos = client.requestInterfacePosition2D(0, PlayerConstants.PLAYER_OPEN_MODE);
        ranger = client.requestInterfaceRanger(0, PlayerConstants.PLAYER_OPEN_MODE);

        rnd = new Random();
        dir = rnd.nextDouble() * 2 - 1;
    }

    protected void avoidObstacles() {
        Double minLeft = Double.MAX_VALUE;
        Double minCenter = Double.MAX_VALUE;
        Double minRight = Double.MAX_VALUE;
        double[] ranges = ranger.getData().getRanges();
        int rcnt = ranges.length / 3;
        for (int i = 0; i < ranges.length; i++) {
            Double r = ranges[i];
            if (i <= rcnt && minRight > r) {
                minRight = r;
            } else if (i > rcnt && i < rcnt * 2 && minCenter > r) {
                minCenter = r;
            } else if (i >= rcnt * 2 && minLeft > r) {
                minLeft = r;
            }
        }
        if (minCenter < 1.0) {
            speed = 0.0;
            dir = -1.0; //rnd.nextDouble() * 2 - 1;
        } else if (minLeft < 2) {
            speed = 0.7;
            dir = -1.0;
        } else if (minRight < 2) {
            speed = 0.7;
            dir = 1.0;
        }
    }

    public void wander() {
        dir += rnd.nextDouble() * 1 - 0.5;

        if (dir > 1.0) {
            dir = 1.0;
        } else if (dir < -1.0) {
            dir = -1.0;
        }
        speed = 1.0;
    }

    public void calculate() {
        wander();
    }

    public void run() {
        if (!runThreaded) {
            client.runThreaded(-1, -1);
            runThreaded = true;
        }
        try {
            while (true) {
                if (ranger.isDataReady()) {

                    calculate();
                    avoidObstacles();

                    pos.setSpeed(speed, dir);

                }

                Thread.yield();
                Thread.sleep(50);

            }
        } catch (InterruptedException ex) {
            Logger.getLogger(DefaultRobot.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
