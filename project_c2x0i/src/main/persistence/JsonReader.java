// Code based on JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

package persistence;

import model.Game;
import model.Player;
import model.Team;
import model.WorkRoom;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public WorkRoom read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorkRoom(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private WorkRoom parseWorkRoom(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        WorkRoom wr = new WorkRoom(name);
        addTeams(wr, jsonObject);
        addPlayers(wr, jsonObject);
        addGames(wr, jsonObject);
        return wr;
    }

    // MODIFIES: wr
    // EFFECTS: parses teams from JSON object and adds them to workroom
    private void addTeams(WorkRoom wr, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("teams");
        for (Object json : jsonArray) {
            JSONObject nextTeam = (JSONObject) json;
            addTeam(wr, nextTeam);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses team from JSON object and adds it to workroom
    private void addTeam(WorkRoom wr, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int wins = jsonObject.getInt("wins");
        int losses = jsonObject.getInt("losses");
        int ties = jsonObject.getInt("ties");
        int points = jsonObject.getInt("points");
        int goalsFor = jsonObject.getInt("goalsFor");
        int goalsAgainst = jsonObject.getInt("goalsAgainst");
        int gamesPlayed = jsonObject.getInt("gamesPlayed");

        Team team = new Team(name);
        team.setWins(wins);
        team.setLoses(losses);
        team.setTies(ties);
        team.setPoints(points);
        team.setGoalsFor(goalsFor);
        team.setGoalsAgainst(goalsAgainst);
        team.setGamesPlayed(gamesPlayed);

        wr.addTeam(team);
    }

    // MODIFIES: wr
    // EFFECTS: parses players from JSON object and adds them to workroom
    private void addPlayers(WorkRoom wr, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("players");
        for (Object json : jsonArray) {
            JSONObject nextPlayer = (JSONObject) json;
            addPlayer(wr, nextPlayer);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses player from JSON object and adds it to workroom
    private void addPlayer(WorkRoom wr, JSONObject jsonObject) {
        String firstName = jsonObject.getString("firstName");
        String lastName = jsonObject.getString("lastName");
        JSONObject jsonTeam = jsonObject.getJSONObject("team");
        int goals = jsonObject.getInt("goals");
        Team createdTeam = addJsonTeam(wr, jsonTeam);
        Team team = findTeam(wr,createdTeam.getName());

        Player player = new Player(team, firstName, lastName);
        player.setGoals(goals);
        wr.addPlayer(player);
    }

    // MODIFIES: wr
    // EFFECTS: parses games from JSON object and adds them to workroom
    private void addGames(WorkRoom wr, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("games");
        for (Object json : jsonArray) {
            JSONObject nextGame = (JSONObject) json;
            addGame(wr, nextGame);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses game from JSON object and adds it to workroom
    private void addGame(WorkRoom wr, JSONObject jsonObject) {
        JSONObject jsonTeam = jsonObject.getJSONObject("homeTeam");
        Team createdTeam = addJsonTeam(wr, jsonTeam);
        Team homeTeam = findTeam(wr, createdTeam.getName());

        jsonTeam = jsonObject.getJSONObject("awayTeam");
        createdTeam = addJsonTeam(wr, jsonTeam);
        Team awayTeam = findTeam(wr, createdTeam.getName());

        String time = jsonObject.getString("time");
        String location = jsonObject.getString("location");
        int homeGoals = jsonObject.getInt("homeGoals");
        int awayGoals = jsonObject.getInt("awayGoals");
        boolean gamePlayed = jsonObject.getBoolean("gamePlayed");

        JSONArray jsonGoalscorers = jsonObject.getJSONArray("goalscorers");
        List<Player> goalscorers = addGoalscorers(jsonGoalscorers, wr);

        Game game = new Game(homeTeam, awayTeam, time, location);
        game.setAwayGoals(awayGoals);
        game.setHomeGoals(homeGoals);
        game.setGamePlayed(gamePlayed);
        game.setGoalscorers(goalscorers);

        wr.addGame(game);
    }

    // helpers

    // EFFECTS: for every player in JsonArray, find appropriate player from workroom and add them to goalscorer list
    // returns goalscorer list
    public List<Player> addGoalscorers(JSONArray jsonGoalscorers, WorkRoom wr) {
        List<Player> goalscorers = new ArrayList<>();
        for (Object json : jsonGoalscorers) {
            JSONObject nextGoalscorer = (JSONObject) json;
            Player goalscorer = addjsonPlayer(wr, nextGoalscorer);
            goalscorers.add(findPlayer(wr, goalscorer.getFirstName(), goalscorer.getLastName()));
        }
        return goalscorers;
    }

    // EFFECTS: searches through workroom's teams for team with given name and returns it
    public Team findTeam(WorkRoom wr, String name) {
        Team returner  = null;
        for (Team t: wr.getTeams()) {
            if (t.getName().equals(name)) {
                returner = t;
            }
        }
        return returner;
    }

    // EFFECTS: searches through worklist's players for player with given first and last name and returns it
    public Player findPlayer(WorkRoom wr, String firstName, String lastName) {
        Player returner = null;
        for (Player p: wr.getPlayers()) {
            if (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)) {
                returner = p;
            }
        }
        return returner;
    }

    // EFFECTS: parses player from JSON object and returns it
    public Player addjsonPlayer(WorkRoom wr, JSONObject jsonObject) {
        String firstName = jsonObject.getString("firstName");
        String lastName = jsonObject.getString("lastName");
        JSONObject jsonTeam = jsonObject.getJSONObject("team");
        int goals = jsonObject.getInt("goals");
        Team team = addJsonTeam(wr, jsonTeam);
        Player player = new Player(team, firstName, lastName);
        player.setGoals(goals);
        return player;
    }

    // EFFECTS: parses team from JSON object and returns it
    private Team addJsonTeam(WorkRoom wr, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int wins = jsonObject.getInt("wins");
        int losses = jsonObject.getInt("losses");
        int ties = jsonObject.getInt("ties");
        int points = jsonObject.getInt("points");
        int goalsFor = jsonObject.getInt("goalsFor");
        int goalsAgainst = jsonObject.getInt("goalsAgainst");
        int gamesPlayed = jsonObject.getInt("gamesPlayed");

        Team team = new Team(name);
        team.setWins(wins);
        team.setLoses(losses);
        team.setTies(ties);
        team.setPoints(points);
        team.setGoalsFor(goalsFor);
        team.setGoalsAgainst(goalsAgainst);
        team.setGamesPlayed(gamesPlayed);

        return team;

    }


}
