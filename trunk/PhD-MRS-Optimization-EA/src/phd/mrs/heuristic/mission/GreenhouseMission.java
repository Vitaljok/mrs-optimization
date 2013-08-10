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

import javax.persistence.Entity;

@Entity
public abstract class GreenhouseMission extends Mission {

    Integer numOfRows;
    Integer numOfPlants;
    Integer numOfWorkCicles;
    Double requiredTolerance;
    Double intervalPlants;
    Double intervalRows;
    Double maxAllowedSpeed;

    public GreenhouseMission() {
    }

    public GreenhouseMission(Integer numOfRows, Integer numOfPlants, Integer numOfWorkCicles) {
        this.numOfRows = numOfRows;
        this.numOfPlants = numOfPlants;
        this.numOfWorkCicles = numOfWorkCicles;
    }

    public Double getRequiredTolerance() {
        return requiredTolerance;
    }

    public void setRequiredTolerance(Double requiredTolerance) {
        this.requiredTolerance = requiredTolerance;
    }

    public Integer getNumOfRows() {
        return numOfRows;
    }

    public void setNumOfRows(Integer numOfRows) {
        this.numOfRows = numOfRows;
    }

    public Integer getNumOfPlants() {
        return numOfPlants;
    }

    public void setNumOfPlants(Integer numOfPlants) {
        this.numOfPlants = numOfPlants;
    }

    public Double getIntervalPlants() {
        return intervalPlants;
    }

    public void setIntervalPlants(Double intervalPlants) {
        this.intervalPlants = intervalPlants;
    }

    public Double getIntervalRows() {
        return intervalRows;
    }

    public void setIntervalRows(Double intervalRows) {
        this.intervalRows = intervalRows;
    }

    public Double getMaxAllowedSpeed() {
        return maxAllowedSpeed;
    }

    public void setMaxAllowedSpeed(Double maxAllowedSpeed) {
        this.maxAllowedSpeed = maxAllowedSpeed;
    }

    public Integer getNumOfWorkCicles() {
        return numOfWorkCicles;
    }

    public void setNumOfWorkCicles(Integer numOfWorkCicles) {
        this.numOfWorkCicles = numOfWorkCicles;
    }
}
