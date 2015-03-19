/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ik;

import ik.solver.Arm2d;
import ik.solver.Joint2d;
import ik.solver.Vector3D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import javax.swing.JPanel;

/**
 *
 * @author Vitaljok
 */
public class IKPanel extends JPanel {

    Float scale = 20f;
    Arm2d arm;
    Vector3D<Double> target;

    public Vector3D<Double> getTarget() {
        return target;
    }

    public void setTarget(Vector3D<Double> target) {
        this.target = target;
    }

    public Arm2d getArm() {
        return arm;
    }

    public void setArm(Arm2d arm) {
        this.arm = arm;
    }

    public Float getScale() {
        return scale;
    }

    public void setScale(Float scale) {
        this.scale = scale;
    }

    public IKPanel() {
        setBorder(javax.swing.BorderFactory.createEtchedBorder());
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D gra = (Graphics2D) g;

        AffineTransform oldAt = gra.getTransform();
        AffineTransform at = new AffineTransform(oldAt);
        at.translate(10, this.getHeight() - 10);
        at.scale(1, -1);
        //at.rotate(Math.PI / 2);
        //at.scale(2, 2);               
        gra.setTransform(at);

        gra.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < 50; i++) {
            if (i == 0) {
                gra.setStroke(new BasicStroke(2));
            } else {
                gra.setStroke(new BasicStroke(1));
            }

            if (i % 5 == 0) {
                gra.setColor(Color.GRAY);
            } else {
                gra.setColor(Color.LIGHT_GRAY);
            }

            gra.drawLine(Math.round(i * scale), 0, Math.round(i * scale), this.getHeight());
            gra.drawLine(0, Math.round(i * scale), this.getWidth(), Math.round(i * scale));
        }

        if (arm != null) {

            gra.setStroke(new BasicStroke(2));
            gra.setColor(Color.blue);

            Path2D.Float path = new Path2D.Float();
            path.moveTo(arm.getBasePose().getA() * scale,
                    arm.getBasePose().getB() * scale);

            Ellipse2D.Double circle = new Ellipse2D.Double();
            circle.height = 10d;
            circle.width = 10d;

            for (Joint2d joint : arm.getJoints()) {
                Vector3D<Double> base = joint.getBasePose();
                Vector3D<Double> tip = joint.getTipPose();

                circle.x = base.getA() * scale - 5;
                circle.y = base.getB() * scale - 5;
                gra.draw(circle);

                path.lineTo(tip.getA() * scale, tip.getB() * scale);
            }

            gra.draw(path);

            for (Joint2d joint : arm.getJoints()) {
                if (joint.auxVector != null) {
                    gra.setColor(Color.MAGENTA);
                    gra.setStroke(new BasicStroke(1));

                    Path2D.Double line = new Path2D.Double();
                    Vector3D<Double> from = joint.getBasePose();
                    Vector3D<Double> to = from.add(joint.auxVector);
                    line.moveTo(from.getA() * scale, from.getB() * scale);
                    line.lineTo(to.getA() * scale, to.getB() * scale);
                    gra.draw(line);
                }
            }
        }

        if (this.target != null) {
            gra.setColor(Color.red);
            gra.setStroke(new BasicStroke(2));

            Path2D.Float cross = new Path2D.Float();

            cross.moveTo(target.getA() * scale - 5, target.getB() * scale - 5);
            cross.lineTo(target.getA() * scale + 5, target.getB() * scale + 5);

            cross.moveTo(target.getA() * scale + 5, target.getB() * scale - 5);
            cross.lineTo(target.getA() * scale - 5, target.getB() * scale + 5);

            gra.draw(cross);
        }

//        gra.setColor(Color.green);
//        gra.setStroke(new BasicStroke(1));
//
//        Path2D.Float toTip = new Path2D.Float();
//        Vector3D<Double> armBase = arm.getBasePose();
//        Vector3D<Double> amrTip = arm.getTip();
//        toTip.moveTo(armBase.getA() * scale, armBase.getB() * scale);
//        toTip.lineTo(amrTip.getA() * scale, amrTip.getB() * scale);
//        gra.draw(toTip);
//        for (Joint2d joint : arm.getJoints()) {
//            gra.setColor(Color.MAGENTA);
//            gra.setStroke(new BasicStroke(1));
//
//            Path2D.Float line = new Path2D.Float();
//            Vector3D<Double> baseVec = joint.getGlobalCenterPose();
//            line.moveTo(baseVec.getA() * scale, baseVec.getB() * scale);
//            line.lineTo(amrTip.getA() * scale, amrTip.getB() * scale);
//            gra.draw(line);
//        }
        gra.setTransform(oldAt);

//        if (arm != null) {
//
//            for (Joint2d joint : arm.getJoints()) {
//
//                if (joint.auxValue != null) {
//                    Vector3D<Double> pos = joint.getBasePose();
//
//                    gra.drawString(joint.auxValue.toString(), pos.getA().floatValue() * scale + 15, this.getHeight() - pos.getB().floatValue() * scale + 15);
//                }
//            }
//        }
    }

}
