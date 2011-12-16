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
package phd.mrs;

import phd.mrs.utils.Config;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import phd.mrs.entity.Component;
import phd.mrs.entity.Device;
import phd.mrs.entity.Project;
import phd.mrs.ga.Island;

/**
 *
 * @author Vitaljok
 */
public class TestMRS {

    Project project;

    public TestMRS() {
        // creating project
        project = new Project(1l, "Test project");
        // defining components
        Component comp1 = new Component(10l, "mobile-base", "Mobile base", null);
        comp1.getProperties().setProperty(Config.propInvestmentCosts, Double.toString(20.00));
        comp1.getProperties().setProperty(Config.propComplexity, Double.toString(0.7));
        comp1.getProperties().setProperty(Config.propCompFamily, "locomotion");
        project.getComponents().add(comp1);

        Component comp2 = new Component(20l, "mowing-machine", "Mowing machine", null);
        comp2.getProperties().setProperty(Config.propInvestmentCosts, Double.toString(15.00));
        comp2.getProperties().setProperty(Config.propCompFamily, "other");
        project.getComponents().add(comp2);

        Component comp3 = new Component(30l, "ir", "IR sensor", null);
        comp3.getProperties().setProperty(Config.propInvestmentCosts, Double.toString(10.00));
        comp3.getProperties().setProperty(Config.propCompFamily, "proximity");
        project.getComponents().add(comp3);

        Component comp4 = new Component(40l, "navigator", "Real time navigator", null);
        comp4.getProperties().setProperty(Config.propInvestmentCosts, Double.toString(50.00));
        comp4.getProperties().setProperty(Config.propCompFamily, "navigator");
        project.getComponents().add(comp4);

        Component comp5 = new Component(50l, "planner", "Planner", null);
        comp5.getProperties().setProperty(Config.propInvestmentCosts, Double.toString(60.00));
        comp5.getProperties().setProperty(Config.propComplexity, Double.toString(1.5));
        comp5.getProperties().setProperty(Config.propCompFamily, "planner");
        project.getComponents().add(comp5);

        Component comp6 = new Component(60l, "transporter", "Grass transporter", null);
        comp6.getProperties().setProperty(Config.propInvestmentCosts, Double.toString(10.00));
        comp6.getProperties().setProperty(Config.propCompFamily, "transporter");
        project.getComponents().add(comp6);
    }

    public void runGA() {
        project.generateDevices();

        List<Island> world = new ArrayList<Island>();

        for (int i = 0; i < Config.NUM_ISLANDS; i++) {
            try {
                Island thread = new Island("Island" + (i + 1), project);
                world.add(thread);
                thread.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void runCosts() {
        Map<Device, Integer> solution = new HashMap<Device, Integer>();

        List<Component> comp = project.getComponents();

        Device dev1 = new Device(1l);
        dev1.getComponents().add(comp.get(0));
        dev1.getComponents().add(comp.get(1));

        //solution.put(null, Integer.SIZE);

        System.out.println(dev1.getInvestmentCosts());
        System.out.println(dev1.getComponents());
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TestMRS mrs = new TestMRS();

        mrs.runCosts();
    }
}
