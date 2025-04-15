import java.awt.*;
import javax.swing.*;

public class RaceTrackApplication {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Race Track");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        
        RaceTrackPanel trackPanel = new RaceTrackPanel();
        frame.add(trackPanel);
        
        frame.setVisible(true);
    }
}

class RaceTrackPanel extends JPanel {
    private int carPosition = 0; // Angle in degrees
    private Timer timer;
    public int innerTrackWidth;
    public int innerTrackHeight;
    public int outerTrackWidth;
    public int outerTrackHeight;
    
    public RaceTrackPanel() {
        setBackground(Color.GREEN);
        
        // Add a timer to animate the car
        timer = new Timer(50, e -> {
            carPosition = (carPosition + 2) % 360; // Move car
            repaint();
        });
        timer.start();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Draw race track here
        drawRaceTrack(g2d);
        // Call drawCar method to draw the car
        drawCar(g2d);
    }
    
    private void drawRaceTrack(Graphics2D g2d) {
        int width = getWidth();
        int height = getHeight();
        
        // Set up rendering quality
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw outer track boundary
        g2d.setColor(Color.GRAY);
        outerTrackWidth = width - 100;
        outerTrackHeight = height - 100;
        g2d.fillOval(50, 50, outerTrackWidth, outerTrackHeight);
        
        // Draw inner track boundary (creates the actual track)
        g2d.setColor(Color.GREEN);
        innerTrackWidth = outerTrackWidth - 100;
        innerTrackHeight = outerTrackHeight - 100;
        g2d.fillOval(100, 100, innerTrackWidth, innerTrackHeight);
        
        // Draw starting/finish line
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(5));
        g2d.drawLine(width / 2, 50, width / 2, 100);

        // Draw lane markings
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{10}, 0));
        
        // Draw dashed lines to separate lanes
        int middleX = width / 2;
        int middleY = height / 2;
        
        // Draw some lane dividers
        for (int i = 0; i < 8; i++) {
            double angle = Math.PI * i / 4;
            int x1 = (int)(middleX + (outerTrackWidth/2) * Math.cos(angle));
            int y1 = (int)(middleY + (outerTrackHeight/2) * Math.sin(angle));
            int x2 = (int)(middleX + (innerTrackWidth/2) * Math.cos(angle));
            int y2 = (int)(middleY + (innerTrackHeight/2) * Math.sin(angle));
            
            g2d.drawLine(x1, y1, x2, y2);
        }
    }

    private void drawCar(Graphics2D g2d) {
        int width = getWidth();
        int height = getHeight();
        int middleX = width / 2;
        int middleY = height / 2;
        
        // Calculate car position along track - corrected to keep car on track
        double angle = Math.toRadians(carPosition);
        
        // Calculate the middle of the track (between outer and inner boundaries)
        int trackRadiusX = (outerTrackWidth - innerTrackWidth) / 4 + innerTrackWidth / 2;
        int trackRadiusY = (outerTrackHeight - innerTrackHeight) / 4 + innerTrackHeight / 2;
        
        // Position car in the middle of the track
        int carX = (int)(middleX + trackRadiusX * Math.cos(angle)) - 15;
        int carY = (int)(middleY + trackRadiusY * Math.sin(angle)) - 10;
        
        // Draw car
        g2d.setColor(Color.RED);
        g2d.fillRect(carX, carY, 30, 20);
        
        // Add car direction indicator (front of car)
        g2d.setColor(Color.BLUE);
        double headingAngle = angle + Math.PI/2; // Car faces tangent to circle
        int headX = (int)(carX + 15 + 10 * Math.cos(headingAngle));
        int headY = (int)(carY + 10 + 10 * Math.sin(headingAngle));
        g2d.fillOval(headX-5, headY-5, 10, 10);
    }
}