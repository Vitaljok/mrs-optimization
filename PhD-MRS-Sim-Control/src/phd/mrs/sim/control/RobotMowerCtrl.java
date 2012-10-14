/*
 * Licensed under the Academic Free License version 3.0
 */
package phd.mrs.sim.control;

import javaclient3.BlobfinderInterface;
import javaclient3.FiducialInterface;
import javaclient3.Position2DInterface;
import javaclient3.RangerInterface;
import javaclient3.structures.PlayerConstants;
import javaclient3.structures.PlayerPose2d;
import phd.mrs.sim.control.brains.RobotMowerBrains;
import phd.mrs.sim.control.debug.RobotMowerFrame;
import phd.mrs.sim.control.util.ProcessingException;

/**
 *
 * @author Vitaljok
 */
public class RobotMowerCtrl extends AbstractPlayerCtrl {

    private Position2DInterface pos;
    private RangerInterface ranger;
    private FiducialInterface cropper;
    private BlobfinderInterface blob;
    private RobotMowerBrains brains;
    private RobotMowerFrame robotFrame;
    private WorldCtrl world;

    public RobotMowerCtrl(String host, Integer port, WorldCtrl world) {
        super(host, port, 50);

        this.robotFrame = new RobotMowerFrame("Robot @" + port);
        this.robotFrame.setLocation(10, (port % 10) * 150);
        this.robotFrame.setVisible(true);
        this.world = world;
    }

    @Override
    public void beforeStart() {
        pos = client.requestInterfacePosition2D(0, PlayerConstants.PLAYER_OPEN_MODE);
        ranger = client.requestInterfaceRanger(0, PlayerConstants.PLAYER_OPEN_MODE);
        cropper = client.requestInterfaceFiducial(0, PlayerConstants.PLAYER_OPEN_MODE);
        blob = client.requestInterfaceBlobfinder(0, PlayerConstants.PLAYER_OPEN_MODE);

        brains = new RobotMowerBrains();
    }

    @Override
    public void process() throws ProcessingException {

        if (ranger.isDataReady()) {
            brains.getInRangerSignal().setRanges360(ranger.getData().getRanges());

            this.robotFrame.setRangesSignal(brains.getInRangerSignal());
        }

        if (cropper.isDataReady()) {
            brains.getInCropper().setValue(cropper.getData());
        }

        if (blob.isDataReady()) {
            this.brains.getInCameraSignal().setValue(blob.getData());
        }

        pos.setSpeed(brains.getOutPositionSignal().getSpeed(),
                brains.getOutPositionSignal().getCourse());

        this.robotFrame.setPosition(brains.getOutPositionSignal());

        if (brains.getOutGrassMowWorldControlSignal() != null) {
            for (String item : brains.getOutGrassMowWorldControlSignal().getObjects()) {
                world.addGrassItemToQueue(item);
            }
        }

        if (brains.getOutStrawCreateWorldControlSignal() != null) {
            
            double dx = Math.cos(this.pos.getYaw());
            double dy = Math.sin(this.pos.getYaw());
            
            world.addStrawItemToQueue(new PlayerPose2d(this.pos.getX() - dx, this.pos.getY() - dy, this.pos.getYaw()));
        }

        brains.tick();

    }
}
