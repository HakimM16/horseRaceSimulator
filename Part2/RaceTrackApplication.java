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

    public RaceTrackApplication(int lanes, int length, String trackShape, String weather, Map<Integer, Horse> horses) {
        // Constructor to initialize the application
        this.lanes = lanes;
        this.length = length;
        this.trackShape = trackShape;
        this.weather = weather;
        this.horses = horses;

        switch (this.trackShape) {
            case "oval":
                createSimpleOvalTrack(this.lanes, this.horses);
                break;
            case "rectangular":
                createRectangularTrack(this.length, this.lanes, this.horses);
                break;
            case "zigzag":
                createZigZagTrack(this.length, this.lanes, this.horses);
                break;
            default:
                throw new AssertionError();
        }
    }
    // horseGraphic class to represent each racing horseGraphic
    static class horseGraphic {
        private double x; // X position on track
        private double y; // Y position within lane
        private int laneNumber; // Which lane the horseGraphic is in
        private String symbol; // horseGraphic symbol (e.g., "H")
        private double speed; // How fast the horseGraphic moves each update
        private Color color; // horseGraphic color
        private String name; // horseGraphic name/identifier
        private JPanel horseGraphicPanel; // Visual representation
        private double angle = 0; // For oval track rotation
        private Path2D.Double path; // For zigzag track
        private double pathPosition = 0; // Position along path (0.0 to 1.0)
        private TrackType trackType;
        private boolean hasFallen; // Flag to indicate if horseGraphic has fallen
        private double confidence; // horseGraphic confidence level
        private double stamina; // horseGraphic stamina level
        private double speedFactor; // horseGraphic speed factor
        private String fallenSymbol = "X"; // Symbol to show when horseGraphic has fallen
        
        // Track type enum
        public enum TrackType {
            RECTANGULAR, OVAL, ZIGZAG
        }
        
        public horseGraphic(String name, int laneNumber, String symbol, int laneHeight, Color color, TrackType trackType, double confidence, double stamina, int speedFactor) {
            this.name = name;
            this.laneNumber = laneNumber;
            this.trackType = trackType;
            this.speed = 1 + (Math.random() * 3); // Random speed between 1-3
            this.color = color;
            this.symbol = symbol;
            this.hasFallen = false;
            this.confidence = confidence;
            this.stamina = stamina;
            this.speedFactor = speedFactor;
            
            // Initial position based on track type
            switch(trackType) {
                case RECTANGULAR:
                    this.x = 10;
                    this.y = laneNumber * laneHeight + laneHeight/4;
                    break;
                case OVAL:
                    this.angle = Math.PI / 2; // Start at bottom of oval
                    break;
                case ZIGZAG:
                    this.pathPosition = 0.0;
                    break;
            }
            
            // Create visual representation with border for better visibility
            horseGraphicPanel = new JPanel();
            horseGraphicPanel.setBackground(color);
            horseGraphicPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            
            // For rectangular track initial positioning
            if (trackType == TrackType.RECTANGULAR) {
                horseGraphicPanel.setBounds((int)x, (int)y, 30, laneHeight/2);
            } else {
                horseGraphicPanel.setBounds(0, 0, 30, laneHeight/2); // Will be positioned later
            }
            
            // Add horseGraphic symbol label to make it more visible
            JLabel horseGraphicLabel = new JLabel(symbol);
            horseGraphicLabel.setForeground(Color.WHITE);
            horseGraphicLabel.setHorizontalAlignment(SwingConstants.CENTER);
            horseGraphicPanel.setLayout(new BorderLayout());
            horseGraphicPanel.add(horseGraphicLabel, BorderLayout.CENTER);
        }
        
        public void setPath(Path2D.Double path) {
            this.path = path;
        }
        
        public void move() {
            switch(trackType) {
                case RECTANGULAR:
                    moveRectangular();
                    break;
                case OVAL:
                    moveOval();
                    break;
                case ZIGZAG:
                    moveZigZag();
                    break;
            }
        }
        
        private void moveRectangular() {
            // if horseGraphic has fallen, stop moving
            if (!this.hasFallen) {
                // Random speed variation to make race interesting
                double randomFactor = (Math.random() * 3) - 1; // -1 to 1
                x += speed + randomFactor * this.speedFactor; // Adjust speed with random factor
                horseGraphicPanel.setBounds((int)x, (int)y, 30, horseGraphicPanel.getHeight());
                if (Math.random() < this.confidence) {
                    // Random chance to fall
                    if (Math.random() < 0.05) { // 5% chance to fall
                        this.hasFallen = true;
                        horseGraphicPanel.setBackground(Color.RED); // Change color to indicate fall
                        horseGraphicPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Add border for visibility
                        setFallenSymbol();// Change symbol to "X"
                    } else {
                        this.hasFallen = false; // Reset fall status
                    }
                } 
            }
        }
        
        private void moveOval() {
            // if horseGraphic has fallen, stop moving
            if (!this.hasFallen) {
                // Random speed variation
                double randomFactor = (Math.random() * 0.02) - 0.01; // Small random factor
                angle += (speed * 0.01) + randomFactor; // Adjust speed for oval movement
                
                // Keep angle in range 0-2π
                if (angle >= 2 * Math.PI) {
                    angle -= 2 * Math.PI;
                }
                
                // Calculate position on oval
                int centerX = 375; // Center X of oval
                int centerY = 225; // Center Y of oval
                int radiusX = 275 - (laneNumber * 20); // X radius, decreasing for inner lanes
                int radiusY = 150 - (laneNumber * 20); // Y radius, decreasing for inner lanes
                
                x = centerX + radiusX * Math.cos(angle);
                y = centerY + radiusY * Math.sin(angle);
                
                horseGraphicPanel.setBounds((int)x - 15, (int)y - 10, 30, 20);
                if (Math.random() < this.confidence) {
                    // Random chance to fall
                    if (Math.random() < 0.05) { // 5% chance to fall
                        this.hasFallen = true;
                        horseGraphicPanel.setBackground(Color.RED); // Change color to indicate fall
                        horseGraphicPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Add border for visibility
                        setFallenSymbol();// Change symbol to "X"
                    } else {
                        this.hasFallen = false; // Reset fall status
                    }
                } 
            }
        }
        
        private void moveZigZag() {
            if (!this.hasFallen) {
                if (path == null) return;
                
                // Random speed variation
                double randomFactor = (Math.random() * 0.003) - 0.001;
                pathPosition += (speed * 0.003) + randomFactor;
                
                // Reset position if completed path
                if (pathPosition > 1.0) {
                    pathPosition = 0.0;
                }
                
                // Calculate position along path
                PathIterator pi = path.getPathIterator(null, pathPosition);
                float[] coords = new float[6];
                if (!pi.isDone()) {
                    pi.currentSegment(coords);
                    x = coords[0];
                    y = coords[1];
                    horseGraphicPanel.setBounds((int)x - 15, (int)y - 10, 30, 20);
                }
                if (Math.random() < this.confidence) {
                    // Random chance to fall
                    if (Math.random() < 0.05) { // 5% chance to fall
                        this.hasFallen = true;
                        horseGraphicPanel.setBackground(Color.RED); // Change color to indicate fall
                        horseGraphicPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Add border for visibility
                        setFallenSymbol();// Change symbol to "X"
                    } else {
                        this.hasFallen = false; // Reset fall status
                    }
                }
            }
        }
        
        public JPanel gethorseGraphicPanel() {
            return horseGraphicPanel;
        }
        
        public double getX() {
            return x;
        }
        
        public double getY() {
            return y;
        }
        
        public double getAngle() {
            return angle;
        }
        
        public double getPathPosition() {
            return pathPosition;
        }
        
        public String getName() {
            return this.name;
        }

        public void resetFallen() {
            this.hasFallen = false;
            horseGraphicPanel.setBackground(color); // Reset color to original
            horseGraphicPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Reset border
            setSymbol(symbol); // Reset symbol to original
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
            JLabel horseGraphicLabel = new JLabel(symbol);
            horseGraphicLabel.setForeground(Color.WHITE);
            horseGraphicLabel.setHorizontalAlignment(SwingConstants.CENTER);
            horseGraphicPanel.removeAll(); // Remove old label
            horseGraphicPanel.add(horseGraphicLabel, BorderLayout.CENTER); // Add new label
            horseGraphicPanel.revalidate(); // Refresh panel to show new label
        }

        public void setFallenSymbol() {
            JLabel horseGraphicLabel = new JLabel(fallenSymbol);
            horseGraphicLabel.setForeground(Color.WHITE);
            horseGraphicLabel.setHorizontalAlignment(SwingConstants.CENTER);
            horseGraphicPanel.removeAll(); // Remove old label
            horseGraphicPanel.add(horseGraphicLabel, BorderLayout.CENTER); // Add new label
            horseGraphicPanel.revalidate(); // Refresh panel to show new label
        }
        
        // Method to reset horseGraphic position
        public void reset() {
            switch(trackType) {
                case RECTANGULAR:
                    x = 10;
                    horseGraphicPanel.setBounds((int)x, (int)y, 30, horseGraphicPanel.getHeight());
                    resetFallen(); // Reset fallen status
                    break;
                case OVAL:
                    angle = Math.PI / 2; // Start at bottom of oval
                    moveOval(); // Update position
                    resetFallen();
                    break;
                case ZIGZAG:
                    pathPosition = 0.0;
                    moveZigZag(); // Update position
                    resetFallen();
                    break;
            }
        }
    }

    // RaceManager class to handle race logic
    static class RaceManager {
        private ArrayList<horseGraphic> horsesGUI = new ArrayList<>();
        private JPanel trackPanel;
        private Timer raceTimer;
        private int trackLength;
        private JLabel resultLabel;
        private boolean raceInProgress = false;
        private horseGraphic.TrackType trackType;
        public Map<Integer, Horse> horses;
        
        public RaceManager(JPanel trackPanel, int trackLength, int lanes, horseGraphic.TrackType trackType, Map<Integer, Horse> horses) {
            this.horses = horses;
            this.trackPanel = trackPanel;
            this.trackLength = trackLength;
            this.trackType = trackType;
            
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
                case ZIGZAG:
                default:
                    laneHeight = 20; // Fixed height for oval and zigzag
                    break;
            }
            
            // Create horseGraphics with different colors
            int i = 0;
            for (Horse horse : horses.values()) {
                horseGraphic horse1 = new horseGraphic(horse.getName(), i, horse.getSymbol(), laneHeight, horse.getColourFromString(horse.getCoatColor()), trackType, horse.getConfidence(), horse.getStamina(), horse.getSpeed());
                System.out.println("Horse " + horse1.getName() + " created with color: " + horse1.color);
                horsesGUI.add(horse1);
                i++;
                // For zigzag track, set path for each horseGraphic
                if (trackType == horseGraphic.TrackType.ZIGZAG) {
                    setupZigZagPath(horse1, i, lanes);
                }
                
                // Add horseGraphic panel to track panel
                trackPanel.add(horse1.gethorseGraphicPanel());
            }
            
            // Important: make sure horseGraphics are visible by setting their z-order to top
            for (horseGraphic horse : horsesGUI) {
                trackPanel.setComponentZOrder(horse.gethorseGraphicPanel(), 0);
            }
            trackPanel.setComponentZOrder(resultLabel, 0);
            
            // Refresh panel to make horseGraphics visible
            trackPanel.revalidate();
            trackPanel.repaint();
        }
        
        private void setupZigZagPath(horseGraphic horse, int laneNumber, int totalLanes) {
            // Define the zig-zag path for this lane
            int segments = 5;
            int margin = 30;
            int trackWidth = trackPanel.getWidth() - (2 * margin);
            int trackHeight = trackPanel.getHeight() - (2 * margin);
            int segmentWidth = trackWidth / segments;
            
            int totalLaneWidth = trackHeight / 2;
            int singleLaneWidth = totalLaneWidth / totalLanes;
            int laneOffset = laneNumber * singleLaneWidth;
            
            Path2D.Double path = new Path2D.Double();
            path.moveTo(margin, margin + laneOffset + singleLaneWidth/2);
            
            for (int i = 1; i <= segments; i++) {
                int x = margin + (i * segmentWidth);
                int y = margin + (i % 2 == 0 ? 0 : trackHeight - totalLaneWidth) + laneOffset + singleLaneWidth/2;
                path.lineTo(x, y);
            }
            
            horse.setPath(path);
        }
        
        public void startRace() {
            if (raceInProgress) return;
            
            raceInProgress = true;
            resultLabel.setText("Race in progress...");
            
            // Create timer to update horseGraphic positions
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
            int count = 0;
            boolean someoneFinished = false;
            String winner = "";
            
            for (horseGraphic horse : horsesGUI) {
                horse.move();
                
                // Check if any horseGraphic has finished, based on track type
                boolean hasFinished = false;
                
                switch(trackType) {
                    case RECTANGULAR:
                        hasFinished = horse.getX() >= trackLength - 40;
                        break;
                    case OVAL:
                        // Finish when crossing finish line at top (angle near 3π/2)
                        double angleDiff = Math.abs(horse.getAngle() - (3 * Math.PI / 2));
                        hasFinished = angleDiff < 0.1 && horse.getAngle() > Math.PI;
                        break;
                    case ZIGZAG:
                        // Finish when reaching end of path
                        hasFinished = horse.getPathPosition() >= 0.98 && horse.getPathPosition() <= 1.0;
                        break;
                }
                
                if (hasFinished && !someoneFinished) {
                    someoneFinished = true;
                    winner = horse.getName();
                    System.out.println(horse.getName() + " has finished");
                }

                // Check if horseGraphic has fallen
                if (horse.hasFallen) {
                    count++;
                }
            }
            
            // Manual repaint after all horseGraphics have moved
            trackPanel.repaint();
            
            if (someoneFinished) {
                resultLabel.setText(winner + " wins the race!");
                return true;
            }
            // set label to say that all horses fell
            if (count == horsesGUI.size()) {
                resultLabel.setText("All horses have fallen!");
                return true;
            }
            return false;
        }
        
        public void resetRace() {
            if (raceTimer != null && raceTimer.isRunning()) {
                raceTimer.stop();
            }
            
            for (horseGraphic horseGraphic : horsesGUI) {
                horseGraphic.reset();

            }
            
            resultLabel.setText("Click 'Start Race' to begin");
            raceInProgress = false;
            trackPanel.repaint();
        }
    }

    // Method to create rectangular track
    public static void createRectangularTrack(int length, int lanes, Map<Integer, Horse> horses) {
        Map<Integer, Horse> horseMap = new HashMap<>(horses);
        // Create a frame for the track
        JFrame trackFrame = new JFrame("Rectangular Race Track");
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
        trackFrame.add(basicPanel, BorderLayout.EAST); // Add basic panel to the left
        trackFrame.add(controlPanel, BorderLayout.SOUTH);
        trackFrame.setResizable(false); // Disable resizing

        
        // Create race manager - after all track elements are added
        RaceManager raceManager = new RaceManager(trackPanel, length, lanes, horseGraphic.TrackType.RECTANGULAR, horseMap);
        
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
    public static void createSimpleOvalTrack(int lanes, Map<Integer, Horse> horses) {
        Map<Integer, Horse> horseMap = new HashMap<>(horses);
        // Create width and height
        int width = 750;
        int height = 450;
        
        // Create a frame for the track
        JFrame trackFrame = new JFrame("Oval Race Track");
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
        JPanel basicPanel = new JPanel();
        basicPanel.setLayout(new BorderLayout());
        basicPanel.setPreferredSize(new Dimension(width / 2, 50)); // Set fixed size for basic panel

        // Add panels to frame
        trackFrame.add(trackPanel, BorderLayout.WEST);
        trackFrame.add(controlPanel, BorderLayout.SOUTH);
        trackFrame.add(basicPanel, BorderLayout.EAST);
        
        // Create race manager - after all track elements are added
        RaceManager raceManager = new RaceManager(trackPanel, width, lanes, horseGraphic.TrackType.OVAL, horses);
        
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
    public static void createZigZagTrack(int length, int lanes, Map<Integer, Horse> horses) {
        Map<Integer, Horse> horseMap = new HashMap<>(horses);
        // Create a frame for the track
        JFrame trackFrame = new JFrame("Zig-Zag Race Track");
        trackFrame.setSize(800, 600);
        trackFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        trackFrame.setLocationRelativeTo(null); // Center the frame on the screen
        
        // Use BorderLayout for overall organization
        trackFrame.setLayout(new BorderLayout());

        // Create a panel to represent the zig-zag track
        JPanel trackPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                
                // Enable antialiasing for smoother lines
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw the green background for the entire area
                g2d.setColor(new Color(34, 139, 34)); // Green background for grass
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
                    g2d.setColor(new Color(34, 139, 34)); // Green color for borders
                    g2d.setStroke(new BasicStroke(2));
                    g2d.draw(lanePolygon);
                }
                
                // Draw finish line
                g2d.setColor(Color.WHITE);
                g2d.setStroke(new BasicStroke(5));
                g2d.drawLine(margin + trackWidth, margin + 170, margin + trackWidth, margin + totalLaneWidth + 170);
            }
        };
        
        trackPanel.setPreferredSize(new Dimension(length, 400));
        trackPanel.setMinimumSize(new Dimension(length, 400));
        trackPanel.setMaximumSize(new Dimension(length, 400));
        trackPanel.setLayout(null); // Use absolute positioning for horseGraphics and labels
        trackFrame.setResizable(false);
        
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
        trackFrame.add(basicPanel, BorderLayout.EAST);
       
        
        // Create race manager - after all track elements are added
        RaceManager raceManager = new RaceManager(trackPanel, length, lanes, horseGraphic.TrackType.ZIGZAG, horses);
        
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
                Map<Integer, Horse> horseMap = new HashMap<>();
                horseMap.put(1, new Horse());
                horseMap.put(2, new Horse("lightning", "Black", "L"));
                horseMap.put(3, new Horse("thunder", "Pinto", "T"));
                horseMap.put(4, new Horse("storm", "Palomino", "S"));
                // horseMap.put(5, new Horse("blaze", "Chestnut", "B"));
                
                
                // Uncomment the track type you want to test
                //createRectangularTrack(600, 4, horseMap);
                //createSimpleOvalTrack(4, horseMap);
                
            }
        });
    }
}
