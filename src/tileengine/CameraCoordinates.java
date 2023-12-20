package tileengine;

import core.Main;
import core.WorldGeneration.World;

public class CameraCoordinates {
    int x;
    int y;
    World world;
    public CameraCoordinates(World w, int x, int y) {
        this.x = x;
        this.y = y;
        world = w;
    }
    public boolean canMoveLeft() {
        return !getLeftCoord().reachedBorder();
    }
    public void moveLeft() {
        x--;
    }

    public boolean canMoveRight() {
        return !getRightCoord().reachedBorder();
    }
    public void moveRight() {
        x++;
    }

    public boolean canMoveUp() {
        return !getUpperCoord().reachedBorder();
    }
    public void moveUp() {
        y++;
    }

    public boolean canMoveDown() {
        return !getLowerCoord().reachedBorder();
    }
    public void moveDown() {
        y--;
    }

    public boolean reachedBorder() {
        if (x > Main.MAP_X_LEN - Main.WINDOW_X_LEN || y > Main.MAP_Y_LEN - Main.WINDOW_Y_LEN
        || x < 0 || y < 0) {
            return true;
        }
        return false;
    }

    public CameraCoordinates getRightCoord(){
        return (new CameraCoordinates(world,x+1, y));
    }
    public CameraCoordinates getLeftCoord(){
        return (new CameraCoordinates(world,x-1, y));
    }
    public CameraCoordinates getUpperCoord(){
        return (new CameraCoordinates(world,x, y+1));
    }
    public CameraCoordinates getLowerCoord(){
        return (new CameraCoordinates(world,x, y-1));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
