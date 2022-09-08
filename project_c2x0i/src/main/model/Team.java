package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.*;
import java.util.Collections;

// This class represents a team that contains a name, a roster and a list of goalscorers
// The user is able to access all of this information, as well as their season statistics

public class Team implements Writable {

    private String name;
    private List<Player> roster;
    private List<Player> teamGoalscorers;

    private int wins;
    private int loses;
    private int ties;
    private int points;
    private int goalsFor;
    private int goalsAgainst;
    private int gamesPlayed;
    // might add GoalDifference

    // Constructs a team
    // EFFECTS: constructs a team with an empty roster
    // set wins, loses, ties, points, gp, gf, ga to 0
    public Team(String name) {
        this.name = name;
        this.roster = new ArrayList<>();
        this.teamGoalscorers = new ArrayList<>();
        this.wins = 0;
        this.loses = 0;
        this.ties = 0;
        this.points = 0;
        this.goalsFor = 0;
        this.goalsAgainst = 0;
        this.gamesPlayed = 0;

    }

    // MODIFIES: this
    // EFFECTS: adds player to roster
    public void addPlayertoRoster(Player player) {
        this.roster.add(player);
    }


    // MODIFIES: this
    // EFFECTS: adds all goalscorers from game to team's list of goalscorers, if they're not already there
    public void recordGoalscorers() {
        for (Player p: roster) {
            if ((! teamGoalscorers.contains(p)) && (0 < p.getGoals())) {
                teamGoalscorers.add(p);
            }
        }
    }

    // EFFECTS: returns list of players with >0 goals ranks from most to least goals
    public List<Player> goalscorers() {
        Collections.sort(this.teamGoalscorers, new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                return o1.getGoals() - o2.getGoals();
            }
        });
        Collections.reverse(teamGoalscorers);
        return this.teamGoalscorers;
        // may need to be reversed Collections.reverse(teamGoalscorers);
    }

    // MODIFIES: this
    // EFFECTS: increment team's ties by 1
    public void addTie() {
        this.ties += 1;
    }

    // MODIFIES; this
    // EFFECTS: increment team's wins by 1
    public void addWin() {
        this.wins += 1;
    }

    // MODIFIES: this
    // EFFECTS: increment team's losses by 1
    public void addLoss() {
        this.loses += 1;
    }

    // MODIFIES: this
    // EFFECTS: increment's team's goalsFor by given int
    public void addGoalsFor(int goals) {
        this.goalsFor += goals;
    }

    // MODIFIES: this
    // EFFECTS: increment's team's goalsAgainst by given int
    public void addGoalsAgainst(int goals) {
        this.goalsAgainst += goals;
    }

    // MODIFIES: this
    // EFFECTS: increments team's gamePlayed by 1
    public void addGamesPlayed() {
        this.gamesPlayed += 1;
    }

    // getters
    public String getName() {
        return this.name;
    }

    public List<Player> getRoster() {
        return this.roster;
    }

    public int getWins() {
        return this.wins;
    }

    public int getLoses() {
        return this.loses;
    }

    public int getTies() {
        return this.ties;
    }

    public int getPoints() {
        this.points = this.wins * 3 + this.ties;
        return this.points;
    }

    public int getGoalsFor() {
        return this.goalsFor;
    }

    public int getGoalsAgainst() {
        return this.goalsAgainst;
    }

    public List<Player> getTeamGoalscorers() {
        return this.teamGoalscorers;
    }

    public int getGamesPlayed() {
        return this.gamesPlayed;
    }

    // setters

    // REQUIRES: input wins >= 0
    // MODIFIES: this
    // EFFECTS: set team's wins to given number of wins
    public void setWins(int wins) {
        this.wins = wins;
    }

    // REQUIRES: input losses >= 0
    // MODIFIES: this
    // EFFECTS: set team's losses to given number of losses
    public void setLoses(int losses) {
        this.loses = losses;
    }

    // REQUIRES: input ties >= 0
    // MODIFIES: this
    // EFFECTS: set team's ties to given number of ties
    public void setTies(int ties) {
        this.ties = ties;
    }

    // REQUIRES: input points >= 0
    // MODIFIES: this
    // EFFECTS: set team's points to given number of points
    public void setPoints(int points) {
        this.points = points;
    }

    // REQUIRES: input goalsFor >= 0
    // MODIFIES: this
    // EFFECTS: set goalsFor to given number of goalsFor
    public void setGoalsFor(int goals) {
        this.goalsFor = goals;
    }

    // REQUIRES: input goalsAgainst >= 0
    // MODIFIES: this
    // EFFECTS: set goalsAgainst to given number of goalsAgainst
    public void setGoalsAgainst(int goals) {
        this.goalsAgainst = goals;
    }

    // REQUIRES: input numGames >= 0
    // MODIFIES: this
    // EFFECTS: set GamesPlayed to given number of GamesPlayed
    public void setGamesPlayed(int numGames) {
        this.gamesPlayed = numGames;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("wins", wins);
        json.put("losses", loses);
        json.put("ties", ties);
        json.put("points", points);
        json.put("goalsFor", goalsFor);
        json.put("goalsAgainst", goalsAgainst);
        json.put("gamesPlayed", gamesPlayed);
        return json;
    }
}
