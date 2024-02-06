package it.unibo.jetpackjoyride.core.entities.powerup.api;

import static it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityType.POWERUP;

import it.unibo.jetpackjoyride.core.entities.entity.api.AbstractEntity;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

public abstract class AbstractPowerUp extends AbstractEntity implements PowerUp {

    public final PowerUpType powerUpType;
    public PowerUpStatus powerUpStatus;
    public PerformingAction performingAction;

    public AbstractPowerUp(PowerUpType powerUpType, Movement movement, Hitbox hitbox) {
        super(POWERUP, movement, hitbox);
        this.powerUpType = powerUpType;
        this.powerUpStatus = PowerUpStatus.CHARGING;
    }

    public PowerUpType getPowerUpType() {
        return this.powerUpType;
    }

    public PowerUpStatus getPowerUpStatus() {
        return this.powerUpStatus;
    }

    public PerformingAction getPerformingAction() {
        return this.performingAction;
    }
    
}
