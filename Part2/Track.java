
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Track {
    private int lanes; // number of lanes in the track
    private int length; // length of the track
    private String trackShape; // shape of the track
    private String weatherCondition; // weather condition

    // Constructor to initialize the track with lanes, length, shape, and weather condition
    public Track(int lanes, int length, String trackShape, String weatherCondition) {
        this.lanes = lanes; // set the number of lanes
        this.length = length; // set the length of the track to 100 meters
        this.trackShape = trackShape; // set the shape of the track
        this.weatherCondition = weatherCondition; // set the weather condition
    }

    // Constructor to initialize the track with lanes and length
    public Track(int lanes, int length) {
        this.lanes = lanes; // set the number of lanes
        this.length = length; // set the length of the track to 100 meters
    }

    // Constructor to initialize the track with default values
    public Track() {
        this.lanes = 1; // default number of lanes
        this.length = 100; // default length of the track to 100 meters
    }

    public int getLanes() {
        return this.lanes; // return the number of lanes
    }

    public int getLength() {
        return this.length; // return the length of the track
    }

    public String getTrackShape() {
        return this.trackShape; // return the shape of the track
    }

    public String getWeatherCondition() {
        return this.weatherCondition; // return the weather condition
    }

    public void setLanes(int lanes) {
        this.lanes = lanes; // set the number of lanes
    }

    public void setLength(int length) {
        this.length = length; // set the length of the track
    }

    public void setTrackShape(String trackShape) {
        this.trackShape = trackShape; // set the shape of the track
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition; // set the weather condition
    }

    // Main method to run the program
    public static void main(String[] args) {
        Track track = new Track(0, 0); // Create a new track with 1 lane
        track.inputValues(); // Call the inputValues method to get user input
        // Print the track details
        System.out.println("Number of lanes: " + track.getLanes()); // Print the number of lanes
        System.out.println("Length of track: " + track.getLength() + " meters"); // Print the length of the track
        System.out.println("Track shape: " + track.getTrackShape()); // Print the shape of the track
        System.out.println("Weather condition: " + track.getWeatherCondition()); // Print the weather condition
    }

    // select number of lanes from user
    public void inputValues() {
        // Create the frame
        JFrame frame = new JFrame("Select Number of Lanes");

        // Create panel and components
        JPanel panel = new JPanel();
        JPanel buttonPanel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));  // Set layout to vertical grid
        JLabel lanes = new JLabel("Enter number of lanes (1-10): ");
        lanes.setHorizontalAlignment(JLabel.CENTER);  // Align label to left
        JTextField inputField = new JTextField(3);  // 5 columns wide

        JLabel length = new JLabel("Enter length of track (up to 180 meters): ");
        length.setHorizontalAlignment(JLabel.CENTER);  // Align label to left
        JTextField lengthField = new JTextField(3);  // 5 columns wide

        JLabel trackShapeLabel = new JLabel("Enter track shape: ");
        JLabel trackShapes = new JLabel("(oval, Figure-eight, rectangular, triangular, zig-zag)");
        trackShapes.setHorizontalAlignment(JLabel.CENTER);  // Align label to left
        trackShapeLabel.setHorizontalAlignment(JLabel.CENTER);  // Align label to left
        JTextField trackShapeField = new JTextField(3);  // 5 columns wide

        JLabel weatherConditionLabel = new JLabel("Enter weather condition: ");
        JLabel weatherConditions = new JLabel("(sunny, rainy, snowy, foggy, windy, muddy, dry, wet, icy)");
        weatherConditions.setHorizontalAlignment(JLabel.CENTER);  // Align label to left
        weatherConditionLabel.setHorizontalAlignment(JLabel.CENTER);  // Align label to left
        JTextField weatherConditionField = new JTextField(3);  // 5 columns wide

        // Create a button to submit the input
        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // remove error label for lanes if it exists
                for (int i = 0; i < panel.getComponentCount(); i++) {
                    if (panel.getComponent(i) instanceof JLabel && ((JLabel) panel.getComponent(i)).getText().equals("Please enter a number between 1 and 10.")) {
                        panel.remove(i);  // Remove the error label
                        break;  // Exit the loop after removing the label
                    }
                }
                // remove error label for length if it exists
                for (int i = 0; i < panel.getComponentCount(); i++) {
                    if (panel.getComponent(i) instanceof JLabel && ((JLabel) panel.getComponent(i)).getText().equals("Please enter a number between 1 and 180.")) {
                        panel.remove(i);  // Remove the error label
                        break;  // Exit the loop after removing the label
                    }
                }
                
                String lanesInput = inputField.getText();  // Get text from field
                String lengthInput = lengthField.getText();  // Get text from field
                // check if lanes is between 1 and 10
                if (Integer.parseInt(lanesInput) < 1 || Integer.parseInt(lanesInput) > 10) {
                    System.out.println("Invalid number of lanes. Please enter a number between 1 and 10.");
                    // Show error message to user as a label
                    JLabel errorLabel = new JLabel("Please enter a number between 1 and 10.");
                    panel.add(errorLabel);  // Add error label to panel
                    // Repaint the panel to show the error message
                    panel.revalidate();  // Revalidate the panel to show the error message
                    panel.repaint();  // Repaint the panel to show the error message
                    return; // Exit the method if invalid input
                }
                // check if length is between 1 and 180
                if (Integer.parseInt(lengthInput) < 1 || Integer.parseInt(lengthInput) > 180) {
                    System.out.println("Invalid length. Please enter a number between 1 and 180.");
                    // Show error message to user as a label
                    JLabel errorLabel = new JLabel("Please enter a number between 1 and 180.");
                    panel.add(errorLabel);  // Add error label to panel
                    // Repaint the panel to show the error message
                    panel.revalidate();  // Revalidate the panel to show the error message
                    panel.repaint();  // Repaint the panel to show the error message
                    return; // Exit the method if invalid input
                }

                // check if track shape is not empty
                if (trackShapeField.getText().isEmpty()) {
                    System.out.println("Invalid track shape. Please enter a valid track shape.");
                    // Show error message to user as a label
                    JLabel errorLabel = new JLabel("Please enter a valid track shape.");
                    panel.add(errorLabel);  // Add error label to panel
                    // Repaint the panel to show the error message
                    panel.revalidate();  // Revalidate the panel to show the error message
                    panel.repaint();  // Repaint the panel to show the error message
                    return; // Exit the method if invalid input
                }

                // check if track shape is in the list of valid shapes
                String[] validShapes = {"oval", "Figure-eight", "rectangular", "triangular", "zig-zag"};
                boolean isValidShape = false;
                for (String shape : validShapes) {
                    if (shape.equalsIgnoreCase(trackShapeField.getText())) {
                        isValidShape = true;  // Set flag to true if valid shape is found
                        break;  // Exit the loop if valid shape is found
                    }
                }
                if (!isValidShape) {
                    System.out.println("Invalid track shape. Please enter a valid track shape.");
                    // Show error message to user as a label
                    JLabel errorLabel = new JLabel("Please enter a valid track shape.");
                    panel.add(errorLabel);  // Add error label to panel
                    // Repaint the panel to show the error message
                    panel.revalidate();  // Revalidate the panel to show the error message
                    panel.repaint();  // Repaint the panel to show the error message
                    return; // Exit the method if invalid input
                }

                // check if weather condition is in the list of valid conditions
                String[] validConditions = {"sunny", "rainy", "snowy", "foggy", "windy", "muddy", "dry", "wet", "icy"};
                boolean isValidCondition = false;
                for (String condition : validConditions) {
                    if (condition.equalsIgnoreCase(weatherConditionField.getText())) {
                        isValidCondition = true;  // Set flag to true if valid condition is found
                        break;  // Exit the loop if valid condition is found
                    }
                }
                if (!isValidCondition) {
                    System.out.println("Invalid weather condition. Please enter a valid weather condition.");
                    // Show error message to user as a label
                    JLabel errorLabel = new JLabel("Please enter a valid weather condition.");
                    panel.add(errorLabel);  // Add error label to panel
                    // Repaint the panel to show the error message
                    panel.revalidate();  // Revalidate the panel to show the error message
                    panel.repaint();  // Repaint the panel to show the error message
                    return; // Exit the method if invalid input
                }
                // check if weather condition is not empty
                if (weatherConditionField.getText().isEmpty()) {
                    System.out.println("Invalid weather condition. Please enter a valid weather condition.");
                    // Show error message to user as a label
                    JLabel errorLabel = new JLabel("Please enter a valid weather condition.");
                    panel.add(errorLabel);  // Add error label to panel
                    // Repaint the panel to show the error message
                    panel.revalidate();  // Revalidate the panel to show the error message
                    panel.repaint();  // Repaint the panel to show the error message
                    return; // Exit the method if invalid input
                }

                // Set the number of lanes and length of the track
                setLanes(Integer.parseInt(lanesInput));  // Set the number of lanes
                setLength(Integer.parseInt(lengthInput));  // Set the length of the track
                setTrackShape(trackShapeField.getText());  // Set the shape of the track
                setWeatherCondition(weatherConditionField.getText());  // Set the weather condition
                inputField.setText("");  // Optionally clear the field after submit
                lengthField.setText("");  // Optionally clear the field after submit
                trackShapeField.setText("");  // Optionally clear the field after submit
                weatherConditionField.setText("");  // Optionally clear the field after submit
                // Close the frame after submission
                frame.dispose();  // Close the frame
            }
        });

        // Add components to panel
        panel.add(lanes);  // Add label to panel
        panel.add(inputField);  // Add text field to panel
        panel.add(length);  // Add label to panel
        panel.add(lengthField);  // Add text field to panel
        panel.add(trackShapeLabel);  // Add label to panel
        panel.add(trackShapes);  // Add label to panel
        panel.add(trackShapeField);  // Add text field to panel
        panel.add(weatherConditionLabel);  // Add label to panel
        panel.add(weatherConditions);  // Add label to panel
        panel.add(weatherConditionField);  // Add text field to panel
        buttonPanel.add(submitButton);  // Add button to panel

        // Add panel to frame
        frame.add(panel);
        frame.add(buttonPanel);  // Add button panel to frame
        frame.setSize(400, 400);  // Set frame size
        frame.setLayout(new GridLayout(0, 1));  // Set layout to vertical grid
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Close operation
        frame.setVisible(true);  // Make frame visible
        frame.setLocationRelativeTo(null);  // Center the frame on the screen
        frame.setResizable(false);  // Make frame not resizable
        // Set background color for the panel
        panel.setBackground(new java.awt.Color(240, 248, 255)); // Light blue background
        buttonPanel.setBackground(new java.awt.Color(240, 248, 255)); // Match button panel background

        // Customize the font and alignment of labels
        java.awt.Font labelFont = new java.awt.Font("Arial", java.awt.Font.BOLD, 14);
        lanes.setFont(labelFont);
        length.setFont(labelFont);
        trackShapeLabel.setFont(labelFont);
        weatherConditionLabel.setFont(labelFont);

        // Customize the font of the text fields
        java.awt.Font textFieldFont = new java.awt.Font("Arial", java.awt.Font.PLAIN, 14);
        inputField.setFont(textFieldFont);
        lengthField.setFont(textFieldFont);
        trackShapeField.setFont(textFieldFont);
        weatherConditionField.setFont(textFieldFont);

        // Customize the submit button
        submitButton.setBackground(new java.awt.Color(100, 149, 237)); // Cornflower blue background
        submitButton.setForeground(java.awt.Color.WHITE); // White text
        submitButton.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
        submitButton.setFocusPainted(false); // Remove focus border

        
    }
    
    
}