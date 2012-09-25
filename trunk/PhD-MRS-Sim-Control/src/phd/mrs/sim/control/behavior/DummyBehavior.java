/*
 * Licensed under the Academic Free License version 3.0
 */
package phd.mrs.sim.control.behavior;

import phd.mrs.sim.control.behavior.structure.Signal;

/**
 *
 * @author Vitaljok
 */
public class DummyBehavior<T extends Signal> extends CachedBehavior<T> implements Behavior {

    Behavior input;

    public Behavior getInput() {
        return input;
    }

    public void setInput(Behavior input) {
        this.input = input;
    }

    @Override
    protected T getOutputInternal(Long requestId) {
        return (T) input.getOutput(requestId);
    }
}
