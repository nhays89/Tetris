/*
 * TCSS 305 - Project Tetris
 */

package model;

/**
 * This interface defines the required operations of mutable Tetris pieces.
 * 
 * @author Alan Fowler
 * @version Autumn 2015
 */
public interface Piece {

    /** Shifts the piece one space to the left. */
    void moveLeft();

    /** Shifts the piece one space to the right. */
    void moveRight();

    /** Shifts the piece one space down. */
    void moveDown();

    /** Rotates the piece one quarter turn clockwise. */
    void rotate();

    /** @return the x coordinate of this Piece. */
    int getX();

    /** @return the y coordinate of this Piece. */
    int getY();
}
