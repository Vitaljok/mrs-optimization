/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phd.mrs.sim.control;

/**
 *
 * @author Vitaljok
 */
public class PhDMRSSimControl {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {

        if (args.length == 0) {
            System.err.println("Please, specify simulation host!");
            System.exit(1);
        }
        
        WorldCtrl world = new WorldCtrl(args[0], 6650);
        world.start();        

        RobotMowerCtrl robot1 = new RobotMowerCtrl(args[0], 6661, world);
        RobotMowerCtrl robot2 = new RobotMowerCtrl(args[0], 6662, world);
        RobotMowerCtrl robot3 = new RobotMowerCtrl(args[0], 6663, world);
        robot2.start();
        Thread.sleep(4000);
        robot1.start();
        Thread.sleep(4000);
        robot3.start();

    }
}
