// Code based on JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorkRoom implements Writable {
    // Represents a database having a collection of teams, players and games
    String name;
    private List<Team> teams;
    private List<Player> players;
    private List<Game> games;

    public WorkRoom(String name) {
        this.name = name;
        teams = new ArrayList<>();
        players = new ArrayList<>();
        games = new ArrayList<>();
    }

    // getters
    public String getName() {
        return this.name;
    }

    // EFFECTS: returns a list of teams
    public List<Team> getTeams() {
        //return Collections.unmodifiableList(teams);
        return this.teams;
    }

    // EFFECTS: returns a list of players
    public List<Player> getPlayers() {
        //return Collections.unmodifiableList(players);
        return this.players;
    }

    // EFFECTS: returns a list of games
    public List<Game> getGames() {
        //return Collections.unmodifiableList(games);
        return this.games;
    }

    // MODIFIES: this
    // EFFECTS: sets saved teams to given list of teams
    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    // MODIFIES: this
    // EFFECTS: sets saved players to given list of players
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    // MODIFIES: this
    // EFFECTS: sets saved games to given list of games
    public void setGames(List<Game> games) {
        this.games = games;
    }

    // MODIFIES: this
    // EFFECTS: adds given team to workroom's teams
    public void addTeam(Team team) {
        teams.add(team);
    }

    // MODIFIES: this
    // EFFECTS: adds given player to the workroom's players
    public void addPlayer(Player player) {
        players.add(player);
    }

    // MODIFIES: this
    // EFFECTS: adds given game to the workroom's games
    public void addGame(Game game) {
        games.add(game);
    }

    // EFFECTS: returns number of teams in workroom
    public int numTeams() {
        return teams.size();
    }

    // EFFECTS: returns number of players in workroom
    public int numPlayers() {
        return players.size();
    }

    // EFFECTS: returns number of games in workroom
    public int numGames() {
        return games.size();
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("teams", teamstoJson());
        json.put("players", playerstoJson());
        json.put("games", gamestoJson());
        return json;
    }

    private JSONArray teamstoJson() {
        JSONArray jsonArray = new JSONArray();
        for (Team t: teams) {
            jsonArray.put(t.toJson());
        }
        return jsonArray;
    }

    private JSONArray playerstoJson() {
        JSONArray jsonArray = new JSONArray();
        for (Player p: players) {
            jsonArray.put(p.toJson());
        }
        return jsonArray;
    }

    private JSONArray gamestoJson() {
        JSONArray jsonArray = new JSONArray();
        for (Game g: games) {
            jsonArray.put(g.toJson());
        }
        return jsonArray;
    }
}
