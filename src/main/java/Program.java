import java.util.Arrays;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        ElevatorSystem system = new ElevatorSystem(2);

        Scanner input = new Scanner(System.in);
        while(true) {
            String[] s = input.nextLine().split(" ");
            switch (s[0]) {
                case "person" -> system.addPerson(Integer.parseInt(s[1]), Integer.parseInt(s[2]));
                case "" -> system.nextStep();
            }
            System.out.println("Elevators:");
            Arrays.stream(system.getStatus()).forEach(System.out::println);
            if(!system.getPeople().isEmpty()) {
                System.out.println("People:");
            }
            system.getPeople().forEach(System.out::println);
        }
    }
}
