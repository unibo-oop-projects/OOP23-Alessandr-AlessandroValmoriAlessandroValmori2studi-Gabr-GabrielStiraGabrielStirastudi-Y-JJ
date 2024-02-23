package it.unibo.jetpackjoyride.core.handler.pickup;

import java.util.ArrayList;
import java.util.List;

import it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityStatus;
import it.unibo.jetpackjoyride.core.entities.entity.impl.EntityModelGeneratorImpl;
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp;
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp.PickUpType;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import java.util.Optional;


public class PickUpHandler {
    private final List<PickUp> listOfPickUp;
    private final EntityModelGeneratorImpl entityModelGenerator;

    public PickUpHandler() {
        this.listOfPickUp = new ArrayList<>();
        this.entityModelGenerator = new EntityModelGeneratorImpl();
    }

    public boolean update(final Optional<Hitbox> playerHitbox) {
        final var iterator = listOfPickUp.iterator();
        boolean pickUpPickedUp = false;
        while (iterator.hasNext()) {
            final var model = iterator.next();

            model.update(false);
            if (playerHitbox.isPresent() && model.getHitbox().isTouching(playerHitbox.get())
                && model.getEntityStatus().equals(EntityStatus.ACTIVE)) {
                pickUpPickedUp = true;
                model.setEntityStatus(EntityStatus.DEACTIVATED);
            }

            if (model.getEntityStatus().equals(EntityStatus.INACTIVE) ) {
                iterator.remove();
            }
        }
        return pickUpPickedUp;
    }

    public void spawnPickUp(final PickUpType pickUpType) {
        listOfPickUp.add(entityModelGenerator.generatePickUp(pickUpType));
    }

    public List<PickUp> getAllPickUps() {
        return this.listOfPickUp;
    }

}
