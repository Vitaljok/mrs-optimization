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

import java.io.File;
import java.util.Date;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.Population;
import phd.mrs.heuristic.db.Evolution;
import phd.mrs.heuristic.db.EvolutionPK;
import phd.mrs.heuristic.mission.AreaCoverageMission;
import phd.mrs.heuristic.object.Component;
import phd.mrs.heuristic.object.Project;
import phd.mrs.heuristic.object.Requirement;
import phd.mrs.heuristic.mission.TransportationMission;
import phd.mrs.heuristic.utils.Debug;

/**
 *
 * @author Vitaljok
 */
public class MRSOptimizer {

    Project project;
    EntityManager entityManager;

    private MRSOptimizer() throws InvalidConfigurationException {
        initDefaultProject();
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
        compWiFi.getRequired().add(new Requirement(compMobileBase, "Wi-Fi should be placed on mobile base"));
        compMowingMachine.getRequired().add(new Requirement(compMobileBase, "Mowing machine is useless on stationary agent"));
        compLoader.getRequired().add(new Requirement(compMobileBase, "Loader should be mobile"));
        compLoader.getRequired().add(new Requirement(compLoad, "Loader should know the weight of cargo"));
        compLaser.getRequired().add(new Requirement(compMobileBase, "Laser is useless on stationary device"));
        compGPS.getRequired().add(new Requirement(compMobileBase, "GPS is useless on stationary device"));
        compNavigation.getRequired().add(new Requirement(compWiFi, "Networking is required for controlling navigation"));
        compTaskAllocation.getRequired().add(new Requirement(compWiFi, "Tasks should be sent via net"));
        compMobileBase.getRequired().add(new Requirement(compGPS, "Mobile base requires GPS for navigation"));
        compMobileBase.getRequired().add(new Requirement(compWiFi, "Mobile base requires WiFi"));
        compMobileBase.getRequired().add(new Requirement(compLaser, "Mobile base requires laser for navigation"));


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

        project.getMissions().add(transportationMission);
    }

    /**
     * Creates new MRS Optimizer instance and loads configuration from file
     * @param config XML file to load
     */
    private MRSOptimizer(String config) throws JAXBException, InvalidConfigurationException {
        Debug.log.info("Loading project from XML file: " + config);
        JAXBContext xml = JAXBContext.newInstance(Project.class);
        Unmarshaller unmarsh = xml.createUnmarshaller();
        this.project = (Project) unmarsh.unmarshal(new File(config));

        this.project.configure();
    }

    public void startEvolution() throws InvalidConfigurationException {

        Debug.log.info("Connecting to database");

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PhD_MRS_Optimization_PU");
        entityManager = factory.createEntityManager();


        entityManager.getTransaction().begin();
        phd.mrs.heuristic.db.Process proc = new phd.mrs.heuristic.db.Process();
        proc.setStartTime(new Date());
        entityManager.persist(proc);
        entityManager.getTransaction().commit();

        Integer procId = proc.getId();

        Debug.log.info("Populating world");
        Genotype world = Genotype.randomInitialGenotype(this.project.getGaConfig());


//        world.evolve();        
//        writePopulation(proc, 1, world.getPopulation(),
//                null, entityManager);

        Debug.log.info("Starting evolution");
        int genNum = 0;
        int lastChangeGen = 0;
        double lastFitValue = -1d;

        int step = 1;

        while (genNum < project.config.generationsLimit) {
            world.evolve(step);
            genNum += step;

            IChromosome best = world.getFittestChromosome();
            if (lastFitValue != best.getFitnessValue()) {
                lastChangeGen = genNum;
                lastFitValue = best.getFitnessValue();

//                writePopulation(proc, genNum, world.getPopulation(),
//                best, entityManager);

                entityManager.getTransaction().begin();
                EvolutionPK pk = new EvolutionPK(procId, genNum, (short)0);
                Evolution ev = new Evolution(pk);
                ev.setAge(best.getAge());
                ev.setFitnessValue(best.getFitnessValueDirectly());
                ev.setBestInd(true);
                entityManager.persist(ev);
                entityManager.getTransaction().commit();
                entityManager.clear(); // detach persisted objects to avoid memory leaks
            } else if (step < project.config.generationsStep) {
                step++;
            }

            Debug.log.log(Level.INFO, "{0}\t~{1}\t{2}\tStep: {3}", new Object[]{genNum, lastChangeGen, best.getFitnessValue(), step});
        }

        entityManager.getTransaction().begin();
        proc = entityManager.find(phd.mrs.heuristic.db.Process.class, procId);
        proc.setEndTime(new Date());
        entityManager.getTransaction().commit();

        
        entityManager.close();
        
        Debug.log.info("Showing results");
        new ChromosomeTestFrame(world.getFittestChromosome(), project).setVisible(true);       
    }

    private void writePopulation(phd.mrs.heuristic.db.Process proc, int gen,
            Population pop, IChromosome best, EntityManager em) {

        em.getTransaction().begin();

        for (short i = 0; i < pop.getChromosomes().size(); i++) {
            IChromosome chrom = pop.getChromosome(i);
            EvolutionPK pk = new EvolutionPK(proc.getId(), gen, (short)(i+1));
            Evolution ev = new Evolution(pk);
            ev.setAge(chrom.getAge());
            ev.setFitnessValue(chrom.getFitnessValueDirectly());
            if (chrom.equals(best)) {
                ev.setBestInd(true);
            }

            em.persist(ev);
        }

        em.getTransaction().commit();

        em.clear(); // detach persisted objects to avoid memory leaks
    }

    /**
     * @param args the command line arguments
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

//        try {
//            MRSOptimizer opt = new MRSOptimizer();
//
//            JAXBContext xml = JAXBContext.newInstance(Project.class);
//            Marshaller marsh = xml.createMarshaller();
//            marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//
//            marsh.marshal(opt.project, new File("test.xml"));
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }

        if (args.length > 0) {
            String xmlFileName = args[0];
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
                Debug.log.severe("Error loading project from XML file: " + args[0]);
            } catch (InvalidConfigurationException ex) {
                Debug.log.severe("Error configuring GA");
            }
        } else {
            Debug.log.severe("Project XML file is not specified");
        }

    }
}