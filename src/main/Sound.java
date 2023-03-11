package main;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
    int size = 4;
    Clip[] clip = new Clip[size];
    URL[] soundURL = new URL[size];
    boolean[] play= new boolean[size];


    public Sound() {
        soundURL[0] = getClass().getResource("/music/Song.wav");
        soundURL[1] = getClass().getResource("/music/cursor.wav");
        soundURL[2] = getClass().getResource("/music/gameover2.wav");
        soundURL[3] = getClass().getResource("/music/pause.wav");
    }

    public void setFile(int i) throws LineUnavailableException, UnsupportedAudioFileException {

        try {

            AudioInputStream aid = AudioSystem.getAudioInputStream(soundURL[i]);
            clip[i] = AudioSystem.getClip();
            clip[i].open(aid);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void play(int i) {
        clip[i].start();
        play[i] = true;

    }
    public void loop(int i) {
        clip[i].loop(Clip.LOOP_CONTINUOUSLY );
    }
    public void stop(int i ) {
        clip[i].stop();
        play[i] = false;

    }
}
