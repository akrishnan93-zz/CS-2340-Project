import model.Market;
import model.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MarketTest {
    private static final int TIMEOUT = 200;
    private Player playerDifficulty1;
    private Player playerDifficulty2;
    private Player playerDifficulty3;

    private Market marketDifficulty1;
    private Market marketDifficulty2;
    private Market marketDifficulty3;

    /**
     * Sets up players.
     */
    @Before
    public void setUp() {
        playerDifficulty1 = new Player("Easy", "Test", "WHEAT SEED", "SUMMER");
        playerDifficulty2 = new Player("Medium", "Test", "WHEAT SEED", "SUMMER");
        playerDifficulty3 = new Player("Hard", "Test", "WHEAT SEED", "SUMMER");

        marketDifficulty1 = new Market(playerDifficulty1);
        marketDifficulty2 = new Market(playerDifficulty2);
        marketDifficulty3 = new Market(playerDifficulty3);
    }

    /**
     * Test for getting difficulty.
     */
    @Test(timeout = TIMEOUT)
    public void testGetSellPrices() {
        assertNotEquals(null, marketDifficulty1.getSellPrices());
        assertNotEquals(null, marketDifficulty2.getSellPrices());
        assertNotEquals(null, marketDifficulty3.getSellPrices());
    }

    /**
     * Test for getting difficulty.
     */
    @Test(timeout = TIMEOUT)
    public void testGetBuyPrices() {
        assertNotEquals(null, marketDifficulty1.getBuyPrices());
        assertNotEquals(null, marketDifficulty2.getBuyPrices());
        assertNotEquals(null, marketDifficulty3.getBuyPrices());
    }

}
