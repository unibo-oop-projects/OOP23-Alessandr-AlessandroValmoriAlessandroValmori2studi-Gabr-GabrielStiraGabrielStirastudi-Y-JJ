package it.unibo.jetpackjoyride.core.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.unibo.jetpackjoyride.core.entities.entity.api.EntityGenerator;
import it.unibo.jetpackjoyride.core.entities.entity.impl.EntityGeneratorImpl;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleStatus;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;
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
    private Image[] images;

    public ObstacleSpawner() {
        this.entityGenerator = new EntityGeneratorImpl();
        this.initialize();
    }

    private void initialize() {
        this.infoResolution = new GameInfo();
        this.images = new Image[51]; // 0-14 MISSILE | 15-34 ZAPPER | 35-50 LASER
        int index=0;

        //MISSILE 15 total 
        for (int i = 0; i < 15; i++) {
            String imagePath = getClass().getClassLoader().getResource("sprites/entities/obstacles/missile/missile_" + (i+1) + ".png").toExternalForm();
            images[index] = new Image(imagePath);  
            index++;
        }

        //ZAPPER 20 total
        for (int i = 0; i < 20; i++) {
            String imagePath = getClass().getClassLoader().getResource("sprites/entities/obstacles/zapper/zapper_" + (i+1) + ".png").toExternalForm();
            images[index] = new Image(imagePath);  
            index++;
        }

        //LASER 16 total
        for (int i = 0; i < 16; i++) {
            String imagePath = getClass().getClassLoader().getResource("sprites/entities/obstacles/laser/laser_" + (i+1) + ".png").toExternalForm();
            images[index] = new Image(imagePath);  
            index++;  
        }
    }

    public List<ObstacleController> generateChunk() {
        //At the moment the only implementation of the generation of obstacle is a random generation; will be changed in some days
        return randomChunk();
    }

    private List<ObstacleController> randomChunk() {
        List<ObstacleController> obstacleControllers = new ArrayList<>();
        Random random = new Random();
        int numberOfObstacles = random.nextInt(4) + 1;
        
        for(int i=0; i<numberOfObstacles; i++) {
            int typeOfObstacle = random.nextInt(3);
            int typeOfMovement = random.nextInt(4);

            Movement movement;
            Hitbox hitbox;
            ObstacleType obstacleType;
            ObstacleStatus startingStatus;
            Image[] actualImages;

            switch (typeOfObstacle) {
                case 0:
                    //MISSILE
                    movement = new MovementGenerator(new Pair<>(infoResolution.getScreenWidth(),random.nextDouble()*infoResolution.getScreenHeight()), new Pair<>(0.0,0.0), new Pair<>(0.0,0.0), new Pair<>(0.0, 0.0)).setMovementChangers(List.of(typeOfMovement == 0 ? MovementChangers.DIAGONALUP : typeOfMovement == 1 ? MovementChangers.DIAGONALDOWN : typeOfMovement == 2 ? MovementChangers.HOMING : MovementChangers.SPEEDY, MovementChangers.BOUNCING));
                    hitbox = new MissileHitbox(movement.getCurrentPosition(), movement.getRotation().get1());
                    obstacleType = ObstacleType.MISSILE;
                    startingStatus = ObstacleStatus.ACTIVE;
                    actualImages = loadImages(0,14);
                    break;
                case 1:
                    //ZAPPER
                    movement = new MovementGenerator(new Pair<>(infoResolution.getScreenWidth(),random.nextDouble()*infoResolution.getScreenHeight()), new Pair<>(0.0,0.0), new Pair<>(0.0, 0.0), new Pair<>(random.nextDouble()*180,random.nextInt(2) == 0 ? 0.0 : random.nextDouble()*5)).setMovementChangers(List.of(MovementChangers.SLOW));
                    hitbox = new ZapperHitbox(movement.getCurrentPosition(), movement.getRotation().get1());
                    obstacleType = ObstacleType.ZAPPER;
                    startingStatus = ObstacleStatus.ACTIVE;
                    actualImages = loadImages(15,34);
                    break;
                case 2:
                    //LASER
                    movement = new MovementGenerator(new Pair<>(infoResolution.getScreenWidth()/2,random.nextDouble()*infoResolution.getScreenHeight()), new Pair<>(0.0,0.0), new Pair<>(0.0, 0.0), new Pair<>(0.0,0.0)).setMovementChangers(List.of(MovementChangers.STATIC));
                    hitbox = new LaserHitbox(movement.getCurrentPosition(),movement.getRotation().get1());
                    obstacleType = ObstacleType.LASER;
                    startingStatus = ObstacleStatus.CHARGING;
                    actualImages = loadImages(35, 50);
                    break;
                default:
                    throw new IllegalStateException();
            }

            Obstacle model = this.entityGenerator.generateObstacle(obstacleType, movement, hitbox);
            model.changeObstacleStatus(startingStatus);

            ObstacleView view = new ObstacleView(actualImages);

            ObstacleController obstacle = new ObstacleController(model, view);
            obstacleControllers.add(obstacle);
        }
        return obstacleControllers;
    }

    private Image[] loadImages(Integer fromIndex, Integer toIndex) {
        int j=0;
        int k=0;
        Image[] actualImages = new Image[toIndex-fromIndex+1];
        for(j=fromIndex; j<=toIndex; j++) {
            actualImages[k] = this.images[j];
            k++;
        }
        return actualImages;
    }


}
