/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phd.mrs.sim.generator;

import java.util.Locale;

/**
 *
 * @author Vitaljok
 */
public class SimObject {

    String typeName;
    Float poseX;
    Float poseY;
    Float size;

    public SimObject(String typeName, Float poseX, Float poseY, Float size) {
        this.typeName = typeName;
        this.poseX = poseX;
        this.poseY = poseY;
        this.size = size;
    }

    public String getCode() {
        return String.format(Locale.US, "%s ( pose [ %8.3f %8.3f 0 %8.3f ] )", typeName, poseX, poseY, 45f);
    }

    public Float getPoseX() {
        return poseX;
    }

    public Float getPoseY() {
        return poseY;
    }

    public Float getSize() {
        return size;
    }
}
