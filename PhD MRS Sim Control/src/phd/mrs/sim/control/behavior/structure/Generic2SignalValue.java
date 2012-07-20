/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phd.mrs.sim.control.behavior.structure;

/**
 *
 * @author Vitaljok
 */
public class Generic2SignalValue<T, E> extends SignalValue {
    private T value1;
    private E value2;

    public Generic2SignalValue(T value1, E value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public T getValue1() {
        return value1;
    }

    public void setValue1(T value1) {
        this.value1 = value1;
    }

    public E getValue2() {
        return value2;
    }

    public void setValue2(E value2) {
        this.value2 = value2;
    }
}
