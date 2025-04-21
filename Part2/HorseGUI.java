// similar to Horse.java in part 1 but changes for GUI
/**
 * In the race, the horse is represented by a symbol and has attributes like speed, stamina, and confidence.
 * Add in rangeOfConfidenceException, CheckArgumentException
 * Make a method where it customises the number of horses that the user wants and returns an arraylist of horses.
 */
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class HorseGUI extends JFrame{
    // GUI Components
    private Horse horse = new Horse();
    private JTextField nameField;
    private JComboBox<String> breedComboBox;
    private JComboBox<String> colorComboBox;
    private JTextField symbolField;
    private JComboBox<String> saddleComboBox;
    private JComboBox<String> horseshoeComboBox;
    private JComboBox<String> accessoryComboBox;
    private JProgressBar speedBar, staminaBar, confidenceBar;
    private JPanel horsePreviewPanel;

    // make arrayList of horses
    private Map<Integer, Horse> horseList = new HashMap<>();
    private int num = 0; // number of horses created
    private int numberOfHorses; // number of horses to be created
    private Map<Integer, Horse> finalHorseList = null; // final list of horses created


    // Constructor
    public HorseGUI() {
        horse = new Horse();
        initializeUI();
    }

    // values from Track.java
    private int lanes; // number of lanes in the track
    private int length; // length of the track
    private String trackShape; // shape of the track
    private String weatherCondition; // weather condition

    // Make a new constrcutor to get the values from inputValues() in Track.java
    public HorseGUI(int lanes, int length, String trackShape, String weatherCondition) {
        this.lanes = lanes;
        this.length = length;
        this.trackShape = trackShape;
        this.weatherCondition = weatherCondition;
        this.numberOfHorses = lanes; // number of horses to be created is equal to the number of lanes
        horse = new Horse();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Horse Customization");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout(10, 10));
        
        // Main panel with form fields
        JPanel mainPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Name field
        mainPanel.add(new JLabel("Horse Name:"));
        nameField = new JTextField(20);
        nameField.addActionListener(e -> horse.setName(nameField.getText()));
        mainPanel.add(nameField);
        
        // Breed selection
        mainPanel.add(new JLabel("Breed:"));
        String[] breeds = {"Thoroughbred", "Arabian", "Quarter Horse", "Appaloosa", "Mustang"};
        breedComboBox = new JComboBox<>(breeds);
        breedComboBox.addActionListener(e -> {
            horse.setBreed((String) breedComboBox.getSelectedItem());
            updateAttributeBars();
        });
        mainPanel.add(breedComboBox);
        
        // Coat color selection
        mainPanel.add(new JLabel("Coat Color:"));
        String[] colors = {"Brown", "Black", "Grey", "White", "Chestnut", "Palomino", "Pinto"};
        colorComboBox = new JComboBox<>(colors);
        colorComboBox.addActionListener(e -> {
            horse.setCoatColor((String) colorComboBox.getSelectedItem());
            updateHorsePreview();
        });
        mainPanel.add(colorComboBox);
        
        // Symbol representation
        mainPanel.add(new JLabel("Symbol (Press enter to submit):"));
        symbolField = new JTextField(1);
        symbolField.setText("H");
        symbolField.addActionListener(e -> {
            if (!symbolField.getText().isEmpty()) {
                horse.setSymbol(symbolField.getText().substring(0, 1));
                updateHorsePreview();
            }
        });
        mainPanel.add(symbolField);
        
        // Saddle selection
        mainPanel.add(new JLabel("Saddle:"));
        String[] saddles = {"Standard", "Racing", "Western", "English", "Dressage"};
        saddleComboBox = new JComboBox<>(saddles);
        saddleComboBox.addActionListener(e -> {
            horse.setSaddle((String) saddleComboBox.getSelectedItem());
            updateHorsePreview();
        });
        mainPanel.add(saddleComboBox);
        
        // Horseshoe selection
        mainPanel.add(new JLabel("Horseshoes:"));
        String[] horseshoes = {"Regular", "Lightweight", "Endurance", "Confidence Booster"};
        horseshoeComboBox = new JComboBox<>(horseshoes);
        horseshoeComboBox.addActionListener(e -> {
            horse.setHorseshoe((String) horseshoeComboBox.getSelectedItem());
            updateAttributeBars();
            updateHorsePreview();
        });
        mainPanel.add(horseshoeComboBox);
        
        // Accessory selection
        mainPanel.add(new JLabel("Accessory:"));
        String[] accessories = {"None", "Racing Blinkers", "Winter Blanket", "Fancy Hat", "Victory Wreath"};
        accessoryComboBox = new JComboBox<>(accessories);
        accessoryComboBox.addActionListener(e -> {
            horse.setAccessory((String) accessoryComboBox.getSelectedItem());
            updateHorsePreview();
        });
        mainPanel.add(accessoryComboBox);
        
        // Add the main panel to the center
        add(mainPanel, BorderLayout.CENTER);
        
        // Create a panel for the attribute bars
        JPanel attributePanel = new JPanel(new GridLayout(3, 2, 10, 10));
        attributePanel.setBorder(BorderFactory.createTitledBorder("Horse Attributes"));
        
        // Speed attribute
        attributePanel.add(new JLabel("Speed:"));
        speedBar = new JProgressBar(0, 12);
        speedBar.setStringPainted(true);
        attributePanel.add(speedBar);
        
        // Stamina attribute
        attributePanel.add(new JLabel("Stamina:"));
        staminaBar = new JProgressBar(0, 12);
        staminaBar.setStringPainted(true);
        attributePanel.add(staminaBar);
        
        // Confidence attribute
        attributePanel.add(new JLabel("Confidence:"));
        confidenceBar = new JProgressBar(0, 12);
        confidenceBar.setStringPainted(true);
        attributePanel.add(confidenceBar);
        
        // Add the attribute panel to the bottom
        add(attributePanel, BorderLayout.SOUTH);
        
        // Create a panel for the horse preview
        horsePreviewPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawHorse(g);
            }
        };
        horsePreviewPanel.setPreferredSize(new Dimension(300, 300));
        horsePreviewPanel.setBorder(BorderFactory.createTitledBorder("Horse Preview"));
        
        // Add the preview panel to the right
        add(horsePreviewPanel, BorderLayout.EAST);
        
        // Add buttons panel
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save Horse");
        saveButton.addActionListener(e -> saveHorse(horse));
        
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> resetForm());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(resetButton);
        add(buttonPanel, BorderLayout.NORTH);
        
        // Initial updates
        updateAttributeBars();
        updateHorsePreview();
        
        setLocationRelativeTo(null);  // Center on screen
    }
    
    private void updateAttributeBars() {
        speedBar.setValue(horse.getSpeed());
        staminaBar.setValue(horse.getStamina());
        confidenceBar.setValue((int) (horse.getConfidence() * 10));
    }
    
    private void updateHorsePreview() {
        horsePreviewPanel.repaint();
    }
    
    private void drawHorse(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int width = horsePreviewPanel.getWidth();
        int height = horsePreviewPanel.getHeight();
        
        // Set background
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        
        // Set coat color
        Color coatColor;
        switch (horse.getCoatColor()) {
            case "Black":
                coatColor = new Color(30, 30, 30);
                break;
            case "Grey":
                coatColor = new Color(160, 160, 160);
                break;
            case "White":
                coatColor = new Color(240, 240, 240);
                break;
            case "Chestnut":
                coatColor = new Color(185, 110, 60);
                break;
            case "Palomino":
                coatColor = new Color(230, 200, 130);
                break;
            case "Pinto":
                coatColor = new Color(240, 240, 240); // Base color for pinto
                break;
            default: // Brown
                coatColor = new Color(139, 69, 19);
                break;
        }
        
        // Draw a simplified horse
        g2d.setColor(coatColor);
        
        // Horse body
        g2d.fillOval(width/4, height/3, width/2, height/4);
        
        // Horse neck and head
        g2d.fillArc(width/6, height/4, width/3, height/3, 0, 180);
        g2d.fillOval(width/8, height/4, width/6, height/6);
        
        // Horse legs
        int legWidth = width/20;
        int legHeight = height/4;
        g2d.fillRect(width/3, height/2, legWidth, legHeight);
        g2d.fillRect(width/2, height/2, legWidth, legHeight);
        g2d.fillRect(width/4, height/2, legWidth, legHeight);
        g2d.fillRect(3*width/5, height/2, legWidth, legHeight);
        
        // If pinto, add patches
        if (horse.getCoatColor().equals("Pinto")) {
            g2d.setColor(new Color(139, 69, 19));
            g2d.fillOval(width/3, height/3, width/6, height/6);
            g2d.fillOval(width/2, height/2 - height/8, width/10, height/10);
        }
        
        // Draw saddle
        Color saddleColor;
        switch (horse.getSaddle()) {
            case "Racing":
                saddleColor = Color.RED;
                break;
            case "Western":
                saddleColor = new Color(160, 82, 45);
                break;
            case "English":
                saddleColor = new Color(101, 67, 33);
                break;
            case "Dressage":
                saddleColor = Color.BLACK;
                break;
            default: // Standard
                saddleColor = new Color(139, 69, 19);
                break;
        }
        g2d.setColor(saddleColor);
        g2d.fillRoundRect(width/3 + width/20, height/3 - height/20, width/4, height/8, 10, 10);
        
        // Draw horseshoes
        Color horseshoeColor;
        switch (horse.getHorseshoe()) {
            case "Lightweight":
                horseshoeColor = Color.LIGHT_GRAY;
                break;
            case "Endurance":
                horseshoeColor = Color.DARK_GRAY;
                break;
            case "Confidence Booster":
                horseshoeColor = Color.YELLOW;
                break;
            default: // Regular
                horseshoeColor = new Color(192, 192, 192);
                break;
        }
        g2d.setColor(horseshoeColor);
        // Front left hoof
        g2d.fillArc(width/3, height/2 + legHeight - height/30, legWidth, height/15, 0, 180);
        // Front right hoof
        g2d.fillArc(width/2, height/2 + legHeight - height/30, legWidth, height/15, 0, 180);
        // Back left hoof
        g2d.fillArc(width/4, height/2 + legHeight - height/30, legWidth, height/15, 0, 180);
        // Back right hoof
        g2d.fillArc(3*width/5, height/2 + legHeight - height/30, legWidth, height/15, 0, 180);
        
        // Draw accessory
        switch (horse.getAccessory()) {
            case "Racing Blinkers":
                g2d.setColor(Color.BLACK);
                g2d.fillRect(width/8, height/4, width/30, height/20);
                break;
            case "Winter Blanket":
                g2d.setColor(Color.BLUE);
                g2d.fillRect(width/4, height/3 - height/20, width/2, height/10);
                break;
            case "Fancy Hat":
                g2d.setColor(Color.RED);
                g2d.fillArc(width/8, height/4 - height/15, width/8, height/10, 0, 180);
                break;
            case "Victory Wreath":
                g2d.setColor(Color.GREEN);
                g2d.fillOval(width/8 - width/40, height/4 - height/30, width/6 + width/20, height/15);
                g2d.setColor(Color.WHITE);
                g2d.fillOval(width/8, height/4 - height/40, width/6, height/20);
                break;
            // None - no accessory to draw
        }
        
        // Draw symbol
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics metrics = g2d.getFontMetrics();
        String symbol = horse.getSymbol();
        int textWidth = metrics.stringWidth(symbol);
        g2d.drawString(symbol, width/2 - textWidth/2, height/2);
        
        // Draw name if provided
        if (horse.getName() != null && !horse.getName().isEmpty()) {
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            metrics = g2d.getFontMetrics();
            textWidth = metrics.stringWidth(horse.getName());
            g2d.drawString(horse.getName(), width/2 - textWidth/2, height - 20);
        }
    }
    
    private void saveHorse(Horse horse) {
        // Get the name from the text field
        horse.setName(nameField.getText());

        // Add the horse to the list
        int horseId = horseList.size() + 1; // Simple ID generation
        horseList.put(horseId, horse);
        // Display confirmation message
        JOptionPane.showMessageDialog(this, 
            "Horse " + horseId + " saved successfully!\n\n" + horse.toString(),
            "Horse Saved", JOptionPane.INFORMATION_MESSAGE);
        
        // Here you would typically save to a database or file
        System.out.println("Horse saved: " + horse);
        System.out.println("Horse size: " + this.horseList.size());
        
        // Increment the number of horses created
        this.num++;

        // Check if we've created all required horses
        if (this.num >= this.numberOfHorses) {
            this.finalHorseList = new HashMap<>(this.horseList); // Create a defensive copy
            JOptionPane.showMessageDialog(this, 
                "All horses have been saved. Total: " + this.finalHorseList.size(), 
                "Complete", 
                JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Final horse list: " + this.finalHorseList);
            
            // Don't immediately exit - allow the calling code to get the finalHorseList
            dispose(); // Close the window but don't terminate the application

            // call the Track class to start
            Track track = new Track();
            RaceTrackApplication raceTrackApp = new RaceTrackApplication(this.lanes, this.length, this.trackShape, this.weatherCondition, this.finalHorseList);
            
        } else {
            // Reset the form for new entry only if we need to create more horses
            resetForm();
        }
    }

    /**
     * Returns the final list of horses once all have been created.
     * @return Map containing all created horses with their IDs, or null if not all horses have been created yet
     */
    public Map<Integer, Horse> getFinalHorseList() {
        return this.finalHorseList != null ? new HashMap<>(this.finalHorseList) : null;
    }

    
    
    private void resetForm() {
        Horse horse = new Horse();
        nameField.setText("");
        breedComboBox.setSelectedIndex(0);
        colorComboBox.setSelectedIndex(0);
        symbolField.setText("H");
        saddleComboBox.setSelectedIndex(0);
        horseshoeComboBox.setSelectedIndex(0);
        accessoryComboBox.setSelectedIndex(0);
        updateAttributeBars();
        updateHorsePreview();
    }
    
    public static void main(String[] args) {
        // Use the system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // First approach: Create a GUI that returns results through a callback
        
    
        // Show the GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            
            HorseGUI horseGUI = new HorseGUI();
            horseGUI.setVisible(true);
            
        });
    }
}
