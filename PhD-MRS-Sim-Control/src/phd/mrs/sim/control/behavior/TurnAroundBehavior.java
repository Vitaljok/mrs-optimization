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
public class TurnAroundBehavior extends CachedBehavior<PositionSignal> implements Behavior {

    private InputBypassNode<Ranger8Signal> rangerInput;
    private Double frontDist;
    private Double speed;
    private Double course;
    private Byte active = 0;

    public TurnAroundBehavior(Double frontDist, Double speed, Double course) {
        this.frontDist = frontDist;
        this.speed = speed;
        this.course = course;

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



        if (active <= 0 && rangerSignal.getFront() < frontDist) {
            this.active = 20;            
        }

        if (active > 0) {
            output.setCourse(course);
            output.setSpeed(speed);
            active--;            
            return output;

        }

        return null;
    }
}
