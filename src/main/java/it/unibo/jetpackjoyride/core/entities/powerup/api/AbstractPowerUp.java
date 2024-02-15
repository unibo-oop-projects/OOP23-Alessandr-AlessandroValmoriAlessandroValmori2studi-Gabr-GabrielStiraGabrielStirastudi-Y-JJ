package it.unibo.jetpackjoyride.core.entities.powerup.api;

import static it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityType.POWERUP;

import it.unibo.jetpackjoyride.core.entities.entity.api.AbstractEntity;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

public abstract class AbstractPowerUp extends AbstractEntity implements PowerUp {

    public final PowerUpType powerUpType;
    public PerformingAction performingAction;

    public AbstractPowerUp(final PowerUpType powerUpType, final Movement movement, final Hitbox hitbox) {
        super(POWERUP, movement, hitbox);
        this.powerUpType = powerUpType;
        this.entityStatus = EntityStatus.CHARGING;
    }

    @Override
    public PowerUpType getPowerUpType() {
        return this.powerUpType;
    }

    @Override
    public PerformingAction getPerformingAction() {
        return this.performingAction;
    }

    @Override
    public void update(final boolean isSpaceBarPressed) {
        super.update(isSpaceBarPressed);
    }
}
