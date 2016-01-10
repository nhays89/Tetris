/*
 * TCSS 305 – Autumn 2015 
 * Assignment 6 – Tetris
 */
package view;

import java.util.Observable;
import java.util.Observer;
/**
 * A Queue that stores all the observers for the Scale Dialog.
 * @author Nick
 * @version 10 December 2015
 */
public class ScaleDialogQueue extends Observable implements Observer {
    /**
     * The board to listen to. 
     */
    private ScaleDialog myScaleDialog;
 
    /**
     * Allows its observers to have their copy of the current board. 
     * @return the board to observe. 
     */
    public ScaleDialog getScaleDialog() {
        return myScaleDialog;
    }

    @Override
    public void update(final Observable theObservable, final Object arg1) {
        if (theObservable instanceof ScaleDialog) {
            myScaleDialog = (ScaleDialog) theObservable;
            setChanged();
            notifyObservers();
        }
    }
}
