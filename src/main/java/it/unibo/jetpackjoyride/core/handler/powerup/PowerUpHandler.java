package it.unibo.jetpackjoyride.core.handler.powerup;

import java.util.ArrayList;
import java.util.List;

import it.unibo.jetpackjoyride.core.entities.entity.api.EntityGenerator;
import it.unibo.jetpackjoyride.core.entities.entity.impl.EntityGeneratorImpl;
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp.PowerUpType;
import it.unibo.jetpackjoyride.core.handler.generic.GenericController;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityStatus;
import javafx.scene.Group;
import javafx.scene.Node;

public final class PowerUpHandler {
    private List<GenericController<PowerUp,PowerUpView>> listOfControllers;
    private EntityGenerator entityGenerator;

    public PowerUpHandler() {
        this.listOfControllers = new ArrayList<>();
        entityGenerator = new EntityGeneratorImpl();
    }

    public void update(final Group powerUpGroup, final boolean isSpaceBarPressed) {
        var iterator = listOfControllers.iterator();
        while (iterator.hasNext()) {
            var controller = iterator.next();

            controller.update(isSpaceBarPressed);

            if (!powerUpGroup.getChildren().contains((Node) controller.getImageView())) {
                powerUpGroup.getChildren().add((Node) controller.getImageView());
            }

            if (controller.getEntityModel().getEntityStatus().equals(EntityStatus.INACTIVE)) {
                powerUpGroup.getChildren().remove((Node) controller.getImageView());
                iterator.remove();
            }
        }
    }

    public void spawnPowerUp(final PowerUpType powerUpType) {
        List<GenericController<PowerUp, PowerUpView>> powerup = entityGenerator.generatePowerUp(powerUpType);
        listOfControllers.addAll(powerup);
    }
}
