package core.test;

import static core.entities.entity.api.Entity.EntityType.*;
import static core.entities.obstacle.api.Obstacle.ObstacleType.*;
import static core.entities.powerup.api.PowerUp.PowerUpType.*;
import static org.junit.Assert.assertEquals;

import core.entities.entity.api.Entity;
import core.entities.entity.api.EntityFactory;
import core.entities.entity.impl.EntityFactoryImpl;
import core.entities.obstacle.impl.ObstacleImpl;
import core.hitbox.Hitbox;
import core.movement.custom.StandardMissileMovement;
import core.utilities.Pair;
import core.entities.entity.api.Entity.EntityType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Test {
	private EntityFactory entityFactory;
	private Map<EntityType, Set<Entity>> entities = new HashMap<>();

	@org.junit.Before
	public void initFactory() {
		this.entityFactory = new EntityFactoryImpl();
	}

	@org.junit.Test
	public void testObstacles() {

		ObstacleImpl missile1 = this.entityFactory.generateObstacle(MISSILE, new StandardMissileMovement(new Pair<Double, Double>(0.0, 0.0)), new Hitbox());
		ObstacleImpl zapper1 = this.entityFactory.generateObstacle(ZAPPER, new StandardMissileMovement(new Pair<Double, Double>(0.0, 0.0)), new Hitbox());
		ObstacleImpl laser1= this.entityFactory.generateObstacle(LASER, new StandardMissileMovement(new Pair<Double, Double>(0.0, 0.0)), new Hitbox());
		
		assertEquals(missile1.getEntityType(), OBSTACLE);
		assertEquals(missile1.getObstacleType(), MISSILE);
		assertEquals(missile1.getStartingPosition(), new Pair<Double, Double>(0.0, 0.0));
		assertEquals(missile1.getCurrentPosition(), new Pair<Double, Double>(0.0, 0.0));

		assertEquals(zapper1.getEntityType(), OBSTACLE);
		assertEquals(zapper1.getObstacleType(), ZAPPER);
		assertEquals(zapper1.getStartingPosition(), new Pair<Double, Double>(0.0, 0.0));
		assertEquals(zapper1.getCurrentPosition(), new Pair<Double, Double>(0.0, 0.0));

		assertEquals(laser1.getEntityType(), OBSTACLE);
		assertEquals(laser1.getObstacleType(), LASER);
		assertEquals(laser1.getStartingPosition(), new Pair<Double, Double>(0.0, 0.0));
		assertEquals(laser1.getCurrentPosition(), new Pair<Double, Double>(0.0, 0.0));

		missile1.setCurrentPosition(new Pair<>(2.0, 5.0));
		assertEquals(missile1.getCurrentPosition(), new Pair<Double, Double>(2.0, 5.0));

		Arrays.stream(EntityType.values()).forEach(e -> entities.put(e, new HashSet<>()));
		
		entities.computeIfAbsent(OBSTACLE, k -> new HashSet<>())
				.add(this.entityFactory.generateObstacle(MISSILE, new StandardMissileMovement(new Pair<Double, Double>(0.0, 0.0)), new Hitbox()));

		entities.computeIfAbsent(POWERUP, k -> new HashSet<>())
				.add(this.entityFactory.generatePowerUp(MRCUDDLE, new StandardMissileMovement(new Pair<Double, Double>(0.0, 0.0)), new Hitbox()));
				
		System.out.println(entities);
	}	
}