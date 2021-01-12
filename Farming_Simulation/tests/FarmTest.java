

import model.Crop;
import model.Player;
import model.Plot;
import model.Farm;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FarmTest {
    private static final int TIMEOUT = 200;
    private Plot testPlot;
    private Farm farm;
    private Crop testCrop;
    private Player player;
    private ArrayList<Plot> plotList;

    /**
     * Sets up a new farm.
     */
    @Before
    public void setUp() {
        player = new Player("Easy", "Test", "WHEAT SEED", "SUMMER");
        testPlot = new Plot(testCrop, 1);
        plotList = new ArrayList<Plot>();
        plotList.add(0, testPlot);
        farm = new Farm(player, plotList);
    }

    /**
     * Test the maturation mechanic of the farm
     */
    @Test(timeout = TIMEOUT)
    public void testMatureFarm() {
        int currDay = player.getDay();
        farm.matureFarm();
        assertEquals(currDay + 1, player.getDay());
    }

    /**
     * Test worker does work
     */
    @Test(timeout = TIMEOUT)
    public void testWorker() {
        player.setInventoryBuy("WORKER3", 1);
        player.setInventoryBuy("WATER", 18);
        for (int i = 0; i < player.getInventory().keySet().size(); i++) {
            farm.plantSeeds(i);
        }
        farm.menialWork();
        for (Plot p : plotList) {
            if (!p.toString().contains("DEAD") && !p.toString().contains("EMPTY PLOT")) {
                assertEquals(p.getMaxWaterlevel() - 1, p.getWaterLevel());
            }
        }
    }

    /**
     * Test worker gets paid daily
     */
    @Test(timeout = TIMEOUT)
    public void testPayWorker() {
        player.setInventoryBuy("WORKER3", 1);
        int first = player.getBalance();
        farm.matureFarm();
        assertTrue(first > player.getBalance());
    }

    /**
     * Test worker leaves when there isn't enough money to pay it
     */
    @Test(timeout = TIMEOUT)
    public void testWorkerLeaves() {
        player.setInventoryBuy("WORKER3", 1);
        assertTrue(player.getInventory().containsKey("WORKER3"));
        for (int i = 0; i < 1000; i++) {
            farm.matureFarm();
        }
        assertFalse(player.getInventory().containsKey("WORKER3"));
    }

    @Test(timeout = TIMEOUT)
    public void testBuyPlot() {
        int bef = farm.getPlotList().size();
        player.setInventoryBuy("FARM EXPANSION", 1);
        int aft = farm.getPlotList().size();
        assertTrue(bef <= aft);

    }
}