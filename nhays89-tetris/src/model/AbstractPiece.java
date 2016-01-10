/*
 * TCSS 305 - Project Tetris
 */

package model;

/**
 * Provides default behavior for Tetris Pieces.
 * 
 * @author Alan Fowler
 * @version Autumn 2015
 */
public abstract class AbstractPiece implements Piece, Cloneable {

    /**
     * The number of blocks in a piece.
     */
    private static final int BLOCKS = 4;

    /** The x coordinate of this Piece. */
    private int myX;

    /** The y coordinate of this Piece. */
    private int myY;

    /** The rotational states of this Piece. */
    private int[][][] myRotations;

    /** The index of the current rotational state of this Piece. */
    private int myCurrentRotation;

    /** The block type representing this piece. */
    private final Block myBlock;

    /**
     * Creates a new piece at the given coordinates.
     * 
     * @param theRotations the rotational states for this Piece.
     * @param theX the initial x coordinate for this piece.
     * @param theY the initial y coordinate for this piece.
     * @param theBlock the type of block.
     */
    protected AbstractPiece(final int[][][] theRotations,
                            final int theX, final int theY,
                            final Block theBlock) {
        
        // make a defensive copy of theRotations 3D array since arrays are mutable
        myRotations = new int[theRotations.length][theRotations[0].length][];
        for (int array2D = 0; array2D < theRotations.length; array2D++) {
            for (int array1D = 0; array1D < theRotations[0].length; array1D++) {
                myRotations[array2D][array1D] = theRotations[array2D][array1D].clone();
            }
        }
        myCurrentRotation = 0;
        myX = theX;
        myY = theY;
        myBlock = theBlock;
    }

    @Override
    public final void moveLeft() {
        myX--;
    }

    @Override
    public final void moveRight() {
        myX++;
    }

    @Override
    public final void moveDown() {
        myY--;
    }

    @Override
    public final void rotate() {
        if (myRotations.length > 1) {
            myCurrentRotation = (myCurrentRotation + 1) % myRotations.length;
        }
    }

    /**
     * Returns the coordinates of this piece's current rotation.
     * 
     * @return The coordinates of this piece's current rotation.
     */
    public final int[][] getRotation() {
        return myRotations[myCurrentRotation].clone();
    }

    @Override
    public final int getX() {
        return myX;
    }

    @Override
    public final int getY() {
        return myY;
    }

    /**
     * @return the current state of this Piece translated to board coordinates.
     */
    public final int[][] getBoardCoordinates() {
        final int[][] result = new int[BLOCKS][2];

        for (int i = 0; i < BLOCKS; i++) {
            result[i][0] = myRotations[myCurrentRotation][i][0] + myX;
            result[i][1] = myRotations[myCurrentRotation][i][1] + myY;
        }
        return result;
    }

    /**
     * @return the block type of this piece
     */
    public final Block getBlock() {
        return myBlock;
    }

    @Override
    public Piece clone() throws CloneNotSupportedException {

        // clone this Piece
        final Piece result = (Piece) super.clone();

        // now separately clone the my_rotations 3D array since arrays are mutable
        final int[][][] newArray3D = new int[myRotations.length][myRotations[0].length][];
        for (int array2D = 0; array2D < myRotations.length; array2D++) {
            for (int array1D = 0; array1D < myRotations[0].length; array1D++) {
                newArray3D[array2D][array1D] = myRotations[array2D][array1D].clone();
            }
        }
        ((AbstractPiece) result).myRotations = newArray3D;

        return result;
    }

    @Override
    public String toString() {

        final int width = determineWidth();
        final int height = determineHeight();
        final StringBuilder sb = new StringBuilder();

        // Construct the string by walking through the piece top to bottom, left to right.
        for (int col = height; col >= 0; col--) {
            for (int row = 0; row < width; row++) {
                boolean found = false;
                for (int block = 0; block < BLOCKS; block++) {
                    if (myRotations[myCurrentRotation][block][1] == col
                        && myRotations[myCurrentRotation][block][0] == row) {
                        // There is a block here, so print and move on
                        sb.append("[]");
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    // None of the blocks are here, so put in empty space
                    sb.append("  ");
                }
            }
            // Move to the next line
            sb.append('\n');
        }
        return sb.toString();
    }
    
    /**
     * Returns the height of this piece.
     * 
     * @return The height of this piece
     */
    private int determineHeight() {
        int result = 0;
        for (int i = 0; i < BLOCKS; i++) {
            if (myRotations[myCurrentRotation][i][1] > result) {
                result = myRotations[myCurrentRotation][i][1];
            }
        }
        return result + 1;
    }

    /**
     * Returns the width of this piece.
     * 
     * @return The width of this piece
     */
    private int determineWidth() {
        int result = 0;
        for (int i = 0; i < BLOCKS; i++) {
            if (myRotations[myCurrentRotation][i][0] > result) {
                result = myRotations[myCurrentRotation][i][0];
            }
        }
        return result + 1;
    }
}
