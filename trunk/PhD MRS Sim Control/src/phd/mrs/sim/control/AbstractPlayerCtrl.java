/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phd.mrs.sim.control;

import phd.mrs.sim.control.util.ProcessingException;
import java.util.List;
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
    public void run() {
        beforeStart();

        client.runThreaded(-1, -1);

        while (true) {
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
    }

    /**
     * Executed before starting main control loop. 
     */
    public void beforeStart() {
    }

    /**
     * Main processing loop 
     */
    abstract public void process() throws ProcessingException;
}
