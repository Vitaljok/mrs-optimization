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

        //        WorldCtrl world = new WorldCtrl("192.168.56.101", 6650);          
        //        world.start();
        //        
        //        RobotWanderCtrl robot = new RobotWanderCtrl("192.168.56.101", 6665, world);
        //        robot.start();
        
        RobotMowerCtrl robot1 = new RobotMowerCtrl("192.168.56.101", 6665);
        //RobotMowerCtrl robot2 = new RobotMowerCtrl("192.168.56.101", 6666);
        //RobotMowerCtrl robot3 = new RobotMowerCtrl("192.168.56.101", 6667);
        robot1.start();
        //robot2.start();
        //robot3.start();

    }
}
