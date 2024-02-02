package it.unibo.jetpackjoyride.core.handler;
import static it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleStatus.ACTIVE;
import static it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleStatus.INACTIVE;

import java.util.*;

import it.unibo.jetpackjoyride.core.hitbox.Hitbox;
import javafx.scene.Group;
import javafx.scene.Node;

public class EntityHandlerImpl implements EntityHandler{

    private ObstacleSpawner obstacleSpawner;
    private List<ObstacleController> listOfControllers;
    private Thread chunkMaker;
    private boolean isRunning = false;

    public void initialize() {
        listOfControllers = new ArrayList<>();
        isRunning = true;
        obstacleSpawner = new ObstacleSpawner();
        chunkMaker = new Thread(this);
        this.start();
    }

    @Override
    public void run(){
        while(isRunning) {
            synchronized(this.listOfControllers) {
                this.listOfControllers.addAll(obstacleSpawner.generateChunk());
            }

            try {
                Thread.sleep(3000);
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
    public boolean update(Group obstacleGroup, Hitbox playerHitbox) {
        synchronized(this.listOfControllers){
            var iterator = listOfControllers.iterator();
            boolean obstacleHitPlayer = false;
            while(iterator.hasNext()) {
                var controller = iterator.next();

                controller.update();

                if(collisionChecker(controller.getObstacleModel().getHitbox(), playerHitbox) && controller.getObstacleModel().getObstacleStatus().equals(ACTIVE)) {
                    System.out.println("Obstacle " + controller.getObstacleModel().getObstacleType() + " hit the player");
                    obstacleHitPlayer=true;
                }

                if(!obstacleGroup.getChildren().contains((Node)controller.getImageView())) {
                    obstacleGroup.getChildren().add((Node)controller.getImageView());
                }
    
                if(controller.getObstacleModel().getObstacleStatus().equals(INACTIVE)) {
                    obstacleGroup.getChildren().remove((Node)controller.getImageView());
                    iterator.remove();  
                }
            }  
            return obstacleHitPlayer;
        }
    }

    private boolean collisionChecker(Hitbox hitbox, Hitbox playerHitbox) {
        for(var vertex : playerHitbox.getHitboxVertex()) {
            if(hitbox.isTouching(vertex)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<ObstacleController> getControllers() {
        return this.listOfControllers;
    }
}
