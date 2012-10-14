/*
 * Licensed under the Academic Free License version 3.0
 */
package phd.mrs.sim.control.behavior;

import java.awt.Color;
import javaclient3.structures.blobfinder.PlayerBlobfinderBlob;
import javaclient3.structures.blobfinder.PlayerBlobfinderData;
import phd.mrs.sim.control.behavior.structure.GenericSignal;
import phd.mrs.sim.control.behavior.structure.PositionSignal;

/**
 *
 * @author Vitaljok
 */
public class FollowBlobBehavior extends CachedBehavior<PositionSignal> implements Behavior {

    Double maxSpeed;
    Double maxCourse;
    Color color;
    private InputBypassNode<GenericSignal<PlayerBlobfinderData>> inCamera;

    public InputBypassNode<GenericSignal<PlayerBlobfinderData>> getInCamera() {
        return inCamera;
    }

    public void setInCamera(InputBypassNode<GenericSignal<PlayerBlobfinderData>> inCamera) {
        this.inCamera = inCamera;
    }

    public FollowBlobBehavior(Double maxSpeed, Double maxCourse, Color color) {
        this.maxSpeed = maxSpeed;
        this.maxCourse = maxCourse;
        this.output = new PositionSignal(0d, 0d);
        this.color = color;
    }

    @Override
    protected PositionSignal getOutputInternal(Long requestId) {

        PlayerBlobfinderData data = inCamera.getOutput(requestId).getValue();

        if (data == null || data.getBlobs_count() == 0) {
            return null;
        }

        float minDist = Integer.MAX_VALUE;
        PlayerBlobfinderBlob minBlob = null;

        for (int i = 0; i < data.getBlobs().length; i++) {
            
            //65280                        

            if (data.getBlobs()[i].getColor() == 65280
                    && minDist > data.getBlobs()[i].getRange()) {
                minBlob = data.getBlobs()[i];
                minDist = minBlob.getRange();
            }
        }

        if (minBlob == null) {
            return null;
        }

        double course = (minBlob.getX() / (data.getWidth() / 2.0) - 1) * maxCourse;
        course = Math.max(course, -maxCourse);
        course = Math.min(course, maxCourse);

        this.output.setCourse(-course);
        this.output.setSpeed(this.maxSpeed); // cropping maxSpeed

        return this.output;
    }
}
