package mrs.alliance;

import mrs.alliance.impl.Robot;
import mrs.alliance.impl.Task;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Vitaljok
 */
public class RunAlliance {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Task taskA = new Task("Bring A component", 100);
        Task taskB = new Task("Bring B component", 100);
        Task taskC = new Task("Bring C component", 100);
        Task taskR = new Task("Report status", 100);
        
        Robot robot1 = new Robot("R1");
        robot1.addTask(taskA, 1);
        robot1.addTask(taskB, 1);
        robot1.addTask(taskR, 1);
        
        Robot robot2 = new Robot("R2");
        robot2.addTask(taskB, 1);
        robot2.addTask(taskC, 1);
        robot2.addTask(taskR, 1);
        
        Robot robot3 = new Robot("R3");
        robot3.addTask(taskA, 1);
        robot3.addTask(taskC, 1);
        robot3.addTask(taskR, 1);               
    }
    
}
