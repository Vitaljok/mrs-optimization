/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phd.mrs.sim.control.util;

import javaclient3.structures.PlayerPose2d;

/**
 *
 * @author Vitaljok
 */
public class StageObject {

    String name;
    PlayerPose2d pose;

    public StageObject(String name, Double x, Double y, Double a) {
        this.name = name;
        this.pose = new PlayerPose2d(x, y, a);
    }

    public StageObject(String name, PlayerPose2d pose) {
        this.name = name;
        this.pose = pose;
    }

    public String getName() {
        return name;
    }

    public PlayerPose2d getPose() {
        return pose;
    }
}
