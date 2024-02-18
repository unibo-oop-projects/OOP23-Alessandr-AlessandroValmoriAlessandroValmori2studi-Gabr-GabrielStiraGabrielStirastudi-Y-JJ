package it.unibo.jetpackjoyride.map;

import it.unibo.jetpackjoyride.core.map.impl.MapBackgroundImpl;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;

import javafx.stage.Stage;
import javafx.scene.Scene;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MapBackgroundImplTest extends ApplicationTest {

    private MapBackgroundImpl mapBackground;

    @Override
    public void start(Stage stage) {
        mapBackground = new MapBackgroundImpl();
        stage.setScene(new Scene(mapBackground.getPane(), 800, 600));
        stage.show();
    }

    @Test
    public void isRisizebleWithGameInfo() {
        interact(() -> {
            GameInfo gameInfo = GameInfo.getInstance();
            Pair<Double, Double> size = mapBackground.getSize();
            mapBackground.updateBackground();
            assertNotNull(size, "The size should not be null");
            assertEquals(size, new Pair<>(gameInfo.getScreenWidth(), gameInfo.getScreenHeight()));

            gameInfo.updateInfo(1600, 800);
            mapBackground.updateBackground();
            Pair<Double, Double> size1 = mapBackground.getSize();
            assertEquals(1600, size1.get1());
            assertEquals(800, size1.get2());

        });
    }

    @Test
    public void testUpdateBackground() {
        interact(() -> {
            double x1 = mapBackground.getPosX().get1();
            double x2 = mapBackground.getPosX().get2();
            mapBackground.updateBackground();
            assertEquals(x1-GameInfo.moveSpeed.get(), mapBackground.getPosX().get1());
            assertEquals(x2-GameInfo.moveSpeed.get(), mapBackground.getPosX().get2());
        });
    }

    @Test
    public void testReset() {
        interact(() -> {
            GameInfo.moveSpeed.set(10);
            assertEquals(10, GameInfo.moveSpeed.get());
            mapBackground.reset();
            assertEquals(5, GameInfo.moveSpeed.get());
        });
    }

    @Test
    public void testGetPosX() {
        Pair<Double, Double> posX = mapBackground.getPosX();
        assertNotNull(posX, "The position X should not be null");
      
    }

    @Test
    public void testGetSize() {
        GameInfo gameInfo = GameInfo.getInstance();
        Pair<Double, Double> size = mapBackground.getSize();
        mapBackground.updateBackground();
        assertNotNull(size, "The size should not be null");
        assertEquals(size, new Pair<>(gameInfo.getScreenWidth(), gameInfo.getScreenHeight()));
        
    }
}
