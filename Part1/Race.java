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
    private int raceLength;
    private Horse lane1Horse;
    private Horse lane2Horse;
    private Horse lane3Horse;

    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     * 
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public Race(int distance) throws NumberFormatException
    {
        // initialise instance variables
        raceLength = distance;
        lane1Horse = null;
        lane2Horse = null;
        lane3Horse = null;
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
        } 

        // prints if the user was wrong about their prediction
        if (raceWonBy(lane1Horse) && !lane1Horse.getName().equals(predict)) {
            System.out.println("The winner is " + lane1Horse.getName() + " but you was wrong.");
        } else if (raceWonBy(lane2Horse) && !lane2Horse.getName().equals(predict)) {
            System.out.println("The winner is " + lane2Horse.getName() + " but you was wrong.");
        } else if (raceWonBy(lane3Horse) && !lane3Horse.getName().equals(predict)) {
            System.out.println("The winner is " + lane2Horse.getName() + " but you was wrong.");
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
        
        multiplePrint('=',raceLength+4); //top edge of track
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
        
        multiplePrint('=',raceLength+4); //bottom edge of track
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
        int spacesAfter = raceLength - theHorse.getDistanceTravelled();
        
        //print a | for the beginning of the lane
        System.out.print('|');
        
        //print the spaces before the horse
        multiplePrint(' ',spacesBefore);
        
        //if the horse has fallen then print dead
        //else print the horse's symbol
        if(theHorse.hasFallen())
        {
            System.out.print('\u274C'); //dead horse symbol
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

    public static String inputString(String message)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);
        // returns the user input
        return scanner.nextLine();
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

    public static int inputInt(String message) 
    {   
        // user input is taken by inputString()
        String string = inputString(message); 
        int numberinput = Integer.parseInt(string); 
        // string is converted into an integer

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
    public static double inputDouble(String message) 
    {   
        // user input is taken by inputString()
        String string = inputString(message); 
        double numberinput = Double.parseDouble(string); 
        // string is converted into a double

        return numberinput; 
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
    public static String prediction(String horsename1, String horsename2, String horsename3){ 
        String p = inputString("Which horse do you want to win? (" + horsename1 + ", " + horsename2 + " or " + horsename3 + "): ");

        while(!p.equals(horsename1) && !p.equals(horsename2) && !p.equals(horsename3)) {
            System.out.println("Invalid input. Please enter a valid horse name.");
            p = inputString("Which horse do you want to win? (" + horsename1 + ", " + horsename2 + " or " + horsename3 + "): ");
        }
        return p; // Return the valid horse name
    }

}
