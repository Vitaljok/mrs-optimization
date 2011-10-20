/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phd.mrs.ga;

import phd.mrs.utils.Config;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import phd.mrs.entity.Component;
import phd.mrs.entity.Project;

/**
 *
 * @author Vitaljok
 */
public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // creating project
        Project project = new Project(1l, "Test project");
        // defining components
        Component comp1 = new Component(10l, "base", "Mobile base", null);
        comp1.getProperties().setProperty(Config.propStatcPrice, Double.toString(20.00));
        comp1.getProperties().setProperty(Config.propComplexity, Double.toString(0.7));
        project.getComponents().add(comp1);

        Component comp2 = new Component(20l, "mowing-machine", "Mowing machine", null);
        comp2.getProperties().setProperty(Config.propStatcPrice, Double.toString(15.00));
        project.getComponents().add(comp2);

        Component comp3 = new Component(30l, "ir", "IR sensor", null);
        comp3.getProperties().setProperty(Config.propStatcPrice, Double.toString(10.00));
        project.getComponents().add(comp3);

        Component comp4 = new Component(40l, "navigator", "Real time navigator", null);
        comp4.getProperties().setProperty(Config.propStatcPrice, Double.toString(50.00));
        project.getComponents().add(comp4);

        Component comp5 = new Component(50l, "planner", "Planner", null);
        comp5.getProperties().setProperty(Config.propStatcPrice, Double.toString(60.00));
        comp5.getProperties().setProperty(Config.propComplexity, Double.toString(1.5));
        project.getComponents().add(comp5);
        
        Component comp6 = new Component(60l, "transporter", "Grass transporter", null);
        comp6.getProperties().setProperty(Config.propStatcPrice, Double.toString(10.00));
        project.getComponents().add(comp6);


        project.generateDevices();
        
        List<Island> world = new ArrayList<Island>();
        
        for (int i = 0; i < Config.NUM_ISLANDS; i++) {
            try {
            Island thread = new Island("Island"+(i+1), project);
            world.add(thread);
            thread.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }        
    }
}
