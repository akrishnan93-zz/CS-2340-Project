import model.Crop;
import model.Plot;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
* Tests for plot object.
*/
public class PlotTest {
    private static final int TIMEOUT = 200;
    private Plot plot;
    private Crop testCrop;

    /**
     * Sets up a new plot.
     */
    @Before
    public void setUp() {
        plot = new Plot(testCrop, 1);
    }

    /**
     * Test empty plot.
     */
    @Test(timeout = TIMEOUT)
    public void testEmptyGetPlot() {
        assertNull(plot.getCrop());
    }

    /**
     * Test set crop for a plot.
     */
    @Test(timeout = TIMEOUT)
    public void testSetCrop() {
        plot.setCrop(testCrop);
        assertEquals(plot.getCrop(), testCrop);
    }

    /**
     * Test default infested values.
     */
    @Test(timeout = TIMEOUT)
    public void testDefaultInfested() {
        assertFalse(plot.isInfested());
    }

    /**
     * Test set infested difficulty 1.
     */
    @Test(timeout = TIMEOUT)
    public void testSetInfestedDifficulty1() {
        while (!plot.isInfested()) {
            plot.setInfested(1);
        }
        assertTrue(plot.isInfested());
    }

    /**
     * Test set infested difficulty 2.
     */
    @Test(timeout = TIMEOUT)
    public void testSetInfestedDifficulty2() {
        while (!plot.isInfested()) {
            plot.setInfested(2);
        }
        assertTrue(plot.isInfested());
    }

    /**
     * Test set infested difficulty 3.
     */
    @Test(timeout = TIMEOUT)
    public void testSetInfestedDifficulty3() {
        while (!plot.isInfested()) {
            plot.setInfested(3);
        }
        assertTrue(plot.isInfested());
    }

    /**
     * Test set invalid infested.
     */
    @Test(expected = RuntimeException.class)
    public void testSetInvalidInfested() {
        plot.setInfested(-1);
    }

    @Test(timeout = TIMEOUT)
    public void testGetFertilizer() {
        assertEquals(0, plot.getFertilizer());
    }


    @Test(timeout = TIMEOUT)
    public void testAddFertilizer() {
        plot.addFertilizer(4);
        assertEquals(4, plot.getFertilizer());
    }

    @Test(timeout = TIMEOUT)
    public void testGetWaterLevel() {
        assertEquals(0, plot.getWaterLevel());
    }

    @Test(timeout = TIMEOUT)
    public void testAddWaterLevel() {
        plot.water(1);
        assertEquals(1, plot.getWaterLevel());
        plot.water(-1);
        assertEquals(0, plot.getWaterLevel());
        plot.water(30);
        assertTrue(plot.getIsDead());
    }

    @Test(timeout = TIMEOUT)
    public void testWater() {
        plot.water(1);

    }

    @Test(timeout = TIMEOUT)
    public void testGetMaturation() {
        plot.setCurrMaturity(3);
        assertEquals(3, plot.getCurrMaturity());
    }


    @Test(timeout = TIMEOUT)
    public void testGetHealth() {
        assertEquals(0, plot.getHealth());
    }

    @Test(timeout = TIMEOUT)
    public void testIsDead() {
        plot.dead();
        assertEquals(0, plot.getWaterLevel());
        assertEquals(0, plot.getCurrMaturity());
        assertTrue(plot.getIsDead());
    }

    @Test(timeout = TIMEOUT)
    public void testMature() {
        int currMaturity = plot.getCurrMaturity();
        plot.mature(3);
        if (plot.getIsDead()) {
            assertEquals(0, plot.getCurrMaturity());
        } else {
            assertEquals(3, plot.getCurrMaturity());

        }
    }

    @Test(timeout = TIMEOUT)
    public void testIsMatured() {
        Crop testCrop2 = Crop.WHEAT;
        Plot plot2 = new Plot(testCrop2, 2);
        assertFalse(plot2.isMatured());
        plot2.setCurrMaturity(9);
        assertTrue(plot2.isMatured());
    }


}