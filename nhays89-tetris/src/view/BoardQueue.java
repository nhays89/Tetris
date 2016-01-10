/*
 * TCSS 305 – Autumn 2015 
 * Assignment 6 – Tetris
 */
package view;

import java.util.Observable;
import java.util.Observer;

import model.Board;
/**
 * Provides a hub for the Tetris Board object to manage its list of observers.
 * 
 * @author Nicholas Hays
 * @version 3 December 2015
 */
public class BoardQueue extends Observable implements Observer {
    /**
     * The board to listen to. 
     */
    private Board myBoard;
 
    /**
     * Allows its observers to have their copy of the current board. 
     * @return the board to observe. 
     */
    public Board getBoard() {
        return myBoard;
    }

    @Override
    public void update(final Observable theObservable, final Object arg1) {
        if (theObservable instanceof Board) {
            myBoard = (Board) theObservable;
            setChanged();
            this.notifyObservers();
        }
    }
}
