package it.unibo.jetpackjoyride.core.handler.powerup;

import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp;
import javafx.scene.Group;
import javafx.scene.image.ImageView;

public class PowerUpController {
    private PowerUp model;
    private PowerUpView view;

    public PowerUpController(PowerUp model, PowerUpView view) {
        this.model = model;
        this.view = view;
    }

    public void update(boolean isSpaceBarPressed, Group powerUpGroup) {
        this.model.update(isSpaceBarPressed);
        this.view.updateView(model);
    }

    public ImageView getImageView() {
        return this.view.getImageView();
    }

    public PowerUp getPowerUpModel() {
        return this.model;
    }
}

