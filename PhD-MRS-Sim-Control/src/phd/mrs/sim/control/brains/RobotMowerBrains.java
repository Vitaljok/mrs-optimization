/*
 * Licensed under the Academic Free License version 3.0
 */
package phd.mrs.sim.control.brains;

import phd.mrs.sim.control.behavior.AvoidObstaclesBehavior;
import phd.mrs.sim.control.behavior.CruiseBehavior;
import phd.mrs.sim.control.behavior.DummyBehavior;
import phd.mrs.sim.control.behavior.FollowObstaclesBehavior;
import phd.mrs.sim.control.behavior.InputBypassNode;
import phd.mrs.sim.control.behavior.SubsumptionNode;
import phd.mrs.sim.control.behavior.WanderBehavior;
import phd.mrs.sim.control.behavior.structure.PositionSignal;
import phd.mrs.sim.control.behavior.structure.Ranger8Signal;

/**
 *
 * @author Vitaljok
 */
public class RobotMowerBrains {

    Long tick = 0l;
    InputBypassNode<Ranger8Signal> inRanger;
    WanderBehavior behWander;
    CruiseBehavior behCruise;
//    AvoidObstaclesBehavior behAvoid;
    DummyBehavior<PositionSignal> outPosition;
    FollowObstaclesBehavior behFollow;

    public RobotMowerBrains() {
        inRanger = new InputBypassNode<Ranger8Signal>();
        inRanger.setBypassSignal(new Ranger8Signal());

        outPosition = new DummyBehavior();

        //behWander = new WanderBehavior(1.5d, 1d, 0.3);
        behCruise = new CruiseBehavior(1.0d);

//        behAvoid = new AvoidObstaclesBehavior(2d, 1.5d, 0.7d, 1d);        
//        behAvoid.setRangerInput(inRanger);
        
        behFollow = new FollowObstaclesBehavior();
        behFollow.setRangerInput(inRanger);

        outPosition.setInput(new SubsumptionNode(behCruise, behFollow));
    }

    public Ranger8Signal getInRangerSignal() {
        return inRanger.getBypassSignal();
    }

    public PositionSignal getOutPositionSignal() {
        PositionSignal res = outPosition.getOutput(tick);
        //System.out.println(res.getCourse()+"\t"+res.getSpeed());
        return res;
    }

    public void tick() {
        tick++;
    }
}
