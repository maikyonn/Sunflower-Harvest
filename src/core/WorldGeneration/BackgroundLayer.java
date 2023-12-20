package core.WorldGeneration;

import edu.princeton.cs.algs4.StdDraw;

public class BackgroundLayer {
    String fileName;
    double currentX;
    double currentY;
    double shadowX;
    double shadowY;
    double sizeX;
    double sizeY;
    boolean bounceState;
    int bounceCount;

    public BackgroundLayer(double currentX, double currentY, String fileName, double sizeX, double sizeY) {
        this.currentX = currentX;
        this.currentY = currentY;
        this.shadowX = -52;
        this.shadowY = currentY;
        this.fileName = fileName;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.bounceState = false;
        this.bounceCount = 0;
    }

    public void bufferLayer() {
        StdDraw.picture(currentX, currentY, fileName, sizeX, sizeY);
        StdDraw.picture(shadowX, currentY, fileName, sizeX, sizeY);
    }

    public void shiftX(double amount) {
        currentX = currentX + amount;
        shadowX = shadowX + amount;

        if (currentX >= 100) {
            currentX = shadowX - 82;
        }
        if (shadowX >= 100) {
            shadowX = currentX - 82;
        }
    }

    public void bounce(double amount) {
        if (bounceCount == 4) {
            if (bounceState) {
                currentY = currentY + amount * -1;
                bounceState = false;
            } else {
                currentY = currentY + amount;
                bounceState = true;
            }
            bounceCount = 0;
        }
        bounceCount++;
    }

    public void printInfo() {
        System.out.println("Current X: " + currentX + "Shadow X: " + shadowX);
    }


}
