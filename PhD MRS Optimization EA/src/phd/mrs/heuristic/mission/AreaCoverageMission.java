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

import phd.mrs.heuristic.entity.Agent;
import phd.mrs.heuristic.entity.Component;

/**
 * Area coverage mission, which imply that agent moves upon defined area 
 * and performs an operation by its work device.
 * @author Vitaljok
 */
public class AreaCoverageMission implements Mission {

    Double areaSizeX;
    Double areaSizeY;
    Double workDensity;
    Double workDeviceWidth;
    Double movingSpeed;
    Component mobileBase;
    Component workDevice;

    /**
     * Creates new mission instance
     * @param areaSizeX size of working area on X axis
     * @param areaSizeY size of working area on Y axis
     * @param workDensity percentage of total area to be processed during work
     */
    public AreaCoverageMission(Double areaSizeX, Double areaSizeY, Double workDensity) {
        this.areaSizeX = areaSizeX;
        this.areaSizeY = areaSizeY;
        this.workDensity = workDensity;
    }

    public Component getMobileBase() {
        return mobileBase;
    }

    /**
     * @param mobileBase component used to move the agent around the area
     * @param movingSpeed moving speed
     */
    public void setMobileBase(Component mobileBase, Double movingSpeed) {
        this.mobileBase = mobileBase;
        this.movingSpeed = movingSpeed;
    }

    public Component getWorkDevice() {
        return workDevice;
    }

    /**
     * @param workDevice component, which performs the work while moving around the area
     * @param workDeviceWidth width of work device
     */
    public void setWorkDevice(Component workDevice, Double workDeviceWidth) {
        this.workDevice = workDevice;
        this.workDeviceWidth = workDeviceWidth;
    }

    @Override
    public Double getAgentPerformance(Agent agent) {
        if (agent.getComponents().contains(this.mobileBase)
                && agent.getComponents().contains(this.workDevice)) {
            return this.movingSpeed;
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
        return new Integer((int)(Math.ceil(this.getAmountOfWork() / this.movingSpeed * 1.25)));
    }
}
