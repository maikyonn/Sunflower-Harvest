package tileengine;

import core.Main;
import core.TileLighting;
import core.WorldGeneration.Coordinate;
import core.WorldGeneration.World;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;
import java.awt.Font;

/**
 * Utility class for rendering tiles. You do not need to modify this file. You're welcome
 * to, but be careful. We strongly recommend getting everything else working before
 * messing with this renderer, unless you're trying to do something fancy like
 * allowing scrolling of the screen or tracking the avatar or something similar.
 */
public class TERenderer {
    private static final int TILE_SIZE = 16;
    private int width;
    private int height;
    private int xOffset;
    private int yOffset;

    /**
     * Same functionality as the other initialization method. The only difference is that the xOff
     * and yOff parameters will change where the renderFrame method starts drawing. For example,
     * if you select w = 60, h = 30, xOff = 3, yOff = 4 and then call renderFrame with a
     * TETile[50][25] array, the renderer will leave 3 tiles blank on the left, 7 tiles blank
     * on the right, 4 tiles blank on the bottom, and 1 tile blank on the top.
     * @param w width of the window in tiles
     * @param h height of the window in tiles.
     */
    public void initialize(int w, int h, int xOff, int yOff) {
        this.width = w;
        this.height = h;
        this.xOffset = xOff;
        this.yOffset = yOff;
        StdDraw.setCanvasSize(width * TILE_SIZE, height * TILE_SIZE);
        Font font = new Font("Monaco", Font.BOLD, TILE_SIZE - 2);
        StdDraw.setFont(font);      
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);

        StdDraw.enableDoubleBuffering();
        StdDraw.picture((double) Main.MAP_X_LEN / 2, (double) Main.MAP_Y_LEN / 2, "assets/background/load100.png", 82, 40);
        StdDraw.show();
    }



    public void initializePlayerPerspectve(int w, int h) {
        this.width = w;
        this.height = h;
        StdDraw.setCanvasSize(w * Main.PIXELDENSITY, h * Main.PIXELDENSITY);
        Font font = new Font("Monaco", Font.BOLD, TILE_SIZE - 2);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, w);
        StdDraw.setYscale(0, h);

        StdDraw.clear(new Color(100, 100, 100));
        StdDraw.enableDoubleBuffering();
        StdDraw.show();
    }

    /**
     * Initializes StdDraw parameters and launches the StdDraw window. w and h are the
     * width and height of the world in number of tiles. If the TETile[][] array that you
     * pass to renderFrame is smaller than this, then extra blank space will be left
     * on the right and top edges of the frame. For example, if you select w = 60 and
     * h = 30, this method will create a 60 tile wide by 30 tile tall window. If
     * you then subsequently call renderFrame with a TETile[50][25] array, it will
     * leave 10 tiles blank on the right side and 5 tiles blank on the top side. If
     * you want to leave extra space on the left or bottom instead, use the other
     * initializatiom method.
     * @param w width of the window in tiles
     * @param h height of the window in tiles.
     */
    public void initialize(int w, int h) {
        initialize(w, h, 0, 0);
    }

    /**
     * Takes in a 2d array of TETile objects and renders the 2d array to the screen, starting from
     * xOffset and yOffset.
     *
     * If the array is an NxM array, then the element displayed at positions would be as follows,
     * given in units of tiles.
     *
     *              positions   xOffset |xOffset+1|xOffset+2| .... |xOffset+world.length
     *                     
     * startY+world[0].length   [0][M-1] | [1][M-1] | [2][M-1] | .... | [N-1][M-1]
     *                    ...    ......  |  ......  |  ......  | .... | ......
     *               startY+2    [0][2]  |  [1][2]  |  [2][2]  | .... | [N-1][2]
     *               startY+1    [0][1]  |  [1][1]  |  [2][1]  | .... | [N-1][1]
     *                 startY    [0][0]  |  [1][0]  |  [2][0]  | .... | [N-1][0]
     *
     * By varying xOffset, yOffset, and the size of the screen when initialized, you can leave
     * empty space in different places to leave room for other information, such as a GUI.
     * This method assumes that the xScale and yScale have been set such that the max x
     * value is the width of the screen in tiles, and the max y value is the height of
     * the screen in tiles.
     * @param world the 2D TETile[][] array to render
     */
    public void renderFrame(TETile[][] world) {
        TileLighting lighter =  new TileLighting();
        lighter.changeLighting(0);

        int numXTiles = world.length;
        int numYTiles = world[0].length;
        StdDraw.clear(new Color(0, 0, 0));
        for (int x = 0; x < numXTiles; x += 1) {
            for (int y = 0; y < numYTiles; y += 1) {
                if (world[x][y] == null) {
                    throw new IllegalArgumentException("Tile at position x=" + x + ", y=" + y
                            + " is null.");
                }
                world[x][y].drawWithSize(x + xOffset, y + yOffset);
            }
        }
        StdDraw.show();
    }

    public void renderPlayerFrame(World world, CameraCoordinates camera, PlayerCoordinates player, int score, TETile cursorTile) {


        TileLighting lighter =  new TileLighting();
        lighter.changeLighting(0);

        StdDraw.clear(new Color(0, 0, 0));
        for (int x = 0; x < Main.WINDOW_X_LEN; x += 1) {
            for (int y = 0; y < Main.WINDOW_Y_LEN; y += 1) {
                Coordinate currrentCoord = new Coordinate(world, x, y);
                if (currrentCoord.getTile() == null) {
                    throw new IllegalArgumentException("Tile at position x=" + x + ", y=" + y
                            + " is null.");
                }
                currrentCoord.getTileWithOffset(camera.x, camera.y).drawWithSize(x, y);
            }
        }

//        StdDraw.picture(12, 6.25, "/assets/gradient.png", 300, 150);
        StdDraw.picture(12, 6.25, "/assets/gradient.png", 300, 150);
        StdDraw.picture(Main.OVERLAY_X_POS, Main.OVERLAY_Y_POS, "/assets/scoreoverlay.png", Main.OVERLAY_SCALED_WIDTH, Main.OVERLAY_SCALED_HEIGHT);

        StdDraw.setPenColor(80, 50, 31);
         Font fontScore = new Font("System", Font.BOLD, 60);
        StdDraw.setFont(fontScore);
        StdDraw.text(7, 2.65, "" + score);
        Font fontSaveNotSelected = new Font("System", Font.PLAIN, 20);
        StdDraw.setFont(fontSaveNotSelected);
        if (cursorTile != null) {
            StdDraw.text(7, 2.3, cursorTile.toString());
        }

        StdDraw.picture(4.5 + player.x, 6.5 + player.y, "/assets/charsprite.png", 1, 1);
        StdDraw.show();
    }
}
