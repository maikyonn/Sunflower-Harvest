package tileengine;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 *
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 *
 * Ex:
 *      world[x][y] = Tileset.FLOOR;
 *
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */

public class Tileset {

    public static final TETile FLOOR = new TETile('·', new Color(128, 192, 128), Color.black,
            "floor", "assets/shadowfloor.png");
    public static final TETile GRASS = new TETile('"', Color.green, Color.black, "grass",
            "assets/lit1/-17.png");
    public static final TETile WATER = new TETile('≈', Color.blue, Color.black, "water",
            "assets/water.png");
    public static final TETile WALL = new TETile('W', new Color(216, 128, 128), Color.darkGray,
            "wall", "assets/wall.png");
    public static final TETile BOTWALL = new TETile('#', new Color(216, 128, 128), Color.darkGray,
            "wall", "assets/botfacingwall.png");
    public static final TETile HORIZONTALFENCE = new TETile('"', Color.green, Color.black, "fence",
            "assets/horizontalfence.png");
    public static final TETile VERTICALFENCE = new TETile('"', Color.green, Color.black, "fence",
            "assets/verticalfence.png");
    public static final TETile TOPLEFTFENCE = new TETile('"', Color.green, Color.black, "fence",
            "assets/topleftfence.png");
    public static final TETile TOPRIGHTFENCE = new TETile('"', Color.green, Color.black, "fence",
            "assets/toprightfence.png");
    public static final TETile BOTLEFTFENCE = new TETile('"', Color.green, Color.black, "fence",
            "assets/leftbotfence.png");
    public static final TETile BOTRIGHTFENCE = new TETile('"', Color.green, Color.black, "fence",
            "assets/rightbotfence.png");
    public static final TETile VERTICALPATH = new TETile('"', Color.green, Color.black, "path",
            "assets/verticalpath.png");
    public static final TETile HORIZONTALPATH = new TETile('"', Color.green, Color.black, "path",
            "assets/horizontalpath.png");
    public static final TETile JUNCTIONPATH = new TETile('"', Color.green, Color.black, "path",
            "assets/cornerpath.png");
    public static final TETile FARM = new TETile('"', Color.green, Color.black, "farm",
            "assets/farm4.png");

    public static final TETile SPACER = new TETile('"', Color.green, Color.black, "grass",
            "assets/farm4.png");

    public static final TETile SHADOWGRASS = new TETile('"', Color.green, Color.black, "grass",
            "assets/shadowgrass.png");
    public static final TETile SHADOWVERTICALPATH = new TETile('"', Color.green, Color.black, "path",
            "assets/shadowverticalpath.png");
    public static final TETile SHADOWHORIZONTALPATH = new TETile('"', Color.green, Color.black, "path",
            "assets/shadowhorizontalpath.png");
    public static final TETile SHADOWJUNCTIONPATH = new TETile('"', Color.green, Color.black, "path",
            "assets/shadowcornerpath.png");
    public static final TETile SHADOWFARM = new TETile('"', Color.green, Color.black, "farm",
            "assets/shadowfarm4.png");

    public static final TETile SHADOWWATER = new TETile('"', Color.green, Color.black, "water",
            "assets/shadowfarm4.png");
    public static final TETile SHADOWFLOOR = new TETile('"', Color.green, Color.black, "floor",
            "assets/shadowfarm4.png");
    public static final TETile SHADOWWALL = new TETile('"', Color.green, Color.black, "wall",
            "assets/shadowfarm4.png");
    public static final TETile SHADOWHORIZONTALFENCE = new TETile('"', Color.green, Color.black, "fence",
            "assets/shadowhorizontalfence.png");
    public static final TETile SHADOWVERTICALFENCE = new TETile('"', Color.green, Color.black, "fence",
            "assets/shadowverticalfence.png");
    public static final TETile SHADOWTOPLEFTFENCE = new TETile('"', Color.green, Color.black, "fence",
            "assets/shadowtopleftfence.png");
    public static final TETile SHADOWTOPRIGHTFENCE = new TETile('"', Color.green, Color.black, "fence",
            "assets/shadowtoprightfence.png");
    public static final TETile SHADOWBOTLEFTFENCE = new TETile('"', Color.green, Color.black, "fence",
            "assets/shadowbotlefence.png");
    public static final TETile SHADOWBOTRIGHTFENCE = new TETile('"', Color.green, Color.black, "fence",
            "assets/shadowbotrightfence.png");
}


