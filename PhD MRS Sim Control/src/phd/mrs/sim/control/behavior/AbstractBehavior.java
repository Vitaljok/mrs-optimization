/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phd.mrs.sim.control.behavior;

/**
 *
 * @author Vitaljok
 */
public abstract class AbstractBehavior implements Behavior {
    private Boolean active;

    @Override
    public Boolean getActive() {
        return active;
    }
}
