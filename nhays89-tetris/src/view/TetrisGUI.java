/*
 * TCSS 305 – Autumn 2015 
 * Assignment 6 – Tetris
 */
package view;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import listeners.TetrisKeyAdapter;
import sound.SoundEffect;

/**
 * Provides objects for the GUI in Tetris.
 * 
 * @author Nicholas Hays
 * @version 12 December 2015
 */
public class TetrisGUI extends JFrame implements Observer {
 
    /**
     * Default serial id. 
     */
    private static final long serialVersionUID = 1L;
    /**
     * Tetris board panel.
     */
    private final TetrisBoardPanel myTetrisPanel;
    /**
     * Tetris piece panel. 
     */
    private final PiecePanel myPiecePanel;
    /**
     * Tetris info panel. 
     */
    private final InfoPanel myInfoPanel;
    /**
     * Tetris Menu Bar. 
     */
    private final TetrisMenuBar myTetrisMenuBar;
    /**
     * Queue to hold all board observers.
     */
    private final BoardQueue myBoardQueue;
    /**
     * Queue to hold all scale dialog observers.
     */
    private final ScaleDialogQueue myScaleDialogQueue;
    /**
     * Key adapter to regulate key controls.
     */
    private final TetrisKeyAdapter myKeyAdapter;
    /**
     * Constructs a Tetris GUI onto the Frame.  
     */
    public TetrisGUI() {
        super();
        myBoardQueue = new BoardQueue();
        myScaleDialogQueue = new ScaleDialogQueue();
        myTetrisPanel = new TetrisBoardPanel();
        myKeyAdapter = new TetrisKeyAdapter();
        myPiecePanel = new PiecePanel();
        myInfoPanel = new InfoPanel();
        myTetrisMenuBar = new TetrisMenuBar();
    }

    /**
     * Initializes a new GUI window.
     */
    public void start() {
        loadSoundFiles();
        addChangeListeners();
        createQueues();
        sendQueue();
        addKeyListener(myKeyAdapter);
        setResizable(false);
        final JPanel previewPanel = new JPanel(new BorderLayout(), true);
        previewPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        previewPanel.add(myPiecePanel, BorderLayout.NORTH);
        previewPanel.add(myInfoPanel, BorderLayout.CENTER);
        final JPanel borderPanel = new JPanel();
        borderPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        borderPanel.add(myTetrisPanel);
        getContentPane().add(borderPanel, BorderLayout.WEST);
        getContentPane().add(previewPanel, BorderLayout.CENTER);
        setJMenuBar(myTetrisMenuBar);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        pack();
    }
    /**
     * load sound files. 
     */
    private void loadSoundFiles() {
        SoundEffect.init();
    }

    /**
     * Initializes property change listeners.
     */
    private void addChangeListeners() {
        myTetrisPanel.addPropertyChangeListener(myInfoPanel);
        myTetrisMenuBar.addPropertyChangeListener(myInfoPanel);
        myTetrisMenuBar.addPropertyChangeListener(myKeyAdapter);
        myTetrisMenuBar.addPropertyChangeListener(myTetrisPanel);
        addPropertyChangeListener(myTetrisMenuBar);
        myInfoPanel.addPropertyChangeListener(myTetrisMenuBar);
    }
    /**
     * Fires property change listeners to send data.
     */
    private void sendQueue() {
        firePropertyChange("boardQueue", null, myBoardQueue);
        firePropertyChange("scaleQueue", null, myScaleDialogQueue);
        firePropertyChange("gui", null, this);
    }
    /**
     * Creates the queues that hold all the observers. 
     */
    private void createQueues() {
        myBoardQueue.addObserver(myTetrisPanel);
        myBoardQueue.addObserver(myPiecePanel);
        myBoardQueue.addObserver(myInfoPanel);
        myBoardQueue.addObserver(myKeyAdapter);
        myBoardQueue.addObserver(myTetrisMenuBar);
        myScaleDialogQueue.addObserver(myTetrisPanel);
        myScaleDialogQueue.addObserver(myPiecePanel);
        myScaleDialogQueue.addObserver(myInfoPanel);
        myScaleDialogQueue.addObserver(this);
    }

    @Override
    public void update(final Observable theObservable, final Object theObj) {
        if (theObservable instanceof ScaleDialogQueue) {
            pack(); 
        }
    }
}
