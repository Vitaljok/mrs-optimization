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
package phd.mrs.heuristic;

import java.util.ArrayList;
import java.util.List;
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
import phd.mrs.heuristic.entity.Agent;
import phd.mrs.heuristic.mission.AreaCoverageMission;
import phd.mrs.heuristic.entity.Component;
import phd.mrs.heuristic.entity.Project;
import phd.mrs.heuristic.ga.AgentGene;
import phd.mrs.heuristic.ga.InverseFitnessEvaluator;
import phd.mrs.heuristic.ga.Island;
import phd.mrs.heuristic.ga.fitness.MrsTotalCostFitnessFunction;
import phd.mrs.heuristic.utils.Config;
import phd.mrs.heuristic.utils.Debug;

/**
 *
 * @author Vitaljok
 */
public class MRSOptimizer {

    Project project;
    Configuration configuration;

    public MRSOptimizer() {

        Debug.log.info("Setting up optimizer");
        // creating project
        project = new Project("Grass trimming project");

        // defining components
        Component compMobileBase = new Component("mobile-base", "Mobile base", null);
        compMobileBase.getProperties().setProperty(Config.Prop.investmentCosts, Double.toString(60d));
        compMobileBase.getProperties().setProperty(Config.Prop.operatingPower, Double.toString(4d));
        compMobileBase.getProperties().setProperty(Config.Prop.compFamily, "wheeled car-like");
        project.getComponents().add(compMobileBase);

        Component compWiFi = new Component("network-wifi", "Wi-Fi networking", null);
        compWiFi.getProperties().setProperty(Config.Prop.investmentCosts, Double.toString(30d));
        compWiFi.getProperties().setProperty(Config.Prop.operatingPower, Double.toString(2d));
        compWiFi.getProperties().setProperty(Config.Prop.complexity, Double.toString(1.1));
        compWiFi.getProperties().setProperty(Config.Prop.compFamily, "Wi-FI");
        project.getComponents().add(compWiFi);

        Component compMowingMachine = new Component("mowing-machine", "Mowing machine", null);
        compMowingMachine.getProperties().setProperty(Config.Prop.investmentCosts, Double.toString(40.00));
        compMowingMachine.getProperties().setProperty(Config.Prop.operatingPower, Double.toString(5d));
        compMowingMachine.getProperties().setProperty(Config.Prop.compFamily, "1-DOF manipulator");
        compMowingMachine.getProperties().setProperty(Config.Prop.workDeviceWidth, Double.toString(1d));
        project.getComponents().add(compMowingMachine);

        Component compLoader = new Component("loader", "Loader", null);
        compLoader.getProperties().setProperty(Config.Prop.investmentCosts, Double.toString(40.00));
        compLoader.getProperties().setProperty(Config.Prop.operatingPower, Double.toString(4));
        compLoader.getProperties().setProperty(Config.Prop.compFamily, "End effector");
        project.getComponents().add(compLoader);

        Component compDumper = new Component("dumper", "Dumper", null);
        compDumper.getProperties().setProperty(Config.Prop.investmentCosts, Double.toString(20.00));
        compDumper.getProperties().setProperty(Config.Prop.operatingPower, Double.toString(3d));
        compDumper.getProperties().setProperty(Config.Prop.compFamily, "1-DOF manipulator");
        project.getComponents().add(compDumper);

        Component compLaser = new Component("laser", "Laser", null);
        compLaser.getProperties().setProperty(Config.Prop.investmentCosts, Double.toString(30.00));
        compLaser.getProperties().setProperty(Config.Prop.operatingPower, Double.toString(2d));
        compLaser.getProperties().setProperty(Config.Prop.compFamily, "Proximity");
        project.getComponents().add(compLaser);

        Component compGPS = new Component("gps", "GPS", null);
        compGPS.getProperties().setProperty(Config.Prop.investmentCosts, Double.toString(25.00));
        compGPS.getProperties().setProperty(Config.Prop.operatingPower, Double.toString(1.5d));
        compGPS.getProperties().setProperty(Config.Prop.compFamily, "Position");
        project.getComponents().add(compGPS);

        Component compLoad = new Component("load", "Load", null);
        compLoad.getProperties().setProperty(Config.Prop.investmentCosts, Double.toString(20.00));
        compLoad.getProperties().setProperty(Config.Prop.operatingPower, Double.toString(0.5d));
        compLoad.getProperties().setProperty(Config.Prop.compFamily, "Position");
        project.getComponents().add(compLoad);

        Component compNavigation = new Component("navigation", "Navigation", null);
        compNavigation.getProperties().setProperty(Config.Prop.investmentCosts, Double.toString(50.00));
        compNavigation.getProperties().setProperty(Config.Prop.operatingPower, Double.toString(1d));
        compNavigation.getProperties().setProperty(Config.Prop.compFamily, "Computation");
        compNavigation.getProperties().setProperty(Config.Prop.complexity, Double.toString(1.3));
        project.getComponents().add(compNavigation);

        Component compTaskAllocation = new Component("task-allocation", "Task allocation", null);
        compTaskAllocation.getProperties().setProperty(Config.Prop.investmentCosts, Double.toString(50.00));
        compTaskAllocation.getProperties().setProperty(Config.Prop.operatingPower, Double.toString(1d));
        compTaskAllocation.getProperties().setProperty(Config.Prop.compFamily, "Computation");
        compTaskAllocation.getProperties().setProperty(Config.Prop.complexity, Double.toString(1.2));
        project.getComponents().add(compTaskAllocation);
        
        // component requirements               
        project.addReq(compWiFi, compMobileBase); // Wi-Fi should be placed on mobile base
        project.addReq(compMowingMachine, compMobileBase); // Mowing machine is useless on stationary agent
        project.addReq(compLoader, compMobileBase); // Loader should be mobile
        project.addReq(compLoader, compLoad); // Loader should know the weight of cargo
        project.addReq(compLaser, compMobileBase); // laser is useless on stationary device
        project.addReq(compGPS, compMobileBase); // GPS is useless on stationary device
        project.addReq(compNavigation, compWiFi); // Networking is required for controlling navigation
        project.addReq(compTaskAllocation, compWiFi); // Tasks should be sent via net
        //reverse requirements
        project.addReq(compMobileBase, compGPS); // Mobile base requires GPS for navigation
        project.addReq(compMobileBase, compWiFi); // Mobile base requires WiFi
        project.addReq(compMobileBase, compLaser); // Mobile base requires laser for navigation
                      
        // Missions
        AreaCoverageMission areaCoverageMission = new AreaCoverageMission(120d, 150d, 0.7);        
        areaCoverageMission.setMobileBase(compMobileBase, 2d); // moves 2m/s
        areaCoverageMission.setWorkDevice(compMowingMachine, 1.2); // trims 1.2 m wide area

        project.getMissions().add(areaCoverageMission);        
    }

    public void execute() {

        this.project.generateAgents();

        try {
            Debug.log.info("Creating configuration");
            this.configuration = new Configuration("mrs", "MRS optimization");

            this.configuration.setBreeder(new GABreeder());
            this.configuration.setRandomGenerator(new StockRandomGenerator());
            this.configuration.setEventManager(new EventManager());

            this.configuration.setMinimumPopSizePercent(0);
            this.configuration.setSelectFromPrevGen(0.95d);
            this.configuration.setKeepPopulationSizeConstant(true);
            this.configuration.setChromosomePool(new ChromosomePool());
            this.configuration.setPopulationSize(Config.POPULATION_SIZE);

            // fitness function
            this.configuration.setFitnessEvaluator(new InverseFitnessEvaluator());
            this.configuration.setFitnessFunction(new MrsTotalCostFitnessFunction(project));

            // genetic operations            
            BestChromosomesSelector bestChromsSelector = new BestChromosomesSelector(
                    this.configuration, 0.90d);
            bestChromsSelector.setDoubletteChromosomesAllowed(true);
            this.configuration.addNaturalSelector(bestChromsSelector, false);
            this.configuration.addGeneticOperator(new CrossoverOperator(this.configuration, 0.35d));
            this.configuration.addGeneticOperator(new MutationOperator(this.configuration, 15));


            // sample chromosome
            List<AgentGene> genes = new ArrayList<AgentGene>();
            for (Agent agent : project.getAgents()) {

                AgentGene gene = new AgentGene(this.configuration, 0, Config.DEVICE_LIMIT, agent);
                gene.setAllele(new Integer(0));
                
                genes.add(gene);
            }

            Chromosome sampleChromosome = new Chromosome(this.configuration, genes.toArray(new AgentGene[0]));
            this.configuration.setSampleChromosome(sampleChromosome);
        } catch (InvalidConfigurationException ex) {
            Debug.log.severe("Error creating configuration");
            ex.printStackTrace();
        }

//        List<Island> world = new ArrayList<Island>();
//
//        for (int i = 0; i < Config.NUM_ISLANDS; i++) {
//            try {
//
//                Island thread = new Island("Island" + (i + 1), configuration);
//                world.add(thread);
//            } catch (InvalidConfigurationException ex) {
//                Debug.log.severe("Error creating island");
//                ex.printStackTrace();
//            }
//        }
//
//        for (Island island : world) {
//            island.start();
//        }


        try {
            Island island = new Island("IslandA", configuration);
            island.run();

        } catch (InvalidConfigurationException ex) {
            Debug.log.severe("Error creating island");
            ex.printStackTrace();
        }

        //new ChromosomeTestFrame(configuration, configuration.getSampleChromosome()).setVisible(true);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
        
        MRSOptimizer optimizer = new MRSOptimizer();
        optimizer.execute();
    }
}
