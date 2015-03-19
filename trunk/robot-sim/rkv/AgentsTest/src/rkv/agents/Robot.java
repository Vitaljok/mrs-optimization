/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rkv.agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.core.behaviours.TickerBehaviour;
import java.awt.Point;
import java.awt.geom.Point2D;
import javaclient3.FiducialInterface;
import javaclient3.GripperInterface;
import javaclient3.PlayerClient;
import javaclient3.Position2DInterface;
import javaclient3.RangerInterface;
import javaclient3.structures.PlayerConstants;
import javaclient3.structures.PlayerPose2d;
import rkv.agents.utils.Utils;

/**
 *
 * @author Vitaljok rob1:rkv.agents.Robot(192.168.1.101:6665);
 */
public class Robot extends Agent {

    class GoToBehavior extends OneShotBehaviour {

        private Point2D.Double target;
        private Double maxSpeed;
        private Double maxTurn;

        public GoToBehavior(Agent a, Point2D.Double target) {
            super(a);
            this.target = target;
            this.maxSpeed = 2.0;
            this.maxTurn = 1.0;
        }

        public Point2D.Double getTarget() {
            return target;
        }

        public void setTarget(Point2D.Double target) {
            this.target = target;
        }

        public Double getMaxSpeed() {
            return maxSpeed;
        }

        public void setMaxSpeed(Double maxSpeed) {
            this.maxSpeed = maxSpeed;
        }

        @Override
        public void action() {

            if (position.getData() == null) {
                return;
            }

            PlayerPose2d pos = position.getData().getPos();
            Point2D.Double A = new Point2D.Double(pos.getPx(), pos.getPy());
            Point2D.Double B = new Point2D.Double(pos.getPx() + Math.cos(pos.getPa()), pos.getPy() + Math.sin(pos.getPa()));

            if (Utils.dirrection(A, B, target) > 0) {
                turn = Utils.angleR(A, B, target);
            } else {
                turn = -Utils.angleR(A, B, target);
            }

            turn = Utils.limit(turn, -maxTurn, maxTurn);
            speed = Utils.limit(A.distance(target) - 1, 0.0, maxSpeed);

            if (speed < 0.01) {
                speed = 0.0;
            }
        }
    }

    class SetCommandsBehaviour extends OneShotBehaviour {

        @Override
        public void action() {
            position.setSpeed(speed, turn);
        }
    }

    class ReadSensorsBehaviour extends TickerBehaviour {

        public ReadSensorsBehaviour(Agent a, long period) {
            super(a, period);
        }

        @Override
        protected void onTick() {
            if (position.isDataReady()) {
            }

            if (fiducial.isDataReady()) {
            }

            if (gripper.isDataReady()) {
            }

            if (ranger.isDataReady()) {
            };
        }
    }

    class AvoidObstaclesBehaviour extends OneShotBehaviour {

        @Override
        public void action() {
        }
    }

    class ArbiterBehaviour extends TickerBehaviour {

        public ArbiterBehaviour(Agent a, long period) {
            super(a, period);
        }

        @Override
        protected void onTick() {
            SequentialBehaviour seq = new SequentialBehaviour(myAgent);

            seq.addSubBehaviour(new GoToBehavior(myAgent, new Point2D.Double(5, 0)));
            //seq.addSubBehaviour(new SetCommandsBehaviour());

            myAgent.addBehaviour(seq);
        }
    }
    private PlayerClient client;
    private Position2DInterface position;
    private FiducialInterface fiducial;
    private GripperInterface gripper;
    private RangerInterface ranger;
    private AID myBrains;
    private double speed;
    private double turn;

    @Override
    protected void setup() {
        super.setup();

        String host = "localhost";
        Integer port = 6665;

        Object[] args = this.getArguments();

        if (args != null && args.length > 0) {
            String address = (String) args[0];
            String[] str = address.split(":");

            host = str[0];
            port = Integer.parseInt(str[1]);
        }

        client = new PlayerClient(host, port);
        position = client.requestInterfacePosition2D(0, PlayerConstants.PLAYER_OPEN_MODE);
        fiducial = client.requestInterfaceFiducial(0, PlayerConstants.PLAYER_OPEN_MODE);
        gripper = client.requestInterfaceGripper(0, PlayerConstants.PLAYER_OPEN_MODE);
        ranger = client.requestInterfaceRanger(0, PlayerConstants.PLAYER_OPEN_MODE);

        client.runThreaded(10, -1);

        // add arbiter behavior
        this.addBehaviour(new ArbiterBehaviour(this, 100));
    }

    @Override
    protected void takeDown() {
        client.close();
        super.takeDown();
    }
}
