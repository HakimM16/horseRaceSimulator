
/**
 * Write a description of class Horse here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Horse
{
    //Fields of class Horse
    String name;
    char symbol;
    int distance;
    boolean hasFallen = false;
    double confidence;
    
      
    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */
    public Horse(char horseSymbol, String horseName, double horseConfidence) throws rangeOfConfidenceException, CheckArgumentException
    {
        
        // Check if the horseName is a valid string
        if (horseName == null || horseName.isEmpty()) {
            throw new CheckArgumentException("Horse name cannot be null or empty");
        }

        // check if the horseSymbol is null or empty or a number
        if (horseSymbol == '\0' || horseSymbol == ' ') {
            throw new CheckArgumentException("Horse symbol cannot be null or empty");
        } else if (horseSymbol >= '0' && horseSymbol <= '9') {
            throw new CheckArgumentException("Horse symbol cannot be a number");
        }

        // Check if the horseName contains only letters
        for (char c : horseName.toCharArray()) {
            if (!Character.isLetter(c)) {
                throw new CheckArgumentException("The horse name (" + horseName + ") must contain only letters");
            }
        }

        // Check if the confidence is within the range of 0.0 to 1.0
        if (horseConfidence < 0.0 || horseConfidence > 1.0) {
            throw new rangeOfConfidenceException("Confidence must be between 0.0 and 1.0");
        }

        // check if the confidence is a double, not a string or char
        if (horseConfidence != (double) horseConfidence) {
            throw new CheckArgumentException("Confidence must be a double");
        }


        
    // Initialise instance variables
       this.symbol = horseSymbol;
       this.name = horseName;
       this.confidence = horseConfidence;
    }
    
    
    
    //Other methods of class Horse
    public void fall()
    {
        this.hasFallen = true;
    }
    
    public double getConfidence()
    {
        return this.confidence;
    }
    
    public int getDistanceTravelled()
    {
        return this.distance;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public char getSymbol()
    {
        return this.symbol;
    }
    
    public void goBackToStart()
    {
        this.distance = 0;
        this.hasFallen = false;
    }
    
    public boolean hasFallen()
    {
        if (this.hasFallen) {
            return true;
        }
        return false;
    }

    public void moveForward()
    {
        this.distance += 1;
    }

    public void setConfidence(double newConfidence)
    {
        this.confidence = newConfidence;
    }
    
    public void setSymbol(char newSymbol)
    {
        this.symbol = newSymbol;
    }
    
}
