package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;

import java.awt.*;

public class Menu {

    private final Font fontSaveSelected = new Font("System", Font.BOLD, 10);
    private final Font fontSaveNotSelected = new Font("System", Font.PLAIN, 10);
    private final TERenderer menuRenderer;
    private final BackgroundProcess bg;
    private final MusicPlayer musicPlayer;
    private final SaveManager saveManager;
    private final boolean gameActive;
    private MenuStates menuState;
    private UserInput userMenuInput;
    private double scrollSpeed;
    private Save loadedSave;

    public Menu() {
        menuRenderer = new TERenderer();
        menuRenderer.initialize(57, 39);
        bg = new BackgroundProcess();
        scrollSpeed = 0;

        musicPlayer = new MusicPlayer();
        musicPlayer.playMusic();

        menuState = MenuStates.MAINMENU;
        userMenuInput = new UserInput();

        saveManager = new SaveManager();
        loadedSave = null;
        gameActive = true;
    }

    public void start() {
        saveManager.loadSavesFromFile();
        menuRenderLoop();
    }

    public void menuRenderLoop() {
        while (gameActive) {
            switch (menuState) {
                case MAINMENU -> displayMenu();
                case NEWWORLDSEEDINPUT -> getSeedInput();
                case ACTIVEGAME -> gamePlay();
                case LOADWORLD -> loadWorldMenu(false);
                case QUICKLOADWORLD -> loadWorldMenu(true);
                default -> {
                    System.exit(0);
                }
            }
        }
    }

    public void displayMenu() {
        bg.bufferMainMenu();
        bg.showLoadingScreen();
        bg.nextFrame(scrollSpeed);
        scrollSpeed += 0.05;
        System.out.println(scrollSpeed);
        StdDraw.show();

        if (StdDraw.hasNextKeyTyped()) {
            char key = StdDraw.nextKeyTyped();
            key = Character.toLowerCase(key);
            switch (key) {
                case 'n', 'N' -> {
                    userMenuInput.add(key);
                    menuState = MenuStates.NEWWORLDSEEDINPUT;
                    return;
                }
                case 's', 'S' -> {
                    userMenuInput.add(key);
                    menuState = MenuStates.LOADWORLD;
                    return;
                }
                case 'l', 'L' -> {
                    userMenuInput.add(key);
                    menuState = MenuStates.QUICKLOADWORLD;
                    return;
                }
                case 'q', 'Q' -> {
                    userMenuInput.add(key);
                    menuState = MenuStates.EXIT;
                    return;
                }
                default -> {
                }
            }
        }

        menuState = MenuStates.MAINMENU;
    }

    public void getSeedInput() {
        char key = ' ';
        while (key != '\n') {
            bg.bufferSeeenu();
            bg.nextFrame(scrollSpeed);
            StdDraw.text(Main.MAP_X_LEN * 0.25, Main.MAP_Y_LEN * 0.6, userMenuInput.toString());
            StdDraw.show();

            if (StdDraw.hasNextKeyTyped()) {
                key = StdDraw.nextKeyTyped();
                userMenuInput.add(key);
                if (key == '\n' || key == 'S' || key == 's') {
                    key = '\n';
                    loadedSave = new Save(WorldGenerator.getWorldFromInput(userMenuInput.toString()));
                    menuState = MenuStates.ACTIVEGAME;
                }
                if (key == 'q' || key == 'Q') {
                    userMenuInput = new UserInput();
                    key = '\n';
                    menuState = MenuStates.MAINMENU;
                }
                StdDraw.clear();
            }
        }
    }

    public void gamePlay() {
        Game game = new Game(loadedSave);
        game.createRenderer();
        boolean rageQuit = game.gameLoop();

        saveManager.saveGame(game);
        saveManager.saveLoadedSavesToFile();

        if (rageQuit) {
            System.exit(0);
        }
        bg.setHasShownLoading();
        userMenuInput = new UserInput();
        menuRenderer.initialize(Main.MAP_X_LEN, Main.MAP_Y_LEN);
        menuState = MenuStates.MAINMENU;
    }

    public void loadWorldMenu(boolean quick) {
        if (quick) {
            loadedSave = saveManager.getLatestSave();
            menuState = MenuStates.ACTIVEGAME;
            return;
        }
        char key = ' ';
        int currentlySelectedSave = 0;
        while (key != '\n') {
            bg.bufferLoadMenu();
            bg.nextFrame(scrollSpeed);
            double yPos = Main.MAP_Y_LEN * 0.6;
            int saveNum = 0;
            for (Save save : saveManager.getLoadedSaves()) {
                if (currentlySelectedSave == saveNum) {
                    StdDraw.setFont(fontSaveSelected);
                    StdDraw.text(Main.MAP_X_LEN * 0.25, yPos, save.toStringDisplay());
                } else {
                    StdDraw.setFont(fontSaveNotSelected);
                    StdDraw.text(Main.MAP_X_LEN * 0.25, yPos, save.toStringDisplay());
                }
                yPos -= 2;
                saveNum += 1;
            }

            StdDraw.show();
            if (StdDraw.hasNextKeyTyped()) {
                key = StdDraw.nextKeyTyped();
                userMenuInput.add(key);
                switch (key) {
                    case 'w' -> {
                        if (currentlySelectedSave == 0) {
                            break;
                        }
                        currentlySelectedSave -= 1;
                    }
                    case 's' -> {
                        if (currentlySelectedSave >= saveManager.get5LoadedSaves().size()) {
                            break;
                        }
                        currentlySelectedSave += 1;
                    }
                    case 'q', 'Q' -> {
                        userMenuInput = new UserInput();
                        key = '\n';
                        menuState = MenuStates.MAINMENU;
                        return;
                    }

                    default -> {
                    }
                }
                StdDraw.clear();
            }
        }
        //Just load the data into Save objects from the file. Don't worry about running the data.
        loadedSave = saveManager.getSaveIndex(currentlySelectedSave);
        menuState = MenuStates.ACTIVEGAME;
    }

    private enum MenuStates {
        MAINMENU, NEWWORLDSEEDINPUT, ACTIVEGAME, QUICKLOADWORLD, LOADWORLD, EXIT
    }
}
