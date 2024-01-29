package it.unibo.jetpackjoyride.core.handler;

import it.unibo.jetpackjoyride.core.entities.entity.api.EntityGenerator;
import it.unibo.jetpackjoyride.core.entities.entity.impl.EntityGeneratorImpl;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;
import it.unibo.jetpackjoyride.core.entities.obstacle.impl.ObstacleImpl;
import it.unibo.jetpackjoyride.core.hitbox.Hitbox;
import it.unibo.jetpackjoyride.core.hitbox.impl.MissileHitbox;
import it.unibo.jetpackjoyride.core.hitbox.impl.ZapperHitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.core.movement.MovementGenerator;
import it.unibo.jetpackjoyride.core.movement.MovementGenerator.MovementChangers;
import it.unibo.jetpackjoyride.utilities.Pair;
import javafx.scene.image.Image;

import java.util.*;
public class ChunkSpawner {
    private final static Movement DEFAULTMOVEMENT = new MovementGenerator(new Pair<>(1400.0,400.0), new Pair<>(0.0,0.0), new Pair<>(0.0,0.0), new Pair<>(0.0, 0.0)).setMovementChangers(List.of(MovementChangers.DEFAULT));
    private final static Movement BOUNCINGMOVEMENT = new MovementGenerator(new Pair<>(1400.0,400.0), new Pair<>(0.0,0.0), new Pair<>(0.0,0.0), new Pair<>(0.0, 0.0)).setMovementChangers(List.of(MovementChangers.DEFAULT, MovementChangers.BOUNCING, MovementChangers.DIAGONALUP));
    private final static Movement HOMINGMOVEMENT = new MovementGenerator(new Pair<>(1400.0,400.0), new Pair<>(0.0,0.0), new Pair<>(0.0,0.0), new Pair<>(0.0, 0.0)).setMovementChangers(List.of(MovementChangers.DEFAULT, MovementChangers.HOMING));
    
    private final static Hitbox MISSILEHITBOX = new MissileHitbox(new Pair<>(1400.0,400.0), 0.0);
    private final static Hitbox ZAPPERHITBOX = new ZapperHitbox(new Pair<>(1400.0,400.0), 0.0);

    private EntityGenerator entityGenerator;

    public void initialize() {
        entityGenerator = new EntityGeneratorImpl();
    }

    public List<ObstacleController> generateChunk() {
        List<ObstacleController> obstacleControllers;
        int numberOfObstacles = 5; 
        Random random = new Random();

        
        int index=0;
        Image[] images = new Image[28];
        for (int i = 0; i < 4; i++) {
            String imagePath = getClass().getClassLoader().getResource("sprites/entities/obstacles/zapper/zapper_" + (i+1) + ".png").toExternalForm();
            
            for(int j = 0 ; j < 7; j++) {
                images[index] = new Image(imagePath);  
                index++;
            }
        }

        obstacleControllers = new ArrayList<>();

        
        for (int i = 0; i < numberOfObstacles; i++) {
            Movement newMovement = new MovementGenerator(new Pair<>(700.0,random.nextDouble()*700), new Pair<>(0.0,0.0), new Pair<>(0.0,0.0), new Pair<>(0.0, 0.0)).setMovementChangers(List.of(MovementChangers.DEFAULT, MovementChangers.SLOW));
            //ObstacleImpl model = this.entityGenerator.generateObstacle(ObstacleType.MISSILE, newMovement, MISSILEHITBOX);
            ObstacleImpl model = this.entityGenerator.generateObstacle(ObstacleType.ZAPPER, newMovement, ZAPPERHITBOX);
            model.getEntityMovement().setRotation(new Pair<>(0.0,5.0*random.nextDouble()));
            ObstacleView view = new ObstacleView(images);

            ObstacleController obstacle = new ObstacleController(model, view);
            obstacleControllers.add(obstacle);
        }

        return obstacleControllers;
    }
}
