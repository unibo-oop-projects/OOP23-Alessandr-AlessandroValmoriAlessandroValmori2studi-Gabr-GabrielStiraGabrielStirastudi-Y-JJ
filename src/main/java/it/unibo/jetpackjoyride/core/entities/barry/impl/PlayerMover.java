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
    private final Map<BarryStatus, List<Image>> statusMap = new HashMap<>();
    private static final int NUM_COPIES = 7;

    private final Map<BarryStatus, Integer> framesPerAnimation = new HashMap<>() {
        {
            put(BarryStatus.WALKING, 4);
            put(BarryStatus.BURNED, 4);
            put(BarryStatus.LASERED, 4);
            put(BarryStatus.ZAPPED, 4);
            put(BarryStatus.FALLING, 2);
            put(BarryStatus.PROPELLING, 2);
            put(BarryStatus.HEAD_DRAGGING, 2);
        }
    };

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
        
        this.buildMap();

        this.view = new BarryView(this.getSpritesForStatus());
    }

    /**
     * Builds the status map containing lists of images for each BarryStatus.
     */
    private void buildMap() {
        for (final var entry : framesPerAnimation.entrySet()) {
            final List<Image> images = new ArrayList<>();
            for (int i = 0; i < entry.getValue(); i++) {
                final String imagePath = getClass().getClassLoader()
                        .getResource("sprites/entities/player/barry" + entry.getKey().toString() + (i + 1) + ".png")
                        .toExternalForm();

                images.addAll(Collections.nCopies(NUM_COPIES, new Image(imagePath)));
            }
            this.statusMap.put(entry.getKey(), new ArrayList<>(images));
        }
    }

    /**
     * Retrieves the list of sprites for the current BarryStatus.
     * 
     * @return The list of sprites for the current BarryStatus.
     */
    private List<Image> getSpritesForStatus() {
        return this.statusMap.get(this.model.getBarryStatus());
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

        this.view.update(model);
        this.view.setCurrentImages(this.getSpritesForStatus(), this.model.getBarryStatus());

        final Node imageView = (Node) this.view.getImageView();
        final Node shieldImageView = (Node) this.view.getShieldImageView();

        if (this.model.isActive()) {
            if (!root.getChildren().contains(imageView)) {
                root.getChildren().add(imageView);
            }
            if (this.model.hasShield() && !root.getChildren().contains(shieldImageView)) {
                root.getChildren().add(shieldImageView);
               
            } else if (!this.model.hasShield() && root.getChildren().contains(shieldImageView)) {
                root.getChildren().remove(shieldImageView);
                
            }
        } else {
            root.getChildren().removeAll(imageView, shieldImageView);
        }

    }

    /**
     * Retrieves the hitbox of the player character.
     * 
     * @return The hitbox of the player character.
     */
    public Optional<Hitbox> getHitbox() {
        return this.model.getHitbox();
    }

    public void hit(ObstacleType type) {
        if(this.model.isAlive()){
        if (this.model.hasShield()) {
            System.out.println("removed shield");
            this.model.removeShield();

        } else {
            this.model.setLifeStatus(BarryLifeStatus.DEAD);
            this.model.kill(type);

        }
    }
    }

    public void setBarryShield() {
        this.model.setShieldOn();
    }

    public void activate() {
        this.model.setActiveValue(true);
    }

    public void deactivate() {
        this.model.setActiveValue(false);
    }

}
