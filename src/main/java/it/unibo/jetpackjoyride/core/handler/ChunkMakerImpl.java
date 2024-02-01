package it.unibo.jetpackjoyride.core.handler;

import static it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleStatus.ACTIVE;
import static it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleStatus.DEACTIVATED;
import static it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleStatus.INACTIVE;

import java.util.*;

import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.core.movement.MovementGenerator;
import it.unibo.jetpackjoyride.core.movement.MovementGenerator.MovementChangers;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;
import javafx.scene.Group;
import javafx.scene.Node;

public class ChunkMakerImpl implements ChunkMaker{

    private ObstacleSpawner obstacleSpawner;
    private List<ObstacleController> listOfControllers;
    private Thread chunkMaker;
    private boolean isRunning = false;
    private GameInfo infoResolution;


    public void initialize() {
        listOfControllers = new ArrayList<>();
        isRunning = true;
        obstacleSpawner = new ObstacleSpawner();
        chunkMaker = new Thread(this);
        infoResolution = new GameInfo();
        this.start();
    }

    @Override
    public void run(){
        while(isRunning) {
            synchronized(this.listOfControllers) {
                this.listOfControllers.addAll(obstacleSpawner.generateChunk());
            }
            
            Random random = new Random();
            long newNum = random.nextLong(3);

            try {
                Thread.sleep(newNum*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } 
        }
    }

    public void over(){
        isRunning=false;
    }

    @Override
    public void start() {
        this.chunkMaker.start();
    }

    @Override
    public void update(Group obstacleGroup) {
        synchronized(this.listOfControllers){
            var iterator = listOfControllers.iterator();
            while(iterator.hasNext()) {
                var controller = iterator.next();

                controller.update();

                if(!obstacleGroup.getChildren().contains((Node)controller.getImageView())) {
                    obstacleGroup.getChildren().add((Node)controller.getImageView());
                }
    
                if(checkObstacles(controller)) {
                    obstacleGroup.getChildren().remove((Node)controller.getImageView());
                    iterator.remove();  
                }
            }  
        }
    }

    private boolean checkObstacles(ObstacleController controller) {
        var obstacle = controller.getObstacleModel();
        switch (obstacle.getObstacleType()) {
            case MISSILE:
                if(obstacle.getEntityMovement().getCurrentPosition().get1() < 0 - infoResolution.getScreenWidth()*0.1) {
                    obstacle.changeObstacleStatus(INACTIVE);
                    return true;
                }
                break;
            case ZAPPER:
                if(obstacle.getEntityMovement().getCurrentPosition().get1() < 0 - infoResolution.getScreenWidth()*0.1) {
                    obstacle.changeObstacleStatus(INACTIVE);
                    return true;
                }
                break;
            case LASER:
                int lifeTime = obstacle.getLifetime();
                switch (lifeTime) {
                    case 100:
                        obstacle.changeObstacleStatus(ACTIVE);
                        break;
                    case 300:
                        obstacle.changeObstacleStatus(DEACTIVATED);
                        break;
                    case 350:
                        obstacle.changeObstacleStatus(INACTIVE);
                        return true;
                    default:
                        return false;
                }
                break;
        }
        return false;
    }

    @Override
    public List<ObstacleController> getControllers() {
        return this.listOfControllers;
    }
}
