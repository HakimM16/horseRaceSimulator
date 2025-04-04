import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        // Users can create three horses with different horse names, confidence levels will be random and symbols are fixed
        System.out.println("Welcome to the horse race stimulator!");
        String l_str = inputString("Enter the length of the race: ");

        System.out.println("Please enter the horse names for three horses.");

        String horseName1 = inputString("Enter the name of horse 1: ");
        String horseName2 = inputString("Enter the name of horse 2: ");
        String horseName3 = inputString("Enter the name of horse 3: ");

        double confidence1 = inputDouble("Enter the confidence level of horse 1 (0.0 - 1.0)"); // Random confidence level between 0.0 and 1.0
        double confidence2 = inputDouble("Enter the confidence level of horse 2 (0.0 - 1.0)");// Random confidence level between 0.0 and 1.0
        double confidence3 = inputDouble("Enter the confidence level of horse 2 (0.0 - 1.0)"); // Random confidence level between 0.0 and 1.0

        try {
            int length = Integer.parseInt(l_str); // Convert the string to an integer

            Race race = new Race(length);
            Horse horse1 = new Horse('\u2658', horseName1, confidence1); // white horse
            Horse horse2 = new Horse('\u265A', horseName2, confidence2); // white king
            Horse horse3 = new Horse('\u2656', horseName3, confidence3); // white rook

            print("Horse 1: " + horseName1 + " with confidence level: " + confidence1);
            print("Horse 2: " + horseName2 + " with confidence level: " + confidence2);
            print("Horse 3: " + horseName3 + " with confidence level: " + confidence3);
            print("Horse 1 symbol: " + horse1.getSymbol());
            print("Horse 2 symbol: " + horse2.getSymbol());
            print("Horse 3 symbol: " + horse3.getSymbol());

            String predict = prediction(horseName1, horseName2, horseName3); // Get the user's prediction

            race.addHorse(horse1, 1);
            race.addHorse(horse2, 2);
            race.addHorse(horse3, 3);

            race.startRace(predict);
        } catch (rangeOfConfidenceException e) { // Catching the exception for confidence level range
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (CheckArgumentException e) { // Catching the exception for invalid horse name
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (NumberFormatException e ) { // Catching the exception of invalid number format for length of race
            System.out.println("The length of the race must be a number.");
            System.exit(1);
        } catch (Exception e) { // Catching any other unexpected exceptions
            System.out.println("An unexpected error occurred: " + e.getMessage());
            System.exit(1);
        }

    }

    public static String inputString(String message)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);
        return scanner.nextLine();
    }

    public static int inputInt(String message) 
    {   
        // user input is taken by inputString()
        String string = inputString(message); 
        int numberinput = Integer.parseInt(string); 
        // string is converted into an integer

        return numberinput; 
    }

    public static double inputDouble(String message) 
    {   
        // user input is taken by inputString()
        String string = inputString(message); 
        double numberinput = Double.parseDouble(string); 
        // string is converted into a double

        return numberinput; 
    }

    public static void print(String message) {
        System.out.println(message);
    }

    public static String prediction(String horsename1, String horsename2, String horsename3){ 
        String p = inputString("Which horse do you want to win? (" + horsename1 + ", " + horsename2 + " or " + horsename3 + "): ");

        while(!p.equals(horsename1) && !p.equals(horsename2) && !p.equals(horsename3)) {
            System.out.println("Invalid input. Please enter a valid horse name.");
            p = inputString("Which horse do you want to win? (" + horsename1 + ", " + horsename2 + " or " + horsename3 + "): ");
        }
        return p; // Return the valid horse name
    }
}