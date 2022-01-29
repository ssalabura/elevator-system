public class ElevatorStatus {
    public int id;
    public int currentFloor;
    public int destinationFloor;
    public Doors doors;
    public Direction direction;

    ElevatorStatus(int id, int currentFloor, int destinationFloor, Doors doors, Direction direction) {
        this.id = id;
        this.currentFloor = currentFloor;
        this.destinationFloor = destinationFloor;
        this.doors = doors;
        this.direction = direction;
    }

    @Override
    public String toString() {
        char dir = ' ';
        switch(direction) {
            case UP -> dir = '^';
            case DOWN -> dir = 'v';
            case IDLE -> dir = '.';
        }
        return "{" + id + ", " + currentFloor + " -> " + destinationFloor + ", " +
                (doors == Doors.OPEN ? "[  ]" : "####") + ", " + dir + "}";
    }
}
