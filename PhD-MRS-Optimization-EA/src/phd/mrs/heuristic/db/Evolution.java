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
package phd.mrs.heuristic.db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Vitaljok
 */
@Entity
@Table(name = "evolution")
@NamedQueries({
    @NamedQuery(name = "Evolution.findAll", query = "SELECT e FROM Evolution e")})
public class Evolution implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EvolutionPK evolutionPK;
    @Column(name = "age")
    private Integer age;
    @Basic(optional = false)
    @Column(name = "fitness_value")
    private double fitnessValue;
    @Column(name = "best_ind")
    private Boolean bestInd;
    @JoinColumn(name = "process_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Process process;

    public Evolution() {
    }

    public Evolution(EvolutionPK evolutionPK) {
        this.evolutionPK = evolutionPK;
    }

    public Evolution(EvolutionPK evolutionPK, double fitnessValue) {
        this.evolutionPK = evolutionPK;
        this.fitnessValue = fitnessValue;
    }

    public Evolution(int processId, int generation, short chromosome) {
        this.evolutionPK = new EvolutionPK(processId, generation, chromosome);
    }

    public EvolutionPK getEvolutionPK() {
        return evolutionPK;
    }

    public void setEvolutionPK(EvolutionPK evolutionPK) {
        this.evolutionPK = evolutionPK;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public double getFitnessValue() {
        return fitnessValue;
    }

    public void setFitnessValue(double fitnessValue) {
        this.fitnessValue = fitnessValue;
    }

    public Boolean getBestInd() {
        return bestInd;
    }

    public void setBestInd(Boolean bestInd) {
        this.bestInd = bestInd;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (evolutionPK != null ? evolutionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Evolution)) {
            return false;
        }
        Evolution other = (Evolution) object;
        if ((this.evolutionPK == null && other.evolutionPK != null) || (this.evolutionPK != null && !this.evolutionPK.equals(other.evolutionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "phd.mrs.heuristic.db.Evolution[ evolutionPK=" + evolutionPK + " ]";
    }
    
}
