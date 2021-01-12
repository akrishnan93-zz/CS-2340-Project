import model.Crop;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for the Crop object.
 */
public class CropTest {
    private static final int TIMEOUT = 200;
    /**
     * The Wheat.
     */
    private Crop wheat = Crop.WHEAT;
    /**
     * The Barley.
     */
    private Crop carrot = Crop.CARROT;
    /**
     * The Corn.
     */
    private Crop potato = Crop.POTATO;


    @Test(timeout = TIMEOUT)
    public void testWheat() {
        assertEquals("WHEAT", wheat.getName());
        assertEquals(4, wheat.getMaxMaturity());
    }

    @Test(timeout = TIMEOUT)
    public void testCarrot() {
        assertEquals("CARROT", carrot.getName());
        assertEquals(6, carrot.getMaxMaturity());
    }

    @Test(timeout = TIMEOUT)
    public void testPotato() {
        assertEquals("POTATO", potato.getName());
        assertEquals(5, potato.getMaxMaturity());
    }
}
