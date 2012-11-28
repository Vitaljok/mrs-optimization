/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package phd.combinations;

/**
 *
 * @author Vitaljok
 */
public class FunctionalUnit {
    private Character value;

    public FunctionalUnit(Character value) {
        this.value = value;
    }

    public FunctionalUnit(Integer num) {
        this.value = Character.toChars(64+num)[0];
    }

    public Character getValue() {
        return value;
    }

    public void setValue(Character value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FunctionalUnit other = (FunctionalUnit) obj;
        if (this.value != other.value && (this.value == null || !this.value.equals(other.value))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.value != null ? this.value.hashCode() : 0);
        return hash;
    }


}
