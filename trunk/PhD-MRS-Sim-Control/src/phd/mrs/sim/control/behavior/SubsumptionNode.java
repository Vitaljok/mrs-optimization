/*
 * Licensed under the Academic Free License version 3.0
 */
package phd.mrs.sim.control.behavior;

import phd.mrs.sim.control.behavior.structure.Signal;

/**
 *
 * @author Vitaljok
 */
public class SubsumptionNode extends CachedBehavior implements Behavior {

    private Behavior slave;
    private Behavior master;

    public SubsumptionNode(Behavior slave, Behavior master) {
        this.slave = slave;
        this.master = master;
    }

    @Override
    protected Signal getOutputInternal(Long requestId) {
        if (master.getOutput(requestId) != null) {
            //System.out.println("Subsumption master: "+ master.getClass().getName());
            return master.getOutput(requestId);
        } else {
            
            //System.out.println("Subsumption slave: "+ slave.getClass().getName());
            return slave.getOutput(requestId);
        }
        
    }
}
