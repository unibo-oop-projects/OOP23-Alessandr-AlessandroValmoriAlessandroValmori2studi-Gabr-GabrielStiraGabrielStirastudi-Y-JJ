package it.unibo.jetpackjoyride.player;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import it.unibo.jetpackjoyride.core.entities.barry.api.Barry.PerformingAction;
import it.unibo.jetpackjoyride.core.entities.barry.api.Barry;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityStatus;
import it.unibo.jetpackjoyride.core.entities.entity.impl.EntityModelGeneratorImpl;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.utilities.Pair;
import org.junit.jupiter.api.Test;


public class PlayerTest {

    private EntityModelGeneratorImpl entityFactory;
    private Barry barry;
    private static final Pair<Double, Double> BARRY_STARTING_POS = new Pair<>(200.0, 630.0);
    private static final double OBS_SPEED = 10.0;
    private static final double HUNDRED = 100.0;
    private static final int LOOP_LIMIT = 500;

    @Test
    public void testPlayerInitialization() {
        this.entityFactory = new EntityModelGeneratorImpl();
        this.barry = this.entityFactory.generateBarry();
        assertTrue(this.barry.isAlive());
        assertEquals(this.barry.getEntityStatus(), EntityStatus.ACTIVE);
        assertEquals(this.barry.getPerformingAction(), PerformingAction.WALKING);
        assertFalse(this.barry.hasShield());
    }

    @Test
    public void testPlayerHit() {
        this.entityFactory = new EntityModelGeneratorImpl();
        this.barry = this.entityFactory.generateBarry();
        this.barry.setShieldOn();
        var status = this.barry.getPerformingAction();
        this.barry.hit(ObstacleType.ZAPPER);
        assertEquals(this.barry.getPerformingAction(), status);
        assertTrue(this.barry.isAlive());
        this.barry.hit(ObstacleType.ZAPPER);
        assertFalse(this.barry.isAlive());
        assertEquals(this.barry.getPerformingAction(), PerformingAction.ZAPPED);
    }

    @Test
    public void testPlayerHit2() {
        this.entityFactory = new EntityModelGeneratorImpl();
        this.barry = this.entityFactory.generateBarry();
        this.barry.hit(ObstacleType.ZAPPER);
        assertFalse(this.barry.isAlive());
        assertEquals(PerformingAction.ZAPPED, this.barry.getPerformingAction());
    }

    @Test
    public void testPlayerMove() {
        this.entityFactory = new EntityModelGeneratorImpl();

        this.barry = this.entityFactory.generateBarry();
        this.barry.update(true);
        assertEquals(PerformingAction.PROPELLING, this.barry.getPerformingAction());
        this.barry.update(true);
        this.barry.update(false);
        assertEquals(PerformingAction.FALLING, this.barry.getPerformingAction());
        for (int i = 0; i < 10; i++) {
            this.barry.update(false);
        }

        assertEquals(PerformingAction.WALKING, this.barry.getPerformingAction());

        for (int i = 0; i < LOOP_LIMIT; i++) {
            this.barry.update(true);
        }
        assertEquals(PerformingAction.HEAD_DRAGGING, this.barry.getPerformingAction());
    }

    @Test
    public void testPlayerObstacleCollision() {
        this.entityFactory = new EntityModelGeneratorImpl();
        this.barry = this.entityFactory.generateBarry();

        boolean hasTouched1 = false;
        boolean hasTouched2 = false;
        boolean hasTouched3 = false;
        boolean hasTouched4 = false;
        boolean hasTouched5 = false;
        boolean hasTouched6 = false;

        var zapper = this.entityFactory.generateObstacle(ObstacleType.ZAPPER, new Movement.Builder().setPosition(0.0, BARRY_STARTING_POS.get2()).setSpeed(-OBS_SPEED,0.0).build());
        var missile = this.entityFactory.generateObstacle(ObstacleType.MISSILE, new Movement.Builder().setPosition(0.0, BARRY_STARTING_POS.get2()).setSpeed(-OBS_SPEED,0.0).build());
        var laser = this.entityFactory.generateObstacle(ObstacleType.LASER, new Movement.Builder().setPosition(0.0, BARRY_STARTING_POS.get2()).build());

        var zapper1 = this.entityFactory.generateObstacle(ObstacleType.ZAPPER, new Movement.Builder().setPosition(0.0, HUNDRED).setSpeed(-OBS_SPEED,0.0).build());
        var missile1 = this.entityFactory.generateObstacle(ObstacleType.MISSILE, new Movement.Builder().setPosition(0.0, HUNDRED).setSpeed(-OBS_SPEED,0.0).build());
        var laser1 = this.entityFactory.generateObstacle(ObstacleType.LASER, new Movement.Builder().setPosition(0.0, HUNDRED).setSpeed(-OBS_SPEED,0.0).build());

        for (int i = 0; i < LOOP_LIMIT; i++) {
            barry.update(false);
            zapper.update(false);
            missile.update(false);
            laser.update(false);
            zapper1.update(false);
            missile1.update(false);
            laser1.update(false);
    
            if (zapper.getHitbox().isTouching(barry.getHitbox())) {
                hasTouched1 = true;
            }
            if (missile.getHitbox().isTouching(barry.getHitbox())) {
                hasTouched2 = true;
            }
            if (laser.getHitbox().isTouching(barry.getHitbox())) {
                hasTouched3 = true;
            }
            if (zapper1.getHitbox().isTouching(barry.getHitbox())) {
                hasTouched4 = true;
            }
            if (missile1.getHitbox().isTouching(barry.getHitbox())) {
                hasTouched5 = true;
            }
            if (laser1.getHitbox().isTouching(barry.getHitbox())) {
                hasTouched6 = true;
            }
        }    
        assertTrue(hasTouched1);
        assertTrue(hasTouched2);
        assertTrue(hasTouched3);
        assertFalse(hasTouched4);
        assertFalse(hasTouched5);
        assertFalse(hasTouched6);
    }


}
