/*
 * Copyright (C) 2012 Vitaljok
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package phd.mrs.heuristic.object.config;

import java.io.Serializable;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

/**
 * Cost estimation model.
 * Calculations are based on polynomial regression:
 * f(x) = b0 + b1*x + b2*x^k    
 * @author Vitaljok
 */
@Embeddable
public class CostModel implements Serializable {

    /**
     * Agent design cost coefficients
     */
    @AttributeOverrides({
        @AttributeOverride(name = "b0", column = @Column(name = "design_b0")),
        @AttributeOverride(name = "b1", column = @Column(name = "design_b1")),
        @AttributeOverride(name = "b2", column = @Column(name = "design_b2")),
        @AttributeOverride(name = "k", column = @Column(name = "design_k")),
    })
    @Embedded
    private AbstractCoefs design = new AbstractCoefs(400d, 100d, 0.5, 4d);
    /**
     * Agent assembly cost coefficients
     */
    @AttributeOverrides({
        @AttributeOverride(name = "b0", column = @Column(name = "assembly_b0")),
        @AttributeOverride(name = "b1", column = @Column(name = "assembly_b1")),
        @AttributeOverride(name = "b2", column = @Column(name = "assembly_b2")),
        @AttributeOverride(name = "k", column = @Column(name = "assembly_k")),
    })
    @Embedded
    private AbstractCoefs assembly = new AbstractCoefs(20d, 15d, 0.3, 3d);
    /**
     * System design coefficients
     */
    @AttributeOverrides({
        @AttributeOverride(name = "b0", column = @Column(name = "sys_design_b0")),
        @AttributeOverride(name = "b1", column = @Column(name = "sys_design_b1")),
        @AttributeOverride(name = "b2", column = @Column(name = "sys_design_b2")),
        @AttributeOverride(name = "k", column = @Column(name = "sys_design_k")),
    })
    @Embedded
    private AbstractCoefs sysDesign = new AbstractCoefs(2800d, 1000d, 10d, 3d);
    /**
     * Agent energy loss coefficients
     */
    @AttributeOverrides({
        @AttributeOverride(name = "b0", column = @Column(name = "energy_loss_b0")),
        @AttributeOverride(name = "b1", column = @Column(name = "energy_loss_b1")),
        @AttributeOverride(name = "b2", column = @Column(name = "energy_loss_b2")),
        @AttributeOverride(name = "k", column = @Column(name = "energy_loss_k")),
    })
    @Embedded
    private AbstractCoefs energyLoss = new AbstractCoefs(0, 0.000000015, 0.000000015, 4);
    /**
     * System maintenance coefficients
     */
    @AttributeOverrides({
        @AttributeOverride(name = "b0", column = @Column(name = "sys_maint_b0")),
        @AttributeOverride(name = "b1", column = @Column(name = "sys_maint_b1")),
        @AttributeOverride(name = "b2", column = @Column(name = "sys_maint_b2")),
        @AttributeOverride(name = "k", column = @Column(name = "sys_maint_k")),
    })
    @Embedded
    private AbstractCoefs sysMaint = new AbstractCoefs(0.001388889, 0.000138889, 2.77778e-05, 2);
    /**
     * Eventual replacement rate
     */
    @Column(name = "system_repl_rate")
    private Double systemReplRate = 1.584438233281084e-8; // once per 2 years

    private double calcFunction(double b0, double b1, double b2, double k, double x) {
        return b0 + b1 * x + b2 * Math.pow(x, k);
    }

    /**
     *
     * @param N number of components in agent
     * @return Agent assembly costs
     */
    public double calcAssembly(int N, double cmplx) {
        return calcFunction(getAssembly().b0, getAssembly().b1, getAssembly().b2, getAssembly().k, N) * cmplx;
    }

    /**
     *
     * @param N number of components in agent
     * @return Agent design costs
     */
    public double calcDesign(int N) {
        return calcFunction(getDesign().b0, getDesign().b1, getDesign().b2, getDesign().k, N);
    }

    /**
     *
     * @param N number of components in agent
     * @return Agent energy costs
     */
    public double calcEnergyLoss(int N) {
        return calcFunction(getEnergyLoss().b0, getEnergyLoss().b1, getEnergyLoss().b2, getEnergyLoss().k, N);
    }

    /**
     *
     * @param N total number of agents in system
     * @return system design costs
     */
    public double calcSysDesign(int N) {
        return calcFunction(getSysDesign().b0, getSysDesign().b1, getSysDesign().b2, getSysDesign().k, N);
    }

    /**
     *
     * @param N total number of agents in system
     * @return system maintenence costs
     */
    public double calcSysMaint(int N) {
        return calcFunction(getSysMaint().b0, getSysMaint().b1, getSysMaint().b2, getSysMaint().k, N);
    }

    public AbstractCoefs getDesign() {
        return design;
    }

    public void setDesign(AbstractCoefs design) {
        this.design = design;
    }

    public AbstractCoefs getAssembly() {
        return assembly;
    }

    public void setAssembly(AbstractCoefs assembly) {
        this.assembly = assembly;
    }

    public AbstractCoefs getSysDesign() {
        return sysDesign;
    }

    public void setSysDesign(AbstractCoefs sysDesign) {
        this.sysDesign = sysDesign;
    }

    public AbstractCoefs getEnergyLoss() {
        return energyLoss;
    }

    public void setEnergyLoss(AbstractCoefs energyLoss) {
        this.energyLoss = energyLoss;
    }

    public AbstractCoefs getSysMaint() {
        return sysMaint;
    }

    public void setSysMaint(AbstractCoefs sysMaint) {
        this.sysMaint = sysMaint;
    }

    public Double getSystemReplRate() {
        return systemReplRate;
    }

    public void setSystemReplRate(Double systemReplRate) {
        this.systemReplRate = systemReplRate;
    }
}
