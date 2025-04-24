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
    
    public RaceManager(JPanel trackPanel, int trackLength, int lanes, HorseGraphic.TrackType trackType, Map<Integer, Horse> horses, String horseBet, int betAmount) {
        this.horses = horses;
        this.trackPanel = trackPanel;
        this.trackLength = trackLength;
        this.trackType = trackType;
        this.horseBet = horseBet;
        this.betAmount = betAmount;
        
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
        String winner = "";
        
        for (HorseGraphic horse : horsesGUI) {
            horse.move();
            
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
            resultLabel.setText(winner + " wins the race!");
            displayBet(winner);
            return true;
        }
        // set label to say that all horses fell
        if (count == horsesGUI.size()) {
            resultLabel.setText("All horses have fallen!");
            displayBetForFallenHorses();
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

    public void displayBet(String winner) {
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
            JOptionPane.showMessageDialog(trackPanel, "You lost £" + (int) lostAmount + " on " + this.horseBet + " with betting odd of " + oddPredict, "Betting Result", JOptionPane.INFORMATION_MESSAGE);
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
            JOptionPane.showMessageDialog(trackPanel, "You won £" + (int) wonAmount + " on " + this.horseBet + " with betting odd of " + oddPredict, "Betting Result", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void displayBetForFallenHorses() {
        // Calculate the amount lost based on the betting odd of the horse
        double oddPredict = 0;
        for (HorseGraphic horse : horsesGUI) {
            if (horse.getName().equals(this.horseBet)) {
                oddPredict = horse.getOdd();
            }
        }
        double oddPercent = oddPredict / 3;
        double lostAmount = betAmount * (1 - oddPercent);
        JOptionPane.showMessageDialog(trackPanel, "All horses have fallen! \nYou lost £" + (int) lostAmount + " on " + this.horseBet + " with betting odd of " + oddPredict, "Betting Result", JOptionPane.INFORMATION_MESSAGE);
        
    }
}