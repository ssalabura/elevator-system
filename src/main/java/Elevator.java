import java.util.TreeSet;

public class Elevator {
    private final int id;
    private int floor;
    private int destination;
    private Doors doors;

    private final TreeSet<Event> to_visit;

    Elevator(int id) {
        this.id = id;
        floor = 0;
        destination = 0;
        doors = Doors.OPEN;
        to_visit = new TreeSet<>();
    }

    void newWaitingPerson(int from, int direction) {
        to_visit.add(new Event(from));

        if(direction == 1) {
            if(from > floor && from < destination) {
                destination = from;
            }
        } else {
            if(from < floor && from > destination) {
                destination = from;
            }
        }
    }

    void pushButton(int floor) {
        to_visit.add(new Event(floor));
    }

    void nextStep() {
        if(destination > floor) {
            floor++;
        } else if(destination < floor) {
            floor--;
        } else {
            to_visit.remove(new Event(floor));
            if(doors == Doors.CLOSED) {
                doors = Doors.OPEN;
            }
            else {
                if(!to_visit.isEmpty()) {
                    // FCFS but with stopping in between
                    // TODO(?): something smarter
                    Event next = new Event(-1);
                    for(Event e : to_visit) {
                        if(next.timestamp > e.timestamp) {
                            next = e;
                        }
                    }
                    if(next.destination - floor > 0) {
                        destination = to_visit.ceiling(new Event(floor)).destination;
                    } else {
                        destination = to_visit.floor(new Event(floor)).destination;
                    }
                }
                // doors can stay open if elevator has nothing to do
                if(destination != floor) {
                    doors = Doors.CLOSED;
                }
            }
        }
    }

    int queueSize() {
        return to_visit.size();
    }

    ElevatorStatus getStatus() {
        return new ElevatorStatus(id, floor, destination, doors);
    }
}
