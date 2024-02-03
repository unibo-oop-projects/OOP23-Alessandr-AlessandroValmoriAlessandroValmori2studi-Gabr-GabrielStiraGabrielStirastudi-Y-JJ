package it.unibo.jetpackjoyride.core.entities.powerup.api;

import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;

public interface PowerUp extends Entity {
    enum PowerUpType {
        MRCUDDLES, BADASHOG, LILSTOMPER, CUSTOM
    }

    enum PowerUpStatus {
        CHARGING, ACTIVE, DEACTIVATED, DESTROYED
    }

    public enum PerformingAction {
        WALKING, JUMPING, ASCENDING, DESCENDING, LANDING
    }

    PowerUpType getPowerUpType();

    PowerUpStatus getPowerUpStatus();

    PerformingAction getPerformingAction();

    void update(boolean isSpaceBarPressed);
}
