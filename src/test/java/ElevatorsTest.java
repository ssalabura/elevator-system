import model.ElevatorSystem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ElevatorsTest {
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 11, 22, 33, 10, 25, 50})
    @Timeout(5)
    public void allRandom(int shift) {
        ElevatorSystem system = new ElevatorSystem(5,100);
        int steps = 0;
        for(int i=0; i<100; i++) {
            int from = ((i+17)*shift)%100;
            int to = ((i+23)*shift)%100;
            if(from != to) {
                system.addPerson(from, to);
            }
            for(int j=0; j<i%4; j++) {
                system.nextStep();
                steps++;
            }
        }
        while(!system.getPeople().isEmpty()) {
            system.nextStep();
            steps++;
        }
//        System.out.println(shift + " finished in " + steps + " steps.");
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 11, 22, 33, 10, 25, 50})
    @Timeout(5)
    public void sameStart(int shift) {
        ElevatorSystem system = new ElevatorSystem(5,100);
        int steps = 0;
        for(int i=0; i<100; i++) {
            int from = shift%100;
            int to = ((i+17)*shift)%100;
            if(from != to) {
                system.addPerson(from, to);
            }
            for(int j=0; j<i%4; j++) {
                system.nextStep();
                steps++;
            }
        }
        while(!system.getPeople().isEmpty()) {
            system.nextStep();
            steps++;
        }
//        System.out.println(shift + " finished in " + steps + " steps.");
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 11, 22, 33, 10, 25, 50})
    @Timeout(5)
    public void sameEnd(int shift) {
        ElevatorSystem system = new ElevatorSystem(5,100);
        int steps = 0;
        for(int i=0; i<100; i++) {
            int from = ((i+17)*shift)%100;
            int to = shift%100;
            if(from != to) {
                system.addPerson(from, to);
            }
            for(int j=0; j<i%4; j++) {
                system.nextStep();
                steps++;
            }
        }
        while(!system.getPeople().isEmpty()) {
            system.nextStep();
            steps++;
        }
//        System.out.println(shift + " finished in " + steps + " steps.");
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 5, 7, 9})
    @Timeout(5)
    public void smallBuilding(int shift) {
        ElevatorSystem system = new ElevatorSystem(2,10);
        int steps = 0;
        for(int i=0; i<100; i++) {
            int from = ((i+3)*shift)%10;
            int to = ((i+5)*shift)%10;
            if(from != to) {
                system.addPerson(from, to);
            }
            for(int j=0; j<i%3; j++) {
                system.nextStep();
                steps++;
            }
        }
        while(!system.getPeople().isEmpty()) {
            system.nextStep();
            steps++;
        }
//        System.out.println(shift + " finished in " + steps + " steps.");
    }

    @Test
    public void sameFloors() {
        ElevatorSystem system = new ElevatorSystem(1,10);
        int steps = 0;
        for(int i=0; i<100; i++) {
            system.addPerson(i%10, i%10);
            int from = (i*3)%10;
            int to = (i*7)%10;
            if(from != to) {
                system.addPerson(from, to);
            }
            for(int j=0; j<i%3; j++) {
                system.nextStep();
                steps++;
            }
        }
        while(!system.getPeople().isEmpty()) {
            system.nextStep();
            steps++;
        }
//        System.out.println(shift + " finished in " + steps + " steps.");
    }
}
