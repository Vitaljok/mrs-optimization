/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rkv_2;

/**
 *
 * @author vitaljok
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Thread(new DefaultRobot("localhost", 6650)).start();
        
        for (int i = 0; i < 7; i++) {
            new Thread(new FollowerRobot("localhost", 6665+i)).start();

        }
    }

}
