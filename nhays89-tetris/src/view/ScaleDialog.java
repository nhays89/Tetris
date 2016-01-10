/*
 * TCSS 305 – Autumn 2015 
 * Assignment 6 – Tetris
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Observable;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/**
 * Creates a slider to scale the tetris panel. 
 * 
 * @author Nicholas Hays
 * @version 3 December 2015
 */
public class ScaleDialog extends Observable {
    /**
     * slider width.
     */
    private static final int SLIDER_MAX = 100;
    /**
     * width.
     */
    private static final int WIDTH = 400;
    /**
     * height.
     */
    private static final int HEIGHT = 150;
    /**
     * default scale.
     */
    private static final int DEFAULT_SCALE = 25;
    /**
     * major tick spacing.
     */
    private static final int MAJOR_TICK = 10;
    /**
     * minor tick spacing. 
     */
    private static final int MINOR_TICK = 2;
    /**
     * scale.
     */
    private int myScale;
    /**
     * the dialog.
     */
    private final JDialog myDialog;
    /**
     * the slider. 
     */
    private final JSlider mySlider;
    /**
     * Constructs a scale dialog to display possible scale values. 
     */
    public ScaleDialog() {
        super();
        myDialog = new JDialog();
        myScale = DEFAULT_SCALE;
        mySlider = new JSlider(JSlider.HORIZONTAL, DEFAULT_SCALE, SLIDER_MAX, DEFAULT_SCALE);
        myDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        initDialog();
    }
    /**
     * Initializes the JDialog. 
     */
    private void initDialog() {
        myDialog.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        myDialog.setResizable(false);
        myDialog.setBackground(Color.black);
        myDialog.setTitle("Scale Window");
        mySlider.setMajorTickSpacing(MAJOR_TICK);
        mySlider.setMinorTickSpacing(MINOR_TICK);
        mySlider.setPaintTicks(true);
        myDialog.setLocationRelativeTo(null);
        myDialog.setVisible(true);
        myDialog.pack();
        myDialog.setLayout(new BorderLayout());
        setDefaultSkin();
        SwingUtilities.updateComponentTreeUI(myDialog);
        setListeners();
    }
    /**
     * Sets the default skin applied to Dialog components. 
     */
    private void setDefaultSkin() {
        final UIDefaults uiDefaults = UIManager.getLookAndFeelDefaults();
        uiDefaults.put("Slider.background", Color.BLACK);
        uiDefaults.put("Slider.tickColor", Color.WHITE);
        uiDefaults.put("Button.background", Color.black);
        uiDefaults.put("Button.foreground", Color.white);
        mySlider.putClientProperty("Nimbus.Overrides", uiDefaults);
        mySlider.putClientProperty("Nimbus.Overrides.InheritDefaults", false);
        setListeners();
    }
    /**
     * Attaches each listener to the Dialog. 
     */
    private void setListeners() {
        mySlider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(final ChangeEvent theEvent) {
                myScale = mySlider.getValue();
                setScale();
            }
        });

        final JPanel southPanel = new JPanel(new FlowLayout());
        myDialog.getContentPane().add(southPanel, BorderLayout.SOUTH);
        southPanel.setBackground(Color.BLACK);

        final JButton okBtn = new JButton("Ok");
        okBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                setScale();
                myDialog.setVisible(false);
            }
        });

        final JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myDialog.setVisible(false);
                close();
            }

        });
        okBtn.setVisible(true);
        cancelBtn.setVisible(true);
        southPanel.add(okBtn);
        southPanel.add(cancelBtn);
        myDialog.getContentPane().add(mySlider, BorderLayout.CENTER);
    }
    /**
     * Closes the window and dispatches a window event. 
     */
    private void close() {
        myDialog.dispatchEvent(new WindowEvent(myDialog, WindowEvent.WINDOW_CLOSING));
    }
    /**
     * Gets the scale factor used by all observers.
     *  
     * @return the scale factor.
     */
    public int getScale() {
        return myScale;
    }
    /**
     * Sets the scale factor used by all observers. 
     */
    public void setScale() {
        this.setChanged();
        this.notifyObservers();
    }
    /**
     * Sets this dialog relative to the GUI. 
     * 
     * @param theGuiFrame the Component to set this object relative to. 
     */
    public void setLocationRelativeTo(final JFrame theGuiFrame) {
        myDialog.setLocationRelativeTo(theGuiFrame);
        myDialog.setVisible(true);
    }

}
