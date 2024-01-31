package it.unibo.jetpackjoyride.core.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.unibo.jetpackjoyride.core.entities.entity.api.EntityGenerator;
import it.unibo.jetpackjoyride.core.entities.entity.impl.EntityGeneratorImpl;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;
import it.unibo.jetpackjoyride.core.entities.obstacle.impl.ObstacleImpl;
import it.unibo.jetpackjoyride.core.hitbox.Hitbox;
import it.unibo.jetpackjoyride.core.hitbox.impl.LaserHitbox;
import it.unibo.jetpackjoyride.core.hitbox.impl.MissileHitbox;
import it.unibo.jetpackjoyride.core.hitbox.impl.ZapperHitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.core.movement.MovementGenerator;
import it.unibo.jetpackjoyride.core.movement.MovementGenerator.MovementChangers;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;
import javafx.scene.image.Image;

public class ObstacleSpawner {
    private EntityGenerator entityGenerator;
    private GameInfo infoResolution;

    public ObstacleSpawner() {
        this.entityGenerator = new EntityGeneratorImpl();
    }

    public List<ObstacleController> generateChunk() {
        List<ObstacleController> obstacleControllers;
        this.infoResolution = new GameInfo();

        int index=0;
        Image[] images = new Image[91]; // 0-34 MISSILE | 35-62 ZAPPER | 63-90 LASER
        for (int i = 0; i < 5; i++) {
            String imagePath = getClass().getClassLoader().getResource("sprites/entities/obstacles/missile/missile_" + (i+1) + ".png").toExternalForm();
            
            for(int j = 0 ; j < 7; j++) {
                images[index] = new Image(imagePath);  
                index++;
            }
        }

        for (int i = 0; i < 4; i++) {
            String imagePath = getClass().getClassLoader().getResource("sprites/entities/obstacles/zapper/zapper_" + (i+1) + ".png").toExternalForm();
            
            for(int j = 0 ; j < 7; j++) {
                images[index] = new Image(imagePath);  
                index++;
            }
        }

        for (int i = 0; i < 4; i++) {
            String imagePath = getClass().getClassLoader().getResource("sprites/entities/obstacles/laser/laser_" + (i+1) + ".png").toExternalForm();
            
            for(int j = 0 ; j < 7; j++) {
                images[index] = new Image(imagePath);  
                index++;
            }
        }

        obstacleControllers = new ArrayList<>();

        return randomChunk(obstacleControllers, images);
    }

    private List<ObstacleController> randomChunk(List<ObstacleController> obstacleControllers, Image[] images) {
        Random random = new Random();
        int numberOfObstacles = random.nextInt(4) + 1;
        
        
        for(int i=0; i<numberOfObstacles; i++) {
            int typeOfObstacle = random.nextInt(3);
            int typeOfMovement = random.nextInt(4);
            int j=0;
            int k=0;

            Movement movement;
            Hitbox hitbox;
            ObstacleType obstacleType;
            Image[] actualImages;

            switch (typeOfObstacle) {
                case 0:
                    //MISSILE
                    movement = new MovementGenerator(new Pair<>(infoResolution.getScreenWidth(),random.nextDouble()*infoResolution.getScreenHeight()), new Pair<>(0.0,0.0), new Pair<>(0.0,0.0), new Pair<>(0.0, 0.0)).setMovementChangers(List.of(typeOfMovement == 0 ? MovementChangers.DIAGONALUP : typeOfMovement == 1 ? MovementChangers.DIAGONALDOWN : typeOfMovement == 2 ? MovementChangers.HOMING : MovementChangers.SPEEDY, MovementChangers.BOUNCING));
                    hitbox = new MissileHitbox(movement.getCurrentPosition(), movement.getRotation().get1());
                    obstacleType = ObstacleType.MISSILE;
                    actualImages = new Image[35];
                    for(j=0; j<35; j++) {
                        actualImages[j] = images[j];
                    }
                    break;
                case 1:
                    //ZAPPER
                    movement = new MovementGenerator(new Pair<>(infoResolution.getScreenWidth(),random.nextDouble()*infoResolution.getScreenHeight()), new Pair<>(0.0,0.0), new Pair<>(0.0, 0.0), new Pair<>(random.nextDouble()*180,random.nextInt(2) == 0 ? 0.0 : random.nextDouble()*5)).setMovementChangers(List.of(MovementChangers.SLOW));
                    hitbox = new ZapperHitbox(movement.getCurrentPosition(), movement.getRotation().get1());
                    obstacleType = ObstacleType.ZAPPER;
                    actualImages = new Image[28];
                    for(j=35; j<63; j++) {
                        actualImages[k] = images[j];
                        k++;
                    }
                    break;
                case 2:
                    //LASER
                    movement = new MovementGenerator(new Pair<>(infoResolution.getScreenWidth()/2,random.nextDouble()*infoResolution.getScreenHeight()), new Pair<>(0.0,0.0), new Pair<>(0.0, 0.0), new Pair<>(0.0,0.0)).setMovementChangers(List.of(MovementChangers.STATIC));
                    hitbox = new LaserHitbox(movement.getCurrentPosition(),movement.getRotation().get1());
                    obstacleType = ObstacleType.LASER;
                    actualImages = new Image[28];
                    for(j=63; j<91; j++) {
                        actualImages[k] = images[j];
                        k++;
                    }
                    break;
                default:
                    throw new IllegalStateException();
            }

            ObstacleImpl model = this.entityGenerator.generateObstacle(obstacleType, movement, hitbox);

            ObstacleView view = new ObstacleView(actualImages);

            ObstacleController obstacle = new ObstacleController(model, view);
            obstacleControllers.add(obstacle);
        }
        return obstacleControllers;
    }


}
