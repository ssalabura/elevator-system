import java.util.Arrays;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("How many? ");
        ElevatorSystem system = new ElevatorSystem(input.nextInt());

        while(true) {
            String[] s = input.nextLine().split(" ");
            if (s[0].equals("")) {
                system.nextStep();
            } else {
                system.addPerson(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
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
