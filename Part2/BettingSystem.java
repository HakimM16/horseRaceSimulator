package Part2;

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class BettingSystem {
    // Create a GUI for the betting system
    private JFrame bettingFrame;
    private JTextField betAmountField;
    private JTextField horseNameField;
    // values from HorseGUI
    private int lanes; // number of lanes in the track
    private int length; // length of the track
    private String trackShape; // shape of the track
    private String weatherCondition; // weather condition
    private Map<Integer, Horse> HorseList;
    // name of horse and the amount of bet
    private String horseName; // name of the horse
    private int betAmount; // amount of the bet

    public BettingSystem(int lanes, int length, String trackShape, String weatherCondition, Map<Integer, Horse> finalHorseList) {
        this.lanes = lanes; // Set the number of lanes in the track
        this.length = length; // Set the length of the track
        this.trackShape = trackShape; // Set the shape of the track
        this.weatherCondition = weatherCondition; // Set the weather condition
        this.HorseList = finalHorseList; // Set the horse list
        bettingGUI();
    }

    public static void main(String[] args) {
        Map<Integer, Horse> horseList = new HashMap<>(); // Create a map to store horse objects
        horseList.put(1, new Horse("Highlander", "Brown", "H")); // Add a horse to the list
        horseList.put(2, new Horse("Thunder", "WHITE", "T")); // Add another horse to the list
        horseList.put(3, new Horse("Lightning", "White", "L")); // Add another horse to the list
        horseList.put(4, new Horse("Storm", "Gray", "S")); // Add another horse to the list
        horseList.put(5, new Horse("Blaze", "Chestnut", "B")); // Add another horse to the list

        BettingSystem bettingSystem = new BettingSystem(3, 1000, "Oval", "Sunny", horseList); // Create a new betting system with the horse list
    }

    public void bettingGUI() {
        // Create a frame for the betting
        this.bettingFrame = new JFrame("Betting System");
        bettingFrame.setSize(800, 600);
        bettingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bettingFrame.setLocationRelativeTo(null); // Center the frame on the screen
        bettingFrame.setLayout(null); // Use null layout for absolute positioning
        
        // Create a panel for the betting system
        JPanel bettingPanel = new JPanel();
        bettingPanel.setLayout(null); // Use null layout for absolute positioning
        bettingPanel.setBackground(new Color(0, 128, 0)); // Set background color for the betting panel
        bettingPanel.setBounds(0, 0, 800, 600); // Set bounds for the betting panel
        // Set a WHITE border for the betting panel
        bettingPanel.setBorder(BorderFactory.createLineBorder(new Color(183, 237, 255), 2));
       
        // display the list of horses in the betting panel
        JLabel horseListLabel = new JLabel("List of Horses:"); // Set the label for the horse list
        horseListLabel.setBounds(350, 10, 200, 30); // Set position and size of the label
        horseListLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set font for the label
        horseListLabel.setForeground(new Color(183, 237, 255)); // Set text color for the label
        bettingPanel.add(horseListLabel); // Add the label to the betting panel  

        // display horse name and odds in panel
        JPanel horsePanel = new JPanel(); // Create a panel for the horse names and odds
        horsePanel.setLayout(new GridLayout(HorseList.size(), 2)); // Set layout for the horse panel
        horsePanel.setBounds(250, 40, 400, 300); // Set position and size of the horse panel
        horsePanel.setBackground(new Color(0, 128, 0)); // Set background color for the horse panel

        // output the horse names in the betting panel
        int num = 0;
        for (Horse horse : HorseList.values()) {
            // Create a label for each horse and add it to the betting panel
            JLabel horseLabel = new JLabel(horse.getName()); // Create a label for the horse name
            JLabel oddsLabel = new JLabel("Odds: " + horse.getOdd());
            horseLabel.setBounds(200, 40 + num, 200, 30); // Set position and size of the label
            horseLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set font for the label
            horseLabel.setForeground(new Color(183, 237, 255)); // Set text color for the label

            oddsLabel.setBounds(400, 40 + num, 200, 30); // Set position and size of the odds label
            oddsLabel.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font for the odds label
            oddsLabel.setForeground(new Color(183, 237, 255)); // Set text color for the odds label
            
            horsePanel.add(horseLabel); // Add the label to the betting panel
            horsePanel.add(oddsLabel); // Add the odds label to the horse panel
            num += 30; // Increment the position for the next horse label
        }

        bettingPanel.add(horsePanel); // Add the horse panel to the betting panel
        // Create a label for the name of the horse
        JLabel horseNameLabel = new JLabel("Enter the name of the horse:"); // Set the horse name
        horseNameLabel.setBounds(200, 350, 400, 30); // Set position and size of the label
        horseNameLabel.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font for the label
        horseNameLabel.setForeground(new Color(183, 237, 255)); // Set text color for the label
        bettingPanel.add(horseNameLabel); // Add the label to the betting panel

        // Create a text field for the user to enter the horse name
        horseNameField = new JTextField(); // Create a text field for the horse name
        horseNameField.setBounds(450, 350, 150, 30); // Set position and size of the text field
        horseNameField.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font for the text field
        horseNameField.setHorizontalAlignment(JTextField.CENTER); // Center the text in the text field
        horseNameField.setText("Highlander"); // Set default text for the text field
        horseNameField.addActionListener(e -> setName(horseNameField.getText())); // Set action listener for the text field
        bettingPanel.add(horseNameField); // Add the text field to the betting panel
        
        // Create a label for the betting amount
        JLabel betAmountLabel = new JLabel("Enter your bet amount:");
        betAmountLabel.setBounds(200, 400, 200, 30); // Set position and size of the label
        betAmountLabel.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font for the label
        betAmountLabel.setForeground(new Color(183, 237, 255)); // Set text color for the label
        bettingPanel.add(betAmountLabel); // Add the label to the betting panel

        // Create a text field for the user to enter the bet amount
        betAmountField = new JTextField();
        betAmountField.setBounds(450, 400, 150, 30); // Set position and size of the text field
        betAmountField.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font for the text field
        betAmountField.setHorizontalAlignment(JTextField.CENTER); // Center the text in the text field
        betAmountField.setText("400"); // Set default text for the text field
        betAmountField.addActionListener(e -> setBetAmount(Integer.parseInt(betAmountField.getText()))); // Set action listener for the text field
        betAmountField.setToolTipText("Enter the amount in numbers"); // Set tooltip for the text field
        bettingPanel.add(betAmountField); // Add the text field to the betting panel

        // add buttons to the betting panel
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save Horse");
        saveButton.addActionListener(e -> saveBet());
        saveButton.setBackground(new Color(183, 237, 255)); // Set background color for the save button
        saveButton.setForeground(Color.BLACK); // Set text color for the save button
        saveButton.setPreferredSize(new Dimension(150, 30)); // Set preferred size for the save button
        saveButton.setFont(new Font("Arial", Font.BOLD, 16)); // Set font for the save button
        
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> resetForm());
        resetButton.setBackground(new Color(183, 237, 255)); // Set background color for the reset button
        resetButton.setForeground(Color.BLACK); // Set text color for the reset button
        resetButton.setPreferredSize(new Dimension(150, 30)); // Set preferred size for the reset button
        resetButton.setFont(new Font("Arial", Font.BOLD, 16)); // Set font for the reset button
        
        buttonPanel.add(saveButton);
        buttonPanel.add(resetButton);
        
        buttonPanel.setBounds(200, 450, 400, 50); // Set position and size of the button panel
        buttonPanel.setBackground(new Color(0, 128, 0)); // Set background color for the button panel
        
        bettingFrame.add(buttonPanel); // Add the button panel to the betting panel
        
        bettingFrame.add(bettingPanel); // Add the betting panel to the frame
        

        // Display the frame
        bettingFrame.setVisible(true);
        bettingFrame.setResizable(false); // Disable resizing of the frame

    }

    public void saveBet() {
        // Get the horse name and bet amount from the text fields
        System.out.println("horseName: " + horseNameField.getText()); // Display the horse name in the console
        System.out.println("betAmount: " + betAmountField.getText()); // Display the bet amount in the console
        this.horseName = horseNameField.getText(); // Get the horse name from the text field
        try {
            this.betAmount = Integer.parseInt(betAmountField.getText()); // Get the bet amount from the text field
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter the amount in numbers.", "Invalid Input", JOptionPane.ERROR_MESSAGE); // Display an error message if the input is invalid
            return; // Exit the method if the input is invalid
        }

        // check if the horse name is empty
        if (horseName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a horse name.", "Invalid Input", JOptionPane.ERROR_MESSAGE); // Display an error message if the horse name is empty
            return; // Exit the method if the horse name is empty
        }

        // check if horse name is in the horse list
        boolean horseExists = false; // Flag to check if the horse exists in the list
        for (Horse horse : HorseList.values()) {
            if (horse.getName().equalsIgnoreCase(horseName)) { // Check if the horse name matches any horse in the list
                horseExists = true; // Set the flag to true if the horse exists
                break; // Exit the loop if the horse is found
            }
        }
        if (!horseExists) {
            JOptionPane.showMessageDialog(null, "Horse not found in the list.", "Invalid Input", JOptionPane.ERROR_MESSAGE); // Display an error message if the horse is not found
            return; // Exit the method if the horse is not found
        }
        if (betAmount <= 0 || betAmount > 100000) { // Check if the bet amount is valid (between 1 and 1000)
            JOptionPane.showMessageDialog(null, "Please enter a valid bet amount between 0 to 100000.", "Invalid Input", JOptionPane.ERROR_MESSAGE); // Display an error message if the bet amount is invalid
            return; // Exit the method if the bet amount is invalid
        }


        System.out.println("Bet saved successfully!"); // Display a message when the bet is saved
        // get bet odd from horseName
        double odd = 0; // Initialize the betting odds
        for (Horse horse : HorseList.values()) {
            if (horse.getName().equalsIgnoreCase(horseName)) { // Check if the horse name matches any horse in the list
                odd = horse.getOdd(); // Get the betting odds for the horse
                break; // Exit the loop after getting the odds
            }
        }
        // potential winnings
        int potentialWinnings = (int)( betAmount + (betAmount * (odd / 3))); // Calculate potential winnings based on the betting odds
        // display the horse name and the bet amount
        JOptionPane.showMessageDialog(null, "Horse Name: " + horseName + "\nBet Amount: £" + betAmount + "\nBetting Odds: " + odd + 
        "\nPotential winnings: £" + potentialWinnings, "Bet Confirmation", JOptionPane.INFORMATION_MESSAGE);

        // close the betting frame
        bettingFrame.dispose(); // Dispose of the betting frame

        // call RaceTrackApplication
        RaceTrackApplication raceTrackApplication = new RaceTrackApplication(this.lanes, this.length, this.trackShape, this.weatherCondition, this.HorseList, this.horseName, this.betAmount); // Create

    }

    public void resetForm() {
        // Reset the form fields to their default values
        horseNameField.setText(""); // Clear the horse name field
        betAmountField.setText(""); // Clear the bet amount field
    }

    public void setName(String horseName) {
        this.horseName = horseName; // Set the horse name
    }

    public void setBetAmount(int betAmount) {
        this.betAmount = betAmount; // Set the bet amount
    }
}