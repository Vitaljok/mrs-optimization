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
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import phd.mrs.heuristic.object.Agent;
import phd.mrs.heuristic.object.Component;
import phd.mrs.heuristic.utils.CachedProperty;

/**
 *
 * @author Vitaljok
 */
@XmlRootElement
@Entity
public class TransportationMission extends Mission {

    @Column(name="target_offset_x")
    private Double targetOffsetX;
    @Column(name="target_offset_y")
    private Double targetOffsetY;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="loader_id")
    private Component loader;
    @Transient
    private CachedProperty<Double> avgDist = new CachedProperty<Double>() {

        @Override
        protected Double calculateValue() {

            double sum = 0;
            int cnt = 0;

            for (double i = targetOffsetX; i < areaSizeX + targetOffsetX; i += areaSizeX * 0.01) {
                for (double j = targetOffsetY; j < areaSizeY + targetOffsetY; j += areaSizeX * 0.01) {
                    cnt++;
                    sum += Math.sqrt(i * i + j * j);
                }
            }

            return sum / cnt;
        }
    };

    public TransportationMission() {
    }

    public TransportationMission(Double areaSizeX, Double areaSizeY) {
        this.areaSizeX = areaSizeX;
        this.areaSizeY = areaSizeY;
    }

    @Override
    public Double getAgentPerformance(Agent agent) {
        if (agent.getComponents().contains(this.mobileBase)
                && agent.getComponents().contains(this.loader)) {
            return this.mobileBaseSpeed;
        } else {
            return 0d;
        }
    }

    @Override
    public Double getAmountOfWork() {
        return this.areaSizeX * this.areaSizeY * this.workDensity * this.avgDist.getValue();
    }

    public Double getTargetOffsetX() {
        return targetOffsetX;
    }

    public void setTargetOffsetX(Double targetOffsetX) {
        this.targetOffsetX = targetOffsetX;
    }

    public Double getTargetOffsetY() {
        return targetOffsetY;
    }

    public void setTargetOffsetY(Double targetOffsetY) {
        this.targetOffsetY = targetOffsetY;
    }

    @XmlIDREF
    public Component getLoader() {
        return loader;
    }

    public void setLoader(Component loader) {
        this.loader = loader;
    }
}
