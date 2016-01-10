/*
 * TCSS 305 – Autumn 2015 
 * Assignment 6 – Tetris
 */

package sound;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Class that loads sound to the Tetris GUI.
 * 
 * @author Nicholas A. Hays
 * @version 11 December 2015
 */
public enum SoundEffect {
    /**
     * Sound for piece movement right or left.
     */
    MOVE("move.wav"),
    /**
     * Sound for line cleared.
     */
    SCORE("score.wav"),
    /**
     * Sound for rotate current piece.
     */
    ROTATE("rotate.wav"),
    /**
     * Sound for drop on the current piece.
     */
    DROP("drop.wav"),
    /**
     * Level up.
     */
    LEVELUP("levelup.wav"),
    /**
     * Game Over.
     */
    GAMEOVER("gameover.wav"),
    /**
     * Pause game.
     */
    PAUSE("pause.wav");
    /**
     * Volume Control.
     * 
     * @author Nicholas A. Hays
     * @version 11 December 2015
     */
    public enum Volume {
        /**
         * The different volume levels.
         */
        MUTE, LOW, MEDIUM, HIGH
    }

    /**
     * Default volume.
     */
    private static Volume volume = Volume.HIGH;

    /**
     * Each sound effect has its own clip, loaded with its own sound file.
     */
    private Clip myClip;

    /**
     * Constructor to construct each element of the enum with its own sound
     * file.
     * 
     * @param theSoundFileName the Sound File name.
     */
    SoundEffect(final String theSoundFileName) {
        try {
            final URL url = this.getClass().getClassLoader().getResource(theSoundFileName);
            final AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            myClip = AudioSystem.getClip();
            myClip.open(audioInputStream);
        } catch (final UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        } catch (final LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Play or Re-play the sound effect from the beginning, by rewinding.
     */
    public void play() {
        if (getVolume() != Volume.MUTE) {
            if (myClip.isRunning()) {
                myClip.stop();
            }
            myClip.setFramePosition(0);
            myClip.start();
        }
    }

    /**
     * Optional static method to pre-load all the sound files.
     */
    public static void init() {
        values();
    }

    /**
     * Gets the Volume of this clip.
     * 
     * @return the Volume level.
     */
    public static Volume getVolume() {
        return volume;
    }

    /**
     * Sets the Volume of this clip.
     * 
     * @param theVolume the Volume.
     */
    public static void setVolume(final Volume theVolume) {
        SoundEffect.volume = theVolume;
    }
}
