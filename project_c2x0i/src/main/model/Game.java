package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// This class represents a game with a home and away team, a time and location
// homegoals, awaygoals and goalscorers are added by the user and confirmed by the user
public class Game implements Writable {

    private Team homeTeam;
    private Team awayTeam;
    private String time;
    private String location;
    private int homeGoals;
    private int awayGoals;
    private List<Player> goalscorers;
    private boolean gamePlayed;

    // Constructor
    // EFFECTS: creates a new game object with home team, away team, time and location
    // initializes goalscorers list as empty
    public Game(Team home, Team away, String time, String location) {
        this.homeTeam = home;
        this.awayTeam = away;
        this.time = time;
        this.location = location;
        this.goalscorers = new ArrayList<>();
        this.gamePlayed = false;
        EventLog.getInstance().logEvent(new Event("Game between " + home.getName() + " and " + away.getName()
                + " at " + time + " at " + location + " has been scheduled.\n"));
    }

    // MODIFIES: this, Player
    // EFFECTS: adds player to list of goalscorers in Game, can be repeated
    //          increments player's goals field by 1
    public void addGoalscorer(Player goalscorer) {
        if (!goalscorers.contains(goalscorer)) {
            goalscorers.add(goalscorer);
        }
        goalscorer.addGoal();
    }

    // MODIFIES: this
    // EFFECTS: set gamePlayed boolean to true
    // triggers gameOutcome(), goalCounter(), recordGoalscorers()
    public void finishGame() {
        gamePlayed = true;
        homeTeam.recordGoalscorers();
        awayTeam.recordGoalscorers();
        gameOutcome();
        goalCounter();
    }

    // MODIFIES: Team
    // EFFECTS: based on number of goals, determines if game is a tie, win or loss for homeTeam
    // if a tie, add a tie to each of the teams
    // if a loss for the homeTeam, add a loss for the home team and a win for the away team
    // if a win for the homeTeam, add a win to the home team and a loss to the away team
    // also increments both teams games played by 1
    public void gameOutcome() {
        if (homeGoals == awayGoals) {
            homeTeam.addTie();
            awayTeam.addTie();
        } else if (homeGoals > awayGoals) {
            homeTeam.addWin();
            awayTeam.addLoss();
        } else {
            homeTeam.addLoss();
            awayTeam.addWin();
        }
        homeTeam.addGamesPlayed();
        awayTeam.addGamesPlayed();
    }

    // MODIFIES: Team
    // EFFECTS: for each team, add their goals to goalsfor and add their opponents goals to goalsAgainst
    // adds awayGoals to the team's away goals
    public void goalCounter() {
        awayTeam.addGoalsFor(awayGoals);
        awayTeam.addGoalsAgainst(homeGoals);

        homeTeam.addGoalsFor(homeGoals);
        homeTeam.addGoalsAgainst(awayGoals);
    }

    // REQUIRES: time be in 00:00 format in 24 hour clock
    // MODIFIES: this
    // EFFECTS: set game's time to given time
    public void setTime(String time) {
        this.time = time;
    }

    // MODIFIES: this
    // EFFECTS: set game's location to given location
    public void setLocation(String location) {
        this.location = location;
    }

    // REQUIRES: homeGoals > 0
    // MODIFIES: this
    // EFFECTS: set game's HomeGoals to given number of goals
    public void setHomeGoals(int homeGoals) {
        this.homeGoals = homeGoals;
    }

    // REQUIRES: awayGoals > 0
    // MODIFIES: this
    // EFFECTS: set game's awayGoals to given number of goals
    public void setAwayGoals(int awayGoals) {
        this.awayGoals = awayGoals;
    }

    // MODIFIES: this
    // EFFECTS: sets GamePlayed to given True or False
    public void setGamePlayed(boolean gp) {
        this.gamePlayed = gp;
    }

    // MODIFIES: this
    // EFFECTS: sets game's goalscorers to given list
    public void setGoalscorers(List<Player> goalscorers) {
        this.goalscorers = goalscorers;
    }

    // getters
    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public String getTime() {
        return this.time;
    }

    public String getLocation() {
        return this.location;
    }

    public int getHomeGoals() {
        return this.homeGoals;
    }

    public int getAwayGoals() {
        return this.awayGoals;
    }

    public List<Player> getGoalscorers() {
        return this.goalscorers;
    }

    public boolean getGamePlayed() {
        return this.gamePlayed;
    }

    public String getTeams() {
        return (homeTeam.getName() + " vs " + awayTeam.getName());
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("homeTeam", homeTeam.toJson());
        json.put("awayTeam", awayTeam.toJson());
        json.put("time", time);
        json.put("location", location);
        json.put("homeGoals", homeGoals);
        json.put("awayGoals", awayGoals);
        json.put("gamePlayed", gamePlayed);
        json.put("goalscorers", goalscorerstoJson());
        return json;
    }

    public JSONArray goalscorerstoJson() {
        JSONArray jsonArray = new JSONArray();

        for (Player p: goalscorers) {
            jsonArray.put(p.toJson());
        }
        return jsonArray;
    }
}
