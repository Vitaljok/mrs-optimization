/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phd.mrs.sim.control.behavior.util;

/**
 *
 * @author Vitaljok
 */
public class BehaviorInputNotReadyException extends Exception {

    public BehaviorInputNotReadyException(String message, Throwable cause) {
        super(message, cause);
    }

    public BehaviorInputNotReadyException(String message) {
        super(message);
    }
    
}
