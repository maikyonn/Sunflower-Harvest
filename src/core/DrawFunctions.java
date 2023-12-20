package core;

import core.WorldGeneration.Coordinate;
import tileengine.TETile;

public class DrawFunctions {
    //The most basic draw action. This function should be called EVERY time anything is being drawn to avoid
    //complicated bounds issues and rerendering operations. Makes literally all code regarding draw safe.
    // Replace Conditions
    // 0 - Draws over everything.
    // 1 - Replaces a specific conditionTile with a given tile.
    // 2 - Skips a specific conditionTile, and draws over all other tiles.

    public static int drawSpot(TETile[][] tiles, Coordinate xy, TETile tile,
                               TETile conditionTile, int replaceCondition) {
        if (xy.isAccessible()) {
            int x = xy.getX();
            int y = xy.getY();

            if (replaceCondition == 0) {
                tiles[x][y] = tile;
                return 0;
            }
            if (replaceCondition == 1) {
                if (tiles[x][y] == conditionTile) {
                    tiles[x][y] = tile;
                    return 1;
                }
                return 2;
            }
            if (replaceCondition == 2) {
                if (tiles[x][y] == conditionTile) {
                    return 2;
                }
                tiles[x][y] = tile;
                return 0;
            }
        }
        return -1;
    }

    public static void drawLine(TETile[][] tiles, Coordinate xy,
                                int length, Direction direction, TETile tile) {
        int x = xy.getX();
        int y = xy.getY();

        switch (direction) {
            case NORTH -> {
                for (int curr = y; curr < y + length; curr++) {
                    drawSpot(tiles, new Coordinate(x, curr), tile, null, 0);
                }
            }
            case EAST -> {
                for (int curr = x; curr < x + length; curr++) {
                    drawSpot(tiles, new Coordinate(curr, y), tile, null, 0);
                }
            }
            case SOUTH -> {
                for (int curr = y; curr > y - length; curr--) {
                    drawSpot(tiles, new Coordinate(x, curr), tile, null, 0);
                }
            }
            case WEST -> {
                for (int curr = x; curr > x - length; curr--) {
                    drawSpot(tiles, new Coordinate(curr, y), tile, null, 0);
                }
            }
            default -> {
                break;
            }
        }
    }

    //Return Values
    // -1 - Failure
    // 0 - Completed Normal Replacement
    // 1 - Completed Specific Replacement
    // 2 - Skipped Tile
    public enum Direction {
        NORTH, EAST, SOUTH, WEST
    }
}
