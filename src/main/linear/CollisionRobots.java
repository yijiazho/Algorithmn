package utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class CollisionRobots {

    class Robot {
        int number;
        int pos;
        int health;
        // true for going right
        boolean direction;

        public Robot(int number, int pos, int health, char d) {
            this.number = number;
            this.pos = pos;
            this.health = health;
            direction = d == 'R';
        }
    }

    // imagine robots 1-> 2-> 3-> <-4 <-5 6-> 7-> <-8
    // keep a data structure to keep all robots going right only,
    // and collide with robot going left
    // this is guaranteeed in order
    public List<Integer> survivedRobotsHealths(int[] positions, int[] healths, String directions) {
        int n = positions.length;
        List<Robot> robotList = new ArrayList<>();
        // build robot list
        for (int i = 0; i < n; i++) {
            Robot robot = new Robot(i, positions[i], healths[i], directions.charAt(i));
            robotList.add(robot);
        }
        // sort by position
        Collections.sort(robotList, (r1, r2) -> r1.pos - r2.pos);
        List<Robot> survivedRobots = new ArrayList<>();
        Stack<Robot> headingRightRobots = new Stack<>();

        // traverse the robot list
        for (Robot robot: robotList) {
            if (robot.direction) {
                // going right, push to stack
                headingRightRobots.push(robot);
            } else {
                // going left
                while (!headingRightRobots.empty()) {
                    Robot top = headingRightRobots.pop();
                    if (top.health == robot.health) {
                        // cancel each other
                        robot.health = 0;
                        break;
                    } else if (top.health > robot.health) {
                        // top survives
                        top.health -= robot.health;
                        headingRightRobots.push(top);
                        robot.health = 0;
                        break;
                    } else {
                        // robot survives, keep fighting next
                        robot.health -= top.health;
                    }
                }

                // if it beats all robots in stack, add to the survival list
                if (robot.health > 0) {
                    survivedRobots.add(robot);
                }
            }

        }


        // all robots in stack will survive
        while (!headingRightRobots.empty()) {
            survivedRobots.add(headingRightRobots.pop());
        }

        Collections.sort(survivedRobots, (r1, r2) -> r1.number - r2.number);
        List<Integer> healthList = new ArrayList<>();
        for (Robot robot: survivedRobots) {
            healthList.add(robot.health);
        }

        return healthList;
    }
}
