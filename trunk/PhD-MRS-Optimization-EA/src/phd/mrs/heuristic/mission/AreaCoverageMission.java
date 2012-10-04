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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
@Entity
public class AreaCoverageMission extends Mission {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="work_device_id")
    private Component workDevice;
    @Column(name="work_device_width")
    private Double workDeviceWidth;

    public AreaCoverageMission() {
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

    public Double getWorkDeviceWidth() {
        return workDeviceWidth;
    }

    public void setWorkDeviceWidth(Double workDeviceWidth) {
        this.workDeviceWidth = workDeviceWidth;
    }

    @XmlIDREF
    public Component getWorkDevice() {
        return workDevice;
    }

    public void setWorkDevice(Component workDevice) {
        this.workDevice = workDevice;
    }

    @Override
    public Double getAgentPerformance(Agent agent) {
        if (agent.getComponents().contains(this.mobileBase)
                && agent.getComponents().contains(this.workDevice)) {
            return this.mobileBaseSpeed * this.workDeviceWidth;
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
        return new Integer((int) (Math.ceil(this.getAmountOfWork() / this.mobileBaseSpeed * 1.25)));
    }
}
