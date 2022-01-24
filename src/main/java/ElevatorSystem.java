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
        // TODO: smarter assignment
        Elevator selectedElevator = elevators[0];

        int direction = to > from ? 1 : -1;
        selectedElevator.newWaitingPerson(from, direction);
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
