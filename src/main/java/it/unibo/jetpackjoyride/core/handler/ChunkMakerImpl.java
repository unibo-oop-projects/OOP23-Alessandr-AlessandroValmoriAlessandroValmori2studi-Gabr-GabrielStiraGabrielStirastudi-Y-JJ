package it.unibo.jetpackjoyride.core.handler;

import java.util.*;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class ChunkMakerImpl implements ChunkMaker{

    ObstacleSpawner obstacleSpawner;
    List<ObstacleController> listOfControllers;
    Thread chunkMaker;

    public void initialize() {
        listOfControllers = new ArrayList<>();
        obstacleSpawner = new ObstacleSpawner();
        chunkMaker = new Thread(this);
        this.start();
    }

    @Override
    public void run(){
        while(true) {
            this.listOfControllers.addAll(obstacleSpawner.generateChunk());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void start() {
        this.chunkMaker.start();
    }

    @Override
    public void updateView(Pane root) {
        for(var controller : listOfControllers) {

            if(!root.getChildren().contains((Node)controller.getImageView())) {
                root.getChildren().add((Node)controller.getImageView());
            }

            if(controller.getObstacleModel().isOutOfBounds()) {
                root.getChildren().remove((Node)controller.getImageView());
            }
        }        
    }

    @Override
    public void updateModel() {
        for(var controller : listOfControllers) {
            controller.update();

            if(controller.getObstacleModel().isOutOfBounds()) {
                //this.listOfControllers.remove(controller);            
            }
        }        
    }

    @Override
    public List<ObstacleController> getControllers() {
        return this.listOfControllers;
    }
}
