/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rkv.agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ReceiverBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vitaljok
 */
public class TestMsgAgent extends Agent {
    
    //a1:rkv.agents.TestMsgAgent(2000);a2:rkv.agents.TestMsgAgent(5000)
    
    static long   t0     = System.currentTimeMillis();
    static void print(Object msg) {
        System.out.println((System.currentTimeMillis() - t0) +"\t"+msg);
    }

    Integer delay;

    @Override
    protected void setup() {
        Object[] args = this.getArguments();

        if (args != null && args.length > 0) {
            delay = Integer.parseInt((String) args[0]);
        } else {
            delay = 1000;
        }

        this.addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                ACLMessage msg = myAgent.receive();

                if (msg != null) {                    
                    print("Message received @" + myAgent.getLocalName() + "\n" + msg);
                    try {
                        int[] obj = (int[]) msg.getContentObject();
                        print(obj[0]);
                        
                    } catch (UnreadableException ex) {
                        Logger.getLogger(TestMsgAgent.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    
                    
                    
                } else {
                    block();
                }

            }
        });

        this.addBehaviour(new WakerBehaviour(this, delay) {
            @Override
            protected void onWake() {
                System.out.println("MsgAgent " + myAgent.getLocalName() + " starting");
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.addReceiver(new AID("a1", AID.ISLOCALNAME));
                msg.addReceiver(new AID("a2", AID.ISLOCALNAME));
                msg.setLanguage("My protocol");

                int[] arr = {11, 22, 33, 44, 55};
                
                try {
                    msg.setContentObject(arr);
                } catch (IOException ex) {
                    Logger.getLogger(TestMsgAgent.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                send(msg);
            }
        });
    }
}
