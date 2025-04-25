import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// RaceManager class to handle race logic
public class RaceManager {
    public ArrayList<HorseGraphic> horsesGUI = new ArrayList<>();
    public JPanel trackPanel;
    public Timer raceTimer;
    public int trackLength;
    public JLabel resultLabel;
    public boolean raceInProgress = false;
    public HorseGraphic.TrackType trackType;
    public Map<Integer, Horse> horses;
    public String horseBet;
    public int betAmount;
    public long raceStartTime;
    public long elapsedTimeMs;
    public String winner;
    public String weather;

    // For displaying history
    public static ArrayList<String> bettedHorseNames = new ArrayList<>();
    public static ArrayList<String> weathers = new ArrayList<>();
    public static ArrayList<String> winners = new ArrayList<>();
    public static ArrayList<String> times = new ArrayList<>();
    public static ArrayList<String> bettingOdds = new ArrayList<>();
    public static ArrayList<String> betAmounts = new ArrayList<>();
    public static ArrayList<String> betResults = new ArrayList<>();

    
    public RaceManager(JPanel trackPanel, int trackLength, int lanes, HorseGraphic.TrackType trackType, Map<Integer, Horse> horses, String horseBet, int betAmount, String weather) {
        this.horses = horses;
        this.trackPanel = trackPanel;
        this.trackLength = trackLength;
        this.trackType = trackType;
        this.horseBet = horseBet;
        this.betAmount = betAmount;
        this.weather = weather;
        
        // Create result display (with darker text for visibility)
        resultLabel = new JLabel("Click 'Start Race' to begin");
        resultLabel.setBounds(10, 0, trackLength - 20, 15);
        resultLabel.setForeground(Color.BLACK);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 14));
        trackPanel.add(resultLabel);
        
        // Calculate lane height based on track type
        int laneHeight;
        switch(trackType) {
            case RECTANGULAR:
                laneHeight = 400 / lanes;
                break;
            case OVAL:
            case HALFOVAL:
            default:
                laneHeight = 20; // Fixed height for oval and half-oval
                break;
        }
        
        // Create HorseGraphics 
        int i = 0;
        for (Horse horse : horses.values()) {
            HorseGraphic horse1 = new HorseGraphic(horse.getName(), i, horse.getSymbol(), laneHeight, horse.getColourFromString(horse.getCoatColor()), trackType, horse.getConfidence(), horse.getStamina(), horse.getSpeed(), horse.getOdd());
            System.out.println("Horse " + horse1.getName() + " created with color: " + horse1.color);
            horsesGUI.add(horse1);
            i++;
            
            // Add HorseGraphic panel to track panel
            trackPanel.add(horse1.getHorseGraphicPanel());
        }
        
        // Important: make sure HorseGraphics are visible by setting their z-order to top
        for (HorseGraphic horse : horsesGUI) {
            trackPanel.setComponentZOrder(horse.getHorseGraphicPanel(), 0);
        }
        trackPanel.setComponentZOrder(resultLabel, 0);
        
        // Refresh panel to make HorseGraphics visible
        trackPanel.revalidate();
        trackPanel.repaint();
    }
    
    public void startRace() {
        raceStartTime = System.currentTimeMillis();

        if (raceInProgress) return;
        
        raceInProgress = true;
        resultLabel.setText("Race in progress...");
        
        // Create timer to update HorseGraphic positions
        raceTimer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean raceFinished = updateRace();
                if (raceFinished) {
                    raceTimer.stop();
                    raceInProgress = false;
                }
            }
        });
        raceTimer.start();
    }
    
    public boolean updateRace() {
        int count = 0;
        boolean someoneFinished = false;
        winner = "";

        // calculate elapsed time
        long currentTime = System.currentTimeMillis();
        elapsedTimeMs = currentTime - raceStartTime;

        // Convert to minutes:seconds.milliseconds format
        long minutes = (elapsedTimeMs / 60000);
        long seconds = (elapsedTimeMs % 60000) / 1000;
        long milliseconds = elapsedTimeMs % 1000;

        String timeString = String.format("%02d:%02d.%03d", minutes, seconds, milliseconds);

        
        for (HorseGraphic horse : horsesGUI) {
            horse.move();

            resultLabel.setText("Race in progress... Time: " + timeString + "s");
            
            // Check if any HorseGraphic has finished, based on track type
            boolean hasFinished = false;
            
            switch(trackType) {
                case RECTANGULAR:
                    hasFinished = horse.getX() >= trackLength - 40;
                    break;
                case OVAL:
                    // Track lap completion
                    boolean hasCompletedLap = false;

                    // Finish when crossing finish line at top (angle near 3π/2)
                    double angleDiff = Math.abs(horse.getAngle() - (3 * Math.PI / 2));
                
                    // assign hasCompletedLap based on angleDiff being at near the finish line
                    if (angleDiff > 3) {
                        hasCompletedLap = true;
                    }
                    
                    // assign hasFinished based on angleDiff and hasCompletedLap
                    hasFinished = (angleDiff > 3.2 && angleDiff < 3.27) && hasCompletedLap;
                    
                    System.out.println("Horse name: " + horse.getName() + " | has finished: " + hasFinished + " | angleDiff: " + angleDiff + " | hasCompletedLap: " + hasCompletedLap);
                    break;
                case HALFOVAL:
                    // Finish when crossing finish line at top (angle near 3π/2)
                    double angleDiff1 = Math.abs(horse.getAngle() - (3 * Math.PI / 2));
                    hasFinished = angleDiff1 < 0.1 && horse.getAngle() > Math.PI;
            }
            
            if (hasFinished && !someoneFinished) {
                someoneFinished = true;
                winner = horse.getName();
                System.out.println(horse.getName() + " has finished");
            }

            // Check if HorseGraphic has fallen
            if (horse.hasFallen) {
                count++;
            }
        }
        
        // Manual repaint after all HorseGraphics have moved
        trackPanel.repaint();
        
        if (someoneFinished) {
            resultLabel.setText(winner + " wins the race! Fastest time: " + timeString + "s");
            displayBet(winner, timeString);
            displayStatistics();
           
            return true;
        }
        // set label to say that all horses fell
        if (count == horsesGUI.size()) {
            resultLabel.setText("All horses have fallen! Race time: " + timeString + "s");
            displayBetForFallenHorses(timeString);
            displayStatistics();
            return true;
        }
        return false;
    }
    
    public void resetRace() {
        if (raceTimer != null && raceTimer.isRunning()) {
            raceTimer.stop();
        }
        
        for (HorseGraphic HorseGraphic : horsesGUI) {
            HorseGraphic.reset();

        }
        
        resultLabel.setText("Click 'Start Race' to begin");
        raceInProgress = false;
        trackPanel.repaint();
    }

    public void displayBet(String winner, String timestring) {
        /**
         * * Display the betting result based on the winner and the horse bet
         * If the horse bet is the same as the winner, display a message saying that the user won
         *     - The winner will get the bet amount multiplied by the betting odd of the horse
         * If the horse bet is not the same as the winner, display a message saying that the user lost
         * *     - The loser will lost the percentage of the bet amount based on the betting odd of the horse
         */
        if (!winner.equals(this.horseBet)) {
            // Calculate the amount lost based on the betting odd of the horse
            double oddPredict = 0;
            for (HorseGraphic horse : horsesGUI) {
                if (horse.getName().equals(this.horseBet)) {
                    oddPredict = horse.getOdd();
                }
            }
            double oddPercent = oddPredict / 3;
            double lostAmount = betAmount * oddPercent;
            JOptionPane.showMessageDialog(trackPanel, winner + " has won the race in " + timestring + "s\nYou lost £" + (int) lostAmount + " on " + this.horseBet + " with betting odd of " 
            + oddPredict, "Betting Result", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // calculate the amount won based on the betting odd of the horse
            double oddPredict = 0;
            for (HorseGraphic horse : horsesGUI) {
                if (horse.getName().equals(this.horseBet)) {
                    oddPredict = horse.getOdd();
                }
            }
            double oddPercent = oddPredict / 3;
            double wonAmount = betAmount + (betAmount * oddPercent);
            JOptionPane.showMessageDialog(trackPanel, winner + " has won the race in " + timestring + "s\nYou won £" + (int) wonAmount + " on " + this.horseBet + " with betting odd of "
             + oddPredict, "Betting Result", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void displayBetForFallenHorses(String timestring) {
        // Calculate the amount lost based on the betting odd of the horse
        double oddPredict = 0;
        for (HorseGraphic horse : horsesGUI) {
            if (horse.getName().equals(this.horseBet)) {
                oddPredict = horse.getOdd();
            }
        }
        double oddPercent = oddPredict / 3;
        double lostAmount = betAmount * oddPercent;
        JOptionPane.showMessageDialog(trackPanel, "Race completed at " + timestring + "s\nAll horses have fallen! \nYou lost £" + (int) lostAmount + " on " + this.horseBet 
        + " with betting odd of " + oddPredict, "Betting Result", JOptionPane.INFORMATION_MESSAGE);
        
    }

    public void displayStatistics() {
        // Create and configure the statistics window
        JFrame statsFrame = new JFrame("Statistics");
        statsFrame.setSize(800, 600);
        statsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        statsFrame.setLayout(new BorderLayout());

        // Create main panel with proper spacing and center alignment
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.setBackground(new Color(173, 216, 230));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Add title with center alignment
        JLabel titleLabel = new JLabel("Horse Statistics");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statsPanel.add(titleLabel);
        
        statsPanel.add(Box.createVerticalStrut(20));
        
        // Create centered panel for bet information
        JPanel betPanel = new JPanel();
        betPanel.setLayout(new BoxLayout(betPanel, BoxLayout.Y_AXIS));
        betPanel.setBackground(new Color(173, 216, 230));
        betPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel betLabel = new JLabel("Your Bet: " + this.horseBet + " - Amount: £" + this.betAmount);
        betLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        betLabel.setForeground(Color.WHITE);
        betLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // check if winner is empty, if so, set it to "All horses have fallen"
        if (this.winner.equals("")) {
            this.winner = "All horses have fallen";
        }
        JLabel winnerLabel = new JLabel("Winner: " + this.winner);
        winnerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        winnerLabel.setForeground(new Color(0, 128, 0));
        winnerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        betPanel.add(betLabel);
        betPanel.add(Box.createVerticalStrut(5));
        betPanel.add(winnerLabel);
        statsPanel.add(betPanel);
        
        statsPanel.add(Box.createVerticalStrut(15));
        
        // Add race time information (centered)
        long timeMs = this.elapsedTimeMs / 1000;
        JLabel timeLabel = new JLabel("Time: " + timeMs + "s");
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statsPanel.add(timeLabel);
        
        statsPanel.add(Box.createVerticalStrut(30));
        
        // Create a panel for horse statistics (centered)
        JPanel horsesPanel = new JPanel();
        horsesPanel.setLayout(new BoxLayout(horsesPanel, BoxLayout.Y_AXIS));
        horsesPanel.setBackground(new Color(173, 216, 230));
        horsesPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add horse statistics with center alignment
        for (HorseGraphic horse : horsesGUI) {
            String horseStats = String.format("%s - Speed: %s, Stamina: %.1f, Confidence: %.1f, Betting Odd: %.1f", 
                    horse.getName(), 
                    horse.getSpeed(), 
                    horse.getStamina(), 
                    horse.getName().equals(this.winner) ? (horse.getConfidence() + 0.1) : (horse.getConfidence() - 0.1),
                    horse.getOdd());
            
            JLabel horseLabel = new JLabel(horseStats);
            horseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            if (horse.getName().equals(this.winner)) {
                horseLabel.setFont(new Font("Arial", Font.BOLD, 16));
                horseLabel.setForeground(Color.RED);
            } else {
                horseLabel.setFont(new Font("Arial", Font.PLAIN, 16));
                horseLabel.setForeground(Color.WHITE);
            }
            
            horsesPanel.add(horseLabel);
            horsesPanel.add(Box.createVerticalStrut(10));
        }
        
        // Add horse statistics panel to a scroll pane with center alignment
        JScrollPane scrollPane = new JScrollPane(horsesPanel);
        scrollPane.setBorder(null);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        statsPanel.add(scrollPane);

        // Add history button
        JButton historyButton = new JButton("View History");
        historyButton.setFont(new Font("Arial", Font.PLAIN, 16));
        historyButton.setBackground(Color.WHITE);
        historyButton.setForeground(Color.BLACK);
        historyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // add the results to the history
                double bettingOdd = 0;
                for (HorseGraphic horse : horsesGUI) {
                    if (horse.getName().equals(horseBet)) {
                        bettingOdd = horse.getOdd();
                    }
                }
                String betResult = winner.equals(horseBet) ? "You won" : "You lost";
                // input the results into the history
                addResults(horseBet, weather, winner, String.valueOf(timeMs), String.valueOf(bettingOdd), String.valueOf(betAmount * 2), betResult); // Example values for weather and betting odd
                // Call the history method to display the history
                // close the statistics window
                statsFrame.dispose();
                history();
            }
        });
        statsPanel.add(historyButton);
        
        // Center the main panel in the frame
        JPanel centeringPanel = new JPanel(new GridBagLayout());
        centeringPanel.setBackground(new Color(173, 216, 230));
        centeringPanel.add(statsPanel);
        
        // Add main panel to frame and display
        statsFrame.add(centeringPanel, BorderLayout.CENTER);
        statsFrame.setVisible(true);
        statsFrame.setResizable(false);

    }

    public static void history() {
        // displays all of the past races such as the betted horse names, the weather, the winner, the time, the betting odd and the bet amount
        /**
         * in raceManager class, create arrayLists of the following: betted horse names, weather, winner, time, betting odd and bet amount
         * create a JFrame to display the history, using a loop to display each race in a new line
         */
        JFrame historyFrame = new JFrame("History");
        historyFrame.setSize(1000, 600);
        historyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        historyFrame.setLayout(new BorderLayout());
        historyFrame.setBackground(new Color(173, 216, 230));
        historyFrame.setResizable(false);

        // Create main panel with proper spacing and center alignment
        JPanel historyPanel = new JPanel();
        historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
        historyPanel.setBackground(new Color(173, 216, 230));
        historyPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        historyPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Add title with center alignment
        JLabel titleLabel = new JLabel("History");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        historyPanel.add(titleLabel);

        // make a new line
        historyPanel.add(Box.createVerticalStrut(20));

        // Create a panel for horse statistics (centered)
        JPanel horsesPanel = new JPanel();
        horsesPanel.setLayout(new BoxLayout(horsesPanel, BoxLayout.Y_AXIS));
        horsesPanel.setBackground(new Color(173, 216, 230));
        horsesPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // add horse statistics with center alignment using a loop
        for (int i = 0; i < bettedHorseNames.size(); i++) {
            String horseStats = String.format("<html><b>Bet:</b> %s - <b>Weather:</b> %s - <b>Winner:</b> %s - <b>Time:</b> %ss - <b>Betting Odd:</b> %s - <b>Bet Amount:</b> £%s - <b>Bet Result:</b> %s</html>", 
                    bettedHorseNames.get(i), 
                    weathers.get(i), 
                    winners.get(i), 
                    times.get(i), 
                    bettingOdds.get(i), 
                    betAmounts.get(i),
                    betResults.get(i));
            
            JLabel horseLabel = new JLabel(horseStats);
            horseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            horseLabel.setFont(new Font("Arial", Font.PLAIN, 15));
            horseLabel.setForeground(Color.WHITE);
            
            horsesPanel.add(horseLabel);
            horsesPanel.add(Box.createVerticalStrut(10));
        }
        // Add horse statistics panel to a scroll pane with center alignment
        JScrollPane scrollPane = new JScrollPane(horsesPanel);
        scrollPane.setBorder(null);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        historyPanel.add(scrollPane);

        // Center the main panel in the frame
        JPanel centeringPanel = new JPanel(new GridBagLayout());
        centeringPanel.setBackground(new Color(173, 216, 230));
        centeringPanel.add(historyPanel);

        // Add main panel to frame and display
        historyFrame.add(centeringPanel, BorderLayout.CENTER);
        historyFrame.setVisible(true);
        historyFrame.setResizable(false);
    }

    public void addResults(String horseName, String weather, String winner, String time, String bettingOdd, String betAmount, String betResult) {
        bettedHorseNames.add(horseName);
        weathers.add(weather);
        // check if there is a winner, if not, add "All horses have fallen"
        if (winner.equals("")) {
            winner = "All horses have fallen";
        }
        winners.add(winner);
        times.add(time);
        bettingOdds.add(bettingOdd);
        betAmounts.add(betAmount);
        betResults.add(betResult);
    }
}