package core.WorldGeneration;

import core.Main;
import tileengine.TETile;
import tileengine.Tileset;
import utils.GoodRandomUtils;

import java.util.ArrayList;

public class World {
    public static final double ROOM_DENSITY = 0.4;
    public static final int ROOM_BRANCH_LIMIT = 3;
    public static final int ROOM_DISTANCE_MIN = 9;
    public static final int ROOM_DISTANCE_MAX = Main.MAP_X_LEN / 2;
    public static final int ROOM_MIN_X_RADIUS = 2;
    public static final int ROOM_MAX_X_RADIUS = Main.MAP_X_LEN / 10;
    public static final int ROOM_MIN_Y_RADIUS = 2;
    public static final int ROOM_MAX_Y_RADIUS = Main.MAP_Y_LEN / 10;
    public static final int MAX_WATER_DISTANCE = 4;
    // Good Random Utils is a utility/helper class that provides all the useful random methods thats being used i wgen
    // It is static because it needs to be initialized differently for every world given its unique seed.
    final long SEED;
    RoomsGenerator roomsInformation;
    //
    private TETile[][] tiles;
    private final GoodRandomUtils utils;
    private final Coordinate ORIGIN;

    public World(long seed) {
        this.SEED = seed;
        utils = new GoodRandomUtils(SEED);
        ORIGIN = new Coordinate(this, utils.minToMaxIntUniform(10, Main.MAP_X_LEN - 10),
                utils.minToMaxIntUniform(10, Main.MAP_Y_LEN - 10));

        //Begin the world generation proceess here.
        createGrass();
        createRooms();

        createFarms();

        createBottomWalls();
        createWaters();
        createShadows();

    }

    public long getSeed() {
        return SEED;
    }

    public void setTile(int x, int y, TETile tile) {
        tiles[x][y] = tile;
    }

    public TETile[][] getWorld() {
        return tiles;
    }

    public GoodRandomUtils getUtils() {
        return utils;
    }

    public void createCornerWaters() {
        for (int i = 0; i < 100; i++) {
            createCornerWater();
        }
    }

    public void createCornerWater() {
        Coordinate start = new Coordinate(this, utils.minToMaxIntUniform(0, Main.MAP_X_LEN - 1),
                utils.minToMaxIntUniform(0, Main.MAP_Y_LEN - 1));
        ArrayList<Coordinate> toBeAddedWaterTiles = new ArrayList<>();

        while (!start.isInCornerLocation()) {
            start = new Coordinate(this, utils.minToMaxIntUniform(0, Main.MAP_X_LEN - 1),
                    utils.minToMaxIntUniform(0, Main.MAP_Y_LEN - 1));
        }
        createCornerWaterHelper(start, toBeAddedWaterTiles, 6);

        for (Coordinate coord : toBeAddedWaterTiles) {
            coord.setTile(Tileset.WATER);
        }
    }

    public void createCornerWaterHelper(Coordinate current, ArrayList<Coordinate> tilesToAdd, int remaining) {
        if (remaining <= 0 || tilesToAdd.contains(current)) {
            tilesToAdd.add(current);
            return;
        }
        System.out.println("Creating water at " + current.toString());

        tilesToAdd.add(current);
        createCornerWaterHelper(current.getLowerCoord(), tilesToAdd, remaining - 1);
        createCornerWaterHelper(current.getUpperCoord(), tilesToAdd, remaining - 1);
        createCornerWaterHelper(current.getRightCoord(), tilesToAdd, remaining - 1);
        createCornerWaterHelper(current.getLeftCoord(), tilesToAdd, remaining - 1);

    }

    public void createWaters() {
        for (int i = 0; i < 20; i++) {
            createWater();
        }
    }

    public void createWater() {
        Coordinate start = new Coordinate(this, utils.minToMaxIntUniform(0, Main.MAP_X_LEN - 1),
                utils.minToMaxIntUniform(0, Main.MAP_Y_LEN - 1));
        ArrayList<Coordinate> toBeAddedWaterTiles = new ArrayList<>();

        while (start.getTile() != Tileset.GRASS && start.isInOuterLocation()) {
            start = new Coordinate(this, utils.minToMaxIntUniform(0, Main.MAP_X_LEN - 1),
                    utils.minToMaxIntUniform(0, Main.MAP_Y_LEN - 1));
        }
        createWaterHelper(start, toBeAddedWaterTiles, 10);

        if (toBeAddedWaterTiles.size() > 20) {
            for (Coordinate coord : toBeAddedWaterTiles) {
                coord.setTile(Tileset.WATER);
            }
        }
    }

    public void createWaterHelper(Coordinate current, ArrayList<Coordinate> tilesToAdd, int remaining) {
        if (remaining <= 0 || !current.ifAdjacentIsGrass() || tilesToAdd.contains(current)) {
            tilesToAdd.add(current);
            return;
        }

        tilesToAdd.add(current);
        createWaterHelper(current.getLowerCoord(), tilesToAdd, remaining - 2);
        createWaterHelper(current.getUpperCoord(), tilesToAdd, remaining - 1);
        createWaterHelper(current.getRightCoord(), tilesToAdd, remaining - 2);
        createWaterHelper(current.getLeftCoord(), tilesToAdd, remaining - 1);

    }


    public void createBottomWalls() {
        for (int x = 0; x < Main.MAP_X_LEN; x++) {
            for (int y = 0; y < Main.MAP_Y_LEN; y++) {
                Coordinate coord = new Coordinate(this, x, y);
                if (coord.getTile() == Tileset.WALL && (new Coordinate(this, x, y - 1).getTile() != Tileset.WALL)
                        && (new Coordinate(this, x, y - 1).getTile() != Tileset.BOTWALL)) {
                    coord.setTile(Tileset.BOTWALL);
                }
            }
        }
    }

    public void createShadows() {
        int xCastSlope = utils.minToMaxIntUniform(1, 4);
        int yCastSlope = utils.minToMaxIntUniform(1, 2);

        for (int x = 0; x < Main.MAP_X_LEN; x++) {
            for (int y = 0; y < Main.MAP_Y_LEN; y++) {
                if (new Coordinate(this, x, y).getTile() == Tileset.WALL
                        || new Coordinate(this, x, y).getTile() == Tileset.BOTWALL
                        || new Coordinate(this, x, y).getTile() == Tileset.FLOOR) {
                    for (int j = 1; j < 4; j++) {
                        for (int i = j; i < 5; i += 3) {
                            new Coordinate(this, (i * j) + x, (j * -1) + y).shadeTile();
                        }
                    }
                }
            }
        }
    }

    public void createGrass() {
        tiles = new TETile[Main.MAP_X_LEN][Main.MAP_Y_LEN];
        for (int x = 0; x < Main.MAP_X_LEN; x++) {
            for (int y = 0; y < Main.MAP_Y_LEN; y++) {
                tiles[x][y] = Tileset.GRASS;
            }
        }
    }

    public void createRooms() {
        roomsInformation = new RoomsGenerator(this);
    }

    public void createFarms() {
        for (int i = 0; i < 20; i++) {
            createFarm();
        }
    }

    public void createFarm() {
        Coordinate waterStart = new Coordinate(this, utils.minToMaxIntUniform(0, Main.MAP_X_LEN - 1),
                utils.minToMaxIntUniform(0, Main.MAP_Y_LEN - 1));
        if (tiles[waterStart.getX()][waterStart.getY()] != Tileset.GRASS) {
            while (tiles[waterStart.getX()][waterStart.getY()] != Tileset.GRASS) {
                waterStart = new Coordinate(this, utils.minToMaxIntUniform(0, Main.MAP_X_LEN - 1),
                        utils.minToMaxIntUniform(0, Main.MAP_Y_LEN - 1));
            }
        }
        for (int x = Math.max(0, waterStart.getX() - MAX_WATER_DISTANCE);
             x <= Math.min(waterStart.getY() + MAX_WATER_DISTANCE,
                Main.MAP_X_LEN - 1); x++) {
            for (int y = Math.max(0, waterStart.getY() - MAX_WATER_DISTANCE);
                 y <= Math.min(waterStart.getY() + MAX_WATER_DISTANCE,
                         Main.MAP_Y_LEN - 1); y++) {
                Coordinate curr = new Coordinate(this, x, y);
                if (!curr.isOneTileAwayFromRoom()) {
                    tiles[curr.getX()][curr.getY()] = Tileset.FARM;
                }
            }
        }
    }

    public Coordinate getORIGIN() {
        return ORIGIN;
    }

    public int getWallsDestroyed() {
        return roomsInformation.getDestroyedWalls();
    }
}
