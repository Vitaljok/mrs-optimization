/*
 * Licensed under the Academic Free License version 3.0
 */
package phd.mrs.sim.control.behavior;

import phd.mrs.sim.control.behavior.structure.Signal;

/**
 *
 * @author Vitaljok
 */
public abstract class CachedBehavior<T extends Signal> implements Behavior {

    private T cachedOutput;
    private Long previousRequestId = -1l;
    protected T output;

    @Override
    public T getOutput(Long requestId) {
        
//        if (output == null)
//            throw new IllegalStateException("Initialize protected output variable in derived objects.");
        
        if (requestId != this.previousRequestId) {
            cachedOutput = null;
        }

        if (cachedOutput == null) {
            cachedOutput = getOutputInternal(requestId);
            previousRequestId = requestId;
        }
        return cachedOutput;
    }

    protected abstract T getOutputInternal(Long requestId);
}
