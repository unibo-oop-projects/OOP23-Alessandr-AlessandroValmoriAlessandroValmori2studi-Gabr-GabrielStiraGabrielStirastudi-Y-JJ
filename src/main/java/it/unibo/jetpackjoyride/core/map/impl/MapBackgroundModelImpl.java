package it.unibo.jetpackjoyride.core.map.impl;

import java.util.List;

import it.unibo.jetpackjoyride.core.map.api.MapBackgroundModel;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class MapBackgroundModelImpl implements MapBackgroundModel {

    private final static int MAX_SPEED = 10;

     private double bgImageX1, bgImageX2;
     private final GameInfo gameInfo;
     private double mapWidth;
     private double mapHeight;
     private Timeline timeline;
    
     public MapBackgroundModelImpl() {

        this.gameInfo = GameInfo.getInstance();
        mapHeight = gameInfo.getScreenHeight();
        mapWidth = gameInfo.getScreenWidth();
        bgImageX1 = 0;
        bgImageX2 = this.mapWidth;
        this.timeline = new Timeline(new KeyFrame(Duration.seconds(60), e -> {
            if(GameInfo.moveSpeed.get() == MAX_SPEED){
                timeline.stop();
            }else{
                gameInfo.setMoveSpeed(GameInfo.moveSpeed.incrementAndGet());
            }       
        }));
    }

    @Override
    public void updateBackgroundModel() {
        if(!timeline.statusProperty().get().equals(Status.RUNNING)){
            timeline.play();
        }
        bgImageX1 -= GameInfo.moveSpeed.get();
        bgImageX2 -= GameInfo.moveSpeed.get();;

        if (isOutofMap(bgImageX1)) {
            bgImageX1 = bgImageX2 + mapWidth-1;
        }
        if (isOutofMap(bgImageX2)) {
            bgImageX2 = bgImageX1 + mapWidth-1;
        }
        updateSize();

    }

    @Override
    public List<Double> getPosX(){
        return List.of(bgImageX1,bgImageX2);
    }

    @Override
    public Pair<Double,Double> getSize(){
        return new Pair<>(this.mapWidth, this.mapHeight);
    }

    private void updateSize() {
        double newWidth = gameInfo.getScreenWidth();
        double newHeight = gameInfo.getScreenHeight();

        if (newWidth != mapWidth || newHeight != mapHeight) {
            double ratioX1 = bgImageX1 / mapWidth;
            double ratioX2 = bgImageX2 / mapWidth;
            mapWidth = newWidth;
            mapHeight = newHeight;

            bgImageX1 = mapWidth * ratioX1;
            bgImageX2 = mapWidth * ratioX2;

        }

    }

    private boolean isOutofMap(double x) {
        return x <= -mapWidth;
    }
}
