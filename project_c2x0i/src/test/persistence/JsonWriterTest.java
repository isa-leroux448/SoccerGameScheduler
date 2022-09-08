package persistence;

import model.Game;
import model.Player;
import model.Team;
import model.WorkRoom;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest extends JsonTest {


    @Test
    void testWriterInvalidFile() {
        try {
            WorkRoom wr = new WorkRoom("My work room");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            WorkRoom wr = new WorkRoom("My work room");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(wr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            wr = reader.read();
            assertEquals("My work room", wr.getName());
            assertEquals(0, wr.numGames());
            assertEquals(0, wr.numPlayers());
            assertEquals(0, wr.numTeams());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            WorkRoom wr = new WorkRoom("My work room");

            Team fusion = new Team("Fusion");
            Team mountain = new Team("Mountain");
            Player player1 = new Player(fusion, "Isabella", "Leroux");
            Player player2 = new Player(mountain, "Jenna", "Baxter");
            fusion.setWins(1);
            fusion.setLoses(0);
            fusion.setTies(0);
            fusion.setPoints(3);
            fusion.setGoalsFor(1);
            fusion.setGoalsAgainst(0);
            fusion.setGamesPlayed(1);

            mountain.setWins(0);
            mountain.setLoses(1);
            mountain.setTies(0);
            mountain.setPoints(0);
            mountain.setGoalsFor(0);
            mountain.setGoalsAgainst(1);
            mountain.setGamesPlayed(1);

            Game game = new Game(fusion, mountain, "18:00", "Richmond High");
            game.setHomeGoals(1);
            game.setAwayGoals(0);
            List<Player> playerList = new ArrayList<>();
            playerList.add(player1);
            game.setGoalscorers(playerList);
            player1.setGoals(1);
            game.setGamePlayed(true);

            wr.addGame(game);
            wr.addTeam(fusion);
            wr.addTeam(mountain);
            wr.addPlayer(player1);
            wr.addPlayer(player2);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.write(wr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
            wr = reader.read();
            assertEquals("My work room", wr.getName());
            List<Team> teams = wr.getTeams();
            List<Player> players = wr.getPlayers();
            List<Game> games = wr.getGames();
            assertEquals(2, teams.size());
            assertEquals(2, players.size());
            assertEquals(1, games.size());

            //checking player1
            assertEquals("Isabella", players.get(0).getFirstName());
            assertEquals("Leroux", players.get(0).getLastName());
            assertEquals("Fusion", players.get(0).getTeam().getName());
            assertEquals(1, players.get(0).getGoals());

            //checking player2
            assertEquals("Jenna", players.get(1).getFirstName());
            assertEquals("Baxter", players.get(1).getLastName());
            assertEquals("Mountain", players.get(1).getTeam().getName());

            //checking fusion
            assertEquals(1, teams.get(0).getWins());
            assertEquals(0, teams.get(0).getLoses());
            assertEquals(0, teams.get(0).getTies());
            assertEquals(3, teams.get(0).getPoints());
            assertEquals(1, teams.get(0).getGoalsFor());
            assertEquals(0, teams.get(0).getGoalsAgainst());
            assertEquals(1, teams.get(0).getGamesPlayed());

            assertEquals("Isabella", teams.get(0).getRoster().get(0).getFirstName());
            teams.get(0).recordGoalscorers();
            assertEquals("Isabella", teams.get(0).getTeamGoalscorers().get(0).getFirstName());

            //checking mountain
            assertEquals(0, teams.get(1).getWins());
            assertEquals(1, teams.get(1).getLoses());
            assertEquals(0, teams.get(1).getTies());
            assertEquals(0, teams.get(1).getPoints());
            assertEquals(0, teams.get(1).getGoalsFor());
            assertEquals(1, teams.get(1).getGoalsAgainst());
            assertEquals(1, teams.get(1).getGamesPlayed());

            //checking game
            assertEquals("Fusion", games.get(0).getHomeTeam().getName());
            assertEquals("Mountain", games.get(0).getAwayTeam().getName());
            assertEquals("18:00", games.get(0).getTime());
            assertEquals("Richmond High", games.get(0).getLocation());
            assertEquals(1, games.get(0).getHomeGoals());
            assertEquals(0, games.get(0).getAwayGoals());
            assertEquals(true, games.get(0).getGamePlayed());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterSetterWorkroom() {
        try {
            WorkRoom wr = new WorkRoom("My work room");

            Team fusion = new Team("Fusion");
            Team mountain = new Team("Mountain");
            Player player1 = new Player(fusion, "Isabella", "Leroux");
            Player player2 = new Player(mountain, "Jenna", "Baxter");
            fusion.setWins(1);
            fusion.setLoses(0);
            fusion.setTies(0);
            fusion.setPoints(3);
            fusion.setGoalsFor(1);
            fusion.setGoalsAgainst(0);
            fusion.setGamesPlayed(1);

            mountain.setWins(0);
            mountain.setLoses(1);
            mountain.setTies(0);
            mountain.setPoints(0);
            mountain.setGoalsFor(0);
            mountain.setGoalsAgainst(1);
            mountain.setGamesPlayed(1);

            Game game = new Game(fusion, mountain, "18:00", "Richmond High");
            game.setHomeGoals(1);
            game.setAwayGoals(0);
            List<Player> playerList = new ArrayList<>();
            playerList.add(player1);
            game.setGoalscorers(playerList);
            player1.setGoals(1);
            game.setGamePlayed(true);

            List<Player> wrPlayer = new ArrayList<>();
            List<Team> wrTeam = new ArrayList<>();
            List<Game> wrGame = new ArrayList<>();
            wrPlayer.add(player1);
            wrPlayer.add(player2);
            wrTeam.add(fusion);
            wrTeam.add(mountain);
            wrGame.add(game);

            wr.setTeams(wrTeam);
            wr.setPlayers(wrPlayer);
            wr.setGames(wrGame);

            JsonWriter writer = new JsonWriter("./data/testWriterSetterWorkroom.json");
            writer.open();
            writer.write(wr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterSetterWorkroom.json");
            wr = reader.read();
            assertEquals("My work room", wr.getName());
            List<Team> teams = wr.getTeams();
            List<Player> players = wr.getPlayers();
            List<Game> games = wr.getGames();
            assertEquals(2, teams.size());
            assertEquals(2, players.size());
            assertEquals(1, games.size());

            //checking player1
            assertEquals("Isabella", players.get(0).getFirstName());
            assertEquals("Leroux", players.get(0).getLastName());
            assertEquals("Fusion", players.get(0).getTeam().getName());
            assertEquals(1, players.get(0).getGoals());

            //checking player2
            assertEquals("Jenna", players.get(1).getFirstName());
            assertEquals("Baxter", players.get(1).getLastName());
            assertEquals("Mountain", players.get(1).getTeam().getName());

            //checking fusion
            assertEquals(1, teams.get(0).getWins());
            assertEquals(0, teams.get(0).getLoses());
            assertEquals(0, teams.get(0).getTies());
            assertEquals(3, teams.get(0).getPoints());
            assertEquals(1, teams.get(0).getGoalsFor());
            assertEquals(0, teams.get(0).getGoalsAgainst());
            assertEquals(1, teams.get(0).getGamesPlayed());

            assertEquals("Isabella", teams.get(0).getRoster().get(0).getFirstName());
            teams.get(0).recordGoalscorers();
            assertEquals("Isabella", teams.get(0).getTeamGoalscorers().get(0).getFirstName());

            //checking mountain
            assertEquals(0, teams.get(1).getWins());
            assertEquals(1, teams.get(1).getLoses());
            assertEquals(0, teams.get(1).getTies());
            assertEquals(0, teams.get(1).getPoints());
            assertEquals(0, teams.get(1).getGoalsFor());
            assertEquals(1, teams.get(1).getGoalsAgainst());
            assertEquals(1, teams.get(1).getGamesPlayed());

            //checking game
            assertEquals("Fusion", games.get(0).getHomeTeam().getName());
            assertEquals("Mountain", games.get(0).getAwayTeam().getName());
            assertEquals("18:00", games.get(0).getTime());
            assertEquals("Richmond High", games.get(0).getLocation());
            assertEquals(1, games.get(0).getHomeGoals());
            assertEquals(0, games.get(0).getAwayGoals());
            assertEquals(true, games.get(0).getGamePlayed());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterWorkRoomPlayersWithSameFirstName() {
        try {
            WorkRoom wr = new WorkRoom("My work room");
            Team fusion = new Team("Fusion");
            Team mountain = new Team("Mountain");
            Player player1 = new Player(fusion, "Isabella", "Leroux");
            Player player2 = new Player(mountain, "Isabella", "Baxter");

            wr.addTeam(fusion);
            wr.addTeam(mountain);
            wr.addPlayer(player1);
            wr.addPlayer(player2);

            JsonWriter writer = new JsonWriter("./data/testWriterWorkRoomPlayersWithSameFirstName.json");
            writer.open();
            writer.write(wr);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterWorkRoomPlayersWithSameFirstName.json");
            wr = reader.read();

            assertEquals("My work room", wr.getName());
            List<Team> teams = wr.getTeams();
            List<Player> players = wr.getPlayers();

            //checking player1
            assertEquals("Isabella", players.get(0).getFirstName());
            assertEquals("Leroux", players.get(0).getLastName());
            assertEquals("Fusion", players.get(0).getTeam().getName());

            //checking player2
            assertEquals("Isabella", players.get(1).getFirstName());
            assertEquals("Baxter", players.get(1).getLastName());
            assertEquals("Mountain", players.get(1).getTeam().getName());

            //checking teams
            assertEquals("Isabella", teams.get(0).getRoster().get(0).getFirstName());
            assertEquals("Leroux", teams.get(0).getRoster().get(0).getLastName());
            assertEquals("Isabella", teams.get(1).getRoster().get(0).getFirstName());
            assertEquals("Baxter", teams.get(1).getRoster().get(0).getLastName());

        } catch (IOException e) {
        fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterWorkRoomPlayersWithSameLastName() {
        try {
            WorkRoom wr = new WorkRoom("My work room");
            Team fusion = new Team("Fusion");
            Team mountain = new Team("Mountain");
            Player player1 = new Player(fusion, "Isabella", "Leroux");
            Player player2 = new Player(mountain, "Jenna", "Leroux");

            wr.addTeam(fusion);
            wr.addTeam(mountain);
            wr.addPlayer(player1);
            wr.addPlayer(player2);

            JsonWriter writer = new JsonWriter("./data/testWriterWorkRoomPlayersWithSameLastName.json");
            writer.open();
            writer.write(wr);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterWorkRoomPlayersWithSameLastName.json");
            wr = reader.read();

            assertEquals("My work room", wr.getName());
            List<Team> teams = wr.getTeams();
            List<Player> players = wr.getPlayers();

            //checking player1
            assertEquals("Isabella", players.get(0).getFirstName());
            assertEquals("Leroux", players.get(0).getLastName());
            assertEquals("Fusion", players.get(0).getTeam().getName());

            //checking player2
            assertEquals("Jenna", players.get(1).getFirstName());
            assertEquals("Leroux", players.get(1).getLastName());
            assertEquals("Mountain", players.get(1).getTeam().getName());

            //checking teams
            assertEquals("Isabella", teams.get(0).getRoster().get(0).getFirstName());
            assertEquals("Leroux", teams.get(0).getRoster().get(0).getLastName());
            assertEquals("Jenna", teams.get(1).getRoster().get(0).getFirstName());
            assertEquals("Leroux", teams.get(1).getRoster().get(0).getLastName());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
