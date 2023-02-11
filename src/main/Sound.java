package main;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
    public static String[] soundFiles = new String[10];

    private Clip clip;
    public Sound() {
        clip = null;
        soundFiles[0] = "res/sounds/BlueBoyAdventure.wav";
        soundFiles[1] = "res/sounds/coin.wav";
        soundFiles[2] = "res/sounds/dooropen.wav";
        soundFiles[3] = "res/sounds/fanfare.wav";
        soundFiles[4] = "res/sounds/powerup.wav";
    }
    public void setFile(String fileName) {
        try {
            File soundFile = new File(fileName);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void play() {
        if (clip != null) {
            clip.start();
        }
    }
    public void loop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
    public void stop() {
        if (clip != null) {
            clip.stop();
        }
    }
}
