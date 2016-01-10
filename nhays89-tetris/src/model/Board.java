/*
 * TCSS 305 - Project Tetris
 */

package model;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

/**
 * A representation of a Tetris game board.
 * 
 * @author Alan Fowler
 * @version Autumn 2015
 */
public final class Board extends Observable {
    
    // constants

    /** The representation of the side walls of the board in string output. */
    private static final String WALL = "|";

    /** The representation of the board corners in string output. */
    private static final String CORNER = "+";

    /** The representation of the board floor in string output. */
    private static final String FLOOR = "-";

    /** The representation of an empty grid position in string output. */
    private static final String EMPTY = " ";

    /** The representation of a frozen block in string output. */
    private static final String FROZEN = "X";

    /** The representation of the current piece's position in string output. */
    private static final String CURRENT_PIECE = "*";

    /** The random number generator used for choosing new pieces. */
    private static final Random RANDOM = new Random();

    /** The minimum size of a board. */
    private static final int MIN_SIZE = 5;

    /** The number of rotations to return a piece to initial state. */
    private static final int ROTATIONS = 4;

    /** The number of extra rows above the board to display in String output. */
    private static final int EXTRA_ROWS = 4;
    
    /** Represents the state when no current piece exists. */
    private static final Piece NO_PIECE = null;

    
    // instance fields
    /**
     * The width of this Tetris board.
     */
    private int myWidth;

    /**
     * The height of this Tetris board.
     */
    private int myHeight;

    /**
     * The current frozen blocks on the board.
     */
    private final List<Block[]> myFrozenBlocks;

    /**
     * The piece currently being moved on the board.
     */
    private Piece myCurrentPiece;

    /**
     * The next piece to be used in the board.
     */
    private Piece myNextPiece;

    /**
     * The predetermined queue of pieces to be fed into the board.
     * This queue will be used repeatedly.
     */
    private Deque<Piece> myPieces;

    /**
     * The boolean representing the game state of Tetris.
     */
    private boolean myGameOver;

    /**
     * Constructs a Board using the specified dimensions and the specified sequence of pieces.
     * 
     * @param theWidth the grid width to assign to this Tetris board
     * @param theHeight the grid height to assign to this Tetris board
     * @param thePieces the sequence of pieces to use;
     * an empty list or null indicates a random game
     */
    public Board(final int theWidth, final int theHeight,
                 final Deque<Piece> thePieces) {
        super();
        myPieces = new ArrayDeque<>();
        myFrozenBlocks = new LinkedList<Block[]>();
        newGame(theWidth, theHeight, thePieces);
    }
    
    /**
     * Constructs a Board using the specified dimensions and random pieces.
     * 
     * @param theWidth the grid width to assign to this Tetris board; 10 is the standard
     * @param theHeight the grid height to assign to this Tetris board; 20 is the standard
     */
    public Board(final int theWidth, final int theHeight) {
        this(theWidth, theHeight, null);
    }

    /**
     * Creates a new game on a Board of the specified width and height
     * and loads the given set of pieces.
     * 
     * @param theWidth The grid width of the board; 10 is the standard value
     * @param theHeight The grid height of the board; 20 is the standard value
     * @param thePieces The pieces to use; null indicates that random pieces should be used
     */
    public void newGame(final int theWidth,
                        final int theHeight,
                        final Deque<Piece> thePieces) {
        
        if (theWidth < MIN_SIZE || theHeight < MIN_SIZE) {
            throw new IllegalArgumentException();
        }
        
        myWidth = theWidth;
        myHeight = theHeight;
        
        if (thePieces == null) {
            myPieces.clear();
        } else {
            myPieces = thePieces;
        }
        
        myCurrentPiece = NO_PIECE;
        myFrozenBlocks.clear();
        myGameOver = false;
        assignNextPiece();
        assignCurrentPiece();
        setChanged();
        notifyObservers();
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return myWidth;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return myHeight;
    }

    /**
     * Returns the current frozen blocks on the board.
     * 
     * @return the current frozen blocks on the board.
     */
    public List<Block[]> getFrozenBlocks() {
        return myFrozenBlocks;
    }

    /**
     * Returns the current Piece being moved.
     * 
     * @return the current Piece being moved.
     */
    public Piece getCurrentPiece() {
        return myCurrentPiece;
    }
    
    /**
     * Returns the next piece to be used.
     * 
     * @return the next piece to be used.
     */
    public Piece getNextPiece() {
        return myNextPiece;
    }

    /**
     * Returns whether the game is over or not.
     * 
     * @return Whether the game is over or not.
     */
    public boolean isGameOver() {
        return myGameOver;
    }

    /**
     * Attempts to move the current piece to the left.
     */
    public void moveLeft() {
        final int[][] blocks = ((AbstractPiece) myCurrentPiece).getBoardCoordinates();
        boolean canPass = true;

        // perform bounds checking on each block
        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i][0] == 0 || // block is already at the left wall
                blockAt(blocks[i][0] - 1, blocks[i][1]) != Block.EMPTY) {
                // block to left is occupied
                canPass = false;
                break; // can't move, no need to keep checking
            }
        }

        if (canPass) {
            myCurrentPiece.moveLeft();
            setChanged();
            notifyObservers();
        }
    }

    /**
     * Attempts to move the current piece to the right.
     */
    public void moveRight() {
        final int[][] blocks = ((AbstractPiece) myCurrentPiece).getBoardCoordinates();
        boolean canPass = true;

        // perform bounds checking on each block
        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i][0] == myWidth - 1 || // block is already at the right wall
                blockAt(blocks[i][0] + 1, blocks[i][1]) != Block.EMPTY) {
                // block to right is occupied
                canPass = false;
                break; // can't move, no need to keep checking
            }
        }

        if (canPass) {
            myCurrentPiece.moveRight();
            setChanged();
            notifyObservers();
        }
    }

    /**
     * Attempts to move the current piece down.
     */
    public void moveDown() {

        if (isMoveDownLegal()) {
            myCurrentPiece.moveDown();
            setChanged();
            notifyObservers();
        } else {
            freeze(); // this will notify observers
        }
    }
    
    /**
     * Performs bound checking to determine if the current piece can move down.
     * 
     * @return true if it possible to move this piece down; false otherwise
     */
    private boolean isMoveDownLegal() {
        boolean canPass = true;
        final int[][] blocks = ((AbstractPiece) myCurrentPiece).getBoardCoordinates();
        
        // perform bounds checking on each block
        for (int i = 0; i < blocks.length; i++) {
            // Is this block at the bottom, or is there a piece below it?
            if (blocks[i][1] == 0 || // block is at the bottom
                blockAt(blocks[i][0], blocks[i][1] - 1) != Block.EMPTY) {
                // block below is occupied
                canPass = false;
                break; // can't move, no need to keep checking
            }
        }
        
        return canPass;
    }

    /**
     * Drops the current Piece all the way down.
     */
    public void hardDrop() {
        while (isMoveDownLegal()) {
            myCurrentPiece.moveDown();
        }
        freeze(); // this will notify observers
    }

    /**
     * Attempts to rotate the current piece clockwise.
     */
    public void rotate() {
        myCurrentPiece.rotate();
        final int[][] blocks = ((AbstractPiece) myCurrentPiece).getBoardCoordinates();
        boolean canPass = true;

        for (final int[] dimension : blocks) {
            if (dimension[0] >= myWidth
                    || blockAt(dimension[0], dimension[1]) != Block.EMPTY) {

                for (int i = 1; i < ROTATIONS; i++) {
                    myCurrentPiece.rotate();
                }
                canPass = false;
                break;
            }
        }
        if (canPass) {
            setChanged();
            notifyObservers();
        }
    }

    /**
     * Updates the game by one step.
     */
    public void step() {
        moveDown();
    }
    
    /**
     * Returns a string that represents the current state of the board.
     * 
     * @return The string representation of the board
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        for (int i = myHeight + EXTRA_ROWS - 1; i > myHeight - 1; i--) {
            sb.append(EMPTY);
            sb.append(getRowString(i));
            sb.append('\n');
        }

        for (int i = myHeight - 1; i >= 0; i--) {
            sb.append(WALL);
            sb.append(getRowString(i));
            sb.append(WALL);
            sb.append('\n');
        }
        sb.append(CORNER);
        for (int i = 0; i < myWidth; i++) {
            sb.append(FLOOR);
        }
        sb.append(CORNER);
        return sb.toString();
    }


    
    
    // private methods

    /**
     * Initializes the current piece.
     */
    private void assignCurrentPiece() {
        myCurrentPiece = myNextPiece;
        assignNextPiece();
    }

    /**
     * Sets the next piece.
     */
    private void assignNextPiece() {
        if (myPieces == null || myPieces.isEmpty()) {
            myNextPiece = randomPiece(myWidth / 2 - 2, myHeight);
        } else {
            myNextPiece = myPieces.removeFirst();
            try {
                myPieces.addLast(((AbstractPiece) myNextPiece).clone());
            } catch (final CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Creates a new piece randomly chosen from the possible pieces at the
     * specified coordinates.
     * 
     * @param theX The x-coordinate
     * @param theY The y-coordinate
     * 
     * @return A randomly chosen piece
     */
    private Piece randomPiece(final int theX, final int theY) {
        final Block[] blocks = Block.values();
        Piece result;

        switch (blocks[RANDOM.nextInt(blocks.length)]) {
            case I:
                result = new IPiece(theX, theY);
                break;

            case J:
                result = new JPiece(theX, theY);
                break;

            case L:
                result = new LPiece(theX, theY);
                break;

            case O:
                result = new OPiece(theX, theY);
                break;

            case S:
                result = new SPiece(theX, theY);
                break;

            case T:
                result = new TPiece(theX, theY);
                break;

            case Z:
                result = new ZPiece(theX, theY);
                break;

            default: // If EMPTY, recursively try again
                result = randomPiece(theX, theY);
                break;
        }
        return result;
    }

    /**
     * Retrieves the block at the specified coordinates.
     * 
     * @param theX The x-coordinate
     * @param theY The y-coordinate
     * 
     * @return The block at the specified coordinates, EMPTY if there is no
     *         block at the specified coordinates, null if the specified
     *         coordinates are outside of the board
     */
    private Block blockAt(final int theX, final int theY) {
        Block result = null; // outside the board coordinates
        if (theX < myWidth && theX >= 0 && theY >= 0) {
            // inside the board
            result = Block.EMPTY; // blocks above the board are empty
            if (theY < myFrozenBlocks.size()) {
                result = myFrozenBlocks.get(theY)[theX];
            }
        }
        return result;
    }

    /**
     * Evaluates if the current piece occupies the provided position.
     * 
     * @param theX The x-coordinate to examine
     * @param theY The y-coordinate to examine
     * @return true if the current piece occupies the position, false otherwise
     */
    private boolean currentPieceAt(final int theX, final int theY) {
        boolean result = false;
        final int[][] blocks = ((AbstractPiece) myCurrentPiece).getBoardCoordinates();

        for (int block = 0; block < blocks.length; block++) {
            if (blocks[block][1] == theY && blocks[block][0] == theX) {
                result = true;
            }
        }
        return result;
    }

    /**
     * The current piece cannot move down so add its blocks to the board.
     */
    private void freeze() {
        final int[][] coordinates = ((AbstractPiece) myCurrentPiece).getBoardCoordinates();

        for (int block = 0; block < coordinates.length; block++) {
            final int x = coordinates[block][0];
            final int y = coordinates[block][1];

            // Add rows until this block can fit in one.
            while (y >= myFrozenBlocks.size()) {
                final Block[] newRow = new Block[myWidth];
                for (int i = 0; i < myWidth; i++) {
                    newRow[i] = Block.EMPTY;
                }
                myFrozenBlocks.add(newRow);
            }
            final Block[] row = myFrozenBlocks.get(y);
            row[x] = ((AbstractPiece) myCurrentPiece).getBlock();
        }
        
        setChanged();
        notifyObservers();
        
        clearLines(); // this may also notify observers
        
        if (myFrozenBlocks.size() > myHeight) {
            myGameOver = true;
        } else {
            assignCurrentPiece();
        }
        
        setChanged();
        notifyObservers();
    }

    /**
     * Checks if there are any lines that need to be cleared
     * and removes them from the board.
     */
    private void clearLines() {
        for (int i = myFrozenBlocks.size() - 1; i >= 0; i--) {
            boolean clear = true;
            final Block[] blocks = myFrozenBlocks.get(i);

            for (final Block block : blocks) {
                if (block == Block.EMPTY) {
                    clear = false;
                    break;
                }
            }
            if (clear) {
                myFrozenBlocks.remove(i);
                setChanged();
                notifyObservers();
            }
        }
    }

    /**
     * Returns a string representing the blocks in the given row.
     * 
     * @param theRow The row to represent
     * @return The string representation
     */
    private String getRowString(final int theRow) {
        final StringBuilder sb = new StringBuilder();

        if (myFrozenBlocks.size() - 1 < theRow) {
            for (int column = 0; column < myWidth; column++) {
                if (currentPieceAt(column, theRow)) {
                    sb.append(CURRENT_PIECE);
                } else {
                    sb.append(EMPTY);
                }
            }
        } else {
            final Block[] rowOfBlocks = myFrozenBlocks.get(theRow);

            for (int column = 0; column < myWidth; column++) {
                if (currentPieceAt(column, theRow)) {
                    sb.append(CURRENT_PIECE);
                } else if (rowOfBlocks[column] == Block.EMPTY) {
                    sb.append(EMPTY);
                } else {
                    sb.append(FROZEN);
                }
            }
        }
        return sb.toString();
    }
}
