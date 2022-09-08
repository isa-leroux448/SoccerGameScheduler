// Code based on SmartHome and C3LectureLabStarter
// https://github.students.cs.ubc.ca/CPSC210/LongFormProblemStarters.git
// https://github.students.cs.ubc.ca/CPSC210/C3-LectureLabStarter.git

package ui.tabs;

import ui.LeagueManagerUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SettingsTab extends Tab {
    private static final int PADDING = 50;
    private static final int ROW_HEIGHT = 50;
    private static final int CENTER = SwingConstants.CENTER;
    private Border border;
    private GridLayout rowLayout;
    LeagueManagerUI controller;

    public SettingsTab(LeagueManagerUI controller) {
        super(controller);
        this.controller = controller;
        border = BorderFactory.createEmptyBorder(0, PADDING, 5, PADDING);
        rowLayout = new GridLayout(1, 2);
        addImage();
        saveButton();
        loadButton();
    }

    // MODIFIES: this
    // EFFECTS: adds image of small soccer ball
    public void addImage() {
        JPanel row = new JPanel(rowLayout);
        row.setPreferredSize(new Dimension(LeagueManagerUI.WIDTH, ROW_HEIGHT * 2));
        row.setBorder(border);
        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File("./data/smallball.png"));
        } catch (IOException e) {
            //handle exception
        }
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        row.add(picLabel);
        add(row);
    }

    // MODIFIES: this
    // EFFECTS: adds save button
    // On click, saves the workroom to file
    public void saveButton() {
        JPanel row = new JPanel(rowLayout);
        row.setPreferredSize(new Dimension(LeagueManagerUI.WIDTH, ROW_HEIGHT));
        row.setBorder(border);
        JButton saveButton = new JButton("Save");
        saveButton.setActionCommand("save");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getController().saveWorkRoom();
            }
        });
        add(saveButton);
        add(row, CENTER);
    }

    // MODIFIES: this
    // EFFECTS: adds a load button
    // On click, loads workroom from file
    public void loadButton() {
        JPanel row = new JPanel(rowLayout);
        row.setPreferredSize(new Dimension(LeagueManagerUI.WIDTH, ROW_HEIGHT));
        row.setBorder(border);
        JButton loadButton = new JButton("Load");
        loadButton.setActionCommand("load");
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getController().loadWorkRoom();
            }
        });
        add(loadButton);
        add(row, CENTER);
    }
}
