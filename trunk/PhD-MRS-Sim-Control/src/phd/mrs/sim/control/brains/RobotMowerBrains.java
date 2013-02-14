/*
 * Licensed under the Academic Free License version 3.0
 */
package phd.mrs.sim.control.brains;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;
import javaclient3.structures.blobfinder.PlayerBlobfinderData;
import javaclient3.structures.fiducial.PlayerFiducialData;
import phd.mrs.sim.control.behavior.AvoidObstaclesBehavior;
import phd.mrs.sim.control.behavior.CreateStrawBehavior;
import phd.mrs.sim.control.behavior.CruiseBehavior;
import phd.mrs.sim.control.behavior.DummyBehavior;
import phd.mrs.sim.control.behavior.FollowBlobBehavior;
import phd.mrs.sim.control.behavior.InputBypassNode;
import phd.mrs.sim.control.behavior.MowGrassBehavior;
import phd.mrs.sim.control.behavior.SubsumptionNode;
import phd.mrs.sim.control.behavior.TurnAroundBehavior;
import phd.mrs.sim.control.behavior.WanderBehavior;
import phd.mrs.sim.control.behavior.structure.GenericSignal;
import phd.mrs.sim.control.behavior.structure.PositionSignal;
import phd.mrs.sim.control.behavior.structure.Ranger8Signal;
import phd.mrs.sim.control.behavior.structure.WorldControlSignal;

/**
 * Robot brains:
 * - avoids obstacles
 * - wanders to find grass
 * - moves towards grass to trim
 * - sends commands to world controller
 * @author Vitaljok
 */
public class RobotMowerBrains {

    private Long tick = 0l;
    // input nodes
    InputBypassNode<Ranger8Signal> inRanger;
    InputBypassNode<GenericSignal<PlayerFiducialData>> inCropper;
    InputBypassNode<GenericSignal<PlayerBlobfinderData>> inCamera;
    InputBypassNode<GenericSignal<Set>> inLoad;
    // output nodes
    DummyBehavior<PositionSignal> outPosition;
    DummyBehavior<WorldControlSignal> outGrassMowWorldControl;
    DummyBehavior<WorldControlSignal> outStrawCreateWorldControl;
    // behaviors
    WanderBehavior behWander;
    AvoidObstaclesBehavior behAvoid;
    FollowBlobBehavior behFollowGrass;
    MowGrassBehavior behMowGrass;
    CreateStrawBehavior behCreateStraw;

    public RobotMowerBrains() {

        // input nodes
        inRanger = new InputBypassNode<Ranger8Signal>();
        inRanger.setBypassSignal(new Ranger8Signal());

        inCamera = new InputBypassNode<GenericSignal<PlayerBlobfinderData>>();
        inCamera.setBypassSignal(new GenericSignal<PlayerBlobfinderData>(null));

        inCropper = new InputBypassNode<GenericSignal<PlayerFiducialData>>();
        inCropper.setBypassSignal(new GenericSignal<PlayerFiducialData>(null));

        inLoad = new InputBypassNode<GenericSignal<Set>>();
        inLoad.setBypassSignal(new GenericSignal<Set>(new HashSet()));

        // default wander
        behWander = new WanderBehavior(4d, 1d, 0.3);
        
        // avoid obstacles
        behAvoid = new AvoidObstaclesBehavior(2d, 1.5d, 1.0d, 1d);
        behAvoid.setRangerInput(inRanger);

        // follow grass
        behFollowGrass = new FollowBlobBehavior(2.0, 1.5, Color.GREEN);
        behFollowGrass.setInCamera(inCamera);

        // create straw
        behCreateStraw = new CreateStrawBehavior(20);
        behCreateStraw.setInLoad(inLoad);

        // world control
        behMowGrass = new MowGrassBehavior();
        behMowGrass.setInCropper(inCropper);
        behMowGrass.setLoadSignal(inLoad.getBypassSignal());


        // output
        outPosition = new DummyBehavior<PositionSignal>();
        outGrassMowWorldControl = new DummyBehavior<WorldControlSignal>();
        outStrawCreateWorldControl = new DummyBehavior<WorldControlSignal>();

        
        
        // behaviour network
        SubsumptionNode sub1 = new SubsumptionNode(behWander, behFollowGrass);
        SubsumptionNode sub2 = new SubsumptionNode(sub1, behAvoid);
        
//        // simplest example 
//        CruiseBehavior behCruise = new CruiseBehavior(3d);
//        TurnAroundBehavior behTurn = new TurnAroundBehavior(4d, 2d, 3.14);
//        behTurn.setRangerInput(inRanger);
//              
//        SubsumptionNode sub = new SubsumptionNode(behCruise, behTurn);                
        outPosition.setInput(sub2);

        outGrassMowWorldControl.setInput(behMowGrass);
        outStrawCreateWorldControl.setInput(behCreateStraw);
    }

    public Ranger8Signal getInRangerSignal() {
        return inRanger.getBypassSignal();
    }

    public GenericSignal<PlayerBlobfinderData> getInCameraSignal() {
        return inCamera.getBypassSignal();
    }

    public GenericSignal<PlayerFiducialData> getInCropper() {
        return inCropper.getBypassSignal();
    }

    public PositionSignal getOutPositionSignal() {
        PositionSignal res = outPosition.getOutput(tick);
        //System.out.println(res.getCourse()+"\t"+res.getSpeed());
        return res;
    }

    public WorldControlSignal getOutGrassMowWorldControlSignal() {
        return outGrassMowWorldControl.getOutput(tick);
    }

    public WorldControlSignal getOutStrawCreateWorldControlSignal() {
        return outStrawCreateWorldControl.getOutput(tick);
    }

    public void tick() {
        tick++;
    }
}
