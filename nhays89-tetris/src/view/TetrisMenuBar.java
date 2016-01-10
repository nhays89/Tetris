/*
 * TCSS 305 – Autumn 2015 Assignment 6 – Tetris
 */

package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import listeners.BoardTimer;
import model.Board;
import sound.SoundEffect;

/**
 * The menu bar for the Tetris GUI.
 * 
 * @author Nicholas A. Hays
 * @version 11 December 2015
 */
public class TetrisMenuBar extends JMenuBar implements Observer, PropertyChangeListener {

    /**
     * Default serial id.
     */
    private static final long serialVersionUID = 1L;
    /**
     * The pause.
     */
    private static final String PAUSE = "Pause";
    /**
     * Calls Game Timer to start game clock.
     */
    private static final String START_GAME_PROPERTY = "startGameClock";
    /**
     * Calls Game Timer to stop game clock.
     */
    private static final String STOP_GAME_PROPERTY = "stopGameClock";
    /**
     * Calls listeners to end game.
     */
    private static final String END_GAME_PROPERTY = "endGame";
    /**
     * Calls listeners to reset data.
     */
    private static final String RESET_DATA_PROPERTY = "resetData";
    /**
     * Calls the board panel to draw grid.
     */
    private static final String GRID_PROPERTY = "grid";
    /**
     * Board width.
     */
    private static final int BOARD_WIDTH = 10;
    /**
     * Board height.
     */
    private static final int BOARD_HEIGHT = 20;
    /**
     * Initial delay timer.
     */
    private static final int INIT_DELAY = 1000;
    /**
     * Amount of decrese between levels.
     */
    private static final double DECREASE = .8;
    /**
     * Midi player for background music.
     */
    private static Sequencer myMidiPlayer;
    /**
     * Mute state.
     */
    protected boolean myMuteState;
    /**
     * Adjusts the delay speed of the cascading objects on the board.
     */
    private int myDelayAdjuster;
    /**
     * GUI frame.
     */
    private JFrame myGuiFrame;
    /**
     * Tetris Board.
     */
    private Board myBoard;
    /**
     * Board timer.
     */
    private Timer myBoardTimer;
    /**
     * Pause game.
     */
    private boolean myPause;
    /**
     * Pause Menu Item.
     */
    private JMenuItem myPauseMntm;
    /**
     * Scale dialog queue.
     */
    private ScaleDialogQueue myScaleDialogQueue;
    /**
     * Board queue.
     */
    private BoardQueue myBoardQueue;
    /**
     * End game menu item.
     */
    private JMenuItem myEndGameMntm;
    /**
     * New game menu item.
     */
    private JMenuItem myNewGameMntm;
    /**
     * Mutes the current track.
     */
    private JCheckBoxMenuItem myMuteMntm;
    /**
     * Determines if its first launch.
     */
    private boolean myFirstLaunch = true;

    /**
     * Constructs a Menu Bar for Tetris.
     */
    public TetrisMenuBar() {
        super();
        createMenus();
    }

    /**
     * Creates Menus.
     */
    private void createMenus() {
        add(createStartMenu());
        add(createWinMenu());
        add(createAboutMenu());
    }

    /**
     * Creates a Start Menu.
     * 
     * @return the start Menu.
     */
    private JMenu createStartMenu() {
        final JMenu startMenu = new JMenu("Start");
        myNewGameMntm = new JMenuItem("New Game");
        myNewGameMntm.setAccelerator(KeyStroke.getKeyStroke("control shift S"));
        myNewGameMntm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                startGame();
            }
        });
        myPauseMntm = new JMenuItem(PAUSE);
        myPauseMntm.setAccelerator(KeyStroke.getKeyStroke("control shift P"));
        myPauseMntm.setEnabled(false);
        myPauseMntm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                initPause();
            }
        });
        myEndGameMntm = new JMenuItem("End Game");
        myEndGameMntm.setEnabled(false);
        myEndGameMntm.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                endGame();
            }
        });
        myMuteMntm = new JCheckBoxMenuItem("Mute");
        myMuteMntm.setEnabled(false);
        myMuteMntm.setAccelerator(KeyStroke.getKeyStroke("control shift M"));
        myMuteMntm.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent arg0) {
                if (myMuteMntm.isSelected()) {
                    myMuteState = true;
                    myMidiPlayer.stop();
                    SoundEffect.setVolume(SoundEffect.Volume.MUTE);
                } else {
                    myMuteState = false;
                    SoundEffect.setVolume(SoundEffect.Volume.HIGH);
                    myMidiPlayer.start();
                }
            }
        });
        startMenu.add(myNewGameMntm);
        startMenu.addSeparator();
        startMenu.add(myPauseMntm);
        startMenu.addSeparator();
        startMenu.add(myEndGameMntm);
        startMenu.addSeparator();
        startMenu.add(myMuteMntm);
        return startMenu;
    }

    /**
     * Initializes pause sequence.
     */
    protected void initPause() {
        if (myPause) {
            SoundEffect.PAUSE.play();
            myBoardTimer.start();
            if (!myMuteMntm.isSelected()) {
                myMidiPlayer.start();
                SoundEffect.setVolume(SoundEffect.Volume.HIGH);
            }
            firePropertyChange(START_GAME_PROPERTY, null, true);
            myPause = false;
            myPauseMntm.setText(PAUSE);
            myMuteMntm.setEnabled(true);
        } else {
            SoundEffect.PAUSE.play();
            SoundEffect.setVolume(SoundEffect.Volume.MUTE);
            myBoardTimer.stop();
            myMidiPlayer.stop();
            firePropertyChange(STOP_GAME_PROPERTY, null, true);
            myPause = true;
            myPauseMntm.setText("Play");
            myMuteMntm.setEnabled(false);
        }
    }

    /**
     * Start game.
     */
    public void startGame() {
        if (myBoard == null) {
            myBoard = new Board(BOARD_WIDTH, BOARD_HEIGHT, null);
            myBoard.addObserver(myBoardQueue);
            myBoardTimer = new Timer(0, new BoardTimer(myBoard));
            myBoardTimer.setDelay(INIT_DELAY);
            myDelayAdjuster = INIT_DELAY;
            myBoardTimer.start();
            initBackgroundMusic();
        } else {
            initBackgroundMusic();
            if (myMuteState) {
                myMidiPlayer.stop();
            }
            myDelayAdjuster = INIT_DELAY;
            myBoard.newGame(BOARD_WIDTH, BOARD_HEIGHT, null);
            myBoardTimer.setDelay(INIT_DELAY);
            myBoardTimer.restart();
        }
        firePropertyChange(RESET_DATA_PROPERTY, null, true);
        firePropertyChange(START_GAME_PROPERTY, null, true);
        SoundEffect.setVolume(SoundEffect.Volume.HIGH);
        myPauseMntm.setText(PAUSE);
        myPauseMntm.setEnabled(true);
        myNewGameMntm.setEnabled(false);
        myEndGameMntm.setEnabled(true);
        myMuteMntm.setEnabled(true);
        myMuteMntm.setSelected(myMuteState);
    }

    /**
     * Starts background music.
     */
    private void initBackgroundMusic() {
        try {
            final File midiFile = new File("SoundTheme/main.mid");
            final Sequence song = MidiSystem.getSequence(midiFile);
            myMidiPlayer = MidiSystem.getSequencer();
            myMidiPlayer.open();
            myMidiPlayer.setSequence(song);
            myMidiPlayer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
            myMidiPlayer.start();
        } catch (final MidiUnavailableException | InvalidMidiDataException
                        | IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Creates a Menu to provide scaling.
     * 
     * @return the window menu to be added to the bar.
     */
    private JMenu createWinMenu() {
        final JMenu winMenu = new JMenu("Window");
        final JMenuItem scale = new JMenuItem("Scale");
        scale.setAccelerator(KeyStroke.getKeyStroke("control shift W"));
        scale.addActionListener(new ActionListener() {
            private ScaleDialog myDialog;

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                if (myFirstLaunch) {
                    myFirstLaunch = false;
                    myDialog = new ScaleDialog();
                    myDialog.addObserver(myScaleDialogQueue);
                } else {
                    myDialog.setLocationRelativeTo(myGuiFrame);
                }
            }
        });
        final JCheckBoxMenuItem gridMntm = new JCheckBoxMenuItem("Grid");
        gridMntm.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                if (gridMntm.isSelected()) {
                    firePropertyChange(GRID_PROPERTY, null, true);
                } else {
                    firePropertyChange(GRID_PROPERTY, null, false);
                }

            }

        });
        winMenu.add(scale);
        winMenu.addSeparator();
        winMenu.add(gridMntm);
        return winMenu;
    }

    /**
     * Creates an about menu.
     * 
     * @return the about menu.
     */
    private JMenu createAboutMenu() {
        final JMenu aboutMenu = new JMenu("About");
        final JMenuItem aboutGameMntm = new JMenuItem("Game");
        aboutGameMntm.setAccelerator(KeyStroke.getKeyStroke("control shift G"));
        aboutGameMntm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                JOptionPane.showMessageDialog(myGuiFrame,
                                              "Scoring " + "\n 1 line cleared: 10pts"
                                                          + "\n 2 lines cleared: 25pts"
                                                          + "\n 3 lines cleared: 50pts"
                                                          + "\n 4 lines cleared: 100pts"
                                                          + "\n Score doubles every level"
                                                          + " advanced.");
            }
        });

        final JMenuItem keyMntm = new JMenuItem("Keys");
        keyMntm.setAccelerator(KeyStroke.getKeyStroke("control shift K"));
        keyMntm.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                new KeyDialog();
            }
        });
        aboutMenu.add(aboutGameMntm);
        aboutMenu.addSeparator();
        aboutMenu.add(keyMntm);
        return aboutMenu;
    }

    /**
     * Ends the current game.
     */
    public void endGame() {
        myMidiPlayer.stop();
        myMidiPlayer.close();
        myBoardTimer.stop();
        SoundEffect.GAMEOVER.play();
        firePropertyChange(STOP_GAME_PROPERTY, null, true);
        firePropertyChange(END_GAME_PROPERTY, null, true);
        myPauseMntm.setEnabled(false);
        myPause = false;
        myNewGameMntm.setEnabled(true);
        myEndGameMntm.setEnabled(false);
        myMuteMntm.setEnabled(false);
        myMuteMntm.setSelected(myMuteState);
    }

    @Override
    public void update(final Observable theObservable, final Object theArg) {
        if (theObservable instanceof BoardQueue) {
            final BoardQueue queue = (BoardQueue) theObservable;
            myBoard = queue.getBoard();
            if (myBoard.isGameOver()) {
                endGame();
            }
        }
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (theEvent.getPropertyName().equals("delay")) {
            myDelayAdjuster *= DECREASE;
            myBoardTimer.setDelay(myDelayAdjuster);
        }
        if (theEvent.getPropertyName().equals("scaleQueue")) {
            myScaleDialogQueue = (ScaleDialogQueue) theEvent.getNewValue();
        }
        if (theEvent.getPropertyName().equals("boardQueue")) {
            myBoardQueue = (BoardQueue) theEvent.getNewValue();
        }
        if (theEvent.getPropertyName().equals("gui")) {
            myGuiFrame = (JFrame) theEvent.getNewValue();
        }
    }
}
