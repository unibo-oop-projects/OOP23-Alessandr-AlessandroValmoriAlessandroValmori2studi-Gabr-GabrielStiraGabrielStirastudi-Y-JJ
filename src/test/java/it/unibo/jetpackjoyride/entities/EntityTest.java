package it.unibo.jetpackjoyride.entities; 

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import java.util.Set;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityStatus;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityType;
import it.unibo.jetpackjoyride.core.entities.entity.impl.EntityModelGeneratorImpl;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.AbstractObstacle;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;
import it.unibo.jetpackjoyride.core.entities.pickups.api.AbstractPickUp;
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp;
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp.PickUpType;
import it.unibo.jetpackjoyride.core.entities.powerup.api.AbstractPowerUp;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp.PowerUpType;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.hitbox.impl.HitboxImpl;
import it.unibo.jetpackjoyride.core.movement.Movement;
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
        final Movement entityMovement = new Movement.Builder().setPosition(FIVEHUNDREDPAIR).build();
        final Hitbox entityHitbox = new HitboxImpl(FIVEHUNDREDPAIR, ONEHUNDREDPAIR,0.0);
        final Entity obstacle = new AbstractObstacle(ObstacleType.MISSILE, entityMovement, entityHitbox) {
            @Override
            protected void updateStatus(final boolean isSpaceBarPressed) {
            }
        };

        final Entity powerup = new AbstractPowerUp(PowerUpType.LILSTOMPER, entityMovement, entityHitbox) {
            @Override
            protected void updateStatus(final boolean isSpaceBarPressed) {
            }
        };

        final Entity pickup = new AbstractPickUp(PickUpType.VEHICLE, entityMovement, entityHitbox) {
            @Override
            protected void updateStatus(final boolean isSpaceBarPressed) {
            }
        };

        this.entityTesting(obstacle);
        this.entityTesting(powerup);
        this.entityTesting(pickup);
    }

    public void entityTesting(final Entity entity) { 

        entity.setEntityStatus(EntityStatus.CHARGING);
        assertNotNull(entity.getEntityType());
        assertNotNull(entity.getEntityStatus());

        assertEquals(Integer.valueOf(0), entity.getLifetime());
        assertEquals(ONEHUNDREDPAIR, entity.getHitbox().getHitboxDimensions());

        assertEquals(FIVEHUNDREDPAIR, entity.getHitbox().getHitboxPosition());

        assertEquals(Set.of(new Pair<>(550.0, 450.0),
                            new Pair<>(450.0, 450.0),
                            new Pair<>(550.0, 550.0),
                            new Pair<>(450.0, 550.0)), entity.getHitbox().getHitboxVertex());
        
        //Setting a movement to the entity which is all 0.0's and empty list of changers
        entity.setEntityMovement(new Movement.Builder().build());
        assertEquals(entity.getEntityMovement().getAcceleration(), ZEROPAIR);
        assertEquals(entity.getEntityMovement().getSpeed(), ZEROPAIR);
        assertEquals(entity.getEntityMovement().getRotation(), ZEROPAIR);
        assertEquals(entity.getEntityMovement().getMovementChangers(), List.of());

        entity.setEntityMovement(new Movement.Builder().setPosition(FIVEHUNDREDPAIR).build());
        assertEquals(entity.getEntityMovement().getPosition(), FIVEHUNDREDPAIR);
        
        entity.setEntityMovement(new Movement.Builder().setPosition(FIVEHUNDREDPAIR).setSpeed(1.0,2.0).build());
        assertEquals(entity.getEntityMovement().getSpeed(), new Pair<>(1.0, 2.0));

        entity.update(false);
        assertEquals(Integer.valueOf(1), entity.getLifetime());
        assertEquals(entity.getEntityMovement().getPosition(), 
                     new Pair<>(FIVEHUNDREDPAIR.get1()+1.0, FIVEHUNDREDPAIR.get2()+2.0));

        //The entity has its status set to CHARGING so the hitbox is not updated (it doesn't make sense to update
        //the hitbox and so do useless calculations if the entity is not ACTIVE)

        assertNotEquals(entity.getEntityMovement().getPosition(),entity.getHitbox().getHitboxPosition());

        entity.setEntityStatus(EntityStatus.ACTIVE);

        //Now the entity is ACTIVE and the hitbox is calculated
        assertEquals(entity.getEntityMovement().getPosition(),entity.getHitbox().getHitboxPosition());

        entity.setEntityMovement(new Movement.Builder().setPosition(ZEROPAIR).setSpeed(new Pair<>(10.0, 10.0)).build());
        for(int i=0; i<50; i++) {
            entity.update(false);
        }
        assertEquals(Integer.valueOf(51), entity.getLifetime());
        assertEquals(entity.getEntityMovement().getPosition(),new Pair<>(500.0, 500.0));

    }

    @org.junit.Test
    public void entityGenerator() { 

        final Movement entityMovement = new Movement.Builder()
                            .setPosition(ZEROPAIR)
                            .setSpeed(ZEROPAIR)
                            .setAcceleration(ZEROPAIR)
                            .build();
        //Movement entityMovement = new Movement(ZEROPAIR, ZEROPAIR, ZEROPAIR, ZEROPAIR, List.of());
        Obstacle zapper = this.entityFactory.generateObstacle(ObstacleType.ZAPPER, entityMovement);
        
        zapper.setEntityStatus(EntityStatus.CHARGING);
        assertEquals(zapper.getEntityType(), EntityType.OBSTACLE);
        assertEquals(zapper.getObstacleType(), ObstacleType.ZAPPER);

        assertEquals(Integer.valueOf(0), zapper.getLifetime());
        assertEquals(zapper.getHitbox().getHitboxDimensions(),new Pair<>(160.0, 30.0));

        assertEquals(ZEROPAIR, zapper.getHitbox().getHitboxPosition());

        assertEquals(Set.of(new Pair<>(-80.0, -15.0),
                            new Pair<>(80.0, -15.0),
                            new Pair<>(-80.0, 15.0),
                            new Pair<>(80.0, 15.0)), zapper.getHitbox().getHitboxVertex());
        
        
        /* Even if a zapper is spawned with a selected Xposition, it will be ignored 
        (other characteristics such as speed, acceleration, rotation, etc.. will not be ignored) 
        This is to prevent zappers from spawning "inside the screen" when they normally should spawn
        out of the screen bounds. The same applies for example to Missiles.*/
        zapper = this.entityFactory.generateObstacle(ObstacleType.ZAPPER, new Movement.Builder().setPosition(FIVEHUNDREDPAIR).build());
        assertNotEquals(FIVEHUNDREDPAIR, zapper.getEntityMovement().getPosition());
        /* This does not prevent from deciding the Yposition coordinate though*/
        assertEquals(Double.valueOf(500.0), zapper.getEntityMovement().getPosition().get2());

        /* Also, since this rule is applied only when using the generator (which is basically the only way
         missiles are generated in this software) and creating a new Zapper, we can change the position and "bypass"
         the rule by using the setMovement method and providing the wanted position*/

        zapper.setEntityMovement(new Movement.Builder().setPosition(FIVEHUNDREDPAIR).build());

        assertEquals(FIVEHUNDREDPAIR, zapper.getEntityMovement().getPosition());

        zapper = this.entityFactory.generateObstacle(ObstacleType.ZAPPER, new Movement.Builder().setSpeed(new Pair<>(1.0, 2.0)).build());

        /* Zapper have an initial speed set to -GameInfo.MOVESPEED, so the same story repeats...*/
        assertNotEquals(new Pair<>(1.0, 2.0), zapper.getEntityMovement().getSpeed());

        zapper.setEntityMovement(new Movement.Builder().setSpeed(1.0, 2.0).build());

        assertEquals(new Pair<>(1.0, 2.0), zapper.getEntityMovement().getSpeed());

        /* Obviously, you can reuse the characteristics of the old movement to generate a new movement */
        zapper.setEntityMovement(new Movement.Builder().setPosition(FIVEHUNDREDPAIR).setSpeed(zapper.getEntityMovement().getSpeed()).build());

        zapper.setEntityStatus(EntityStatus.CHARGING);

        zapper.update(false);
        assertEquals(Integer.valueOf(1), zapper.getLifetime());
        assertEquals(zapper.getEntityMovement().getPosition(), 
                     new Pair<>(FIVEHUNDREDPAIR.get1()+1.0, FIVEHUNDREDPAIR.get2()+2.0));

        //The entity has its status set to CHARGING so the hitbox is not update (it doesn't make sense to update
        //the hitbox and so do useless calculations if the entity is not ACTIVE)

        assertNotEquals(zapper.getEntityMovement().getPosition(),zapper.getHitbox().getHitboxPosition());

        zapper.setEntityStatus(EntityStatus.ACTIVE);

        //Now the entity is ACTIVE and the hitbox is calculated
        assertEquals(zapper.getEntityMovement().getPosition(),zapper.getHitbox().getHitboxPosition());

        zapper = this.entityFactory.generateObstacle(ObstacleType.ZAPPER, new Movement.Builder().build());

        zapper.setEntityMovement(new Movement.Builder().setPosition(ZEROPAIR).setSpeed(10.0, 10.0).build());
        
        for(int i=0; i<50; i++) {
            zapper.update(false);
        }
        assertEquals(Integer.valueOf(50), zapper.getLifetime());
        assertEquals(new Pair<>(500.0, 500.0),zapper.getEntityMovement().getPosition());

    }

    @org.junit.Test
    public void collisionChecking() { 
        final Movement missileMovement = new Movement.Builder()
                                .setPosition(new Pair<>(300.0, 500.0))
                                .setSpeed(new Pair<>(-10.0,0.0))
                                .build();

        final Obstacle missile = this.entityFactory.generateObstacle(ObstacleType.MISSILE, missileMovement);

        final PowerUp powerUp = this.entityFactory.generatePowerUp(PowerUpType.PROFITBIRD).get(0);

        powerUp.setEntityMovement(new Movement.Builder().setPosition(new Pair<>(100.0, 500.0)).build());
        powerUp.setEntityStatus(EntityStatus.ACTIVE);
        boolean hasTouched = false;
        
        for(int i=0; i<350; i++) {
            missile.update(false);
            if(missile.getHitbox().isTouching(powerUp.getHitbox())) {
                hasTouched = true;
            }
        }
        assertTrue(hasTouched);

        final PickUp vehiclePickUp = this.entityFactory.generatePickUp(PickUpType.VEHICLE);
        vehiclePickUp.setEntityStatus(EntityStatus.ACTIVE);
        vehiclePickUp.setEntityMovement(new Movement.Builder().setPosition(500.0,650.0).setSpeed(-10.0,0.0).build());

        final PowerUp powerUp2 = this.entityFactory.generatePowerUp(PowerUpType.LILSTOMPER).get(0);
        powerUp2.setEntityMovement(new Movement.Builder().setPosition(100.0,600.0).build());
        powerUp2.setEntityStatus(EntityStatus.ACTIVE);
        hasTouched = false;
        
        for(int i=0; i<50; i++) {
            powerUp2.update(false);
            vehiclePickUp.update(false);
            if(powerUp2.getHitbox().isTouching(vehiclePickUp.getHitbox())) {
                hasTouched = true;
            }
        }
        assertTrue(hasTouched);
    }
}
