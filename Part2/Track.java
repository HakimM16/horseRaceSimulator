import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Track {
    public int lanes; // number of lanes in the track
    public int length; // length of the track
    public String trackShape; // shape of the track
    public String weatherCondition; // weather condition

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
        Track track = new Track(); // Create a new track with 1 lane
        track.inputValues(); // Call the inputValues method to get user input
        // track.createRectangularTrack(track.getLength(), track.getLanes());
        // track.createSimpleOvalTrack(track.getLanes()); // Call the createTrack method to draw the track
        // track.createZigZagTrack(track.getLength(), track.getLanes()); // Call the createTrack method to draw the track
        
        // // Print the track details
        // System.out.println("Number of lanes: " + track.getLanes()); // Print the number of lanes
        // System.out.println("Length of track: " + track.getLength() + " meters"); // Print the length of the track
        // System.out.println("Track shape: " + track.getTrackShape()); // Print the shape of the track
        // System.out.println("Weather condition: " + track.getWeatherCondition()); // Print the weather condition
    }

    // select number of lanes from user
    public void inputValues() {
        // Create the frame
        JFrame frame = new JFrame("Select Number of Lanes");

        // Create panel and components
        JPanel panel = new JPanel();
        JPanel buttonPanel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));  // Set layout to vertical grid
        JLabel lanes = new JLabel("Enter number of lanes (1-5): ");
        lanes.setHorizontalAlignment(JLabel.CENTER);  // Align label to left
        JTextField inputField = new JTextField(3);  // 5 columns wide
        inputField.setHorizontalAlignment(JTextField.CENTER); // Center the input text

        JLabel length = new JLabel("Enter length of track (between 100 and 700 meters): ");
        length.setHorizontalAlignment(JLabel.CENTER);  // Align label to left
        JTextField lengthField = new JTextField(3);  // 5 columns wide
        lengthField.setHorizontalAlignment(JTextField.CENTER); // Center the input text

        JLabel trackShapeLabel = new JLabel("Enter track shape: ");
        JLabel trackShapes = new JLabel("(oval, rectangular, zig-zag)");
        trackShapes.setHorizontalAlignment(JLabel.CENTER);  // Align label to left
        trackShapeLabel.setHorizontalAlignment(JLabel.CENTER);  // Align label to left
        JTextField trackShapeField = new JTextField(3);  // 5 columns wide
        trackShapeField.setHorizontalAlignment(JTextField.CENTER); // Center the input text

        JLabel weatherConditionLabel = new JLabel("Enter weather condition: ");
        JLabel weatherConditions = new JLabel("(sunny, rainy, snowy, foggy, windy, muddy, dry, wet, icy)");
        weatherConditions.setHorizontalAlignment(JLabel.CENTER);  // Align label to left
        weatherConditionLabel.setHorizontalAlignment(JLabel.CENTER);  // Align label to left
        JTextField weatherConditionField = new JTextField(3);  // 5 columns wide
        weatherConditionField.setHorizontalAlignment(JTextField.CENTER); // Center the input text

        // Create a button to submit the input
        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // remove error label for lanes if it exists
                for (int i = 0; i < panel.getComponentCount(); i++) {
                    if (panel.getComponent(i) instanceof JLabel && ((JLabel) panel.getComponent(i)).getText().equals("Please enter a number between 1 and 5.")) {
                        panel.remove(i);  // Remove the error label
                        break;  // Exit the loop after removing the label
                    }
                }
                // remove error label for length if it exists
                for (int i = 0; i < panel.getComponentCount(); i++) {
                    if (panel.getComponent(i) instanceof JLabel && ((JLabel) panel.getComponent(i)).getText().equals("Please enter a number between 100 and 700.")) {
                        panel.remove(i);  // Remove the error label
                        break;  // Exit the loop after removing the label
                    }
                }
                
                String lanesInput = inputField.getText();  // Get text from field
                String lengthInput = lengthField.getText();  // Get text from field
                // check if lanes is between 1 and 10
                if (Integer.parseInt(lanesInput) < 1 || Integer.parseInt(lanesInput) > 5) {
                    System.out.println("Invalid number of lanes. Please enter a number between 1 and 5.");
                    // Show error message to user as a label
                    JLabel errorLabel = new JLabel("Please enter a number between 1 and 5.");
                    panel.add(errorLabel);  // Add error label to panel
                    // Repaint the panel to show the error message
                    panel.revalidate();  // Revalidate the panel to show the error message
                    panel.repaint();  // Repaint the panel to show the error message
                    return; // Exit the method if invalid input
                }
                // check if length is between 1 and 180
                if (Integer.parseInt(lengthInput) < 100 || Integer.parseInt(lengthInput) > 700) {
                    System.out.println("Invalid length. Please enter a number between 100 and 700.");
                    // Show error message to user as a label
                    JLabel errorLabel = new JLabel("Please enter a number between 100 and 700.");
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
                String[] validShapes = {"oval", "rectangular", "zig-zag"};
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

                // store values in variables for HorseGUI
                int lanes = getLanes();  // Get the number of lanes
                int length = getLength();  // Get the length of the track
                String trackShape = getTrackShape();  // Get the shape of the track
                String weatherCondition = getWeatherCondition();  // Get the weather condition

                inputField.setText("");  // Optionally clear the field after submit
                lengthField.setText("");  // Optionally clear the field after submit
                trackShapeField.setText("");  // Optionally clear the field after submit
                weatherConditionField.setText("");  // Optionally clear the field after submit
                // Close the frame after submission
                frame.dispose();  // Close the frame
                // Call the createTrack method to draw the track
                HorseGUI gui = new HorseGUI(lanes, length, trackShape, weatherCondition);  // Create a new HorseGUI object
                gui.setVisible(true);
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

    // method to draw the track

    // method to create rectangular track
    public void createRectangularTrack(int length, int lanes) {
        // Create a frame for the track
        JFrame trackFrame = new JFrame("Race Track");
        trackFrame.setSize(800, 600);
        trackFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        trackFrame.setLocationRelativeTo(null); // Center the frame on the screen
        
        // Use GridBagLayout for true centering
        trackFrame.setLayout(new GridBagLayout());

        // Create a panel to represent the track
        JPanel trackPanel = new JPanel();
        trackPanel.setPreferredSize(new Dimension(length, 400)); // Set fixed size
        trackPanel.setBackground(new java.awt.Color(34, 139, 34)); // Green background for grass
        trackPanel.setLayout(null); // Use absolute positioning for lanes

        // Add lanes to the track
        int laneHeight = 400 / lanes; // Calculate lane height based on fixed panel height
        for (int i = 0; i < lanes; i++) {
            JPanel lane = new JPanel();
            lane.setBounds(0, i * laneHeight, length, laneHeight - 5); // Leave space between lanes
            lane.setBackground(new java.awt.Color(173, 216, 230)); // Light blue color for lanes
            trackPanel.add(lane);
        }

        // Add the track panel to the frame using GridBagLayout
        trackFrame.add(trackPanel);

        // Display the frame
        trackFrame.setVisible(true);

        // make finish line
        JPanel finishLine = new JPanel();
        finishLine.setBounds(length - 10, 0, 10, 400); // Position at the end of the track
        finishLine.setBackground(Color.WHITE); // White color for the finish line
        trackPanel.add(finishLine);
    }

    // method to create oval track
    public void createSimpleOvalTrack(int lanes) {
        // create width and height
        int width = 750;
        int height = 450;
        // Create a frame for the track
        JFrame trackFrame = new JFrame("Oval Race Track");
        trackFrame.setSize(800, 600);
        trackFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        trackFrame.setLocationRelativeTo(null); // Center the frame on the screen
        
        // Use GridBagLayout for true centering
        trackFrame.setLayout(new GridBagLayout());

        // Create a panel to represent the oval track
        JPanel trackPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                
                // Enable antialiasing for smoother curves
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Set the background to white
                g2d.setColor(new Color(173, 216, 230)); // Green background for grass
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Set the stroke width
                g2d.setStroke(new BasicStroke(1.5f));
                g2d.setColor(new Color(34, 139, 34)); // Green color for the track
                
                // Calculate dimensions for the oval track
                int margin = 50;
                int trackWidth = getWidth() - (2 * margin);
                int trackHeight = getHeight() - (2 * margin);
                
                // Calculate lane spacing
                int laneSpacing = Math.min(trackWidth, trackHeight) / (lanes * 3);
                
                // Draw concentric ovals for each lane
                for (int i = 0; i < lanes + 1; i++) {
                    int x = margin + (i * laneSpacing);
                    int y = margin + (i * laneSpacing);
                    int width = trackWidth - (2 * i * laneSpacing);
                    int height = trackHeight - (2 * i * laneSpacing);
                    
                    // Draw the lane as an oval
                    g2d.drawOval(x, y, width, height);
                }

                // Draw the start/finish line
                g2d.setColor(Color.WHITE);
                g2d.setStroke(new BasicStroke(5));
                
                g2d.drawLine(margin + (trackWidth / 2), margin, margin + (trackWidth / 2), margin + (trackHeight - (2 * lanes * laneSpacing)));
                
                
            }
        };
        
        trackPanel.setPreferredSize(new Dimension(width, height));

        // Add the track panel to the frame using GridBagLayout
        trackFrame.add(trackPanel);

        // Display the frame
        trackFrame.setVisible(true);
    }

    public void createZigZagTrack(int length, int lanes) {
        // Create a frame for the track
        JFrame trackFrame = new JFrame("Zig-Zag Race Track");
        trackFrame.setSize(800, 600);
        trackFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        trackFrame.setLocationRelativeTo(null); // Center the frame on the screen
        
        // Use GridBagLayout for true centering
        trackFrame.setLayout(new GridBagLayout());

        // Create a panel to represent the zig-zag track
        JPanel trackPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                
                // Enable antialiasing for smoother lines
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw the green background for the entire area
                g2d.setColor(new Color(173, 216, 230)); // Light blue background for grass
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Define the zig-zag path
                int segments = 5; // Number of zigs and zags
                int margin = 30;
                int trackWidth = getWidth() - (2 * margin);
                int trackHeight = getHeight() - (2 * margin);
                int segmentWidth = trackWidth / segments;
                
                // Calculate lane parameters
                int totalLaneWidth = trackHeight / 2;
                int singleLaneWidth = totalLaneWidth / lanes;
                
                // Create points for the zig-zag path
                int[] xPoints = new int[segments + 1];
                int[] yPoints = new int[segments + 1];
                
                for (int i = 0; i <= segments; i++) {
                    xPoints[i] = margin + (i * segmentWidth);
                    yPoints[i] = margin + (i % 2 == 0 ? 0 : trackHeight - totalLaneWidth);
                }
                
                // Draw each lane
                for (int lane = 0; lane < lanes; lane++) {
                    int laneOffset = lane * singleLaneWidth;
                    
                    Polygon lanePolygon = new Polygon();
                    
                    // Top path of lane
                    for (int i = 0; i <= segments; i++) {
                        lanePolygon.addPoint(xPoints[i], yPoints[i] + laneOffset);
                    }
                    
                    // Bottom path of lane (in reverse)
                    for (int i = segments; i >= 0; i--) {
                        lanePolygon.addPoint(xPoints[i], yPoints[i] + laneOffset + singleLaneWidth);
                    }
                    
                    // Fill the lane
                    g2d.setColor(new Color(173, 216, 230)); // Light blue color for lanes
                    g2d.fill(lanePolygon);
                    
                    // Draw lane borders
                    g2d.setColor(new Color(34, 139, 34)); // Green color for the track
                    g2d.setStroke(new BasicStroke(2));
                    g2d.draw(lanePolygon);
                }
                
                // Draw start/finish line
                g2d.setColor(Color.WHITE);
                g2d.setStroke(new BasicStroke(5));
                g2d.drawLine(margin + 340, margin + 340, margin + 340, margin + totalLaneWidth);
            }
        };
        
        trackPanel.setPreferredSize(new Dimension(length, 400)); // Set fixed size

        // Add the track panel to the frame
        trackFrame.add(trackPanel);

        // Display the frame
        trackFrame.setVisible(true);
    }

}