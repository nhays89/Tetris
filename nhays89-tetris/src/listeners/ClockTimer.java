/*
 * TCSS 305 – Autumn 2015 
 * Assignment 6 – Tetris
 */
package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.InfoPanel;
/**
 * Clock Timer used to keep track of time between levels. 
 * @author Nick
 * @version 10 December 2015
 */
public class ClockTimer implements ActionListener {
    
    /**
     * The class which displays the current level time. 
     */
    private final InfoPanel myInfoPanel;
    /**
     * Constructs a clock timer used for the Information Panel. 
     * @param theInfoPanel the Panel to display the time.
     */
    public ClockTimer(final InfoPanel theInfoPanel) {
        myInfoPanel = theInfoPanel;
    }
    
    @Override
    public void actionPerformed(final ActionEvent theEvent) {
        myInfoPanel.updateTime();
    }

}
