/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simtestjava;

import javaclient3.PlayerClient;
import javaclient3.SimulationInterface;
import javaclient3.structures.PlayerConstants;
import javaclient3.structures.PlayerPose2d;
import javaclient3.structures.simulation.PlayerSimulationPose3dReq;

/**
 *
 * @author Vitaljok
 */
public class SimTestJava {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        PlayerClient client = new PlayerClient("192.168.56.101", 6650);
        SimulationInterface sim = client.requestInterfaceSimulation(0, PlayerConstants.PLAYER_OPEN_MODE);

        client.runThreaded(-1, -1);

        while (true) {
            sim.get3DPose("extra_long_object_name");

            if (sim.isPose3DReady()) {
                PlayerSimulationPose3dReq res = sim.getSimulationPose3D();
                System.out.println(String.valueOf(res.getName()) + " -> " + res.getPose() + "\t@" + res.getSimtime());
            } else {
                System.out.println("Waiting...");
            }
            
            sim.set2DPose("extra_long_object_name", new PlayerPose2d(-3, -2, 45));

            Thread.sleep(1000);
        }
    }
}
