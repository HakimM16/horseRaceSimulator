
import java.awt.*;
import java.awt.geom.Path2D;
import javax.swing.*;

// HorseGraphic class to represent each racing HorseGraphic
public class HorseGraphic {
    public double x; // X position on track
    public double y; // Y position within lane
    public int laneNumber; // Which lane the HorseGraphic is in
    public String symbol; // HorseGraphic symbol (e.g., "H")
    public double speed; // How fast the HorseGraphic moves each update
    public Color color; // HorseGraphic color
    public String name; // HorseGraphic name/identifier
    public JPanel HorseGraphicPanel; // Visual representation
    public double angle = 0; // For oval track rotation
    public Path2D.Double path; // For zigzag track
    public double pathPosition = 0; // Position along path (0.0 to 1.0)
    public TrackType trackType;
    public boolean hasFallen; // Flag to indicate if HorseGraphic has fallen
    public double confidence; // HorseGraphic confidence level
    public double stamina; // HorseGraphic stamina level
    public double speedFactor; // HorseGraphic speed factor
    public String fallenSymbol = "X"; // Symbol to show when HorseGraphic has fallen
    
    // Track type enum
    public enum TrackType {
        RECTANGULAR, OVAL, HALFOVAL
    }
    
    public HorseGraphic(String name, int laneNumber, String symbol, int laneHeight, Color color, TrackType trackType, double confidence, double stamina, int speedFactor) {
        this.name = name;
        this.laneNumber = laneNumber;
        this.trackType = trackType;
        this.speed = 1 + (Math.random() * 3); // Random speed between 1-3
        this.color = color;
        this.symbol = symbol;
        this.hasFallen = false;
        this.confidence = confidence;
        this.stamina = stamina;
        this.speedFactor = speedFactor;
        
        // Initial position based on track type
        switch(trackType) {
            case RECTANGULAR:
                this.x = 10;
                this.y = laneNumber * laneHeight + laneHeight/4;
                break;
            case OVAL:
                this.angle = Math.PI / 2; // Start at bottom of oval
                break;
            case HALFOVAL:
                this.angle = Math.PI / 2;
                break;
        }
        
        // Create visual representation with border for better visibility
        HorseGraphicPanel = new JPanel();
        HorseGraphicPanel.setBackground(color);
        
        // For rectangular track initial positioning
        if (trackType == TrackType.RECTANGULAR) {
            HorseGraphicPanel.setBounds((int)x, (int)y, 30, laneHeight/2);
        } else {
            HorseGraphicPanel.setBounds(0, 0, 30, laneHeight/2); // Will be positioned later
        }
        
        // Add HorseGraphic symbol label to make it more visible
        JLabel HorseGraphicLabel = new JLabel(symbol);
        HorseGraphicLabel.setForeground(Color.WHITE);
        HorseGraphicLabel.setHorizontalAlignment(SwingConstants.CENTER);
        HorseGraphicPanel.setLayout(new BorderLayout());
        HorseGraphicPanel.add(HorseGraphicLabel, BorderLayout.CENTER);
    }
    
    public void setPath(Path2D.Double path) {
        this.path = path;
    }
    
    public void move() {
        switch(trackType) {
            case RECTANGULAR:
                moveRectangular();
                break;
            case OVAL:
                moveOval();
                break;
            case HALFOVAL:
                moveHalfOval();
                break;
        }
    }
    
    public void moveRectangular() {
        // if HorseGraphic has fallen, stop moving
        if (!this.hasFallen) {
            // Random speed variation to make race interesting
            double randomFactor = (Math.random() * 3) - 1; // -1 to 1
            x += speed + randomFactor * this.speedFactor; // Adjust speed with random factor
            HorseGraphicPanel.setBounds((int)x, (int)y, 30, HorseGraphicPanel.getHeight());
            if (Math.random() < this.confidence) {
                // Random chance to fall
                // use stamina to decrease the chance of falling
                if (Math.random() * this.stamina < 0.01) { // 5% chance to fall
                    this.hasFallen = true;
                    setConfidence(this.confidence - 0.1); // Decrease confidence on fall
                    HorseGraphicPanel.setBackground(Color.RED); // Change color to indicate fall
                    HorseGraphicPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Add border for visibility
                    setFallenSymbol();// Change symbol to "X"
                } else {
                    this.hasFallen = false; // Reset fall status
                }
            } 
        }
    }
    
    public void moveOval() {
        // if HorseGraphic has fallen, stop moving
        if (!this.hasFallen) {
            // Random speed variation
            double randomFactor = (Math.random() * 0.02) - 0.01; // Small random factor
            angle += (speed * 0.01 * (this.speedFactor * 0.2)) + randomFactor; // Adjust speed for oval movement
            
            // Keep angle in range 0-2π
            if (angle >= 2 * Math.PI) {
                angle -= 2 * Math.PI;
            }
            
            // Calculate position on oval
            int centerX = 375; // Center X of oval
            int centerY = 225; // Center Y of oval
            int radiusX = 275 - (laneNumber * 20); // X radius, decreasing for inner lanes
            int radiusY = 150 - (laneNumber * 20); // Y radius, decreasing for inner lanes
            
            x = centerX - radiusX * Math.cos(angle);
            y = centerY - radiusY * Math.sin(angle);
            
            HorseGraphicPanel.setBounds((int)x - 15, (int)y - 10, 30, 20);
            if (Math.random() < this.confidence) {
                // Random chance to fall
                // use stamina to decrease the chance of falling
                if (Math.random() * this.stamina < 0.01) { // 5% chance to fall
                    this.hasFallen = true;
                    setConfidence(this.confidence - 0.1); // Decrease confidence on fall
                    HorseGraphicPanel.setBackground(Color.RED); // Change color to indicate fall
                    HorseGraphicPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Add border for visibility
                    setFallenSymbol();// Change symbol to "X"
                } else {
                    this.hasFallen = false; // Reset fall status
                }
            } 
        }
    }

    public void moveHalfOval() {
        // if HorseGraphic has fallen, stop moving
        if (!this.hasFallen) {
            // Random speed variation
            double randomFactor = (Math.random() * 0.02) - 0.01; // Small random factor
            angle += (speed * 0.01 * (this.speedFactor * 0.2)) + randomFactor; // Adjust speed for oval movement
            
            // Keep angle in range 0-2π
            if (angle >= 2 * Math.PI) {
                angle -= 2 * Math.PI;
            }
            
            // Calculate position on oval
            int centerX = 375; // Center X of oval
            int centerY = 225; // Center Y of oval
            int radiusX = 275 - (laneNumber * 20); // X radius, decreasing for inner lanes
            int radiusY = 150 - (laneNumber * 20); // Y radius, decreasing for inner lanes
            
            x = centerX + radiusX * Math.cos(angle);
            y = centerY + radiusY * Math.sin(angle);
            
            HorseGraphicPanel.setBounds((int)x - 15, (int)y - 10, 30, 20);
            if (Math.random() < this.confidence) {
                // Random chance to fall
                // use stamina to decrease the chance of falling
                if (Math.random() * this.stamina < 0.01) { // 5% chance to fall
                    this.hasFallen = true;
                    setConfidence(this.confidence - 0.1); // Decrease confidence on fall
                    HorseGraphicPanel.setBackground(Color.RED); // Change color to indicate fall
                    HorseGraphicPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Add border for visibility
                    setFallenSymbol();// Change symbol to "X"
                } else {
                    this.hasFallen = false; // Reset fall status
                }
            } 
        }
    }
    
    
    public JPanel getHorseGraphicPanel() {
        return HorseGraphicPanel;
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    public double getAngle() {
        return angle;
    }
    
    public double getPathPosition() {
        return pathPosition;
    }
    
    public String getName() {
        return this.name;
    }

    public void resetFallen() {
        this.hasFallen = false;
        HorseGraphicPanel.setBackground(color); // Reset color to original
        HorseGraphicPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Reset border
        setSymbol(symbol); // Reset symbol to original
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
        JLabel HorseGraphicLabel = new JLabel(symbol);
        HorseGraphicLabel.setForeground(Color.WHITE);
        HorseGraphicLabel.setHorizontalAlignment(SwingConstants.CENTER);
        HorseGraphicPanel.removeAll(); // Remove old label
        HorseGraphicPanel.add(HorseGraphicLabel, BorderLayout.CENTER); // Add new label
        HorseGraphicPanel.revalidate(); // Refresh panel to show new label
    }

    public void setFallenSymbol() {
        JLabel HorseGraphicLabel = new JLabel(fallenSymbol);
        HorseGraphicLabel.setForeground(Color.WHITE);
        HorseGraphicLabel.setHorizontalAlignment(SwingConstants.CENTER);
        HorseGraphicPanel.removeAll(); // Remove old label
        HorseGraphicPanel.add(HorseGraphicLabel, BorderLayout.CENTER); // Add new label
        HorseGraphicPanel.revalidate(); // Refresh panel to show new label
    }

    // set new confidence
    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }
    
    // Method to reset HorseGraphic position
    public void reset() {
        switch(trackType) {
            case RECTANGULAR:
                x = 10;
                HorseGraphicPanel.setBounds((int)x, (int)y, 30, HorseGraphicPanel.getHeight());
                resetFallen(); // Reset fallen status
                break;
            case OVAL:
                angle = Math.PI / 2; // Start at bottom of oval
                moveOval(); // Update position
                resetFallen();
                break;
            case HALFOVAL:
                angle = Math.PI / 2; // Start at bottom of oval
                moveHalfOval(); // Update position
                resetFallen();
                break;
        }
    }
}