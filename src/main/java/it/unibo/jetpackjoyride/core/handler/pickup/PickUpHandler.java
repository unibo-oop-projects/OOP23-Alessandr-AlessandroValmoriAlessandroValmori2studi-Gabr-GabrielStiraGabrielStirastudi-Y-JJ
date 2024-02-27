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
import java.util.HashSet;
import java.util.Collections;
import java.util.Optional;

 /**
  * The {@link PickUpHandler} class is one of the main handlers of the game, 
  * which are classes used to manage the various entities in the game.
  * This class in particular deals with the generation, updating and interaction
  * managing of all {@link PickUp} in the game.
  *
  * @author gabriel.stira@studio.unibo.it
  */
public class PickUpHandler {
    /**
     * Defines the base chance a pickup has to be generated at each call of the spawnPickUp() method.
     */
    private static final Integer BASEPICKUPSPAWNCHANCE = 150;
    /**
     * Is used to store all non INACTIVE pickups in game.
     */
    private final List<PickUp> listOfPickUp;
    /**
     * Is used to generate the pickups.
     */
    private final EntityModelGeneratorImpl entityModelGenerator;
    /**
     * Is used to generate random numbers.
     */
    private final Random random;
    /**
     * Is used to keep track of all unlocked items in the shop (since the pickup only generates
     * items which have been bought in the shop).
     */
    private final Set<Items> unlockedItems;

    /**
     * Constructor used to create an instance of PickUpHandler and initialize it.
     * @param unlockedItems
     */
    public PickUpHandler(final Set<Items> unlockedItems) {
        this.unlockedItems = new HashSet<>(unlockedItems);
        this.listOfPickUp = new ArrayList<>();
        this.entityModelGenerator = new EntityModelGeneratorImpl();
        this.random = new Random();
    }

    /**
     * Updates all pickups in the game. If a pickup has a INACTIVE entityStatus,
     * it is removed from the set of all pickups. 
     * If a pickup collides with the hitbox passed as a parameter, the pickup is 
     * also removed, but before, it activates certains effects based on its type.
     * 
     * @param playerHitbox The hitbox of the player/powerup.
     * @return True if the pickup has been collected, false otherwise.
     */
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

            if (model.getEntityStatus().equals(EntityStatus.INACTIVE)) {
                iterator.remove();
            }
        }
        return pickUpPickedUp;
    }

    /**
     * Defines a method which automatically check what type of pickups have been unlocked in the shop
     * and if at least one has, it tries to generate it at each call. A base chance controls the rate at
     * which the pickups can be generated.
     */
    public void spawnPickUp() {
        if (random.nextInt(BASEPICKUPSPAWNCHANCE) != 0) {
            return;
        }

        final List<PickUpType> setOfPossiblePickUps = new ArrayList<>();
        if (this.unlockedItems.stream().filter(p -> p.getCorresponding().isEmpty()).findAny().isPresent()) {
            // Shield
            setOfPossiblePickUps.add(PickUpType.SHIELD);
        }
        if (this.unlockedItems.stream().filter(p -> p.getCorresponding().isPresent()).findAny().isPresent()) {
            // Powerup
            setOfPossiblePickUps.add(PickUpType.VEHICLE);
        }

        Collections.shuffle(setOfPossiblePickUps);
        final PickUpType newTypeOfPickup = setOfPossiblePickUps.get(0);

        listOfPickUp.add(entityModelGenerator.generatePickUp(newTypeOfPickup));

        if (newTypeOfPickup.equals(PickUpType.VEHICLE)) {
            final List<PowerUpType> allPowerUpUnlocked = unlockedItems.stream()
                    .filter(i -> i.getCorresponding().isPresent()).map(p -> p.getCorresponding().get()).toList();
            final PowerUpType powerUpSpawned = allPowerUpUnlocked.get(random.nextInt(allPowerUpUnlocked.size()));

            listOfPickUp.stream().filter(p -> p.getPickUpType().equals(PickUpType.VEHICLE)).forEach(p -> {
                final VehiclePickUp vehiclePickUp = (VehiclePickUp) p;
                vehiclePickUp.setVehicleSpawn(powerUpSpawned);
            });
        }
    }

    /**
     * Gets the list of all non INACTVE pickup in game.
     * @return The list of all non INACTIVE pickups.
     */

    public List<PickUp> getAllPickUps() {
        return Collections.unmodifiableList(this.listOfPickUp);
    }

}
