/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rkv_3;

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
public class Robot implements Runnable {

    PlayerClient client;
    Position2DInterface pos;
    RangerInterface ranger;
    RangerInterface bumper;
    ActionSelector selector;
    Double dist = Double.MAX_VALUE;
    int distSteps = 20;

    public Robot(String host, Integer port) {
        client = new PlayerClient(host, port);

        pos = client.requestInterfacePosition2D(0, PlayerConstants.PLAYER_OPEN_MODE);
        ranger = client.requestInterfaceRanger(0, PlayerConstants.PLAYER_OPEN_MODE);
        bumper = client.requestInterfaceRanger(1, PlayerConstants.PLAYER_OPEN_MODE);

        selector = new ActionSelector();

        client.runThreaded(-1, -1);
    }

    public void run() {
        try {
            while (true) {

                //TODO: implement reinforcement
                // 1. + when correct dirrection (rounded)
                // 2. + when current state is better then previous (unstalled, moved from obstacle...)
                // 3.
                if (bumper.isDataReady() && pos.isDataReady()) {
                    double min = Double.MAX_VALUE;
                    for (double d : bumper.getData().getRanges()) {
                        if (min > d) {
                            min = d;
                        }
                    }

                    if (pos.getData().getStall() == 1) {
                        System.out.println("Negative reinforcement: stalled");
                        selector.learn(-1.0);
                    } else if (min < 0.5) {
                        System.out.println("Negative reinforcement: " + min);
                        selector.learn(-0.8);
//                    } else {
//                        System.out.println("Positive reinforcement");
//                        selector.learn(0.01);
//                    }
                    } else {
                        if (distSteps < 0) {
                            Double d = Math.hypot(pos.getX() - 8, pos.getY() - 8);

                            if (d < dist) {
                                System.out.println("Positive reinforcement: dirrection");
                                selector.learn(0.05);
                            } else {
                                System.out.println("Negative reinforcement: dirrection");
                                selector.learn(-0.9);
                            }

                            dist = d;
                            distSteps = 20;
                        }

                        distSteps--;
                    }
                }

                if (ranger.isDataReady()) {

                    double[] sens = new double[4];

                    for (int i = 0; i < sens.length; i++) {
                        sens[i] = ranger.getData().getRanges()[i] / 5.0;
                    }

                    Action act = selector.select(sens);

                    System.out.println("Action selected: " + act.toString());

                    switch (act) {
                        case StraightForward:
                            pos.setSpeed(1.0, 0.0);
                            break;
                        case LeftForward:
                            pos.setSpeed(0.7, 1.0);
                            break;
                        case RightForward:
                            pos.setSpeed(0.7, -1.0);
                            break;
                        case StraightReverse:
                            pos.setSpeed(-0.7, 0.0);
                            break;
                        case LeftRverse:
                            pos.setSpeed(-0.5, -1.0);
                            break;
                        case RightReverse:
                            pos.setSpeed(-0.5, 1.0);
                            break;
                    }
                }

                Thread.yield();
                Thread.sleep(100);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Robot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
