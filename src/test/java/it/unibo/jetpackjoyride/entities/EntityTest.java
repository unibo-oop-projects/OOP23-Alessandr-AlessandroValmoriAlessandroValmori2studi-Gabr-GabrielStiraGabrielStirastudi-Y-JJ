package it.unibo.jetpackjoyride.entities; 
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityStatus;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityType;
import it.unibo.jetpackjoyride.core.entities.entity.impl.EntityModelGeneratorImpl;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;
import it.unibo.jetpackjoyride.core.entities.obstacle.impl.Missile;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.hitbox.impl.HitboxImpl;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.core.movement.MovementImpl;
import it.unibo.jetpackjoyride.utilities.Pair;

public class EntityTest{

    private final static Pair<Double,Double> ZEROPAIR = new Pair<>(0.0,0.0);
    private final static Pair<Double,Double> ONEHUNDREDPAIR = new Pair<>(100.0,100.0);
    private final static Pair<Double,Double> FIVEHUNDREDPAIR = new Pair<>(500.0,500.0);
    private EntityModelGeneratorImpl entityFactory;

    @org.junit.Before
    public void init() {
        this.entityFactory = new EntityModelGeneratorImpl();
    }

    @org.junit.Test
    public void obstacleTesting() { 
        Movement missileMovement = new MovementImpl(ZEROPAIR, ZEROPAIR, ZEROPAIR, ZEROPAIR, List.of());
        Hitbox missileHitbox = new HitboxImpl(FIVEHUNDREDPAIR, ONEHUNDREDPAIR);
        Entity missile = new Missile(missileMovement, missileHitbox);

        assertTrue(missile.getEntityType().equals(EntityType.OBSTACLE));
        assertFalse(missile.getEntityType().equals(EntityType.POWERUP));
        assertFalse(missile.getEntityType().equals(EntityType.PICKUP));

        assertTrue(missile.getEntityStatus().equals(EntityStatus.CHARGING));
        assertTrue(missile.getLifetime().equals(0));

        assertTrue(missile.getHitbox().getHitboxDimensions().equals(ONEHUNDREDPAIR));
        assertTrue(missile.getHitbox().getHitboxPosition().equals(FIVEHUNDREDPAIR));

        System.out.println(missileHitbox.getHitboxVertex());
        assertEquals(Set.of(new Pair<>(550.0, 450.0),
                            new Pair<>(450.0, 450.0),
                            new Pair<>(550.0, 550.0),
                            new Pair<>(450.0, 550.0)), missileHitbox.getHitboxVertex());
        
        assertEquals(missile.getEntityMovement().getCurrentPosition(), ZEROPAIR);
        assertEquals(missile.getEntityMovement().getAcceleration(), ZEROPAIR);
        assertEquals(missile.getEntityMovement().getSpeed(), ZEROPAIR);
        assertEquals(missile.getEntityMovement().getRotation(), ZEROPAIR);
        assertEquals(missile.getEntityMovement().getMovementChangers(), List.of());

        missile.getEntityMovement().setCurrentPosition(FIVEHUNDREDPAIR);
        assertEquals(missile.getEntityMovement().getCurrentPosition(), FIVEHUNDREDPAIR);
        missile.getEntityMovement().setSpeed(new Pair<>(1.0, 2.0));
        assertEquals(missile.getEntityMovement().getSpeed(), new Pair<>(1.0, 2.0));

        missile.update(false);
        assertEquals(missile.getEntityMovement().getCurrentPosition(), 
                     new Pair<>(FIVEHUNDREDPAIR.get1()+1.0, FIVEHUNDREDPAIR.get2()+2.0));

        //The entity has its status set to CHARING so the hitbox is not update (it doesn't make sense to update
        //the hitbox and so do useless calculations if the entity is not ACTIVE)
        assertNotEquals(missile.getEntityMovement().getCurrentPosition(),missileHitbox.getHitboxPosition());

        missile.setEntityStatus(EntityStatus.ACTIVE);

        //Now the entity is ACTIVE and the hitbox is calculated
        assertEquals(missile.getEntityMovement().getCurrentPosition(),missileHitbox.getHitboxPosition());

        missile.getEntityMovement().setCurrentPosition(ZEROPAIR);
        missile.getEntityMovement().setSpeed(new Pair<>(10.0, 10.0));
        for(int i=0; i<50; i++) {
            missile.update(false);
        }
        System.out.println(missile.getEntityMovement().getCurrentPosition());
        assertEquals(missile.getEntityMovement().getCurrentPosition(),new Pair<>(500.0, 500.0));

        Entity missile2 = this.entityFactory.generateObstacle(ObstacleType.MISSILE, missileMovement);

    }

}


