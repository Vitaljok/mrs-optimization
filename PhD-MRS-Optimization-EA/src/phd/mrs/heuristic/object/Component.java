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
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Vitaljok
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"project_id", "code"})})
public class Component implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "code")
    private String code;
    @Column(name = "name")
    private String name;
    @Column(name = "family")
    private String family;
    @Column(name = "investment_costs")
    private Double investmentCosts;
    @Column(name = "operating_power")
    private Double operatingPower;
    @Column(name = "complexity")
    private Double complexity = 1.0;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "component")
    private List<Constraint> constraints = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    @XmlElement
    @XmlJavaTypeAdapter(PropertiesXmlAdapter.class)
    @Transient
    private Properties properties = new Properties();

    public Properties getProperties() {
        return properties;
    }

    protected Component() {
    }

    public Component(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @PrePersist
    private void prePersist() {
        for (Constraint req : constraints) {
            req.setComponent(this);
        }
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @XmlTransient
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlID
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

    public Double getComplexity() {
        return complexity;
    }

    public void setComplexity(Double complexity) {
        this.complexity = complexity;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public Double getInvestmentCosts() {
        return investmentCosts;
    }

    public void setInvestmentCosts(Double investmentCosts) {
        this.investmentCosts = investmentCosts;
    }

    public Double getOperatingPower() {
        return operatingPower;
    }

    public void setOperatingPower(Double operatingPower) {
        this.operatingPower = operatingPower;
    }

    /**
     * Sets operating power per hour
     *
     * @param operatingPowerH
     */
    public void setOperatingPowerH(Double operatingPowerH) {
        this.operatingPower = operatingPowerH / 60 / 60;
    }

    @XmlElement
    public List<Constraint> getConstraints() {
        return constraints;
    }

    public void addConstraint(ConstraintType type, String comment, Component... components) {
        this.constraints.add(new Constraint(this, type, comment, components));
    }

    @Override
    public String toString() {
        return "[" + code + "]";
    }

    //@XmlTransient
    public Double getDoubleProperty(String key) {
        return new Double(this.properties.getProperty(key));
    }
}

@XmlAccessorType(XmlAccessType.FIELD)
class PropElement {

    @XmlElement
    String key;
    @XmlElement
    String value;

    public PropElement(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public PropElement() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

class PropertiesXmlAdapter extends XmlAdapter<PropElement[], Properties> {

    @Override
    public Properties unmarshal(PropElement[] elements) throws Exception {
        Properties prop = new Properties();

        for (PropElement element : elements) {
            prop.put(element.getKey(), element.getValue());
        }

        return prop;
    }

    @Override
    public PropElement[] marshal(Properties prop) throws Exception {
        PropElement[] elements = new PropElement[prop.keySet().size()];

        int i = 0;

        for (Enumeration<Object> en = prop.keys(); en.hasMoreElements();) {
            String key = (String) en.nextElement();
            elements[i++] = new PropElement(key, prop.getProperty(key));
        }

        return elements;
    }
}
