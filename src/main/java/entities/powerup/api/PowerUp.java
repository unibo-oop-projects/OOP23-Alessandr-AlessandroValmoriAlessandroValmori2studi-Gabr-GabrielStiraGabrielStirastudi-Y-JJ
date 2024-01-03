package entities.powerup.api;

import entities.entity.api.Entity;

public interface PowerUp extends Entity {
    enum PowerUpType {
        MRCUDDLE, BADASHOG, LILSTOMPER
    }

    PowerUpType getPowerUpType();
}
