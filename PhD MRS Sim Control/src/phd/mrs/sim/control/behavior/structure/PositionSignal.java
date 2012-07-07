/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phd.mrs.sim.control.behavior.structure;

/**
 *
 * @author Vitaljok
 */
public class PositionSignal extends BehaviorSignal {
    private Double speed;
    private Double course;

    public PositionSignal(Double speed, Double course) {
        this.speed = speed;
        this.course = course;
    }
    
    public Double getCourse() {
        return course;
    }

    public void setCourse(Double course) {
        this.course = course;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }
}
