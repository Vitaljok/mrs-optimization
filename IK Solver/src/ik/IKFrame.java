/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ik;

import ik.solver.Arm2d;
import ik.solver.IKSolver;
import ik.solver.Joint2d;
import ik.solver.Vector3D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Timer;

/**
 *
 * @author Vitaljok
 */
public class IKFrame extends javax.swing.JFrame {

    Arm2d arm;
    IKSolver solver = new IKSolver();
    Vector3D<Double> target = new Vector3D<>(25d, 10d, 0d);

    /**
     * Creates new form IKFrame
     */
    public IKFrame() {
        initComponents();

        arm = new Arm2d();
        arm.setBase(new Vector3D(5d, 2d, 0d));
        Joint2d joint1 = new Joint2d(arm, 10d, -30d);
        joint1.setAlfaOffset(90d);
        Joint2d joint2 = new Joint2d(arm, 7.5d, -60d);
        Joint2d joint3 = new Joint2d(arm, 5d, -60d);
        Joint2d joint4 = new Joint2d(arm, 5d, -30d);

        this.jointPanel1.setJoint(joint1);
        this.jointPanel2.setJoint(joint2);
        this.jointPanel3.setJoint(joint3);
        this.jointPanel4.setJoint(joint4);

        arm.addJoint(joint1);
        arm.addJoint(joint2);
        arm.addJoint(joint3);
        arm.addJoint(joint4);

        PropertyChangeListener pcl = new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                iKPanel.repaint();
            }
        };

        for (Joint2d joint : arm.getJoints()) {
            joint.addPropertyChangeListener(pcl);
        }

        this.iKPanel.setArm(arm);
        this.iKPanel.setTarget(target);

        solver.setArm(arm);
        solver.setTarget(target);

        new Timer(50, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!solver.isSolved()) {
                    solver.step();
                    iKPanel.repaint();
                }
            }
        }).start();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        iKPanel = new ik.IKPanel();
        jointPanel1 = new ik.JointPanel();
        jointPanel2 = new ik.JointPanel();
        jointPanel3 = new ik.JointPanel();
        jointPanel4 = new ik.JointPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        iKPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                iKPanelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout iKPanelLayout = new javax.swing.GroupLayout(iKPanel);
        iKPanel.setLayout(iKPanelLayout);
        iKPanelLayout.setHorizontalGroup(
            iKPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 742, Short.MAX_VALUE)
        );
        iKPanelLayout.setVerticalGroup(
            iKPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(iKPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jointPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jointPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jointPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jointPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(iKPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jointPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jointPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jointPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jointPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void iKPanelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iKPanelMouseReleased

        evt.translatePoint(-10, 10);
        this.target = new Vector3D<>(evt.getX() / 20d, (this.iKPanel.getHeight() - evt.getY()) / 20d, 0d);
        this.iKPanel.setTarget(target);
        //this.iKPanel.repaint();
        this.solver.setTarget(target);
    }//GEN-LAST:event_iKPanelMouseReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(IKFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IKFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IKFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IKFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IKFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private ik.IKPanel iKPanel;
    private ik.JointPanel jointPanel1;
    private ik.JointPanel jointPanel2;
    private ik.JointPanel jointPanel3;
    private ik.JointPanel jointPanel4;
    // End of variables declaration//GEN-END:variables
}
