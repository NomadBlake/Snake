package snakegame;

import javax.swing.JPanel;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.Timer;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Panel extends JPanel implements KeyListener, ActionListener {
    
    // Import the images
    ImageIcon titleImage;
    ImageIcon bodyImage;
    ImageIcon leftImage; 
    ImageIcon rightImage;
    ImageIcon upImage;
    ImageIcon downImage; 
    ImageIcon food1Image;

    // ImageIcon titleImage = new ImageIcon("image/title.jpg");
    // ImageIcon bodyImage = new ImageIcon("image/body.png");
    // ImageIcon leftImage = new ImageIcon("image/left.png");
    // ImageIcon rightImage = new ImageIcon("image/right.png");
    // ImageIcon upImage = new ImageIcon("image/up.png");
    // ImageIcon downImage = new ImageIcon("image/down.png");
    // ImageIcon food1Image = new ImageIcon("image/food1.png");

    private Snake snake;
    private Food food;
    private Timer timer;

    // Controller of the snake
    private boolean isPlaying;
    public boolean isDead;

    // Time variables
    private int elapsedTime; // Elapsed time in seconds
    private Timer gameTimer; // Timer to update the elapsed time

    // Best score
    private int bestScore;

    Clip bgm;

    // Constructor
    public Panel() {
        loadImages();
        this.setFocusable(true);
        this.addKeyListener(this);
        initGame();
        loadBGM();
    }
    
    public void paintComponent(Graphics graph) {
        super.paintComponent(graph);
        this.setBackground(Color.WHITE);
        drawTitle(graph);
        drawBoard(graph);
        snake.drawSnake(graph);
        food.drawFood(graph);
        printStartPrompt(graph);
        printEndPrompt(graph);
        drawStats(graph);

   }
    

    public void initGame() {
        snake = new Snake(this);
        food = new Food(this, snake);
        timer = new Timer(200, this);
        isPlaying = false;
        isDead = false;
                
        elapsedTime = 0; // Reset the time
        gameTimer = new Timer(1000, e -> { // Timer to update every second
            if (isPlaying && !isDead) {
                elapsedTime++;
            }
        });
        timer.start();
    }

    // Print the title of the game
    private void drawTitle(Graphics graph) {
        titleImage.paintIcon(this, graph, 25, 11);
    }

    // Draw the board of the game
    private void drawBoard(Graphics graph) {
        graph.fillRect(25, 75, 850, 600);
    }


    private void printStartPrompt(Graphics graph) {
        if (!isPlaying) {
            String message = "Press 'Space' to start the game";
            graph.setColor(Color.WHITE);
            graph.setFont(new Font("Helvetica", Font.BOLD, 30));

            // Print the game start message in the middle of the screen
            FontMetrics metrics = graph.getFontMetrics(graph.getFont());
            int x = (900 - metrics.stringWidth(message)) / 2;
            graph.drawString(message, x, 300);
        }
    }

    private void printEndPrompt(Graphics graph) {
        if (isDead) {
            String message = "Game Over! Press 'Space' to restart";
            graph.setColor(Color.RED);
            graph.setFont(new Font("Helvetica", Font.BOLD, 30));

            // Print the game over message in the middle of the screen
            FontMetrics metrics = graph.getFontMetrics(graph.getFont());
            int x = (900 - metrics.stringWidth(message)) / 2;

            graph.drawString(message, x, 300);

            // Update the best score
            String bestScoreMessage = "Best Score: " + bestScore;
            graph.setColor(Color.WHITE);
            graph.setFont(new Font("Helvetica", Font.BOLD, 20));

            x = (900 - metrics.stringWidth(bestScoreMessage)) / 2;

            graph.drawString(bestScoreMessage, x, 350);
        }
    }

    private void drawStats(Graphics graph) {
        graph.setColor(Color.WHITE);
        graph.setFont(new Font("Helvetica", Font.BOLD, 12));
        graph.drawString("Length: " + snake.getLength(), 780, 25);
        graph.drawString("Score: " + snake.getScore(), 780, 40);
        
        // Convert the elapsed time to minutes and seconds
        int minutes = elapsedTime / 60;
        int seconds = elapsedTime % 60;
        String convertedElaspedTime = String.format("%02d:%02d", minutes, seconds);       
        graph.drawString("Time: " + convertedElaspedTime, 780, 55);
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        handleKeyPressed(e);
    }
    

    private void handleKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (isDead) {    
                isDead = false;
                initGame();
            } else {
                isPlaying = !isPlaying;
                
                // Start or stop the game timer
                if (isPlaying) {
                    gameTimer.start();
                    playBGM();
                } else {
                    gameTimer.stop();
                    stopBGM();
                }
            }  
        } else {
            snake.updateDirection(e);
        }

        repaint(); // Refresh the panel
    }


    
    @Override
    public void keyReleased(KeyEvent e) {
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (isPlaying) {
            snake.move(this); 

            if (isDead) {
                // Check if the current score is the best score
                scoreCheck();
                
                stopBGM();
                gameTimer.stop(); 
                timer.stop();
            } else {
                // Check if the snake eats the food
                if (snake.eatFood(food)) {
                    food.initFood();
                }
            }

            repaint();
        }
    }

    public void reduceTimeDelay(double factor) {
        int currentDelay = timer.getDelay();
        int newDelay = (int) (currentDelay * factor);
        timer.setDelay(newDelay);
    }

    
    private void scoreCheck() {
        if (snake.getScore() > bestScore) {
            bestScore = snake.getScore();
        }
    }

    private void loadImages() {
        InputStream is;
        try {
            // ImageIcon titleImage = new ImageIcon("image/title.jpg");
            // ImageIcon bodyImage = new ImageIcon("image/body.png");
            // ImageIcon leftImage = new ImageIcon("image/left.png");
            // ImageIcon rightImage = new ImageIcon("image/right.png");
            // ImageIcon upImage = new ImageIcon("image/up.png");
            // ImageIcon downImage = new ImageIcon("image/down.png");
            // ImageIcon food1Image = new ImageIcon("image/food1.png");
            is = getClass().getClassLoader().getResourceAsStream("image/title.jpg");
            titleImage = new ImageIcon(ImageIO.read(is));

            is = getClass().getClassLoader().getResourceAsStream("image/body.png");
            bodyImage = new ImageIcon(ImageIO.read(is));

            is = getClass().getClassLoader().getResourceAsStream("image/left.png");
            leftImage = new ImageIcon(ImageIO.read(is));

            is = getClass().getClassLoader().getResourceAsStream("image/right.png");
            rightImage = new ImageIcon(ImageIO.read(is));

            is = getClass().getClassLoader().getResourceAsStream("image/up.png");
            upImage = new ImageIcon(ImageIO.read(is));

            is = getClass().getClassLoader().getResourceAsStream("image/down.png");
            downImage = new ImageIcon(ImageIO.read(is));

            is = getClass().getClassLoader().getResourceAsStream("image/food1.png");
            food1Image = new ImageIcon(ImageIO.read(is));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    private void loadBGM() {
        // Play the background music
        try {
            bgm = AudioSystem.getClip();
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResourceAsStream("sound/bgm.wav"));
            bgm.open(audioInput);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void playBGM() {
        bgm.loop(Clip.LOOP_CONTINUOUSLY);
    }

    private void stopBGM() {
        bgm.stop();
    }


}






