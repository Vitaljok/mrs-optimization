/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phd.mrs.sim.control.behavior;

import phd.mrs.sim.control.behavior.structure.BasicSignal;
import phd.mrs.sim.control.behavior.structure.GenericSignalValue;
import phd.mrs.sim.control.behavior.structure.Signal;
import phd.mrs.sim.control.behavior.util.BehaviorInvalidInputSignalException;

/**
 *
 * @author Vitaljok
 */
public class MyInputNode extends CachedBehavior implements Behavior  {

    Integer aaa;

    public MyInputNode(Integer aaa) {
        this.aaa = aaa;
    }
    
    @Override
    protected Signal getOutputInternal(Long requestId) throws BehaviorInvalidInputSignalException {
        return new BasicSignal(Boolean.TRUE, new GenericSignalValue<Integer>(aaa));
    }
}
