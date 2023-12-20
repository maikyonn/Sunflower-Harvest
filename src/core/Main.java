package core;

import tileengine.TERenderer;
import tileengine.TETile;

import java.util.Scanner;

public class Main {


    //X and Y length are the dimensions of the World and the renderer. AKA the dimensions of tiles
    public static final int MAP_X_LEN = 60;
    public static final int MAP_Y_LEN = 40;
    public static final int WINDOW_X_LEN = 9;
    public static final int WINDOW_Y_LEN = 13;
    public static final int PIXELDENSITY = 50;

    public static final int OVERLAY_X_POS = 4;
    public static final int OVERLAY_Y_POS = 2;
    public static final double OVERLAY_SCALED_WIDTH = 9;
    public static final double OVERLAY_SCALED_HEIGHT = 4.5;


    public static final boolean DEBUG_WORLD_GEN = false;


    public static void main(String[] args) {
//        TERenderer renderer = new TERenderer();
//        renderer.initialize(60, 40);
//        WorldGenerator.createInterestingWorld(renderer);

        Menu menu = new Menu();
        menu.start();
    }

    public static void renderWorldPause(TERenderer renderer, TETile[][] w) {
        if (DEBUG_WORLD_GEN) {
            Scanner myObj = new Scanner(System.in);
            System.out.println("Render Paused: Press enter to proceed.");
            myObj.nextLine();
            renderer.renderFrame(w);
        }
    }

    public static void renderWorldBrowse(TERenderer renderer, TETile[][] w) {
        renderer.renderFrame(w);
        Scanner myObj = new Scanner(System.in);
        System.out.println("Press enter to proceed to the next world.");
        myObj.nextLine();
    }

}
