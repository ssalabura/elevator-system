public class ElevatorStatus {
    public int id;
    public int currentFloor;
    public int destinationFloor;
    public Doors doors;

    ElevatorStatus(int id, int currentFloor, int destinationFloor, Doors doors) {
        this.id = id;
        this.currentFloor = currentFloor;
        this.destinationFloor = destinationFloor;
        this.doors = doors;
    }

    @Override
    public String toString() {
        return "{" + id + ", " + currentFloor + " -> " + destinationFloor + ", " +
                (doors == Doors.OPEN ? "[  ]" : "####") + "}";
    }
}
