package core;

import core.WorldGeneration.BackgroundLayer;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class BackgroundProcess {

    ArrayList<BackgroundLayer> layers;
    boolean hasShownLoading;

    public BackgroundProcess() {
        layers = new ArrayList<>();
        hasShownLoading = false;
        int tracker = 100;
        for (int i = 1; i < 17; i++) {
            layers.add(new BackgroundLayer((double) Main.MAP_X_LEN / 2, (double) Main.MAP_Y_LEN / 2,
                    "assets/background/" + i + ".png", 82, 40));
        }
    }

    public void showLoadingScreen() {
        if (!hasShownLoading) {
            int tracker = 100;
            while (tracker > 5) {
                StdDraw.picture((double) Main.MAP_X_LEN / 2, (double) Main.MAP_Y_LEN / 2,
                        "assets/background/load" + tracker + ".png", 82, 40);
                tracker -= 10;
            }
            tracker = 100;
            while (tracker > 5) {
                if (tracker == 100) {
                    StdDraw.pause(3000);
                }
                StdDraw.picture((double) Main.MAP_X_LEN / 2, (double) Main.MAP_Y_LEN / 2,
                        "assets/background/load" + tracker + ".png", 82, 40);
                tracker -= 10;
                StdDraw.pause(50);
                StdDraw.show();
            }
        }
        hasShownLoading = true;
    }


    public void setHasShownLoading() {
        layers = new ArrayList<>();
        hasShownLoading = false;
        int tracker = 100;
        for (int i = 1; i < 17; i++) {
            layers.add(new BackgroundLayer((double) Main.MAP_X_LEN / 2,
                    (double) Main.MAP_Y_LEN / 2, "assets/background/" + i + ".png", 82, 40));
        }
    }

    public void bufferLayers() {
        int mainCount = 13;
        for (BackgroundLayer layer : layers) {
            if (mainCount == 0) {
                return;
            }
            layer.bufferLayer();
            mainCount--;
        }
    }

    public void bufferMainMenu() {
        bufferLayers();
        layers.get(13).bufferLayer();
    }

    public void bufferSeeenu() {
        bufferLayers();
        layers.get(14).bufferLayer();
    }

    public void bufferLoadMenu() {
        bufferLayers();
        layers.get(15).bufferLayer();
    }


    public void nextFrame(double speed) {
        if (speed > 3) {
            speed = 3;
        }
        layers.get(0).shiftX(0.01 * speed);
        layers.get(1).shiftX(0.05 * speed);
        layers.get(2).shiftX(0.03 * speed);
        layers.get(3).shiftX(0.04 * speed);
        layers.get(4).shiftX(0.05 * speed);
        layers.get(5).shiftX(0.05 * speed);
        layers.get(6).shiftX(0.15 * speed);
        layers.get(7).shiftX(0.15 * speed);
        layers.get(8).shiftX(0.00 * speed);
        layers.get(9).shiftX(0.00 * speed);
        layers.get(9).bounce(0.3);
        layers.get(10).shiftX(0.15 * speed);
        layers.get(11).shiftX(0.2 * speed);
        layers.get(12).shiftX(0.3 * speed);
    }


}
