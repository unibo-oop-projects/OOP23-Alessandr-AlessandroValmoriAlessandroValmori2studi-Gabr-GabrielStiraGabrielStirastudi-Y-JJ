package it.unibo.jetpackjoyride.core.handler;

import java.util.*;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class ChunkMakerImpl implements ChunkMaker{

    ObstacleSpawner obstacleSpawner;
    List<ObstacleController> listOfControllers;
    Thread chunkMaker;
    private boolean isRunning =false;

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
    public void updateView(Pane root) {
        synchronized(this.listOfControllers){
            var iterator = listOfControllers.iterator();
            while(iterator.hasNext()) {
                var controller = iterator.next();

                if(!root.getChildren().contains((Node)controller.getImageView())) {
                    root.getChildren().add((Node)controller.getImageView());
                }
    
                if(controller.getObstacleModel().isOutOfBounds()) {
                    root.getChildren().remove((Node)controller.getImageView());
                }
            }  
        }
    }

    @Override
    public void updateModel() {
        synchronized(this.listOfControllers){
            var iterator = listOfControllers.iterator();
            while(iterator.hasNext()) {
                var controller = iterator.next();
                controller.update();
    
                if(controller.getObstacleModel().isOutOfBounds()) {
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
