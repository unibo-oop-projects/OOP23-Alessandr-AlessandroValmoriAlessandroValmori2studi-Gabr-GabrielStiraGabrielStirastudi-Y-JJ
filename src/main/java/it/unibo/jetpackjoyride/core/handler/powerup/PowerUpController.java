package it.unibo.jetpackjoyride.core.handler.powerup;

import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp;
import javafx.scene.Group;
import javafx.scene.image.ImageView;

public final class PowerUpController {
    private final PowerUp model;
    private final PowerUpView view;

    public PowerUpController(final PowerUp model, final PowerUpView view) {
        this.model = model;
        this.view = view;
    }

    public void update(final boolean isSpaceBarPressed, final Group powerUpGroup) {
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
