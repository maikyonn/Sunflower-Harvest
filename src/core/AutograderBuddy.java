package core;

import core.WorldGeneration.World;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutograderBuddy {

    /**
     * Simulates a game, but doesn't render anything or call any StdDraw
     * methods. Instead, returns the world that would result if the input string
     * had been typed on the keyboard.
     * <p>
     * Recall that strings ending in ":q" should cause the game to quit and
     * save. To "quit" in this method, save the game to a file, then just return
     * the TETile[][]. Do not call System.exit(0) in this method.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public static TETile[][] getWorldFromInput(String input) {
        System.out.println(input);
        String inputLower = input.toLowerCase();
        Pattern preSeedPattern = Pattern.compile("^[a-z]");
        Pattern seedPattern = Pattern.compile("[a-z](\\d+)");
        Pattern postSeedPattern = Pattern.compile("(?<=\\ds)[a-z]+");
        Pattern postLoadPattern = Pattern.compile("(?<=l)[a-z]+");
        SaveManager saveManager = new SaveManager();
        saveManager.loadSavesFromFile();

        Matcher preSeedMatcher = preSeedPattern.matcher(inputLower);

        String menuOption = "n";
        if (preSeedMatcher.find()) {
            menuOption = preSeedMatcher.group(0);
        }

        UserInput savedUserInput = new UserInput();
        long seed = 0;
        Save loadedSave = new Save(seed, savedUserInput);
        Game loadedGame = new Game(loadedSave);

        switch (menuOption) {
            case "n" -> {
                Matcher seedMatcher = seedPattern.matcher(inputLower);
                if (seedMatcher.find()) {
                    String seedString = seedMatcher.group(1);
                    seed = Long.parseLong(seedString);
                }

                Matcher postSeedMatcher = postSeedPattern.matcher(inputLower);
                savedUserInput = new UserInput();
                if (postSeedMatcher.find()) {
                    for (char c : postSeedMatcher.group(0).toCharArray()) {
                        savedUserInput.add(c);
                    }
                }
                loadedSave = new Save(seed, savedUserInput);
                loadedGame = new Game(loadedSave);

                saveManager.saveGame(loadedGame);
                saveManager.saveLoadedSavesToFile();
            }
            case "l" -> {
                System.out.println("Loading: ");
                Matcher postLoadMatcher = postLoadPattern.matcher(inputLower);
                savedUserInput = new UserInput();
                if (postLoadMatcher.find()) {
                    for (char c : postLoadMatcher.group(0).toCharArray()) {
                        savedUserInput.add(c);
                    }
                }
                loadedSave = saveManager.getLatestSave();
                saveManager.printSaves();
                if (loadedSave == null) {
                    return null;
                }
                loadedGame = new Game(loadedSave);
                loadedGame.loadSavedInputAuto(savedUserInput);

                saveManager.saveGame(loadedGame);
                saveManager.saveLoadedSavesToFile();
                saveManager.printSaves();
            }
            default -> {

            }
        }
        World completedActionsWorld = loadedGame.getLoadedWorld();
        return completedActionsWorld.getWorld();
    }


    /**
     * Used to tell the autograder which tiles are the floor/ground (including
     * any lights/items resting on the ground). Change this
     * method if you add additional tiles.
     */
    public static boolean isGroundTile(TETile t) {
        return t.character() == Tileset.FLOOR.character()
                || t.character() == Tileset.GRASS.character()
                || t.character() == Tileset.WATER.character()
                || t.character() == Tileset.VERTICALPATH.character()
                || t.character() == Tileset.HORIZONTALPATH.character()
                || t.character() == Tileset.JUNCTIONPATH.character()
                || t.character() == Tileset.FARM.character()
                || t.character() == Tileset.SHADOWGRASS.character()
                || t.character() == Tileset.SHADOWVERTICALPATH.character()
                || t.character() == Tileset.SHADOWHORIZONTALPATH.character()
                || t.character() == Tileset.SHADOWJUNCTIONPATH.character()
                || t.character() == Tileset.SHADOWFARM.character();
    }

    /**
     * Used to tell the autograder while tiles are the walls/boundaries. Change
     * this method if you add additional tiles.
     */
    public static boolean isBoundaryTile(TETile t) {
        return t.character() == Tileset.WALL.character()
                || t.character() == Tileset.BOTWALL.character()

                || t.character() == Tileset.VERTICALFENCE.character()
                || t.character() == Tileset.HORIZONTALFENCE.character()
                || t.character() == Tileset.TOPLEFTFENCE.character()
                || t.character() == Tileset.TOPRIGHTFENCE.character()
                || t.character() == Tileset.BOTLEFTFENCE.character()
                || t.character() == Tileset.BOTRIGHTFENCE.character()
                || t.character() == Tileset.SHADOWTOPRIGHTFENCE.character()
                || t.character() == Tileset.SHADOWTOPLEFTFENCE.character()
                || t.character() == Tileset.SHADOWBOTRIGHTFENCE.character()
                || t.character() == Tileset.SHADOWBOTLEFTFENCE.character()
                || t.character() == Tileset.SHADOWVERTICALFENCE.character()
                || t.character() == Tileset.SHADOWHORIZONTALFENCE.character();
    }
}
