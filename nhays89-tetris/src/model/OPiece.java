/*
 * TCSS 305 - Project Tetris
 */

package model;

/**
 * Defines the Tetris OPiece.
 * 
 * @author Alan Fowler
 * @version Autumn 2015
 */
public final class OPiece extends AbstractPiece {

    /**
     * The x and y-coordinates for all rotations of an OPiece.
     * The x coordinate is listed first, then the y coordinate.
     */
    private static final int[][][] MY_ROTATIONS = {{{1, 2}, {2, 2}, {1, 1}, {2, 1}}};

    /**
     * Creates a new O piece at the given coordinates.
     * 
     * @param theX The x coordinate of the Piece.
     * @param theY The y coordinate of the piece
     */
    protected OPiece(final int theX, final int theY) {
        super(MY_ROTATIONS, theX, theY, Block.O);
    }
}
