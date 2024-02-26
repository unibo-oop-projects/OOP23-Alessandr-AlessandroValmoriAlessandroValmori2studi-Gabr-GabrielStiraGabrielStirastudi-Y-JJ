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

/**
 * Tests all entities, entity generator, collision detection, entity movement
 * and others.
 */
public final class EntityTest {
    private static final Pair<Double, Double> ZEROPAIR = new Pair<>(0.0, 0.0);
    private static final int TEN = 10;
    private static final int FIFTY = 50;
    private static final Double ONEHUNDRED = 100.0;
    private static final Double THREEHUNDRED = 300.0;
    private static final Double FIVEHUNDRED = 500.0;
    private static final Pair<Double, Double> ZAPPERDIMENSIONS = new Pair<>(160.0, 30.0);
    /**
     * The factory which produces all entities.
     */
    private EntityModelGeneratorImpl entityFactory;

    /**
     * Initializes the entity generator.
     */
    @org.junit.Before
    public void init() {
        this.entityFactory = new EntityModelGeneratorImpl();
    }

    /**
     * Tests all types of entities.
     */
    @org.junit.Test
    public void testingAllEntitites() {
        final Movement entityMovement = new Movement.Builder().setPosition(new Pair<>(FIVEHUNDRED, FIVEHUNDRED))
                .build();
        final Hitbox entityHitbox = new HitboxImpl(new Pair<>(FIVEHUNDRED, FIVEHUNDRED),
                new Pair<>(ONEHUNDRED, ONEHUNDRED), 0.0);
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

    /**
     * Used to test each type of entity.
     * 
     * @param entity
     */
    public void entityTesting(final Entity entity) {

        entity.setEntityStatus(EntityStatus.CHARGING);
        assertNotNull(entity.getEntityType());
        assertNotNull(entity.getEntityStatus());

        assertEquals(Integer.valueOf(0), entity.getLifetime());
        assertEquals(new Pair<>(ONEHUNDRED, ONEHUNDRED), entity.getHitbox().getHitboxDimensions());

        assertEquals(new Pair<>(FIVEHUNDRED, FIVEHUNDRED), entity.getHitbox().getHitboxPosition());

        assertEquals(Set.of(
                new Pair<>(FIVEHUNDRED + Double.valueOf(FIFTY),
                        FIVEHUNDRED - Double.valueOf(FIFTY)),
                new Pair<>(FIVEHUNDRED - Double.valueOf(FIFTY),
                        FIVEHUNDRED - Double.valueOf(FIFTY)),
                new Pair<>(FIVEHUNDRED + Double.valueOf(FIFTY),
                        FIVEHUNDRED + Double.valueOf(FIFTY)),
                new Pair<>(FIVEHUNDRED - Double.valueOf(FIFTY),
                        FIVEHUNDRED + Double.valueOf(FIFTY))),
                entity.getHitbox().getHitboxVertex());

        // Setting a movement to the entity which is all 0.0's and empty list of
        // changers
        entity.setEntityMovement(new Movement.Builder().build());
        assertEquals(entity.getEntityMovement().getAcceleration(), ZEROPAIR);
        assertEquals(entity.getEntityMovement().getSpeed(), ZEROPAIR);
        assertEquals(entity.getEntityMovement().getRotation(), ZEROPAIR);
        assertEquals(entity.getEntityMovement().getMovementChangers(), List.of());

        entity.setEntityMovement(new Movement.Builder().setPosition(new Pair<>(FIVEHUNDRED, FIVEHUNDRED)).build());
        assertEquals(entity.getEntityMovement().getPosition(), FIVEHUNDRED);

        entity.setEntityMovement(
                new Movement.Builder().setPosition(new Pair<>(FIVEHUNDRED, FIVEHUNDRED)).setSpeed(1.0, 2.0).build());
        assertEquals(entity.getEntityMovement().getSpeed(), new Pair<>(1.0, 2.0));

        entity.update(false);
        assertEquals(Integer.valueOf(1), entity.getLifetime());
        assertEquals(entity.getEntityMovement().getPosition(),
                new Pair<>(FIVEHUNDRED + 1.0, FIVEHUNDRED + 2.0));

        // The entity has its status set to CHARGING so the hitbox is not updated (it
        // doesn't make sense to update
        // the hitbox and so do useless calculations if the entity is not ACTIVE)

        assertNotEquals(entity.getEntityMovement().getPosition(), entity.getHitbox().getHitboxPosition());

        entity.setEntityStatus(EntityStatus.ACTIVE);

        // Now the entity is ACTIVE and the hitbox is calculated
        assertEquals(entity.getEntityMovement().getPosition(), entity.getHitbox().getHitboxPosition());

        entity.setEntityMovement(new Movement.Builder().setPosition(ZEROPAIR).setSpeed(new Pair<>(10.0, 10.0)).build());
        for (int i = 0; i < FIFTY; i++) {
            entity.update(false);
        }
        assertEquals(Integer.valueOf(FIFTY), entity.getLifetime());
        assertEquals(entity.getEntityMovement().getPosition(), new Pair<>(FIVEHUNDRED, FIVEHUNDRED));

    }

    /**
     * Tests the generation of entities via EntityGenerator.
     */
    @org.junit.Test
    public void entityGenerator() {

        final Movement entityMovement = new Movement.Builder()
                .setPosition(ZEROPAIR)
                .setSpeed(ZEROPAIR)
                .setAcceleration(ZEROPAIR)
                .build();
        // Movement entityMovement = new Movement(ZEROPAIR, ZEROPAIR, ZEROPAIR,
        // ZEROPAIR, List.of());
        Obstacle zapper = this.entityFactory.generateObstacle(ObstacleType.ZAPPER, entityMovement);

        zapper.setEntityStatus(EntityStatus.CHARGING);
        assertEquals(zapper.getEntityType(), EntityType.OBSTACLE);
        assertEquals(zapper.getObstacleType(), ObstacleType.ZAPPER);

        assertEquals(Integer.valueOf(0), zapper.getLifetime());
        assertEquals(zapper.getHitbox().getHitboxDimensions(), ZAPPERDIMENSIONS);

        assertEquals(ZEROPAIR, zapper.getHitbox().getHitboxPosition());

        assertEquals(Set.of(new Pair<>(-ZAPPERDIMENSIONS.get1() / 2, -ZAPPERDIMENSIONS.get2() / 2),
                new Pair<>(ZAPPERDIMENSIONS.get1() / 2, -ZAPPERDIMENSIONS.get2() / 2),
                new Pair<>(-ZAPPERDIMENSIONS.get1() / 2, ZAPPERDIMENSIONS.get2() / 2),
                new Pair<>(ZAPPERDIMENSIONS.get1() / 2, ZAPPERDIMENSIONS.get2() / 2)),
                zapper.getHitbox().getHitboxVertex());

        /*
         * Even if a zapper is spawned with a selected Xposition, it will be ignored
         * (other characteristics such as speed, acceleration, rotation, etc.. will not
         * be ignored)
         * This is to prevent zappers from spawning "inside the screen" when they
         * normally should spawn
         * out of the screen bounds. The same applies for example to Missiles.
         */
        zapper = this.entityFactory.generateObstacle(ObstacleType.ZAPPER,
                new Movement.Builder().setPosition(new Pair<>(FIVEHUNDRED, FIVEHUNDRED)).build());
        assertNotEquals(new Pair<>(FIVEHUNDRED, FIVEHUNDRED), zapper.getEntityMovement().getPosition());
        /* This does not prevent from deciding the Yposition coordinate though */
        assertEquals(FIVEHUNDRED, zapper.getEntityMovement().getPosition().get2());

        /*
         * Also, since this rule is applied only when using the generator (which is
         * basically the only way
         * missiles are generated in this software) and creating a new Zapper, we can
         * change the position and "bypass"
         * the rule by using the setMovement method and providing the wanted position
         */

        zapper.setEntityMovement(new Movement.Builder().setPosition(new Pair<>(FIVEHUNDRED, FIVEHUNDRED)).build());

        assertEquals(new Pair<>(FIVEHUNDRED, FIVEHUNDRED), zapper.getEntityMovement().getPosition());

        zapper = this.entityFactory.generateObstacle(ObstacleType.ZAPPER,
                new Movement.Builder().setSpeed(new Pair<>(1.0, 2.0)).build());

        /*
         * Zapper have an initial speed set to -GameInfo.MOVESPEED, so the same story
         * repeats...
         */
        assertNotEquals(new Pair<>(1.0, 2.0), zapper.getEntityMovement().getSpeed());

        zapper.setEntityMovement(new Movement.Builder().setSpeed(1.0, 2.0).build());

        assertEquals(new Pair<>(1.0, 2.0), zapper.getEntityMovement().getSpeed());

        /*
         * Obviously, you can reuse the characteristics of the old movement to generate
         * a new movement
         */
        zapper.setEntityMovement(new Movement.Builder().setPosition(new Pair<>(FIVEHUNDRED, FIVEHUNDRED))
                .setSpeed(zapper.getEntityMovement().getSpeed()).build());

        zapper.setEntityStatus(EntityStatus.CHARGING);

        zapper.update(false);
        assertEquals(Integer.valueOf(1), zapper.getLifetime());
        assertEquals(zapper.getEntityMovement().getPosition(),
                new Pair<>(FIVEHUNDRED + 1.0, FIVEHUNDRED + 2.0));

        // The entity has its status set to CHARGING so the hitbox is not update (it
        // doesn't make sense to update
        // the hitbox and so do useless calculations if the entity is not ACTIVE)

        assertNotEquals(zapper.getEntityMovement().getPosition(), zapper.getHitbox().getHitboxPosition());

        zapper.setEntityStatus(EntityStatus.ACTIVE);

        // Now the entity is ACTIVE and the hitbox is calculated
        assertEquals(zapper.getEntityMovement().getPosition(), zapper.getHitbox().getHitboxPosition());

        zapper = this.entityFactory.generateObstacle(ObstacleType.ZAPPER, new Movement.Builder().build());

        zapper.setEntityMovement(new Movement.Builder().setPosition(ZEROPAIR).setSpeed(10.0, 10.0).build());

        for (int i = 0; i < FIFTY; i++) {
            zapper.update(false);
        }
        assertEquals(Integer.valueOf(FIFTY), zapper.getLifetime());
        assertEquals(new Pair<>(FIVEHUNDRED, FIVEHUNDRED), zapper.getEntityMovement().getPosition());

    }

    /**
     * Tests the collision detection of entities.
     */
    @org.junit.Test
    public void collisionChecking() {
        final Movement missileMovement = new Movement.Builder()
                .setPosition(new Pair<>(THREEHUNDRED, FIVEHUNDRED))
                .setSpeed(new Pair<>(-Double.valueOf(TEN), 0.0))
                .build();

        final Obstacle missile = this.entityFactory.generateObstacle(ObstacleType.MISSILE, missileMovement);

        final PowerUp powerUp = this.entityFactory.generatePowerUp(PowerUpType.PROFITBIRD).get(0);

        powerUp.setEntityMovement(
                new Movement.Builder().setPosition(new Pair<>(ONEHUNDRED, FIVEHUNDRED)).build());
        powerUp.setEntityStatus(EntityStatus.ACTIVE);
        boolean hasTouched = false;

        for (int i = 0; i < THREEHUNDRED; i++) {
            missile.update(false);
            if (missile.getHitbox().isTouching(powerUp.getHitbox())) {
                hasTouched = true;
            }
        }
        assertTrue(hasTouched);

        final PickUp vehiclePickUp = this.entityFactory.generatePickUp(PickUpType.VEHICLE);
        vehiclePickUp.setEntityStatus(EntityStatus.ACTIVE);
        vehiclePickUp.setEntityMovement(
                new Movement.Builder().setPosition(FIVEHUNDRED, FIVEHUNDRED + ONEHUNDRED + Double.valueOf(FIFTY))
                        .setSpeed(-Double.valueOf(TEN), 0.0).build());

        final PowerUp powerUp2 = this.entityFactory.generatePowerUp(PowerUpType.LILSTOMPER).get(0);
        powerUp2.setEntityMovement(new Movement.Builder().setPosition(ONEHUNDRED, FIVEHUNDRED + ONEHUNDRED).build());
        powerUp2.setEntityStatus(EntityStatus.ACTIVE);
        hasTouched = false;

        for (int i = 0; i < FIFTY; i++) {
            powerUp2.update(false);
            vehiclePickUp.update(false);
            if (powerUp2.getHitbox().isTouching(vehiclePickUp.getHitbox())) {
                hasTouched = true;
            }
        }
        assertTrue(hasTouched);
    }
}
