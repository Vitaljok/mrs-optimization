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

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSeeAlso;
import phd.mrs.heuristic.object.Agent;
import phd.mrs.heuristic.object.Component;
import phd.mrs.heuristic.object.Project;

/**
 *
 * @author Vitaljok
 */
@XmlSeeAlso({AreaCoverageMission.class, TransportationMission.class})
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "mission_type")
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"project_id", "mission_type"})})
public abstract class Mission implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    @Column(name = "area_size_x")
    protected Double areaSizeX;
    @Column(name = "area_size_y")
    protected Double areaSizeY;
    @Column(name = "work_density")
    protected Double workDensity;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="mobile_base_id")
    protected Component mobileBase;
    @Column(name="mobile_base_speed")
    protected Double mobileBaseSpeed;

    public abstract Double getAgentPerformance(Agent agent);

    public abstract Double getAmountOfWork();

    public abstract Integer getMaxTimeEstimation();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
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

    public Double getWorkDensity() {
        return workDensity;
    }

    public void setWorkDensity(Double workDensity) {
        this.workDensity = workDensity;
    }

    @XmlIDREF
    public Component getMobileBase() {
        return mobileBase;
    }

    public void setMobileBase(Component mobileBase) {
        this.mobileBase = mobileBase;
    }

    public Double getMobileBaseSpeed() {
        return mobileBaseSpeed;
    }

    public void setMobileBaseSpeed(Double mobileBaseSpeed) {
        this.mobileBaseSpeed = mobileBaseSpeed;
    }    
}
