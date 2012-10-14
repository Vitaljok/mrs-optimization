/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phd.mrs.sim.control.behavior.structure;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vitaljok
 */
public class WorldControlSignal extends Signal {
    public enum Command {
        mowGrass,
        placeStraw,
        collectStraw        
    }
    
    private Command cmd;
    private List<String> objects;

    public WorldControlSignal(Command cmd) {
        this.cmd = cmd;
        this.objects = new ArrayList<String>();
    }   
    
    public Command getCmd() {
        return cmd;
    }

    public void setCmd(Command cmd) {
        this.cmd = cmd;
    }

    public List<String> getObjects() {
        return objects;
    }      
}
