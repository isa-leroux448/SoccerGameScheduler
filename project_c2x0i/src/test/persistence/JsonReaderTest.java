package persistence;

import model.Game;
import model.Player;
import model.Team;
import model.WorkRoom;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            WorkRoom wr = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testWriterEmptyWorkRoom.json");
        try {
            WorkRoom wr = reader.read();
            assertEquals("My work room", wr.getName());
            assertEquals(0, wr.numTeams());
            assertEquals(0, wr.numPlayers());
            assertEquals(0, wr.numGames());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testWriterGeneralWorkRoom.json");
        try {
            WorkRoom wr = reader.read();
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
            fail("Couldn't read from file");
        }
    }
}
