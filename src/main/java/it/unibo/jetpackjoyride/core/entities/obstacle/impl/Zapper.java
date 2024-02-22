package it.unibo.jetpackjoyride.core.entities.obstacle.impl;

import it.unibo.jetpackjoyride.core.entities.obstacle.api.AbstractObstacle;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

public final class Zapper extends AbstractObstacle {
    private static final Double OUTOFBOUNDSSX = -100.0;
    public Zapper(final Movement movement, final Hitbox hitbox) {
        super(ObstacleType.ZAPPER, movement, hitbox);
        this.entityStatus = EntityStatus.ACTIVE;
    }

    @Override
    protected void updateStatus(final boolean isSpaceBarPressed) {
        if (this.movement.getRealPosition().get1() < OUTOFBOUNDSSX) {
            this.entityStatus = EntityStatus.INACTIVE;
        }
        //System.out.println(" hitbox " + this.hitbox.getHitboxDimensions());
    }
}