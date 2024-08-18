package snakegame;

import java.awt.*;
import java.awt.event.*;

public class Snake {
    
  
    private int length = 3;
    private int score = 0;
    private int[] snakeX = new int[800];
    private int[] snakeY = new int[800];
    private String direction = "R";
    private Panel panel;

    // Constructor
    public Snake(Panel panel) {
        this.panel = panel;
        initSnake();
    }

    // Initialize the postion and direction of the snake
    public void initSnake(){
        length = 3;
        snakeX[0] = 100;
        snakeY[0] = 100;
        snakeX[1] = 75;
        snakeY[1] = 100;
        snakeX[2] = 50;
        snakeY[2] = 100;
        direction = "R";
        score = 0;
    }

    public void drawSnake(Graphics graph) {
        drawSnakeHead(graph);
        drawSnakeBody(graph);
    }

    private void drawSnakeHead(Graphics graph) {
        switch (direction) {
            case "R":
                panel.rightImage.paintIcon(panel, graph, snakeX[0], snakeY[0]);
                break;
            case "L":
                panel.leftImage.paintIcon(panel, graph, snakeX[0], snakeY[0]);
                break;
            case "U":
                panel.upImage.paintIcon(panel, graph, snakeX[0], snakeY[0]);
                break;
            case "D":
                panel.downImage.paintIcon(panel, graph, snakeX[0], snakeY[0]);
                break;
        }
    }

    private void drawSnakeBody(Graphics graph){
        for (int i = 1; i < length; i++) {
            panel.bodyImage.paintIcon(panel, graph, snakeX[i], snakeY[i]);
        }
    }

    public void move(Panel panel) {
        if (panel.isDead) return;

        // Store previous head position
        int prevX = snakeX[0];
        int prevY = snakeY[0];

        // Move the snake's head
        moveHead();

        // Check for collisions
        if (hitWall() || eatSelf()) {
            panel.isDead = true;
            // Reset the head position to the previous position
            snakeX[0] = prevX;
            snakeY[0] = prevY;
            return;
        }

        // Move the body only if no collision occurred
        moveBody(prevX, prevY);
    }

    private void moveHead() {
        int newX = snakeX[0];
        int newY = snakeY[0];

        switch (direction) {
            case "R":
                newX += 25;
                break;
            case "L":
                newX -= 25;
                break;
            case "U":
                newY -= 25;
                break;
            case "D":
                newY += 25;
                break;
        }

        // Update head position
        snakeX[0] = newX;
        snakeY[0] = newY;
    }

    private void moveBody(int prevX, int prevY) {
        for (int i = length - 1; i > 0; i--) {
            snakeX[i] = snakeX[i - 1];
            snakeY[i] = snakeY[i - 1];
        }
        snakeX[1] = prevX;  // Set the first body segment to the previous head position
        snakeY[1] = prevY;
    }



    public void updateDirection(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT && direction != "R") {
            direction = "L";
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && direction != "L") {
            direction = "R";
        } else if (e.getKeyCode() == KeyEvent.VK_UP && direction != "D") {
            direction = "U";
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && direction != "U") {
            direction = "D";
        }
    }


    public boolean eatFood(Food food) {
        if (snakeX[0] == food.getFoodX() && snakeY[0] == food.getFoodY()) {
            
            // Make sure the snake's body grows in the right direction
            snakeX[length] = snakeX[length - 1];
            snakeY[length] = snakeY[length - 1];

            length++;
            score += 10;

            panel.reduceTimeDelay(0.96);
            return true;
        }
        return false;
    }

    public boolean eatSelf() {
        for (int i = 1; i < length; i++) {
            if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                return true;
            }
        }
        return false;
    }

    public boolean hitWall() {
        if (snakeX[0] < 25 || snakeX[0] > 850 || snakeY[0] < 75 || snakeY[0] > 650) {
            return true;
        }
        return false;
    }
    

    
    // Getters and Setters
    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getSnakeX(int index) {
        return snakeX[index];
    }

    public int getSnakeY(int index) {
        return snakeY[index];
    }


    
}
