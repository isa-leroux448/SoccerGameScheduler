// Code based on SmartHome and C3LectureLabStarter
// https://github.students.cs.ubc.ca/CPSC210/LongFormProblemStarters.git
// https://github.students.cs.ubc.ca/CPSC210/C3-LectureLabStarter.git

package ui.tabs;

import model.Game;
import model.Player;
import model.Team;
import ui.LeagueManagerUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GameTab extends Tab {

    private static final int PADDING = 50;
    private static final int ROW_HEIGHT = 50;
    private static final int CENTER = SwingConstants.CENTER;
    private static final int LEFT = SwingConstants.LEFT;

    private JScrollPane reportPane;

    private String[] stringTeams = {"Fusion", "NSGSC", "Mountain", "Cascades"};
    private Border border;
    private GridLayout rowLayout;

    private String away;
    private String home;
    private JTextArea location;
    private JTextArea time;
    private JTextArea homeGoals;
    private JTextArea awayGoals;
    private JTextArea newTime;
    private JTextArea newLocation;

    // for easier removal (cases where removeAll() isn't used)
    private JPanel row1;
    private JPanel row2;
    private JPanel row3;
    private JPanel row4;
    private JPanel row5;
    private JButton confirmButton;

    private List<String> goalscorerNames;
    private List<Player> goalscorers;
    private String gameChoice;
    private String playerChoice;
    private Game selectedGame;

    public GameTab(LeagueManagerUI controller) {
        super(controller);

        border = BorderFactory.createEmptyBorder(0, PADDING, 5, PADDING);
        rowLayout = new GridLayout(1, 2);
        setup();
    }

    // Main window

    // MODIFIES: this
    // EFFECTS: removes all current rows from panel, adds 3 main buttons
    public void setup() {
        removeAll();
        repaint();
        gameStatsButton();
        newGameButton();
        updateGameButton();
        reportGameButton();
    }

    // MODIFIES: this
    // EFFECTS: creates and adds game stats button, button redirects user to game statistics panel
    public void gameStatsButton() {
        JPanel row = createNewRow(LeagueManagerUI.WIDTH);
        JButton button = new JButton("See Game Stats");
        button.setActionCommand("gameStats");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameStats();
            }
        });
        row.add(button);
        add(row);
    }

    // MODIFIES: this
    // EFFECTS: creates and adds new game button, button redirects user to new game panel
    public void newGameButton() {
        JPanel row = createNewRow(LeagueManagerUI.WIDTH);
        JButton button = new JButton("New Game");
        button.setActionCommand("gameStats");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newGame();
            }
        });
        row.add(button);
        add(row);
    }

    // MODIFIES: this
    // EFFECTS: creates and adds report game button, button redirects user to report game panel
    public void reportGameButton() {
        JPanel row = new JPanel(rowLayout);
        goalscorerNames = new ArrayList<>();
        goalscorers = new ArrayList<>();
        row.setPreferredSize(new Dimension(LeagueManagerUI.WIDTH, ROW_HEIGHT));
        row.setBorder(border);
        JButton button = new JButton("Report Game");
        button.setActionCommand("gameStats");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reportGame();
            }
        });
        row.add(button);
        add(row);
    }

    // MODIFIES: this
    // EFFECTS: creates and adds report update game button, button redirects user to update game panel
    public void updateGameButton() {
        JPanel row = createNewRow(LeagueManagerUI.WIDTH);
        JButton button = new JButton("Update time or location");
        button.setActionCommand("update");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                update();
            }
        });
        row.add(button);
        add(row);
    }

    // MODIFIES: this
    // EFFECTS: removes all current rows, adds back button, game dropdown with all games, game's current time
    // and location, modifiable text box for new time and location, and update buttons
    public void update() {
        removeAll();
        back();
        updateChooseGame();
        updateRow1();
        updateRow2();
        updateRow3();
        updateButtons();
    }

    // Game stats window

    // MODIFIES: this
    // EFFECTS: removes all current rows, adds back button, home team, away team, location, time and goalscorer
    public void gameStats() {
        removeAll();
        back();
        statsRow1();
        statsRow2();
        statsRow3();
        statsRow4();
        statsRow5();
        statsRow6();
        searchButton();
        refresh();
    }

    // MODIFIES: this
    // EFFECTS: displays dropdown with all created games
    // selected game is stored
    public void statsRow1() {
        row1 = new JPanel(rowLayout);
        row1.setPreferredSize(new Dimension(LeagueManagerUI.WIDTH - PADDING, ROW_HEIGHT));
        row1.setBorder(border);

        String[] stringGames = new String[getController().getSchedule().size()];
        for (int i = 0; i < getController().getSchedule().size(); i++) {
            stringGames[i] = getController().getSchedule().get(i).getTeams();
        }

        JComboBox gameCombo = new JComboBox(stringGames);
        gameCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameChoice = (String) gameCombo.getSelectedItem();
            }
        });
        row1.add(gameCombo);
        add(row1);
    }

    // MODIFIES: this
    // EFFECTS: creates and adds a search button
    // On click, removes all rows and refreshes home team, away team, location, time and goalscorer labels
    public void searchButton() {
        JPanel row = createNewRow(LeagueManagerUI.WIDTH);

        JButton statsButton = new JButton("Search Game");
        statsButton.setActionCommand("searchGame");
        statsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedGame = selectGame(gameChoice);
                removeAll();
                back();
                add(row1);
                refreshedRow2();
                refreshedRow3();
                refreshedRow4();
                refreshedRow5();
                refreshedRow6();
                searchButton();
                refresh();
            }
        });
        add(statsButton);
        add(row);
    }

    // MODIFIES: this
    // EFFECTS: displays Home team label
    public void statsRow2() {
        JPanel row = createNewRow(LeagueManagerUI.WIDTH - PADDING);

        row.add(new JLabel("Home Team:", LEFT));
        row.add(new JLabel("", CENTER));
        row.add(new JLabel("", CENTER));

        add(row);
    }

    // MODIFIES: this
    // EFFECTS: displays Away team label
    public void statsRow3() {
        JPanel row = createNewRow(LeagueManagerUI.WIDTH - PADDING);
        row.add(new JLabel("Away Team:", LEFT));
        row.add(new JLabel("", CENTER));
        row.add(new JLabel("", CENTER));

        add(row);
    }

    // MODIFIES: this
    // EFFECTS: displays location label
    public void statsRow4() {
        JPanel row = createNewRow(LeagueManagerUI.WIDTH - PADDING);
        row.add(new JLabel("Location:", LEFT));
        row.add(new JLabel("", CENTER));

        add(row);
    }

    // MODIFIES: this
    // EFFECTS: displays time label
    public void statsRow5() {
        JPanel row = createNewRow(LeagueManagerUI.WIDTH - PADDING);
        row.add(new JLabel("Time:", LEFT));
        row.add(new JLabel("", CENTER));

        add(row);
    }

    // MODIFIES: this
    // EFFECTS: displays goalscorers label (with nothing after)
    public void statsRow6() {
        JPanel row = createNewRow(LeagueManagerUI.WIDTH - PADDING);
        row.add(new JLabel("Goalscorers:", LEFT));
        row.add(new JLabel("", CENTER));

        add(row);
    }

    // MODIFIES: this
    // EFFECTS: displays back button
    // On click, return to main panel
    public void back() {
        JPanel row = createNewRow(LeagueManagerUI.WIDTH);

        JButton statsButton = new JButton("Back");
        statsButton.setActionCommand("back");
        statsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setup();
            }
        });
        add(statsButton);
        add(row);
    }

    // MODIFIES: this
    // EFFECTS: adds a refresh button
    // On click, refreshes all content on game stats panel
    public void refresh() {
        JPanel row = createNewRow(LeagueManagerUI.WIDTH);

        JButton statsButton = new JButton("Refresh");
        statsButton.setActionCommand("refresh");
        statsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameStats();
            }
        });
        add(statsButton);
        add(row);
    }

    // Refreshed

    // MODIFIES: this
    // EFFECTS: displays Home team label with selected game's home team
    // if the game has been played, also display home team goals
    public void refreshedRow2() {
        JPanel row = createNewRow(LeagueManagerUI.WIDTH - PADDING);

        row.add(new JLabel("Home Team:", LEFT));
        row.add(new JLabel(selectedGame.getHomeTeam().getName(), LEFT));

        if (selectedGame.getGamePlayed()) {
            row.add(new JLabel(String.valueOf(selectedGame.getHomeGoals()), LEFT));
        } else {
            row.add(new JLabel("", CENTER));
        }

        add(row);
    }

    // MODIFIES: this
    // EFFECTS: displays Away team label with selected game's away team
    // if the game has been played, also display away team goals
    public void refreshedRow3() {
        JPanel row = createNewRow(LeagueManagerUI.WIDTH - PADDING);
        row.add(new JLabel("Away Team:", LEFT));
        row.add(new JLabel(selectedGame.getAwayTeam().getName(), LEFT));
        if (selectedGame.getGamePlayed()) {
            row.add(new JLabel(String.valueOf(selectedGame.getAwayGoals()), LEFT));
        } else {
            row.add(new JLabel("", CENTER));
        }

        add(row);
    }

    // MODIFIES: this
    // EFFECTS: displays location label with selected game's location
    public void refreshedRow4() {
        JPanel row = createNewRow(LeagueManagerUI.WIDTH - PADDING);
        row.add(new JLabel("Location:", LEFT));
        row.add(new JLabel(selectedGame.getLocation(), LEFT));

        add(row);
    }

    // MODIFIES: this
    // EFFECTS: displays time label with selected game's time
    public void refreshedRow5() {
        JPanel row = createNewRow(LeagueManagerUI.WIDTH - PADDING);
        row.add(new JLabel("Time:", LEFT));
        row.add(new JLabel(selectedGame.getTime(), LEFT));

        add(row);
    }

    // MODIFIES: this
    // EFFECTS: displays goalscorer label with selected goalscorers as a scrollable list
    public void refreshedRow6() {
        JPanel row = createNewRow(LeagueManagerUI.WIDTH - PADDING);
        row.add(new JLabel("Goalscorers:", LEFT));

        String[] stringPlayers = new String[selectedGame.getGoalscorers().size()];
        for (int i = 0; i < selectedGame.getGoalscorers().size(); i++) {
            stringPlayers[i] = selectedGame.getGoalscorers().get(i).getFullName();
        }

        JList<String> list = new JList();
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(-1);
        list.setListData(stringPlayers);

        row.add(new JScrollPane(list));
        add(row);
    }


    // New game window

    // MODIFIES: this
    // EFFECTS: removes all current rows, adds back button, home team dropdown, away team dropdown
    // modifiable team text box, modifiable location text box and add game button
    public void newGame() {
        removeAll();
        back();
        newGameRow1();
        newGameRow2();
        newGameRow3();
        newGameRow4();
        addGameButton();
    }

    // MODIFIES: this
    // EFFECTS: displays Home team label and dropdown with all teams
    public void newGameRow1() {
        JPanel row = createNewRow(LeagueManagerUI.WIDTH - PADDING);

        row.add(new JLabel("Home Team:", CENTER));
        JComboBox gameCombo = new JComboBox(stringTeams);
        gameCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                home = (String) gameCombo.getSelectedItem();
            }
        });
        row.add(gameCombo);
        add(row);
    }

    // MODIFIES: this
    // EFFECTS: displays Away team label and dropdown with all teams
    // Possible improvement: display all teams except home team
    public void newGameRow2() {
        JPanel row = createNewRow(LeagueManagerUI.WIDTH - PADDING);
        row.add(new JLabel("Away Team:", CENTER));
        JComboBox gameCombo = new JComboBox(stringTeams);
        gameCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                away  = (String) gameCombo.getSelectedItem();
            }
        });
        row.add(gameCombo);
        add(row);
    }

    // MODIFIES: this
    // EFFECTS: displays time label and modifiable text block
    public void newGameRow3() {
        JPanel row = createNewRow(LeagueManagerUI.WIDTH);

        row.add(new JLabel("Time:", CENTER));

        JPanel reportBlock = new JPanel(new GridLayout(2, 1));
        reportBlock.setSize(LeagueManagerUI.WIDTH - (LeagueManagerUI.WIDTH / 5),
                LeagueManagerUI.HEIGHT - (LeagueManagerUI.HEIGHT / 5));
        time = new JTextArea(1, 1);
        reportPane = new JScrollPane(time);
        reportBlock.add(reportPane);

        row.add(reportBlock);

        add(row);
    }

    // MODIFIES: this
    // EFFECTS: displays location with modifiable text block
    public void newGameRow4() {
        JPanel row = createNewRow(LeagueManagerUI.WIDTH);

        row.add(new JLabel("Location:", CENTER));

        JPanel reportBlock = new JPanel(new GridLayout(2, 1));
        reportBlock.setSize(LeagueManagerUI.WIDTH - (LeagueManagerUI.WIDTH / 5),
                LeagueManagerUI.HEIGHT - (LeagueManagerUI.HEIGHT / 5));
        location = new JTextArea(1, 1);
        reportPane = new JScrollPane(location);
        reportBlock.add(reportPane);

        row.add(reportBlock);

        add(row);
    }

    // MODIFIES: this, LeagueManager
    // EFFECTS: creates and adds a game button
    // On click, creates a new game with home team, away team, time and location fields
    // Adds this game to LeagueManager's schedule
    // Return to main panel
    public void addGameButton() {
        JPanel row = createNewRow(LeagueManagerUI.WIDTH);

        JButton newGameButton = new JButton("Add Game");
        newGameButton.setActionCommand("addGame");
        newGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Game game = new Game(selectTeam(home), selectTeam(away), time.getText(), location.getText());
                getController().getSchedule().add(game);
                setup();
            }
        });
        add(newGameButton);
        add(row);
    }

    // Report Game Window

    // MODIFIES: this
    // EFFECTS: removes all current rows, adds a back button, a game dropdown, home/away goals modifiable fields
    // a player dropdown, an add goalscorer button, an empty list and a confirm changes button
    public void reportGame() {
        removeAll();
        back();
        chooseGameDropdown();
        reportGameRow1();
        reportGameRow2();
        reportGameRow3();
        reportGameRow4();
        reportGameRow5();
    }

    // MODIFIES: this
    // EFFECTS: creates and adds a dropdown with all games that haven't been played yet
    // selected game is stored
    public void chooseGameDropdown() {
        JPanel row = createNewRow(LeagueManagerUI.WIDTH - PADDING);
        row.add(new JLabel("Game", CENTER));

        String[] stringGames = new String[getController().getSchedule().size()];
        for (int i = 0; i < getController().getSchedule().size(); i++) {
            if (!getController().getSchedule().get(i).getGamePlayed()) {
                stringGames[i] = getController().getSchedule().get(i).getTeams();
            }
        }
        JComboBox gameCombo = new JComboBox(stringGames);
        gameCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameChoice = (String) gameCombo.getSelectedItem();
            }
        });
        row.add(gameCombo);
        add(row);
    }

    // MODIFIES: this
    // EFFECTS: displays a Home Goals label and modifiable text block
    public void reportGameRow1() {
        JPanel row = createNewRow(LeagueManagerUI.WIDTH);

        row.add(new JLabel("Home Goals:", CENTER));

        JPanel reportBlock = new JPanel(new GridLayout(2, 1));
        reportBlock.setSize(LeagueManagerUI.WIDTH - (LeagueManagerUI.WIDTH / 5),
                LeagueManagerUI.HEIGHT - (LeagueManagerUI.HEIGHT / 5));
        homeGoals = new JTextArea(1,1);
        reportPane = new JScrollPane(homeGoals);
        reportBlock.add(reportPane);

        row.add(reportBlock);

        add(row);
    }

    // MODIFIES: this
    // EFFECTS: displays an Away Goals label and modifiable text block
    public void reportGameRow2() {
        JPanel row = createNewRow(LeagueManagerUI.WIDTH);

        row.add(new JLabel("Away Goals:", CENTER));

        JPanel reportBlock = new JPanel(new GridLayout(2, 1));
        reportBlock.setSize(LeagueManagerUI.WIDTH - (LeagueManagerUI.WIDTH / 5),
                LeagueManagerUI.HEIGHT - (LeagueManagerUI.HEIGHT / 5));
        awayGoals = new JTextArea(1, 1);
        reportPane = new JScrollPane(awayGoals);
        reportBlock.add(reportPane);

        row.add(reportBlock);

        add(row);

    }

    // MODIFIES; this
    // EFFECTS: displays a player dropdown with all players and add player button
    public void reportGameRow3() {
        JPanel row = createNewRow(LeagueManagerUI.WIDTH - PADDING);
        row.add(new JLabel("Player", CENTER));

        String[] stringPlayers = new String[getController().getPlayerDatabase().size()];
        for (int i = 0; i < getController().getPlayerDatabase().size(); i++) {
            stringPlayers[i] = getController().getPlayerDatabase().get(i).getFullName();
        }
        JComboBox teamCombo = new JComboBox(stringPlayers);
        teamCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playerChoice = (String) teamCombo.getSelectedItem();
            }
        });
        row.add(teamCombo);

        JButton button = addGoalscorerButton();
        row.add(button);
        add(row);
    }

    // MODIFIES: this
    // EFFECTS: helper method that creates add Goalscorer button
    // On click, removes confirm changes button, player list
    // Adds selected player's names to list
    // Converts selected player to Player Class and adds it to list
    private JButton addGoalscorerButton() {
        JButton button = new JButton("Add Goalscorer");
        button.setActionCommand("addGoalscorer");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove(confirmButton);
                remove(row5);
                remove(row4);
                goalscorerNames.add(playerChoice);
                goalscorers.add(convertGoalscorertoPlayer(playerChoice));
                revalidate();
                reportGameRow4();
                reportGameRow5();

            }
        });
        return button;
    }

    // MODIFIES: this
    // EFFECTS: displays empty list
    public void reportGameRow4() {
        row4 = new JPanel(rowLayout);
        row4.setPreferredSize(new Dimension(LeagueManagerUI.WIDTH - PADDING, ROW_HEIGHT));
        row4.setBorder(border);

        JList<String> list = new JList(goalscorerNames.toArray());
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(-1);

        row4.add(list);
        add(row4);
    }

    // MODIFIES: this, LeagueManager
    // EFFECTS: creates and adds a confirm changes button
    // On click, retrieves selected game from LeagueManager and sets its home, away goals to provided input
    // Adds all goalscorers from list of Players to game's goalscorers
    // Finishes the game
    // Return to main panel
    public void reportGameRow5() {
        row5 = new JPanel(rowLayout);
        row5.setPreferredSize(new Dimension(LeagueManagerUI.WIDTH, ROW_HEIGHT));
        row5.setBorder(border);

        confirmButton = new JButton("Confirm changes");
        confirmButton.setActionCommand("confirm");
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Game game = selectGame(gameChoice);

                game.setHomeGoals(Integer.parseInt(homeGoals.getText()));
                game.setAwayGoals(Integer.parseInt(awayGoals.getText()));

                for (Player p: goalscorers) {
                    game.addGoalscorer(p);
                }
                game.finishGame();
                setup();
            }
        });
        add(confirmButton);
        add(row5);
    }

    // MODIFIES: this
    // EFFECTS: displays dropdown with all unplayed games
    // on selection, selected game is stored and current time and location display selected game's time and location
    public void updateChooseGame() {
        JPanel row = new JPanel(rowLayout);
        row.setPreferredSize(new Dimension(LeagueManagerUI.WIDTH - PADDING, ROW_HEIGHT));
        row.setBorder(border);

        String[] stringGames = new String[getController().getSchedule().size()];
        for (int i = 0; i < getController().getSchedule().size(); i++) {
            if (!getController().getSchedule().get(i).getGamePlayed()) {
                stringGames[i] = getController().getSchedule().get(i).getTeams();
            }
        }

        JComboBox gameCombo = new JComboBox(stringGames);
        gameCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameChoice = (String) gameCombo.getSelectedItem();
                displayCurrentTimeAndLocation();
            }
        });
        row.add(gameCombo);
        add(row);
    }

    // MODIFIES: this
    // EFFECTS: all rows are removed except dropdown are removed, and current location
    // and current time are reloaded with selected game's time and location. Every other row is added again (same state)
    public void displayCurrentTimeAndLocation() {
        remove(row5);
        remove(row4);
        remove(row3);
        remove(row2);
        repaint();
        refreshedRow1();
        updateRow2();
        updateRow3();
        updateButtons();
        revalidate();
    }

    // MODIFIES: this
    // EFFECTS: displays current time label and current location label
    public void updateRow1() {
        row2 = new JPanel(rowLayout);
        row2.setPreferredSize(new Dimension(LeagueManagerUI.WIDTH, ROW_HEIGHT));
        row2.setBorder(border);
        row2.add(new JLabel("Current Time:", SwingConstants.CENTER));
        row2.add(new JLabel(" ", SwingConstants.CENTER));
        row2.add(new JLabel("Current Location:", SwingConstants.CENTER));
        row2.add(new JLabel(" ", SwingConstants.CENTER));
        add(row2);
    }

    // MODIFIES: this
    // EFFECTS: displays current time label with selected game's time
    // and current location label with selected game's location
    public void refreshedRow1() {
        row2 = new JPanel(rowLayout);
        row2.setPreferredSize(new Dimension(LeagueManagerUI.WIDTH, ROW_HEIGHT));
        row2.setBorder(border);
        Game game = selectGame(gameChoice);
        row2.add(new JLabel("Current Time:", SwingConstants.CENTER));
        row2.add(new JLabel(game.getTime(), SwingConstants.CENTER));
        row2.add(new JLabel("Current Location:", SwingConstants.CENTER));
        row2.add(new JLabel(game.getLocation(), SwingConstants.CENTER));
        add(row2);
    }

    // MODIFIES: this
    // EFFECTS: displays new time and new location labels
    public void updateRow2() {
        row3 = new JPanel(rowLayout);
        row3.setPreferredSize(new Dimension(LeagueManagerUI.WIDTH, ROW_HEIGHT));
        row3.setBorder(border);
        row3.add(new JLabel("New Time:", LEFT));
        row3.add(new JLabel(" ", CENTER));
        row3.add(new JLabel("New Location:", LEFT));
        add(row3);
    }

    // MODIFIES: this
    // EFFECTS: displays modifiable text boxes for new location and new time
    public void updateRow3() {
        row4 = new JPanel(rowLayout);
        row4.setPreferredSize(new Dimension(LeagueManagerUI.WIDTH, ROW_HEIGHT));
        row4.setBorder(border);

        JPanel reportBlock = new JPanel(new GridLayout(2, 1));
        reportBlock.setSize(LeagueManagerUI.WIDTH - (LeagueManagerUI.WIDTH / 5),
                LeagueManagerUI.HEIGHT - (LeagueManagerUI.HEIGHT / 5));
        newTime = new JTextArea(1, 1);
        JScrollPane reportPane = new JScrollPane(newTime);
        reportBlock.add(reportPane);

        row4.add(reportBlock);

        row4.add(row4.add(new JLabel(" ", SwingConstants.CENTER)));

        JPanel reportBlock2 = new JPanel(new GridLayout(2, 1));
        reportBlock2.setSize(LeagueManagerUI.WIDTH - (LeagueManagerUI.WIDTH / 5),
                LeagueManagerUI.HEIGHT - (LeagueManagerUI.HEIGHT / 5));
        newLocation = new JTextArea(1, 1);
        JScrollPane reportPane2 = new JScrollPane(newLocation);
        reportBlock2.add(reportPane2);

        row4.add(reportBlock2, LEFT);

        add(row4);
    }

    // MODIFIES: this
    // EFFECTS: displays update time and update location buttons
    public void updateButtons() {
        row5 = new JPanel(rowLayout);
        row5.setPreferredSize(new Dimension(LeagueManagerUI.WIDTH, ROW_HEIGHT));
        row5.setBorder(border);

        JButton updateTime = updateTimeButton();
        row5.add(updateTime);

        JButton updateLocation = updateLocationButton();
        row5.add(updateLocation);

        add(row5);
    }

    // EFFECTS: creates an update location button
    // On click, the selected game's location is updated
    private JButton updateLocationButton() {
        JButton updateLocation = new JButton("Update location");
        updateLocation.setActionCommand("updateLocation");
        updateLocation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Game game = selectGame(gameChoice);
                game.setLocation(newLocation.getText());
                setup();
            }
        });
        return updateLocation;
    }

    // EFFECTS: creates an update time button
    // On click, the selected game's time is updated
    private JButton updateTimeButton() {
        JButton updateTime = new JButton("Update time");
        updateTime.setActionCommand("updateTime");
        updateTime.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Game game = selectGame(gameChoice);
                game.setTime(newTime.getText());
                setup();
            }
        });
        return updateTime;
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

    // EFFECTS: returns a game from LeagueManager's schedule based on the provided string of form "Team1 vs Team2"
    // Possible problem: what if two games have same teams?
    private Game selectGame(String gameString) {
        Game game = null;
        for (Game g: getController().getSchedule()) {
            if (g.getTeams().equals(gameString)) {
                game = g;
            }
        }
        return game;
    }

    // EFFECTS: returns a player from LeagueManager's players based on the provided full name
    public Player convertGoalscorertoPlayer(String playerString) {
        Player player = null;
        for (Player p: getController().getPlayerDatabase()) {
            if (p.getFullName().equals(playerString)) {
                player = p;
            }
        }
        return player;
    }

    // EFFECTS: creates new row of LeagueManager panel's width and row height, with a border
    private JPanel createNewRow(int width) {
        JPanel row = new JPanel(rowLayout);
        row.setPreferredSize(new Dimension(width, ROW_HEIGHT));
        row.setBorder(border);
        return row;
    }
}
