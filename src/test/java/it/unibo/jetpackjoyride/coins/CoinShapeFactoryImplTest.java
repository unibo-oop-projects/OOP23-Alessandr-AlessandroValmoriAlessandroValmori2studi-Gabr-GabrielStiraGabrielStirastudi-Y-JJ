package it.unibo.jetpackjoyride.coins;

import it.unibo.jetpackjoyride.core.entities.coin.api.CoinShapeFactory;
import it.unibo.jetpackjoyride.core.entities.coin.impl.CoinShapeFactoryImpl;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;


/**
 * Test cases for the ButtonFactory class.
 * @author yukai.zhou@studio.unibo.it
 */
class CoinShapeFactoryImplTest {

    private static final int COUNT = 50;
    private static final double MIN_SIZE_RATE = 0.2;
    private static final double MAX_SIZE_RATE = 0.9;
    private CoinShapeFactory coinShapeFactory;

    /**
     * Test that the list of shapes should not be null or empty.
     */
    @Test
    void theListShouldNotbeNullorEmpty() {
        coinShapeFactory = new CoinShapeFactoryImpl();
        for (int i = 0; i < COUNT; i++) {
            final var list = this.coinShapeFactory.regularShapes();
            assertFalse(list.isEmpty());
        }
    }

    /**
     * Test Y coordinates are within default size bounds.
     */
    @Test
    void testYCoordinatesWithinDefaultSize() {
        final double screenY = GameInfo.getInstance().getScreenHeight();
        coinShapeFactory = new CoinShapeFactoryImpl();
        for (final var shape : coinShapeFactory.regularShapes()) {
            assertTrue("Y coordinate is above minimum bound", shape.get2() >=  screenY * MIN_SIZE_RATE);
            assertTrue("Y coordinate is below maximum bound", shape.get2() <=  screenY * MAX_SIZE_RATE);
        }
    }
}
