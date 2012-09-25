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

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Vitaljok
 */
@XmlRootElement
public class Component {

    private String id;
    private String name;
    private String family;
    private Double investmentCosts;
    private Double operatingPower;
    private Double complexity = 1.0;
    
    private List<Requirement> required = new ArrayList<>();

    protected Component() {
    }

    public Component(String id, String name) {
        this.id = id;
        this.name = name;                
    }   
    
    @XmlID
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @XmlElement
    public List<Requirement> getRequired() {
        return required;
    }
    

    @Override
    public String toString() {
        return "[" + id + "]";
    }
}
