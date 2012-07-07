/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phd.mrs.sim.control;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaclient3.FiducialInterface;
import javaclient3.PlayerClient;
import javaclient3.Position2DInterface;
import javaclient3.structures.PlayerConstants;
import javaclient3.structures.fiducial.PlayerFiducialItem;
import phd.mrs.sim.control.util.ProcessingException;

/**
 *
 * @author Vitaljok
 */
public class RobotWander extends AbstractPlayerCtrl {

    private Position2DInterface pos;
    private FiducialInterface fid;
    private Random rnd;
    private WorldCtrl world;
    private Double dir = 0.0;

    public RobotWander(String host, Integer port, WorldCtrl world) {
        super(host, port, 50);
        this.world = world;
    }

    @Override
    public void beforeStart() {
        rnd = new Random();
        pos = client.requestInterfacePosition2D(0, PlayerConstants.PLAYER_OPEN_MODE);
        fid = client.requestInterfaceFiducial(0, PlayerConstants.PLAYER_OPEN_MODE);
    }

    @Override
    public void process() throws ProcessingException {
        
        if (fid.isDataReady()) {
            for (PlayerFiducialItem f : fid.getData().getFiducials()) {
                world.addGrassItemToMow("grass"+f.getId());
            }
        }
        
        
        dir += rnd.nextDouble() * 1 - 0.5;

        dir = Math.max(dir, -2);
        dir = Math.min(dir, 2);

        pos.setSpeed(3.0, dir);
    }
}
