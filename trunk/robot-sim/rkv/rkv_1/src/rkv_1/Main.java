/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rkv_1;

import java.util.Random;
import javaclient3.PlayerClient;
import javaclient3.Position2DInterface;
import javaclient3.RangerInterface;
import javaclient3.structures.PlayerConstants;

/**
 *
 * @author vitaljok
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        PlayerClient client = new PlayerClient("localhost", 6665);

        Position2DInterface pos = client.requestInterfacePosition2D(0,
                PlayerConstants.PLAYER_OPEN_MODE);
        RangerInterface ranger = client.requestInterfaceRanger(0,
                PlayerConstants.PLAYER_OPEN_MODE);

        client.runThreaded(-1, -1);


        Random rnd = new Random();

        Double wanderDir = rnd.nextDouble() * 2 - 1;

        while (true) {
            while (!ranger.isDataReady()) {
                Thread.sleep(10);
            }

            Double minLeft = Double.MAX_VALUE;
            Double minCenter = Double.MAX_VALUE;
            Double minRight = Double.MAX_VALUE;

            double[] ranges = ranger.getData().getRanges();

            Integer rcnt = ranges.length / 3;

            for (int i = 0; i < ranges.length; i++) {
                double r = ranges[i];

                if (i <= rcnt && minRight > r) {
                    minRight = r;
                } else if (i > rcnt && i < rcnt * 2 && minCenter > r) {
                    minCenter = r;
                } else if (i >= rcnt * 2 && minLeft > r) {
                    minLeft = r;
                }

                if (minCenter < 1) {
                    pos.setSpeed(-0.5,
                            rnd.nextDouble() * 2 - 1);
                } else if (minLeft < 2) {
                    pos.setSpeed(0.7, -1);
                } else if (minRight < 2) {
                    pos.setSpeed(0.7, +1);
                } else {
                    wanderDir = +rnd.nextDouble() * 0.5 - 0.25;

                    if (wanderDir > 1.0) {
                        wanderDir = 1.0;
                    } else if (wanderDir < -1.0) {
                        wanderDir = -1.0;
                    }

                    pos.setSpeed(1.0, wanderDir);
                }

            }
        }
    }
}
