/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ik.solver;

/**
 *
 * @author Vitaljok
 */
public class IKSolver {

    Arm2d arm;
    Vector3D<Double> target;

    public Arm2d getArm() {
        return arm;
    }

    public void setArm(Arm2d arm) {
        this.arm = arm;
    }

    public Vector3D<Double> getTarget() {
        return target;
    }

    public void setTarget(Vector3D<Double> target) {
        this.target = target;
    }

    public boolean isSolved() {

        Vector3D<Double> toTarget = target.sub(arm.getTip());
        return toTarget.lenght() < 0.25;
    }

    public void step() {

        Vector3D axis = new Vector3D(0d, 0d, 1d);

        Vector3D<Double> tip = arm.getTip();
        Vector3D<Double> toTarget = this.target.sub(tip);

        for (Joint2d joint : arm.getJoints()) {
            Vector3D<Double> toTip = tip.sub(joint.getBasePose());
            Vector3D<Double> movement = toTip.cross(axis);
            double gradient = movement.dot(toTarget);

            joint.auxVector = movement.mult(gradient / 100);
            joint.auxValue = gradient;

            joint.changeAlfa(-gradient / 10);
        }
    }
}
