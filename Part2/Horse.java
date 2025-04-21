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

    // Maps to store attribute modifiers based on selections
    private static final Map<String, int[]> BREED_ATTRIBUTES = new HashMap<>();
    static {
        // Format: {speed, stamina, confidence}
        BREED_ATTRIBUTES.put("Thoroughbred", new int[]{10, 7, 8});
        BREED_ATTRIBUTES.put("Arabian", new int[]{9, 9, 8});
        BREED_ATTRIBUTES.put("Quarter Horse", new int[]{8, 8, 9});
        BREED_ATTRIBUTES.put("Appaloosa", new int[]{7, 10, 8});
        BREED_ATTRIBUTES.put("Mustang", new int[]{8, 9, 10});
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
        this.name = "highlander"; // Default name
        this.breed = "Thoroughbred"; // Default breed
        this.coatColor = "Brown"; // Default coat color
        this.symbol = "H"; // Default symbol
        this.saddle = "Standard"; // Default saddle
        this.horseshoe = "Regular"; // Default horseshoe
        this.accessory = "None"; // Default accessory
        updateAttributes();
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
    }

    private void updateAttributes() {
        // Set base attributes from breed
        int[] breedAttrs = BREED_ATTRIBUTES.getOrDefault(breed, new int[]{8, 8, 8});
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
    
    public int getSpeed() { 
        return this.speed; 
    }

    public int getStamina() { 
        return this.stamina; 
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
                return new Color(255, 228, 181); // RGB for Palomino
            case "Pinto":
                return new Color(128, 0, 0); // RGB for Pinto
                
            default:
                return Color.WHITE; // Default color if not found
        }
    }
    
    @Override
    public String toString() {
        return "Horse [name=" + name + ", breed=" + breed + ", color=" + coatColor +
                ", symbol=" + symbol + ", attributes={speed=" + speed + 
                ", stamina=" + stamina + ", confidence=" + confidence + "}]";
    }
}