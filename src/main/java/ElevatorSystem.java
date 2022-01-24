import java.util.ArrayList;
import java.util.Arrays;

public class ElevatorSystem {
    private final Elevator[] elevators;
    private final ArrayList<Person> people;

    ElevatorSystem(int count) {
        if(count < 1) {
            throw new IllegalArgumentException("At least 1 elevator is needed");
        }
        elevators = new Elevator[count];
        for(int i=0; i<count; i++) {
            elevators[i] = new Elevator(i);
        }
        people = new ArrayList<>();
    }

    public void addPerson(int from, int to) {
        if(from < 0 || to < 0) {
            throw new IllegalArgumentException("Floor should not be negative");
        }
        people.add(new Person(this, from, to));
        // in realistic scenario we only know the direction
        int direction = to > from ? 1 : -1;

        // TODO(?): smarter assignment
        Elevator bestElevator = null;
        int points = (int) 1e9; // lower is better

        for(Elevator elevator : elevators) {
            ElevatorStatus status = elevator.getStatus();
            int elevatorPoints;
            // "good" scenario:
            // elevator can stop during its route
            // we choose the closest one
            if((direction == 1 && status.currentFloor < from && status.destinationFloor > from) ||
                    (direction == -1 && status.currentFloor > from && status.destinationFloor < from)) {
                elevatorPoints = Math.abs(status.currentFloor - from);
            }
            // "bad" scenario:
            // we take into account elevator's original route length (or 0 if stationary) and its workload
            else {
                int workloadConstant = 2; // TODO
                elevatorPoints = Math.abs(status.destinationFloor - status.currentFloor) +
                        Math.abs(from - status.destinationFloor) +
                        workloadConstant * elevator.queueSize();
            }
            if(elevatorPoints < points) {
                points = elevatorPoints;
                bestElevator = elevator;
            }
        }

        bestElevator.newWaitingPerson(from, direction);
    }

    void removePerson(Person person) {
        people.remove(person);
    }

    public void nextStep() {
        ArrayList<Person> peopleCopy = new ArrayList<>(people);
        for(Person person : peopleCopy) {
            person.nextStep();
        }
        for(Elevator elevator : elevators) {
            elevator.nextStep();
        }
    }

    public ElevatorStatus[] getStatus() {
        return Arrays.stream(elevators).map(Elevator::getStatus).toArray(ElevatorStatus[]::new);
    }

    public ArrayList<Person> getPeople() {
        return people;
    }

    Elevator getOpenedElevator(int floor) {
        for(Elevator elevator : elevators) {
            ElevatorStatus status = elevator.getStatus();
            if(status.currentFloor == floor && status.doors == Doors.OPEN) {
                return elevator;
            }
        }
        return null;
    }
}
