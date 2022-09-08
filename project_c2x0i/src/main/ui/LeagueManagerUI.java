// Code based on SmartHomeUI class in SmartHome:
// https://github.students.cs.ubc.ca/CPSC210/LongFormProblemStarters.git
// addWindowListener in LeagueManager constructor based on:
// https://www.clear.rice.edu/comp310/JavaResources/frame_close.html

package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.tabs.*;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LeagueManagerUI extends JFrame {
    private static final String JSON_STORE = "./data/workroom.json";
    private WorkRoom workRoom;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private Team fusion;
    private Team nsgsc;
    private Team cascades;
    private Team mountain;

    private List<Player> playerDatabase;
    private List<Game> schedule;
    private List<Team> teams;

    public static final int PLAYER_TAB_INDEX = 0;
    public static final int GAME_TAB_INDEX = 1;
    public static final int TEAM_TAB_INDEX = 2;
    public static final int SETTINGS_TAB_INDEX = 3;

    public static final int WIDTH = 600;
    public static final int HEIGHT = 500;
    private JTabbedPane sidebar;

    public static void main(String[] args) {
        new LeagueManagerUI();
    }

        // MODIFIES: this
        // EFFECTS: creates LeagueManagerUI, loads teams, displays sidebar and tabs
    private LeagueManagerUI() {
        super("League Manager");
        setSize(WIDTH, HEIGHT);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                printLog(EventLog.getInstance());
                System.exit(0);
            }
        });

        workRoom = new WorkRoom("User's database");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        sidebar = new JTabbedPane();
        sidebar.setTabPlacement(JTabbedPane.LEFT);

        init();
        loadTabs();
        add(sidebar);

        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes teams and players
    private void init() {
        // All teams are initialized
        fusion = new Team("Fusion");
        nsgsc = new Team("NSGSC");
        cascades = new Team("ASA Cascades");
        mountain = new Team("Mountain");

        playerDatabase = new ArrayList<>();
        schedule = new ArrayList<>();
        teams = new ArrayList<>();

        teams.add(fusion);
        teams.add(nsgsc);
        teams.add(cascades);
        teams.add(mountain);
    }

    private void loadTabs() {
        JPanel teamTab = new TeamTab(this);
        JPanel gameTab = new GameTab(this);
        JPanel playerTab = new PlayerTab(this);
        JPanel settingsTab = new SettingsTab(this);

        sidebar.add(playerTab, PLAYER_TAB_INDEX);
        sidebar.setTitleAt(PLAYER_TAB_INDEX, "Players");

        sidebar.add(gameTab, GAME_TAB_INDEX);
        sidebar.setTitleAt(GAME_TAB_INDEX, "Games");

        sidebar.add(teamTab, TEAM_TAB_INDEX);
        sidebar.setTitleAt(TEAM_TAB_INDEX, "Teams");

        sidebar.add(settingsTab, SETTINGS_TAB_INDEX);
        sidebar.setTitleAt(SETTINGS_TAB_INDEX, "Settings");
    }

    // EFFECTS: loads the workroom to file
    // sets all team variables to teams from workroom and records their goalscorers
    public void loadWorkRoom() {
        try {
            workRoom = jsonReader.read();
            teams = workRoom.getTeams();
            playerDatabase = workRoom.getPlayers();
            schedule = workRoom.getGames();

            fusion = teams.get(0);
            nsgsc = teams.get(1);
            cascades = teams.get(2);
            mountain = teams.get(3);

            fusion.recordGoalscorers();
            nsgsc.recordGoalscorers();
            cascades.recordGoalscorers();
            mountain.recordGoalscorers();

            System.out.println("Loaded " + workRoom.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: saves the workroom to file
    public void saveWorkRoom() {
        try {
            workRoom.setGames(schedule);
            workRoom.setPlayers(playerDatabase);
            workRoom.setTeams(teams);
            jsonWriter.open();
            jsonWriter.write(workRoom);
            jsonWriter.close();
            System.out.println("Saved all teams, players and games to database");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: prints all events in EventLog to console
    public void printLog(EventLog el) {
        for (Event event: el) {
            String eventString = event.toString();
            System.out.println(eventString);
        }
    }

    // getters
    public List<Player> getPlayerDatabase() {
        return playerDatabase;
    }

    public List<Game> getSchedule() {
        return schedule;
    }

    public List<Team> getTeams() {
        return teams;
    }
}

