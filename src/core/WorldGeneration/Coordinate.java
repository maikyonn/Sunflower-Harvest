package core.WorldGeneration;

import core.Main;
import tileengine.TETile;
import tileengine.Tileset;

public class Coordinate {
    private int x;
    private int y;
    World world;
    TETile[][] tileWorld;
    TETile tileItself;

    public Coordinate(World w, int x, int y) {
        this.x = x;
        this.y = y;
        world = w;
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
        world = null;
    }

    public Coordinate(TETile[][] tileWorld, int x, int y) {
        this.x = x;
        this.y = y;
        this.tileWorld = tileWorld;
        tileItself = tileWorld[x][y];
    }

    public boolean isAccessible() {
        return x >= 0 && y >= 0 && x < Main.MAP_X_LEN && y < Main.MAP_Y_LEN;
    }

    public boolean isInDecentLocation() {
        if (x < 5 || y < 5 || x >= Main.MAP_X_LEN - 10 || y >= Main.MAP_Y_LEN - 10) {
            return false;
        }
        return x >= 15 && y >= 10;
    }

    public boolean isInOuterLocation() {
        return x < 3 || y < 3 || x >= Main.MAP_X_LEN - 3 || y >= Main.MAP_Y_LEN - 3;
    }

    public boolean isInCornerLocation() {
        return x < 18 && y < 8;
    }

    public boolean onARoom() {
        return world.getWorld()[x][y] == Tileset.FLOOR;
    }

    public boolean isOneTileAwayFromRoom() {
        TETile[][] tiles = world.getWorld();
        int width = tiles.length;
        int height = tiles[0].length;
        return (x > 0 && y > 0 && tiles[x - 1][y - 1] != Tileset.GRASS && tiles[x - 1][y - 1] != Tileset.FARM
                || x > 0 && tiles[x - 1][y] != Tileset.GRASS && tiles[x - 1][y] != Tileset.FARM
                || x > 0 && y < height - 1 && tiles[x - 1][y + 1]
                != Tileset.GRASS && tiles[x - 1][y + 1] != Tileset.FARM
                || y > 0 && tiles[x][y - 1] != Tileset.GRASS && tiles[x][y - 1] != Tileset.FARM
                || y < height - 1 && tiles[x][y + 1] != Tileset.GRASS && tiles[x][y + 1] != Tileset.FARM
                || x < width - 1 && y > 0 && tiles[x + 1][y - 1] != Tileset.GRASS && tiles[x + 1][y - 1] != Tileset.FARM
                || x < width - 1 && tiles[x + 1][y] != Tileset.GRASS && tiles[x + 1][y] != Tileset.FARM
                || x < width - 1 && y < height - 1 && tiles[x + 1][y + 1]
                != Tileset.GRASS && tiles[x + 1][y + 1] != Tileset.FARM);
    }

    public boolean ifAdjacentIsGrass() {
        return getLeftTile() == Tileset.GRASS
                && getRightTile() == Tileset.GRASS
                && getLowerTile() == Tileset.GRASS
                && getUpperTile() == Tileset.GRASS;
    }

    public TETile getTile() {
        if (isAccessible()) {
            return world.getWorld()[x][y];
        }
        return null;
    }

    public void setTile(TETile tile) {
        if (isAccessible()) {
            world.getWorld()[x][y] = tile;
        }
    }

    public TETile getTileWithOffset(int xOffset, int yOffset) {
        Coordinate offsetCoord = new Coordinate(world, x + xOffset, y + yOffset);
        if (offsetCoord.isAccessible()) {
            return offsetCoord.getTile();
        }
        return this.getTile();
    }

    public void setWorldTile(TETile tile) {
        if (isAccessible()) {
            tileWorld[x][y] = tile;
        }
    }

    public void moveLeft() {
        if (getLeftCoord().isAccessible()) {
            x--;
        }
    }

    public void moveRight() {
        if (getRightCoord().isAccessible()) {
            x++;
        }
    }

    public void moveUp() {
        if (getUpperCoord().isAccessible()) {
            y++;
        }
    }

    public void moveDown() {
        if (getLowerCoord().isAccessible()) {
            y--;
        }
    }

    public Coordinate getRightCoord() {
        return (new Coordinate(world, x + 1, y));
    }

    public Coordinate getLeftCoord() {
        return (new Coordinate(world, x - 1, y));
    }

    public Coordinate getUpperCoord() {
        return (new Coordinate(world, x, y + 1));
    }

    public Coordinate getLowerCoord() {
        return (new Coordinate(world, x, y - 1));
    }

    public TETile getRightTile() {
        return (new Coordinate(world, x + 1, y)).getTile();
    }

    public TETile getLeftTile() {
        return (new Coordinate(world, x - 1, y)).getTile();
    }

    public TETile getUpperTile() {
        return (new Coordinate(world, x, y + 1)).getTile();
    }

    public TETile getLowerTile() {
        return (new Coordinate(world, x, y - 1)).getTile();
    }


    public void shadeTile() {
        if (isAccessible()) {
            if (getTile() == Tileset.GRASS) {
                world.getWorld()[x][y] = Tileset.SHADOWGRASS;
            }
            if (getTile() == Tileset.FARM) {
                world.getWorld()[x][y] = Tileset.SHADOWFARM;
            }
            if (getTile() == Tileset.VERTICALFENCE) {
                world.getWorld()[x][y] = Tileset.SHADOWVERTICALFENCE;
            }
            if (getTile() == Tileset.HORIZONTALFENCE) {
                world.getWorld()[x][y] = Tileset.SHADOWHORIZONTALFENCE;
            }
            if (getTile() == Tileset.TOPRIGHTFENCE) {
                world.getWorld()[x][y] = Tileset.SHADOWTOPRIGHTFENCE;
            }
            if (getTile() == Tileset.TOPRIGHTFENCE) {
                world.getWorld()[x][y] = Tileset.SHADOWTOPRIGHTFENCE;
            }
            if (getTile() == Tileset.TOPLEFTFENCE) {
                world.getWorld()[x][y] = Tileset.SHADOWTOPLEFTFENCE;
            }
            if (getTile() == Tileset.BOTRIGHTFENCE) {
                world.getWorld()[x][y] = Tileset.SHADOWBOTRIGHTFENCE;
            }
            if (getTile() == Tileset.BOTLEFTFENCE) {
                world.getWorld()[x][y] = Tileset.SHADOWBOTLEFTFENCE;
            }
            if (getTile() == Tileset.HORIZONTALPATH) {
                world.getWorld()[x][y] = Tileset.SHADOWHORIZONTALPATH;
            }
            if (getTile() == Tileset.VERTICALPATH) {
                world.getWorld()[x][y] = Tileset.SHADOWVERTICALPATH;
            }
            if (getTile() == Tileset.JUNCTIONPATH) {
                world.getWorld()[x][y] = Tileset.SHADOWJUNCTIONPATH;
            }
            if (getTile() == Tileset.WATER) {
                world.getWorld()[x][y] = Tileset.SHADOWWATER;
            }

        }
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
