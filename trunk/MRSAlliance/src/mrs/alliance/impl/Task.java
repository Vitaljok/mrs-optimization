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
public class Task {
    String name;  
    Integer amount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Task(String name, Integer amount) {
        this.name = name;
        this.amount = amount;
    }

    public Task() {
    }

    public Task(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Task["+this.name+"]";
    }

    
}
