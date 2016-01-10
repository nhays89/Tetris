/*
 * TCSS 305 – Autumn 2015 
 * Assignment 6 – Tetris
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import listeners.ClockTimer;
import sound.SoundEffect;
/**
 * Creates an Information Panel to display information to the user during game play. 
 * 
 * @author Nicholas A. Hays
 * @version 9 December 2015
 */
public class InfoPanel extends JPanel implements Observer, PropertyChangeListener {
    /**
     * Default serial id. 
     */
    private static final long serialVersionUID = 1L;
    /**
     * current font name.
     */
    private static final String FONT_NAME = "Times New Roman";
    /**
     * Score label string.
     */
    private static final String SCORE = "Score: ";
    /**
     * Default time label.
     */
    private static final String TIME = "Time: ";
    /**
     * Lines cleared label.
     */
    private static final String LINES_CLEARED = "Lines Cleared: ";
    /**
     * boarder spacing.
     */
    private static final int BORDER_SPACING = 10;
    /**
     * 60 seconds to a minute. 
     */
    private static final int SEC_PER_MIN = 60;
    /**
     * 120 seconds per level.
     */
    private static final int SEC_PER_LVL = 120;
    /**
     * warning to the user that the time is winding down. 
     */
    private static final int TIME_RUNNING_OUT = 10;
    /**
     * Default scale factor.
     */
    private static final int DEFAULT_SCALE = 25;
    /**
     * Level label.
     */
    private static final String LEVEL = "Level: ";
    /**
     * Default block size.
     */
    private static final int PREVIEW_PANEL_WIDTH = 5;
    /**
     * Default block size.
     */
    private static final int PREVIEW_PANEL_HEIGHT = 20;
    /**
     * checks if one line is cleared from board.
     */
    private static final int ONE_LINE_CLEARED = 1;
    /**
     * checks if two line is cleared from board.
     */
    private static final int TWO_LINE_CLEARED = 2;
    /**
     * checks if three line is cleared from board.
     */
    private static final int THREE_LINE_CLEARED = 3;
    /**
     * Updates myScore by 10 points.
     */
    private static final int TEN_PTS = 10;
    /**
     * Updates myScore by 15 points. 
     */
    private static final int FIFTEEN_PTS = 15;
    /**
     * Updates myScore by 25 points.
     */
    private static final int TWENTY_FIVE_PTS = 25;
    /**
     * Updates myScore by 50 points.
     */
    private static final int FIFTY_PTS = 50;
    /**
     * The Delay of the clock timer. 
     */
    private static final int DELAY = 1000;
    /**
     * Current score.
     */
    private int myScore;
    /**
     * Keeps track of current lines cleared.
     */
    private int myTotalLinesCleared;
    /**
     * Scale factor.
     */
    private final int myScale;
    /**
     * Level time in seconds.
     */
    private int myLevelTime;
    /**
     * My Clock Timer. 
     */
    private final Timer myClockTimer;
    /**
     * current level.
     */
    private int myCurrentLevel;
    /**
     * List of Labels.
     */
    private List<JLabel> myLabels;
    /**
     * Score label.
     */
    private final JLabel myScoreLbl;
    /**
     * Time label.
     */
    private final JLabel myTimeLbl;
    /**
     * Lines cleared label.
     */
    private final JLabel myLinesClearedLbl;
    /**
     * Level Label.
     */
    private final JLabel myLevelLbl;
    /**
     * Default color.
     */
    private Color myTimerColor = Color.lightGray;
 
   

    /**
     * Constructs an information panel which displays current statistics to the
     * user.
     */
    public InfoPanel() {
        super(true);
        myClockTimer = new Timer(0, new ClockTimer(this));
        myClockTimer.setDelay(DELAY);
        myScoreLbl = new JLabel(SCORE);
        myLevelTime = SEC_PER_LVL;
        myScale = DEFAULT_SCALE;
        myTimeLbl = new JLabel(TIME);
        myLinesClearedLbl = new JLabel(LINES_CLEARED);
        myLevelLbl = new JLabel(LEVEL);
        init();
    }
    /**
     * Initializes objects. 
     */
    private void init() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.darkGray);
        myLabels = new ArrayList<JLabel>();
        createLabels(new Font(FONT_NAME, Font.BOLD, myScale / 2));
        addLabels();
    }

    /**
     * Creates the Labels.
     * 
     * @param theFont the font to be used.
     */
    private void createLabels(final Font theFont) {
        if (!myLabels.isEmpty()) {
            myLabels.clear();
        }
        myLabels.add(initLabel(myLevelLbl, theFont));
        myLabels.add(initLabel(myTimeLbl, theFont));
        myLabels.add(initLabel(myScoreLbl, theFont));
        myLabels.add(initLabel(myLinesClearedLbl, theFont));
    }

    /**
     * Initializes the Labels.
     * 
     * @param theLabel the Label to apply the font to.
     * @param theFont the font object to be used for each label.
     * @return the JLabel to add to the Panel.
     */
    private JLabel initLabel(final JLabel theLabel, final Font theFont) {
        theLabel.setFont(theFont);
        return theLabel;
    }
    /**
     * Adds Labels to information panel. 
     */
    private void addLabels() {
        this.removeAll();
        for (final JLabel lbl : myLabels) {
            lbl.setForeground(Color.lightGray);
            add(lbl);
            setBorder(BorderFactory.createEmptyBorder(BORDER_SPACING, 
                                                      BORDER_SPACING, 
                                                      BORDER_SPACING, 
                                                      BORDER_SPACING));
        }
    }
    /**
     * Updates the time to be displayed on the panel. 
     */
    public void updateTime() {
        final int minutes = myLevelTime / SEC_PER_MIN;
        final int seconds = myLevelTime % SEC_PER_MIN;
        myLevelTime -= 1;
        final String updatedTime = String.format("%1d" + ":" + "%02d", minutes, seconds);
        if (minutes == 0 && seconds == TIME_RUNNING_OUT) {
            myTimerColor = Color.red;
            myTimeLbl.setForeground(Color.red);
        } else if (minutes == 2) {
            myTimerColor = Color.lightGray;
            myTimeLbl.setForeground(Color.lightGray);
        }
        if (minutes == 0 && seconds == 0) {
            myScore += myScore;
            SoundEffect.LEVELUP.play();
            myLevelLbl.setText(LEVEL + ++myCurrentLevel);
            myScoreLbl.setText(SCORE + myScore);
            firePropertyChange("delay", null, true);
            myLevelTime = SEC_PER_LVL;
        }
        myTimeLbl.setText(TIME + updatedTime);
    }
    /**
     * Initializes data for information panel. 
     */
    public void initData() {
        myCurrentLevel = 1;
        myLevelTime = SEC_PER_LVL;
        myTotalLinesCleared = 0;
        myScore = 0;
        myTimeLbl.setForeground(Color.lightGray);
        myTimeLbl.setText(TIME);
        myScoreLbl.setText(SCORE + myScore);
        myLinesClearedLbl.setText(LINES_CLEARED + myTotalLinesCleared);
        myLevelLbl.setText(LEVEL + myCurrentLevel);
    }

    @Override
    public void update(final Observable theObservable, final Object theObj) {
        if (theObservable instanceof ScaleDialogQueue) {
            final ScaleDialogQueue queue = (ScaleDialogQueue) theObservable;
            final ScaleDialog dialog = queue.getScaleDialog();
            final int scaler = dialog.getScale();
            createLabels(new Font(FONT_NAME, Font.BOLD, scaler / 2));
            setPreferredSize(new Dimension(myScale * PREVIEW_PANEL_WIDTH, 
                                           (PREVIEW_PANEL_HEIGHT * scaler)
                                                           - (PREVIEW_PANEL_WIDTH * scaler)));
            myTimeLbl.setForeground(myTimerColor);
        }
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (theEvent.getPropertyName().equals("score")) {
            final int linesCleared = (int) theEvent.getNewValue();
            if (linesCleared == 0) {
                return;
            }
            if (linesCleared == ONE_LINE_CLEARED) {
                myScore += TEN_PTS;
                SoundEffect.SCORE.play();
            } else if (linesCleared == TWO_LINE_CLEARED) {
                myScore += FIFTEEN_PTS;
            } else if (linesCleared == THREE_LINE_CLEARED) {
                myScore += TWENTY_FIVE_PTS;
            } else {
                myScore += FIFTY_PTS;
            }
            myTotalLinesCleared += 1;
            myScoreLbl.setText(SCORE + myScore);
            myLinesClearedLbl.setText(LINES_CLEARED + myTotalLinesCleared);
        }
        if (theEvent.getPropertyName().equals("resetData")) {
            initData();
        }
        if (theEvent.getPropertyName().equals("startGameClock")) {
            myClockTimer.start();
        }
        if (theEvent.getPropertyName().equals("stopGameClock")) {
            myClockTimer.stop();
        }
    }
}
