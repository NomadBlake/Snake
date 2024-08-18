package snakegame;

import java.util.Random;
import java.awt.Graphics;

public class Food {
    

    // ImageIcon food = new ImageIcon("image/food.png");

    // Data structure to store the food's position
    private int foodX;
    private int foodY;
    private Panel panel;
    private Snake snake;
    private Random random = new Random();
    

    // Constructor
    public Food(Panel panel, Snake snake) {     
        this.panel = panel;
        this.snake = snake;
        initFood();
    }

    // Initialize the food's position
    public void initFood() {
        do {
            foodX = random.nextInt(34) * 25 + 25;
            foodY = random.nextInt(24) * 25 + 75;
        } while (positionOverlap());
    }

    // Check if the food is on the snake's body
    public boolean positionOverlap() {
        for (int i = 0; i < snake.getLength(); i++) {
            if (foodX == snake.getSnakeX(i) && foodY == snake.getSnakeY(i)) {
                return true;
            }
        }
        return false;
    }
    

    // Draw the food
    public void drawFood(Graphics graph) {
        panel.food1Image.paintIcon(panel, graph, foodX, foodY);
    }
    
    // Getters and Setters
    public int getFoodX() {
        return foodX;
    }

    public int getFoodY() {
        return foodY;
    }

}
    
