package snakegame;

import javax.swing.JFrame;

public class SnakeGameDriver {
    
    public static void main(String[] args) {
        
        // Creating a new JFrame
        JFrame frame = new JFrame();
        frame.setBounds(50, 50, 900, 720); // Setting the size of the frame
        frame.setResizable(false); // Making the frame non-resizable
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Setting the default close operation
        
        // Creating a new Panel and adding it to the frame
        Panel panel = new Panel();
        frame.add(panel);

        frame.setVisible(true); // Visibility of the frame
    }
}

