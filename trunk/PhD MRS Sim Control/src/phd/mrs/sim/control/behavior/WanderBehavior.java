/*
 * Licensed under the Academic Free License version 3.0
 */
package phd.mrs.sim.control.behavior;

import java.util.Random;
import phd.mrs.sim.control.behavior.structure.PositionSignal;

/**
 *
 * @author Vitaljok
 */
public class WanderBehavior extends CachedBehavior<PositionSignal> implements Behavior {

    private Random random;
    private Double course = 0d;
    private Double maxCourse;
    private Double courseDelta;

    public WanderBehavior(Double speed, Double maxCourse, Double courseDelta) {
        this.maxCourse = maxCourse;
        this.courseDelta = courseDelta;
        this.random = new Random();

        this.output = new PositionSignal(speed, 0d);
    }

    @Override
    protected PositionSignal getOutputInternal(Long requestId) {

        course += courseDelta * (random.nextDouble() * 2 - 1);
        course = Math.max(course, -maxCourse);
        course = Math.min(course, maxCourse);

        this.output.setCourse(course);

        return this.output;
    }
}
