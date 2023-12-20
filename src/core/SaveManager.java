package core;

import java.io.*;
import java.util.List;
import java.util.Stack;

public class SaveManager {
    private final Stack<Save> loadedSaves;

    private final String filepath = "testsave.txt";

    public SaveManager() {
        loadedSaves = new Stack<>();
        // Load in save objects from a file
    }

    public void loadSavesFromFile() {
        File savedata = new File(filepath);
        if (savedata.exists() && savedata.canRead() && savedata.isFile()) {
            try (BufferedReader fileRead = new BufferedReader(new FileReader(savedata))) {
                String currLine = fileRead.readLine();
                int lineCounter = 0;
                while (currLine != null) { // reads each line while a new line exists
                    lineCounter++;
                    Save currSave = parseSaveFromString(currLine);
                    if (currSave != null) {
                        loadedSaves.push(currSave);
                    }
                    currLine = fileRead.readLine();
                }
            } catch (IOException error) {
                error.getStackTrace();
            }
        }
    }

    private Save parseSaveFromString(String currLine) {
        String[] storage = currLine.split(" ", 2);
        long seed = Long.parseLong(storage[0]);
        UserInput userInput = new UserInput();

        if (storage.length > 1) { // if the user inputted a seed
            for (char ch : storage[1].toCharArray()) {
                userInput.add(ch);
            }
        }
        return new Save(seed, userInput);
    }

    public void saveLoadedSavesToFile() {
        try (BufferedWriter fileWrite = new BufferedWriter(new FileWriter(filepath))) {
            for (Save currSave : loadedSaves) {
                fileWrite.write(currSave.getSeed() + " " + currSave.getGameInputString());
                fileWrite.newLine();
            }
        } catch (IOException error) {
            error.getStackTrace();
        }
    }

    public void saveGame(Game game) {
        Save saveObj = game.saveGame();
        this.addSave(saveObj);

    }

    public Save getLatestSave() {
        if (loadedSaves.size() != 0) {
            return loadedSaves.peek();
        }
        return null;
    }

    public Save getSaveIndex(int index) {
        return loadedSaves.get(index);
    }

    private void addSave(Save newSave) {
        loadedSaves.add(newSave);
    }

    public Stack<Save> getLoadedSaves() {
        return loadedSaves;
    }

    public List<Save> get5LoadedSaves() {
        if (loadedSaves.size() < 6) {
            return loadedSaves;
        }
        return loadedSaves.subList(loadedSaves.size() - 5, loadedSaves.size());
    }

    public void printSaves() {
        System.out.println("Printing Saves");
        for (Save save : loadedSaves) {
            System.out.println(save);
        }
    }
}

