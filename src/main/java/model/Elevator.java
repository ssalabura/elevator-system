package model;

import java.util.TreeSet;

public class Elevator {
    private final int id;
    private int floor;
    private int destination;
    private Doors doors;
    private Direction direction;

    private Direction nextDirection;

    private final TreeSet<Integer> toVisit;

    Elevator(int id) {
        this.id = id;
        floor = 0;
        destination = 0;
        doors = Doors.OPEN;
        direction = Direction.IDLE;
        nextDirection = Direction.IDLE;
        toVisit = new TreeSet<>();
    }

    void newWaitingPerson(int from, Direction direction) {
        toVisit.add(from);

        if(direction == Direction.UP) {
            if(from > floor && from < destination) {
                destination = from;
            }
        } else {
            if(from < floor && from > destination) {
                destination = from;
            }
        }
        if(toVisit.size() == 1 && from != floor){
            /* elevator is currently idle
             * we have to update the direction after stopping on their floor */
            nextDirection = direction;
        }
    }

    void pushButton(int floor) {
        toVisit.add(floor);
        if(direction == Direction.IDLE) {
            if(floor > this.floor) {
                direction = Direction.UP;
            }
            else {
                direction = Direction.DOWN;
            }
        }
    }

    void nextStep() {
        if(destination > floor) {
            floor++;
        } else if(destination < floor) {
            floor--;
        } else {
            toVisit.remove(floor);
            if(doors == Doors.CLOSED) {
                doors = Doors.OPEN;
                if(toVisit.isEmpty()) {
                    // we use the previously stored direction
                    direction = nextDirection;
                    nextDirection = Direction.IDLE;
                }
            }
            else {
                if(!toVisit.isEmpty()) {
                    if(direction == Direction.UP) destination = toVisit.first();
                    else if(direction == Direction.DOWN) destination = toVisit.last();
                    else {
                        destination = toVisit.first();
                        if(destination > floor) direction = Direction.UP;
                        if(destination < floor) direction = Direction.DOWN;
                    }
                }
                // doors can stay open if elevator has nothing to do
                if(destination != floor) {
                    doors = Doors.CLOSED;
                }
            }
        }
    }

    int getQueueSize() {
        return toVisit.size();
    }

    ElevatorStatus getStatus() {
        return new ElevatorStatus(id, floor, destination, doors, direction);
    }
}
