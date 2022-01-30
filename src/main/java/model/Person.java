package model;

public class Person {
    private final ElevatorSystem system;
    private final int from;
    private final int to;
    int assigned;
    Elevator elevator;

    Person(ElevatorSystem system, int from, int to) {
        this.system = system;
        this.from = from;
        this.to = to;
        assigned = -1;
    }

    void nextStep() {
        if(elevator == null) {
            Elevator[] elevators = system.getOpenElevators(from);
            for(Elevator elevator : elevators) {
                ElevatorStatus status = elevator.getStatus();
                if(status.direction == getDirection() || status.direction == Direction.IDLE) {
                    stepInto(elevator);
                    break;
                }
            }
        } else {
            ElevatorStatus status = elevator.getStatus();
            if(status.currentFloor == to && status.doors == Doors.OPEN) {
                stepOut();
            }
        }
    }

    private void stepInto(Elevator elevator) {
        this.elevator = elevator;
        elevator.pushButton(to);
    }

    private void stepOut() {
        system.removePerson(this);
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public Direction getDirection() {
        if(to > from) return Direction.UP;
        else if(to < from) return Direction.DOWN;
        else return Direction.IDLE;
    }

    public Elevator getElevator() {
        return elevator;
    }

    @Override
    public String toString() {
        return "{" + from + " -> " + to + ", " +
                (elevator == null ? "waiting" : "elevator " + elevator.getStatus().id) + "}";
    }
}
