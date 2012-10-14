/*
 * Licensed under the Academic Free License version 3.0
 */
package phd.mrs.sim.control.behavior;

import java.util.HashSet;
import java.util.Set;
import javaclient3.structures.fiducial.PlayerFiducialData;
import javaclient3.structures.fiducial.PlayerFiducialItem;
import phd.mrs.sim.control.behavior.structure.GenericSignal;
import phd.mrs.sim.control.behavior.structure.WorldControlSignal;

/**
 *
 * @author Vitaljok
 */
public class MowGrassBehavior extends CachedBehavior<WorldControlSignal> implements Behavior {

    InputBypassNode<GenericSignal<PlayerFiducialData>> inCropper;
    private GenericSignal<Set> loadSignal;

    public InputBypassNode<GenericSignal<PlayerFiducialData>> getInCropper() {
        return inCropper;
    }

    public void setInCropper(InputBypassNode<GenericSignal<PlayerFiducialData>> inCropper) {
        this.inCropper = inCropper;
    }

    public GenericSignal<Set> getLoadSignal() {
        return loadSignal;
    }

    public void setLoadSignal(GenericSignal<Set> loadSignal) {
        this.loadSignal = loadSignal;
    }

    public MowGrassBehavior() {

        this.output = new WorldControlSignal(WorldControlSignal.Command.mowGrass);
    }

    @Override
    protected WorldControlSignal getOutputInternal(Long requestId) {
        PlayerFiducialData data = this.inCropper.getBypassSignal().getValue();

        if (data == null || data.getFiducials_count() == 0) {
            return null;
        }

        this.output.getObjects().clear();

        Set<Integer> ids = new HashSet<Integer>();

        for (PlayerFiducialItem item : data.getFiducials()) {
            this.output.getObjects().add("grass" + item.getId());
            ids.add(item.getId());
        }

        if (loadSignal != null && !ids.isEmpty()) {
            loadSignal.getValue().addAll(ids);
        }

        return this.output;
    }
}
