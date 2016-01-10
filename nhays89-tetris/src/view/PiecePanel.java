/*
 * TCSS 305 – Autumn 2015 
 * Assignment 6 – Tetris
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import model.AbstractPiece;
import model.Board;
/**
 * Displays a graphical preview of the next Piece on the Board. 
 * 
 * @author Nicholas Hays
 * @version 10 December 2015
 */
public class PiecePanel extends JPanel implements Observer {

    /**
     * serial id.
     */
    private static final long serialVersionUID = -6092307664999075994L;
    /**
     * default scale.
     */
    private static final int DEFAULT_SCALE = 25;
    /**
     * The default block width.
     */
    private static final int PANEL_BLOCK_WIDTH_NUM = 5;
    /**
     * The default block height.
     */
    private static final int PANEL_BLOCK_HEIGHT_NUM = 4;
    /**
     * next piece.
     */
    private AbstractPiece myNextPiece;
    /**
     * current board.
     */
    private Board myBoard;
    /**
     * is the board started.
     */
    private boolean myIsDrawable;
    /**
     * current scale factor.
     */
    private int myScale;
    
    /**
     * Constructs a piece panel to the GUI. 
     */
    public PiecePanel() {
        super(true);
        myScale = DEFAULT_SCALE;
        this.setBackground(Color.darkGray);
        this.setLayout(new BorderLayout());
        setPreferredSize(new Dimension(myScale * PANEL_BLOCK_WIDTH_NUM,
                                       myScale * PANEL_BLOCK_HEIGHT_NUM));
    }

    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        final AffineTransform aft = g2d.getTransform();
        g2d.translate(myScale , this.getHeight() - myScale);
        if (myIsDrawable) {
            g2d.setColor(Color.lightGray);
            final int[][] pieceCoords = myNextPiece.getRotation();
            for (int i = 0; i < pieceCoords.length; i++) {
                g2d.fill3DRect(pieceCoords[i][0] * myScale, pieceCoords[i][1] * -myScale,
                               myScale, myScale, true);
            }
        }
        g2d.setTransform(aft);
    }
    
    @Override
    public void update(final Observable theObservable, final Object theObj) {
        if (theObservable instanceof BoardQueue) {
            final BoardQueue queue = (BoardQueue) theObservable;
            myBoard = queue.getBoard();
            myIsDrawable = true;
            myNextPiece = (AbstractPiece) myBoard.getNextPiece();
            repaint();
        }
        if (theObservable instanceof ScaleDialogQueue) {
            final ScaleDialogQueue queue = (ScaleDialogQueue) theObservable;
            final ScaleDialog dialog = queue.getScaleDialog();
            myScale = dialog.getScale();
            setPreferredSize(new Dimension(PANEL_BLOCK_WIDTH_NUM * myScale,
                                           PANEL_BLOCK_HEIGHT_NUM * myScale));
        }
    }
}
