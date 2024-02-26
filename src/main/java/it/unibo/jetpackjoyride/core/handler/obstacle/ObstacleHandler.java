package it.unibo.jetpackjoyride.core.handler.obstacle;

import it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityStatus;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.util.Set;
import java.util.Optional;
import java.util.HashSet;
import java.util.Collections;

 /**
  * The {@link ObstacleHandler} class is one of the main handlers of the game, 
  * which are classes used to manage the various entities in the game.
  * This class in particular deals with the generation, updating and interaction
  * managing of all {@link Obstacle} in the game.
  * A thread is used for the generation process of obstacles.
  *
  * @author gabriel.stira@studio.unibo.it
  */
public final class ObstacleHandler {
    /**
     * The obstacle generation works like this:
     * Every MINIMUMSPAWNTIME seconds, the set of all obstacles is updated
     * by adding new obstacles which have just been generated. The method used
     * (getInstanceOfPattern()) returns a set of obstacles which are just one 
     * part of the entire pattern of obstacles that is being generated. 
     * All patterns are formed by ostacles that are generated at different times, 
     * so every MINIMUMSPAWNTIME, the obstacles associated to that particular tick
     * are loaded. The maximum number of ticks defining a the lenght of a particular 
     * pattern are chosen inside the {@link ObstacleLoader} class.
     */
    private static final Double MINIMUMSPAWNTIME = 0.25;
    /**
     * Defines the class used to handle the generation of obstacles.
     */
    private final ObstacleLoader obstacleLoader;
    /**
     * Defines the set which contains all obstacles which are in a CHARGING, ACTIVE or DEACTIVATED status.
     */
    private final Set<Obstacle> setOfObstacles;
    /**
     * Defines the thread used to handle the generation of obstacles separately from the updating and managing.
     */
    private final Timeline timeline;

    /**
     * Constructor used to initialize the various elements described before.
     */
    public ObstacleHandler() {
        this.setOfObstacles = new HashSet<>();
        this.obstacleLoader = new ObstacleLoader();
        this.timeline = new Timeline(new KeyFrame(Duration.seconds(MINIMUMSPAWNTIME), e -> generate()));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    /**
     * 
     */
    private void generate() {
        synchronized (this.setOfObstacles) {
            this.setOfObstacles.addAll(obstacleLoader.getInstanceOfPattern());
        }
    }

    /**
     * Used to stop the thread which generates the obstacles.
     */
    public void over() {
        timeline.stop();
    }

    /**
     * Used to start the thread which generates the obstacles.
     */
    public void start() {
        timeline.play();
    }

    /**
     * Updates all obstacles in the game. If an obstacle has a INACTIVE entityStatus,
     * it is removed from the set of all obstacles. 
     * If an obstacle collides with the hitbox passed as a parameter, all obstacles
     * have their entityStatus set to DEACTIVATED and the {@link ObstacleType} of the
     * obstacle which collided is returned (Barry need to know what obstacle hit him 
     * for its death animation).
     * 
     * @param playerHitbox The hitbox of the player/powerup.
     * @return The {@link ObstacleType} if an obstacle collided with the hitbox,
     * Optional.Empty() otherwise.
     */
    public Optional<ObstacleType> update(final Optional<Hitbox> playerHitbox) {
        synchronized (this.setOfObstacles) {

            var iterator = setOfObstacles.iterator();
            Optional<ObstacleType> obstacleHitPlayer = Optional.empty(); 
                while (iterator.hasNext()) {
                    final var model = iterator.next();

                    model.update(false);
                    if (playerHitbox.isPresent() && model.getHitbox().isTouching(playerHitbox.get())
                    && model.getEntityStatus().equals(EntityStatus.ACTIVE)) {
                        obstacleHitPlayer = Optional.of(model.getObstacleType());
                        model.setEntityStatus(EntityStatus.DEACTIVATED);
                    }

                    if (model.getEntityStatus().equals(EntityStatus.INACTIVE)) {
                        iterator.remove();
                    }
                }

                /* Deactivate all obstacles on screen if one hit the player (give the player a
                 brief moment of grace time) */
                if (obstacleHitPlayer.isPresent()) {
                    iterator = setOfObstacles.iterator();
                    while (iterator.hasNext()) {
                        final var model = iterator.next();
                        model.setEntityStatus(EntityStatus.DEACTIVATED);
                    }
                }
            return obstacleHitPlayer;
        }
    }

    /**
     * Gets the set containing all the obstacles.
     * @return The set of all obstacles.
     */
    public Set<Obstacle> getAllObstacles() {
        return Collections.unmodifiableSet(this.setOfObstacles);
    }

    /**
     * Deactivates all obstacles which are currently CHARGING or ACTIVE.
     */
    public void deactivateAllObstacles() {
        synchronized (this.setOfObstacles) {
            this.setOfObstacles.forEach(model -> model.setEntityStatus(EntityStatus.DEACTIVATED));
        }
    }
}
