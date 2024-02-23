package it.unibo.jetpackjoyride.core.handler.powerup;

import java.util.ArrayList;
import java.util.List;

import it.unibo.jetpackjoyride.core.entities.entity.impl.EntityControllerGeneratorImpl;
import it.unibo.jetpackjoyride.core.entities.entity.impl.EntityModelGeneratorImpl;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp.PowerUpType;
import it.unibo.jetpackjoyride.core.handler.generic.GenericController;
import it.unibo.jetpackjoyride.core.entities.entity.api.EntityModelGenerator;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityStatus;
import javafx.scene.Group;
import javafx.scene.Node;

public final class PowerUpHandler {
    private final List<GenericController<PowerUp,PowerUpView>> listOfControllers;
    private final EntityModelGenerator entityModelGenerator;
    private final EntityControllerGeneratorImpl entityControllerGenerator;

    public PowerUpHandler() {
        this.listOfControllers = new ArrayList<>();
        entityControllerGenerator = new EntityControllerGeneratorImpl();
        this.entityModelGenerator = new EntityModelGeneratorImpl();
    }

    public void update(final Group powerUpGroup, final boolean isSpaceBarPressed) {
        final var iterator = listOfControllers.iterator();
        while (iterator.hasNext()) {
            final var controller = iterator.next();

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
        final List<PowerUp> listOfPowerUp = this.entityModelGenerator.generatePowerUp(powerUpType);
        final List<GenericController<PowerUp, PowerUpView>> powerup = entityControllerGenerator.generatePowerUpControllers(listOfPowerUp);
        listOfControllers.addAll(powerup);
    }

    public List<GenericController<PowerUp, PowerUpView>> getAllPowerUps() {
        return this.listOfControllers;
    }

    public void destroyAllPowerUps() {
        this.listOfControllers.forEach(controller -> controller.getEntityModel().setEntityStatus(EntityStatus.INACTIVE));
    }
}
