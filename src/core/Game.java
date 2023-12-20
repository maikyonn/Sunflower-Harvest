package core;

import core.WorldGeneration.Coordinate;
import core.WorldGeneration.World;
import edu.princeton.cs.algs4.StdDraw;
import tileengine.*;

public class Game {
    private final World loadedWorld;
    private final UserInput loadedGameInput;
    private final UserInput currentGameInput;
    private final PlayerCoordinates playerPosition;
    private final CameraCoordinates cameraPosition;
    private TERenderer playerRender;
    private boolean isAutomatedMovement;
    private int score;
    private boolean quitDetector;
    private boolean quitDetected;


    public Game(Save loadedSave) {
        loadedGameInput = loadedSave.getGameInput();
        currentGameInput = new UserInput();
        currentGameInput.addAll(loadedGameInput);
        quitDetector = false;

        this.loadedWorld = loadedSave.getWorld();
        cameraPosition = new CameraCoordinates(loadedWorld, 15, 15);
        playerPosition = new PlayerCoordinates(cameraPosition, loadedWorld, 0, 0);
    }

    public void createRenderer() {
        playerRender = new TERenderer();
        playerRender.initializePlayerPerspectve(Main.WINDOW_X_LEN, Main.WINDOW_Y_LEN);

        score = 0;
        isAutomatedMovement = true;
    }

    public void loadSavedInput() {
        for (char actionKey : loadedGameInput) {
            inputHandler(actionKey);
            playerRender.renderPlayerFrame(loadedWorld, cameraPosition, playerPosition, score, Tileset.GRASS);
            StdDraw.pause(50);
        }
    }

    public void loadSavedInputAuto(UserInput input) {
        currentGameInput.addAll(input);
    }

    public boolean gameLoop() {
        playerRender.renderPlayerFrame(loadedWorld, cameraPosition, playerPosition, score, Tileset.GRASS);

        loadSavedInput();
        isAutomatedMovement = false;
        manualInputLoop();
        StdDraw.clear();
        if (quitDetected) {
            return true;
        }
        return false;
    }

    public void manualInputLoop() {
        char key = ' ';
        TETile prevTile = null;
        while (key != 'S' && !quitDetected) {
            TETile cursorTile = new Coordinate(loadedWorld, (int) StdDraw.mouseX() + cameraPosition.getX(),
                    (int) StdDraw.mouseY() + cameraPosition.getY()).getTile();
            if (cursorTile != null && cursorTile != prevTile) {
                prevTile = cursorTile;
                playerRender.renderPlayerFrame(loadedWorld, cameraPosition, playerPosition, score, cursorTile);
            }

            if (StdDraw.hasNextKeyTyped()) {
                key = StdDraw.nextKeyTyped();
                inputHandler(key);
                playerRender.renderPlayerFrame(loadedWorld, cameraPosition, playerPosition, score, cursorTile);
                printGameInfo();
            }
        }
    }

    private void inputHandler(char input) {
        switch (input) {
            case 'S' -> saveGame();
            case ':' -> setQuitDetector();
            case 'Q' -> quitGame();
            case 'w' -> inputUp();
            case 'a' -> inputLeft();
            case 's' -> inputDown();
            case 'd' -> inputRight();
            case 'p' -> pick();
            default -> {
            }
        }
    }

    public void quitGame () {
        if (quitDetector) {
            quitDetected = true;
        }
    }
    public void setQuitDetector() {
        quitDetector = true;
    }

    private void printGameInfo() {
        System.out.println("\nInput #" + currentGameInput.size() + " Score: " + score);
        System.out.println("Rendering camera centered at: " + cameraPosition.toString());
        System.out.println("Rendering player centered at: " + playerPosition.toString());
        System.out.println("Rendering true player centered at: "
                + playerPosition.getTruePlayerCoordinates().toString());
        System.out.println("Game Input: " + currentGameInput);
    }

    public Save saveGame() {
        Save currentSave = new Save(loadedWorld.getSeed(), currentGameInput);
        return currentSave;

        //1. Save thesee two inputs into a file. The first is the seed and 2nd is the player input as shown above
        //4 lines of the file should look like
        // 1234123123 pawdasdwaapawpppppawdsp
        // 7571248708 pawdasdwaapawpppppawdsp
        // 413455221 ppwwep
        // 3123
        //      ^ empty string

        //2. In the main function, write a way to load the file through the function loadWorldMenu()
    }

    public void pick() {
        PlayerCoordinates pos = playerPosition.getTruePlayerCoordinates();
        if (pos.isPickable()) {
            pos.setTile(Tileset.GRASS);
            score++;
            currentGameInput.add('p');
        }
        quitDetector = false;
    }

    private void inputUp() {
        quitDetector = false;
        boolean movementSuccess = false;
        if (playerPosition.canMoveUp()) {
            if (playerPosition.getY() != 0) {
                if (playerPosition.canMoveUp()) {
                    playerPosition.moveUp();
                    movementSuccess = true;
                }
            } else {
                if (cameraPosition.canMoveUp()) {
                    cameraPosition.moveUp();
                    movementSuccess = true;
                } else {
                    if (playerPosition.canMoveUp()) {
                        playerPosition.moveUp();
                        movementSuccess = true;
                    }
                }
            }
        }

        if (movementSuccess && !isAutomatedMovement) {
            currentGameInput.add('w');
        }
    }

    private void inputDown() {
        quitDetector = false;
        boolean movementSuccess = false;
        if (playerPosition.canMoveDown()) {
            if (playerPosition.getY() != 0) {
                if (playerPosition.canMoveDown()) {
                    playerPosition.moveDown();
                    movementSuccess = true;
                }
            } else {
                if (cameraPosition.canMoveDown()) {
                    cameraPosition.moveDown();
                    movementSuccess = true;
                } else {
                    if (playerPosition.canMoveDown()) {
                        playerPosition.moveDown();
                        movementSuccess = true;
                    }
                }
            }
        }
        if (movementSuccess && !isAutomatedMovement) {
            currentGameInput.add('s');
        }

    }

    private void inputLeft() {
        quitDetector = false;
        boolean movementSuccess = false;
        if (playerPosition.canMoveLeft()) {
            if (playerPosition.getX() != 0) {
                playerPosition.moveLeft();
                movementSuccess = true;
            } else {
                if (cameraPosition.canMoveLeft()) {
                    cameraPosition.moveLeft();
                    movementSuccess = true;
                } else {
                    playerPosition.moveLeft();
                    movementSuccess = true;
                }
            }
        }
        if (movementSuccess && !isAutomatedMovement) {
            currentGameInput.add('a');
        }

    }

    private void inputRight() {
        quitDetector = false;
        boolean movementSuccess = false;
        if (playerPosition.canMoveRight()) {
            if (playerPosition.getX() != 0) {
                if (playerPosition.canMoveRight()) {
                    playerPosition.moveRight();
                    movementSuccess = true;
                }
            } else {
                if (cameraPosition.canMoveRight()) {
                    cameraPosition.moveRight();
                    movementSuccess = true;
                } else {
                    if (playerPosition.canMoveRight()) {
                        playerPosition.moveRight();
                        movementSuccess = true;
                    }
                }
            }
        }
        if (movementSuccess && !isAutomatedMovement) {
            currentGameInput.add('d');
        }
    }

    public World getLoadedWorld() {
        return loadedWorld;
    }
}
