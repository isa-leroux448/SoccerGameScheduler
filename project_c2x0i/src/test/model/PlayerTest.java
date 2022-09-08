package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerTest {
    Player Isabella;
    Team fusion;

    //need to fix add player
    @Test
    public void testPlayerConstructor() {
        fusion = new Team("Fusion");
        Isabella = new Player(fusion, "Isabella", "Leroux");

        assertEquals(fusion, Isabella.getTeam());
        assertEquals(0, Isabella.getGoals());
        assertEquals("Isabella", Isabella.getFirstName());
        assertEquals("Leroux", Isabella.getLastName());
        assertEquals("Isabella Leroux", Isabella.getFullName());
        assertEquals(1, fusion.getRoster().size());
        assertTrue(fusion.getRoster().contains(Isabella));
    }
    //checking goal field tested in gameTest

    @Test
    public void testSetGoals() {
        fusion = new Team("Fusion");
        Isabella = new Player(fusion, "Isabella", "Leroux");
        Isabella.setGoals(2);
        assertEquals(2, Isabella.getGoals());
    }
}
