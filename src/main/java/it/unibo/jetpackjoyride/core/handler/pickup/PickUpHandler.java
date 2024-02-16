package it.unibo.jetpackjoyride.core.handler.pickup;

import java.util.ArrayList;
import java.util.List;

import it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityStatus;
import it.unibo.jetpackjoyride.core.entities.entity.api.EntityGenerator;
import it.unibo.jetpackjoyride.core.entities.entity.impl.EntityGeneratorImpl;
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp;
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp.PickUpType;
import it.unibo.jetpackjoyride.core.handler.generic.GenericController;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.core.movement.MovementImpl;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;

public class PickUpHandler {
    private List<GenericController<PickUp,PickUpView>> listOfControllers;
    private EntityGenerator entityGenerator;

    public PickUpHandler() {
        this.listOfControllers = new ArrayList<>();
        entityGenerator = new EntityGeneratorImpl();
        this.init();
    }

    public boolean update(final Group powerUpGroup, final Hitbox playerHitbox) {
        final var iterator = listOfControllers.iterator();
        boolean pickUpPickedUp = false;
        while (iterator.hasNext()) {
            var controller = iterator.next();

            controller.update(true);

            if (collisionChecker(controller.getEntityModel().getHitbox(), playerHitbox)
                        && controller.getEntityModel().getEntityStatus().equals(EntityStatus.ACTIVE)) {
                            pickUpPickedUp = true;
                    controller.getEntityModel().setEntityStatus(EntityStatus.DEACTIVATED);
                }

            if (!powerUpGroup.getChildren().contains((Node) controller.getImageView())) {
                powerUpGroup.getChildren().add((Node) controller.getImageView());
            }

            if (controller.getEntityModel().getEntityStatus().equals(EntityStatus.INACTIVE)) {
                powerUpGroup.getChildren().remove((Node) controller.getImageView());
                iterator.remove();
            }
        }
        return pickUpPickedUp;
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

    private void init() {
        Double screenSizeX = GameInfo.getInstance().getScreenWidth();
        Double screenSizeY = GameInfo.getInstance().getScreenHeight();

        Movement movement = new MovementImpl(new Pair<>(screenSizeX, screenSizeY/2), new Pair<>(0.0, -10.0),new Pair<>(0.0, 0.0), new Pair<>(0.0, 0.0),List.of());

        PickUp pickUpModel = entityGenerator.generatePickUp(PickUpType.VEHICLE, movement, null);
        Image[] pickUpActualImage = new Image[8];
        for (int i = 0; i < 8; i++) {
            pickUpActualImage[i] = new Image(getClass().getClassLoader()
                    .getResource("sprites/entities/pickups/vehiclepickup/vehiclepickup_" + (i + 1) + ".png")
                    .toExternalForm());
        }
        PickUpView pickUpView = new PickUpView(pickUpActualImage);

        GenericController<PickUp, PickUpView> vehiclePickUp = new GenericController<PickUp,PickUpView>(pickUpModel, pickUpView);

        listOfControllers.add(vehiclePickUp);
    }


}
