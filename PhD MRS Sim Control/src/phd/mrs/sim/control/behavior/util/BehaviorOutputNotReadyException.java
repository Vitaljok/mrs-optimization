/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phd.mrs.sim.control.behavior.util;

/**
 *
 * @author Vitaljok
 */
public class BehaviorOutputNotReadyException extends Exception {

    public BehaviorOutputNotReadyException(String message, Throwable cause) {
        super(message, cause);
    }

    public BehaviorOutputNotReadyException(String message) {
        super(message);
    }
    
}
