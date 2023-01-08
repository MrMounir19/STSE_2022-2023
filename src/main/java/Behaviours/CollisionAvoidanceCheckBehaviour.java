package Behaviours;

import Enums.CollisionAction;
import Utils.Messages;
import WarehouseServer.RobotObject;
import WarehouseServer.RobotStorage;
import WarehouseShared.Config;
import WarehouseShared.Position;
import jade.core.behaviours.CyclicBehaviour;
import java.util.ArrayList;


/**
 * Simple Tuple structure used to store two robots that are actively in collision avoidance with each other.
 *
 * @author Maxim
 * @author Thimoty
 * @version 1.0
 * @since 07/01/2023
 */
class RobotTuple {
    public RobotObject robot1;
    public RobotObject robot2;
    
    public RobotTuple(RobotObject robot1, RobotObject robot2) {
        this.robot1 = robot1;
        this.robot2 = robot2;
    }
    
    public boolean matches(RobotObject robot1, RobotObject robot2) {
        if (this.robot1 == robot1 && this.robot2 == robot2) {
            return true;
        }
        return this.robot1 == robot2 && this.robot2 == robot1;
    }
}


/**
 * Checks for any likely collisions and initiates collision avoidance if so.
 *
 * @author Maxim
 * @author Thimoty
 * @version 1.0
 * @since 07/01/2023
 */
public class CollisionAvoidanceCheckBehaviour extends CyclicBehaviour {
    private ArrayList<RobotTuple> robotsColliding = new ArrayList<>();
    private ArrayList<RobotObject> robotsCollidingStopped = new ArrayList<>();
    private ArrayList<RobotObject> robotsCollidingClearing = new ArrayList<>();

    @Override
    public void action() {
        for (RobotObject robot1 : RobotStorage.getRobots()) {
            for (RobotObject robot2 : RobotStorage.getRobots()) {
                if (alreadyColliding(robot1, robot2)) {
                    if (!collisionCheck(robot1, robot2)) {
                        endCollisionAvoidance(robot1, robot2);
                    }
                }

                if (collisionCheck(robot1, robot2)) {
                    startCollisionAvoidance(robot1, robot2);
                }
            }
        }
    }

    /**
     * Checks if two robots are within collision avoidance distance of each other.
     *
     * @param robot1 First robot
     * @param robot2 Second robot
     * @return Whether robot1 and robot2 are within collision avoidance distance of each other.
     *
     * @author Maxim
     * @author Thimoty
     * @since 07/01/2023
     */
    public boolean collisionCheck(RobotObject robot1, RobotObject robot2) {
        Position robot1Position = robot1.position;
        Position robot2Position = robot2.position;

        float collisionAvoidanceDistance = Config.getConfig().get("collision_avoidance_distance").getAsFloat();

        return robot1Position.distanceTo(robot2Position) < collisionAvoidanceDistance;
    }

    private boolean alreadyColliding(RobotObject robot1, RobotObject robot2) {
        for (RobotTuple robotTuple : robotsColliding) {
            if (robotTuple.matches(robot1, robot2)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Initiates collision avoidance for the two robots.
     * This means one robot will be forced to stop until collision avoidance is over, while the other will continue their path.
     * If they collide, the clearing robot will use Object Avoidance to move around the other robot.
     *
     * @param robot1 First robot
     * @param robot2 Second robot
     *
     * @author Maxim
     * @author Thimoty
     * @since 07/01/2023
     */
    public void startCollisionAvoidance(RobotObject robot1, RobotObject robot2) {
        System.out.println("Initiating collision avoidance for " + robot1.getRobotId() + " and " + robot2 + ".");

        RobotObject robotClearing = robot1;
        RobotObject robotStopping = robot2;

        if (robotsCollidingClearing.contains(robot2) || robotsCollidingStopped.contains(robot1)) {
            robotClearing = robot2;
            robotStopping = robot1;
        }

        myAgent.send(Messages.collisionMessage(robotClearing.getRobotId(), CollisionAction.Continue));
        myAgent.send(Messages.collisionMessage(robotStopping.getRobotId(), CollisionAction.Stop));

        robotsCollidingStopped.remove(robot1);
        robotsCollidingStopped.remove(robot2);
        robotsCollidingClearing.remove(robot1);
        robotsCollidingClearing.remove(robot2);

        robotsCollidingClearing.add(robotClearing);
        robotsCollidingStopped.add(robotStopping);
        robotsColliding.add(new RobotTuple(robot1, robot2));
    }

    /**
     * Ends collision avoidance between two robots.
     *
     * @param robot1 First robot
     * @param robot2 Second robot
     *
     * @author Maxim
     * @author Thimoty
     * @since 07/01/2023
     */
    public void endCollisionAvoidance(RobotObject robot1, RobotObject robot2) {
        System.out.println("ending collision avoidance for " + robot1.getRobotId() + " and " + robot2 + ".");

        if (robotsCollidingStopped.contains(robot1)) {
            myAgent.send(Messages.collisionMessage(robot1.getRobotId(), CollisionAction.Continue));
        }

        if (robotsCollidingStopped.contains(robot2)) {
            myAgent.send(Messages.collisionMessage(robot2.getRobotId(), CollisionAction.Continue));
        }

        robotsCollidingStopped.remove(robot1);
        robotsCollidingStopped.remove(robot2);
        robotsCollidingClearing.remove(robot1);
        robotsCollidingClearing.remove(robot2);

        RobotTuple robotTupleToRemove = null;
        for (RobotTuple robotTuple : robotsColliding) {
            if (robotTuple.matches(robot1, robot2)) {
                robotTupleToRemove = robotTuple;
                break;
            }
        }
        robotsColliding.remove(robotTupleToRemove);
    }
}
