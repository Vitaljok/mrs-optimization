/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phd.mrs.sim.control.behavior.structure;

/**
 *
 * @author Vitaljok
 */
public class Ranger8Signal extends Signal {

    private Double front;
    private Double frontRight;
    private Double frontLeft;
    private Double right;
    private Double left;
    private Double rearRight;
    private Double rearLeft;
    private Double rear;

    public Ranger8Signal() {
        this.resetRanges();
    }

    public void setRanges8(double[] ranges) {
        this.front = ranges[0];
        this.frontRight = ranges[1];
        this.right = ranges[2];
        this.rearRight = ranges[3];
        this.rear = ranges[4];
        this.rearLeft = ranges[5];
        this.left = ranges[6];
        this.frontLeft = ranges[7];
    }

    public void setRanges360(double[] ranges) {

        Float angleStep = 360f / ranges.length;
        Float fovHalf = 30f;

        //precalculate indexes        
        Integer[] fromI = new Integer[8];
        Integer[] toI = new Integer[8];

        for (int i = 0; i < 8; i++) {
            fromI[i] = Math.round((float) Math.floor((i * 45f - fovHalf) / angleStep));

            if (fromI[i] < 0) {
                fromI[i] += ranges.length;
            }

            toI[i] = Math.round((float) Math.floor((i * 45f + fovHalf) / angleStep));
        }

        this.resetRanges();

        for (int i = 0; i < ranges.length; i++) {

            // first one starts from the end of array --> ||
            if (i > fromI[0] || i < toI[0]) {
                this.rear = Math.min(this.rear, ranges[i]);
            }

            if (i > fromI[1] && i < toI[1]) {
                this.rearRight = Math.min(this.rearRight, ranges[i]);
            }

            if (i > fromI[2] && i < toI[2]) {
                this.right = Math.min(this.right, ranges[i]);
            }

            if (i > fromI[3] && i < toI[3]) {
                this.frontRight = Math.min(this.frontRight, ranges[i]);
            }

            if (i > fromI[4] && i < toI[4]) {
                this.front = Math.min(this.front, ranges[i]);
            }

            if (i > fromI[5] && i < toI[5]) {
                this.frontLeft = Math.min(this.frontLeft, ranges[i]);
            }

            if (i > fromI[6] && i < toI[6]) {
                this.left = Math.min(this.left, ranges[i]);
            }

            if (i > fromI[7] && i < toI[7]) {
                this.rearLeft = Math.min(this.rearLeft, ranges[i]);
            }
        }
    }

    private void resetRanges() {
        front = Double.MAX_VALUE;
        frontRight = Double.MAX_VALUE;
        frontLeft = Double.MAX_VALUE;
        right = Double.MAX_VALUE;
        left = Double.MAX_VALUE;
        rearRight = Double.MAX_VALUE;
        rearLeft = Double.MAX_VALUE;
        rear = Double.MAX_VALUE;
    }

    public Double getFront() {
        return front;
    }

    public Double getFrontLeft() {
        return frontLeft;
    }

    public Double getFrontRight() {
        return frontRight;
    }

    public Double getLeft() {
        return left;
    }

    public Double getRear() {
        return rear;
    }

    public Double getRearLeft() {
        return rearLeft;
    }

    public Double getRearRight() {
        return rearRight;
    }

    public Double getRight() {
        return right;
    }
}
