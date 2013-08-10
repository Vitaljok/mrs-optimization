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
public class GreenhouseInspectionMission extends GreenhouseMission {

    Double inspectionTime;

    public Double getInspectionTime() {
        return inspectionTime;
    }

    public void setInspectionTime(Double inspectionTime) {
        this.inspectionTime = inspectionTime;
    }

    @Override
    public Double getAgentPerformance(Agent agent) {
        Map<String, Component> compMap = agent.getComponentMap();

        if (compMap.containsKey(Const.familyPlatform)
                && compMap.containsKey(Const.familyRanger)
                && compMap.containsKey(Const.familyCamera)) {

            if (compMap.get(Const.familyRanger).getDoubleProperty(Const.propTolerance) > this.requiredTolerance) {
                // ranger's tolerance is too low for this task
                // agent is unable to perform it
                return 0d;
            }

            Double approachDistance = compMap.get(Const.familyPlatform).getDoubleProperty(Const.propApproachDist);
            Double robotMovingSpeed = Math.min(this.maxAllowedSpeed, compMap.get(Const.familyPlatform).getDoubleProperty(Const.propMaxSpeed));
            Double sampleRate = compMap.get(Const.familyRanger).getDoubleProperty(Const.propSampleRate);

            // time for moving between plants
            Double movingTime = (this.intervalPlants - approachDistance) / robotMovingSpeed;

            // time for positioning near plant
            Double positioningTime = approachDistance / (this.requiredTolerance * sampleRate);

            // work units per sec
            return 1 / (movingTime + positioningTime + inspectionTime);
        } else {
            return 0d;
        }
    }

    @Override
    public Double getAmountOfWork() {
        // work units to perform - number of plants
        return 1d * this.numOfWorkCicles * this.numOfRows * this.numOfPlants;
    }

    public GreenhouseInspectionMission(Integer numOfRows, Integer numOfPlants, Integer numOfWorkCicles) {
        super(numOfRows, numOfPlants, numOfWorkCicles);
        this.maxAllowedSpeed = 999d;
    }

    public GreenhouseInspectionMission() {
        super();
    }
}
