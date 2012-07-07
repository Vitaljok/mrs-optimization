/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phd.mrs.sim.control.behavior;

import phd.mrs.sim.control.behavior.util.BehaviorInputNotReady;
import phd.mrs.sim.control.behavior.util.BehaviorInvalidInputSignal;
import phd.mrs.sim.control.behavior.structure.BehaviorSignal;
import phd.mrs.sim.control.behavior.util.BehaviorOutputNotReady;

/**
 *
 * @author Vitaljok
 */
public interface Behavior {

    public Boolean getActive() throws BehaviorOutputNotReady;

    public BehaviorSignal getOutput() throws BehaviorOutputNotReady;

    public void Process() throws BehaviorInputNotReady, BehaviorInvalidInputSignal;
}
