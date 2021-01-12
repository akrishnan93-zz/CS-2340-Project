import model.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for player.
 */
public class PlayerTest {
    private static final int TIMEOUT = 200;
    private Player playerDifficulty1;
    private Player playerDifficulty2;
    private Player playerDifficulty3;
    private Player playerDifficultyInvalid;

    /**
     * Sets up players.
     */
    @Before
    public void setUp() {
        playerDifficulty1 = new Player("Easy", "Test", "WHEAT SEED", "SUMMER");
        playerDifficulty2 = new Player("Medium", "Test", "WHEAT SEED", "SUMMER");
        playerDifficulty3 = new Player("Hard", "Test", "WHEAT SEED", "SUMMER");
        playerDifficultyInvalid = new Player("Way too hard", "Test", "WHEAT SEED", "SUMMER");
    }

    /**
     * Test for getting current difficulty.
     */
    @Test(timeout = TIMEOUT)
    public void testGetDifficulty() {
        assertEquals(1, playerDifficulty1.getDifficulty());
        assertEquals(2, playerDifficulty2.getDifficulty());
        assertEquals(3, playerDifficulty3.getDifficulty());
        assertEquals(1, playerDifficultyInvalid.getDifficulty());
    }

    /**
     * Test for setting invalid difficulty.
     */
    @Test(timeout = TIMEOUT)
    public void testSetDifficulty() {
        playerDifficultyInvalid.setDifficulty(2);
        assertEquals(2, playerDifficultyInvalid.getDifficulty());
    }

    /**
     * Test for getting balance.
     */
    @Test(timeout = TIMEOUT)
    public void testGetBalance() {
        assertEquals(3000, playerDifficulty1.getBalance());
        assertEquals(2000, playerDifficulty2.getBalance());
        assertEquals(1000, playerDifficulty3.getBalance());
        assertEquals(100, playerDifficultyInvalid.getBalance());
    }

    /**
     * Test for setting balance.
     */
    @Test(timeout = TIMEOUT)
    public void testSetBalance() {
        playerDifficultyInvalid.setBalance(200);
        assertEquals(200, playerDifficultyInvalid.getBalance());
    }

    /**
     * Test for getting default exp.
     */
    @Test(timeout = TIMEOUT)
    public void testGetDefaultExp() {
        assertEquals(0, playerDifficulty1.getExp());
    }

    /**
     * Test for setting exp.
     */
    @Test(timeout = TIMEOUT)
    public void testSetExp() {
        playerDifficulty1.setExp(5);
        assertEquals(5, playerDifficulty1.getExp());
    }

    @Test(timeout = TIMEOUT)
    public void testNextDay() {
        int currDay = playerDifficulty1.getDay();
        playerDifficulty1.nextDay();
        assertEquals(currDay + 1, playerDifficulty1.getDay());
    }

    @Test(timeout = TIMEOUT)
    public void testBuy() {
        playerDifficulty1.setInventoryBuy("TEST", 1);
        assertTrue(playerDifficulty1.getInventory().containsKey("TEST"));
    }

    @Test(timeout = TIMEOUT)
    public void testOverBuy() {
        playerDifficulty1.setInventoryBuy("TEST", 30);
        assertFalse(playerDifficulty1.getInventory().containsKey("TEST"));
    }

    @Test(timeout = TIMEOUT)
    public void testWin() {
        playerDifficulty1.setBalance(5000);
        assertTrue(playerDifficulty1.hasWon());
    }

    @Test(timeout = TIMEOUT)
    public void testLose() {
        playerDifficulty1.setBalance(0);
        assertTrue(playerDifficulty1.hasLost());
    }

    @Test(timeout = TIMEOUT)
    public void testMaxHarvest() {
        int maxbef = playerDifficulty1.getMaxHarvestPerDay();
        playerDifficulty1.setInventoryBuy("TRACTOR", 1);
        int maxaft = playerDifficulty1.getMaxHarvestPerDay();
        assertTrue(maxbef <= maxaft);
    }

    @Test(timeout = TIMEOUT)
    public void testMaxWater() {
        int maxbef = playerDifficulty1.getMaxWaterPerDay();
        playerDifficulty1.setInventoryBuy("IRRIGATION", 1);
        int maxaft = playerDifficulty1.getMaxWaterPerDay();
        assertTrue(maxbef <= maxaft);
    }
}