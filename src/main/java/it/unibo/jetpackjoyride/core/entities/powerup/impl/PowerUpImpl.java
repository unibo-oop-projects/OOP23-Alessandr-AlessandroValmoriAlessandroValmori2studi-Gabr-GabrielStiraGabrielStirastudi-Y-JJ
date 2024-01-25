package it.unibo.jetpackjoyride.core.entities.powerup.impl;

import static it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityType.POWERUP;

import it.unibo.jetpackjoyride.core.entities.entity.api.AbstractEntity;

import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp;
import it.unibo.jetpackjoyride.core.hitbox.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

public class PowerUpImpl extends AbstractEntity implements PowerUp{
    private final PowerUpType powerUpType;
    
    public PowerUpImpl(PowerUpType powerUpType, Movement movement, Hitbox hitbox) {
        super(POWERUP, movement, hitbox);
        this.powerUpType = powerUpType;
    }

    @Override
    public PowerUpType getPowerUpType() {
        return this.powerUpType;
    }
    
}
