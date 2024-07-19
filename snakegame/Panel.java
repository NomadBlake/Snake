package snakegame;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.Timer;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Random;

public class Panel extends JPanel implements KeyListener, ActionListener {
    
    // Import the images
    ImageIcon title = new ImageIcon("image/title.jpg");
    ImageIcon body = new ImageIcon("image/body.png");
    ImageIcon left = new ImageIcon("image/left.png");
    ImageIcon right = new ImageIcon("image/right.png");
    ImageIcon up = new ImageIcon("image/up.png");
    ImageIcon down = new ImageIcon("image/down.png");
    ImageIcon food = new ImageIcon("image/food.png");

    // Controller of the snake
    boolean isPlaying = false;
    boolean isDead = false;

    // Data structure to store the snake's position
    int len = 3;
    int score = 0;
    int[] snakeX = new int[800];
    int[] snakeY = new int[800];
    
    // Data structure to store the food's position
    int foodX;
    int foodY;
    Random rand = new Random();

    // Initial direction of the snake
    String direction = "R";

    Timer timer = new Timer(200, this);


    // Constructor
    public Panel() {
        initSnake();
        this.setFocusable(true);
        this.addKeyListener(this);
        timer.start();
    }
    
    public void paintComponent(Graphics graph) {
        super.paintComponent(graph);
        this.setBackground(Color.WHITE);
        
        // Drawing the title image
        title.paintIcon(this, graph, 25, 11);

        // Drawing the border of the gameplay board
        graph.fillRect(25, 75, 850, 600);


        // Printing the snake's head
        if (direction == "R") {
            right.paintIcon(this, graph, snakeX[0], snakeY[0]);
        } else if (direction == "L") {
            left.paintIcon(this, graph, snakeX[0], snakeY[0]);
        } else if (direction == "U") {
            up.paintIcon(this, graph, snakeX[0], snakeY[0]);
        } else if (direction == "D") {
            down.paintIcon(this, graph, snakeX[0], snakeY[0]);
        }
        
        // Printing the snake's body
        for (int i = 1; i < len; i++) {
            body.paintIcon(this, graph, snakeX[i], snakeY[i]);
        }

        // Printing the food
        food.paintIcon(this, graph, foodX, foodY);

        // Printing the start prompt
        if (!isPlaying) {
            graph.setColor(Color.WHITE);
            graph.setFont(new Font("Helvetica", Font.BOLD, 20));
            graph.drawString("Press 'Space' to start the game", 290, 300);
        }

        // Printing the game over prompt
        if (isDead) {
            graph.setColor(Color.RED);
            graph.setFont(new Font("Helvetica", Font.BOLD, 20));
            graph.drawString("Game Over! Press 'Space' to restart", 275, 300);
        }

        // Printing the score and the length of the snake
        graph.setColor(Color.WHITE);
        graph.setFont(new Font("Helvetica", Font.BOLD, 12));
        graph.drawString("Length: " + len, 780, 30);
        graph.drawString("Score: " + score, 780, 45);


    }
    

    public void initSnake() {
        len = 3;
        snakeX[0] = 100;
        snakeY[0] = 100;
        snakeX[1] = 75;
        snakeY[1] = 100;
        snakeX[2] = 50;
        snakeY[2] = 100;
        foodX = 25 + 25 * rand.nextInt(34);
        foodY = 75 + 25 * rand.nextInt(24);
        direction = "R";
        score = 0;
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (isDead) {
                isDead = false;
                initSnake();
            } else {
                isPlaying = !isPlaying;
            }
     
            repaint(); // Refresh the panel
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            direction = "L";
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            direction = "R";
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            direction = "U";
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            direction = "D";
        }
    }
    
    
    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isPlaying && !isDead) {
            for (int i = len - 1; i > 0; i--) {
                snakeX[i] = snakeX[i - 1];
                snakeY[i] = snakeY[i - 1];
            }
            
            // Move the snake's head
            // TODO: Snake can't move in the opposite direction
            if (direction == "R") {
                snakeX[0] = snakeX[0] + 25;
                if (snakeX[0] > 850) {
                    snakeX[0] = 25;
                }
            } else if (direction == "L") {
                snakeX[0] = snakeX[0] - 25;
                if (snakeX[0] < 25) {
                    snakeX[0] = 850;
                }
            } else if (direction == "U") {
                snakeY[0] = snakeY[0] - 25;
                if (snakeY[0] < 75) {
                    snakeY[0] = 650;
                }
            } else if (direction == "D") {
                snakeY[0] = snakeY[0] + 25;
                if (snakeY[0] > 650) {
                    snakeY[0] = 75;
                }
            }
            
            // Check if the snake eats the food
            if (snakeX[0] == foodX && snakeY[0] == foodY) {
                len++;
                score = score + 10;
                foodX = 25 + 25 * rand.nextInt(34);
                foodY = 75 + 25 * rand.nextInt(24);
                // TODO: Check if the food is generated on the snake's body
            }

            // Check if the snake hits itself
            for (int i = 1; i < len; i++) {
                if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                    isDead = true;
                }
            }

            // TODO: Check if the snake hits the border

            repaint();
        }

        timer.start();
    }
    
}
