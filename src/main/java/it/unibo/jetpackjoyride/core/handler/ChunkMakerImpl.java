package it.unibo.jetpackjoyride.core.handler;
import static it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleStatus.INACTIVE;

import java.util.*;


import javafx.scene.Group;
import javafx.scene.Node;

public class ChunkMakerImpl implements ChunkMaker{

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
    
                if(controller.getObstacleModel().getObstacleStatus().equals(INACTIVE)) {
                    obstacleGroup.getChildren().remove((Node)controller.getImageView());
                    iterator.remove();  
                }
            }  
        }
    }



    @Override
    public List<ObstacleController> getControllers() {
        return this.listOfControllers;
    }
}
