/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rkv.agents;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.core.behaviours.TickerBehaviour;

/**
 *
 * @author Vitaljok
 */
public class TestAgent extends Agent {

    @Override
    protected void setup() {
        super.setup();

        final OneShotBehaviour arbiter = new OneShotBehaviour(this) {
            int state = 1;

            @Override
            public void action() {
                System.out.println("2. Arbiter (state = " + state + ")");
                state++;

                SequentialBehaviour s = new SequentialBehaviour(myAgent);

                if (state < 4) {
                    s.addSubBehaviour(new OneShotBehaviour(myAgent) {
                        @Override
                        public void action() {
                            System.out.println("3. Worker A");
                        }
                    });
                } else {
                    s.addSubBehaviour(new OneShotBehaviour(myAgent) {
                        @Override
                        public void action() {
                            System.out.println("3. Worker B");
                        }
                    });
                }

                s.addSubBehaviour(new OneShotBehaviour(myAgent) {
                    @Override
                    public void action() {
                        System.out.println("4. Avoid obstacles");
                    }
                });

                myAgent.addBehaviour(s);
            }
        };





        this.addBehaviour(new TickerBehaviour(this, 2000) {
            @Override
            protected void onTick() {
                System.out.println("Tick");

                SequentialBehaviour seq = new SequentialBehaviour(myAgent);

                seq.addSubBehaviour(new OneShotBehaviour(myAgent) {
                    @Override
                    public void action() {
                        System.out.println("1. Read sensors");
                    }
                });

                seq.addSubBehaviour(arbiter);
                myAgent.addBehaviour(seq);
            }
        });
    }
}
