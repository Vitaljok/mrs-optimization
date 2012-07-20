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
public class SubsumptionBehavior extends CachedBehavior implements Behavior {

    private Behavior slave;
    private Behavior master;

    public SubsumptionBehavior(Behavior slave, Behavior master) {
        this.slave = slave;
        this.master = master;
    }

    @Override
    protected Signal getOutputInternal(Long requestId) throws BehaviorInvalidInputSignalException {
         if (master.getOutput(requestId).isActive()) {
            return master.getOutput(requestId);
        } else {
            return slave.getOutput(requestId);
        }
    }
}
