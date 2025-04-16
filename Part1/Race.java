import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * A three-horse race, each horse running in its own lane
 * for a given distance
 * 
 * @author McRaceface
 * @version 1.0
 */
public class Race
{
    private double raceLength;
    private Horse lane1Horse;
    private Horse lane2Horse;
    private Horse lane3Horse;
    static int winners = 0; // variable to count the number of winners

    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     * 
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public Race(double distance) throws NumberFormatException, LengthOfRaceException
    {
        // initialise instance variables
        raceLength = distance;
        lane1Horse = null;
        lane2Horse = null;
        lane3Horse = null;

        // checks if raceLength is under the max length
        if (distance > 175) {
            throw new LengthOfRaceException("The length of the race can't be more than 175");
        } else if (distance < 1) {
            throw new LengthOfRaceException("The length of the race can't be less than 1");
        }
    }

    // add main method to test the class
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

            // check for same horse name
            checkHorseName(horseName1, horseName2, horseName3);

            // remove spaces from the horse names
            horseName1 = horseName1.replaceAll("\\s+", "");
            horseName2 = horseName2.replaceAll("\\s+", "");
            horseName3 = horseName3.replaceAll("\\s+", "");

            double confidence1 = inputDouble("Enter the confidence level of horse 1 (0.1 - 1.0)"); // Random confidence level between 0.0 and 1.0
            double confidence2 = inputDouble("Enter the confidence level of horse 2 (0.1 - 1.0)");// Random confidence level between 0.0 and 1.0
            double confidence3 = inputDouble("Enter the confidence level of horse 3 (0.1 - 1.0)"); // Random confidence level between 0.0 and 1.0
            
            // round confidence level up to 1 decimal place
            confidence1 = Math.round(confidence1 * 10.0) / 10.0;
            confidence2 = Math.round(confidence2 * 10.0) / 10.0;
            confidence3 = Math.round(confidence3 * 10.0) / 10.0;
            

            char symbol1 = inputChar("Enter the symbol for horse 1 (first character of input is taken as the symbol): "); 
            char symbol2 = inputChar("Enter the symbol for horse 2 (first character of input is taken as the symbol): ");
            char symbol3 = inputChar("Enter the symbol for horse 3 (first character of input is taken as the symbol): ");
            
            // check for same symbol
            checkSymbol(symbol1, symbol2, symbol3);

            Horse horse1 = new Horse(symbol1, horseName1, confidence1); // white horse, symbol: \u2658
            Horse horse2 = new Horse(symbol2, horseName2, confidence2); // white king, symbol: \u265A
            Horse horse3 = new Horse(symbol3, horseName3, confidence3); // white rook, symbol: \u2656

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

    // inputString()

    /*
    how it works:
    - it takes in input by the user and returns it

    implementation:
    - the method takes in the string argument message
    - The input function scanner is created
    - message is outputted in system.out.println
    - The user makes an input using scanner.nextLine() and the value is returned
    */
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

    // inputInt()

    /*
    how it works:
    - it takes in input by the user and returns it in an integer type

    implementation:
    - the method takes in the string argument message
    - The string variable string is initialised to the value of inputInt() with the argument message
    - The int variable numberinput is initialised to the value of string converted into an integer type
    - then, the method returns number input
    */
    public static int inputInt(String message) throws EmptyStringException
    {   
        // user input is taken by inputString()
        String string = inputString(message); 
        int numberinput = Integer.parseInt(string); 
        // string is converted into an integer

        return numberinput; 
    }


    // inputDouble()
    /*
    how it works:
    - it takes in input by the user and returns it in a double type
    implementation:
    - the method takes in the string argument message
    - The string variable string is initialised to the value of inputInt() with the argument message
    - The double variable numberinput is initialised to the value of string converted into a double type
    - then, the method returns number input
    */
    public static double inputDouble(String message) throws EmptyStringException
    {   
        // user input is taken by inputString()
        String string = inputString(message); 
        double numberinput = Double.parseDouble(string); 
        // string is converted into a double

        return numberinput; 
    }

    // print()
    /*
    how it works:
    - it takes in input by the user and returns it
    implementation:
    - the method takes in the string argument message
    - The input function scanner is created
    - message is outputted in system.out.println
    - The user makes an input using scanner.nextLine() and the value is returned
    */
    public static void print(String message) {
        System.out.println(message);
    }

    // prediction()
    /*
    how it works:
    - it takes in input by the user and returns it
    implementation:
    - the method takes in the string argument message
    - The input function scanner is created
    - message is outputted in system.out.println
    - It keeps on asking the user for input until a valid horse name is given
    - The user makes an input using scanner.nextLine() and the value is returned
    */
    public static String prediction(String horsename1, String horsename2, String horsename3) throws EmptyStringException { 
        String p = inputString("Which horse do you want to win? (" + horsename1 + ", " + horsename2 + " or " + horsename3 + "): ");

        while(!p.equals(horsename1) && !p.equals(horsename2) && !p.equals(horsename3)) {
            System.out.println("Invalid input. Please enter a valid horse name.");
            p = inputString("Which horse do you want to win? (" + horsename1 + ", " + horsename2 + " or " + horsename3 + "): ");
        }
        return p; // Return the valid horse name
    }

    // check for same symbol
    public static void checkSymbol(char symbol1, char symbol2, char symbol3) throws SameNameException {
        if (symbol1 == symbol2 || symbol1 == symbol3 || symbol2 == symbol3) {
            throw new SameNameException("You can't have at least 2 horses of the same symbol");
        }
    }
    
    // check for same horse name
    public static void checkHorseName(String horseName1, String horseName2, String horseName3) throws SameNameException {
        if (horseName1.equals(horseName2) || horseName1.equals(horseName3) || horseName2.equals(horseName3)) {
            throw new SameNameException("You can't have at least 2 horses of the same name");
        }
    }
    
    /**
     * Adds a horse to the race in a given lane
     * 
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public void addHorse(Horse theHorse, int laneNumber)
    {
        if (laneNumber == 1)
        {
            lane1Horse = theHorse;
        }
        else if (laneNumber == 2)
        {
            lane2Horse = theHorse;
        }
        else if (laneNumber == 3)
        {
            lane3Horse = theHorse;
        }
        else
        {
            System.out.println("Cannot add horse to lane " + laneNumber + " because there is no such lane");
        }
    }
    
    /**
     * Start the race
     * The horse are brought to the start and
     * then repeatedly moved forward until the 
     * race is finished
     */
    public void startRace(String predict)
    {
        //declare a local variable to tell us when the race is finished
        boolean finished = false;
        
        //reset all the lanes (all horses not fallen and back to 0). 
        lane1Horse.goBackToStart();
        lane2Horse.goBackToStart();
        lane3Horse.goBackToStart();
                      
        while (!finished)
        {
            //move each horse
            moveHorse(lane1Horse);
            moveHorse(lane2Horse);
            moveHorse(lane3Horse);
                        
            //print the race positions
            printRace();
            
            //if any of the three horses has won the race is finished
            if ( raceWonBy(lane1Horse) || raceWonBy(lane2Horse) || raceWonBy(lane3Horse) )
            {
                winners = 1; // increment the number of winners
                if (raceWonBy(lane1Horse) && raceWonBy(lane2Horse)) {
                    winners = 2; // increment the number of winners
                } else if (raceWonBy(lane1Horse) && raceWonBy(lane3Horse)) {
                    winners = 2; // increment the number of winners
                } else if (raceWonBy(lane2Horse) && raceWonBy(lane3Horse)) {
                    winners = 2; // increment the number of winners
                }
                
                if (raceWonBy(lane1Horse) && raceWonBy(lane2Horse) && raceWonBy(lane3Horse)) {
                    winners = 3; // increment the number of winners
                }
                finished = true;
            } else if (lane1Horse.hasFallen() && lane2Horse.hasFallen() && lane3Horse.hasFallen()) {
                // Made an if-statement to check if all horses have fallen
                System.out.println("All horses have fallen. No winner.");
                finished = true;
            } 
           
            //wait for 100 milliseconds
            try{ 
                TimeUnit.MILLISECONDS.sleep(100);
            }catch(Exception e){}
        }
    
        if (winners == 1) {
                // prints the winner and if the user was right
            if (raceWonBy(lane1Horse) && lane1Horse.getName().equals(predict)) {
                System.out.println("The winner is " + lane1Horse.getName() + "!");
                System.out.println("Congratulations! You was right.");
            } else if (raceWonBy(lane2Horse) && lane2Horse.getName().equals(predict)) {
                System.out.println("The winner is " + lane2Horse.getName() + "!");
                System.out.println("Congratulations! You was right.");
            } else if (raceWonBy(lane3Horse) && lane3Horse.getName().equals(predict)) {
                System.out.println("The winner is " + lane3Horse.getName() + "!");
                System.out.println("Congratulations! You was right.");
            } // prints if the user was wrong about their prediction
            else if (raceWonBy(lane1Horse) && !lane1Horse.getName().equals(predict)) {
                System.out.println("The winner is " + lane1Horse.getName() + " but you was wrong.");
            } else if (raceWonBy(lane2Horse) && !lane2Horse.getName().equals(predict)) {
                System.out.println("The winner is " + lane2Horse.getName() + " but you was wrong.");
            } else if (raceWonBy(lane3Horse) && !lane3Horse.getName().equals(predict)) {
                System.out.println("The winner is " + lane3Horse.getName() + " but you was wrong.");
            }
        } else if (winners == 2) {
            // prints if the user is right but it was a draw
            if (raceWonBy(lane1Horse) && raceWonBy(lane2Horse) && lane1Horse.getName().equals(predict)) { // 1 and 2 and 1 is the winner
                System.out.println("The winner is " + lane1Horse.getName() + " and " + lane2Horse.getName() + " and you was right.");
            } else if (raceWonBy(lane1Horse) && raceWonBy(lane3Horse) && lane1Horse.getName().equals(predict)) { // 1 and 3 and 1 is the winner
                System.out.println("The winner is " + lane1Horse.getName() + " and " + lane3Horse.getName() + " and you was right.");
            } else if (raceWonBy(lane2Horse) && raceWonBy(lane3Horse) && lane2Horse.getName().equals(predict)) { // 2 and 3 and 2 is the winner
                System.out.println("The winner is " + lane2Horse.getName() + " and " + lane3Horse.getName() + " and you was right.");
            } else if (raceWonBy(lane1Horse) && raceWonBy(lane2Horse) && raceWonBy(lane3Horse)) {
                System.out.println("The winner is " + lane1Horse.getName() + ", " + lane2Horse.getName() + " and " + lane3Horse.getName() + " and you was right.");
            } // prints if the user is wrong but it was a draw
            else if (raceWonBy(lane1Horse) && raceWonBy(lane2Horse) && lane3Horse.getName().equals(predict)) { // 1 and 2 and predicted 3
                System.out.println("The winner is " + lane1Horse.getName() + " and " + lane2Horse.getName() + " but you was wrong.");
            } else if (raceWonBy(lane1Horse) && raceWonBy(lane3Horse) && lane2Horse.getName().equals(predict)) { // 1 and 3 and predicted 2
                System.out.println("The winner is " + lane1Horse.getName() + " and " + lane3Horse.getName() + " but you was wrong.");
            } else if (raceWonBy(lane2Horse) && raceWonBy(lane3Horse) && lane1Horse.getName().equals(predict)) { // 2 and 3 and predicted 1
                System.out.println("The winner is " + lane2Horse.getName() + " and " + lane3Horse.getName() + " but you was wrong.");
            } 
        } else if (winners == 3) {
            // prints if the user is right but it was a draw
            System.out.println("The winner is " + lane1Horse.getName() + ", " + lane2Horse.getName() + " and " + lane3Horse.getName() + " and you was right.");
        }
    }
    
    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     * 
     * @param theHorse the horse to be moved
     */
    private void moveHorse(Horse theHorse)
    {
        //if the horse has fallen it cannot move, 
        //so only run if it has not fallen
        if  (!theHorse.hasFallen())
        {
            //the probability that the horse will move forward depends on the confidence;
            if (Math.random() < theHorse.getConfidence())
            {
               theHorse.moveForward();
            }

            // If statement that increase confidence if horse wins
            if (theHorse.getDistanceTravelled() == raceLength) {
                double newConfidence = Math.round((theHorse.getConfidence() + 0.1) * 10.0) / 10.0; 
                theHorse.setConfidence(newConfidence); 
            }
            
            //the probability that the horse will fall is very small (max is 0.1)
            //but will also will depends exponentially on confidence 
            //so if you double the confidence, the probability that it will fall is *2
            if (Math.random() < (0.1*theHorse.getConfidence()*theHorse.getConfidence()))
            {
                theHorse.fall();
                double newConfidence = Math.round((theHorse.getConfidence() - 0.1) * 10.0) / 10.0; //decrease confidence if horse falls
                theHorse.setConfidence(newConfidence); 
            }
        } 
    }
        
    /** 
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean raceWonBy(Horse theHorse)
    {
        if (theHorse.getDistanceTravelled() == raceLength)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /***
     * Print the race on the terminal
     */
    private void printRace()
    {
        System.out.print('\u000C');  //clear the terminal window
        
        multiplePrint('=',(int) raceLength+3); //top edge of track
        System.out.println();
        
        // print the lanes for each horse
        // and the horse's name and confidence rating
        printLane(lane1Horse);
        System.out.print(" ");
        System.out.print(lane1Horse.getName() + " (Current confidence " + lane1Horse.getConfidence() + ")");
        System.out.println();
        
        printLane(lane2Horse);
        System.out.print(" ");
        System.out.print(lane2Horse.getName() + " (Current confidence " + lane2Horse.getConfidence() + ")");
        System.out.println();
        
        printLane(lane3Horse);
        System.out.print(" ");
        System.out.print(lane3Horse.getName() + " (Current confidence " + lane3Horse.getConfidence() + ")");
        System.out.println();
        
        multiplePrint('=',(int) raceLength+3); //bottom edge of track
        System.out.println();    
    }
    
    /**
     * print a horse's lane during the race
     * for example
     * |           X                      |
     * to show how far the horse has run
     */
    private void printLane(Horse theHorse)
    {
        //calculate how many spaces are needed before
        //and after the horse
        int spacesBefore = theHorse.getDistanceTravelled();
        int spacesAfter = (int) raceLength - theHorse.getDistanceTravelled();
        
        //print a | for the beginning of the lane
        System.out.print('|');
        
        //print the spaces before the horse
        multiplePrint(' ',spacesBefore);
        
        //if the horse has fallen then print dead
        //else print the horse's symbol
        if(theHorse.hasFallen())
        {
            System.out.print('\u0078'); //dead horse symbol
        }
        else
        {
            System.out.print(theHorse.getSymbol());
        }
        
        //print the spaces after the horse
        multiplePrint(' ',spacesAfter);
        
        //print the | for the end of the track
        System.out.print('|');
    }
        
    
    /***
     * print a character a given number of times.
     * e.g. printmany('x',5) will print: xxxxx
     * 
     * @param aChar the character to Print
     */
    private void multiplePrint(char aChar, int times)
    {
        int i = 0;
        while (i < times)
        {
            System.out.print(aChar);
            i = i + 1;
        }
    }

}
