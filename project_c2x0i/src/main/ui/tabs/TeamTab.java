// Code based on SmartHome and C3LectureLabStarter
// https://github.students.cs.ubc.ca/CPSC210/LongFormProblemStarters.git
// https://github.students.cs.ubc.ca/CPSC210/C3-LectureLabStarter.git

package ui.tabs;

import model.Event;
import model.EventLog;
import model.Team;
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

public class TeamTab extends Tab {
    private static final int PADDING = 50;
    private static final int ROW_HEIGHT = 50;
    private static final int CENTER = SwingConstants.CENTER;

    private String[] stringTeams = {"Fusion", "NSGSC", "ASA Cascades", "Mountain"};
    private String[] emptyString = {};
    private Border border;
    private GridLayout rowLayout;
    private JComboBox teamCombo;
    private String teamChoice;

    private JPanel imageRow;
    private JPanel row1;
    private JPanel row3;
    private JPanel row4;
    private JPanel tableRow;

    public TeamTab(LeagueManagerUI controller) {
        super(controller);

        border = BorderFactory.createEmptyBorder(0, PADDING, 5, PADDING);
        rowLayout = new GridLayout(1, 2);
        row1();
        row3();
        row4();
        table();
        seeStats();
    }

    // MODIFIES: this
    // EFFECTS: displays a team label and a dropdown with all teams
    // Selected team is stored
    public void row1() {
        row1 = new JPanel(rowLayout);
        row1.setPreferredSize(new Dimension(LeagueManagerUI.WIDTH, ROW_HEIGHT));
        row1.setBorder(border);

        row1.add(new JLabel("Team", CENTER));

        teamCombo = new JComboBox(stringTeams);
        teamCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                teamChoice = (String) teamCombo.getSelectedItem();
            }
        });
        row1.add(teamCombo);
        add(row1);
    }

    // MODIFIES: this
    // EFFECTS: displays a Roster label and a list
    public void row3() {
        row3 = new JPanel(rowLayout);
        row3.setPreferredSize(new Dimension(LeagueManagerUI.WIDTH - PADDING, ROW_HEIGHT * 2));
        row3.setBorder(border);

        row3.add(new JLabel("Roster", CENTER));

        JList<String> list = new JList(); //data has type Object[]
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(-1);
        list.setListData(emptyString);

        row3.add(list);
        add(row3);
    }

    // MODIFIES: this
    // EFFECTS: displays a Goalscorers label and an empty list
    public void row4() {
        row4 = new JPanel(rowLayout);
        row4.setPreferredSize(new Dimension(LeagueManagerUI.WIDTH - PADDING, ROW_HEIGHT * 2));
        row4.setBorder(border);

        row4.add(new JLabel("Goalscorers", CENTER));

        JList<String> list = new JList(); //data has type Object[]
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(-1);
        list.setListData(emptyString);

        row4.add(list);
        add(row4);
    }

    // MODIFIES: this
    // EFFECTS: displays a table with all GamesPlayed, Points, Wins, Losses, Ties, GoalsFor, GoalsAgainst
    // All statistics displayed as 0
    public void table() {
        tableRow = new JPanel(rowLayout);
        tableRow.setPreferredSize(new Dimension(LeagueManagerUI.WIDTH - PADDING, ROW_HEIGHT));
        tableRow.setBorder(border);

        String[] columnNames = {
                "GP",
                "P",
                "W",
                "L",
                "T",
                "GF",
                "GA"};
        Object[][] data = {
                {0, 0, 0, 0, 0, 0, 0}};

        JTable table = new JTable(data, columnNames);
        tableRow.add(new JScrollPane(table));

        add(tableRow);
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
    // EFFECTS: adds a search teams button
    // On click, removes all rows except row with team dropdown (row1)
    // Adds refreshed rows
    public void seeStats() {
        JButton searchButton = new JButton("Search");
        searchButton.setActionCommand("search");
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeAll();
                repaint();
                searchRow1();
                searchImage();
                searchRow3();
                searchRow4();
                searchTable();
                seeStats();
            }
        });
        add(searchButton);
    }

    // MODIFIES: this
    // EFFECTS: displays a team label and a dropdown with all teams, and previous selected team selected from dropdown
    public void searchRow1() {
        row1 = new JPanel(rowLayout);
        row1.setPreferredSize(new Dimension(LeagueManagerUI.WIDTH, ROW_HEIGHT));
        row1.setBorder(border);

        row1.add(new JLabel("Team", CENTER));

        teamCombo = new JComboBox(stringTeams);
        teamCombo.setSelectedIndex(findIndex(teamChoice));
        teamCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                teamChoice = (String) teamCombo.getSelectedItem();
            }
        });
        row1.add(teamCombo);
        add(row1);
    }

    // EFFECTS: returns index of given team's name in LeagueManager's list of teams
    public int findIndex(String team) {
        int index = 0;
        for (Team t: getController().getTeams()) {
            if (t.getName().equals(team)) {
                index = getController().getTeams().indexOf(t);
            }
        }
        return index;
    }

    // MODIFIES: this
    // EFFECTS: displays appropriate team logo based on selected team
    public void searchImage() {
        imageRow = new JPanel(rowLayout);
        imageRow.setPreferredSize(new Dimension(LeagueManagerUI.WIDTH, ROW_HEIGHT * 2));
        imageRow.setBorder(border);
        BufferedImage myPicture = null;
        try {
            if (teamChoice.toLowerCase().equals("fusion")) {
                myPicture = ImageIO.read(new File("./data/fusion.png"));
            } else if (teamChoice.toLowerCase().equals("nsgsc")) {
                myPicture = ImageIO.read(new File("./data/nsgsc.png"));
            } else if (teamChoice.toLowerCase().equals("asa cascades")) {
                myPicture = ImageIO.read(new File("./data/asa.png"));
            } else if (teamChoice.toLowerCase().equals("mountain")) {
                myPicture = ImageIO.read(new File("./data/mountain.png"));
            } else {
                myPicture = ImageIO.read(new File("./data/emptyImage.png"));
            }
        } catch (IOException e) {
            //handle exception
        }
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        imageRow.add(picLabel);

        add(imageRow);
    }

    // MODIFIES: this
    // EFFECTS: displays a Roster label and a list of name of players on the selected team's roster
    public void searchRow3() {
        row3 = new JPanel(rowLayout);
        row3.setPreferredSize(new Dimension(LeagueManagerUI.WIDTH - PADDING, ROW_HEIGHT * 2));
        row3.setBorder(border);

        row3.add(new JLabel("Roster", CENTER));

        Team team = selectTeam(teamChoice);

        String[] stringPlayers = new String[team.getRoster().size()];
        for (int i = 0; i < team.getRoster().size(); i++) {
            stringPlayers[i] = team.getRoster().get(i).getFullName();
        }

        JList<String> list = new JList(); //data has type Object[]
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(-1);
        list.setListData(stringPlayers);

        row3.add(new JScrollPane(list));
        add(row3);
    }

    // MODIFIES: this
    // EFFECTS: displays a Goalscorers label and a list of all selected team's goalscorers (name and goals)
    public void searchRow4() {
        row4 = new JPanel(rowLayout);
        row4.setPreferredSize(new Dimension(LeagueManagerUI.WIDTH - PADDING, ROW_HEIGHT * 2));
        row4.setBorder(border);

        row4.add(new JLabel("Goalscorers", CENTER));

        Team team = selectTeam(teamChoice);
        team.goalscorers();

        String[] stringPlayers = new String[team.getTeamGoalscorers().size()];
        for (int i = 0; i < team.getTeamGoalscorers().size(); i++) {
            stringPlayers[i] = team.getTeamGoalscorers().get(i).getFullName() + ": "
                    + team.getTeamGoalscorers().get(i).getGoals();
        }

        JList<String> list = new JList();
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(-1);
        list.setListData(stringPlayers);

        row4.add(new JScrollPane(list));
        add(row4);
    }

    // MODIFIES: this
    // EFFECTS: displays a table with all GamesPlayed, Points, Wins, Losses, Ties, GoalsFor, GoalsAgainst
    // Each number corresponds to selected game's appropriate stat
    public void searchTable() {
        tableRow = new JPanel(rowLayout);
        tableRow.setPreferredSize(new Dimension(LeagueManagerUI.WIDTH - PADDING, ROW_HEIGHT));
        tableRow.setBorder(border);
        Team team = selectTeam(teamChoice);

        String[] columnNames = {
                "GP",
                "P",
                "W",
                "L",
                "T",
                "GF",
                "GA"};
        Object[][] data = {
                {team.getGamesPlayed(),
                        team.getPoints(),
                        team.getWins(),
                        team.getLoses(),
                        team.getTies(),
                        team.getGoalsFor(),
                        team.getGoalsAgainst()}};

        JTable table = new JTable(data, columnNames);
        tableRow.add(new JScrollPane(table));

        add(tableRow);
    }
}
