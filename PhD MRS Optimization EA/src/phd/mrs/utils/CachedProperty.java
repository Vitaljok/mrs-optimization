/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phd.mrs.utils;

/**
 *
 * @author Vitaljok
 */
public abstract class CachedProperty<T> {

    T value;

    protected abstract T calculateValue();

    public T getValue() {
        if (this.value == null) {
            this.value = calculateValue();
        }
        return value;
    }
}
