package it.unibo.jetpackjoyride.utilities;



import javafx.scene.input.KeyCode;

public class InputHandler {
    private boolean isSpacePressed=false;

    public InputHandler() {
        // Initialize input states
        isSpacePressed = false;
    }

    public boolean isSpacePressed() {
        return isSpacePressed;
    }

    public void keyPressed(KeyCode keyCode) {
        if (keyCode == KeyCode.SPACE) {
            isSpacePressed = true;
        }
    }

    public void keyReleased(KeyCode keyCode) {
        if (keyCode == KeyCode.SPACE) {
            isSpacePressed = false;
        }
    }
}