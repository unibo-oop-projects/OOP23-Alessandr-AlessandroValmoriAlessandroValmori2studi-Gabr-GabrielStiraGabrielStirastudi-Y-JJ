package core.test;

import static core.entities.entity.api.Entity.EntityType.*;
import static core.entities.obstacle.api.Obstacle.ObstacleStatus.*;
import static core.entities.obstacle.api.Obstacle.ObstacleType.*;
import static core.movement.impl.MissileMovement.MissileMovementType;
import static org.junit.Assert.*;

import core.entities.entity.api.EntityGenerator;
import core.entities.entity.impl.EntityGeneratorImpl;
import core.entities.obstacle.api.Obstacle.ObstacleType;
import core.entities.obstacle.impl.ObstacleImpl;
import core.hitbox.Hitbox;
import core.hitbox.impl.MissileHitbox;
import core.movement.Movement;
import core.movement.impl.MissileMovement;
import core.utilities.Pair;

import java.util.*;


public class Test {
	private final static Movement DEFAULTMISSILEMOVEMENT = new MissileMovement(new Pair<>(0.0, 0.0), new Pair<>(0.0, 0.0), new Pair<>(0.0, 0.0), List.of(MissileMovementType.DEFAULT));
	private final static Hitbox MISSILEHITBOX = new MissileHitbox(new Pair<>(0.0, 0.0));

	private EntityGenerator entityGenerator;

	@org.junit.Before
	public void initFactory() {
		this.entityGenerator = new EntityGeneratorImpl();
	}

	@org.junit.Test
	public void testObstacles() {
		ObstacleImpl missile = this.entityGenerator.generateObstacle(ObstacleType.MISSILE, DEFAULTMISSILEMOVEMENT, null);

		assertEquals(missile.getEntityType(), OBSTACLE);
		assertEquals(missile.getObstacleType(), MISSILE);
		assertEquals(missile.getObstacleStatus(), INACTIVE);

		missile.getEntityMovement().setSpeed(new Pair<>(1.0, 5.0));
		int counter=0;
		while(true) {
			try {
				Thread.sleep(30);
			  } catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			  }
			  counter++;
			if(counter == 100) {
				missile.changeObstacleMovement(new MissileMovement(missile.getEntityMovement().getCurrentPosition(), missile.getEntityMovement().getSpeed(), missile.getEntityMovement().getAcceleration(), List.of(MissileMovementType.BOUNCING)));
			}
			missile.getEntityMovement().update();
			System.out.println("Position:" + missile.getEntityMovement().getCurrentPosition() + "     Speed: " + missile.getEntityMovement().getSpeed());
			System.out.flush();
		}
	}	


	@org.junit.Test
	public void testHitboxes() {
		ObstacleImpl missile = this.entityGenerator.generateObstacle(ObstacleType.MISSILE, DEFAULTMISSILEMOVEMENT, MISSILEHITBOX);

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
}