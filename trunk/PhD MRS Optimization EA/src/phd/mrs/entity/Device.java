/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phd.mrs.entity;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import phd.mrs.utils.CachedProperty;
import phd.mrs.utils.Config;

/**
 *
 * @author Vitaljok
 */
@Entity
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany
    private List<Component> components = new ArrayList<Component>();

    protected Device() {
    }

    public Device(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void print() {
        System.out.println("Device #" + this.id);

        for (Component comp : components) {
            System.out.println("\t" + comp.getCode());
        }
    }
    
    @Transient
    CachedProperty<Double> investmentCots = new CachedProperty<Double>() {

        @Override
        protected Double calculateValue() {
            Double price = 0d;

            Double maxComplexity = 0d;

            // sum of components
            for (Component comp : getComponents()) {
                Double compPrice = Double.valueOf(comp.getProperties().getProperty(Config.propInvestmentCosts));
                if (compPrice == null) {
                    throw new RuntimeException(MessageFormat.format("Component '{0}' does not have '{1}' property!", comp.getName(), Config.propInvestmentCosts));
                }

                maxComplexity = Math.max(maxComplexity, Double.valueOf(comp.getProperties().getProperty(Config.propComplexity, "1.0")));

                price += compPrice;
            }

            // producion coefficient
            price *= Config.C_DEVICE_PRODUCTION_LIN * Math.exp(Config.C_DEVICE_PRODUCTION_EXP * getComponents().size());

            // complexity coefficient
            price *= maxComplexity;

            return price;
        }
    };

    public Double getInvestmentCosts() {
        return this.investmentCots.getValue();
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
        if (!(object instanceof Device)) {
            return false;
        }
        Device other = (Device) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "phd.mrs.entity.Device[id=" + id + "]";
    }
}
