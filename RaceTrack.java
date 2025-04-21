import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class RaceTrack{
    // Car class to represent each racing car
    static class Car {
        private int x; // X position on track
        private int y; // Y position within lane
        private int laneNumber; // Which lane the car is in
        private int speed; // How fast the car moves each update
        private Color color; // Car color
        private String name; // Car name/identifier
        private JPanel carPanel; // Visual representation
        
        public Car(String name, int laneNumber, int laneHeight, Color color) {
            this.name = name;
            this.laneNumber = laneNumber;
            this.x = 10; // Start position
            this.y = laneNumber * laneHeight + laneHeight/4; // Position in middle of lane
            this.speed = 1 + (int)(Math.random() * 3); // Random speed between 1-3
            this.color = color;
            
            // Create visual representation with border for better visibility
            carPanel = new JPanel();
            carPanel.setBackground(color);
            carPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            carPanel.setBounds(x, y, 30, laneHeight/2); // Car size
            
            // Add car number label to make it more visible
            JLabel carLabel = new JLabel(name.substring(name.length()-1));
            carLabel.setForeground(Color.WHITE);
            carLabel.setHorizontalAlignment(SwingConstants.CENTER);
            carPanel.setLayout(new BorderLayout());
            carPanel.add(carLabel, BorderLayout.CENTER);
        }
        
        public void move() {
            // Random speed variation to make race interesting
            int randomFactor = (int)(Math.random() * 3) - 1; // -1, 0, or 1
            x += speed + randomFactor;
            carPanel.setBounds(x, y, 30, carPanel.getHeight());
        }
        
        public JPanel getCarPanel() {
            return carPanel;
        }
        
        public int getX() {
            return x;
        }
        
        public String getName() {
            return name;
        }
        
        // Method to reset car position
        public void reset() {
            x = 10;
            carPanel.setBounds(x, y, 30, carPanel.getHeight());
        }
    }

    // RaceManager class to handle race logic
    static class RaceManager {
        private ArrayList<Car> cars = new ArrayList<>();
        private JPanel trackPanel;
        private Timer raceTimer;
        private int trackLength;
        private int finishLine;
        private JLabel resultLabel;
        private boolean raceInProgress = false;
        
        public RaceManager(JPanel trackPanel, int trackLength, int lanes) {
            this.trackPanel = trackPanel;
            this.trackLength = trackLength;
            this.finishLine = trackLength - 40; // Account for car length
            
            // Create result display (with darker text for visibility)
            resultLabel = new JLabel("Click 'Start Race' to begin");
            resultLabel.setBounds(10, 0, trackLength - 20, 20);
            resultLabel.setForeground(Color.BLACK);
            resultLabel.setFont(new Font("Arial", Font.BOLD, 14));
            trackPanel.add(resultLabel);
            
            // Calculate lane height
            int laneHeight = 400 / lanes;
            
            // Create cars with different colors
            Color[] carColors = {Color.RED, Color.BLUE, Color.YELLOW, Color.MAGENTA, Color.ORANGE};
            for (int i = 0; i < lanes; i++) {
                Car car = new Car("Car " + (i+1), i, laneHeight, carColors[i % carColors.length]);
                cars.add(car);
                // Add car panel to track panel - make sure to add it AFTER lanes are created
                trackPanel.add(car.getCarPanel());
            }
            
            // Important: make sure cars are visible by setting their z-order to top
            for (Car car : cars) {
                trackPanel.setComponentZOrder(car.getCarPanel(), 0);
            }
            trackPanel.setComponentZOrder(resultLabel, 0);
            
            // Refresh panel to make cars visible
            trackPanel.revalidate();
            trackPanel.repaint();

            // Set layout to null to allow absolute positioning
            trackPanel.setLayout(null);
        }
        
        public void startRace() {
            if (raceInProgress) return;
            
            raceInProgress = true;
            resultLabel.setText("Race in progress...");
            
            // Create timer to update car positions
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
        
        private boolean updateRace() {
            boolean someoneFinished = false;
            String winner = "";
            
            for (Car car : cars) {
                car.move();
                
                // Check if any car crossed the finish line
                if (car.getX() >= finishLine && !someoneFinished) {
                    someoneFinished = true;
                    winner = car.getName();
                }
            }
            
            // Manual repaint after all cars have moved
            trackPanel.repaint();
            
            if (someoneFinished) {
                resultLabel.setText(winner + " wins the race!");
                return true;
            }
            return false;
        }
        
        public void resetRace() {
            if (raceTimer != null && raceTimer.isRunning()) {
                raceTimer.stop();
            }
            
            for (Car car : cars) {
                car.reset();
            }
            
            resultLabel.setText("Click 'Start Race' to begin");
            raceInProgress = false;
            trackPanel.repaint();
        }
    }

    // Method to create rectangular track
    public static void createRectangularTrack(int length, int lanes) {
        // Create a frame for the track
        JFrame trackFrame = new JFrame("Race Track");
        trackFrame.setSize(length + 50, 600); // Make sure frame is large enough
        trackFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        trackFrame.setLocationRelativeTo(null); // Center the frame on the screen
        
        // Use BorderLayout for overall organization
        trackFrame.setLayout(new BorderLayout());

        // Create a panel to represent the track
        JPanel trackPanel = new JPanel();
        trackPanel.setPreferredSize(new Dimension(length, 400)); // Set fixed size
        trackPanel.setBackground(new Color(34, 139, 34)); // Green background for grass
        trackPanel.setLayout(null); // Use absolute positioning for lanes

        // Add lanes to the track
        int laneHeight = 400 / lanes; // Calculate lane height based on fixed panel height
        for (int i = 0; i < lanes; i++) {
            JPanel lane = new JPanel();
            lane.setBounds(0, i * laneHeight, length, laneHeight - 5); // Leave space between lanes
            lane.setBackground(new Color(173, 216, 230)); // Light blue color for lanes
            trackPanel.add(lane);
        }

        // Make finish line
        JPanel finishLine = new JPanel();
        finishLine.setBounds(length - 10, 0, 10, 400); // Position at the end of the track
        finishLine.setBackground(Color.WHITE); // White color for the finish line
        trackPanel.add(finishLine);
        
        // Create control panel
        JPanel controlPanel = new JPanel();
        JButton startButton = new JButton("Start Race");
        JButton resetButton = new JButton("Reset Race");
        controlPanel.add(startButton);
        controlPanel.add(resetButton);

        // create basic panel - this is for the statistics and other information
        JPanel basicPanel = new JPanel();
        basicPanel.setLayout(new BorderLayout());
        basicPanel.setPreferredSize(new Dimension(length / 2, 50)); // Set fixed size for basic panel
        
        // Add panels to frame
        trackFrame.add(trackPanel, BorderLayout.WEST);
        trackFrame.add(controlPanel, BorderLayout.SOUTH);
        trackFrame.add(basicPanel, BorderLayout.EAST); // Add basic panel to the top
        
        // Create race manager - after all track elements are added
        RaceManager raceManager = new RaceManager(trackPanel, length, lanes);
        
        // Add action for start button
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                raceManager.startRace();
            }
        });
        
        // Add action for reset button
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                raceManager.resetRace();
            }
        });

        // Display the frame
        trackFrame.pack();
        trackFrame.setVisible(true);
    }

    // Main method to run the application
    public static void main(String[] args) {
        // Run GUI construction on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createRectangularTrack(600, 5); // Create a track with length 600 and 5 lanes
            }
        });
    }
}