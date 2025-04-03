import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Race race = new Race(20);
        /*
        Planning:
        - For the try-catch block, allow the user to create three horses with different confidence levels✅
        - give the user to choose which horse to bet on ✅
        - Once race is done, display whether the chosen horse has won or not ✅
        - Add more exceptions for invalid input (e.g. number instead of string, char instead of string) ✅
         */

        // Users can create three horses with different horse names, confidence levels will be random and symbols are fixed
        System.out.println("Welcome to the horse race stimulator!");
        System.out.println("Please enter the horse names for three horses.");

        String horseName1 = inputString("Enter the name of horse 1: ");
        String horseName2 = inputString("Enter the name of horse 2: ");
        String horseName3 = inputString("Enter the name of horse 3: ");

        double confidence1 = Math.round(Math.random() * 10.0) / 10.0; // Random confidence level between 0.0 and 1.0
        double confidence2 = Math.round(Math.random() * 10.0) / 10.0;// Random confidence level between 0.0 and 1.0
        double confidence3 = Math.round(Math.random() * 10.0) / 10.0; // Random confidence level between 0.0 and 1.0

        try {
            Horse horse1 = new Horse('\u2658', horseName1, confidence1); // white horse
            Horse horse2 = new Horse('\u265A', horseName2, confidence2); // white king
            Horse horse3 = new Horse('\u2656', horseName3, confidence3); // white rook

            String predict = inputString("Which horse do you want to win? (" + horseName1 + ", " + horseName2 + " or " + horseName3 + "): ");

            race.addHorse(horse1, 1);
            race.addHorse(horse2, 2);
            race.addHorse(horse3, 3);

            race.startRace(predict);
        } catch (rangeOfConfidenceException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (CheckArgumentException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

    }

    public static String inputString(String message)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);
        return scanner.nextLine();
    }
}