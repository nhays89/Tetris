/*
 * TCSS 305 - Project Tetris
 */

package model;

/**
 * Defines the Tetris TPiece.
 * 
 * @author Alan Fowler
 * @version Autumn 2015
 */
public final class TPiece extends AbstractPiece {

    /**
     * The x and y-coordinates for all rotations of a TPiece.
     * The x coordinate is listed first, then the y coordinate.
     */
    private static final int[][][] MY_ROTATIONS = {{{1, 2}, {0, 1}, {1, 1}, {2, 1}},
                                                   {{1, 2}, {1, 1}, {2, 1}, {1, 0}},
                                                   {{0, 1}, {1, 1}, {2, 1}, {1, 0}},
                                                   {{1, 2}, {0, 1}, {1, 1}, {1, 0}}};

    /**
     * Creates a new T piece at the given coordinates.
     * 
     * @param theX The x coordinate of the Piece.
     * @param theY The y coordinate of the piece
     */
    protected TPiece(final int theX, final int theY) {
        super(MY_ROTATIONS, theX, theY, Block.T);
    }
}
