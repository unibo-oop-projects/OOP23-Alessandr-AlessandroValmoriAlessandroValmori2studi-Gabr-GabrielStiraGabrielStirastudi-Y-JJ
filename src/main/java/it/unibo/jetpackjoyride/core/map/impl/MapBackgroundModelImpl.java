package it.unibo.jetpackjoyride.core.map.impl;

import it.unibo.jetpackjoyride.core.map.api.MapBackgroundModel;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;

/**
 * Implementation of the MapBackground interface.
 * This class provides functionality to control the background model of the game.
 * @author yukai.zhou@studio.unibo.it
 */
public class MapBackgroundModelImpl implements MapBackgroundModel {

   
    private final static int OFFSET = 1;

    /**
     * the backgroung position and size
     */
     private double bgImageX1, bgImageX2;
     private final GameInfo gameInfo;
     private double mapWidth;
     private double mapHeight;
    
    /**
     * Constructor of the MapBackgroundModelImpl.
     */
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

        if (isOutofMap(bgImageX1)) { //If the Backgroung Image is completely outside the map.
            bgImageX1 = bgImageX2 + mapWidth-OFFSET;// then reset its position to behind the other. 
        }
        if (isOutofMap(bgImageX2)) {
            bgImageX2 = bgImageX1 + mapWidth-OFFSET;
        }
        updateSize();

    }

    @Override
    public Pair<Double, Double> getPosX(){
        return new Pair<>(this.bgImageX1, this.bgImageX2);
    }

    @Override
    public Pair<Double,Double> getSize(){
        return new Pair<>(this.mapWidth, this.mapHeight);
    }

    @Override
    public void reset(){
        this.bgImageX1 = 0;
        this.bgImageX2 = mapWidth;
    }

     /**
     * Updates the size of the background based on the screen size.
     * If the screen size has changed, this method adjusts the background accordingly.
     */
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

    /**
     * Checks if the given x-coordinate is outside the map area.
     * 
     * @param x The x-coordinate to check.
     * @return True if the x-coordinate is outside the map area, false otherwise.
     */
    private boolean isOutofMap(double x) {
        return x <= -mapWidth;
    }
}
