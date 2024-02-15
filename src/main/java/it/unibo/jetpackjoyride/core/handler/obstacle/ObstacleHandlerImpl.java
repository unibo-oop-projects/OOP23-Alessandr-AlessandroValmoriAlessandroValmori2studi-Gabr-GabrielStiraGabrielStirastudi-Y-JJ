package it.unibo.jetpackjoyride.core.handler.obstacle;

import java.util.List;
import java.util.ArrayList;

import it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityStatus;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import javafx.scene.Group;
import javafx.scene.Node;

public final class ObstacleHandlerImpl implements Runnable {

    private static final Integer TIMEBETWEENCHUNKS = 3000;

    private ObstacleSpawner obstacleSpawner;
    private List<ObstacleController> listOfControllers;
    private Thread chunkMaker;
    private boolean isRunning;

    public void initialize() {
        this.isRunning = true;
        this.listOfControllers = new ArrayList<>();
        this.obstacleSpawner = new ObstacleSpawner();

        this.chunkMaker = new Thread(this);
        this.start();
    }

    @Override
    public void run() {
        while (isRunning) {
            synchronized (this.listOfControllers) {
                this.listOfControllers.addAll(obstacleSpawner.generateChunk());
            }

            try {
                Thread.sleep(TIMEBETWEENCHUNKS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void over() {
        isRunning = false;
    }

    public void start() {
        this.chunkMaker.start();
    }

    public boolean update(final Group obstacleGroup, final Hitbox playerHitbox) {
        synchronized (this.listOfControllers) {
            final var iterator = listOfControllers.iterator();
            boolean obstacleHitPlayer = false;
            while (iterator.hasNext()) {
                final var controller = iterator.next();

                controller.update();

                if (collisionChecker(controller.getObstacleModel().getHitbox(), playerHitbox)
                        && controller.getObstacleModel().getEntityStatus().equals(EntityStatus.ACTIVE)) {
                    obstacleHitPlayer = true;
                    controller.getObstacleModel().setEntityStatus(EntityStatus.DEACTIVATED);
                }

                if (!obstacleGroup.getChildren().contains((Node) controller.getImageView())) {
                    obstacleGroup.getChildren().add((Node) controller.getImageView());
                }

                if (controller.getObstacleModel().getEntityStatus().equals(EntityStatus.INACTIVE)) {
                    obstacleGroup.getChildren().remove((Node) controller.getImageView());
                    iterator.remove();
                }
            }

            // Deactivate all obstacles on screen if one hit the player (give the player a
            // brief moment to focus again)
            /*if (obstacleHitPlayer) {
                iterator = listOfControllers.iterator();
                while (iterator.hasNext()) {
                    var controller = iterator.next();
                    controller.getObstacleModel().changeObstacleStatus(ObstacleStatus.DEACTIVATED);
                }
            }*/
            return obstacleHitPlayer;
        }
    }

    private boolean collisionChecker(final Hitbox hitbox, final Hitbox playerHitbox) {
        for (final var vertex : playerHitbox.getHitboxVertex()) {
            if (hitbox.isTouching(vertex) || hitbox.isTouching(playerHitbox.getHitboxPosition())) {
                return true;
            }
        }
        for (final var vertex : hitbox.getHitboxVertex()) {
            if (playerHitbox.isTouching(vertex) || playerHitbox.isTouching(hitbox.getHitboxPosition())) {
                return true;
            }
        }
        return false;
    }

    public List<ObstacleController> getControllers() {
        return this.listOfControllers;
    }
}
