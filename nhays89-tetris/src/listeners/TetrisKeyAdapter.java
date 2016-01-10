/*
 * TCSS 305 – Autumn 2015 
 * Assignment 6 – Tetris
 */
package listeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;
import model.Board;
import sound.SoundEffect;
import view.BoardQueue;

/**
 * Provides key commands to update the Board's current piece. 
 * 
 * @author Nicholas Hays
 * @version 10 December 2015
 */
public class TetrisKeyAdapter extends KeyAdapter implements Observer, PropertyChangeListener {
    /**
     * Property to check if the game clock has started. 
     */
    private static final String START_GAME_PROPERTY = "startGameClock";
    /**
     * Property to check if the game clock has stopped. 
     */
    private static final String STOP_GAME_PROPERTY = "stopGameClock";
    /**
     * The Tetris Board. 
     */
    private Board myBoard;
    /**
     * The key to move the board's current piece left. 
     */
    private final int myLeftKey;
    /**
     * The key to move the board's current piece right. 
     */
    private final int myRightKey;
    /**
     * The key to move the board's current piece down. 
     */
    private final int myDownKey;
    /**
     * The key to drop the board's current piece. 
     */
    private final int myDropKey;
    /**
     * The key to rotate the board's current piece. 
     */
    private final int myRotateKey;
    /**
     * determines if the game is in progress.
     */
    private boolean myIsPaused;
    
    /**
     * Constructs a tetris key adapter object to manage key controls. 
     */
    public TetrisKeyAdapter() {
        super();
        myLeftKey = KeyEvent.VK_A;
        myRightKey = KeyEvent.VK_F;
        myDownKey = KeyEvent.VK_S;
        myDropKey = KeyEvent.VK_D;
        myRotateKey = KeyEvent.VK_W;
    }
    @Override
    public void keyPressed(final KeyEvent theKey) {
        if (myBoard == null || myBoard.isGameOver() || myIsPaused) {
            return;
        }
        final int keyCode = theKey.getKeyCode();
        if (keyCode == myLeftKey) {
            SoundEffect.MOVE.play();
            myBoard.moveLeft();
        }
        if (keyCode == myRightKey) {
            SoundEffect.MOVE.play();
            myBoard.moveRight();
        }
        if (keyCode == myDownKey) {
            SoundEffect.MOVE.play();
            myBoard.moveDown();
        }
        if (keyCode == myDropKey) {
            SoundEffect.DROP.play();
            myBoard.hardDrop();
        }
        if (keyCode == myRotateKey) {
            SoundEffect.ROTATE.play();
            myBoard.rotate();
        }
    }
    
    @Override
    public void update(final Observable theObservable, final Object theObject) {
        if (theObservable instanceof BoardQueue) {
            final BoardQueue queue = (BoardQueue) theObservable;
            myBoard = queue.getBoard();
        }
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        final String propertyName = theEvent.getPropertyName();
        if (propertyName.equals(START_GAME_PROPERTY)) {
            myIsPaused = false;
        }
        if (propertyName.equals(STOP_GAME_PROPERTY)) {
            myIsPaused = true;
        }
    }
}
