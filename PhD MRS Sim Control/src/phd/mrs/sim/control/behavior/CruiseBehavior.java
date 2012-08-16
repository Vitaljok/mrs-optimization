/*
 * Licensed under the Academic Free License version 3.0
 */
package phd.mrs.sim.control.behavior;

import phd.mrs.sim.control.behavior.structure.PositionSignal;

/**
 *
 * @author Vitaljok
 */
public class CruiseBehavior extends CachedBehavior<PositionSignal> implements Behavior {

    public CruiseBehavior(Double speed) {
        this.output = new PositionSignal(speed, 0d);
    }

    @Override
    protected PositionSignal getOutputInternal(Long requestId) {
        return output;
    }
}
