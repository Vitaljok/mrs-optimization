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
package phd.mrs.heuristic.mission;

import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import phd.mrs.heuristic.object.Agent;
import phd.mrs.heuristic.object.Component;

/**
 * Area coverage mission, which imply that agent moves upon defined area 
 * and performs an operation by its work device.
 * @author Vitaljok
 */
@XmlRootElement
public class AreaCoverageMission extends Mission {

    private Double areaSizeX;
    private Double areaSizeY;
    private Double workDensity;        
    private Component mobileBase;
    private Double mobileBaseSpeed;
    private Component workDevice;
    private Double workDeviceWidth;

    private AreaCoverageMission() {
    }    

    /**
     * Creates new mission instance
     * @param areaSizeX size of working area on X axis
     * @param areaSizeY size of working area on Y axis
     * @param workDensity percentage of total area to be processed during work
     */
    public AreaCoverageMission(Double areaSizeX, Double areaSizeY) {
        this.areaSizeX = areaSizeX;
        this.areaSizeY = areaSizeY;
    }

    public Double getAreaSizeX() {
        return areaSizeX;
    }

    public void setAreaSizeX(Double areaSizeX) {
        this.areaSizeX = areaSizeX;
    }

    public Double getAreaSizeY() {
        return areaSizeY;
    }

    public void setAreaSizeY(Double areaSizeY) {
        this.areaSizeY = areaSizeY;
    }

    public Double getMobileBaseSpeed() {
        return mobileBaseSpeed;
    }

    public void setMobileBaseSpeed(Double mobileBaseSpeed) {
        this.mobileBaseSpeed = mobileBaseSpeed;
    }

    public Double getWorkDensity() {
        return workDensity;
    }

    public void setWorkDensity(Double workDensity) {
        this.workDensity = workDensity;
    }

    public Double getWorkDeviceWidth() {
        return workDeviceWidth;
    }

    public void setWorkDeviceWidth(Double workDeviceWidth) {
        this.workDeviceWidth = workDeviceWidth;
    }    
    
    @XmlIDREF
    public Component getMobileBase() {
        return mobileBase;
    }

    @XmlIDREF
    public Component getWorkDevice() {
        return workDevice;
    }

    public void setMobileBase(Component mobileBase) {
        this.mobileBase = mobileBase;
    }

    public void setWorkDevice(Component workDevice) {
        this.workDevice = workDevice;
    }    

    @Override
    public Double getAgentPerformance(Agent agent) {
        if (agent.getComponents().contains(this.mobileBase)
                && agent.getComponents().contains(this.workDevice)) {
            return this.mobileBaseSpeed*this.workDeviceWidth;
        } else {
            return 0d;
        }
    }    

    @Override
    public Double getAmountOfWork() {
        return this.areaSizeX * this.areaSizeY / this.workDeviceWidth * this.workDensity;
    }

    @Override
    public Integer getMaxTimeEstimation() {
        return new Integer((int)(Math.ceil(this.getAmountOfWork() / this.mobileBaseSpeed * 1.25)));
    }
}
