/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phd.mrs.sim.control.behavior.structure;

/**
 *
 * @author Vitaljok
 */
public class GenericSignalValue<T> extends SignalValue {
    private T value;

    public GenericSignalValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
