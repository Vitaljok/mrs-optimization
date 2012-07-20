/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phd.mrs.sim.control.behavior;

import phd.mrs.sim.control.behavior.structure.GenericSignalValue;
import phd.mrs.sim.control.behavior.util.BehaviorInvalidInputSignalException;

/**
 *
 * @author Vitaljok
 */
public class BehaviorTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws BehaviorInvalidInputSignalException {
        Behavior in1 = new MyInputNode(123);
        Behavior in2 = new MyInputNode(234);
        Behavior beh1 = new MyBasicBehavior(in1);
        Behavior beh2 = new MyBasicBehavior(in2);
        Behavior sub = new SubsumptionBehavior(beh2, beh1);
        Behavior beh3 = new MyBasicBehavior(sub);

        System.out.println("Result: " + ((GenericSignalValue<Integer>)(beh3.getOutput(1l).getValue())).getValue());
        System.out.println("Result: " + ((GenericSignalValue<Integer>)(beh3.getOutput(1l).getValue())).getValue());

    }
}
