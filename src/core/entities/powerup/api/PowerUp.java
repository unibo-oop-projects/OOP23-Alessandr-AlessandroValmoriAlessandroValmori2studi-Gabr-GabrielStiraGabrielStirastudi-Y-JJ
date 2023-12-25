package core.entities.powerup.api;

import core.entities.entity.api.Entity;

public interface PowerUp extends Entity {
    enum PowerUpType {
        MRCUDDLE, BADASHOG, LILSTOMPER
    }

    PowerUpType getPowerUpType();
}
