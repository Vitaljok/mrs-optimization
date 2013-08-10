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

import phd.mrs.heuristic.object.config.Const;
import java.io.File;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import phd.mrs.heuristic.ga.AgentGene;
import phd.mrs.heuristic.mission.AreaCoverageMission;
import phd.mrs.heuristic.mission.DummyMission;
import phd.mrs.heuristic.mission.GreenhouseInspectionMission;
import phd.mrs.heuristic.mission.GreenhouseSprayingMission;
import phd.mrs.heuristic.mission.GreenhouseTransportingMission;
import phd.mrs.heuristic.object.Component;
import phd.mrs.heuristic.object.Project;
import phd.mrs.heuristic.mission.TransportationMission;
import phd.mrs.heuristic.object.ConstraintType;
import phd.mrs.heuristic.object.Evolution;
import phd.mrs.heuristic.utils.Debug;

/**
 *
 * @author Vitaljok
 */
public class MRSOptimizer {

    Project project;
    

    private MRSOptimizer() throws InvalidConfigurationException {
        //initDefaultProject();
        initGreenhouseProject();
        project.configure();
    }

    private void initDefaultProject() {

        // creating project
        project = new Project();
        project.setName("Grass trimming project");

        // defining components
        Component compMobileBase = new Component("mobile-base", "Mobile base");
        compMobileBase.setInvestmentCosts(60d);
        compMobileBase.setOperatingPower(4d);
        compMobileBase.setFamily("wheeled car-like");
        project.getComponents().add(compMobileBase);

        Component compWiFi = new Component("network-wifi", "Wi-Fi networking");
        compWiFi.setInvestmentCosts(30d);
        compWiFi.setOperatingPower(2d);
        compWiFi.setComplexity(1.1);
        compWiFi.setFamily("Wi-FI");
        project.getComponents().add(compWiFi);

        Component compMowingMachine = new Component("mowing-machine", "Mowing machine");
        compMowingMachine.setInvestmentCosts(40.00);
        compMowingMachine.setOperatingPower(5d);
        compMowingMachine.setFamily("1-DOF manipulator");
        project.getComponents().add(compMowingMachine);

        Component compLoader = new Component("loader", "Loader");
        compLoader.setInvestmentCosts(40.00);
        compLoader.setOperatingPower(4d);
        compLoader.setFamily("End effector");
        project.getComponents().add(compLoader);

        Component compDumper = new Component("dumper", "Dumper");
        compDumper.setInvestmentCosts(20.00);
        compDumper.setOperatingPower(3d);
        compDumper.setFamily("1-DOF manipulator");
        project.getComponents().add(compDumper);

        Component compLaser = new Component("laser", "Laser");
        compLaser.setInvestmentCosts(30.00);
        compLaser.setOperatingPower(2d);
        compLaser.setFamily("Proximity");
        project.getComponents().add(compLaser);

        Component compGPS = new Component("gps", "GPS");
        compGPS.setInvestmentCosts(25.00);
        compGPS.setOperatingPower(1.5d);
        compGPS.setFamily("Position");
        project.getComponents().add(compGPS);

        Component compLoad = new Component("load", "Load");
        compLoad.setInvestmentCosts(20.00);
        compLoad.setOperatingPower(0.5d);
        compLoad.setFamily("Sensing");
        project.getComponents().add(compLoad);

        Component compNavigation = new Component("navigation", "Navigation");
        compNavigation.setInvestmentCosts(50.00);
        compNavigation.setOperatingPower(1d);
        compNavigation.setFamily("Computation");
        compNavigation.setComplexity(1.3);
        project.getComponents().add(compNavigation);

        Component compTaskAllocation = new Component("task-allocation", "Task allocation");
        compTaskAllocation.setInvestmentCosts(50.00);
        compTaskAllocation.setOperatingPower(1d);
        compTaskAllocation.setFamily("Computation");
        compTaskAllocation.setComplexity(1.2);
        project.getComponents().add(compTaskAllocation);


        // component requirements                       
//        compMowingMachine.addRequired(compMobileBase, "Mowing machine is useless on stationary agent");
//        compLoader.addRequired(compMobileBase, "Loader should be mobile");
//        compLoader.addRequired(compLoad, "Loader should know the weight of cargo");
//        compLaser.addRequired(compMobileBase, "Laser is useless on stationary device");
//        compGPS.addRequired(compMobileBase, "GPS is useless on stationary device");
//        compNavigation.addRequired(compWiFi, "Networking is required for controlling navigation");
//        compTaskAllocation.addRequired(compWiFi, "Tasks should be sent via net");
//        compMobileBase.addRequired(compWiFi, "Mobile base requires WiFi for receiving control commands");

        // additional
//        compMobileBase.addRequired(compGPS, "Mobile base requires GPS for navigation");
//        compMobileBase.addRequired(compLaser, "Mobile base requires laser for navigation");
//        compWiFi.addRequired(compMobileBase, "Wi-Fi should be placed on mobile base");


        // Missions
        AreaCoverageMission areaCoverageMission = new AreaCoverageMission(120d, 150d);
        areaCoverageMission.setWorkDensity(0.9);
        areaCoverageMission.setMobileBase(compMobileBase);
        areaCoverageMission.setMobileBaseSpeed(2d); // moves 2m/s
        areaCoverageMission.setWorkDevice(compMowingMachine);
        areaCoverageMission.setWorkDeviceWidth(1.2); // trims 1.2 m wide area        

        project.getMissions().add(areaCoverageMission);

        TransportationMission transportationMission = new TransportationMission(120d, 150d);
        transportationMission.setWorkDensity(0.04); // package should be collected after every 25 units of area coverage mission
        transportationMission.setTargetOffsetX(20d);
        transportationMission.setTargetOffsetY(10d);
        transportationMission.setMobileBase(compMobileBase);
        transportationMission.setMobileBaseSpeed(8d);
        transportationMission.setLoader(compLoader);

        project.getMissions().add(transportationMission);
    }

    private void initGreenhouseProject() {
        // creating project
        project = new Project();
        project.setName("Greenhouse project");

        // defining components

        Component compStorage = new Component("storage", "Pesticide storage");
        compStorage.setInvestmentCosts(500d);
        compStorage.setOperatingPowerH(0.00275d);
        compStorage.setFamily(Const.familyStorage);
        project.getComponents().add(compStorage);

        Component compRangerLaser = new Component("ranger-laser", "Ranger - laser");
        compRangerLaser.setInvestmentCosts(800d);
        compRangerLaser.setOperatingPowerH(0.00022d);
        compRangerLaser.setFamily(Const.familyRanger);
        compRangerLaser.getProperties().setProperty(Const.propTolerance, new Double(0.05).toString());
        compRangerLaser.getProperties().setProperty(Const.propSampleRate, new Double(100).toString());               
        project.getComponents().add(compRangerLaser);

        Component compRangerRadio = new Component("ranger-radio", "Ranger - radio");
        compRangerRadio.setInvestmentCosts(100d);
        compRangerRadio.setOperatingPowerH(0.00077d);
        compRangerRadio.setFamily(Const.familyRanger);
        compRangerRadio.getProperties().setProperty(Const.propTolerance, new Double(0.5).toString());
        compRangerRadio.getProperties().setProperty(Const.propSampleRate, new Double(100).toString());
        project.getComponents().add(compRangerRadio);

        Component compRangerOptical = new Component("ranger-visual", "Ranger - visual");
        compRangerOptical.setInvestmentCosts(50d);
        compRangerOptical.setOperatingPowerH(0.00022d);
        compRangerOptical.setFamily(Const.familyRanger);
        compRangerOptical.getProperties().setProperty(Const.propTolerance, new Double(0.2).toString());
        compRangerOptical.getProperties().setProperty(Const.propSampleRate, new Double(3).toString());
        project.getComponents().add(compRangerOptical);

        Component compCamera = new Component("camera", "HD Camera");
        compCamera.setInvestmentCosts(70d);
        compCamera.setOperatingPowerH(0.00022d);
        compCamera.setFamily(Const.familyCamera);
        compCamera.setComplexity(1.2);
        project.getComponents().add(compCamera);

        Component compPlatformLight = new Component("platform-light", "Light robot platform");
        compPlatformLight.setInvestmentCosts(4000d);
        compPlatformLight.setOperatingPowerH(0.055d);
        compPlatformLight.setFamily(Const.familyPlatform);
        compPlatformLight.getProperties().setProperty(Const.propMaxSpeed, new Double(3.0).toString());
        compPlatformLight.getProperties().setProperty(Const.propMaxWeight, new Double(10.0).toString());
        compPlatformLight.getProperties().setProperty(Const.propApproachDist, new Double(0.2).toString());
        project.getComponents().add(compPlatformLight);

        Component compPlatformHeavy = new Component("platform-heavy", "Heavy robot platform");
        compPlatformHeavy.setInvestmentCosts(7000d);
        compPlatformHeavy.setOperatingPowerH(0.099d);
        compPlatformHeavy.setFamily(Const.familyPlatform);
        compPlatformHeavy.getProperties().setProperty(Const.propMaxSpeed, new Double(1.0).toString());
        compPlatformHeavy.getProperties().setProperty(Const.propMaxWeight, new Double(60.0).toString());
        compPlatformHeavy.getProperties().setProperty(Const.propApproachDist, new Double(0.4).toString());
        project.getComponents().add(compPlatformHeavy);

        Component compSpray = new Component("sprayer", "Pesticide sprayer");
        compSpray.setInvestmentCosts(50d);
        compSpray.setOperatingPowerH(0.00055d);
        compSpray.setFamily(Const.familySpray);
        project.getComponents().add(compSpray);

        Component compTank = new Component("tank", "Tank for chemicals");
        compTank.setInvestmentCosts(100d);
        compTank.setOperatingPowerH(0.00055d);
        compTank.setFamily(Const.familyTank);
        project.getComponents().add(compTank);

        // component constraints     
        compStorage.addConstraint(ConstraintType.Exclude, "Storage should be stationary", compPlatformHeavy, compPlatformLight);        
        compPlatformHeavy.addConstraint(ConstraintType.Exclude, "Only one platform is needed", compPlatformLight);
        compPlatformLight.addConstraint(ConstraintType.Exclude, "Only one platform is needed", compPlatformHeavy);
        compRangerLaser.addConstraint(ConstraintType.Exclude, "Only one ranger is needed", compRangerOptical, compRangerRadio);
        compRangerOptical.addConstraint(ConstraintType.Exclude, "Only one ranger is needed", compRangerLaser, compRangerRadio);
        compRangerRadio.addConstraint(ConstraintType.Exclude, "Only one ranger is needed", compRangerOptical, compRangerLaser);
        compCamera.addConstraint(ConstraintType.DependAny, "Camera should be mobile", compPlatformHeavy, compPlatformLight);
        compRangerLaser.addConstraint(ConstraintType.DependAny, "Ranger should be mobile", compPlatformHeavy, compPlatformLight);
        compRangerOptical.addConstraint(ConstraintType.DependAny, "Ranger should be mobile", compPlatformHeavy, compPlatformLight);
        compRangerRadio.addConstraint(ConstraintType.DependAny, "Ranger should be mobile", compPlatformHeavy, compPlatformLight);
        compSpray.addConstraint(ConstraintType.DependAny, "Spray should be mobile", compPlatformHeavy, compPlatformLight);
        compTank.addConstraint(ConstraintType.DependAny, "Tank should be mobile", compPlatformHeavy, compPlatformLight);


        // Missions
        int rows = 80;
        int plants = 150;
        int cicles = 1000;
        GreenhouseInspectionMission inspectionMission = new GreenhouseInspectionMission(rows, plants, cicles);
        inspectionMission.setInspectionTime(1d);
        inspectionMission.setRequiredTolerance(0.5);
        inspectionMission.setIntervalPlants(1d);
        this.project.getMissions().add(inspectionMission);

        GreenhouseSprayingMission sprayingMission = new GreenhouseSprayingMission(rows, plants, cicles);
        sprayingMission.setRequiredTolerance(0.2);
        sprayingMission.setWorkDensity(0.4); // 40% of plants to spray
        sprayingMission.setMaxAllowedSpeed(1.5);
        sprayingMission.setSprayingTime(10d);
        sprayingMission.setIntervalPlants(1d);
        this.project.getMissions().add(sprayingMission);

        GreenhouseTransportingMission transportingMission = new GreenhouseTransportingMission(rows, plants, cicles);
        transportingMission.setWorkDensity(0.4); // 40% of plants to spray
        transportingMission.setVolumeConsumption(0.2); // 0.2 l per plant
        transportingMission.setVolumeTransferSpeed(0.1); // 0.1 l per sec
        transportingMission.setMaxAllowedSpeed(2d);
        transportingMission.setDistanceToStorage(50d); // 50 meters from storage to greenhouse
        transportingMission.setIntervalPlants(1d);
        transportingMission.setIntervalRows(1.5d);        
        this.project.getMissions().add(transportingMission);

        this.project.getMissions().add(new DummyMission(Const.familyStorage));
    }

    /**
     * Creates new MRS Optimizer instance and loads configuration from file
     *
     * @param config XML file to load
     */
    private MRSOptimizer(String config) throws JAXBException, InvalidConfigurationException {
        Debug.log.info("Loading project from XML file: " + config);
        JAXBContext xml = JAXBContext.newInstance(Project.class);
        Unmarshaller unmarsh = xml.createUnmarshaller();
        this.project = (Project) unmarsh.unmarshal(new File(config));

        if (this.project.getMissions().size() == 0) {
            throw new Error("No missions loaded");
        }

        this.project.configure();
    }

    public void startEvolution() throws InvalidConfigurationException {

        Debug.log.info("Connecting to database");

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PhD_MRS_Optimization_PU");

        EntityManager projectEm = factory.createEntityManager();

        // store project definition
        projectEm.getTransaction().begin();
        projectEm.persist(this.project);
        projectEm.getTransaction().commit();


        EntityManager evolutionEm = factory.createEntityManager();

        Debug.log.info("Populating world");
        Genotype world = Genotype.randomInitialGenotype(this.project.getGaConfig());

        Debug.log.info("Starting evolution (" + project.getConfig().getGenerationsLimit() + " generations)");
        int genNum = 0;
        int lastChangeGen = 0;
        double lastFitValue = -1d;

        int step = 1;

        while (genNum < project.getConfig().getGenerationsLimit()) {
            world.evolve(step);
            genNum += step;

            IChromosome best = world.getFittestChromosome();
            if (lastFitValue != best.getFitnessValue()) {
                lastChangeGen = genNum;
                lastFitValue = best.getFitnessValue();

                evolutionEm.getTransaction().begin();
                Evolution evo = new Evolution();
                evo.setFitnessValue(lastFitValue);
                evo.setGeneration(genNum);
                evo.setProject(project);

                for (Gene gene : best.getGenes()) {
                    AgentGene agentGene = (AgentGene) gene;
                    Integer inst_num = (Integer) agentGene.getAllele();
                    if (inst_num > 0) {
                        evo.getAgents().put(agentGene.getAgent(), inst_num);
                    }
                }

                evolutionEm.persist(evo);
                evolutionEm.getTransaction().commit();
                evolutionEm.clear(); // detach persisted objects to avoid memory leaks
            } else if (step < project.getConfig().getGenerationsStep()) {
                step++;
            }

            Debug.log.info(genNum + " +" + step + "\t~" + lastChangeGen + "\t" + best.getFitnessValue());
        }

        // save end time
        projectEm.getTransaction().begin();
        //projectEm.refresh(this.project);
        this.project.setEndTime(new Date());
        projectEm.getTransaction().commit();
        projectEm.close();

        Debug.log.info("Showing results");
        new ChromosomeTestFrame(world.getFittestChromosome(), project).setVisible(true);
    }

    /**
     * @param args the command line arguments: -config file.xml -test
     */
    public static void main(String[] args) {

        System.setProperty("java.util.logging.config.file", "logging.properties");

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            Debug.log.warning("Error loading \"Nimbus\" look and feel.");
        }

        if (args.length > 0) {

            switch (args[0]) {
                case "-test":
                    try {
                        MRSOptimizer opt = new MRSOptimizer();

                        JAXBContext xml = JAXBContext.newInstance(Project.class);
                        Marshaller marsh = xml.createMarshaller();
                        marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                        marsh.marshal(opt.project, new File("test.xml"));
                        Debug.log.info("XML file created!");

//                        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PhD_MRS_Optimization_PU");
//                        EntityManager em = factory.createEntityManager();
//                        em.getTransaction().begin();
//                        em.persist(opt.project);
//                        em.getTransaction().commit();
//                        em.close();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    if (args.length > 1 && args[1].equals("-exec")) {
                        args[1] = "test.xml";
                    } else {
                        break;
                    }

                case "-config":
                    if (args.length < 2) {
                        System.out.println("Config file is not specified.");
                        break;
                    }
                    String xmlFileName = args[1];
                    MRSOptimizer optimizer;
                    try {
                        optimizer = new MRSOptimizer(xmlFileName);

                        try {
                            optimizer.startEvolution();
                        } catch (InvalidConfigurationException e) {
                            Debug.log.severe("Error running GA");
                            e.printStackTrace();
                        }

                    } catch (JAXBException ex) {
                        Debug.log.severe("Error loading project from XML file: " + args[1]);
                        ex.printStackTrace();
                    } catch (InvalidConfigurationException ex) {
                        Debug.log.severe("Error configuring GA");
                    }
                    break;
                default:
                    System.out.println("Invalid parameter: " + args[0]);
            }

        } else {
            System.out.println("No parameters specified");
            System.out.println("Usage: ");
            System.out.println(" -config [file.xml]");
            System.out.println("\tloads configuration from [file.xml] and executes GA.");
            System.out.println(" -test <exec>");
            System.out.println("\tcreates text.xml file using default configuration");
            System.out.println("\texecutes GA if second param is specified");

        }
    }
}
