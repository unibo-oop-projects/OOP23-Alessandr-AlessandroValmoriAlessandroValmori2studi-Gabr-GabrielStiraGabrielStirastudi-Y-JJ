package it.unibo.jetpackjoyride.utilities;

import javafx.scene.input.KeyCode;

/**
 * Utility class for handling input events.
 * Manages the state of the space key press.
 */
public final class InputHandler {
    private boolean isSpacePressed;

    /**
     * Constructs a new InputHandler.
     * Initializes the space key press state to false.
     */
    public InputHandler() {
        isSpacePressed = false;
    }

    /**
     * Checks if the space key is pressed.
     * @return true if the space key is pressed, false otherwise.
     */
    public boolean isSpacePressed() {
        return isSpacePressed;
    }

    /**
     * Handles key press events.
     * Sets the space key press state to true if the space key is pressed.
     * @param keyCode The KeyCode representing the pressed key.
     */
    public void keyPressed(KeyCode keyCode) {
        if (keyCode == KeyCode.SPACE) {
            isSpacePressed = true;
        }
    }

    /**
     * Handles key release events.
     * Sets the space key press state to false if the space key is released.
     * @param keyCode The KeyCode representing the released key.
     */
    public void keyReleased(KeyCode keyCode) {
        if (keyCode == KeyCode.SPACE) {
            isSpacePressed = false;
        }
    }
}
