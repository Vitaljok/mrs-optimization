/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mrs.alliance.impl;

/**
 *
 * @author Vitaljok
 */
public class Message implements Processable {
    Robot from;
    Robot to;
    Task task;
    
    Byte timeToLive = 5;

    public Robot getFrom() {
        return from;
    }

    public void setFrom(Robot from) {
        this.from = from;
    }

    public Robot getTo() {
        return to;
    }

    public void setTo(Robot to) {
        this.to = to;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Message(Robot from, Robot to, Task task) {
        this.from = from;
        this.to = to;
        this.task = task;
    }
    
    public String getMessage(){
        return from+" working on "+task;
    }

    public Byte getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(Byte timeToLive) {
        this.timeToLive = timeToLive;
    }    

    @Override
    public void process() {
        this.timeToLive--;
    }

}
