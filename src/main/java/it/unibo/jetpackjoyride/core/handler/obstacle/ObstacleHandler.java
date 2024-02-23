package it.unibo.jetpackjoyride.core.handler.obstacle;

import it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityStatus;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;
import it.unibo.jetpackjoyride.core.handler.generic.GenericController;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.util.Duration;
import java.util.*;

public final class ObstacleHandler {
    private final static Double MINIMUMSPAWNTIME = 0.25;
    private ObstacleLoader obstacleLoader;
    private List<GenericController<Obstacle, ObstacleView>> listOfControllers;
    private Timeline timeline;

    public void initialize() {

        this.listOfControllers = new ArrayList<>();
        this.obstacleLoader = new ObstacleLoader();
        this.timeline = new Timeline(new KeyFrame(Duration.seconds(MINIMUMSPAWNTIME), e -> generate()));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void generate() {
        synchronized (this.listOfControllers) {
            this.listOfControllers.addAll(obstacleLoader.getInstanceOfPattern());
        }
    }

    public void over() {
        timeline.stop();
    }

    public void start() {
        timeline.play();
    }

    public Optional<ObstacleType> update(final Group obstacleGroup, final Optional<Hitbox> playerHitbox) {
        synchronized (this.listOfControllers) {

            var iterator = listOfControllers.iterator();
            Optional<ObstacleType> obstacleHitPlayer = Optional.empty();
            
                while (iterator.hasNext()) {
                    final var controller = iterator.next();

                    controller.update(false);
                    if (playerHitbox.isPresent() && controller.getEntityModel().getHitbox().isTouching(playerHitbox.get())
                    && controller.getEntityModel().getEntityStatus().equals(EntityStatus.ACTIVE)) {
                        final Obstacle obstacleHit = (Obstacle) controller.getEntityModel();
                        obstacleHitPlayer = Optional.of(obstacleHit.getObstacleType());
                        controller.getEntityModel().setEntityStatus(EntityStatus.DEACTIVATED);
                    }
                    
                    if (!obstacleGroup.getChildren().contains((Node) controller.getImageView())) {
                        obstacleGroup.getChildren().add((Node) controller.getImageView());
                    }

                    if (controller.getEntityModel().getEntityStatus().equals(EntityStatus.INACTIVE)) {
                        obstacleGroup.getChildren().remove((Node) controller.getImageView());
                        iterator.remove();
                    }
                }

                // Deactivate all obstacles on screen if one hit the player (give the player a
                // brief moment of grace time)
                if (obstacleHitPlayer.isPresent()) {
                    iterator = listOfControllers.iterator();
                    while (iterator.hasNext()) {
                        final var controller = iterator.next();
                        controller.getEntityModel().setEntityStatus(EntityStatus.DEACTIVATED);
                    }
                }
            
            return obstacleHitPlayer;

        }
    }

    public void deactivateAllObstacles() {
        synchronized (this.listOfControllers) {
            this.listOfControllers
                .forEach(controller -> controller.getEntityModel().setEntityStatus(EntityStatus.DEACTIVATED));
        }
    }
}
