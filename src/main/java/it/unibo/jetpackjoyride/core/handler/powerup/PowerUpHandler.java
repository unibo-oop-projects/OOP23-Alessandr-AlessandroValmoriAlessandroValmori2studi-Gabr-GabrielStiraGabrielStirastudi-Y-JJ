package it.unibo.jetpackjoyride.core.handler.powerup;

import java.util.ArrayList;
import java.util.List;

import it.unibo.jetpackjoyride.core.entities.entity.impl.EntityModelGeneratorImpl;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp.PowerUpType;
import it.unibo.jetpackjoyride.core.entities.entity.api.EntityModelGenerator;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityStatus;

public final class PowerUpHandler {
    private final List<PowerUp> listOfPowerUp;
    private final EntityModelGenerator entityModelGenerator;

    public PowerUpHandler() {
        this.listOfPowerUp = new ArrayList<>();
        this.entityModelGenerator = new EntityModelGeneratorImpl();
    }

    public void update(final boolean isSpaceBarPressed) {
        final var iterator = listOfPowerUp.iterator();
        while (iterator.hasNext()) {
            final var model = iterator.next();

            model.update(isSpaceBarPressed);

            if (model.getEntityStatus().equals(EntityStatus.INACTIVE)) {
                iterator.remove();
            }
        }
    }

    public void spawnPowerUp(final PowerUpType powerUpType) {
        final List<PowerUp> powerup = this.entityModelGenerator.generatePowerUp(powerUpType);
        this.listOfPowerUp.addAll(powerup);
    }

    public List<PowerUp> getAllPowerUps() {
        return this.listOfPowerUp;
    }

    public void destroyAllPowerUps() {
        this.listOfPowerUp.forEach(model -> model.setEntityStatus(EntityStatus.INACTIVE));
    }
}
