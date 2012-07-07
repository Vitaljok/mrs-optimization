/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phd.mrs.sim.control.behavior.util;

/**
 *
 * @author Vitaljok
 */
public class BehaviorInputNotReady extends Exception {

    public BehaviorInputNotReady(String message, Throwable cause) {
        super(message, cause);
    }

    public BehaviorInputNotReady(String message) {
        super(message);
    }
    
}
