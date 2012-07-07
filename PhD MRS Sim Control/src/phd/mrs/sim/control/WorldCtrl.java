/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phd.mrs.sim.control;

import phd.mrs.sim.control.util.ProcessingException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaclient3.SimulationInterface;
import javaclient3.structures.PlayerConstants;
import javaclient3.structures.PlayerPose2d;
import phd.mrs.sim.control.util.StageObject;

/**
 *
 * @author Vitaljok
 */
public class WorldCtrl extends AbstractPlayerCtrl {

    private SimulationInterface simulation;
    private BlockingQueue<String> grassQueue;
    private BlockingQueue<StageObject> stackQueue;
    private Integer strawCounter = 1;

    public WorldCtrl(String host, Integer port) {
        super(host, port, 150);
        simulation = client.requestInterfaceSimulation(0, PlayerConstants.PLAYER_OPEN_MODE);
        grassQueue = new LinkedBlockingQueue<String>();
        stackQueue = new LinkedBlockingQueue<StageObject>();
    }

    public void addGrassItemToMow(String item) {
        try {
            grassQueue.put(item);
        } catch (InterruptedException ex) {
            Logger.getLogger(WorldCtrl.class.getName()).log(Level.SEVERE, "Error adding grass item [" + item + "] to queue.", ex);
        }
    }

    public void addStackItemToShow(PlayerPose2d pose) {
        try {
            stackQueue.put(new StageObject("stack" + strawCounter++, pose));
        } catch (InterruptedException ex) {
            Logger.getLogger(WorldCtrl.class.getName()).log(Level.SEVERE, "Error adding straw item [stack" + strawCounter + "] to queue.", ex);
        }
    }

    private void mowGrass() {
        while (!grassQueue.isEmpty()) {
            simulation.set2DPose(grassQueue.poll(), new PlayerPose2d(-100, -100, 0));
        }
    }

    private void showStack() {
        while (!stackQueue.isEmpty()) {
            StageObject obj = stackQueue.poll();
            simulation.set2DPose(obj.getName(), obj.getPose());
        }
    }

    @Override
    public void process() throws ProcessingException {

        mowGrass();
        showStack();
    }
}
