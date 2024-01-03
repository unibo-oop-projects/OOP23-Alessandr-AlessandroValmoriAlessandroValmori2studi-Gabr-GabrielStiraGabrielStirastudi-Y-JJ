package entities.powerup.impl;

import static entities.entity.api.Entity.EntityType.POWERUP;

import entities.entity.api.AbstractEntity;

import entities.powerup.api.PowerUp;
import hitbox.Hitbox;
import movement.impl.MovementImpl;

public class PowerUpImpl extends AbstractEntity implements PowerUp{
    private final PowerUpType powerUpType;
    
    public PowerUpImpl(PowerUpType powerUpType, MovementImpl movement, Hitbox hitbox) {
        super(POWERUP, movement, hitbox);
        this.powerUpType = powerUpType;
    }

    @Override
    public PowerUpType getPowerUpType() {
        return this.powerUpType;
    }
    
}
