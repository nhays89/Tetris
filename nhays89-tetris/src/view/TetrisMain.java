/*
 * TCSS 305 – Autumn 2015 
 * Assignment 6 – Tetris
 */
package view;
/**
 * Driver for Tetris GUI object. 
 * 
 * @author Nick
 * @version 10 December 2015
 */
public final class TetrisMain {
    /**
     * Should no be construcuted.
     */
    private TetrisMain() {
        //do nothing
    }

    /**
     * Constructs the main GUI window frame.
     * 
     * @param theArgs Command line arguments (ignored).
     */
    public static void main(final String[] theArgs) {
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            /*
             * start the gui. 
             */
            public void run() {
                new TetrisGUI().start();
            }
        });
    }
    
}
