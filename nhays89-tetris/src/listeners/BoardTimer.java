/*
 * TCSS 305 – Autumn 2015 
 * Assignment 6 – Tetris
 */
package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Board;
/**
 * Animates the Board. 
 * 
 * @author Nick
 * @version 10 December 2015
 */
public class BoardTimer implements ActionListener {
    /**
     * The Tetris board. 
     */
    private final Board myBoard;
    /**
     * Constructs a timer used to call step on the Board object.
     * @param theBoard the Board to animate. 
     */
    public BoardTimer(final Board theBoard) {
        myBoard = theBoard;
    }

    @Override
    public void actionPerformed(final ActionEvent theEvent) {
        myBoard.step();
    }
}
