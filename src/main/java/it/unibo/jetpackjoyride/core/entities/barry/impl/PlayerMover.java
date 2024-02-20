package it.unibo.jetpackjoyride.core.entities.barry.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Collections;
import it.unibo.jetpackjoyride.core.entities.barry.api.Barry;
import it.unibo.jetpackjoyride.core.entities.barry.api.Barry.BarryLifeStatus;
import it.unibo.jetpackjoyride.core.entities.barry.api.Barry.BarryStatus;

import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.statistical.api.GameStatsController;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;

/**
 * The PlayerMover class is responsible for managing the movement of the player
 * character (Barry).
 * It handles updating the player's model and view based on user input and game
 * logic.
 */
public class PlayerMover {

    private Pair<Double, Double> lastScreenDims;

    private final Barry model;
    private final BarryView view;


    /**
     * Constructs a new PlayerMover instance.
     */
    public PlayerMover(final GameStatsController gameStatsHandler) {
        this.lastScreenDims = new Pair<>(GameInfo.getInstance().getScreenWidth(),
                GameInfo.getInstance().getScreenHeight());
        this.model = new BarryImpl();
        if(gameStatsHandler.getGameStatsModel().isShieldEquipped()){
            this.model.setShieldOn();
            gameStatsHandler.getGameStatsModel().addShields(gameStatsHandler.getGameStatsModel().getNumOfShields()-1);
            gameStatsHandler.getGameStatsModel().setShield(false);
        }
        
        

        this.view = new BarryView();
    }

    /**
     * Builds the status map containing lists of images for each BarryStatus.
     */

     public Barry getModel(){
        return this.model;
     }

    /**
     * Moves the player character based on the given input.
     * 
     * @param pressed Indicates whether the movement input is pressed.
     */
    public boolean move(final boolean pressed) {
        
        
            final var currendScreenDims = new Pair<>(GameInfo.getInstance().getScreenWidth(),
                    GameInfo.getInstance().getScreenHeight());
            if (!currendScreenDims.equals(this.lastScreenDims)) {
                this.model.updateLimits(currendScreenDims.get1() / lastScreenDims.get1(),
                        currendScreenDims.get2() / lastScreenDims.get2());
                this.lastScreenDims = currendScreenDims;
            }
            return this.model.move(pressed);
            
         
    }

    /**
     * Updates the view of the player character.
     * 
     * @param root The root pane to which the player character's view will be added.
     */
    public void updateView(final Group root) {
        this.view.update(root, model);
    }

    /**
     * Retrieves the hitbox of the player character.
     * 
     * @return The hitbox of the player character.
     */
    public Optional<Hitbox> getHitbox() {
        return this.model.getHitbox();
    }
    /**
     * Handles the player character being hit by an obstacle.
     * 
     * @param type The type of obstacle that hit the player character.
     */
    public void hit(ObstacleType type) {
        if(this.model.isAlive()){
        if (this.model.hasShield()) {
     
            this.model.removeShield();

        } else {
            
            this.model.kill(type);

        }
    }
    }
    /**
     * Sets the shield on the player character.
     */
    public void setBarryShield() {
        this.model.setShieldOn();
    }
    /**
     * Activates the player character.
     */
    public void activate() {
        this.model.setActiveValue(true);
    }
    /**
     * Deactivates the player character.
     */
    public void deactivate() {
        this.model.setActiveValue(false);
        
    }

}
