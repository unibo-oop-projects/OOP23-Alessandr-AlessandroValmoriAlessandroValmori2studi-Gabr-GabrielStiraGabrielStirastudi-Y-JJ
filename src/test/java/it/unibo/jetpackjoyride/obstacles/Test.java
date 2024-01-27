/*package it.unibo.jetpackjoyride.obstacles;

import static it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityType.*;
import static it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleStatus.*;
import static it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType.*;
import static org.junit.Assert.*;

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


import java.util.*;


public class Test {
	private final static Movement DEFAULTMOVEMENT = new MovementGenerator(new Pair<>(0.0, 0.0), new Pair<>(0.0,0.0), new Pair<>(0.0,0.0)).setMovementChangers(List.of(MovementChangers.SPEEDY, MovementChangers.BOUNCING, MovementChangers.DIAGONALUP));
	

	private final static Hitbox MISSILEHITBOX = new MissileHitbox(new Pair<>(0.0, 0.0));

	private EntityGenerator entityGenerator;
	


	@org.junit.Before
	public void initFactory() {
		this.entityGenerator = new EntityGeneratorImpl();
	}

	@org.junit.Test
	public void testObstacles() {
		ObstacleImpl missile = this.entityGenerator.generateObstacle(ObstacleType.MISSILE, DEFAULTMOVEMENT, null);

		assertEquals(missile.getEntityType(), OBSTACLE);
		assertEquals(missile.getObstacleType(), MISSILE);
		assertEquals(missile.getObstacleStatus(), INACTIVE);

		while(true) {
			try {
				Thread.sleep(30);
			  } catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			  }

			missile.getEntityMovement().update();
			System.out.println("Position:" + missile.getEntityMovement().getCurrentPosition() + "     Speed: " + missile.getEntityMovement().getSpeed() + "     Acceleratio: " + missile.getEntityMovement().getAcceleration());
			System.out.flush();
		}
	}	
	



	@org.junit.Test
	public void testHitboxes() {
		ObstacleImpl missile = this.entityGenerator.generateObstacle(ObstacleType.MISSILE, DEFAULTMOVEMENT, MISSILEHITBOX);

		missile.getEntityMovement().setSpeed(new Pair<>(1.0, 5.0));
		while(true) {
			try {
				Thread.sleep(30);
			  } catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			  }

			missile.getEntityMovement().update();
			missile.getHitbox().updateHitbox(missile.getEntityMovement().getCurrentPosition());
			System.out.println("Missile Position: " + missile.getEntityMovement().getCurrentPosition() + "     Hitbox Position: " + missile.getHitbox().getHitboxPosition());
			System.out.flush();
		}
	}
}*/