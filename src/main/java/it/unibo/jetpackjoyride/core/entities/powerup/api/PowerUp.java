package it.unibo.jetpackjoyride.core.entities.powerup.api;

import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;

public interface PowerUp extends Entity {
    enum PowerUpType {
        MRCUDDLES, LILSTOMPER, PROFITBIRD, DUKEFISHRON
    }

    enum PowerUpStatus {
        CHARGING, ACTIVE, DEACTIVATED, DESTROYED
    }

    enum PerformingAction {
        WALKING, JUMPING, ASCENDING, DESCENDING, GLIDING, LANDING
    }

    PowerUpType getPowerUpType();

    PowerUpStatus getPowerUpStatus();

    PerformingAction getPerformingAction();

    void update(boolean isSpaceBarPressed);
}
