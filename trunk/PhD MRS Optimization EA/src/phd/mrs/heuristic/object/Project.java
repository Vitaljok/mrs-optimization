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
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
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
@XmlRootElement
@XmlSeeAlso({AreaCoverageMission.class, TransportationMission.class})
public class Project implements Serializable {

    private String name;
    @XmlElementWrapper(name = "components")
    @XmlElement(name = "component")
    private List<Component> components = new ArrayList<>();
    @XmlElementWrapper(name = "missions")
    @XmlElementRef
    private List<Mission> missions = new ArrayList<>();
    @XmlElement
    public Config config = new Config();
    @XmlElement
    public CostModel costModel = new CostModel();
    // private
    private List<Agent> agents = new ArrayList<>();
    private Configuration gaConfig;

    public Project() {
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

    public List<Mission> getMissions() {
        return missions;
    }

    public List<Agent> getAgents() {
        return agents;
    }

    private boolean isAgentValid(Agent agent) {
        for (Component comp : agent.getComponents()) {
            List<Requirement> refList = comp.getRequired();

            if (!refList.isEmpty()) {
                for (Requirement ref : refList) {
                    if (!agent.getComponents().contains(ref.getComponent())) {
                        return false;
                    }
                }

            }
        }

        return true;
    }

    private Integer generateAgents() {
        Integer compNum = this.getComponents().size();
        Debug.log.config("Generating agents: " + (1 << compNum));

        int inv = 0;

        for (int i = 1; i <= (1 << compNum); i++) {
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

        this.gaConfig.setMinimumPopSizePercent(this.config.minimumPopSizePercent);
        this.gaConfig.setSelectFromPrevGen(this.config.selectFromPrevGen);
        this.gaConfig.setKeepPopulationSizeConstant(this.config.keepPopulationSizeConstant);
        this.gaConfig.setChromosomePool(new ChromosomePool());
        this.gaConfig.setPopulationSize(this.config.populationSize);

        // fitness function
        this.gaConfig.setFitnessEvaluator(new InverseFitnessEvaluator());
        this.gaConfig.setFitnessFunction(new MrsTotalCostFitnessFunction(this));

        // genetic operations            
        BestChromosomesSelector bestChromsSelector = new BestChromosomesSelector(
                this.gaConfig, this.config.selectorOriginalRate);
        bestChromsSelector.setDoubletteChromosomesAllowed(this.config.doubletteChromosomesAllowed);
        this.gaConfig.addNaturalSelector(bestChromsSelector, false);
        this.gaConfig.addGeneticOperator(new CrossoverOperator(this.gaConfig, this.config.crossoverRate));
        this.gaConfig.addGeneticOperator(new MutationOperator(this.gaConfig, this.config.mutationRate));


        // sample chromosome
        List<AgentGene> genes = new ArrayList<>();
        for (Agent agent : this.agents) {

            AgentGene gene = new AgentGene(this.gaConfig, 0, this.config.agentInstanceLimit, agent);
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

    public void setGaConfig(Configuration gaConfig) {
        this.gaConfig = gaConfig;
    }
}
