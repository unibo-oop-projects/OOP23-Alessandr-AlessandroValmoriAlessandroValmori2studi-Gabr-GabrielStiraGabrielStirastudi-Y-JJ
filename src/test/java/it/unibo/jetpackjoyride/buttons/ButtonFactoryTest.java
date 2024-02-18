package it.unibo.jetpackjoyride.buttons;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import it.unibo.jetpackjoyride.menu.buttoncommand.ButtonFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ButtonFactoryTest extends ApplicationTest {

    @Override
    public void start(Stage stage) {
      
    }

    @Test
    public void testButtonCreationWithValidImage() { 
        interact(() -> {      
            Button button = ButtonFactory.createButton("PlayAgain", event -> {}, 100, 100);
            assertNotNull(button.getGraphic(), "Button should have a graphic");
            assertTrue(button.getGraphic() instanceof ImageView, "Button graphic should be an ImageView");
        });
    }

    @Test
    public void testButtonCreationFallbackToText() {
        interact(() -> {
            Button button = ButtonFactory.createButton("invalidImageName", event -> {}, 100, 100);
            assertEquals("invalidImageName", button.getText(), "Button text should match the provided text");
            assertNotNull(button, "Button should not be null");
            assertEquals(null, button.getGraphic());
        });
    }
}
