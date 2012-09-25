/*
 * Licensed under the Academic Free License version 3.0
 */
package phd.mrs.sim.control.behavior;

import phd.mrs.sim.control.behavior.structure.Signal;

/**
 *
 * @author Vitaljok
 */
public class InhibitionNode extends CachedBehavior implements Behavior {

    private Behavior slave;
    private Behavior master;

    public InhibitionNode(Behavior slave, Behavior master) {
        this.slave = slave;
        this.master = master;
    }

    @Override
    protected Signal getOutputInternal(Long requestId) {
         if (master.getOutput(requestId) != null) {
            return null;
        } else {
            return slave.getOutput(requestId);
        }
    }
}
