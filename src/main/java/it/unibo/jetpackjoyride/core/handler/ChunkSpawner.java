package it.unibo.jetpackjoyride.core.handler;

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
import javafx.scene.image.Image;

import java.util.*;
public class ChunkSpawner {
    private final static Movement DEFAULTMOVEMENT = new MovementGenerator(new Pair<>(1400.0,400.0), new Pair<>(0.0,0.0), new Pair<>(0.0,0.0), new Pair<>(0.0, 0.0)).setMovementChangers(List.of(MovementChangers.DEFAULT));
    private final static Movement BOUNCINGMOVEMENT = new MovementGenerator(new Pair<>(1400.0,400.0), new Pair<>(0.0,0.0), new Pair<>(0.0,0.0), new Pair<>(0.0, 0.0)).setMovementChangers(List.of(MovementChangers.BOUNCING, MovementChangers.DIAGONALUP));
    private final static Movement HOMINGMOVEMENT = new MovementGenerator(new Pair<>(1400.0,400.0), new Pair<>(0.0,0.0), new Pair<>(0.0,0.0), new Pair<>(0.0, 0.0)).setMovementChangers(List.of(MovementChangers.HOMING));
    
    private final static Hitbox MISSILEHITBOX = new MissileHitbox(new Pair<>(1400.0,400.0), 0.0);

    private EntityGenerator entityGenerator;

    public void initialize() {
        entityGenerator = new EntityGeneratorImpl();
    }

    public List<ObstacleController> generateChunk() {
        List<ObstacleController> obstacleControllers;
        int numberOfMissiles = 5; 

        int index=0;
        Image[] images = new Image[35];
        for (int i = 0; i < 7; i++) {
            String imagePath = getClass().getClassLoader().getResource("sprites/entities/obstacles/missile/missile_" + (i+1) + ".png").toExternalForm();
            for(int j = 0 ; j < 5; j++) {
                images[index] = new Image(imagePath);  
                index++;
            }
        }

        
        obstacleControllers = new ArrayList<>();
        for (int i = 0; i < numberOfMissiles; i++) {
            Random random = new Random();
            ObstacleImpl model = this.entityGenerator.generateObstacle(ObstacleType.MISSILE, random.nextInt(2) == 0 ? DEFAULTMOVEMENT : BOUNCINGMOVEMENT, MISSILEHITBOX);

            ObstacleView view = new ObstacleView(images);

            System.out.println("Spawned a missile");
            ObstacleController obstacle = new ObstacleController(model, view);
            obstacleControllers.add(obstacle);
        }
        return obstacleControllers;
    }
}
