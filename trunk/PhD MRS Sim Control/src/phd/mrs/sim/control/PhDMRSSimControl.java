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
        
        WorldCtrl world = new WorldCtrl("192.168.56.101", 6650);          
        world.start();
        
        RobotWander robot = new RobotWander("192.168.56.101", 6665, world);
        robot.start();
        
//        while (true) {
//            try {
//                
//                //RobotWander robot = new RobotWander("192.168.56.101", 6665);
//                RobotGraph3d robot = new RobotGraph3d("192.168.56.101", 6666);
//
//                robot.start();
//                
//                while (robot.isAlive()) {
//                    Thread.sleep(2000);
//                    System.out.println("Working...");
//                }
//                
//            } catch (Exception ex) {
//                System.out.println("Waiting...");
//                Thread.sleep(2000);
//            }
//        }
    }
}
