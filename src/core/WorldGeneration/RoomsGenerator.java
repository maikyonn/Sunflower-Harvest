package core.WorldGeneration;

import core.DrawFunctions;
import core.Main;
import tileengine.Tileset;
import utils.GoodRandomUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class RoomsGenerator implements Iterable<Room> {

    private final ArrayList<Room> roomsData;
    int destroyedWalls;
    private final UnionRoomFind disjointRoomIdentification;
    private final World world;
    private final GoodRandomUtils utils;
    private int roomCount;


    public RoomsGenerator(World w) {
        world = w;
        utils = w.getUtils();
        roomsData = new ArrayList<>();
        destroyedWalls = 0;
        disjointRoomIdentification = new UnionRoomFind(world);

        createRoomsTreelike(World.ROOM_BRANCH_LIMIT);
        drawRooms();
    }

    //Creates rooms in a recursive tree-like way and appends them to roomsData,
    // and has a limiter so that it doesn't generate forever.
    public void createRoomsTreelike(int limiter) {
        // Queue to create neighbors for nodes.
        //1. Add a single starting room, which we will optionally manually set its number of neighbors.
        Queue<Room> createNeighborsQueue = new LinkedList<>();
        Room startingRoom = new Room(world, world.getORIGIN(), utils);
        createNeighborsQueue.add(startingRoom);

        //2. Create a branching counter/limiter so that we don't loop forever.
        int branchLimiter = limiter;

        //3. We deque the first item in the queue and add it to our more
        // permanent roomsData variable to access anytime.
        // With the first item, we create its neighbors and add all of them
        // back onto the queue, so that they will be further
        // creating neighbors, in a recursive-like way.
        while (!createNeighborsQueue.isEmpty()) {
            //Pop first item in queue
            Room popped = createNeighborsQueue.remove();
            roomsData.add(popped);

            //The branchLimiter variable counts how many times we
            // createNeighbors, and ensure that we only do it a certain amount of times.
            if (branchLimiter > 0) {
                ArrayList<Room> newNeighbors = popped.createNeighbors();
                createNeighborsQueue.addAll(newNeighbors);
                branchLimiter--;
            }
        }
    }

    // drawRooms has three stages and is split into 3 for loops (for readability):
    // 1. Drawing the floor
    // 2. Drawing the paths
    // 3. Drawing the room walls
    // drawRooms also features a pause method, which allows for pausing of the
    // world generation midway through in order
    // to observe the steps and debug more effectively. This feature can be
    // disabled in the Main.java DEBUG_WORLD_GEN variable.
    private void drawRooms() {
        for (Room room : roomsData) {
            drawFloorWithRadius(room);
        }
        for (Room room : roomsData) {
            drawPaths(room);
        }
        for (Room room : roomsData) {
            drawWallWithRadius(room);
        }
        drawFences();
        drawFixes();

    }

    public void drawFixes() {
        for (int i = 0; i < Main.MAP_X_LEN; i++) {
            for (int j = 0; j < Main.MAP_Y_LEN; j++) {
                Coordinate coord = new Coordinate(world, i, j);
                if (coord.getTile() == Tileset.FLOOR) {

                    DrawFunctions.drawSpot(world.getWorld(), coord.getRightCoord(), Tileset.WALL, Tileset.GRASS, 1);
                    DrawFunctions.drawSpot(world.getWorld(), coord.getLeftCoord(), Tileset.WALL, Tileset.GRASS, 1);
                    DrawFunctions.drawSpot(world.getWorld(), coord.getLowerCoord(), Tileset.WALL, Tileset.GRASS, 1);
                    DrawFunctions.drawSpot(world.getWorld(), coord.getUpperCoord(), Tileset.WALL, Tileset.GRASS, 1);
                }
                if (coord.getTile() == Tileset.JUNCTIONPATH) {
                    if (coord.getRightTile() != Tileset.HORIZONTALPATH
                            && coord.getLeftTile() != Tileset.HORIZONTALPATH
                            && coord.getLowerTile() != Tileset.HORIZONTALPATH
                            && coord.getUpperTile() != Tileset.HORIZONTALPATH
                            && coord.getRightTile() != Tileset.VERTICALPATH
                            && coord.getLeftTile() != Tileset.VERTICALPATH
                            && coord.getLowerTile() != Tileset.VERTICALPATH
                            && coord.getUpperTile() != Tileset.VERTICALPATH) {
                        DrawFunctions.drawSpot(world.getWorld(), coord, Tileset.FLOOR, Tileset.GRASS, 0);
                    }
                }
                if (coord.getTile() == Tileset.BOTLEFTFENCE) {
                    if (coord.getUpperTile() != Tileset.VERTICALFENCE) {
                        DrawFunctions.drawSpot(world.getWorld(), coord, Tileset.TOPLEFTFENCE, Tileset.GRASS, 0);
                    }
                }
            }
        }
    }

    public void drawFences() {
        for (int i = 0; i < Main.MAP_X_LEN; i++) {
            for (int j = 0; j < Main.MAP_Y_LEN; j++) {
                Coordinate coord = new Coordinate(world, i, j);
                if (coord.isAccessible()) {
                    if (coord.getTile() == Tileset.HORIZONTALPATH) {
                        DrawFunctions.drawSpot(world.getWorld(), coord.getUpperCoord(),
                                Tileset.HORIZONTALFENCE, Tileset.GRASS, 1);
                        DrawFunctions.drawSpot(world.getWorld(), coord.getLowerCoord(),
                                Tileset.HORIZONTALFENCE, Tileset.GRASS, 1);
                    }
                    if (coord.getTile() == Tileset.VERTICALPATH) {
                        DrawFunctions.drawSpot(world.getWorld(), coord.getLeftCoord(),
                                Tileset.VERTICALFENCE, Tileset.GRASS, 1);
                        DrawFunctions.drawSpot(world.getWorld(), coord.getRightCoord(),
                                Tileset.VERTICALFENCE, Tileset.GRASS, 1);
                    }

                }
            }
        }
        for (int i = 0; i < Main.MAP_X_LEN; i++) {
            for (int j = 0; j < Main.MAP_Y_LEN; j++) {
                Coordinate coord = new Coordinate(world, i, j);
                if (coord.isAccessible()) {
                    if (coord.getTile() == Tileset.JUNCTIONPATH) {
                        DrawFunctions.drawSpot(world.getWorld(), coord.getRightCoord(),
                                Tileset.VERTICALFENCE, Tileset.GRASS, 1);
                        DrawFunctions.drawSpot(world.getWorld(), coord.getLeftCoord(),
                                Tileset.VERTICALFENCE, Tileset.GRASS, 1);
                        DrawFunctions.drawSpot(world.getWorld(), coord.getLowerCoord(),
                                Tileset.HORIZONTALFENCE, Tileset.GRASS, 1);
                        DrawFunctions.drawSpot(world.getWorld(), coord.getUpperCoord(),
                                Tileset.HORIZONTALFENCE, Tileset.GRASS, 1);

                        Coordinate upperRightCoord = new Coordinate(world, coord.getX() + 1, coord.getY() + 1);
                        Coordinate upperLeftCoord = new Coordinate(world, coord.getX() - 1, coord.getY() + 1);
                        Coordinate lowerLeftCoord = new Coordinate(world, coord.getX() - 1, coord.getY() - 1);
                        Coordinate lowerRightCoord = new Coordinate(world, coord.getX() + 1, coord.getY() - 1);
                        DrawFunctions.drawSpot(world.getWorld(), upperRightCoord,
                                Tileset.TOPRIGHTFENCE, Tileset.GRASS, 1);
                        DrawFunctions.drawSpot(world.getWorld(), upperLeftCoord,
                                Tileset.TOPLEFTFENCE, Tileset.GRASS, 1);
                        DrawFunctions.drawSpot(world.getWorld(), lowerLeftCoord,
                                Tileset.BOTLEFTFENCE, Tileset.GRASS, 1);
                        DrawFunctions.drawSpot(world.getWorld(), lowerRightCoord,
                                Tileset.BOTRIGHTFENCE, Tileset.GRASS, 1);

                    }
                }
            }
        }
        drawFences2();
    }

    public void drawFences2() {
        for (int i = 0; i < Main.MAP_X_LEN; i++) {
            for (int j = 0; j < Main.MAP_Y_LEN; j++) {
                Coordinate coord = new Coordinate(world, i, j);
                if (coord.isAccessible()) {
                    if (coord.getTile() == Tileset.VERTICALFENCE) {
                        if (coord.getRightCoord().isAccessible()) {
                            if (coord.getRightTile() == Tileset.HORIZONTALFENCE) {
                                coord.setTile(Tileset.BOTLEFTFENCE);
                            }
                        }
                        if (coord.getLowerCoord().isAccessible()) {
                            if (coord.getLeftTile() == Tileset.HORIZONTALFENCE) {
                                coord.setTile(Tileset.TOPLEFTFENCE);
                            }
                        }
                    }
                    if (world.getWorld()[coord.getX()][coord.getY()] == Tileset.HORIZONTALFENCE) {
                        if (coord.getLowerCoord().isAccessible()) {
                            if (coord.getLowerTile() == Tileset.VERTICALFENCE) {
                                coord.setTile(Tileset.TOPRIGHTFENCE);
                            }
                        }
                        if (coord.getUpperCoord().isAccessible()) {
                            if (coord.getUpperTile() == Tileset.VERTICALFENCE) {
                                coord.setTile(Tileset.BOTRIGHTFENCE);
                            }
                        }

                    }
                }

            }
        }
    }

    // (1/1) The drawFloorWithRadius is actually very complicated,
    // here I will explain the need for a disjoint set.
    // We use a disjoint set to mark each room give each room its own
    // identifier. If our generator overlaps two rooms and their floors touch
    // the disjoint set will merge the two rooms into the same set.
    private void drawFloorWithRadius(Room room) {
        Coordinate coord = room.getCoord();
        int x = coord.getX();
        int y = coord.getY();
        int xRadius = room.getXRadius();
        int yRadius = room.getYRadius();

        for (int i = x - xRadius; i <= x + xRadius; i++) {
            for (int j = y - yRadius; j <= y + yRadius; j++) {
                DrawFunctions.drawSpot(world.getWorld(), new Coordinate(world, i, j),
                        Tileset.FLOOR, null, 0);
                disjointRoomIdentification.unionTileWithTwoCoords(room.getCoord(), new Coordinate(world, i, j));
            }
        }
    }

    // (1/3) This is the first of the functions used to draw the path
    // from a (parent) room to its neightbors (child).
    // The tricky thing with drawPaths is that you don't want to draw a
    // path to a room that is already connected to the origin (starter) room.
    // If you do, the board will look far too messy with unnecessary
    // paths and it will look ugly :(
    private void drawPaths(Room parentRoom) {
        for (Room asssociatedRoom : parentRoom.neighbors) {
            // ONLY DRAW PATHS TO ROOMS THAT ARE NOT CONNECTED TO THE ORIGIN ROOM
            if (!disjointRoomIdentification.checkCoordIsConnectedToOriginRoom(asssociatedRoom.getCoord())) {
                drawPath(parentRoom, asssociatedRoom);
            }
        }
    }

    private void drawPath(Room parentRoom, Room asssociatedRoom) {
        int xDiff = asssociatedRoom.getX() - parentRoom.getX();
        int yDiff = asssociatedRoom.getY() - parentRoom.getY();
        if (xDiff < 0) {
            if (yDiff < 0) {
                //left down
                drawPathLine(parentRoom.getCoord(), Math.abs(xDiff), DrawFunctions.Direction.WEST);
                drawPathLine(new Coordinate(world, parentRoom.getX() + xDiff,
                        parentRoom.getY()), Math.abs(yDiff), DrawFunctions.Direction.SOUTH);
            }
            if (yDiff >= 0) {
                //left up
                drawPathLine(parentRoom.getCoord(), Math.abs(xDiff), DrawFunctions.Direction.WEST);
                drawPathLine(new Coordinate(world, parentRoom.getX() + xDiff,
                        parentRoom.getY()), Math.abs(yDiff), DrawFunctions.Direction.NORTH);
            }
        }
        if (xDiff >= 0) {
            if (yDiff < 0) {
                //right down
                drawPathLine(parentRoom.getCoord(), Math.abs(xDiff), DrawFunctions.Direction.EAST);
                drawPathLine(new Coordinate(world, parentRoom.getX() + xDiff,
                        parentRoom.getY()), Math.abs(yDiff), DrawFunctions.Direction.SOUTH);
            }
            if (yDiff >= 0) {
                //right up
                drawPathLine(parentRoom.getCoord(), Math.abs(xDiff), DrawFunctions.Direction.EAST);
                drawPathLine(new Coordinate(world, parentRoom.getX() + xDiff,
                        parentRoom.getY()), Math.abs(yDiff), DrawFunctions.Direction.NORTH);
            }
        }
    }

    public void drawPathLine(Coordinate xy, int length, DrawFunctions.Direction direction) {
        int x = xy.getX();
        int y = xy.getY();

        switch (direction) {
            case NORTH -> {
                for (int curr = y; curr < y + length; curr++) {
                    Coordinate spot = new Coordinate(world, x, curr);
                    Coordinate leftSpot = new Coordinate(world, x - 1, curr);
                    Coordinate rightSpot = new Coordinate(world, x + 1, curr);


                    if (spot.isAccessible()) {
                        if (world.getWorld()[spot.getX()][spot.getY()] == Tileset.WALL) {
                            destroyedWalls++;
                        }
                    }
                    DrawFunctions.drawSpot(world.getWorld(), spot, Tileset.VERTICALPATH, Tileset.GRASS, 1);
                    disjointRoomIdentification.unionTile(spot);
                }
            }
            case EAST -> {
                for (int curr = x; curr <= x + length; curr++) {
                    Coordinate spot = new Coordinate(world, curr, y);
                    Coordinate upperSpot = new Coordinate(world, curr, y + 1);
                    Coordinate lowerSpot = new Coordinate(world, curr, y - 1);
                    if (spot.isAccessible()) {
                        if (world.getWorld()[spot.getX()][spot.getY()] == Tileset.WALL) {
                            destroyedWalls++;
                        }
                    }
                    DrawFunctions.drawSpot(world.getWorld(), spot, Tileset.HORIZONTALPATH, Tileset.GRASS, 1);
                    disjointRoomIdentification.unionTile(spot);
                    if (curr == x + length) {
                        DrawFunctions.drawSpot(world.getWorld(), spot, Tileset.JUNCTIONPATH, null, 0);
                    }
                }
            }
            case SOUTH -> {
                for (int curr = y; curr > y - length; curr--) {
                    Coordinate spot = new Coordinate(world, x, curr);
                    Coordinate leftSpot = new Coordinate(world, x - 1, curr);
                    Coordinate rightSpot = new Coordinate(world, x + 1, curr);
                    if (spot.isAccessible()) {
                        if (world.getWorld()[spot.getX()][spot.getY()] == Tileset.WALL) {
                            destroyedWalls++;
                        }
                    }
                    DrawFunctions.drawSpot(world.getWorld(), spot, Tileset.VERTICALPATH, Tileset.GRASS, 1);
                    disjointRoomIdentification.unionTile(spot);
                }
            }
            case WEST -> {
                for (int curr = x; curr >= x - length; curr--) {
                    Coordinate spot = new Coordinate(world, curr, y);
                    Coordinate upperSpot = new Coordinate(world, curr, y + 1);
                    Coordinate lowerSpot = new Coordinate(world, curr, y - 1);
                    if (spot.isAccessible()) {
                        if (world.getWorld()[spot.getX()][spot.getY()] == Tileset.WALL) {
                            destroyedWalls++;
                        }
                    }
                    DrawFunctions.drawSpot(world.getWorld(), spot, Tileset.HORIZONTALPATH, Tileset.GRASS, 1);

                    if (curr == x - length) {
                        DrawFunctions.drawSpot(world.getWorld(), spot, Tileset.JUNCTIONPATH, null, 0);
                    }
                    disjointRoomIdentification.unionTile(spot);

                }
            }
            default -> {
            }
        }
    }


    //For loop give coordinates to a bordered rectangle.
    private void drawWallWithRadius(Room room) {
        Coordinate coord = room.getCoord();
        int x = coord.getX();
        int y = coord.getY();
        int xRadius = room.getXRadius();
        int yRadius = room.getYRadius();

        for (int i = x - xRadius - 1; i <= x + xRadius + 1; i++) {
            for (int j = y - yRadius - 1; j <= y + yRadius + 1; j++) {
                if (i == x - xRadius - 1 || j == y - yRadius - 1 || i == x + xRadius + 1 || j == y + yRadius + 1) {
                    DrawFunctions.drawSpot(world.getWorld(), new Coordinate(world, i, j),
                            Tileset.WALL, Tileset.GRASS, 1);
                    DrawFunctions.drawSpot(world.getWorld(), new Coordinate(world, i, j),
                            Tileset.FLOOR, Tileset.HORIZONTALPATH, 1);
                    DrawFunctions.drawSpot(world.getWorld(), new Coordinate(world, i, j),
                            Tileset.FLOOR, Tileset.VERTICALPATH, 1);
                }
            }
        }
    }

    public ArrayList<Room> getRoomsData() {
        return roomsData;
    }

    public int getRoomCount() {
        return roomCount;
    }

    public Room getRoom(int index) {
        return roomsData.get(index);
    }

    public int getDestroyedWalls() {
        return destroyedWalls;
    }

    //Makes RoomsGenerator work with an enhanced for loop, which tbh isn't really necessary LOL.
    @Override
    public Iterator<Room> iterator() {
        return new RoomIterator();
    }

    class RoomIterator implements Iterator<Room> {

        private int index = 0;

        public boolean hasNext() {
            return index < getRoomCount();
        }

        public Room next() {
            return getRoom(index++);
        }
    }
}
