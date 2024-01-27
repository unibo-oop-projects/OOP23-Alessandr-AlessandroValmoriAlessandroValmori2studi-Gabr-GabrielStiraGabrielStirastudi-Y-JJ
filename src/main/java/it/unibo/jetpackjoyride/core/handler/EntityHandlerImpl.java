package it.unibo.jetpackjoyride.core.handler;
import java.util.List;

import it.unibo.jetpackjoyride.core.entities.entity.api.EntityGenerator;
import it.unibo.jetpackjoyride.core.entities.entity.impl.EntityGeneratorImpl;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;
import it.unibo.jetpackjoyride.core.entities.obstacle.impl.ObstacleImpl;
import it.unibo.jetpackjoyride.core.hitbox.Hitbox;
import it.unibo.jetpackjoyride.core.hitbox.impl.MissileHitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.core.movement.MovementGenerator;
import it.unibo.jetpackjoyride.core.movement.MovementGenerator.MovementChangers;
import it.unibo.jetpackjoyride.utilities.Pair;

public class EntityHandlerImpl implements EntityHandler {

    private final static Movement DEFAULTMOVEMENT = new MovementGenerator(new Pair<>(0.0, 0.0), new Pair<>(0.0,0.0), new Pair<>(0.0,0.0)).setMovementChangers(List.of(MovementChangers.SPEEDY, MovementChangers.BOUNCING, MovementChangers.DIAGONALUP));
	private final static Hitbox MISSILEHITBOX = new MissileHitbox(new Pair<>(0.0, 0.0));

    private EntityGenerator entityGenerator = new EntityGeneratorImpl();
    ObstacleImpl ciao = this.entityGenerator.generateObstacle(ObstacleType.MISSILE, DEFAULTMOVEMENT, MISSILEHITBOX);

    public void update() {
        ciao.getEntityMovement().update();
        if(ciao.isOutOfBounds()) {
            ciao.getEntityMovement().setCurrentPosition(new Pair<>(0.0,0.0));
        }
        System.out.println(ciao.getEntityMovement().getCurrentPosition());
    }
}
