package it.unibo.jetpackjoyride.entities; 
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityStatus;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityType;
import it.unibo.jetpackjoyride.core.entities.entity.impl.EntityModelGeneratorImpl;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;
import it.unibo.jetpackjoyride.core.entities.obstacle.impl.Missile;
import it.unibo.jetpackjoyride.core.entities.pickups.impl.VehiclePickUp;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp.PowerUpType;
import it.unibo.jetpackjoyride.core.entities.powerup.impl.LilStomper;
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
    public void testingAllEntitites() {
        Movement entityMovement = new MovementImpl(ZEROPAIR, ZEROPAIR, ZEROPAIR, ZEROPAIR, List.of());
        Hitbox entityHitbox = new HitboxImpl(FIVEHUNDREDPAIR, ONEHUNDREDPAIR);
        Entity obstacle = new Missile(entityMovement, entityHitbox);

        entityMovement = new MovementImpl(ZEROPAIR, ZEROPAIR, ZEROPAIR, ZEROPAIR, List.of());
        entityHitbox = new HitboxImpl(FIVEHUNDREDPAIR, ONEHUNDREDPAIR);
        Entity powerUp = new LilStomper(entityMovement, entityHitbox);

        entityMovement = new MovementImpl(ZEROPAIR, ZEROPAIR, ZEROPAIR, ZEROPAIR, List.of());
        entityHitbox = new HitboxImpl(FIVEHUNDREDPAIR, ONEHUNDREDPAIR);
        Entity pickUp = new VehiclePickUp(entityMovement, entityHitbox);

        this.entityTesting(obstacle);
        this.entityTesting(powerUp);
        this.entityTesting(pickUp);
    }


    public void entityTesting(Entity entity) { 
        entity.setEntityStatus(EntityStatus.CHARGING);
        assertNotNull(entity.getEntityType());
        assertNotNull(entity.getEntityStatus());

        assertTrue(entity.getLifetime().equals(0));
        assertTrue(entity.getHitbox().getHitboxDimensions().equals(ONEHUNDREDPAIR));

        assertTrue(entity.getHitbox().getHitboxPosition().equals(FIVEHUNDREDPAIR));

        assertEquals(Set.of(new Pair<>(550.0, 450.0),
                            new Pair<>(450.0, 450.0),
                            new Pair<>(550.0, 550.0),
                            new Pair<>(450.0, 550.0)), entity.getHitbox().getHitboxVertex());
        
        assertEquals(entity.getEntityMovement().getCurrentPosition(), ZEROPAIR);
        assertEquals(entity.getEntityMovement().getAcceleration(), ZEROPAIR);
        assertEquals(entity.getEntityMovement().getSpeed(), ZEROPAIR);
        assertEquals(entity.getEntityMovement().getRotation(), ZEROPAIR);
        assertEquals(entity.getEntityMovement().getMovementChangers(), List.of());

        entity.getEntityMovement().setCurrentPosition(FIVEHUNDREDPAIR);
        assertEquals(entity.getEntityMovement().getCurrentPosition(), FIVEHUNDREDPAIR);
        entity.getEntityMovement().setSpeed(new Pair<>(1.0, 2.0));
        assertEquals(entity.getEntityMovement().getSpeed(), new Pair<>(1.0, 2.0));

        entity.update(false);
        assertTrue(entity.getLifetime().equals(1));
        assertEquals(entity.getEntityMovement().getCurrentPosition(), 
                     new Pair<>(FIVEHUNDREDPAIR.get1()+1.0, FIVEHUNDREDPAIR.get2()+2.0));

        //The entity has its status set to CHARGING so the hitbox is not update (it doesn't make sense to update
        //the hitbox and so do useless calculations if the entity is not ACTIVE)

        assertNotEquals(entity.getEntityMovement().getCurrentPosition(),entity.getHitbox().getHitboxPosition());

        entity.setEntityStatus(EntityStatus.ACTIVE);

        //Now the entity is ACTIVE and the hitbox is calculated
        assertEquals(entity.getEntityMovement().getCurrentPosition(),entity.getHitbox().getHitboxPosition());

        entity.getEntityMovement().setCurrentPosition(ZEROPAIR);
        entity.getEntityMovement().setSpeed(new Pair<>(10.0, 10.0));
        for(int i=0; i<50; i++) {
            entity.update(false);
        }
        assertTrue(entity.getLifetime().equals(51));
        assertEquals(entity.getEntityMovement().getCurrentPosition(),new Pair<>(500.0, 500.0));

    }

    @org.junit.Test
    public void entityGenerator() { 
        Movement entityMovement = new MovementImpl(ZEROPAIR, ZEROPAIR, ZEROPAIR, ZEROPAIR, List.of());
        Obstacle zapper = this.entityFactory.generateObstacle(ObstacleType.ZAPPER, entityMovement);
        
        zapper.setEntityStatus(EntityStatus.CHARGING);
        assertEquals(zapper.getEntityType(), EntityType.OBSTACLE);
        assertEquals(zapper.getObstacleType(), ObstacleType.ZAPPER);

        assertTrue(zapper.getLifetime().equals(0));
        assertEquals(zapper.getHitbox().getHitboxDimensions(),new Pair<>(160.0, 30.0));

        assertTrue(zapper.getHitbox().getHitboxPosition().equals(ZEROPAIR));

        assertEquals(Set.of(new Pair<>(-80.0, -15.0),
                            new Pair<>(80.0, -15.0),
                            new Pair<>(-80.0, 15.0),
                            new Pair<>(80.0, 15.0)), zapper.getHitbox().getHitboxVertex());
        


        zapper.getEntityMovement().setCurrentPosition(FIVEHUNDREDPAIR);
        assertEquals(zapper.getEntityMovement().getCurrentPosition(), FIVEHUNDREDPAIR);
        zapper.getEntityMovement().setSpeed(new Pair<>(1.0, 2.0));
        assertEquals(zapper.getEntityMovement().getSpeed(), new Pair<>(1.0, 2.0));

        zapper.update(false);
        assertTrue(zapper.getLifetime().equals(1));
        assertEquals(zapper.getEntityMovement().getCurrentPosition(), 
                     new Pair<>(FIVEHUNDREDPAIR.get1()+1.0, FIVEHUNDREDPAIR.get2()+2.0));

        //The entity has its status set to CHARGING so the hitbox is not update (it doesn't make sense to update
        //the hitbox and so do useless calculations if the entity is not ACTIVE)

        assertNotEquals(zapper.getEntityMovement().getCurrentPosition(),zapper.getHitbox().getHitboxPosition());

        zapper.setEntityStatus(EntityStatus.ACTIVE);

        //Now the entity is ACTIVE and the hitbox is calculated
        assertEquals(zapper.getEntityMovement().getCurrentPosition(),zapper.getHitbox().getHitboxPosition());

        zapper.getEntityMovement().setCurrentPosition(ZEROPAIR);
        zapper.getEntityMovement().setSpeed(new Pair<>(10.0, 10.0));
        for(int i=0; i<50; i++) {
            zapper.update(false);
        }
        assertTrue(zapper.getLifetime().equals(51));
        assertEquals(zapper.getEntityMovement().getCurrentPosition(),new Pair<>(500.0, 500.0));

    }

    @org.junit.Test
    public void collisionChecking() { 
        Movement missileMovement = new MovementImpl(new Pair<>(300.0, 500.0), new Pair<>(-10.0,0.0), ZEROPAIR, ZEROPAIR, List.of());
        Obstacle missile = this.entityFactory.generateObstacle(ObstacleType.MISSILE, missileMovement);
        missile.setEntityStatus(EntityStatus.ACTIVE);

        PowerUp powerUp = this.entityFactory.generatePowerUp(PowerUpType.PROFITBIRD).get(0);
        powerUp.getEntityMovement().setCurrentPosition(new Pair<>(100.0, 500.0));
        powerUp.setEntityStatus(EntityStatus.ACTIVE);
        boolean hasTouched = false;
        
        for(int i=0; i<50; i++) {
            missile.update(false);
            if(missile.getHitbox().isTouching(powerUp.getHitbox())) {
                hasTouched = true;
            }
        }
        assertTrue(hasTouched);

        
    }

}


