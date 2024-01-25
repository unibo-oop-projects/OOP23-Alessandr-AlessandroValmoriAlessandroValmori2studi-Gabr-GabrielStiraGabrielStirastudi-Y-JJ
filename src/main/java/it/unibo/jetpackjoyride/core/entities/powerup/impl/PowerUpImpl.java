package it.unibo.jetpackjoyride.core.entities.powerup.impl;

import static it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityType.POWERUP;

import it.unibo.jetpackjoyride.core.entities.entity.api.AbstractEntity;

import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp;
import it.unibo.jetpackjoyride.core.hitbox.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.utilities.Pair;

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

    @Override
    public Pair<Double, Double> getStartingPosition() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getStartingPosition'");
    }

    @Override
    public Pair<Double, Double> getCurrentPosition() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCurrentPosition'");
    }

    @Override
    public void setCurrentPosition(Pair<Double, Double> newCurrentPosition) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setCurrentPosition'");
    }
    
}
