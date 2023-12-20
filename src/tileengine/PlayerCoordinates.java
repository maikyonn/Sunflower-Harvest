package tileengine;

import core.Main;
import core.WorldGeneration.World;

public class PlayerCoordinates{
    int x;
    int y;
    World world;
    CameraCoordinates camera;

    public PlayerCoordinates(CameraCoordinates camera, World w, int x, int y) {
        this.x = x;
        this.y = y;
        this.world = w;
        this.camera = camera;
    }

    public PlayerCoordinates getTruePlayerCoordinates() {
        return new PlayerCoordinates(camera, world, x + camera.x + 4, y + camera.y + 6);
    }

    public TETile getTile() {
        if(isAccessible()) {
            return world.getWorld()[x][y];
        }
        return null;
    }

    public void setTile(TETile tile) {
        if(isAccessible()) {
            world.setTile(x, y, tile);
        }
    }

    public boolean isAccessible() {
        if (x < 0 || x >= Main.MAP_X_LEN || y < 0 || y >= Main.MAP_Y_LEN) {
            return false;
        }
        return true;
    }

    public boolean canMoveLeft() {
        PlayerCoordinates temp = getTruePlayerCoordinates().getLeftCoord();
        if (temp.isBorder()) {
            return false;
        }
        if (temp.isAccessible()) {
            return true;
        }
        return false;
    }

    public void moveLeft() {
        x--;
    }
    public boolean canMoveRight() {
        PlayerCoordinates temp = getTruePlayerCoordinates().getRightCoord();
        if (temp.isBorder()) {
            return false;
        }
        if (temp.isAccessible()) {
            return true;
        }
        return false;
    }

    public void moveRight() {
        x++;
    }

    public boolean canMoveUp() {
        PlayerCoordinates temp = getTruePlayerCoordinates().getUpperCoord();
        if (temp.isBorder()) {
            return false;
        }
        if (temp.isAccessible()) {
            return true;
        }
        return false;
    }

    public void moveUp() {
        y++;
    }
    public boolean canMoveDown() {
        PlayerCoordinates temp = getTruePlayerCoordinates().getLowerCoord();
        if (temp.isBorder()) {
            return false;
        }
        if (temp.isAccessible()) {
            return true;
        }
        return false;
    }

    public void moveDown() {
        y--;
    }

    public PlayerCoordinates getRightCoord(){
        return (new PlayerCoordinates(camera, world,x+1, y));
    }
    public PlayerCoordinates getLeftCoord(){
        return (new PlayerCoordinates(camera, world,x-1, y));
    }
    public PlayerCoordinates getUpperCoord(){
        return (new PlayerCoordinates(camera, world,x, y+1));
    }
    public PlayerCoordinates getLowerCoord(){
        return (new PlayerCoordinates(camera, world,x, y-1));
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public boolean isBorder() {
        TETile tile = getTile();
        return tile == Tileset.WALL
        || tile == Tileset.BOTWALL
        || tile == Tileset.WATER;
    }

    public boolean isPickable() {
        TETile tile = getTile();
        return tile == Tileset.FARM
                || tile == Tileset.SHADOWFARM;
    }
}
