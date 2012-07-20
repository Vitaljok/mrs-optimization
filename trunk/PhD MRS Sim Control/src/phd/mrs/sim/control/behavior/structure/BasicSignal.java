/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phd.mrs.sim.control.behavior.structure;

/**
 *
 * @author Vitaljok
 */
public class BasicSignal implements Signal {
    
    private Boolean active;
    private SignalValue value;

    public BasicSignal(Boolean active, SignalValue value) {
        this.active = active;
        this.value = value;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setValue(SignalValue value) {
        this.value = value;
    }    

    @Override
    public Boolean isActive() {
        return active;
    }

    @Override
    public SignalValue getValue() {
        return value;
    }
    
}
