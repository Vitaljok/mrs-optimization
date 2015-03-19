/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mrs.alliance.impl;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vitaljok
 */
public class Robot implements Processable{
    
    String name;
    
    List<TaskInfo> tasks = new ArrayList<>();
    
    public void addTask(Task task, Integer performance){
        TaskInfo info = new TaskInfo(task);
        info.setPerformace(performance);
        info.sensoryFeedback = 1;
            
        tasks.add(info);
    }
    
    @Override
    public void process() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<TaskInfo> getTasks() {
        return tasks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Robot(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Robot["+name+"]";
    }
    
    
}
