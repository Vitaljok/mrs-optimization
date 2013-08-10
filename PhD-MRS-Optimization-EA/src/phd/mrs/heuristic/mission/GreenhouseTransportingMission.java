/*
 * Copyright (C) 2013 Vitaljok
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

import java.util.Map;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import phd.mrs.heuristic.object.Agent;
import phd.mrs.heuristic.object.Component;
import phd.mrs.heuristic.object.config.Const;

@XmlRootElement
@Entity
public class GreenhouseTransportingMission extends GreenhouseMission {

    Double volumeConsumption;
    Double volumeTransferSpeed;
    Double distanceToStorage;

    public Double getDistanceToStorage() {
        return distanceToStorage;
    }

    public void setDistanceToStorage(Double distanceToStorage) {
        this.distanceToStorage = distanceToStorage;
    }

    public Double getVolumeTransferSpeed() {
        return volumeTransferSpeed;
    }

    public void setVolumeTransferSpeed(Double volumeTransferSpeed) {
        this.volumeTransferSpeed = volumeTransferSpeed;
    }

    public Double getVolumeConsumption() {
        return volumeConsumption;
    }

    public void setVolumeConsumption(Double volumeConsumption) {
        this.volumeConsumption = volumeConsumption;
    }

    @Override
    public Double getAgentPerformance(Agent agent) {
        Map<String, Component> compMap = agent.getComponentMap();

        if (compMap.containsKey(Const.familyPlatform)
                && compMap.containsKey(Const.familyTank)) {

            Double agentVolume = compMap.get(Const.familyPlatform).getDoubleProperty(Const.propMaxWeight);
            Double robotMovingSpeed = Math.min(this.maxAllowedSpeed, compMap.get(Const.familyPlatform).getDoubleProperty(Const.propMaxSpeed));

            // time for refueling tank at storage
            Double refuelingTime = agentVolume / this.volumeTransferSpeed;

            // time for moving from storage to greenhouse
            Double movingFromStorageTime = this.distanceToStorage / robotMovingSpeed;

            Double movingInGreenhouseTime = 0.5 * (this.numOfPlants * this.intervalPlants + this.numOfRows * this.intervalRows) / robotMovingSpeed;

            // work units per sec - liters per sec
            return agentVolume / (refuelingTime + movingFromStorageTime + movingInGreenhouseTime);
        } else {
            return 0d;
        }
    }

    @Override
    public Double getAmountOfWork() {
        // work unit - liters transported   
        return this.numOfWorkCicles * this.numOfRows * this.numOfPlants * this.workDensity * this.volumeConsumption;
    }

    public GreenhouseTransportingMission(Integer numOfRows, Integer numOfPlants, Integer numOfWorkCicles) {
        super(numOfRows, numOfPlants, numOfWorkCicles);
    }

    public GreenhouseTransportingMission() {
        super();
    }
}
