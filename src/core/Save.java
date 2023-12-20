package core;

import core.WorldGeneration.World;

public class Save {
    private final long seed;
    private final UserInput gameInput;
    private final World world;

    public Save(long seed, UserInput gameInput) {
        this.seed = seed;
        this.gameInput = gameInput;
        this.world = WorldGenerator.createWorldFromSeed(seed);
    }

    public Save(World world) {
        this.world = world;
        this.seed = world.getSeed();
        this.gameInput = new UserInput();
    }

    @Override
    public String toString() {
        return "Save: SEED:" + seed + " User Input: " + gameInput;
    }

    public String toStringDisplay() {
        if (gameInput.size() > 10) {
            return "SEED:" + seed + " User Input: " + gameInput.subList(gameInput.size() - 10, gameInput.size());
        }
        return "SEED:" + seed + " User Input: " + gameInput;
    }

    public void appendInput(UserInput input) {
        gameInput.addAll(input);
    }

    public long getSeed() {
        return seed;
    }

    public UserInput getGameInput() {
        return gameInput;
    }

    public String getGameInputString() {
        return gameInput.toString();
    }

    public World getWorld() {
        return world;
    }
}
