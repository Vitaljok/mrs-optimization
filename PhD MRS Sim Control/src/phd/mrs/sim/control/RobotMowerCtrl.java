/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phd.mrs.sim.control;

import phd.mrs.sim.control.util.ProcessingException;

/**
 *
 * @author Vitaljok
 */
public class RobotMowerCtrl extends AbstractPlayerCtrl {

    public RobotMowerCtrl(String host, Integer port) {
        super(host, port, 50);
    }
    
    @Override
    public void process() throws ProcessingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
