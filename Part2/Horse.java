import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

// make a model of a horse with attributes and methods to set and get those attributes
public class Horse {
    private String name;
    private String breed;
    private String coatColor;
    private String symbol;
    private String saddle;
    private String horseshoe;
    private String accessory;
    
    // Attributes that could be affected by breed
    private int speed;
    private int stamina;
    private int confidence;
    private int newSpeed;
    private int newStamina;
    private int newConfidence;

    private double bettingOdd;


    // Maps to store attribute modifiers based on selections
    private static final Map<String, int[]> BREED_ATTRIBUTES = new HashMap<>();
    static {
        // Format: {speed, stamina, confidence}
        BREED_ATTRIBUTES.put("Thoroughbred", new int[]{8, 5, 6});
        BREED_ATTRIBUTES.put("Arabian", new int[]{7, 7, 6});
        BREED_ATTRIBUTES.put("Quarter Horse", new int[]{6, 6, 7});
        BREED_ATTRIBUTES.put("Appaloosa", new int[]{5, 8, 6});
        BREED_ATTRIBUTES.put("Mustang", new int[]{8, 7, 6});
    }

    private static final Map<String, int[]> HORSESHOE_ATTRIBUTES = new HashMap<>();
    static {
        // Format: {speed, stamina, confidence}
        HORSESHOE_ATTRIBUTES.put("Regular", new int[]{0, 0, 0});
        HORSESHOE_ATTRIBUTES.put("Lightweight", new int[]{2, -1, 0});
        HORSESHOE_ATTRIBUTES.put("Endurance", new int[]{-1, 2, 0});
        HORSESHOE_ATTRIBUTES.put("Confidence Booster", new int[]{0, 0, 2});
    }

    public Horse() {
        this.name = "Highlander"; // Default name
        this.breed = "Thoroughbred"; // Default breed
        this.coatColor = "Brown"; // Default coat color
        this.symbol = "H"; // Default symbol
        this.saddle = "Standard"; // Default saddle
        this.horseshoe = "Regular"; // Default horseshoe
        this.accessory = "None"; // Default accessory
        updateAttributes();
        //setOdd();
    }

    public Horse(String name, String coatColor, String symbol) {
        this.name = name;
        this.breed = "Thoroughbred"; // Default breed
        this.coatColor = coatColor; // Set coat color from parameter
        this.symbol = symbol; // Set symbol from parameter
        this.saddle = "Standard"; // Default saddle
        this.horseshoe = "Regular"; // Default horseshoe
        this.accessory = "None"; // Default accessory
        updateAttributes();
        setOdd();
    }

    private void updateAttributes() {
        // Set base attributes from breed
        int[] breedAttrs = BREED_ATTRIBUTES.getOrDefault(breed, new int[]{6, 6, 6});
        this.speed = breedAttrs[0];
        this.stamina = breedAttrs[1];
        this.confidence = breedAttrs[2];
        
        // Apply horseshoe modifiers
        int[] horseshoeModifiers = HORSESHOE_ATTRIBUTES.getOrDefault(horseshoe, new int[]{0, 0, 0});
        this.speed += horseshoeModifiers[0];
        this.stamina += horseshoeModifiers[1];
        this.confidence += horseshoeModifiers[2];
    }

    public String getName() { 
        return this.name; 
    }
    public void setName(String name) { 
        this.name = name; 
    }

    public String getBreed() { 
        return this.breed; 
    }
    public void setBreed(String breed) { 
        this.breed = breed; 
        updateAttributes();
    }

    public String getCoatColor() { 
        return this.coatColor; 
    }

    public void setCoatColor(String coatColor) { 
        this.coatColor = coatColor; 
    }
    
    public String getSymbol() { 
        return this.symbol; 
    }

    public void setSymbol(String symbol) { 
        this.symbol = symbol; 
    }
    
    public String getSaddle() { 
        return this.saddle; 
    }

    public void setSaddle(String saddle) { 
        this.saddle = saddle; 
    }
    
    public String getHorseshoe() { 
        return this.horseshoe; 
    }

    public void setHorseshoe(String horseshoe) { 
        this.horseshoe = horseshoe; 
        updateAttributes();
    }
    
    public String getAccessory() { 
        return this.accessory; 
    }

    public void setAccessory(String accessory) { 
        this.accessory = accessory; 
    }

    public double getOdd() { 
        return this.bettingOdd; 
    }
    public void setOdd() { 
        // make betting odd based on attributes
        // max odd is 3 and min is 0
        double odds = getConfidence() * 0.5 + getStamina() * 0.5 + getSpeed() * 0.2; // Example formula
        System.out.println("Betting odds for " + this.name + ": " + odds);
        double odd_rounded = Math.round(odds * 100.0) / 100.0; // Round to 2 decimal places
        this.bettingOdd = odd_rounded;
    }

    public void setAttributes(int speed, int stamina, int confidence, String weather) {
        /** weather conditions
         * Sunny:
         * - Speed: +2 - stamina: +1 - confidence: +2
         * Rainy:
         * - Speed: -1 - stamina: +1 - confidence: -1
         * snowy:
         * - Speed: -3 - stamina: -2 - confidence: -2
         * foggy:
         * - Speed: -1 - stamina: +1 - confidence: 0
         * windy:
         * - Speed: -2 - stamina: +1 - confidence: 0
         * muddy:
         * - Speed: -2 - stamina: -1 - confidence: -1
         * dry:
         * - Speed: +1 - stamina: +1 - confidence: +1
         * wet:
         * - Speed: -1 - stamina: +1 - confidence: 0
         * icy:
         * - Speed: -3 - stamina: -2 - confidence: -3
         */
        switch (weather) {
            case "sunny":
                speed += 2;
                stamina += 1;
                confidence += 2;
                this.bettingOdd += 0.3; // Increase odds for sunny weather
                break;
            case "rainy":
                speed -= 1;
                stamina += 1;
                confidence -= 1;
                this.bettingOdd -= 0.2; // Decrease odds for rainy weather
                break;
            case "snowy":
                speed -= 6;
                stamina -= 4;
                confidence -= 4;
                this.bettingOdd -= 0.7; // Decrease odds for snowy weather
                break;
            case "foggy":
                speed -= 1;
                stamina += 1;
                confidence += 0;
                this.bettingOdd -= 0.1; // Decrease odds for foggy weather
                break;
            case "windy":
                speed -= 2;
                stamina += 1;
                confidence += 0;
                this.bettingOdd -= 0.3; // Decrease odds for windy weather
                break;
            case "muddy":
                speed -= 2;
                stamina -= 1;
                confidence -= 1;
                this.bettingOdd -= 0.5; // Decrease odds for muddy weather
                break;
            case "dry":
                speed += 1;
                stamina += 1;
                confidence += 1;
                this.bettingOdd += 0.1; // Increase odds for dry weather
                break; 
            case "wet":
                speed -= 1; 
                stamina += 1; 
                confidence += 0; 
                this.bettingOdd -= 0.1; // Decrease odds for wet weather
                break; 
            case "icy":
                speed -= 5; 
                stamina -= 2; 
                confidence -= 4; 
                this.bettingOdd -= 0.8; // Decrease odds for icy weather
                break;
        }
        this.newSpeed = speed;
        this.newStamina = stamina;
        this.newConfidence = confidence;
    }
    
    public int getSpeed() { 
        return this.speed; 
    }

    public int getStaminaRaw() { 
        return this.stamina; 
    }

    public double getStamina() { 
        double d_stamina = this.stamina / 10.0; // Convert to a scale of 0-1
        if (d_stamina > 1) {
            d_stamina = 1; // Cap at 1
        } else if (d_stamina < 0) {
            d_stamina = 0; // Cap at 0
        }
        return d_stamina; 
    }
    

    public int getConfidenceRaw() { 
        return this.confidence; 
    }

    public double getConfidence() { 
        double d_confidence = this.confidence / 10.0; // Convert to a scale of 0-1
        if (d_confidence > 1) {
            d_confidence = 1; // Cap at 1
        } else if (d_confidence < 0) {
            d_confidence = 0; // Cap at 0
        }
        return d_confidence; 
    }

    public Color getColourFromString(String color) {
        switch (color) {
            case "Brown":
                return new Color(165, 42, 42); // RGB for Brown
            case "Black":
                return Color.BLACK;
            case "White":
                return Color.WHITE;
            case "Grey":
                return Color.GRAY;
            case "Chestnut":
                return new Color(139, 69, 19); // RGB for Chestnut
            case "Palomino":
                return new Color(207, 180, 122); // RGB for Palomino
            case "Pinto":
                return new Color(128, 0, 0); // RGB for Pinto
                
            default:
                return Color.WHITE; // Default color if not found
        }
    }
    
    @Override
    public String toString() {
        return "Horse [name=" + name + ", breed=" + breed + ", color=" + coatColor +
                ", symbol=" + symbol + ", attributes={speed=" + newSpeed + 
                ", stamina=" + newStamina + ", confidence=" + newConfidence + "}, " + "bettingOdd=" + bettingOdd + "]";
    }
}