package model;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game game1;
    Player player1;
    Player player2;
    Team fusion;
    Team nsgsc;

    @BeforeEach
    public void setup() {
        fusion = new Team("Fusion");
        nsgsc = new Team("NSGSC");
        game1 = new Game(nsgsc, fusion, "17:00", "Windsor Secondary");
    }

    @Test
    public void testGameConstructor() {

        assertEquals(nsgsc, game1.getHomeTeam());
        assertEquals(fusion, game1.getAwayTeam());
        assertEquals("17:00", game1.getTime());
        assertEquals("Windsor Secondary", game1.getLocation());
        assertEquals(0, game1.getGoalscorers().size());
        assertFalse(game1.getGamePlayed());
        assertEquals("NSGSC vs Fusion", game1.getTeams());
    }

    //set location, set time, set away goals, set home goals
    @Test
    public void testUpdatingGameInfo() {
        game1.setTime("19:00");
        assertEquals("19:00", game1.getTime());

        game1.setLocation("Sutherland Secondary");
        assertEquals("Sutherland Secondary", game1.getLocation());

        game1.setHomeGoals(0);
        assertEquals(0, game1.getHomeGoals());

        game1.setAwayGoals(7);
        assertEquals(7, game1.getAwayGoals());
    }

    @Test
    public void testAddingGoalscorers() {
        player1 = new Player(fusion, "Heather", "Ross");
        player2 = new Player(nsgsc, "Rachel", "Moore");

        game1.setHomeGoals(2);
        game1.setAwayGoals(1);

        game1.addGoalscorer(player1);
        assertEquals(1, game1.getGoalscorers().size());
        assertTrue(game1.getGoalscorers().contains(player1));
        assertEquals(1, player1.getGoals());

        game1.addGoalscorer(player2);
        assertEquals(2, game1.getGoalscorers().size());
        assertTrue(game1.getGoalscorers().contains(player2));
        assertEquals(1, player2.getGoals());

        game1.addGoalscorer(player1);
        assertEquals(2, game1.getGoalscorers().size());
        assertTrue(game1.getGoalscorers().contains(player1));
        assertEquals(2, player1.getGoals());
    }

    @Test
    public void testFinishGame() {
        game1.finishGame();
        assertTrue(game1.getGamePlayed());
    }

    @Test
    public void testSetters() {
        List<Player> players = new ArrayList<>();
        player1 = new Player(fusion, "Heather", "Ross");player2 = new Player(nsgsc, "Rachel", "Moore");
        player2 = new Player(nsgsc, "Rachel", "Moore");
        players.add(player1);
        players.add(player2);

        game1.setHomeGoals(1);
        game1.setAwayGoals(0);
        game1.setGoalscorers(players);
        game1.setGamePlayed(true);
        assertTrue(game1.getGamePlayed());
        assertEquals(2, game1.getGoalscorers().size());
        assertTrue(game1.getGoalscorers().contains(player1));
        assertTrue(game1.getGoalscorers().contains(player2));

    }


}