package it.unibo.jetpackjoyride.core.entities.powerup.api;

import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import it.unibo.jetpackjoyride.core.entities.powerup.impl.LilStomper.PerformingAction;

public interface PowerUp extends Entity {
    enum PowerUpType {
        MRCUDDLE, BADASHOG, LILSTOMPER
    }

    enum PowerUpStatus {
        CHARGING, ACTIVE, DEACTIVATED, DESTROYED
    }

    PowerUpType getPowerUpType();

    PowerUpStatus getPowerUpStatus();

    PerformingAction getPerformingAction();

    void update(boolean isSpaceBarPressed);
}
