package it.unibo.jetpackjoyride.core.entities.controllable;

import static it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityType.CONTROLLABLE;
import static it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityType.PLAYER;

import it.unibo.jetpackjoyride.core.entities.entity.api.AbstractEntity;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp.PerformingAction;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp.PowerUpStatus;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp.PowerUpType;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

public class AbstractControllable /*extends AbstractEntity */implements Controllable{
    
    public PerformingAction performingAction;
    public final ControllableType controllableType;
    public PowerUpStatus powerUpStatus;

     public AbstractControllable(ControllableType controllableType, Movement movement, Hitbox hitbox) {
        super(CONTROLLABLE, movement, hitbox);
        this.controllableType = controllableType;
        this.powerUpStatus = PowerUpStatus.CHARGING;
    }

}
