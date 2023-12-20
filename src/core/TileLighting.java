package core;

import tileengine.PlayerCoordinates;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.ArrayList;

public class TileLighting {


    ArrayList<TETile> litTiles;
    ArrayList<TETile> shadowTiles;

    public TileLighting() {
        litTiles = new ArrayList<>();
        litTiles.add(Tileset.BOTWALL);
        litTiles.add(Tileset.JUNCTIONPATH);
        litTiles.add(Tileset.FARM);
        litTiles.add(Tileset.HORIZONTALPATH);
        litTiles.add(Tileset.BOTLEFTFENCE);
        litTiles.add(Tileset.BOTRIGHTFENCE);
        litTiles.add(Tileset.TOPLEFTFENCE);
        litTiles.add(Tileset.TOPRIGHTFENCE);
        litTiles.add(Tileset.VERTICALFENCE);
        litTiles.add(Tileset.VERTICALPATH);
        litTiles.add(Tileset.WATER);
        litTiles.add(Tileset.HORIZONTALFENCE);
        litTiles.add(Tileset.FLOOR);
        litTiles.add(Tileset.WALL);
        litTiles.add(Tileset.GRASS);

        shadowTiles = new ArrayList<>();
        shadowTiles.add(Tileset.SPACER);
        shadowTiles.add(Tileset.SHADOWJUNCTIONPATH);
        shadowTiles.add(Tileset.SHADOWFARM);
        shadowTiles.add(Tileset.SHADOWHORIZONTALPATH);
        shadowTiles.add(Tileset.SHADOWBOTLEFTFENCE);
        shadowTiles.add(Tileset.SHADOWBOTRIGHTFENCE);
        shadowTiles.add(Tileset.SHADOWTOPLEFTFENCE);
        shadowTiles.add(Tileset.SHADOWTOPRIGHTFENCE);
        shadowTiles.add(Tileset.SHADOWVERTICALFENCE);
        shadowTiles.add(Tileset.SHADOWVERTICALPATH);
        shadowTiles.add(Tileset.SHADOWWATER);
        shadowTiles.add(Tileset.SHADOWHORIZONTALFENCE);
        shadowTiles.add(Tileset.SHADOWFLOOR);
        shadowTiles.add(Tileset.SHADOWWALL);
        shadowTiles.add(Tileset.SHADOWGRASS);
    }

    public void changeLighting(int lightLevel) {
        for (int i = 0; i < 10; i++) {
            litTiles.get(i).setFilepath("assets/lit" + lightLevel + "/-0" + i + ".png");
        }
        for (int i = 10; i < 15; i++) {
            litTiles.get(i).setFilepath("assets/lit" + lightLevel + "/-" + i + ".png");
        }

        for (int i = 0; i < 10; i++) {
            shadowTiles.get(i).setFilepath("assets/lit10/-0" + i + ".png");
        }
        for (int i = 10; i < 15; i++) {
            shadowTiles.get(i).setFilepath("assets/lit10/-" + i + ".png");
        }
    }

    public void torchLight() {

    }
}
