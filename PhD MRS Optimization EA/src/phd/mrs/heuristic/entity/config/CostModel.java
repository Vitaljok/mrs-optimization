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
package phd.mrs.heuristic.entity.config;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Cost estimation model.
 * Calculations are based on polynomial regression:
 * f(x) = b0 + b1*x + b2*x^k    
 * @author Vitaljok
 */
@XmlRootElement
public class CostModel {

    /**
     * Agent design cost coefficients
     */
    public AbstractCoefs design = new AbstractCoefs(40, 10, 0.5, 2);
    /**
     * Agent assembly cost coefficients
     */
    public AbstractCoefs assembly = new AbstractCoefs(10, 5, 0.02, 3);
    /**
     * System design coefficients
     */
    public AbstractCoefs sysDesign = new AbstractCoefs(280, 20, 2, 2);
    /**
     * Agent energy loss coefficients
     */
    public AbstractCoefs energyLoss = new AbstractCoefs(0, 1, 0.01, 2);
    /**
     * System maintenance coefficients
     */
    public AbstractCoefs sysMaint = new AbstractCoefs(8, 2, 0.1, 2);
    /**
     * Eventual replacement rate
     */
    public Double systemReplRate = 0.005d;

    private double calcFunction(double b0, double b1, double b2, double k, double x) {
        return b0 + b1 * x + b2 * Math.pow(x, k);
    }

    /**
     *
     * @param N number of components in agent
     * @return Agent assembly costs
     */
    public double calcAssembly(int N, double cmplx) {
        return calcFunction(assembly.b0, assembly.b1, assembly.b2, assembly.k, N) * cmplx;
    }

    /**
     *
     * @param N number of components in agent
     * @return Agent design costs
     */
    public double calcDesign(int N) {
        return calcFunction(design.b0, design.b1, design.b2, design.k, N);
    }

    /**
     *
     * @param N number of components in agent
     * @return Agent energy costs
     */
    public double calcEnergyLoss(int N) {
        return calcFunction(energyLoss.b0, energyLoss.b1, energyLoss.b2, energyLoss.k, N);
    }

    /**
     *
     * @param N total number of agents in system
     * @return system design costs
     */
    public double calcSysDesign(int N) {
        return calcFunction(sysDesign.b0, sysDesign.b1, sysDesign.b2, sysDesign.k, N);
    }

    /**
     *
     * @param N total number of agents in system
     * @return system maintenence costs
     */
    public double calcSysMaint(int N) {
        return calcFunction(sysMaint.b0, sysMaint.b1, sysMaint.b2, sysMaint.k, N);
    }
}
