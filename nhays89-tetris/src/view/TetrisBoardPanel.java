/*
 * TCSS 305 – Autumn 2015 Assignment 6 – Tetris
 */

package view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import model.AbstractPiece;
import model.Block;
import model.Board;

/**
 * Renders the tetris board with current piece and frozen blocks on the GUI.
 * 
 * @author Nicholas Hays
 * @version 9 December 2015
 */
public class TetrisBoardPanel extends JPanel implements Observer, PropertyChangeListener {
    /**
     * Property change event name to notify listeners of score changes.
     */
    private static final String SCORE_PROPERTY = "score";
    /**
     * Resets the board data.
     */
    private static final Object RESET_DATA_PROPERTY = "resetData";
    /**
     * Draws end game to board.
     */
    private static final Object END_GAME_PROPERTY = "endGame";
    /**
     * Constructs a grid.
     */
    private static final Object GRID_PROPERTY = "grid";
    /**
     * The default scale.
     */
    private static final int DEFAULT_SCALE = 25;
    /**
     * alignment offset for game over text.
     */
    private static final int OFFSET = 4;
    /**
     * serial id.
     */
    private static final long serialVersionUID = 1L;
    /**
     * The tetris board width.
     */
    private static final int BOARD_WIDTH = 10;
    /**
     * The tetris board height.
     */
    private static final int BOARD_HEIGHT = 20;
    /**
     * default panel width.
     */
    private static final int PANEL_WIDTH = 250;
    /**
     * default panel height.
     */
    private static final int PANEL_HEIGHT = 500;
    /**
     * Width of grid pen.
     */
    private static final int GRID_WIDTH = 1;
    /**
     * Color of the Grid.
     */
    private static final Color GRID_COLOR = new Color(192, 192, 192, 80);
    /**
     * The current scale factor.
     */
    private int myScale;
    /**
     * Determines if the frozen blocks is non null.
     */
    private boolean myIsDrawable;
    /**
     * The board object.
     */
    private Board myBoard;
    /**
     * game over.
     */
    private boolean myGameOver;
    /**
     * Determines if the paint component should graw a grid.
     */
    private boolean myIsGrid;

    /**
     * Constructs the GUI representation of the Board object.
     */
    public TetrisBoardPanel() {
        super(new BorderLayout(), true);
        myScale = DEFAULT_SCALE;
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(Color.BLACK);
    }

    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        g2d.translate(0, this.getHeight());
        if (myIsDrawable) {
            g2d.setPaint(Color.lightGray);
            drawCurrentPiece(g2d);
            if (!myBoard.getFrozenBlocks().isEmpty()) {
                drawFrozenBlocks(g2d);
            }
        }
        if (myIsGrid) {
            drawGrid(g2d);
        }
        if (myGameOver) {
            drawGameOver(g2d);
        }
    }

    /**
     * Draws the current cascading piece to the panel.
     * 
     * @param theG2d the graphics object.
     */
    private void drawCurrentPiece(final Graphics2D theG2d) {
        final AbstractPiece currentPiece = (AbstractPiece) myBoard.getCurrentPiece();
        final int[][] boardCoords = currentPiece.getBoardCoordinates();
        for (int i = 0; i < boardCoords.length; i++) {
            theG2d.fill3DRect(boardCoords[i][0] * myScale,
                              (boardCoords[i][1] * -myScale) - myScale, myScale, myScale,
                              true);

        }
    }

    /**
     * Draws the frozen blocks to the panel.
     * 
     * @param theGraphics the graphics object.
     */
    private void drawFrozenBlocks(final Graphics2D theGraphics) {
        final LinkedList<Block[]> frozenBlocks =
                        (LinkedList<Block[]>) myBoard.getFrozenBlocks();
        for (int i = 0; i < frozenBlocks.size(); i++) {
            for (int j = 0; j < frozenBlocks.get(i).length; j++) {
                if (!(frozenBlocks.get(i)[j] == Block.EMPTY)) {
                    theGraphics.fill3DRect(j * myScale, (i * -myScale) - myScale, myScale,
                                           myScale, true);
                }
            }
        }
    }

    /**
     * 
     * @param theClearedLines the number of lines that were cleared.
     */
    private void updateScore(final int theClearedLines) {
        firePropertyChange(SCORE_PROPERTY, null, theClearedLines);
    }

    @Override
    public void update(final Observable theObservable, final Object theObject) {
        if (theObservable instanceof BoardQueue) {
            final BoardQueue queue = (BoardQueue) theObservable;
            myBoard = queue.getBoard();
            if (myBoard.isGameOver()) {
                myGameOver = true;
                repaint();
                return;
            }
            getFilledLines();
            myIsDrawable = true;
            revalidate();
            repaint();
        }
        if (theObservable instanceof ScaleDialogQueue) {
            final ScaleDialogQueue queue = (ScaleDialogQueue) theObservable;
            final ScaleDialog dialog = queue.getScaleDialog();
            myScale = dialog.getScale();
            setPreferredSize(new Dimension(myScale * BOARD_WIDTH, myScale * BOARD_HEIGHT));
            setSize(new Dimension(myScale * BOARD_WIDTH, myScale * BOARD_HEIGHT));
            revalidate();
            repaint();
        }
    }

    /**
     * Draws game over to board panel.
     * 
     * @param theGraphics the Graphics object.
     */
    private void drawGameOver(final Graphics2D theGraphics) {
        theGraphics.setFont(new Font("Times New Roman", Font.BOLD, myScale));
        theGraphics.setColor(Color.red);
        theGraphics.drawString("Game Over", this.getWidth() / OFFSET, -(this.getHeight() / 2));

    }

    /**
     * Draws a grid to the Panel.
     * 
     * @param theG2d the Graphics object to draw.
     */
    private void drawGrid(final Graphics2D theG2d) {
        theG2d.setColor(GRID_COLOR);
        theG2d.setStroke(new BasicStroke(GRID_WIDTH));
        for (int i = 0; i < (this.getWidth() / myScale) + GRID_WIDTH; i++) {
            theG2d.draw(new Line2D.Double(i * myScale, 0, i * myScale, -this.getHeight()));
        }

        for (int i = 0; i < (this.getHeight() / myScale) + GRID_WIDTH; i++) {
            theG2d.draw(new Line2D.Double(0, -i * myScale, this.getWidth(), -myScale * i));
        }
    }

    /**
     * Determines the number of lines that are completely filled.
     */
    private void getFilledLines() {
        int numOfLines = 0;
        for (int i = myBoard.getFrozenBlocks().size() - 1; i >= 0; i--) {
            boolean clear = true;
            final Block[] blocks = myBoard.getFrozenBlocks().get(i);

            for (final Block block : blocks) {
                if (block == Block.EMPTY) {
                    clear = false;
                    break;
                }
            }
            if (clear) {
                numOfLines++;
            }
        }
        if (numOfLines > 0) {
            updateScore(numOfLines);
        }
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (theEvent.getPropertyName().equals(RESET_DATA_PROPERTY)) {
            myGameOver = false;
        }
        if (theEvent.getPropertyName().equals(END_GAME_PROPERTY)) {
            myGameOver = true;
            repaint();
        }
        if (theEvent.getPropertyName().equals(GRID_PROPERTY)) {
            if (theEvent.getNewValue().equals(true)) {
                myIsGrid = true;
                repaint();
            } else {
                myIsGrid = false;
                repaint();
            }
        }
    }
}
