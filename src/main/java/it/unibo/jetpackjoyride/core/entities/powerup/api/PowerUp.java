package it.unibo.jetpackjoyride.core.entities.powerup.api;

import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;

public interface PowerUp extends Entity {
    enum PowerUpType {
        MRCUDDLE, BADASHOG, LILSTOMPER
    }

    PowerUpType getPowerUpType();
}
