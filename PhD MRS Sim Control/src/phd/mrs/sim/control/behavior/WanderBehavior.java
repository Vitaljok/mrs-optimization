/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phd.mrs.sim.control.behavior;

import java.util.Random;
import phd.mrs.sim.control.behavior.structure.BasicSignal;
import phd.mrs.sim.control.behavior.structure.PositionSignalValue;
import phd.mrs.sim.control.behavior.structure.Signal;
import phd.mrs.sim.control.behavior.util.BehaviorInvalidInputSignalException;

/**
 *
 * @author Vitaljok
 */
public class WanderBehavior extends CachedBehavior implements Behavior {

    private Random random;
    private Double course = 0d;
    private Double speed;
    private Double maxCourse;
    private Double courseDelta;

    public WanderBehavior(Double speed, Double maxCourse, Double courseDelta) {
        this.speed = speed;
        this.maxCourse = maxCourse;
        this.courseDelta = courseDelta;
        this.random = new Random();
    }

    @Override
    protected Signal getOutputInternal(Long requestId) throws BehaviorInvalidInputSignalException {

        course += courseDelta * (random.nextDouble() * 2 - 1);
        course = Math.max(course, -maxCourse);
        course = Math.min(course, maxCourse);

        return new BasicSignal(Boolean.TRUE, new PositionSignalValue(speed, course));
    }
}
