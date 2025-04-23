import java.awt.*;
import javax.swing.*;

public class BettingSystem {
    // Create a GUI for the betting system
    private JFrame bettingFrame;
    private JTextField betAmountField;
    private JTextField horseNameField;

    public static void main(String[] args) {
        // Create an instance of the BettingSystem class to display the GUI
        BettingSystem bettingSystem = new BettingSystem();
        bettingSystem.bettingGUI();
    }

    public void bettingGUI() {
        // Create a frame for the betting
        JFrame bettingFrame = new JFrame("Betting System");
        bettingFrame.setSize(800, 600);
        bettingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bettingFrame.setLocationRelativeTo(null); // Center the frame on the screen
        bettingFrame.setLayout(null); // Use null layout for absolute positioning
        
        // Create a panel for the betting system
        JPanel bettingPanel = new JPanel();
        bettingPanel.setLayout(null); // Use null layout for absolute positioning
        bettingPanel.setBackground(Color.LIGHT_GRAY); // Set background color for the betting panel
        bettingPanel.setBounds(0, 0, 800, 600); // Set bounds for the betting panel
        // Set a black border for the betting panel
        bettingPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
       
        // display the list of horses in the betting panel
        JLabel horseListLabel = new JLabel("List of Horses:"); // Set the label for the horse list
        horseListLabel.setBounds(350, 10, 200, 30); // Set position and size of the label
        horseListLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set font for the label
        bettingPanel.add(horseListLabel); // Add the label to the betting panel  

        // Create a list of horses (this should be replaced with actual horse data)
        String[] horseNames = {"Thunder", "Lightning", "Storm", "Blaze", "Shadow"}; // Example horse names
        int num = 0;
        for (String horse : horseNames) {
            // Create a label for each horse and add it to the betting panel
            JLabel horseLabel = new JLabel(horse); // Create a label for the horse name
            horseLabel.setBounds(200 + num, 40, 200, 30); // Set position and size of the label
            horseLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set font for the label
            horseLabel.setForeground(Color.BLACK); // Set text color for the label
            bettingPanel.add(horseLabel); // Add the label to the betting panel
            num += 90; // Increment the position for the next horse label
        }

        // Create a label for the name of the horse
        JLabel horseNameLabel = new JLabel("Enter the name of the horse:"); // Set the horse name
        horseNameLabel.setBounds(200, 100, 400, 30); // Set position and size of the label
        horseNameLabel.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font for the label
        bettingPanel.add(horseNameLabel); // Add the label to the betting panel

        // Create a text field for the user to enter the horse name
        JTextField horseNameField = new JTextField(); // Create a text field for the horse name
        horseNameField.setBounds(450, 100, 150, 30); // Set position and size of the text field
        horseNameField.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font for the text field
        horseNameField.setText(""); // Set default text for the text field
        bettingPanel.add(horseNameField); // Add the text field to the betting panel
        
        // Create a label for the betting amount
        JLabel betAmountLabel = new JLabel("Enter your bet amount:");
        betAmountLabel.setBounds(200, 150, 200, 30); // Set position and size of the label
        betAmountLabel.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font for the label
        bettingPanel.add(betAmountLabel); // Add the label to the betting panel

        // Create a text field for the user to enter the bet amount
        JTextField betAmountField = new JTextField();
        betAmountField.setBounds(450, 150, 150, 30); // Set position and size of the text field
        betAmountField.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font for the text field
        bettingPanel.add(betAmountField); // Add the text field to the betting panel

        // add buttons to the betting panel
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save Horse");
        saveButton.addActionListener(e -> saveBet());
        
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> resetForm());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(resetButton);
        
        buttonPanel.setBounds(200, 200, 400, 50); // Set position and size of the button panel
        buttonPanel.setBackground(Color.LIGHT_GRAY); // Set background color for the button panel
        
        bettingFrame.add(buttonPanel); // Add the button panel to the betting panel
        
        bettingFrame.add(bettingPanel); // Add the betting panel to the frame
        

        // Display the frame
        bettingFrame.setVisible(true);
        bettingFrame.setResizable(false); // Disable resizing of the frame

    }

    public static void saveBet() {
        System.out.println("Bet saved successfully!"); // Display a message when the bet is saved
    }

    public void resetForm() {
        // Reset the form fields to their default values
        horseNameField.setText(""); // Clear the horse name field
        betAmountField.setText(""); // Clear the bet amount field
    }
}