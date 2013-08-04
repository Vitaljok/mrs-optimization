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
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.InvalidConfigurationException;
import org.jgap.event.EventManager;
import org.jgap.impl.BestChromosomesSelector;
import org.jgap.impl.ChromosomePool;
import org.jgap.impl.CrossoverOperator;
import org.jgap.impl.GABreeder;
import org.jgap.impl.MutationOperator;
import org.jgap.impl.StockRandomGenerator;
import phd.mrs.heuristic.mission.AreaCoverageMission;
import phd.mrs.heuristic.mission.Mission;
import phd.mrs.heuristic.mission.TransportationMission;
import phd.mrs.heuristic.object.config.Config;
import phd.mrs.heuristic.object.config.CostModel;
import phd.mrs.heuristic.ga.AgentGene;
import phd.mrs.heuristic.ga.InverseFitnessEvaluator;
import phd.mrs.heuristic.ga.fitness.MrsTotalCostFitnessFunction;
import phd.mrs.heuristic.utils.Debug;

/**
 *
 * @author Vitaljok
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Project.findAll", query = "SELECT p FROM Project p")})
@XmlRootElement
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Short id;
    @Column(name = "name")
    private String name;
    @Column(name = "start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Column(name = "end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Component> components = new ArrayList<>();
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Mission> missions = new ArrayList<>();
    @Embedded
    private Config config = new Config();
    @Embedded
    private CostModel costModel = new CostModel();
    // private
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Agent> agents = new ArrayList<>();
    @Transient
    private Configuration gaConfig;
    @Transient
    private List<ComponentGroup> groups = new ArrayList<>();

    public Project() {
        this.startTime = new Date();
    }

    @PrePersist
    private void prePersist() {
        for (Component comp : components) {
            comp.setProject(this);
        }

        for (Mission mis : missions) {
            mis.setProject(this);
        }
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElementWrapper(name = "components")
    @XmlElement(name = "component")
    public List<Component> getComponents() {
        return components;
    }

    @XmlElementRefs({
        @XmlElementRef(type = AreaCoverageMission.class),
        @XmlElementRef(type = TransportationMission.class)
    })
    @XmlElementWrapper(name = "missions")
    public List<Mission> getMissions() {
        return missions;
    }

    @XmlElementRefs({
        @XmlElementRef(type = RequiredGroup.class)
    })
    @XmlElementWrapper(name = "groups")
    public List<ComponentGroup> getGroups() {
        return groups;
    }

    public List<Agent> getAgents() {
        return agents;
    }

    private boolean isAgentValid(Agent agent) {
        for (Component comp : agent.getComponents()) {
            List<Constraint> constrList = comp.getConstraints();

            if (!constrList.isEmpty()) {
                for (Constraint constr : constrList) {

                    if (constr.getType() == ConstraintType.Exclude) {
                        // if agent contains of exclusive componennts
                        for (Component refComp : constr.getRefComponents()) {                            
                            if (agent.getComponents().contains(refComp)) {
                                //System.out.println("Excluded component: "+refComp);
                                return false;
                            }
                        }

                    } else if (constr.getType() == ConstraintType.DependAny){
                        // if agent does not have all dependent component                                               
                        
                        if (Collections.disjoint(agent.getComponents(), constr.getRefComponents())) {
                            //System.out.println("None of required components: "+constr.getRefComponents());
                            return false;
                        }                        
                    }

                }

            }
        }
        
        //System.out.println("OK");
        return true;
    }

    private Integer generateAgents() {
        Integer compNum = this.getComponents().size();
        Debug.log.config("Generating agents: " + (1 << compNum));

        int inv = 0;

        for (int i = 1; i < (1 << compNum); i++) {
            String pattern = String.format("%" + compNum + "s",
                    Integer.toBinaryString(i)).replaceAll("\\s", "0");
          
            Agent agent = new Agent(this);

            for (int j = 0; j < compNum; j++) {
                if (pattern.charAt(compNum - j - 1) == '1') {
                    agent.getComponents().add(this.getComponents().get(j));
                }
            }

            

            if (this.isAgentValid(agent)) {
                this.getAgents().add(agent);
                //System.out.println(agent.getComponents());
            } else {
                inv++;
            }
        }

        Debug.log.config("Skipped invalid agents: " + inv);

        return 0;
    }

    public void configure() throws InvalidConfigurationException {
        Debug.log.info("Configuring GA");
        this.generateAgents();

        this.gaConfig = new Configuration("mrsGA", "MRS GA optimization");

        this.gaConfig.setBreeder(new GABreeder());
        this.gaConfig.setRandomGenerator(new StockRandomGenerator());
        this.gaConfig.setEventManager(new EventManager());

        this.gaConfig.setMinimumPopSizePercent(this.getConfig().getMinimumPopSizePercent());
        this.gaConfig.setSelectFromPrevGen(this.getConfig().getSelectFromPrevGen());
        this.gaConfig.setKeepPopulationSizeConstant(this.getConfig().isKeepPopulationSizeConstant());
        this.gaConfig.setChromosomePool(new ChromosomePool());
        this.gaConfig.setPopulationSize(this.getConfig().getPopulationSize());

        // fitness function
        this.gaConfig.setFitnessEvaluator(new InverseFitnessEvaluator());
        this.gaConfig.setFitnessFunction(new MrsTotalCostFitnessFunction(this));

        // genetic operations            
        BestChromosomesSelector bestChromsSelector = new BestChromosomesSelector(
                this.gaConfig, this.getConfig().getSelectorOriginalRate());
        bestChromsSelector.setDoubletteChromosomesAllowed(this.getConfig().isDoubletteChromosomesAllowed());
        this.gaConfig.addNaturalSelector(bestChromsSelector, false);
        this.gaConfig.addGeneticOperator(new CrossoverOperator(this.gaConfig, this.getConfig().getCrossoverRate()));
        this.gaConfig.addGeneticOperator(new MutationOperator(this.gaConfig, this.getConfig().getMutationRate()));


        // sample chromosome
        List<AgentGene> genes = new ArrayList<>();
        for (Agent agent : this.agents) {

            AgentGene gene = new AgentGene(this.gaConfig, 0, this.getConfig().getAgentInstanceLimit(), agent);
            gene.setAllele(new Integer(0));

            genes.add(gene);
        }

        Chromosome sampleChromosome = new Chromosome(this.gaConfig, genes.toArray(new AgentGene[0]));

        this.gaConfig.setSampleChromosome(sampleChromosome);

    }

    @XmlTransient
    public Configuration getGaConfig() {
        return gaConfig;
    }

    @XmlTransient
    public Short getId() {
        return id;
    }

    @XmlElement
    public Config getConfig() {
        return config;
    }

    @XmlElement
    public CostModel getCostModel() {
        return costModel;
    }

    @XmlTransient
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @XmlTransient
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public void setCostModel(CostModel costModel) {
        this.costModel = costModel;
    }
}
