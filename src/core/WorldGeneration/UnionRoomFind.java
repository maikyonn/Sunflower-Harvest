package core.WorldGeneration;

import core.Main;

public class UnionRoomFind {
    World world;
    private final int[] data;

    public UnionRoomFind(World w) {
        world = w;
        int size = Main.MAP_X_LEN * Main.MAP_Y_LEN;
        data = new int[size];

        for (int i = 0; i < size; i++) {
            data[i] = -1;
        }
    }

    public void unionTileWithTwoCoords(Coordinate roomCoords, Coordinate coord) {
        if (coord.isAccessible() && roomCoords.isAccessible()) {
            int roomIndex = getIndexFromCoordinate(roomCoords);
            int index = getIndexFromCoordinate(coord);

            union(roomIndex, index);
        }
    }

    public void unionTile(Coordinate coord) {
        if (coord.isAccessible()) {
            int originIndex = getIndexFromCoordinate(world.getORIGIN());
            int index = getIndexFromCoordinate(coord);

            union(originIndex, index);
        }
    }

    public boolean checkCoordIsConnectedToOriginRoom(Coordinate coord) {
        if (coord.isAccessible()) {
            return connected(getIndexFromCoordinate(world.getORIGIN()), getIndexFromCoordinate(coord));
        }
        return false;
    }

    public int sizeOf(int v) {
        int find = find(v);
        int parent = parent(find);
        return parent * -1;
    }

    public void unionRoomToCoordinates(Room rm, int x, int y) {
        // System.out.println("Unioning: " + rm.getX() + ", " + rm.getY() + " | " + x + ", " + y);
        union(coord2Dto1D(rm.getX(), rm.getY()), coord2Dto1D(x, y));
    }

    public int getNumLargeRoots() {
        int count = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i] < -1) {
                count++;
            }
        }
        return count;
    }

    public int getValueAtCoordinate(int x, int y) {
        return data[coord2Dto1D(x, y)];
    }

    public int getIndexFromCoordinate(Coordinate xy) {
        return coord2Dto1D(xy.getX(), xy.getY());
    }

    private int coord2Dto1D(int x, int y) {
        return y * Main.MAP_X_LEN + x;
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        return data[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        if (v > data.length || v < 0) {
            throw new IllegalArgumentException();
        }
        int root = v;
        while (parent(root) >= 0) {
            root = parent(root);
        }

        //Path Compression
        while (v != root) {
            int temp = data[v];
            data[v] = root;
            v = temp;
        }

        return root;
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing a item with itself or items that are
       already connected should not change the structure. */

    public void union(int v1, int v2) {
        int firstRoot = find(v1);
        int secondRoot = find(v2);
        if (firstRoot == secondRoot) {
            return;
        }

        if (-data[firstRoot] < -data[secondRoot] || data[firstRoot] == data[secondRoot]) {
            data[secondRoot] += data[firstRoot];
            data[firstRoot] = secondRoot;
            return;
        }
        data[firstRoot] += data[secondRoot];
        data[secondRoot] = firstRoot;
    }

    /**
     * DO NOT DELETE OR MODIFY THIS, OTHERWISE THE TESTS WILL NOT PASS.
     */
    public int[] returnData() {
        return data;
    }
}
