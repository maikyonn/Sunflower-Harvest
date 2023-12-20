package core;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;

public class MusicPlayer {

    private final Queue<String> songQueue;

    public MusicPlayer() {
        songQueue = new LinkedList<>();
        songQueue.add("assets/MyRealm.wav");
        songQueue.add("assets/NowIGo.wav");
    }

    public void playMusic() {
        if (songQueue.isEmpty()) {
            return;
        }

        try {
            String filePath = songQueue.poll(); // Retrieve and remove the head of the queue
            URL soundURL = Main.class.getClassLoader().getResource(filePath);
            if (soundURL == null) {
                throw new IllegalArgumentException("Cannot find file: " + filePath);
            }

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundURL);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();

            // Add a listener to play the next song when the current one ends
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                    playMusic(); // Play the next song
                }
            });

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

}
