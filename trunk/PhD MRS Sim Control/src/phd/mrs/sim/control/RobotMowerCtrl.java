/*
 * Licensed under the Academic Free License version 3.0
 */
package phd.mrs.sim.control;

import javaclient3.BlobfinderInterface;
import javaclient3.FiducialInterface;
import javaclient3.Position2DInterface;
import javaclient3.RangerInterface;
import javaclient3.structures.PlayerConstants;
import phd.mrs.sim.control.brains.RobotMowerBrains;
import phd.mrs.sim.control.debug.RobotMowerFrame;
import phd.mrs.sim.control.util.ProcessingException;

/**
 *
 * @author Vitaljok
 */
public class RobotMowerCtrl extends AbstractPlayerCtrl {

    Position2DInterface pos;
    RangerInterface ranger;
    FiducialInterface cropper;
    BlobfinderInterface blob;
    RobotMowerBrains brains;
    private RobotMowerFrame robotFrame;

    public RobotMowerCtrl(String host, Integer port) {
        super(host, port, 50);

        this.robotFrame = new RobotMowerFrame("Robot @" + port);
        this.robotFrame.setLocation(10, (port - 6665) * 150);
        this.robotFrame.setVisible(true);
    }

    @Override
    public void beforeStart() {
        pos = client.requestInterfacePosition2D(0, PlayerConstants.PLAYER_OPEN_MODE);
        ranger = client.requestInterfaceRanger(0, PlayerConstants.PLAYER_OPEN_MODE);
        //cropper = client.requestInterfaceFiducial(0, PlayerConstants.PLAYER_OPEN_MODE);
        //blob = client.requestInterfaceBlobfinder(0, PlayerConstants.PLAYER_OPEN_MODE);

        brains = new RobotMowerBrains();
    }

    @Override
    public void process() throws ProcessingException {
        if (ranger.isDataReady()) {
            brains.getInRangerSignal().setRanges360(ranger.getData().getRanges());

            this.robotFrame.setRangesSignal(brains.getInRangerSignal());

        }

        pos.setSpeed(brains.getOutPositionSignal().getSpeed(),
                brains.getOutPositionSignal().getCourse());

        brains.tick();
    }
}
