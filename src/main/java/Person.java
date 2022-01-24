public class Person {
    private final ElevatorSystem system;
    private final int from;
    private final int to;
    private Elevator elevator;

    Person(ElevatorSystem system, int from, int to) {
        this.system = system;
        this.from = from;
        this.to = to;
    }

    void nextStep() {
        if(elevator == null) {
            Elevator myElevator = system.getOpenedElevator(from);
            if(myElevator != null) stepInto(myElevator);
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

    @Override
    public String toString() {
        return "{" + from + " -> " + to + ", " +
                (elevator == null ? "waiting" : "elevator" + elevator.getStatus().id) + "}";
    }
}
