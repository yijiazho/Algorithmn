package utility;

import java.util.List;
import java.util.Map;

class Switch {
    //number of switch
    int number;
    //on or off
    boolean type;
}

class Grid {
    //Could be floor, bridge or occupied
    String type;

    // All the switches it contains, if it's a floor
    List<Switch> switches;

    //Open or closed, if it's a bridge
    boolean open;

    public Grid(String type, List<Switch> switches, boolean open) {
        this.type = type;
        this.switches = switches;
        this.open = open;
    }

    public Grid(String type) {
        this.type = type;
    }
}

class Position {
    int number;
    int[] positiveSwitchPosition;
    int[] negativeSwitchPosition;
    int[] positiveBridgePosition;
    int[] negativeBridgePosition;


    public Position(int number, int[] positiveSwitchPosition, int[] negativeSwitchPosition,
                    int[] positiveBridgePosition, int[] negativeBridgePosition) {
        this.number = number;
        this.negativeSwitchPosition = negativeSwitchPosition;
        this.positiveSwitchPosition = positiveSwitchPosition;
        this.negativeBridgePosition = negativeBridgePosition;
        this.positiveBridgePosition = positiveBridgePosition;
    }
}

public class BridgeMaze {

    private boolean[] switchers = {false, false, false, false, false, true, true, true, true, true};

    private Grid[][] maze = new Grid[7][7];

    private Grid[][] initialMaze;

    private Map<Integer, Position> map;

    private static void initializePosition() {
       // map.put(1, new Position(1, {4,4}, {4,2}, {4,1}, ));

    }

    private static void initialize() {
        for (int i = 0; i< 7; i++) {
            for (int j = 0; j < 7; j++) {
                Grid grid;

                if (i % 2 == 1 && j % 2 == 1) {
                    grid = new Grid("Unavailable");
                } else if (i % 2 == 0 && j % 2 == 0) {
                    //Floor
                    grid = new Grid("floor");
                } else {
                    grid = new Grid("bridge");
                }

                //initialize
            }
        }
    }

    public static void main (String[] args) {
        System.out.println("Hello");
    }
}
