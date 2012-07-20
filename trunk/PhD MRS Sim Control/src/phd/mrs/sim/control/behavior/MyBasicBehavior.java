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
public class MyBasicBehavior extends CachedBehavior implements Behavior {

    private Behavior input;

    public MyBasicBehavior(Behavior input) {
        this.input = input;
    }
    
    @Override
    protected Signal getOutputInternal(Long requestId) throws BehaviorInvalidInputSignalException {
        
        //this.input.getOutput(requestId);
        
        return input.getOutput(requestId);
    }
    
}
