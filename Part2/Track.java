
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
        track.selectLanes(); // Call the method to select lanes
        System.out.println("Track created with " + track.getLanes() + " lanes and length of " + track.getLength() + " meters.");
    }
    // select number of lanes from user
    public void selectLanes() {
        // Create the frame
        JFrame frame = new JFrame("Select Number of Lanes");

        // Create panel and components
        JPanel panel = new JPanel();
        JLabel lanes = new JLabel("Enter number of lanes (1-10): ");
        JTextField inputField = new JTextField(5);  // 5 columns wide

        JLabel length = new JLabel("Enter length of track (in meters): ");
        JTextField lengthField = new JTextField(5);  // 5 columns wide
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
                System.out.println("User entered: " + lanesInput + " lanes and " + lengthInput + " meters");
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
                // Set the number of lanes and length of the track
                setLanes(Integer.parseInt(lanesInput));  // Set the number of lanes
                setLength(Integer.parseInt(lengthInput));  // Set the length of the track
                inputField.setText("");  // Optionally clear the field after submit
                lengthField.setText("");  // Optionally clear the field after submit
                // Close the frame after submission
                frame.dispose();  // Close the frame
            }
        });

        // Add components to panel
        panel.add(lanes);  // Add label to panel
        panel.add(inputField);  // Add text field to panel
        panel.add(length);  // Add label to panel
        panel.add(lengthField);  // Add text field to panel
        panel.add(submitButton);  // Add button to panel

        // Add panel to frame
        frame.add(panel);
        frame.setSize(300, 300);  // Set frame size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Close operation
        frame.setVisible(true);  // Make frame visible
        frame.setLocationRelativeTo(null);  // Center the frame on the screen
        frame.setResizable(false);  // Make frame not resizable


        
    }
    
    
}