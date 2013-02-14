/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rkv_test;

import javaclient3.FiducialInterface;
import javaclient3.GripperInterface;
import javaclient3.PlayerClient;
import javaclient3.Position2DInterface;
import javaclient3.structures.PlayerConstants;
import javaclient3.structures.fiducial.PlayerFiducialItem;

/**
 *
 * @author vitaljok
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {

        String host = "localhost";
        Integer port = 6665;

        if (args.length > 0) {
            host = args[0];
        }

        if (args.length > 1) {
            port = new Integer(args[1]);
        }

        PlayerClient client = new PlayerClient(host, port);

        Position2DInterface pos = client.requestInterfacePosition2D(0, PlayerConstants.PLAYER_OPEN_MODE);
        FiducialInterface fid = client.requestInterfaceFiducial(0, PlayerConstants.PLAYER_OPEN_MODE);
        GripperInterface grip = client.requestInterfaceGripper(0, PlayerConstants.PLAYER_OPEN_MODE);

        client.runThreaded(-1, -1);

        Boolean haveObject = false;

        while (true) {
            if (fid.isDataReady()) {
                PlayerFiducialItem item = null;

                for (PlayerFiducialItem im : fid.getData().getFiducials()) {
                    if (im.getId() == 10 && !haveObject) {
                        item = im;
                    } else if (im.getId() == 11 && haveObject) {
                        item = im;
                    }
                }

                if (item != null) {
                    pos.setSpeed(Math.min(1.0, item.getPose().getPx() - 0.5), item.getPose().getPy());


                } else {
                    pos.setSpeed(0, 1);
                }
            }

            if (grip.isDataReady()) {
                if (grip.getData().getBeams() > 0
                        && grip.getData().getState() != 2) {
                    pos.setSpeed(0, 0);
                    grip.close();
                } else if (grip.getData().getState() == 2) {
                    haveObject = true;
                }
            }


            Thread.sleep(50);
        }
    }
}
