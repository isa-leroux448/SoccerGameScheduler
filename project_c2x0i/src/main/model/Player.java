package model;

import org.json.JSONObject;
import persistence.Writable;

// This class represents a player wth a team and a goal tally
// Users are able to add players to teams
public class Player implements Writable {

    private Team team;
    private int goals;
    private String firstName;
    private String lastName;

    // Constructs a Player
    // EFFECTS: creates a player object with a team and 0 goals
    // adds player to their team object
    public Player(Team team, String fn, String ln) {
        this.team = team;
        this.goals = 0;
        this.firstName = fn;
        this.lastName = ln;
        team.addPlayertoRoster(this);
        EventLog.getInstance().logEvent(new Event("Player " + firstName + " " + lastName
                + " added to " + team.getName() + "\n"));
    }

    // MODIFIES: this
    // EFFECTS: adds a goal to the player's goal field
    public void addGoal() {
        this.goals += 1;
    }

    // MODIFIES: this
    // EFFECTS: sets the player's goals to the given goals
    public void setGoals(int goals) {
        this.goals = goals;
    }

    // getters
    public Team getTeam() {
        return this.team;
    }

    public int getGoals() {
        return this.goals;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getFullName() {
        return (firstName + " " + lastName);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("firstName", firstName);
        json.put("lastName", lastName);
        json.put("team", team.toJson());
        json.put("goals", goals);
        return json;
    }
}
