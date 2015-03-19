/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rkv_2;

import javaclient3.BlobfinderInterface;
import javaclient3.structures.PlayerConstants;
import javaclient3.structures.blobfinder.PlayerBlobfinderBlob;

/**
 *
 * @author vitaljok
 */
public class FollowerRobot extends DefaultRobot {

    BlobfinderInterface blob;

    public FollowerRobot(String host, Integer port) {
        super(host, port);

        blob = client.requestInterfaceBlobfinder(0,
                PlayerConstants.PLAYER_OPEN_MODE);
    }

    @Override
    public void calculate() {
        if (blob.isDataReady()
                && blob.getData().getBlobs_count() > 0) {
            follow();
        } else {
            wander();
        }
    }

    private void follow() {
        PlayerBlobfinderBlob b = blob.getData().getBlobs()[0];
        speed = 2.0;
        dir = -(b.getX() - 160.0) / 320;
    }
}
