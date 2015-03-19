/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ik.solver;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *
 * @author Vitaljok
 */
public class Joint2d {

    final private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    private Double lenght;
    private Double alfa;
    private Double alfaOffset = 0d;
    
    private Arm2d arm;
    
    public Vector3D<Double> auxVector;
    public Double auxValue;

    private Joint2d parentJoint;

    public Double getLenght() {
        return lenght;
    }

    public void setLenght(Double lenght) {
        Double oldLenght = this.lenght;
        this.lenght = lenght;
        this.pcs.firePropertyChange("lenght", oldLenght, lenght);
    }

    public Double getAlfa() {
        return alfa;
    }

    public void setAlfa(Double alfa) {
        
        alfa = Math.min(Math.max(alfa, -90d), 90d);
        
        Double oldAlfa = this.alfa;
        this.alfa = alfa;
        this.pcs.firePropertyChange("alfa", oldAlfa, alfa);
    }
    
    public void changeAlfa(Double delta){
        double maxDelta = 5;
        this.setAlfa(this.alfa + Math.max(Math.min(delta, maxDelta), -maxDelta));
    }

    public Double getAlfaOffset() {
        return alfaOffset;
    }

    public void setAlfaOffset(Double alfaOffset) {
        this.alfaOffset = alfaOffset;
    }
    
    public Vector3D<Double> getBasePose() {
        if (parentJoint != null) {
            return this.parentJoint.getBasePose().add(this.parentJoint.getVector());
        } else
        {
            return arm.getBasePose();
        } 
    }
    
    public Vector3D<Double> getTipPose() {
        return this.getBasePose().add(this.getVector());
    }        

    public Vector3D<Double> getVector() {
        Double globalAlfa = this.getGlobalAlfa();               
        
        return new Vector3D(
                Math.cos(Math.toRadians(globalAlfa)) * this.lenght,
                Math.sin(Math.toRadians(globalAlfa)) * this.lenght,
                0d);
    }

    public Double getGlobalAlfa() {
        if (parentJoint != null) {
            return this.parentJoint.getGlobalAlfa() + this.alfa + this.alfaOffset;
        } else {
            return this.alfa + this.alfaOffset;
        }

    }

    public Joint2d(Arm2d arm, Double lenght, Double alfa) {
        this.lenght = lenght;
        this.alfa = alfa;
        this.arm = arm;
    }

    
    public Joint2d getParentJoint() {
        return parentJoint;
    }

    public void setParentJoint(Joint2d parentJoint) {
        this.parentJoint = parentJoint;
    }

    public Joint2d() {
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

    @Override
    public String toString() {
        return "Joint <" + this.alfa + ", " + this.lenght + ">";
    }

    public Arm2d getArm() {
        return arm;
    }

}
