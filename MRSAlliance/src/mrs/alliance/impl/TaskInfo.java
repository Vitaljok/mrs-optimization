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
public class TaskInfo {

    Task task;
    Byte sensoryFeedback;
    Byte activitySuppression;
    Byte acquiescence;
    Byte impatenceReset;

    Integer performace;

    Double impatience;
    Double motivation = 0d;

    public Integer getPerformace() {
        return performace;
    }

    public void setPerformace(Integer performace) {
        this.performace = performace;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public TaskInfo(Task task) {
        this.task = task;
    }

    public Byte isSensoryFeedback() {
        return sensoryFeedback;
    }

    public void setSensoryFeedback(Byte sensoryFeedback) {
        this.sensoryFeedback = sensoryFeedback;
    }

    public Byte isActivitySuppression() {
        return activitySuppression;
    }

    public void setActivitySuppression(Byte activitySuppression) {
        this.activitySuppression = activitySuppression;
    }

    public Byte isAcquiescence() {
        return acquiescence;
    }

    public void setAcquiescence(Byte acquiescence) {
        this.acquiescence = acquiescence;
    }

    public Byte isImpatenceReset() {
        return impatenceReset;
    }

    public void setImpatenceReset(Byte impatenceReset) {
        this.impatenceReset = impatenceReset;
    }

    public Double getImpatience() {
        return impatience;
    }

    public void setImpatience(Double impatience) {
        this.impatience = impatience;
    }

    public Double getMotivation() {
        return motivation;
    }

    public void setMotivation(Double motivation) {
        this.motivation = motivation;
    }

    public void calculateMotivation() {
        this.motivation = (this.motivation + impatience)
                * activitySuppression
                * sensoryFeedback
                * acquiescence
                * impatenceReset;
    }
}
