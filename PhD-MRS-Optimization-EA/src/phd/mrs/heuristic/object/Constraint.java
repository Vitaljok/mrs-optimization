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
package phd.mrs.heuristic.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Vitaljok
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"component_id", "ref_component_id"})},
        name = "Comp_Constraints")
public class Constraint implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    
    @Column(name="comment")
    private String comment;
    
    private ConstraintType type;
    
    @JoinColumn(name = "component_id", referencedColumnName = "id")
    @ManyToOne(optional = false, cascade= CascadeType.ALL)
    private Component component;
    
    @ManyToMany(cascade= CascadeType.ALL)
    @JoinTable(
      name="Comp_Constraints_refs",
      joinColumns={@JoinColumn(name="constraint_id", referencedColumnName="ID")},
      inverseJoinColumns={@JoinColumn(name="ref_component_id", referencedColumnName="ID")})
    private List<Component> refComponents = new ArrayList<>();        

    public Constraint() {
    }        

    protected Constraint(Component component, ConstraintType type, String comment, Component... refComponents) {
        this.component = component;        
        this.type = type;
        this.comment = comment;
        this.refComponents.addAll(Arrays.asList(refComponents));
        
        if (this.refComponents.contains(this.component))
            throw new Error("Self reference in constraint components: "+component.getCode());
    }
    
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @XmlTransient  
    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }
    
    @XmlIDREF
    public List<Component> getRefComponents() {
        return refComponents;
    }

    public void addRefComponent(Component refComponent) {
        this.refComponents.add(refComponent);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ConstraintType getType() {
        return type;
    }

    public void setType(ConstraintType type) {
        this.type = type;
    }        
}
