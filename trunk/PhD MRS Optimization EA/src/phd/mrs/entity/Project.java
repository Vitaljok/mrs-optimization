/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phd.mrs.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Vitaljok
 */
@Entity
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    @OneToMany
    private List<Component> components = new ArrayList<Component>();
    @OneToMany
    private List<Device> devices = new ArrayList<Device>();

    protected Project() {
    }

    public Project(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Component> getComponents() {
        return components;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public Integer generateDevices() {

        Integer compNum = this.getComponents().size();

        for (int i = 1; i <= Math.pow(2, compNum); i++) {
            String pattern = String.format("%" + compNum + "s",
                    Integer.toBinaryString(i)).replaceAll("\\s", "0");

            Device device = new Device(i * 1l);

            for (int j = 0; j < compNum; j++) {
                if (pattern.charAt(compNum - j - 1) == '1') {
                    device.getComponents().add(this.getComponents().get(j));
                }
            }
            this.getDevices().add(device);
        }

        return 0;
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
        if (!(object instanceof Project)) {
            return false;
        }
        Project other = (Project) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "phd.mrs.entity.Project[id=" + id + "]";
    }
}