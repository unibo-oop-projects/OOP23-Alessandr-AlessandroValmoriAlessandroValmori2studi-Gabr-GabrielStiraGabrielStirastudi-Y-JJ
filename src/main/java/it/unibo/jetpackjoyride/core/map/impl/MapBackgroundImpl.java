package it.unibo.jetpackjoyride.core.map.impl;

import it.unibo.jetpackjoyride.core.map.api.MapBackground;
import it.unibo.jetpackjoyride.core.map.api.MapBackgroundModel;
import it.unibo.jetpackjoyride.core.map.api.MapBackgroundView;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;


public class MapBackgroundImpl implements MapBackground {

    private final static int MAX_SPEED = 10;
    private final static int DEFAULT_SPEED = 5;

    private MapBackgroundModel model;
    private MapBackgroundView view;
    private Timeline timeline;
    private GameInfo gameInfo;

    public MapBackgroundImpl(){
        model = new MapBackgroundModelImpl();
        view = new MapBackgroungViewImpl();
        gameInfo = GameInfo.getInstance();
          this.timeline = new Timeline(new KeyFrame(Duration.seconds(60), e -> {
            if(GameInfo.moveSpeed.get() == MAX_SPEED){
                timeline.stop();
            }else{
                gameInfo.setMoveSpeed(GameInfo.moveSpeed.incrementAndGet());
            }       
        }));
    }

    @Override
    public void updateBackgroundView() {
        this.view.updateBackgroundView(model.getPosX().get(0), model.getPosX().get(1)
        ,model.getSize().get1(), model.getSize().get2());
    }

    @Override
    public void updateBackgroundModel() { 
        if(!timeline.statusProperty().get().equals(Status.RUNNING)){
            timeline.play();
        }
        this.model.updateBackgroundModel();
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

}