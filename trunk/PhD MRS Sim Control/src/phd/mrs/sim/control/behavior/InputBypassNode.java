/*
 * Licensed under the Academic Free License version 3.0
 */
package phd.mrs.sim.control.behavior;

import phd.mrs.sim.control.behavior.structure.Signal;

/**
 *
 * @author Vitaljok
 */
public class InputBypassNode<T extends Signal> extends CachedBehavior<T> implements Behavior {

    public T getBypassSignal() {
        return output;
    }

    public void setBypassSignal(T bypassSignal) {
        this.output = bypassSignal;
    }

    @Override
    protected T getOutputInternal(Long requestId) {        
        return output;
    }
}
