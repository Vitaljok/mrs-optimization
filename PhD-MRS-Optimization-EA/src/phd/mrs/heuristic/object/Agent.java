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
package phd.mrs.heuristic.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Transient;
import phd.mrs.heuristic.utils.CachedProperty;
import phd.mrs.heuristic.utils.Debug;

/**
 *
 * @author Vitaljok
 */
@Entity
public class Agent implements Cloneable, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    @OneToMany(cascade= CascadeType.ALL)
    @JoinTable(name = "agent_component",
    joinColumns = {
        @JoinColumn(name = "agent_id")
    },
    inverseJoinColumns = {
        @JoinColumn(name = "component_id")
    })
    private List<Component> components = new ArrayList<>();
    @Transient
    private CachedProperty<Double> designCosts = new CachedProperty<Double>() {

        @Override
        protected Double calculateValue() {
            Debug.log.fine("Calculated cached desing costs");
            return project.getCostModel().calcDesign(getComponents().size());
        }
    };
    @Transient
    private CachedProperty<Double> productionCosts = new CachedProperty<Double>() {

        @Override
        protected Double calculateValue() {
            Debug.log.fine("Calculated cached production costs");
            double costs = 0d;
            double maxComplexity = 0d;

            // sum of components
            for (Component comp : getComponents()) {
                double compPrice = comp.getInvestmentCosts();
                maxComplexity = Math.max(maxComplexity, comp.getComplexity());
                costs += compPrice;
            }

            // add assembly costs
            costs += project.getCostModel().calcAssembly(getComponents().size(), maxComplexity);
            return costs;
        }
    };
    @Transient
    private CachedProperty<Double> operatingEnergy = new CachedProperty<Double>() {

        @Override
        protected Double calculateValue() {
            Debug.log.fine("Calculated cached operating energy");

            double energy = 0d;
            for (Component comp : components) {
                energy += comp.getOperatingPower();
            }

            // add energy loss
            energy += project.getCostModel().calcEnergyLoss(getComponents().size());
            return energy;
        }
    };
    
    @Lob
    @Column(name="code")
    private String code;

    @PrePersist
    private void prePersist(){
        this.code = "";
        for (Component comp : components) {
            this.code += comp.toString()+"\n";
        }        
    }
    
    public Agent() {
    }

    public Double getOperatingEnergy() {
        return operatingEnergy.getValue();
    }

    public Agent(Project project) {
        this.project = project;
        Debug.log.finest("New agent created");
    }

    public List<Component> getComponents() {
        return components;
    }

    public Double getProductionCosts() {
        return this.productionCosts.getValue();
    }

    public Double getDesignCosts() {
        return this.designCosts.getValue();
    }

    @Override
    public Agent clone() {
        Agent new_agent = new Agent(project);
        new_agent.components = this.components;
        new_agent.operatingEnergy = this.operatingEnergy;
        new_agent.productionCosts = this.productionCosts;
        new_agent.designCosts = this.designCosts;

        return new_agent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }    
    
    @Transient
    private CachedProperty<Map<String, Component>> componentMap = new CachedProperty<Map<String, Component>>() {

        @Override
        protected Map<String, Component> calculateValue() {
            Map<String, Component> map = new HashMap<>();
            
            for (Component comp : components) {
                map.put(comp.getFamily(), comp);
            }
            
            return map;
        }
    };

    public Map<String, Component> getComponentMap() {
        return componentMap.getValue();
    }
    
    
    
}
