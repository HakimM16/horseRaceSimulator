import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RaceTrackApplication {
    public int lanes;
    public int length;
    public String trackShape;
    public String weather;
    public Map<Integer, Horse> horses;
    public String horseBet;
    public int betAmount;

    public RaceTrackApplication(int lanes, int length, String trackShape, String weather, Map<Integer, Horse> horses, String horseBet, int betAmount) {
        // Constructor to initialize the application
        this.lanes = lanes;
        this.length = length;
        this.trackShape = trackShape;
        this.weather = weather;
        this.horses = horses;
        this.horseBet = horseBet;
        this.betAmount = betAmount;

        switch (this.trackShape) {
            case "oval":
                createSimpleOvalTrack(this.lanes, this.horses, this.weather);
                break;
            case "rectangular":
                createRectangularTrack(this.length, this.lanes, this.horses, this.weather);
                break;
            case "half-oval":
                createHalfOvalTrack(this.lanes, this.horses, this.weather);
                break;
            default:
                throw new AssertionError();
        }
    }

    // Method to create rectangular track
    public static void createRectangularTrack(int length, int lanes, Map<Integer, Horse> horses, String weather) {
        Map<Integer, Horse> horseMap = new HashMap<>(horses);
        // Create a frame for the track
        JFrame trackFrame = new JFrame("Rectangular Race Track - " + weather);
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
        // make a loop to show the horseGraphic names and stats in the basic panel
        JPanel basicPanel = new JPanel(new FlowLayout());
        JLabel basicLabel = new JLabel("Horse Statistics");
        basicLabel.setFont(new Font("Arial", Font.BOLD, 16));
        basicLabel.setForeground(Color.BLACK);
        basicPanel.add(basicLabel);
        basicPanel.setBackground(new Color(173, 216, 230)); // Light blue color for basic panel
        basicPanel.setBorder(BorderFactory.createLineBorder(new Color(34, 139, 34), 5)); // Add border for visibility
        for (Horse horse : horses.values()) {
            JPanel horsePanel = new JPanel(new GridLayout(0, 1));
            horsePanel.setBackground(horse.getColourFromString(horse.getCoatColor()));
            JLabel horseLabel = new JLabel(horse.getName() + " - " + horse.getSymbol());
            JLabel horseStats = new JLabel("Average Speed: " + horse.getSpeed() + " | Stamina: " + horse.getStamina() + " | Confidence: " + horse.getConfidence());
            horseLabel.setForeground(Color.WHITE);
            horseStats.setForeground(Color.WHITE);
            horseLabel.setFont(new Font("Arial", Font.BOLD, 14));
            horseLabel.setPreferredSize(new Dimension(200, 20)); // Set fixed size for labels
            horsePanel.add(horseLabel);
            horsePanel.add(horseStats);
            horsePanel.setPreferredSize(new Dimension(300, 50)); // Set fixed size for horse panel
            basicPanel.add(horsePanel);
        }

        basicPanel.setPreferredSize(new Dimension(400, 50)); // Set fixed size for basic panel
        
        // Add panels to frame
        trackFrame.add(trackPanel, BorderLayout.WEST);
        trackFrame.add(basicPanel, BorderLayout.EAST); // Add basic panel to the left
        trackFrame.add(controlPanel, BorderLayout.SOUTH);
        trackFrame.setResizable(false); // Disable resizing

        
        // Create race manager - after all track elements are added
        RaceManager raceManager = new RaceManager(trackPanel, length, lanes, HorseGraphic.TrackType.RECTANGULAR, horseMap);
        
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

    // Method to create oval track with racing functionality
    public static void createSimpleOvalTrack(int lanes, Map<Integer, Horse> horses, String weather) {
        Map<Integer, Horse> horseMap = new HashMap<>(horses);
        // Create width and height
        int width = 750;
        int height = 450;
        
        // Create a frame for the track
        JFrame trackFrame = new JFrame("Oval Race Track - " + weather);
        trackFrame.setSize(800, 600);
        trackFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        trackFrame.setLocationRelativeTo(null); // Center the frame on the screen
        
        // Use BorderLayout for overall organization
        trackFrame.setLayout(new BorderLayout());

        // Create a panel to represent the oval track
        JPanel trackPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                
                // Enable antialiasing for smoother curves
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Set the background 
                g2d.setColor(new Color(34, 139, 34)); // Green background for grass
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Set the stroke width
                g2d.setStroke(new BasicStroke(1.5f));
                
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
                    
                    // Fill the lane
                    g2d.setColor(new Color(173, 216, 230)); // Light blue color for lanes
                    g2d.fillOval(x, y, width, height);
                    
                    // Draw the lane border
                    g2d.setColor(new Color(34, 139, 34)); // Green color for the track
                    g2d.drawOval(x, y, width, height);
                }

                // Draw the start/finish line
                g2d.setColor(Color.WHITE);
                g2d.setStroke(new BasicStroke(5));
                
                // Draw finish line at top of track
                int finishX = margin + (trackWidth / 2);
                g2d.drawLine(finishX, margin, finishX, margin + laneSpacing * lanes);
            }
        };
        
        trackPanel.setPreferredSize(new Dimension(width, height));
        trackPanel.setLayout(null); // Use absolute positioning for horseGraphics and labels
        trackFrame.setResizable(false);
        
        
        // Create control panel
        JPanel controlPanel = new JPanel();
        JButton startButton = new JButton("Start Race");
        JButton resetButton = new JButton("Reset Race");
        controlPanel.add(startButton);
        controlPanel.add(resetButton);

        // create basic panel - this is for the statistics and other information
        // make a loop to show the horseGraphic names and stats in the basic panel
        JPanel basicPanel = new JPanel(new FlowLayout());
        JLabel basicLabel = new JLabel("Horse Statistics");
        basicLabel.setFont(new Font("Arial", Font.BOLD, 16));
        basicLabel.setForeground(Color.BLACK);
        basicPanel.add(basicLabel);
        basicPanel.setBackground(new Color(173, 216, 230)); // Light blue color for basic panel
        basicPanel.setBorder(BorderFactory.createLineBorder(new Color(34, 139, 34), 5)); // Add border for visibility
        for (Horse horse : horses.values()) {
            JPanel horsePanel = new JPanel(new GridLayout(0, 1));
            horsePanel.setBackground(horse.getColourFromString(horse.getCoatColor()));
            JLabel horseLabel = new JLabel(horse.getName() + " - " + horse.getSymbol());
            JLabel horseStats = new JLabel("Average Speed: " + horse.getSpeed() + " | Stamina: " + horse.getStamina() + " | Confidence: " + horse.getConfidence());
            horseLabel.setForeground(Color.WHITE);
            horseStats.setForeground(Color.WHITE);
            horseLabel.setFont(new Font("Arial", Font.BOLD, 14));
            horseLabel.setPreferredSize(new Dimension(200, 20)); // Set fixed size for labels
            horsePanel.add(horseLabel);
            horsePanel.add(horseStats);
            horsePanel.setPreferredSize(new Dimension(300, 50)); // Set fixed size for horse panel
            basicPanel.add(horsePanel);
        }
        

        basicPanel.setPreferredSize(new Dimension(width / 2, 50)); // Set fixed size for basic panel

        // Add panels to frame
        trackFrame.add(trackPanel, BorderLayout.WEST);
        trackFrame.add(controlPanel, BorderLayout.SOUTH);
        trackFrame.add(basicPanel, BorderLayout.EAST);
        
        // Create race manager - after all track elements are added
        RaceManager raceManager = new RaceManager(trackPanel, width, lanes, HorseGraphic.TrackType.OVAL, horses);
        
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

    // Method to create oval track with racing functionality
    public static void createHalfOvalTrack(int lanes, Map<Integer, Horse> horses, String weather) {
        Map<Integer, Horse> horseMap = new HashMap<>(horses);
        // Create width and height
        int width = 750;
        int height = 450;
        
        // Create a frame for the track
        JFrame trackFrame = new JFrame("Half Oval Race Track - " + weather);
        trackFrame.setSize(800, 600);
        trackFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        trackFrame.setLocationRelativeTo(null); // Center the frame on the screen
        
        // Use BorderLayout for overall organization
        trackFrame.setLayout(new BorderLayout());

        // Create a panel to represent the oval track
        JPanel trackPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                
                // Enable antialiasing for smoother curves
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Set the background 
                g2d.setColor(new Color(34, 139, 34)); // Green background for grass
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Set the stroke width
                g2d.setStroke(new BasicStroke(1.5f));
                
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
                    
                    // Fill the lane
                    g2d.setColor(new Color(173, 216, 230)); // Light blue color for lanes
                    g2d.fillOval(x, y, width, height);
                    
                    // Draw the lane border
                    g2d.setColor(new Color(34, 139, 34)); // Green color for the track
                    g2d.drawOval(x, y, width, height);
                }

                // Draw the start/finish line
                g2d.setColor(Color.WHITE);
                g2d.setStroke(new BasicStroke(5));
                
                // Draw finish line at top of track
                int finishX = margin + (trackWidth / 2);
                g2d.drawLine(finishX, margin, finishX, margin + laneSpacing * lanes);

                // Draw finish line at bottom of track
                g2d.setColor(Color.BLACK);
                int finishY = margin + trackHeight - laneSpacing * lanes;
                g2d.drawLine(finishX, finishY, finishX, finishY + laneSpacing * lanes);
            }
        };
        
        trackPanel.setPreferredSize(new Dimension(width, height));
        trackPanel.setLayout(null); // Use absolute positioning for horseGraphics and labels
        trackFrame.setResizable(false);
        
        
        // Create control panel
        JPanel controlPanel = new JPanel();
        JButton startButton = new JButton("Start Race");
        JButton resetButton = new JButton("Reset Race");
        controlPanel.add(startButton);
        controlPanel.add(resetButton);

        // create basic panel - this is for the statistics and other information
        // make a loop to show the horseGraphic names and stats in the basic panel
        JPanel basicPanel = new JPanel(new FlowLayout());
        JLabel basicLabel = new JLabel("Horse Statistics");
        basicLabel.setFont(new Font("Arial", Font.BOLD, 16));
        basicLabel.setForeground(Color.BLACK);
        basicPanel.add(basicLabel);
        basicPanel.setBackground(new Color(173, 216, 230)); // Light blue color for basic panel
        basicPanel.setBorder(BorderFactory.createLineBorder(new Color(34, 139, 34), 5)); // Add border for visibility
        for (Horse horse : horses.values()) {
            JPanel horsePanel = new JPanel(new GridLayout(0, 1));
            horsePanel.setBackground(horse.getColourFromString(horse.getCoatColor()));
            JLabel horseLabel = new JLabel(horse.getName() + " - " + horse.getSymbol());
            JLabel horseStats = new JLabel("Average Speed: " + horse.getSpeed() + " | Stamina: " + horse.getStamina() + " | Confidence: " + horse.getConfidence());
            horseLabel.setForeground(Color.WHITE);
            horseStats.setForeground(Color.WHITE);
            horseLabel.setFont(new Font("Arial", Font.BOLD, 14));
            horseLabel.setPreferredSize(new Dimension(200, 20)); // Set fixed size for labels
            horsePanel.add(horseLabel);
            horsePanel.add(horseStats);
            horsePanel.setPreferredSize(new Dimension(300, 50)); // Set fixed size for horse panel
            basicPanel.add(horsePanel);
        }

        basicPanel.setPreferredSize(new Dimension(width / 2, 50)); // Set fixed size for basic panel

        // Add panels to frame
        trackFrame.add(trackPanel, BorderLayout.WEST);
        trackFrame.add(controlPanel, BorderLayout.SOUTH);
        trackFrame.add(basicPanel, BorderLayout.EAST);
        
        // Create race manager - after all track elements are added
        RaceManager raceManager = new RaceManager(trackPanel, width, lanes, HorseGraphic.TrackType.HALFOVAL, horses);
        
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
    // Method to create zigzag track with racing functionality

    // Main method to run the application
    public static void main(String[] args) {
        // Run GUI construction on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Map<Integer, Horse> horseMap = new HashMap<>();
                horseMap.put(1, new Horse());
                horseMap.put(2, new Horse("lightning", "Black", "L"));
                horseMap.put(3, new Horse("thunder", "Pinto", "T"));
                horseMap.put(4, new Horse("storm", "Palomino", "S"));
                // horseMap.put(5, new Horse("blaze", "Chestnut", "B"));
                
                // Uncomment the track type you want to test
                createRectangularTrack(600, 4, horseMap, "Sunny");
                // createSimpleOvalTrack(1, horseMap, "Sunny");
                // createHalfOvalTrack(3, horseMap, "Rainy");
            }
        });
    }
}
