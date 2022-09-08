// Code based on SmartHome and C3LectureLabStarter
// https://github.students.cs.ubc.ca/CPSC210/LongFormProblemStarters.git
// https://github.students.cs.ubc.ca/CPSC210/C3-LectureLabStarter.git

package ui.tabs;

import model.Player;
import model.Team;
import ui.LeagueManagerUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerTab extends Tab {
    private static final int PADDING = 50;
    private static final int ROW_HEIGHT = 50;
    private static final int CENTER = SwingConstants.CENTER;

    private Border border;
    private GridLayout rowLayout;
    private JScrollPane reportPane;

    // fields for creating player
    private JTextArea firstName;
    private JTextArea lastName;
    private JComboBox playerTeam;

    // rows
    private JPanel row2;
    private JPanel row3;
    private JPanel row4;
    private JPanel row5;

    private String[] stringTeams = {"Fusion", "NSGSC", "ASA Cascades", "Mountain"};
    private String teamChoice;
    private JComboBox playerCombo;
    private int playerIndex;

    public PlayerTab(LeagueManagerUI controller) {
        super(controller);

        border = BorderFactory.createEmptyBorder(0, PADDING, 5, PADDING);
        rowLayout = new GridLayout(1, 2);
        initialize();
    }

    // MODIFIES: this
    // EFFECTS: displays initial state of player's tab
    public void initialize() {
        placeRow1();
        placeRow2();
        placeRow3();
        placeRow4();
        placeRow5();
        refresh();
    }

    // MODIFIES: this
    // EFFECTS: displays an add player label and a players statistics label
    public void placeRow1() {
        JPanel row1 = new JPanel(rowLayout);
        row1.setPreferredSize(new Dimension(LeagueManagerUI.WIDTH - PADDING, ROW_HEIGHT));

        row1.add(new JLabel("Add Player", CENTER));
        row1.add(new JLabel("Player Statistics", SwingConstants.CENTER));

        add(row1);
    }

    // MODIFIES: this
    // EFFECTS: displays a dropdown with all teams and a dropdown with all players from LeagueManager
    // stores selected team and dropdown index of selected player
    public void placeRow2() {
        row2 = new JPanel(rowLayout);
        row2.setPreferredSize(new Dimension(LeagueManagerUI.WIDTH, ROW_HEIGHT));
        row2.setBorder(border);

        playerTeam = new JComboBox(stringTeams);
        playerTeam.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                teamChoice = (String) playerTeam.getSelectedItem();
            }
        });
        row2.add(playerTeam);

        String[] stringPlayers = new String[getController().getPlayerDatabase().size()];
        for (int i = 0; i < getController().getPlayerDatabase().size(); i++) {
            stringPlayers[i] = getController().getPlayerDatabase().get(i).getFullName();
        }

        playerCombo = new JComboBox(stringPlayers);
        playerCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playerIndex = playerCombo.getSelectedIndex();
            }
        });

        row2.add(playerCombo);

        add(row2);
    }

    // MODIFIES: this
    // EFFECTS: adds a first name label, modifiable text box and team label
    public void placeRow3() {
        row3 = new JPanel(rowLayout);
        row3.setPreferredSize(new Dimension(LeagueManagerUI.WIDTH, ROW_HEIGHT));
        row3.setBorder(border);

        row3.add(new JLabel("First name", CENTER));

        JPanel reportBlock = new JPanel(new GridLayout(2, 1));
        reportBlock.setSize(LeagueManagerUI.WIDTH - (LeagueManagerUI.WIDTH / 5),
                LeagueManagerUI.HEIGHT - (LeagueManagerUI.HEIGHT / 5));
        firstName = new JTextArea(1, 1);
        reportPane = new JScrollPane(firstName);

        reportBlock.add(reportPane);

        row3.add(reportBlock);

        row3.add(new JLabel("Team:", CENTER));
        row3.add(new JLabel(" ", CENTER));

        add(row3);
    }

    // MODIFIES: this
    // EFFECTS: adds a last name label, a modifiable textbox and a goals label
    public void placeRow4() {
        row4 = new JPanel(rowLayout);
        row4.setPreferredSize(new Dimension(LeagueManagerUI.WIDTH, ROW_HEIGHT));
        row4.setBorder(border);

        row4.add(new JLabel("Last name", CENTER));

        JPanel reportBlock = new JPanel(new GridLayout(2, 1));
        reportBlock.setSize(LeagueManagerUI.WIDTH - (LeagueManagerUI.WIDTH / 5),
                LeagueManagerUI.HEIGHT - (LeagueManagerUI.HEIGHT / 5));
        lastName = new JTextArea(1, 1);
        reportPane = new JScrollPane(lastName);

        reportBlock.add(reportPane);

        row4.add(reportBlock);

        row4.add(new JLabel("Goals:", CENTER));
        row4.add(new JLabel(" ", CENTER));

        add(row4);
    }

    // MODIFIES: this
    // EFFECTS: displays an addplayer button and a see player button
    public void placeRow5() {
        row5 = new JPanel(rowLayout);
        row5.setPreferredSize(new Dimension(LeagueManagerUI.WIDTH, ROW_HEIGHT));
        row5.setBorder(border);
        placeAddPlayerButton();
        placeSeePlayerStatsButton();
        add(row5);
    }

    // MODIFIES: this, LeagueManager
    // EFFECTS: creates a addplayer button
    // On click, creates a new player with firstname, last name fields and selected team
    // Adds player to LeagueManager's players
    // Removes all rows from panel and replaces all rows
    public void placeAddPlayerButton() {
        JButton addPlayerButton = new JButton("Add Player");
        addPlayerButton.setActionCommand("addPlayer");
        addPlayerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Player player = new Player(selectTeam(teamChoice), firstName.getText(), lastName.getText());
                getController().getPlayerDatabase().add(player);

                removeAll();
                placeRow1();
                placeRow2();
                placeRow3();
                placeRow4();
                placeRow5();
                refresh();
            }
        });
        add(addPlayerButton);
    }

    // MODIFIES: this, LeagueManager
    // EFFECTS: creates a seePlayerStatsButton
    // On click, removes all rows and adds updated rows 3 and 4, with player's team and goals
    public void placeSeePlayerStatsButton() {
        JButton statsButton = new JButton("See Player Stats");
        statsButton.setActionCommand("playerStats");
        statsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeAll();
                placeRow1();
                placeRow2();
                searchRow3();
                searchRow4();
                placeRow5();
                refresh();
            }
        });
        add(statsButton);
    }

    // MODIFIES: this
    // EFFECTS: adds a first name label, modifiable text box, team label and selected player's team's name
    public void searchRow3() {
        row3 = new JPanel(rowLayout);
        row3.setPreferredSize(new Dimension(LeagueManagerUI.WIDTH, ROW_HEIGHT));
        row3.setBorder(border);

        row3.add(new JLabel("First name", CENTER));

        JPanel reportBlock = new JPanel(new GridLayout(2, 1));
        reportBlock.setSize(LeagueManagerUI.WIDTH - (LeagueManagerUI.WIDTH / 5),
                LeagueManagerUI.HEIGHT - (LeagueManagerUI.HEIGHT / 5));
        firstName = new JTextArea(1, 1);
        reportPane = new JScrollPane(firstName);

        reportBlock.add(reportPane);

        row3.add(reportBlock);

        row3.add(new JLabel("Team:", CENTER));
        row3.add(new JLabel(getController().getPlayerDatabase().get(playerIndex).getTeam().getName(), CENTER));

        add(row3);
    }

    // MODIFIES: this
    // EFFECTS: adds a first name label, modifiable text box, team label and selected player's goals
    public void searchRow4() {
        row4 = new JPanel(rowLayout);
        row4.setPreferredSize(new Dimension(LeagueManagerUI.WIDTH, ROW_HEIGHT));
        row4.setBorder(border);

        row4.add(new JLabel("Last name", CENTER));

        JPanel reportBlock = new JPanel(new GridLayout(2, 1));
        reportBlock.setSize(LeagueManagerUI.WIDTH - (LeagueManagerUI.WIDTH / 5),
                LeagueManagerUI.HEIGHT - (LeagueManagerUI.HEIGHT / 5));
        lastName = new JTextArea(1, 1);
        reportPane = new JScrollPane(lastName);

        reportBlock.add(reportPane);

        row4.add(reportBlock);

        row4.add(new JLabel("Goals:", CENTER));
        String goals = Integer.toString(getController().getPlayerDatabase().get(playerIndex).getGoals());
        row4.add(new JLabel(goals, CENTER));

        add(row4);
    }

    // EFFECTS: returns a team from LeagueManager's teams based on the provided team's name
    private Team selectTeam(String stringTeam) {
        if (stringTeam.toLowerCase().equals("fusion")) {
            return getController().getTeams().get(0);
        } else if (stringTeam.toLowerCase().equals("nsgsc")) {
            return getController().getTeams().get(1);
        } else if (stringTeam.toLowerCase().equals("cascades")) {
            return getController().getTeams().get(2);
        } else {
            return getController().getTeams().get(3);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a refresh button
    // On click, removes all rows and adds all initial rows
    public void refresh() {
        JButton statsButton = new JButton("Refresh");
        statsButton.setActionCommand("refresh");
        statsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeAll();
                initialize();
            }
        });
        add(statsButton);
    }

}
