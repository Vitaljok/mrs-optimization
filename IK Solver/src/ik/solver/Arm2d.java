/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ik.solver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Vitaljok
 */
public class Arm2d {

    private Vector3D<Double> base;

    private final List<Joint2d> joints = new ArrayList<>();

    public List<Joint2d> getJoints() {
        return Collections.unmodifiableList(joints);
    }

    public void addJoint(Joint2d joint) {

        if (!joints.isEmpty()) {
            joint.setParentJoint(joints.get(joints.size() - 1));
        }

        joints.add(joint);
    }

    public Vector3D<Double> getTip() {
        if (joints.isEmpty()) {
            return null;
        } else {

            Vector3D tip = base.clone();

            for (Joint2d joint : joints) {
                tip = tip.add(joint.getVector());
            }

            return tip;
        }
    }

    public Arm2d() {
        //this.joints.add(new Joint2d(1f, 90f));
    }

    public Vector3D<Double> getBasePose() {
        return base;
    }

    public void setBase(Vector3D<Double> base) {
        this.base = base;
    }
}
