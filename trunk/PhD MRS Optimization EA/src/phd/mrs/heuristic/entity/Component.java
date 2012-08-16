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
package phd.mrs.heuristic.entity;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Properties;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import phd.mrs.heuristic.utils.Config;

/**
 *
 * @author Vitaljok
 */
@Entity
public class Component implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String code;
    private String name;
    @ManyToOne
    private Component parent;
    @ElementCollection
    @MapKeyColumn(name = "name")
    @Column(name = "value")
    @CollectionTable(name = "component_properties", joinColumns =
    @JoinColumn(name = "component_id"))
    Properties properties = new Properties();

    protected Component() {
    }

    public Component(Long id, String code, String name, Component parent) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.parent = parent;
    }

    public Properties getProperties() {
        return properties;
    }

    public Double getDoubleProperty(String key) {

        try {
            return Double.valueOf(this.getProperties().getProperty(Config.Prop.investmentCosts));
        } catch (Exception ex) {
            throw new RuntimeException(
                    MessageFormat.format("Component \"{0}\" does not have valid \"{1}\" property!",
                    this.getCode(),
                    key));
        }
    }

    public Double getDoubleProperty(String key, Double defaultValue) {
        try {
            return this.getDoubleProperty(key);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Component getParent() {
        return parent;
    }

    public void setParent(Component parent) {
        this.parent = parent;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Component)) {
            return false;
        }
        Component other = (Component) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Component[" + code + "]";
    }
}
