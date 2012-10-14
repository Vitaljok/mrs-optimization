/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phd.mrs.sim.control;

import phd.mrs.sim.control.util.ProcessingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaclient3.PlayerClient;

/**
 *
 * @author Vitaljok
 */
public abstract class AbstractPlayerCtrl extends Thread {

    protected PlayerClient client;
    private Integer ctrlLoopDelay;
    private Boolean stopSignal = false;

    public AbstractPlayerCtrl(String host, Integer port) {
        super();
        client = new PlayerClient(host, port);
        ctrlLoopDelay = 50;
    }

    public AbstractPlayerCtrl(String host, Integer port, Integer ctrlLoopDelay) {
        this(host, port);
        this.ctrlLoopDelay = ctrlLoopDelay;
    }

    /**
     * Handles processing thread loop.
     */
    @Override
    public final void run() {
        beforeStart();

        client.runThreaded(-1, -1);

        while (!stopSignal) {
            try {
                process();
                Thread.sleep(ctrlLoopDelay);
            } catch (ProcessingException ex) {
                Logger.getLogger(AbstractPlayerCtrl.class.getName()).log(Level.SEVERE, "Error in processing loop!", ex);
                Thread.currentThread().interrupt();
            } catch (InterruptedException ex) {
                Logger.getLogger(AbstractPlayerCtrl.class.getName()).log(Level.SEVERE, "General error in processing loop!", ex);
                Thread.currentThread().interrupt();
            }
        }
        
        afterEnd();
    }

    /**
     * Executed after ending main control loop. 
     */
    public void afterEnd() {
    }
    
    /**
     * Executed before starting main control loop. 
     */
    public void beforeStart() {
    }

    public void sendStopSignal() {
            this.stopSignal = true;
    }
    /**
     * Main processing loop 
     */
    abstract public void process() throws ProcessingException;
}
