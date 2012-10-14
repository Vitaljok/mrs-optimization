/*
 * Licensed under the Academic Free License version 3.0
 */
package phd.mrs.sim.control.behavior;

import java.util.Set;
import phd.mrs.sim.control.behavior.structure.GenericSignal;
import phd.mrs.sim.control.behavior.structure.WorldControlSignal;

/**
 *
 * @author Vitaljok
 */
public class CreateStrawBehavior extends CachedBehavior<WorldControlSignal> implements Behavior {

    Integer maxLoad;
    
    InputBypassNode<GenericSignal<Set>> inLoad;

    public InputBypassNode<GenericSignal<Set>> getInLoad() {
        return inLoad;
    }

    public void setInLoad(InputBypassNode<GenericSignal<Set>> inLoad) {
        this.inLoad = inLoad;
    }        
    
    public CreateStrawBehavior(Integer maxLoad) {
        this.output = new WorldControlSignal(WorldControlSignal.Command.placeStraw);
        this.maxLoad = maxLoad;
    }

    @Override
    protected WorldControlSignal getOutputInternal(Long requestId) {
        if (inLoad.getBypassSignal().getValue().size() >= maxLoad ) {
            inLoad.getBypassSignal().getValue().clear();
            //
            
            return this.output;
        } 
        return null;
    }
}
