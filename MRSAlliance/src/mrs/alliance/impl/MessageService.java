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
public class MessageService implements Processable {

    List<Robot> robots = new ArrayList<>();
    List<Message> messages = new ArrayList<>();

    public void registerRobot(Robot robot) {
        robots.add(robot);
    }

    public void sendMessage(Robot robot, Task task) {
        for (Robot rob : robots) {
            messages.add(new Message(robot, rob, task));
        }
    }

    public Message getNextMessage(Robot robot) {

        Message res = null;
        for (Message msg : messages) {
            if (msg.getTo().equals(robot)) {
                res = msg;
                break;
            }
        }
        messages.remove(res);
        return res;
    }

    @Override
    public void process() {
        List<Message> deadMessages = new ArrayList<>();
        for (Message msg : messages) {
            msg.process();

            if (msg.getTimeToLive() <= 0) {
                deadMessages.add(msg);
            }
        }

        messages.removeAll(deadMessages);
    }

    public List<Robot> getRobots() {
        return robots;
    }

    public List<Message> getMessages() {
        return messages;
    }        
}
