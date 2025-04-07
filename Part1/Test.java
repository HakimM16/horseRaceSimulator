import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws EmptyStringException {

        try {
            // Users can create three horses with different horse names, confidence levels will be random and symbols are fixed
            System.out.println("Welcome to the horse race stimulator!");
            String l_str = inputString("Enter the length of the race: ");

            double length = Double.parseDouble(l_str); // convert string to double

            Race race = new Race(length);
            

            System.out.println("Please enter the horse names for three horses.");

            String horseName1 = inputString("Enter the name of horse 1: ");
            String horseName2 = inputString("Enter the name of horse 2: ");
            String horseName3 = inputString("Enter the name of horse 3: ");

            double confidence1 = inputDouble("Enter the confidence level of horse 1 (0.1 - 1.0)"); // Random confidence level between 0.0 and 1.0
            double confidence2 = inputDouble("Enter the confidence level of horse 2 (0.1 - 1.0)");// Random confidence level between 0.0 and 1.0
            double confidence3 = inputDouble("Enter the confidence level of horse 3 (0.1 - 1.0)"); // Random confidence level between 0.0 and 1.0

            char symbol1 = inputChar("Enter the symbol for horse 1 (first character of input is taken as the symbol): "); 
            char symbol2 = inputChar("Enter the symbol for horse 2 (first character of input is taken as the symbol): ");
            char symbol3 = inputChar("Enter the symbol for horse 3 (first character of input is taken as the symbol): ");

            Horse horse1 = new Horse(symbol1, horseName1, confidence1); // white horse, symbol: \u2658
            Horse horse2 = new Horse(symbol2, horseName2, confidence2); // white king, symbol: \u265A
            Horse horse3 = new Horse(symbol3, horseName3, confidence3); // white rook, symbol: \u2656

            String predict = prediction(horseName1, horseName2, horseName3); // Get the user's prediction

            print("Horse 1: " + horseName1 + " with confidence level: " + confidence1);
            print("Horse 2: " + horseName2 + " with confidence level: " + confidence2);
            print("Horse 3: " + horseName3 + " with confidence level: " + confidence3);
            print("Horse 1 symbol: " + horse1.getSymbol());
            print("Horse 2 symbol: " + horse2.getSymbol());
            print("Horse 3 symbol: " + horse3.getSymbol());

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
            System.out.println("The length of the race must be a number, not a string.");
            System.exit(1);
        } catch (LengthOfRaceException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (EmptyStringException e) { // Catching any other unexpected exceptions
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (SameNameException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (LongNameException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

    }

    public static String inputString(String message) throws EmptyStringException 
    { 
        
        // Scanner class is used to take input from the user
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);

        String input = scanner.nextLine(); // user input is taken
        // if the input is empty, an exception is thrown
        if (input.isEmpty()) {
            throw new EmptyStringException("Input cannot be empty.");
        }
        // if the input is not empty, it is returned
        return input;
    }

    public static char inputChar(String message) throws EmptyStringException
    {   
        // user input is taken by inputString()
        String string = inputString(message); 
        char charinput = string.charAt(0); 
        // first character of the string is taken as a char

        return charinput; 
    }

    public static int inputInt(String message) throws EmptyStringException
    {   
        // user input is taken by inputString()
        String string = inputString(message); 
        int numberinput = Integer.parseInt(string); 
        // string is converted into an integer

        return numberinput; 
    }

    public static double inputDouble(String message) throws EmptyStringException
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

    public static String prediction(String horsename1, String horsename2, String horsename3) throws EmptyStringException, SameNameException { 
        if (horsename1.equals(horsename3) || horsename1.equals(horsename2) || horsename2.equals(horsename3)) {
            throw new SameNameException("You can't have at least 2 horses of the same name");
        }

        String p = inputString("Which horse do you want to win? (" + horsename1 + ", " + horsename2 + " or " + horsename3 + "): ");

        while(!p.equals(horsename1) && !p.equals(horsename2) && !p.equals(horsename3)) {
            System.out.println("Invalid input. Please enter a valid horse name.");
            p = inputString("Which horse do you want to win? (" + horsename1 + ", " + horsename2 + " or " + horsename3 + "): ");
        }
        return p; // Return the valid horse name
    }
}