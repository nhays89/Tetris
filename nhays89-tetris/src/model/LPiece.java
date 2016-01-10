/*
 * TCSS 305 - Project Tetris
 */

package model;

/**
 * Defines the Tetris LPiece.
 * 
 * @author Alan Fowler
 * @version Autumn 2015
 */
public final class LPiece extends AbstractPiece {

    /**
     * The x and y-coordinates for all rotations of a LPiece.
     * The x coordinate is listed first, then the y coordinate.
     */
    private static final int[][][] MY_ROTATIONS = {{{2, 2}, {0, 1}, {1, 1}, {2, 1}},
                                                   {{1, 2}, {1, 1}, {1, 0}, {2, 0}},
                                                   {{0, 1}, {1, 1}, {2, 1}, {0, 0}},
                                                   {{0, 2}, {1, 2}, {1, 1}, {1, 0}}};

    /**
     * Creates a new L piece at the given coordinates.
     * 
     * @param theX The x coordinate of the Piece.
     * @param theY The y coordinate of the piece
     */
    protected LPiece(final int theX, final int theY) {
        super(MY_ROTATIONS, theX, theY, Block.L);
    }
}
