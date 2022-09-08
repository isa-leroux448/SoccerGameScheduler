package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//import static org.junit.Assert.assertTrue;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TeamTest {
    Team fusion;
    Team nsgsc;
    Team cascades;
    Game game1;
    Game game2;
    Game game3;
    Player player1;
    Player player2;
    Player player3;

    @BeforeEach
    public void setup() {
        fusion = new Team("Fusion");
        nsgsc = new Team("NSGSC");
        cascades = new Team("AFC Cascades");
    }

    @Test
    public void testTeamConstructor() {

        assertEquals("Fusion", fusion.getName());
        assertTrue(fusion.getRoster().isEmpty());

        assertEquals(0, fusion.getWins());
        assertEquals(0, fusion.getLoses());
        assertEquals(0, fusion.getTies());
        assertEquals(0, fusion.getGoalsFor());
        assertEquals(0, fusion.getGoalsAgainst());
        assertEquals(0, fusion.getGamesPlayed());
    }

    // recording one goalscorer
    @Test
    public void testRecord1goalscorer() {
        player1 = new Player(fusion, "Heather", "Ross");
        game1 = new Game(nsgsc, fusion, "17:00", "Windsor Secondary");
        game1.addGoalscorer(player1);
        game1.finishGame();
        assertTrue(fusion.getTeamGoalscorers().contains(player1));
        assertEquals(1, fusion.getTeamGoalscorers().size());
    }

    // recording two goalscorers, one from each team
    @Test
    public void testRecord2goalscorers() {
        player1 = new Player(fusion, "Heather", "Ross");
        player2 = new Player(nsgsc, "Rachel", "Moore");
        game1 = new Game(nsgsc, fusion, "17:00", "Windsor Secondary");

        game1.addGoalscorer(player1);
        game1.addGoalscorer(player2);
        game1.finishGame();

        assertTrue(fusion.getTeamGoalscorers().contains(player1));
        assertEquals(1, fusion.getTeamGoalscorers().size());

        assertTrue(nsgsc.getTeamGoalscorers().contains(player2));
        assertEquals(1, nsgsc.getTeamGoalscorers().size());
    }

    // recording three goalscorers, 2 from 1 team, 1 from the other
    @Test
    public void testRecord3goalscorers() {
        player1 = new Player(fusion, "Heather", "Ross");
        player2 = new Player(nsgsc, "Rachel", "Moore");
        player3 = new Player(fusion, "Melissa", "Oneal");
        game1 = new Game(nsgsc, fusion, "17:00", "Windsor Secondary");

        game1.addGoalscorer(player1);
        game1.addGoalscorer(player2);
        game1.addGoalscorer(player3);
        game1.finishGame();

        assertTrue(fusion.getTeamGoalscorers().contains(player1));
        assertTrue(fusion.getTeamGoalscorers().contains(player3));
        assertEquals(2, fusion.getTeamGoalscorers().size());

        assertTrue(nsgsc.getTeamGoalscorers().contains(player2));
        assertEquals(1, nsgsc.getTeamGoalscorers().size());
    }

    // recording goalscorers in different games
    @Test
    public void testRecordGoalscorersDifferentGames() {
        player1 = new Player(fusion, "Heather", "Ross");
        player2 = new Player(nsgsc, "Rachel", "Moore");
        player3 = new Player(fusion, "Melissa", "Oneal");
        game1 = new Game(nsgsc, fusion, "17:00", "Windsor Secondary");
        game2 = new Game(fusion, nsgsc, "19:00", "Sutherland Secondary");

        game1.addGoalscorer(player1);
        game1.addGoalscorer(player2);
        game1.finishGame();

        assertTrue(fusion.getTeamGoalscorers().contains(player1));
        assertEquals(1, fusion.getTeamGoalscorers().size());

        assertTrue(nsgsc.getTeamGoalscorers().contains(player2));
        assertEquals(1, nsgsc.getTeamGoalscorers().size());

        game2.addGoalscorer(player1);
        game2.addGoalscorer(player3);
        game2.finishGame();

        assertTrue(fusion.getTeamGoalscorers().contains(player1));
        assertTrue(fusion.getTeamGoalscorers().contains(player3));
        assertEquals(2, fusion.getTeamGoalscorers().size());
    }



    // test top goalscorers, need to add players, games, update games

    @Test
    public void testTopGoalscorers() {
        game1 = new Game(nsgsc, fusion, "17:00", "Windsor Secondary");
        player1 = new Player(fusion, "Heather", "Ross");
        player2 = new Player(fusion, "Susan", "Thomas");
        player3 = new Player(fusion, "Melissa", "Oneal");

        game1.addGoalscorer(player2);
        game1.addGoalscorer(player3);
        game1.addGoalscorer(player2);
        game1.addGoalscorer(player1);
        game1.addGoalscorer(player1);
        game1.addGoalscorer(player1);
        game1.finishGame();

        assertEquals(3, fusion.getTeamGoalscorers().size());
        fusion.goalscorers();
        assertEquals(player1, fusion.getTeamGoalscorers().get(0));
        assertEquals(player2, fusion.getTeamGoalscorers().get(1));
        assertEquals(player3, fusion.getTeamGoalscorers().get(2));

    }
    // test adding players to roster
    @Test
    public void testAddPlayerToRoster() {
        player1 = new Player(fusion, "Heather", "Ross");
        assertEquals(1, fusion.getRoster().size());
        assertEquals(player1, fusion.getRoster().get(0));

        player2 = new Player(fusion, "Susan", "Thomas");
        assertEquals(2, fusion.getRoster().size());
        assertEquals(player2, fusion.getRoster().get(1));

        player3 = new Player(fusion, "Melissa", "Oneal");
        assertEquals(3, fusion.getRoster().size());
        assertEquals(player3, fusion.getRoster().get(2));
    }

    @Test
    public void testGameStats1Game() {
        game1 = new Game(nsgsc, fusion, "17:00", "Windsor Secondary");
        game1.setHomeGoals(0);
        game1.setAwayGoals(7);
        game1.finishGame();

        assertEquals(1, fusion.getGamesPlayed());
        assertEquals(3, fusion.getPoints());
        assertEquals(1, fusion.getWins());
        assertEquals(0, fusion.getLoses());
        assertEquals(0, fusion.getTies());
        assertEquals(7, fusion.getGoalsFor());
        assertEquals(0, fusion.getGoalsAgainst());

        assertEquals(1, nsgsc.getGamesPlayed());
        assertEquals(0, nsgsc.getPoints());
        assertEquals(0, nsgsc.getWins());
        assertEquals(1, nsgsc.getLoses());
        assertEquals(0, nsgsc.getTies());
        assertEquals(0, nsgsc.getGoalsFor());
        assertEquals(7, nsgsc.getGoalsAgainst());
    }

    @Test
    public void testGameStatsTie() {
        game1 = new Game(nsgsc, fusion, "17:00", "Windsor Secondary");
        game1.setHomeGoals(3);
        game1.setAwayGoals(3);
        game1.finishGame();

        assertEquals(1, fusion.getGamesPlayed());
        assertEquals(1, fusion.getPoints());
        assertEquals(0, fusion.getWins());
        assertEquals(0, fusion.getLoses());
        assertEquals(1, fusion.getTies());
        assertEquals(3, fusion.getGoalsFor());
        assertEquals(3, fusion.getGoalsAgainst());

        assertEquals(1, nsgsc.getGamesPlayed());
        assertEquals(1, nsgsc.getPoints());
        assertEquals(0, nsgsc.getWins());
        assertEquals(0, nsgsc.getLoses());
        assertEquals(1, nsgsc.getTies());
        assertEquals(3, nsgsc.getGoalsFor());
        assertEquals(3, nsgsc.getGoalsAgainst());
    }

    @Test
    public void testGameStats3Games() {
        game1 = new Game(fusion, nsgsc, "17:00", "Windsor Secondary");
        game1.setHomeGoals(7);
        game1.setAwayGoals(0);
        game1.finishGame();

        assertEquals(1, fusion.getGamesPlayed());
        assertEquals(3, fusion.getPoints());
        assertEquals(1, fusion.getWins());
        assertEquals(0, fusion.getLoses());
        assertEquals(0, fusion.getTies());
        assertEquals(7, fusion.getGoalsFor());
        assertEquals(0, fusion.getGoalsAgainst());

        game2 = new Game(fusion, nsgsc, "19:00", "Sutherland Secondary");
        game2.setHomeGoals(3);
        game2.setAwayGoals(3);
        game2.finishGame();

        assertEquals(2, fusion.getGamesPlayed());
        assertEquals(4, fusion.getPoints());
        assertEquals(1, fusion.getWins());
        assertEquals(0, fusion.getLoses());
        assertEquals(1, fusion.getTies());
        assertEquals(10, fusion.getGoalsFor());
        assertEquals(3, fusion.getGoalsAgainst());

        game3 = new Game(fusion, cascades, "18:00", "Richmond High");
        game3.setHomeGoals(1);
        game3.setAwayGoals(2);
        game3.finishGame();

        assertEquals(3, fusion.getGamesPlayed());
        assertEquals(4, fusion.getPoints());
        assertEquals(1, fusion.getWins());
        assertEquals(1, fusion.getLoses());
        assertEquals(1, fusion.getTies());
        assertEquals(11, fusion.getGoalsFor());
        assertEquals(5, fusion.getGoalsAgainst());
    }

    @Test
    public void testSetters() {

        fusion.setGamesPlayed(2);
        fusion.setPoints(4);
        fusion.setWins(1);
        fusion.setTies(1);
        fusion.setLoses(0);
        fusion.setGoalsFor(4);
        fusion.setGoalsAgainst(1);

        assertEquals(2, fusion.getGamesPlayed());
        assertEquals(4, fusion.getPoints());
        assertEquals(1, fusion.getWins());
        assertEquals(1, fusion.getTies());
        assertEquals(0, fusion.getLoses());
        assertEquals(4, fusion.getGoalsFor());
        assertEquals(1, fusion.getGoalsAgainst());
    }

}
