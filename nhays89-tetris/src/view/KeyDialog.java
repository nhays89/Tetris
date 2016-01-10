/*
 * TCSS 305 – Autumn 2015 Assignment 6 – Tetris
 */

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
/**
 * Displays keys to the user. 
 * 
 * @author Nicholas Hays
 * @version 11 December 2015
 */
public class KeyDialog extends JPanel {
    /**
     * default serial id. 
     */
    private static final long serialVersionUID = 1L;
    /**
     * The height of each cell.
     */
    private static final int CELL_HEIGHT = 30;
    /**
     * The panel width.
     */
    private static final int PANEL_WIDTH = 400;
    /**
     * The panel height.
     */
    private static final int PANEL_HEIGHT = 225;
    /**
     * The Column names.
     */
    private final String[] myColumnNames = {"Movement", "Key"};
    /**
     * Table data.
     */
    private final Object[][] myData = 
        {
        {"Move left", 'w'},
        {"Move right", 'f'},
        {"Move down", 's'},
        {"Drop", 'd'},
        {"Rotate", 'r'}
        };
    /**
     * The key dialog. 
     */
    private final JDialog myKeyDialog;
    /**
     * Panel used for border. 
     */
    private final JPanel myBorderPanel;
    /**
     * Constructs the Key Table. 
     */
    public KeyDialog() {
        super();
        myKeyDialog = new JDialog();
        myBorderPanel = new JPanel(new BorderLayout());
        init();
    }
    /**
     * Initializes object fields. 
     */
    private void init() {
        myBorderPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        myKeyDialog.setSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        myKeyDialog.setVisible(true);
        myKeyDialog.setResizable(false);
        myKeyDialog.setLayout(new BorderLayout());
        myKeyDialog.setLocationRelativeTo(null);
        final Font font = new Font("Times New Roman", Font.BOLD, 20);
        final JTable table = new JTable(myData, myColumnNames);
        table.getTableHeader().setFont(font);
        table.setForeground(Color.white);
        table.setEnabled(false);
        table.setRowHeight(CELL_HEIGHT);
        table.setBackground(Color.darkGray);
        table.setGridColor(Color.lightGray);
        table.setFont(font);
        final JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.darkGray);
        myBorderPanel.add(scrollPane, BorderLayout.CENTER);
        myKeyDialog.add(myBorderPanel, BorderLayout.CENTER);
    }
}
