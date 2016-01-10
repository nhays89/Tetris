/*
 * TCSS 305 - Project Tetris
 */

package model;

/**
 * Defines the Tetris JPiece.
 * 
 * @author Alan Fowler
 * @version Autumn 2015
 */
public final class JPiece extends AbstractPiece {

    /**
     * The x and y-coordinates for all rotations of a JPiece.
     * The x coordinate is listed first, then the y coordinate.
     */
    private static final int[][][] MY_ROTATIONS = {{{0, 2}, {0, 1}, {1, 1}, {2, 1}},
                                                   {{1, 2}, {2, 2}, {1, 1}, {1, 0}},
                                                   {{0, 1}, {1, 1}, {2, 1}, {2, 0}},
                                                   {{1, 2}, {1, 1}, {0, 0}, {1, 0}}};

    /**
     * Creates a new J piece at the given coordinates.
     * 
     * @param theX The x coordinate of the Piece.
     * @param theY The y coordinate of the piece
     */
    protected JPiece(final int theX, final int theY) {
        super(MY_ROTATIONS, theX, theY, Block.J);
    }
}
