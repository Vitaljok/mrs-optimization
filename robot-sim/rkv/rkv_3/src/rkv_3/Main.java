/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rkv_3;

/**
 *
 * @author vitaljok
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Thread(new Robot("localhost", 6665)).start();
    }

}
