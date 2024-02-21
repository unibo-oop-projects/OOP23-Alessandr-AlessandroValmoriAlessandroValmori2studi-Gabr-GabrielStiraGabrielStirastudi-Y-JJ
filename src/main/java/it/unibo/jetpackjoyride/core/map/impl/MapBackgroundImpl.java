package it.unibo.jetpackjoyride.core.map.impl;

import java.util.List;

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
public final class MapBackgroundImpl implements MapBackground {

    private static final int MAX_SPEED = 14;
    private static final int DEFAULT_SPEED = 5;
    private static final int DURATION_SECONDS = 5;

    private final MapBackgroundModel model;
    private final MapBackgroundView view;
    private Timeline timeline;
    private final GameInfo gameInfo;

    /**
     * Constructor of the MapBackgroundImpl.
     * @param gameRoot the main Root of the Game
     */
    public MapBackgroundImpl(final Pane gameRoot) {
        model = new MapBackgroundModelImpl();
        view = new MapBackgroungViewImpl(this.getModelData());
        gameInfo = GameInfo.getInstance();
          this.timeline = new Timeline(new KeyFrame(Duration.seconds(DURATION_SECONDS), e -> {
            if (GameInfo.MOVE_SPEED.get() == MAX_SPEED) {
                this.timeline.stop();
            } else {
                gameInfo.setMoveSpeed(GameInfo.MOVE_SPEED.incrementAndGet());
            }
        }));
    }

    @Override
    public void setMapOnGameRoot(final Pane root) {
            view.addNodeInRoot(root);
    }

    @Override
    public void updateBackground() {
        updateBackgroundModel();
        updateBackgroundView();
    }


    @Override
    public void reset() {
        if (GameInfo.MOVE_SPEED.get() != DEFAULT_SPEED) {
            gameInfo.setMoveSpeed(DEFAULT_SPEED);
        }
        model.reset();
        timeline.playFromStart();
    }

    @Override
    public List<Pair<Double, Double>> getModelData() {
         return List.of(model.getPosX(), model.getSize());
    }

     /**
     * Method that updates the background view.
     */
    private void updateBackgroundView() {
        this.view.updateBackgroundView(this.getModelData());
    }

   /**
     * Method that updates the background model.
     */
    private void updateBackgroundModel() { 
        if (!timeline.statusProperty().get().equals(Status.RUNNING)) {
            timeline.play();
        }
        this.model.updateBackgroundModel();
    } 
}
