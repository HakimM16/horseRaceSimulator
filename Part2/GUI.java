import javax.swing.*;

public class GUI {
    public static void main(String[] args) {
        // Create a new JFrame
        // planning:
        // - create a custom class that extends JFrame for the GUI
        JFrame frame = new JFrame("GUI development"); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exits the program when the window is closed
        frame.setSize(400, 400); // set the size of the window
        frame.setVisible(true); // make the window visible
    }
}