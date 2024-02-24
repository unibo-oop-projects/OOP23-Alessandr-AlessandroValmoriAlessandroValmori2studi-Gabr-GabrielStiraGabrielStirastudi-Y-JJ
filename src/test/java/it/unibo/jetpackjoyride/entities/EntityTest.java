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
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp;
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp.PickUpType;
import it.unibo.jetpackjoyride.core.entities.pickups.impl.ShieldPickUp;
import it.unibo.jetpackjoyride.core.entities.pickups.impl.VehiclePickUp;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp.PowerUpType;
import it.unibo.jetpackjoyride.core.entities.powerup.impl.LilStomper;
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
        Movement entityMovement = new Movement.Builder().build();
        Hitbox entityHitbox = new HitboxImpl(FIVEHUNDREDPAIR, ONEHUNDREDPAIR,0.0);
        Entity obstacle = new Missile(entityMovement, entityHitbox);

        entityMovement = new Movement.Builder().build();
        entityHitbox = new HitboxImpl(FIVEHUNDREDPAIR, ONEHUNDREDPAIR,0.0);
        Entity powerUp = new LilStomper(entityMovement, entityHitbox);

        entityMovement = new Movement.Builder().build();
        entityHitbox = new HitboxImpl(FIVEHUNDREDPAIR, ONEHUNDREDPAIR,0.0);
        Entity pickUp = new ShieldPickUp(entityMovement, entityHitbox);

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
        assertTrue(entity.getLifetime().equals(1));
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
        assertTrue(entity.getLifetime().equals(51));
        assertEquals(entity.getEntityMovement().getPosition(),new Pair<>(500.0, 500.0));

    }

    @org.junit.Test
    public void entityGenerator() { 

        Movement entityMovement = new Movement.Builder()
                            .setPosition(ZEROPAIR)
                            .setSpeed(ZEROPAIR)
                            .setAcceleration(ZEROPAIR)
                            .build();
        //Movement entityMovement = new Movement(ZEROPAIR, ZEROPAIR, ZEROPAIR, ZEROPAIR, List.of());
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
        
        
        /* Even if a zapper is spawned with a selected Xposition, it will be ignored 
        (other characteristics such as speed, acceleration, rotation, etc.. will not be ignored) 
        This is to prevent zappers from spawning "inside the screen" when they normally should spawn
        out of the screen bounds. The same applies for example to Missiles.*/
        zapper = this.entityFactory.generateObstacle(ObstacleType.ZAPPER, new Movement.Builder().setPosition(FIVEHUNDREDPAIR).build());
        assertNotEquals(FIVEHUNDREDPAIR, zapper.getEntityMovement().getPosition());
        /* This does not prevent from deciding the Yposition coordinate though*/
        assertTrue(zapper.getEntityMovement().getPosition().get2().equals(500.0));

        /* Also, since this rule is applied only when using the generator (which is basically the only way
         missiles are generated in this software) and creating a new Zapper, we can change the position and "bypass"
         the rule by using the setMovement method and providing the wanted position*/

        zapper.setEntityMovement(new Movement.Builder().setPosition(FIVEHUNDREDPAIR).build());

        assertEquals(FIVEHUNDREDPAIR, zapper.getEntityMovement().getPosition());

        zapper = this.entityFactory.generateObstacle(ObstacleType.ZAPPER, new Movement.Builder().setSpeed(new Pair<>(1.0, 2.0)).build());

        assertEquals(zapper.getEntityMovement().getSpeed(), new Pair<>(1.0, 2.0));

        /* Obviously, you can reuse the characteristics of the old movement to generate a new movement */
        zapper.setEntityMovement(new Movement.Builder().setPosition(FIVEHUNDREDPAIR).setSpeed(zapper.getEntityMovement().getSpeed()).build());

        zapper.setEntityStatus(EntityStatus.CHARGING);

        zapper.update(false);
        assertTrue(zapper.getLifetime().equals(1));
        assertEquals(zapper.getEntityMovement().getPosition(), 
                     new Pair<>(FIVEHUNDREDPAIR.get1()+1.0, FIVEHUNDREDPAIR.get2()+2.0));

        //The entity has its status set to CHARGING so the hitbox is not update (it doesn't make sense to update
        //the hitbox and so do useless calculations if the entity is not ACTIVE)

        assertNotEquals(zapper.getEntityMovement().getPosition(),zapper.getHitbox().getHitboxPosition());

        zapper.setEntityStatus(EntityStatus.ACTIVE);

        //Now the entity is ACTIVE and the hitbox is calculated
        assertEquals(zapper.getEntityMovement().getPosition(),zapper.getHitbox().getHitboxPosition());

        zapper = this.entityFactory.generateObstacle(ObstacleType.ZAPPER, new Movement.Builder().setSpeed(new Pair<>(10.0, 10.0)).build());
        zapper.setEntityMovement(new Movement.Builder().setPosition(ZEROPAIR).setSpeed(zapper.getEntityMovement().getSpeed()).build());
        
        for(int i=0; i<50; i++) {
            zapper.update(false);
        }
        assertTrue(zapper.getLifetime().equals(50));
        assertEquals(zapper.getEntityMovement().getPosition(),new Pair<>(500.0, 500.0));

    }

    @org.junit.Test
    public void collisionChecking() { 
        

        Movement missileMovement = new Movement.Builder()
                                .setPosition(new Pair<>(300.0, 500.0))
                                .setSpeed(new Pair<>(-10.0,0.0))
                                .build();

        Obstacle missile = this.entityFactory.generateObstacle(ObstacleType.MISSILE, missileMovement);

        PowerUp powerUp = this.entityFactory.generatePowerUp(PowerUpType.PROFITBIRD).get(0);

        powerUp.setEntityMovement(new Movement.Builder().setPosition(new Pair<>(100.0, 500.0)).build());
        powerUp.setEntityStatus(EntityStatus.ACTIVE);
        boolean hasTouched = false;
        
        for(int i=0; i<350; i++) {
            missile.update(false);
            System.out.println("pos:" + missile.getEntityMovement().getPosition());
            if(missile.getHitbox().isTouching(powerUp.getHitbox())) {
                hasTouched = true;
            }
        }
        assertTrue(hasTouched);

        PickUp vehiclePickUp = this.entityFactory.generatePickUp(PickUpType.VEHICLE);
        vehiclePickUp.setEntityStatus(EntityStatus.ACTIVE);
        vehiclePickUp.setEntityMovement(new Movement.Builder().setPosition(500.0,650.0).setSpeed(-10.0,0.0).build());

        PowerUp powerUp2 = this.entityFactory.generatePowerUp(PowerUpType.LILSTOMPER).get(0);
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
