/*
 * Licensed under the Academic Free License version 3.0
 */
package phd.mrs.sim.control.behavior;

import phd.mrs.sim.control.behavior.structure.PositionSignal;
import phd.mrs.sim.control.behavior.structure.Ranger8Signal;

/**
 *
 * @author Vitaljok
 */
public class AvoidObstaclesBehavior extends CachedBehavior<PositionSignal> implements Behavior {

    private InputBypassNode<Ranger8Signal> rangerInput;
    private Double sideDist;
    private Double frontDist;
    private Double avoidSpeed;
    private Double avoidCourse;

    public AvoidObstaclesBehavior(Double sideDist, Double frontDist, Double avoidSpeed, Double avoidCourse) {
        this.sideDist = sideDist;
        this.frontDist = frontDist;
        this.avoidSpeed = avoidSpeed;
        this.avoidCourse = avoidCourse;

        this.output = new PositionSignal(0d, 0d);
    }

    public InputBypassNode<Ranger8Signal> getRangerInput() {
        return rangerInput;
    }

    public void setRangerInput(InputBypassNode<Ranger8Signal> rangerInput) {
        this.rangerInput = rangerInput;
    }

    @Override
    protected PositionSignal getOutputInternal(Long requestId) {

        Ranger8Signal rangerSignal = rangerInput.getOutput(requestId);

        if (rangerSignal == null) {
            return null;
        }

        if (rangerSignal.getFront() < frontDist) {
            output.setSpeed(-avoidSpeed / 2d);

            if (rangerSignal.getFrontLeft() > rangerSignal.getFrontRight()) {
                output.setCourse(avoidCourse);
            } else {
                output.setCourse(-avoidCourse);
            }
            
            return output;
        }

        if (rangerSignal.getFrontLeft() < sideDist) {
            output.setCourse(-avoidCourse);
            output.setSpeed(avoidSpeed);
            return output;
        }

        if (rangerSignal.getFrontRight() < sideDist) {
            output.setCourse(avoidCourse);
            output.setSpeed(avoidSpeed);
            return output;
        }

        return null;
    }
}
