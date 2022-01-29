import java.util.ArrayList;
import java.util.Arrays;

public class ElevatorSystem {
    private final Elevator[] elevators;
    private final ArrayList<Person> people;
    private int max_floor;

    ElevatorSystem(int elevators, int floors) {
        if(elevators < 1) {
            throw new IllegalArgumentException("At least 1 elevator is needed");
        }
        this.elevators = new Elevator[elevators];
        for(int i=0; i<elevators; i++) {
            this.elevators[i] = new Elevator(i);
        }
        people = new ArrayList<>();
        max_floor = floors;
    }

    public void addPerson(int from, int to) {
        if(from < 0 || from > max_floor || to < 0 || to > max_floor) {
            throw new IllegalArgumentException("Invalid floor, should be between 0 and " + max_floor);
        }
        Person new_person = new Person(this, from, to);
        people.add(new_person);
        findElevatorForPerson(new_person);
    }

    void findElevatorForPerson(Person person) {
        Direction direction = person.getDirection();

        Elevator best_elevator = null;
        int best_distance = (int) 1e9; // lower is better

        for(Elevator elevator : elevators) {
            ElevatorStatus status = elevator.getStatus();
            if((status.direction == direction && status.currentFloor < person.getFrom()) ||
                    (status.direction == Direction.IDLE && elevator.getQueueSize() == 0)) {
                int elevator_distance = Math.abs(status.currentFloor - person.getFrom());
                if(elevator_distance < best_distance) {
                    best_distance = elevator_distance;
                    best_elevator = elevator;
                }
            }
        }

        if (best_elevator != null) {
            person.assigned = true;
            best_elevator.newWaitingPerson(person.getFrom(), direction);
        }
    }

    void removePerson(Person person) {
        people.remove(person);
    }

    public void nextStep() {
        ArrayList<Person> peopleCopy = new ArrayList<>(people);
        for(Person person : peopleCopy) {
            if(!person.assigned) {
                findElevatorForPerson(person);
            }
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

    Elevator[] getOpenElevators(int floor) {
        ArrayList<Elevator> list = new ArrayList<>();
        for(Elevator elevator : elevators) {
            ElevatorStatus status = elevator.getStatus();
            if(status.currentFloor == floor && status.doors == Doors.OPEN) {
                list.add(elevator);
            }
        }
        return list.toArray(Elevator[]::new);
    }
}
