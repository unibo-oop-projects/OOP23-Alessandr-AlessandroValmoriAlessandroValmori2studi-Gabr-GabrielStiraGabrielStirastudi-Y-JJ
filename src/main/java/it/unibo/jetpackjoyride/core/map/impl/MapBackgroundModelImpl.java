package it.unibo.jetpackjoyride.core.map.impl;

import java.util.List;

import it.unibo.jetpackjoyride.core.map.api.MapBackgroundModel;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;




public class MapBackgroundModelImpl implements MapBackgroundModel {

   
    private final static int OFFSET = 1;

     private double bgImageX1, bgImageX2;
     private final GameInfo gameInfo;
     private double mapWidth;
     private double mapHeight;
    
     public MapBackgroundModelImpl() {

        this.gameInfo = GameInfo.getInstance();
        mapHeight = gameInfo.getScreenHeight();
        mapWidth = gameInfo.getScreenWidth();
        bgImageX1 = 0;
        bgImageX2 = this.mapWidth;
      
    }

    @Override
    public void updateBackgroundModel() {
        
        bgImageX1 -= GameInfo.moveSpeed.get();
        bgImageX2 -= GameInfo.moveSpeed.get();;

        if (isOutofMap(bgImageX1)) {
            bgImageX1 = bgImageX2 + mapWidth-OFFSET;
        }
        if (isOutofMap(bgImageX2)) {
            bgImageX2 = bgImageX1 + mapWidth-OFFSET;
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

    public void reset(){
        this.bgImageX1 = 0;
        this.bgImageX2 = mapWidth;
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
