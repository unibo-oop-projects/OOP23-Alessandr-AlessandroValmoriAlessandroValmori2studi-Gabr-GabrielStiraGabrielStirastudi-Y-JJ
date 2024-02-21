package it.unibo.jetpackjoyride.core.handler.pickup;

import java.util.ArrayList;
import java.util.List;

import it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityStatus;
import it.unibo.jetpackjoyride.core.entities.entity.impl.EntityControllerGeneratorImpl;
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp;
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp.PickUpType;
import it.unibo.jetpackjoyride.core.handler.generic.GenericController;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import javafx.scene.Group;
import javafx.scene.Node;
import java.util.Optional;


public class PickUpHandler {
    private final List<GenericController<PickUp,PickUpView>> listOfControllers;
    private final EntityControllerGeneratorImpl entityGenerator;

    public PickUpHandler() {
        this.listOfControllers = new ArrayList<>();
        entityGenerator = new EntityControllerGeneratorImpl();
    }

    public boolean update(final Group pickUpGroup, final Optional<Hitbox> playerHitbox) {
        final var iterator = listOfControllers.iterator();
        boolean pickUpPickedUp = false;
        while (iterator.hasNext()) {
            final var controller = iterator.next();

            controller.update(false);
            if (playerHitbox.isPresent() && controller.getEntityModel().getHitbox().isTouching(playerHitbox.get())
                && controller.getEntityModel().getEntityStatus().equals(EntityStatus.ACTIVE)) {
                pickUpPickedUp = true;
                controller.getEntityModel().setEntityStatus(EntityStatus.DEACTIVATED);
                
            }

            if (!pickUpGroup.getChildren().contains((Node) controller.getImageView())) {
                pickUpGroup.getChildren().add((Node) controller.getImageView());
            }

            if (controller.getEntityModel().getEntityStatus().equals(EntityStatus.INACTIVE)) {
                pickUpGroup.getChildren().remove((Node) controller.getImageView());
                iterator.remove();
            }
        }
        return pickUpPickedUp;
    }

    public void spawnPickUp(final PickUpType pickUpType) {
        listOfControllers.add( entityGenerator.generatePickUpController(pickUpType));
    }

    public List<GenericController<PickUp, PickUpView>> getAllPickUps() {
        return this.listOfControllers;
    }

}
