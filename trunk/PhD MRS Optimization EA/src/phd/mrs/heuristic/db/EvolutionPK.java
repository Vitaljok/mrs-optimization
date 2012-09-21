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
import javax.persistence.Embeddable;

/**
 *
 * @author Vitaljok
 */
@Embeddable
public class EvolutionPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "process_id")
    private int processId;
    @Basic(optional = false)
    @Column(name = "generation")
    private int generation;
    @Basic(optional = false)
    @Column(name = "chromosome")
    private short chromosome;

    public EvolutionPK() {
    }

    public EvolutionPK(int processId, int generation, short chromosome) {
        this.processId = processId;
        this.generation = generation;
        this.chromosome = chromosome;
    }

    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public short getChromosome() {
        return chromosome;
    }

    public void setChromosome(short chromosome) {
        this.chromosome = chromosome;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) processId;
        hash += (int) generation;
        hash += (int) chromosome;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvolutionPK)) {
            return false;
        }
        EvolutionPK other = (EvolutionPK) object;
        if (this.processId != other.processId) {
            return false;
        }
        if (this.generation != other.generation) {
            return false;
        }
        if (this.chromosome != other.chromosome) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "phd.mrs.heuristic.db.EvolutionPK[ processId=" + processId + ", generation=" + generation + ", chromosome=" + chromosome + " ]";
    }
    
}
