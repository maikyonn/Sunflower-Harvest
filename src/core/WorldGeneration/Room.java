package core.WorldGeneration;

import utils.GoodRandomUtils;

import java.util.ArrayList;

public class Room {
    private final Coordinate coord;
    private final int xRadius;
    private final int yRadius;
    ArrayList<Room> neighbors;
    GoodRandomUtils utils;
    private final int numOfNeighbors;
    private final boolean isConnected;
    private final World world;


    public Room(World w, Coordinate coord, GoodRandomUtils utils) {
        world = w;
        this.coord = coord;
        xRadius = utils.minToMaxIntGauss(World.ROOM_MIN_X_RADIUS, World.ROOM_MAX_X_RADIUS);
        yRadius = utils.minToMaxIntGauss(World.ROOM_MIN_Y_RADIUS, World.ROOM_MAX_Y_RADIUS);
        numOfNeighbors = xRadius;
        neighbors = new ArrayList<>();
        isConnected = false;
        this.utils = utils;
    }

    public ArrayList<Room> createNeighbors() {
        for (int i = 0; i < numOfNeighbors; i++) {
            Coordinate nextCoord = nextNeighborCoordinates();
            if (nextCoord.onARoom()) {
                int decision2 = utils.minToMaxIntUniform(0, 5);
                if (decision2 < 4) {
                    continue;
                }
            }
            neighbors.add(new Room(world, nextCoord, utils));
        }
        return neighbors;
    }

    public Coordinate nextNeighborCoordinates() {
        int xOffset = utils.minToMaxIntUniform(World.ROOM_DISTANCE_MIN, World.ROOM_DISTANCE_MAX);
        int yOffset = utils.minToMaxIntUniform(World.ROOM_DISTANCE_MIN, World.ROOM_DISTANCE_MAX);
        int decision = utils.minToMaxIntUniform(0, 4);
        int x = coord.getX();
        int y = coord.getY();

        switch (decision) {
            case 0 -> {
                Coordinate newCoord = new Coordinate(world, x + xOffset, y + yOffset);
                if (newCoord.isInDecentLocation()) {

                    return newCoord;
                }
            }
            case 1 -> {
                Coordinate newCoord = new Coordinate(world, x + xOffset, y - yOffset);
                if (newCoord.isInDecentLocation()) {
                    return newCoord;
                }
            }
            case 2 -> {
                Coordinate newCoord = new Coordinate(world, x - xOffset, y + yOffset);
                if (newCoord.isInDecentLocation()) {
                    return newCoord;
                }
            }
            case 3 -> {
                Coordinate newCoord = new Coordinate(world, x - xOffset, y - yOffset);
                if (newCoord.isInDecentLocation()) {
                    return newCoord;
                }
            }
            default -> {
                return null;
            }
        }
        return nextNeighborCoordinates();
    }

    public int getX() {
        return coord.getX();
    }

    public int getY() {
        return coord.getY();
    }

    public int getYRadius() {
        return yRadius;
    }

    public int getXRadius() {
        return xRadius;
    }

    public Coordinate getCoord() {
        return coord;
    }

}
