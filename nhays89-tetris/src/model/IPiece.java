/*
 * TCSS 305 - Project Tetris
 */

package model;

/**
 * Defines the Tetris IPiece.
 * 
 * @author Alan Fowler
 * @version Autumn 2015
 */
public final class IPiece extends AbstractPiece {

    /**
     * The x and y-coordinates for all rotations of a IPiece.
     * The x coordinate is listed first, then the y coordinate.
     */
    private static final int[][][] MY_ROTATIONS = {{{0, 2}, {1, 2}, {2, 2}, {3, 2}},
                                                   {{2, 3}, {2, 2}, {2, 1}, {2, 0}},
                                                   {{0, 1}, {1, 1}, {2, 1}, {3, 1}},
                                                   {{1, 3}, {1, 2}, {1, 1}, {1, 0}}};

    /**
     * Creates a new I piece at the given coordinates.
     * 
     * @param theX The x coordinate of the Piece.
     * @param theY The y coordinate of the piece
     */
    protected IPiece(final int theX, final int theY) {
        super(MY_ROTATIONS, theX, theY, Block.I);
    }
}
