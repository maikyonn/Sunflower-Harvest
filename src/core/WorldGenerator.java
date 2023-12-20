package core;

import core.WorldGeneration.World;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WorldGenerator {
    public static World createWorldFromSeed(long seed) {
        return new World(seed);
    }

    public static World getWorldFromInput(String input) {
        String inputLower = input.toLowerCase();
        Pattern preSeedPattern = Pattern.compile("^[a-z]");
        Pattern seedPattern = Pattern.compile("[a-z](\\d+)");
        Pattern postSeedPattern = Pattern.compile("(?<=\\d)[a-z]+");
        long seed = 0;

        Matcher preSeedMatcher = preSeedPattern.matcher(inputLower);
        if (preSeedMatcher.find()) {
            System.out.println(preSeedMatcher.group(0));
        }

        Matcher seedMatcher = seedPattern.matcher(inputLower);
        if (seedMatcher.find()) {
            String seedString = seedMatcher.group(1);
            seed = Long.parseLong(seedString);
        }

        Matcher postSeedMatcher = postSeedPattern.matcher(inputLower);
        if (postSeedMatcher.find()) {
            System.out.println(postSeedMatcher.group(0));
        }

        World generatedWorld = WorldGenerator.createWorldFromSeed(seed);
        System.out.println(input + "Getting world with seed " + seed);
        return generatedWorld;
    }

    public static World createInterestingWorldFromSeed(long seed) {
        int interestingTiles = 0;
        World world = new World(seed);
        while (seed < Long.MAX_VALUE) {
            while (interestingTiles < 1000) {
                interestingTiles = 0;
                world = new World(seed);
                for (TETile[] tileRow : world.getWorld()) {
                    for (TETile tile : tileRow) {
                        if (tile != Tileset.GRASS) {
                            interestingTiles++;
                        }
                    }
                }
                System.out.println("Seed: " + seed + " | Interesting tiles: " + interestingTiles);
                seed++;
            }
            System.out.println("This world seems interesting! Interesting tiles: " + interestingTiles);
            interestingTiles = 0;
            return world;

        }
        return world;
    }

    public static World createInterestingWorld(TERenderer renderer) {
        int interestingTiles = 0;
        long seed = 100;
        World world = new World(seed);

        while (seed < 1000000000) {
            while (interestingTiles < 500) {
                interestingTiles = 0;
                world = new World(seed);
                for (TETile[] tileRow : world.getWorld()) {
                    for (TETile tile : tileRow) {
                        if (tile != Tileset.GRASS) {
                            interestingTiles++;
                        }
                    }
                }
                System.out.println("Seed: " + seed + " | Interesting tiles: " + interestingTiles);
                seed++;
            }
            System.out.println("This world seems interesting! Interesting tiles: " + interestingTiles);
            Main.renderWorldBrowse(renderer, world.getWorld());
            seed++;
            interestingTiles = 0;
        }

        return world;
    }
}
