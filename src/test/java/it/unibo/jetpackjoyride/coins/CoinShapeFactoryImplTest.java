package it.unibo.jetpackjoyride.coins;

import it.unibo.jetpackjoyride.core.entities.coin.impl.CoinShapeFactoryImpl;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;

import java.util.List;

import static org.junit.Assert.*;

public class CoinShapeFactoryImplTest {

    private CoinShapeFactoryImpl coinShapeFactory;

    @org.junit.Before
    public void setUp() {
        coinShapeFactory = new CoinShapeFactoryImpl();
       
    }

    @org.junit.Test
    public void theListShouldNotbeNullorEmpty() {
        for (int i = 0; i < 50; i++) {
            List<Pair<Double, Double>> shapes = coinShapeFactory.regularShapes();
            assertNotNull("The list of shapes should not be null  " + i, shapes);
            assertFalse("The list of shapes should not be empty " + i, shapes.isEmpty());
        }
    }

    @org.junit.Test
    public void testYCoordinatesWithinDefaultSize() {
        List<Pair<Double, Double>> shapes = coinShapeFactory.regularShapes();
        assertNotNull("The list of shapes should not be null", shapes);
        assertFalse("The list of shapes should not be empty", shapes.isEmpty());

        for (Pair<Double, Double> shape : shapes) {
            GameInfo gameInfo = GameInfo.getInstance();
            double y = shape.get2();
            assertTrue("Y coordinate is below minimum bound", y >= gameInfo.getDefaultHeight()* 0.2);
            assertTrue("Y coordinate is above maximum bound", y <= gameInfo.getDefaultHeight() * 0.9);
        }
    }
    
}
