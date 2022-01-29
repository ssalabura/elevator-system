import java.util.TreeSet;

public class Elevator {
    private final int id;
    private int floor;
    private int destination;
    private Doors doors;
    private Direction direction;

    private Direction next_direction;

    private final TreeSet<Integer> to_visit;

    Elevator(int id) {
        this.id = id;
        floor = 0;
        destination = 0;
        doors = Doors.OPEN;
        direction = Direction.IDLE;
        next_direction = Direction.IDLE;
        to_visit = new TreeSet<>();
    }

    void newWaitingPerson(int from, Direction direction) {
        to_visit.add(from);

        if(direction == Direction.UP) {
            if(from > floor && from < destination) {
                destination = from;
            }
        } else {
            if(from < floor && from > destination) {
                destination = from;
            }
        }
        if(to_visit.size() == 1 && from != floor){
            /* elevator is currently idle
             * we have to update the direction after stopping on their floor */
            next_direction = direction;
        }
    }

    void pushButton(int floor) {
        to_visit.add(floor);
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
            to_visit.remove(floor);
            if(doors == Doors.CLOSED) {
                doors = Doors.OPEN;
                if(to_visit.isEmpty()) {
                    // we use the previously stored direction
                    direction = next_direction;
                    next_direction = Direction.IDLE;
                }
            }
            else {
                if(!to_visit.isEmpty()) {
                    if(direction == Direction.UP) destination = to_visit.first();
                    else if(direction == Direction.DOWN) destination = to_visit.last();
                    else {
                        destination = to_visit.first();
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
        return to_visit.size();
    }

    ElevatorStatus getStatus() {
        return new ElevatorStatus(id, floor, destination, doors, direction);
    }
}
