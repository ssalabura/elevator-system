package model;

import java.util.ArrayList;
import java.util.List;

public class ElevatorSystem {
    private final ArrayList<Elevator> elevators;
    private final ArrayList<Person> people;
    private final int maxFloor;

    public ElevatorSystem(int elevators, int floors) {
        if(elevators < 1) {
            throw new IllegalArgumentException("At least 1 elevator is needed");
        }
        this.elevators = new ArrayList<>();
        for(int i=0; i<elevators; i++) {
            this.elevators.add(new Elevator(i));
        }
        people = new ArrayList<>();
        maxFloor = floors;
    }

    public void addPerson(int from, int to) {
        if(from < 0 || from > maxFloor || to < 0 || to > maxFloor) {
            throw new IllegalArgumentException("Invalid floor, should be between 0 and " + maxFloor);
        }
        Person new_person = new Person(this, from, to);
        people.add(new_person);
        findElevatorForPerson(new_person);
    }

    private void findElevatorForPerson(Person person) {
        Direction direction = person.getDirection();

        Elevator bestElevator = null;
        int bestDistance = (int) 1e9; // lower is better

        for(Elevator elevator : elevators) {
            ElevatorStatus status = elevator.getStatus();
            int elevator_distance = Math.abs(status.currentFloor - person.getFrom());
            if (elevator_distance < bestDistance && ((status.direction == direction && (
                    (direction == Direction.UP && status.currentFloor < person.getFrom()) ||
                    (direction == Direction.DOWN && status.currentFloor > person.getFrom()))) ||
                    (status.direction == Direction.IDLE && elevator.getQueueSize() == 0))) {
                bestDistance = elevator_distance;
                bestElevator = elevator;
            }
        }

        if (bestElevator != null) {
            person.assigned = bestElevator.getId();
            bestElevator.newWaitingPerson(person.getFrom(), direction);
        }
    }

    void removePerson(Person person) {
        people.remove(person);
    }

    public void nextStep() {
        ArrayList<Person> peopleCopy = new ArrayList<>(people);
        for(Person person : peopleCopy) {
            if(person.assigned == -1) {
                findElevatorForPerson(person);
            } else if(person.elevator == null) {
                // sometimes someone steals their elevator and we have to find a new one
                ElevatorStatus status = elevators.get(person.assigned).getStatus();
                if((status.direction == Direction.UP && status.currentFloor > person.getFrom()) ||
                        (status.direction == Direction.DOWN && status.currentFloor < person.getFrom()) ||
                        status.direction == Direction.IDLE) {
                    findElevatorForPerson(person);
                }
            }
            person.nextStep();
        }
        for(Elevator elevator : elevators) {
            elevator.nextStep();
        }
    }

    public List<ElevatorStatus> getStatus() {
        return elevators.stream().map(Elevator::getStatus).toList();
    }

    public List<Person> getPeople() {
        return people;
    }

    public String getFullStatus() {
        StringBuilder builder = new StringBuilder();
        builder.append("Elevators:\n");
        elevators.stream().map(Elevator::getStatus).forEach(elevatorStatus -> builder.append(elevatorStatus).append('\n'));
        if(!people.isEmpty()) {
            builder.append("People:\n");
        }
        people.forEach(person -> builder.append(person).append('\n'));
        return builder.toString();
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
