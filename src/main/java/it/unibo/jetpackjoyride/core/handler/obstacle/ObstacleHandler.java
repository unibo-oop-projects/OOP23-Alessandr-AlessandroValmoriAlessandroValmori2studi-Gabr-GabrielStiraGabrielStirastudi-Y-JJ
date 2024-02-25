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

public final class ObstacleHandler {
    private static final Double MINIMUMSPAWNTIME = 0.25;
    private ObstacleLoader obstacleLoader;
    private Set<Obstacle> listOfObstacles;
    private Timeline timeline;

    public void initialize() {

        this.listOfObstacles = new HashSet<>();
        this.obstacleLoader = new ObstacleLoader();
        this.timeline = new Timeline(new KeyFrame(Duration.seconds(MINIMUMSPAWNTIME), e -> generate()));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void generate() {
        synchronized (this.listOfObstacles) {
            this.listOfObstacles.addAll(obstacleLoader.getInstanceOfPattern());
        }
    }

    public void over() {
        timeline.stop();
    }

    public void start() {
        timeline.play();
    }

    public Optional<ObstacleType> update(final Optional<Hitbox> playerHitbox) {
        synchronized (this.listOfObstacles) {

            var iterator = listOfObstacles.iterator();
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

                // Deactivate all obstacles on screen if one hit the player (give the player a
                // brief moment of grace time)
                if (obstacleHitPlayer.isPresent()) {
                    iterator = listOfObstacles.iterator();
                    while (iterator.hasNext()) {
                        final var model = iterator.next();
                        model.setEntityStatus(EntityStatus.DEACTIVATED);
                    }
                }
            return obstacleHitPlayer;
        }
    }

    public Set<Obstacle> getAllObstacles() {
        return this.listOfObstacles;
    }

    public void deactivateAllObstacles() {
        synchronized (this.listOfObstacles) {
            this.listOfObstacles.forEach(model -> model.setEntityStatus(EntityStatus.DEACTIVATED));
        }
    }
}
