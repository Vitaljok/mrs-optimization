/*
 * Licensed under the Academic Free License version 3.0
 */
package phd.mrs.sim.control.behavior;

import phd.mrs.sim.control.Utils;
import phd.mrs.sim.control.behavior.structure.PositionSignal;
import phd.mrs.sim.control.behavior.structure.Ranger8Signal;

/**
 *
 * @author Vitaljok
 */
public class FollowObstaclesBehavior extends CachedBehavior<PositionSignal> implements Behavior {

    private InputBypassNode<Ranger8Signal> rangerInput;

    public FollowObstaclesBehavior() {
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

        if (rangerSignal.getFrontLeft() < 4
                || rangerSignal.getLeft() < 4
                || rangerSignal.getRearLeft() < 4
                || rangerSignal.getFront() < 4) {

            Double followDist = 2.0d;

            output.setSpeed(Utils.scaleLimit(rangerSignal.getFront(), 1d, 4d, 0d, 1d));
            Double turn1 = Utils.scaleLimit(rangerSignal.getLeft(), 0d, 2d, -0.25d, 0.25d);
            Double turn2 = Utils.scaleLimit(rangerSignal.getFrontLeft() - rangerSignal.getRearLeft(), -1d, +1d, -0.75d, 0.75d);
            Double turn3 = Utils.scaleLimit(rangerSignal.getFront(), 1d, 2d, -0.75d, 0.0d);

            output.setCourse(Utils.limit(turn1 + turn2 + turn3, -0.8, 0.8));

            //System.out.println("turn1 = " + turn1 + "\tturn2 = " + turn2 + "\tResult = " + Utils.limit(turn1 + turn2, -0.4, 0.4));


            return output;
        } else {
            return null;
        }
    }
}
