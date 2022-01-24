import java.util.ArrayList;
import java.util.Collections;

public class Elevator {
    private final int id;
    private int floor;
    private int destination;
    private Doors doors;

    private final ArrayList<Integer> to_visit;

    Elevator(int id) {
        this.id = id;
        floor = 0;
        destination = 0;
        doors = Doors.OPEN;
        to_visit = new ArrayList<>();
    }

    void newWaitingPerson(int from, int direction) {
        to_visit.add(from);
        // TODO: is direction useful?
    }

    void pushButton(int floor) {
        to_visit.add(floor);
    }

    void nextStep() {
        if(destination > floor) {
            floor++;
        } else if(destination < floor) {
            floor--;
        } else {
            if(doors == Doors.CLOSED) {
                doors = Doors.OPEN;
                to_visit.removeAll(Collections.singleton(floor));
            }
            else {
                while(!to_visit.isEmpty() && destination == floor) {
                    // TODO: something smarter than FCFS
                    destination = to_visit.remove(0);
                }
                // doors can stay open if elevator has nothing to do
                if(destination != floor) {
                    doors = Doors.CLOSED;
                }
            }
        }
    }

    ElevatorStatus getStatus() {
        return new ElevatorStatus(id, floor, destination, doors);
    }
}
