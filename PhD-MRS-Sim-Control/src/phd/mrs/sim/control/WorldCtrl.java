/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phd.mrs.sim.control;

import java.util.HashSet;
import java.util.Set;
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
    private BlockingQueue<StageObject> strawQueue;
    private Integer strawCounter = 1;
    private BlockingQueue<String> strawPool;
    private Set<String> grass = new HashSet<String>();
    private long start;

    @Override
    public void beforeStart() {
        start = System.currentTimeMillis();
    }
    
    

    public WorldCtrl(String host, Integer port) {
        super(host, port, 150);
        simulation = client.requestInterfaceSimulation(0, PlayerConstants.PLAYER_OPEN_MODE);
        grassQueue = new LinkedBlockingQueue<String>();
        strawQueue = new LinkedBlockingQueue<StageObject>();
        strawPool = new LinkedBlockingQueue<String>(100);

        for (int i = 0; i < 100; i++) {
            try {
                strawPool.put("straw" + (i + 1));
            } catch (InterruptedException ex) {
                Logger.getLogger(WorldCtrl.class.getName()).log(Level.SEVERE, "Error creating straw pool", ex);
            }
        }

    }

    public void addGrassItemToQueue(String item) {
        try {
            grassQueue.put(item);
            grass.add(item);
            if (grass.size() % 100 == 0) {
                System.out.println("Mowed: " + grass.size() + " @ "+Math.round((System.currentTimeMillis() - start) / 1000.0));
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(WorldCtrl.class.getName()).log(Level.SEVERE, "Error adding grass item [" + item + "] to queue.", ex);
        }
    }

    public void addStrawItemToQueue(PlayerPose2d pose) {
        try {
            strawQueue.put(new StageObject(strawPool.poll(), pose));
        } catch (InterruptedException ex) {
            Logger.getLogger(WorldCtrl.class.getName()).log(Level.SEVERE, "Error adding straw item [straw" + strawCounter + "] to queue.", ex);
        }
    }

    private void mowGrass() {
        while (!grassQueue.isEmpty()) {
            simulation.set2DPose(grassQueue.poll(), new PlayerPose2d(-50, -0, 0));
        }
    }

    private void showStraw() {
        while (!strawQueue.isEmpty()) {
            StageObject obj = strawQueue.poll();
            simulation.set2DPose(obj.getName(), obj.getPose());


        }
    }

    @Override
    public void process() throws ProcessingException {
        mowGrass();
        showStraw();
    }
}
