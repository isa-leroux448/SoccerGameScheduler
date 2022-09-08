// Code based on TellerApp, JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/TellerApp.git
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo


package ui;

import model.Game;
import model.Player;
import model.Team;
import model.WorkRoom;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class LeagueManagerApp {

    private static final String JSON_STORE = "./data/workroom.json";
    private Scanner input;
    private WorkRoom workRoom;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private Team fusion;
    private Team nsgsc;
    private Team cascades;
    private Team mountain;

    private Player player;

    private Game game;

    private List<Player> playerDatabase;
    private List<Game> schedule;
    private List<Team> teams;

    // EFFECTS: runs the LeagueManagerApp
    public LeagueManagerApp() {
        workRoom = new WorkRoom("User's database");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runLeagueManager();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runLeagueManager() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }

            //System.out.println("Thank you for using LeagueManager");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes teams and players
    private void init() {
        // All teams are initialized
        fusion = new Team("Fusion");
        nsgsc = new Team("NSGSC");
        cascades = new Team("Cascades");
        mountain = new Team("Mountain");

        playerDatabase = new ArrayList<>();
        schedule = new ArrayList<>();
        teams = new ArrayList<>();

        teams.add(fusion);
        teams.add(nsgsc);
        teams.add(cascades);
        teams.add(mountain);

        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\t1 -> Player Database");
        System.out.println("\t2 -> Game Management");
        System.out.println("\t3 -> Team Statistics");
        System.out.println("\ts -> Save data");
        System.out.println("\tl -> Load data");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("1")) {
            displayPlayerMenu();
        } else if (command.equals("2")) {
            displayGameMenu();
        } else if (command.equals("3")) {
            displayTeamMenu();
        } else if (command.equals("s")) {
            saveWorkRoom();
        } else if (command.equals("l")) {
            loadWorkRoom();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: displays menu of option in team statistics to user
    private void displayTeamMenu() {
        String command;
        System.out.println("\nSelect from:");
        System.out.println("\t1 -> View team record");
        System.out.println("\t2 -> View team roster");
        System.out.println("\t3 -> View team goalscorers");

        command = input.next();
        command = command.toLowerCase();

        if (command.equals("1")) {
            displaySeasonStatistics();
        } else if (command.equals("2")) {
            Team selected = selectTeam();
            System.out.println("Roster: ");
            printPlayerList(selected.getRoster());
        } else if (command.equals("3")) {
            Team selected = selectTeam();
            System.out.println("Goalscorers: ");
            for (Player p: selected.goalscorers()) {
                System.out.println(p.getFullName() + " : " + p.getGoals() + " goals");
            }
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: displays all game statistics from selected team
    private void displaySeasonStatistics() {
        Team selected = selectTeam();
        System.out.println("GP: " + selected.getGamesPlayed());
        System.out.println("Points: " + selected.getPoints());
        System.out.println("Wins: " + selected.getWins());
        System.out.println("Losses: " + selected.getLoses());
        System.out.println("Ties: " + selected.getTies());
        System.out.println("Goals for: " + selected.getGoalsFor());
        System.out.println("Goals against: " + selected.getGoalsAgainst());
    }

    // MODIFIES: this
    // EFFECTS: displays menu of option in game management to user
    private void displayGameMenu() {
        String command;
        System.out.println("\nSelect from:");
        System.out.println("\t1 -> New Game");
        System.out.println("\t2 -> Update game time");
        System.out.println("\t3 -> Update game location");
        System.out.println("\t4 -> Report game");
        System.out.println("\t5 -> See game info");


        command = input.next();
        command = command.toLowerCase();

        if (command.equals("1")) {
            addGame();
        } else if (command.equals("2")) {
            updateGameTime();
        } else if (command.equals("3")) {
            updateGameLocation();
        } else if (command.equals("4")) {
            reportGame();
        } else if (command.equals("5")) {
            displayGameInformation();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this, Game
    // EFFECTS: update the selected game to given time
    private void updateGameTime() {
        Game selected = selectGame();
        System.out.println("What is the new game time?");
        String command = input.next();
        selected.setTime(command);
        System.out.println("Game time has been updated to: " + selected.getTime());
    }

    // MODIFIES: this, Game
    // EFFECTS: update the selected game to given location
    private void updateGameLocation() {
        Game selected = selectGame();
        System.out.println("What is the new game location?");
        String command = input.next();
        selected.setLocation(command);
        System.out.println("Game location has been updated to: " + selected.getLocation());
    }

    // EFFECTS: if game has been completed, displays the teams, time, location, score and goalscorers
    // else only display the teams, time and location
    private void displayGameInformation() {
        Game selected = selectGame();
        if (selected.getGamePlayed()) {
            System.out.println(selected.getHomeTeam().getName() + " " + selected.getHomeGoals());
            System.out.println(selected.getAwayTeam().getName() + " " + selected.getAwayGoals());
            System.out.println(selected.getTime() + " at " + selected.getLocation());
            System.out.println("Goalscorers: ");
            printPlayerList(selected.getGoalscorers());
        } else {
            System.out.println(selected.getHomeTeam().getName() + " vs " + selected.getAwayTeam().getName()
                    + " at " + selected.getLocation() + " at " + selected.getTime());
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a new game based on user's input
    private void addGame() {
        // ask for home team, away team, location, time
        System.out.println("What's the home team?");
        Team homeTeam = selectTeam();
        System.out.println("What's the away team?");
        Team awayTeam = selectTeam();
        System.out.println("What's the game time?");
        String time = input.next();
        System.out.println("What's the game location?");
        String location = input.next();

        game = new Game(homeTeam, awayTeam, time, location);
        schedule.add(game);
    }

    // MODIFIES: this
    // EFFECTS: asks user home team's goals, away team's goals and goalscorers
    // will loop until user confirms this information
    private void reportGame() {
        boolean gameConfirmed = false;

        System.out.println("What game would you like to report?");
        Game game = selectPlayableGame();

        while (!gameConfirmed) {
            System.out.println("What were the home team's goals?");
            String answer = input.next().toLowerCase();
            if (!answer.equals("skip")) {
                int goals = Integer.parseInt(answer);
                game.setHomeGoals(goals);
            }

            System.out.println("What were the away team's goals?");
            answer = input.next().toLowerCase();
            if (!answer.equals("skip")) {
                int goals = Integer.parseInt(answer);
                game.setAwayGoals(goals);
            }
            reportGoalscorers(game);

            System.out.println("Would you like to confirm this game information?");
            if (input.next().equalsIgnoreCase("yes")) {
                gameConfirmed = true;
                game.finishGame();
            }
        }
    }

    // MODIFIES: this, Team, Player
    // EFFECTS: asks the user if they would like to report goalscorers for the game
    // if the answer is yes, add each user's input (one at a time) as a goalscorer as long as their input isn't "done"
    private void reportGoalscorers(Game selectedGame) {
        System.out.println("Where there any goalscorers?");
        String answer = input.next().toLowerCase();
        if (answer.equals("yes")) {
            System.out.println("Who were the goalscorers? Write one at a time, type done when finished.");
            System.out.println("Information is available on the following players:");
            checkRegisteredPlayers();
            answer = input.next().toLowerCase();
            while (!answer.equals("done")) {
                int numAnswer = Integer.parseInt(answer);
                Player player = playerDatabase.get(numAnswer - 1);
                selectedGame.addGoalscorer(player);
                System.out.println((player.getFullName() + " added as goalscorer"));
                answer = input.next().toLowerCase();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: displays menu of options in player information to user
    private void displayPlayerMenu() {
        String command;
        System.out.println("\nSelect from:");
        System.out.println("\t1 -> Add player");
        System.out.println("\t2 -> See a player's goals");
        System.out.println("\t3 -> See a player's team");

        command = input.next();
        command = command.toLowerCase();

        if (command.equals("1")) {
            addPlayer();
        } else if (command.equals("2")) {
            Player selected = selectPlayer();
            System.out.println(selected.getFullName() + " has " + selected.getGoals() + " goals.");
        } else if (command.equals("3")) {
            Player selected = selectPlayer();
            String team = selected.getTeam().getName();
            System.out.println(selected.getFullName() + " is on " + team);
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: If there are still registration spots left, create a new player in the highest slot of player database
    // Adds the player to the user's inputted team and confirms it
    private void addPlayer() {
        Team team = selectTeam();
        System.out.println("What's the player's first name?");
        String firstName = input.next();
        System.out.println("What's the player's last name?");
        String lastName = input.next();
        player = new Player(team, firstName, lastName);
        playerDatabase.add(player);
        System.out.println(player.getFullName() + " has been added to " + player.getTeam().getName());

    }

    // EFFECTS: prompts user to select a team and return it
    private Team selectTeam() {
        String selection = "";

        while (!(selection.equals("fusion") || selection.equals("nsgsc") || selection.equals("cascades")
                || selection.equals("mountain"))) {
            System.out.println("Available teams are: Fusion, NSGSC, Cascades, Mountain");
            selection = input.next();
            selection = selection.toLowerCase();
        }
        if (selection.equals("fusion")) {
            return fusion;
        } else if (selection.equals("nsgsc")) {
            return nsgsc;
        } else if (selection.equals("cascades")) {
            return cascades;
        } else {
            return mountain;
        }
    }

    // REQUIRES: input to be int
    // EFFECTS: prompts user to select a game and returns it
    private Game selectGame() {
        int selection = 0;

        while (!((selection > 0) && (selection <= schedule.size()))) {
            System.out.println("Available games are: ");
            checkAllGames();
            selection = input.nextInt();
        }
        return schedule.get(selection - 1);
    }

    // REQUIRES: input to be int
    // EFFECTS: displays all games that aren't finished, prompts user to select a game and returns it
    private Game selectPlayableGame() {
        int selection = 0;
        List<Game> playableGames = schedule;

        while (!((selection > 0) && (selection <= playableGames.size()))) {
            System.out.println("Available games are: ");

            for (Game g: playableGames) {
                if (g.getGamePlayed()) {
                    playableGames.remove(g);
                }
            }
            for (Game g: playableGames) {
                System.out.println((playableGames.indexOf(g) + 1) + ". " + g.getHomeTeam().getName() + " vs "
                        + g.getAwayTeam().getName() + " at " + g.getLocation() + " at " + g.getTime());
            }
            selection = input.nextInt();
        }
        return playableGames.get(selection - 1);
    }

    // EFFECTS: displays games that have been instantiated
    private void checkAllGames() {
        for (Game g: schedule) {
            System.out.println((schedule.indexOf(g) + 1) + ". " + g.getHomeTeam().getName() + " vs "
                    + g.getAwayTeam().getName() + " at " + g.getLocation() + " at " + g.getTime());
        }
    }

    // REQUIRES: input to be int
    // EFFECTS: prompts user to select a player and returns it
    private Player selectPlayer() {
        int selection = 0;

        while (!((selection > 0) && (selection <= playerDatabase.size()))) {
            System.out.println("Information is available on the following players:");
            checkRegisteredPlayers();
            selection = input.nextInt();
        }
        return playerDatabase.get(selection - 1);
    }

    // EFFECTS: displays players that have been instantiated
    private void checkRegisteredPlayers() {
        for (Player p: playerDatabase) {
            System.out.println((playerDatabase.indexOf(p) + 1) + ". " + p.getFullName());
        }
    }

    // EFFECTS: prints out all the players in a given list
    public void printPlayerList(List<Player> playerList) {
        for (Player p: playerList) {
            System.out.println(p.getFullName());
        }
    }

    // EFFECTS: saves the workroom to file
    private void saveWorkRoom() {
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

    // EFFECTS: loads the workroom to file
    // sets all team variables to teams from workroom and records their goalscorers
    private void loadWorkRoom() {
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



}

