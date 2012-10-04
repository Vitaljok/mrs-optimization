/*
 * Copyright (C) 2011 Vitaljok
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
package phd.mrs.heuristic.object.config;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Vitaljok
 */
@Embeddable
public class Config implements Serializable {

    @Column(name = "agent_instance_limit")
    private Short agentInstanceLimit = 10;
    @Column(name = "population_size")
    private Short populationSize = 20;
    @Column(name = "generations_limit")
    private Integer generationsLimit = 1500;
    @Column(name = "generations_step")
    private Short generationsStep = 20;
    @Column(name = "minimum_pop_size_percent")
    private Short minimumPopSizePercent = 0;
    @Column(name = "select_from_prev_gen")
    private double selectFromPrevGen = 0.95;
    @Column(name = "keep_population_size_constant")
    private boolean keepPopulationSizeConstant = true;
    @Column(name = "selector_original_rate")
    private double selectorOriginalRate = 0.90;
    @Column(name = "doublette_chromosomes_allowed")
    private boolean doubletteChromosomesAllowed = false;
    @Column(name = "crossover_rate")
    private double crossoverRate = 0.35;
    @Column(name = "mutation_rate")
    private short mutationRate = 15;
    @Column(name = "near_nfinity")
    private Double nearInfinity = 1.0E12;
    @Column(name = "near_zero")
    private Double nearZero = 1.0E-12;

    public Short getAgentInstanceLimit() {
        return agentInstanceLimit;
    }

    public void setAgentInstanceLimit(Short agentInstanceLimit) {
        this.agentInstanceLimit = agentInstanceLimit;
    }

    public Short getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(Short populationSize) {
        this.populationSize = populationSize;
    }

    public Integer getGenerationsLimit() {
        return generationsLimit;
    }

    public void setGenerationsLimit(Integer generationsLimit) {
        this.generationsLimit = generationsLimit;
    }

    public Short getGenerationsStep() {
        return generationsStep;
    }

    public void setGenerationsStep(Short generationsStep) {
        this.generationsStep = generationsStep;
    }

    public Short getMinimumPopSizePercent() {
        return minimumPopSizePercent;
    }

    public void setMinimumPopSizePercent(Short minimumPopSizePercent) {
        this.minimumPopSizePercent = minimumPopSizePercent;
    }

    public double getSelectFromPrevGen() {
        return selectFromPrevGen;
    }

    public void setSelectFromPrevGen(double selectFromPrevGen) {
        this.selectFromPrevGen = selectFromPrevGen;
    }

    public boolean isKeepPopulationSizeConstant() {
        return keepPopulationSizeConstant;
    }

    public void setKeepPopulationSizeConstant(boolean keepPopulationSizeConstant) {
        this.keepPopulationSizeConstant = keepPopulationSizeConstant;
    }

    public double getSelectorOriginalRate() {
        return selectorOriginalRate;
    }

    public void setSelectorOriginalRate(double selectorOriginalRate) {
        this.selectorOriginalRate = selectorOriginalRate;
    }

    public boolean isDoubletteChromosomesAllowed() {
        return doubletteChromosomesAllowed;
    }

    public void setDoubletteChromosomesAllowed(boolean doubletteChromosomesAllowed) {
        this.doubletteChromosomesAllowed = doubletteChromosomesAllowed;
    }

    public double getCrossoverRate() {
        return crossoverRate;
    }

    public void setCrossoverRate(double crossoverRate) {
        this.crossoverRate = crossoverRate;
    }

    public short getMutationRate() {
        return mutationRate;
    }

    public void setMutationRate(short mutationRate) {
        this.mutationRate = mutationRate;
    }

    public Double getNearInfinity() {
        return nearInfinity;
    }

    public void setNearInfinity(Double nearInfinity) {
        this.nearInfinity = nearInfinity;
    }

    public Double getNearZero() {
        return nearZero;
    }

    public void setNearZero(Double nearZero) {
        this.nearZero = nearZero;
    }
}
