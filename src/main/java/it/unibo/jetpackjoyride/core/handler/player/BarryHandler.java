package it.unibo.jetpackjoyride.core.handler.player;


import java.util.List;
import java.util.Optional;

import it.unibo.jetpackjoyride.core.entities.barry.api.Barry;
import it.unibo.jetpackjoyride.core.entities.barry.impl.BarryImpl;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.hitbox.impl.HitboxImpl;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.core.statistical.api.GameStatsController;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.MovementChangers;
import it.unibo.jetpackjoyride.utilities.Pair;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;
import javafx.scene.Group;


/**
 * The PlayerMover class is responsible for managing the movement of the player
 * character (Barry).
 * It handles updating the player's model and view based on user input and game
 * logic.
 */
public class BarryHandler {

    private Pair<Double, Double> lastScreenDims;

    private final Barry model;
    private final BarryView view;
    


    /**
     * Constructs a new PlayerMover instance.
     */
    public BarryHandler(final GameStatsController gameStatsHandler) {
      
            
                

                Movement barryMovement = new Movement.Builder().setPosition(50.0,0.0).setMovementChangers(List.of(MovementChangers.GRAVITY, MovementChangers.BOUNDS)).build();
                Hitbox barryHitbox = new HitboxImpl(barryMovement.getRelativePosition(), new Pair<>(GameInfo.getInstance().getDefaultWidth()/17, GameInfo.getInstance().getScreenHeight()/7));
        this.model = new BarryImpl(barryMovement,barryHitbox, gameStatsHandler);
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
    public void update(boolean isSpaceBarPressed){
        this.model.update(isSpaceBarPressed);
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
    

}
