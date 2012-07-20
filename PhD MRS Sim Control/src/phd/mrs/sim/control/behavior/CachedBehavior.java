/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phd.mrs.sim.control.behavior;

import phd.mrs.sim.control.behavior.structure.Signal;
import phd.mrs.sim.control.behavior.util.BehaviorInvalidInputSignalException;

/**
 *
 * @author Vitaljok
 */
public abstract class CachedBehavior implements Behavior {

    private Signal cachedOutput;
    private Long previousRequestId = -1l;

    @Override
    public Signal getOutput(Long requestId) throws BehaviorInvalidInputSignalException {
        if (requestId != this.previousRequestId) {
            cachedOutput = null;
        }

        if (cachedOutput == null) {
            System.out.println(this+" - calculated output");
            cachedOutput = getOutputInternal(requestId);
            previousRequestId = requestId;
        }
        System.out.println(this+" - output");
        return cachedOutput;
    }

    protected abstract Signal getOutputInternal(Long requestId) throws BehaviorInvalidInputSignalException;
}
