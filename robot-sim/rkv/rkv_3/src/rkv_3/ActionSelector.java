/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rkv_3;

import org.neuroph.core.Connection;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.Neuron;
import org.neuroph.nnet.Perceptron;
import org.neuroph.util.TransferFunctionType;

/**
 *
 * @author vitaljok
 */
public class ActionSelector {

    static private Integer featureCount = 25;
    static private Integer sensorCount = 4;
    static private Integer actionCount = 6;
    private NeuralNetwork featureNet;
    private NeuralNetwork actionNet;
    private Integer lastActionIndex = -1;

    public ActionSelector() {
        featureNet = new Perceptron(sensorCount, featureCount, TransferFunctionType.SIGMOID);
        actionNet = new Perceptron(featureCount, actionCount, TransferFunctionType.SIGMOID);
    }

    public void learn(double reinf) {
        double a1 = 0.8;
        double a2 = 0.5;

        for (int i = 0; i < featureCount; i++) {
            Neuron n = featureNet.getOutputNeurons()[i];
            for (Connection c : n.getInputConnections()) {

                double I = c.getInput();
                double W = c.getWeight().getValue();
                double G = this.actionNet.getInputNeurons()[i].getNetInput();
                
                c.getWeight().inc(a1*Math.abs(reinf)*I*G*W);
            }
        }

        for (int i = 0; i < actionCount; i++) {
            Neuron n = actionNet.getOutputNeurons()[i];

            for (Connection c : n.getInputConnections()) {
                double G = c.getInput();

                double A = (i == lastActionIndex ? n.getOutput() : 0);

                double W = c.getWeight().getValue();


                c.getWeight().inc(a2*reinf*G*A*W);
            }
        }
    }

    public Action select(double[] input) {
        for (int i = 0; i < sensorCount; i++) {
            this.featureNet.getInputNeurons()[i].setInput(input[i]);
        }

        this.featureNet.calculate();
        double[] out = this.featureNet.getOutput();

        for (int i = 0; i < featureCount; i++) {
            this.actionNet.getInputNeurons()[i].setInput(getWinner(out, i));
        }

        actionNet.calculate();

        int maxIndex = 1;
        double maxValue = Double.MIN_VALUE;

        for (int i = 0; i < actionNet.getOutput().length; i++) {
            double d = actionNet.getOutput()[i];
            if (d > maxValue) {
                maxValue = d;
                maxIndex = i;
            }
        }

        this.lastActionIndex = maxIndex; ;
        return Action.values()[maxIndex];
    }

    private double getWinner(double[] values, int index) {

        int N = 3;
        int a = Math.max(index - N, 0);
        int b = Math.min(index + N, values.length - 1);

        for (int i = a; i <= b; i++) {
            if (i != index && values[i] > values[index]) {
                return 0;
            }
        }

        return values[index];
    }
}
