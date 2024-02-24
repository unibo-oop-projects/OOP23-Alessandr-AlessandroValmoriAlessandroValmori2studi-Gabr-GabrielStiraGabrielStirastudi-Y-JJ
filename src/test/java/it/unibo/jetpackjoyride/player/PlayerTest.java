package it.unibo.jetpackjoyride.player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import it.unibo.jetpackjoyride.core.entities.barry.api.Barry.PerformingAction;
import it.unibo.jetpackjoyride.core.entities.barry.api.Barry;
import it.unibo.jetpackjoyride.core.entities.barry.impl.BarryImpl;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityStatus;
import it.unibo.jetpackjoyride.core.entities.entity.impl.EntityModelGeneratorImpl;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.hitbox.impl.HitboxImpl;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.MovementChangers;
import it.unibo.jetpackjoyride.utilities.Pair;
import org.junit.jupiter.api.Test;
import java.util.List;

public class PlayerTest {

    private EntityModelGeneratorImpl entityFactory;

    private Barry barry;
    private final static Pair<Double, Double> ZEROPAIR = new Pair<>(0.0, 0.0);
    private final static Pair<Double, Double> ONEHUNDREDPAIR = new Pair<>(100.0, 100.0);
    private final static Pair<Double, Double> FIVEHUNDREDPAIR = new Pair<>(500.0, 500.0);

    @org.junit.Before
    public void init() {
        this.entityFactory = new EntityModelGeneratorImpl();
    }

    @Test
    public void testPlayerInitialization() {
        Movement barryMovement = new Movement.Builder().setPosition(100.0, 630.0).build();
        Hitbox barryHitbox = new HitboxImpl(barryMovement.getPosition(),
                new Pair<>(GameInfo.getInstance().getDefaultWidth() / 17, GameInfo.getInstance().getScreenHeight() / 7),
                0.0);
        this.barry = new BarryImpl(barryMovement, barryHitbox);
        assertTrue(this.barry.isAlive());
        assertEquals(this.barry.getEntityStatus(), EntityStatus.ACTIVE);
        assertEquals(this.barry.getPerformingAction(), PerformingAction.WALKING);
        assertFalse(this.barry.hasShield());
    }

    @Test
    public void testPlayerHit() {
        Movement barryMovement = new Movement.Builder().setPosition(100.0, 0.0)
                .setMovementChangers(List.of(MovementChangers.GRAVITY, MovementChangers.BOUNDS)).build();
        Hitbox barryHitbox = new HitboxImpl(barryMovement.getPosition(),
                new Pair<>(GameInfo.getInstance().getDefaultWidth() / 17, GameInfo.getInstance().getScreenHeight() / 7),
                0.0);
        this.barry = new BarryImpl(barryMovement, barryHitbox);
        this.barry.setShieldOn();
        var status = this.barry.getPerformingAction();
        this.barry.hit(ObstacleType.ZAPPER);
        assertEquals(this.barry.getPerformingAction(), status);
        assertTrue(this.barry.isAlive());

        this.barry.hit(ObstacleType.MISSILE);
        assertFalse(this.barry.isAlive());
        assertEquals(this.barry.getPerformingAction(), PerformingAction.BURNED);
    }

    @Test
    public void testPlayerHit2() {
        Movement barryMovement = new Movement.Builder().setPosition(100.0, 0.0)
                .setMovementChangers(List.of(MovementChangers.GRAVITY, MovementChangers.BOUNDS)).build();
        Hitbox barryHitbox = new HitboxImpl(barryMovement.getPosition(),
                new Pair<>(GameInfo.getInstance().getDefaultWidth() / 17, GameInfo.getInstance().getScreenHeight() / 7),
                0.0);
        this.barry = new BarryImpl(barryMovement, barryHitbox);
        this.barry.hit(ObstacleType.ZAPPER);
        assertFalse(this.barry.isAlive());
        assertEquals(PerformingAction.ZAPPED, this.barry.getPerformingAction());
    }

    @Test
    public void testPlayerMove() {
        Movement barryMovement = new Movement.Builder().setPosition(100.0, 630.0)
                .setMovementChangers(List.of(MovementChangers.GRAVITY, MovementChangers.BOUNDS)).build();
        Hitbox barryHitbox = new HitboxImpl(barryMovement.getPosition(),
                new Pair<>(GameInfo.getInstance().getDefaultWidth() / 17, GameInfo.getInstance().getScreenHeight() / 7),
                0.0);
        this.barry = new BarryImpl(barryMovement, barryHitbox);
        this.barry.update(true);
        assertEquals(PerformingAction.PROPELLING, this.barry.getPerformingAction());
        this.barry.update(true);
        this.barry.update(false);
        assertEquals(PerformingAction.FALLING, this.barry.getPerformingAction());
        for (int i = 0; i < 10; i++) {
            this.barry.update(false);
        }

        assertEquals(PerformingAction.WALKING, this.barry.getPerformingAction());

        for (int i = 0; i < 100; i++) {
            this.barry.update(true);
        }
        assertEquals(PerformingAction.HEAD_DRAGGING, this.barry.getPerformingAction());
    }

    @Test
    public void testPlayerObstacleCollision() {
        this.entityFactory = new EntityModelGeneratorImpl();

        this.barry = this.entityFactory.generateBarry();
        boolean hasTouched = false;

        var zapper = this.entityFactory.generateObstacle(ObstacleType.ZAPPER, new Movement.Builder().setPosition(0.0, 580.0).setSpeed(-10.0,0.0).build());

        for (int i = 0; i < 110; i++) {
            barry.update(false);
            zapper.update(false);
            
            if (zapper.getHitbox().isTouching(barry.getHitbox())) {
                hasTouched = true;
            }
        }
        assertTrue(hasTouched);

    }

    @Test
    public void testPickupCollision() {
        Movement barryMovement = new Movement.Builder().setPosition(100.0, 630.0)
                .setMovementChangers(List.of(MovementChangers.GRAVITY, MovementChangers.BOUNDS)).build();
        Hitbox barryHitbox = new HitboxImpl(barryMovement.getPosition(),
                new Pair<>(GameInfo.getInstance().getDefaultWidth() / 17, GameInfo.getInstance().getScreenHeight() / 7),
                0.0);
        this.barry = new BarryImpl(barryMovement, barryHitbox);
        this.barry.update(true);
    }

    @Test
    public void testShieldPickup() {
        Movement barryMovement = new Movement.Builder().setPosition(100.0, 630.0)
                .setMovementChangers(List.of(MovementChangers.GRAVITY, MovementChangers.BOUNDS)).build();
        Hitbox barryHitbox = new HitboxImpl(barryMovement.getPosition(),
                new Pair<>(GameInfo.getInstance().getDefaultWidth() / 17, GameInfo.getInstance().getScreenHeight() / 7),
                0.0);
        this.barry = new BarryImpl(barryMovement, barryHitbox);
        this.barry.update(true);
    }
}
