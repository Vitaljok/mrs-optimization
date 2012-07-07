/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phd.mrs.sim.control.behavior.util;

/**
 *
 * @author Vitaljok
 */
public class BehaviorOutputNotReady extends Exception {

    public BehaviorOutputNotReady(String message, Throwable cause) {
        super(message, cause);
    }

    public BehaviorOutputNotReady(String message) {
        super(message);
    }
    
}
