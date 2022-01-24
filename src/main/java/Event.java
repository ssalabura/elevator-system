public class Event implements Comparable<Event> {
    int destination;
    int timestamp;
    static int counter = 0;

    Event(int destination) {
        this.destination = destination;
        timestamp = counter++;
    }

    @Override
    public int compareTo(Event event) {
        return Integer.compare(destination, event.destination);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return destination == event.destination;
    }
}
