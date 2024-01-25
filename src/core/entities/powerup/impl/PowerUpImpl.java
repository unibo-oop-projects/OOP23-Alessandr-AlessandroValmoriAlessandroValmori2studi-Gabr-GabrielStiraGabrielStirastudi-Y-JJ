package core.entities.powerup.impl;

import static core.entities.entity.api.Entity.EntityType.POWERUP;

import core.entities.entity.api.AbstractEntity;

import core.entities.powerup.api.PowerUp;
import core.hitbox.Hitbox;
import core.movement.Movement;

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
