package it.unibo.jetpackjoyride.core.handler.pickup;

import java.util.ArrayList;
import java.util.List;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityStatus;
import it.unibo.jetpackjoyride.core.entities.entity.impl.EntityModelGeneratorImpl;
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp;
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp.PickUpType;
import it.unibo.jetpackjoyride.core.entities.pickups.impl.VehiclePickUp;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp.PowerUpType;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController.Items;
import java.util.Random;
import java.util.Set;
import java.util.Collections;
import java.util.Optional;



public class PickUpHandler {
    private final static Integer BASEPICKUPSPAWNCHANCE = 100;
    private final List<PickUp> listOfPickUp;
    private final EntityModelGeneratorImpl entityModelGenerator;
    private final Random random;

    public PickUpHandler() {
        this.listOfPickUp = new ArrayList<>();
        this.entityModelGenerator = new EntityModelGeneratorImpl();
        random = new Random();
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

    public void spawnPickUp(final Set<Items> unlockedItems) {
        if(random.nextInt(BASEPICKUPSPAWNCHANCE) != 0) {
            return;
        }

        final List<PickUpType> setOfPossiblePickUps = new ArrayList<>();
        if(unlockedItems.stream().filter(p -> p.getCorresponding().isEmpty()).findAny().isPresent()) {
            //Shield
            setOfPossiblePickUps.add(PickUpType.SHIELD);
        }
        if(unlockedItems.stream().filter(p -> p.getCorresponding().isPresent()).findAny().isPresent()) {
            //Powerup
            setOfPossiblePickUps.add(PickUpType.VEHICLE);
        }

        Collections.shuffle(setOfPossiblePickUps);
        final PickUpType newTypeOfPickup = setOfPossiblePickUps.get(0);

        listOfPickUp.add(entityModelGenerator.generatePickUp(newTypeOfPickup));

        if(newTypeOfPickup.equals(PickUpType.VEHICLE)) {
            final List<PowerUpType> allPowerUpUnlocked = unlockedItems.stream().filter(i -> i.getCorresponding().isPresent()).map(p -> p.getCorresponding().get()).toList();
            final PowerUpType powerUpSpawned = allPowerUpUnlocked.get(random.nextInt(allPowerUpUnlocked.size()));

            listOfPickUp.stream().filter(p -> p.getPickUpType().equals(PickUpType.VEHICLE)).forEach(p -> { final VehiclePickUp vehiclePickUp = (VehiclePickUp)p; vehiclePickUp.setVehicleSpawn(powerUpSpawned);});
        }
    }




    public List<PickUp> getAllPickUps() {
        return this.listOfPickUp;
    }

}
