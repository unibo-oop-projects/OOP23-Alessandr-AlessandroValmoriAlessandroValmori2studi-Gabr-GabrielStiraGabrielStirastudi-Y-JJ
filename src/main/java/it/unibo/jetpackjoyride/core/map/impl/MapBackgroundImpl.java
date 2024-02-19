package it.unibo.jetpackjoyride.core.map.impl;

import it.unibo.jetpackjoyride.core.map.api.MapBackground;
import it.unibo.jetpackjoyride.core.map.api.MapBackgroundModel;
import it.unibo.jetpackjoyride.core.map.api.MapBackgroundView;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Implementation of the MapBackground interface.
 * This class provides functionality to control the background of the game.
 * @author yukai.zhou@studio.unibo.it
 */
public class MapBackgroundImpl implements MapBackground {

    private final static int MAX_SPEED = 15;
    private final static int DEFAULT_SPEED = 5;

    private final MapBackgroundModel model;
    private final MapBackgroundView view;
    private Timeline timeline;
    private final GameInfo gameInfo;

    /**
     * Constructor of the MapBackgroundImpl.
     */
    public MapBackgroundImpl(){
        model = new MapBackgroundModelImpl();
        view = new MapBackgroungViewImpl(this);
        gameInfo = GameInfo.getInstance();
          this.timeline = new Timeline(new KeyFrame(Duration.seconds(30), e -> {
            if(GameInfo.moveSpeed.get() == MAX_SPEED){
                this.timeline.stop();
            }else{
                gameInfo.setMoveSpeed(GameInfo.moveSpeed.incrementAndGet());
            }       
        }));
    }

    @Override
    public void updateBackground() {
        updateBackgroundModel();
        updateBackgroundView();
    }

    @Override
    public Pane getPane(){
        return view.getPane();
    }

    @Override
    public void reset() {
        if(GameInfo.moveSpeed.get() != DEFAULT_SPEED){
            gameInfo.setMoveSpeed(DEFAULT_SPEED);
        }
        model.reset();
        timeline.playFromStart();
        
    }

    @Override
    public Pair<Double, Double> getPosX() {
         return this.model.getPosX();
    }

    @Override
    public Pair<Double, Double> getSize() {
         return this.model.getSize();
    }

     /**
     * Method that updates the background view.
     */
    private void updateBackgroundView() {
        this.view.updateBackgroundView();
    }

   /**
     * Method that updates the background model.
     */
    private void updateBackgroundModel() { 
        if(!timeline.statusProperty().get().equals(Status.RUNNING)){
            timeline.play();
        }
        this.model.updateBackgroundModel();
    }

  
}