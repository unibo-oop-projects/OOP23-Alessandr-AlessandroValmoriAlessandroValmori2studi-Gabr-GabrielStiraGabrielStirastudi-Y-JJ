package it.unibo.jetpackjoyride.core.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.unibo.jetpackjoyride.core.entities.entity.api.EntityGenerator;
import it.unibo.jetpackjoyride.core.entities.entity.impl.EntityGeneratorImpl;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;
import it.unibo.jetpackjoyride.core.entities.obstacle.impl.ObstacleImpl;
import it.unibo.jetpackjoyride.core.hitbox.impl.MissileHitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.core.movement.MovementGenerator;
import it.unibo.jetpackjoyride.core.movement.MovementGenerator.MovementChangers;
import it.unibo.jetpackjoyride.utilities.Pair;
import javafx.scene.image.Image;

public class ObstacleSpawner {
    private EntityGenerator entityGenerator;

    public ObstacleSpawner() {
        this.entityGenerator = new EntityGeneratorImpl();
    }

    public List<ObstacleController> generateChunk() {
        List<ObstacleController> obstacleControllers;
        Random random = new Random();
        int numberOfObstacles = random.nextInt(5) + 1;
        int index=0;
        Image[] images = new Image[35];
        for (int i = 0; i < 5; i++) {
            String imagePath = getClass().getClassLoader().getResource("sprites/entities/obstacles/missile/missile_" + (i+1) + ".png").toExternalForm();
            
            for(int j = 0 ; j < 7; j++) {
                images[index] = new Image(imagePath);  
                index++;
            }
        }

        obstacleControllers = new ArrayList<>();

        for(int i=0; i<numberOfObstacles; i++) {
            Movement newMovement = new MovementGenerator(new Pair<>(1200.0,random.nextDouble()*700), new Pair<>(0.0,0.0), new Pair<>(0.0,0.0), new Pair<>(0.0, 0.0)).setMovementChangers(List.of(MovementChangers.DEFAULT));
            ObstacleImpl model = this.entityGenerator.generateObstacle(ObstacleType.MISSILE, newMovement, new MissileHitbox(new Pair<>(1400.0,400.0), 0.0));

            ObstacleView view = new ObstacleView(images);

            ObstacleController obstacle = new ObstacleController(model, view);
            obstacleControllers.add(obstacle);
        }

        return obstacleControllers;
    }


}
