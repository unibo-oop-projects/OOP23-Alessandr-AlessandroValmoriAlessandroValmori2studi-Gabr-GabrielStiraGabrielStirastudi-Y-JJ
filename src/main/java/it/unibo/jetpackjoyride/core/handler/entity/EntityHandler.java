package it.unibo.jetpackjoyride.core.handler.entity;

import it.unibo.jetpackjoyride.core.GameLoop;
import it.unibo.jetpackjoyride.core.entities.barry.api.Barry;
import it.unibo.jetpackjoyride.core.entities.coin.impl.CoinGenerator;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import it.unibo.jetpackjoyride.core.entities.entity.api.EntityModelGenerator;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityStatus;
import it.unibo.jetpackjoyride.core.entities.entity.impl.EntityModelGeneratorImpl;
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp;
import it.unibo.jetpackjoyride.core.entities.pickups.impl.VehiclePickUp;
import it.unibo.jetpackjoyride.core.handler.obstacle.ObstacleHandler;
import it.unibo.jetpackjoyride.core.handler.pickup.PickUpHandler;
import it.unibo.jetpackjoyride.core.handler.powerup.PowerUpHandler;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController.Items;
import java.util.Set;
import java.util.HashSet;
import java.util.Optional;
import javafx.scene.Group;
import java.util.Collections;

/**
 * The {@link EntityHandler} class acts as a handler
 * for the other handlers ({@link ObstacleHandler}, {@link PowerupHandler},
 * {@link PickupHandler}),
 * the {@link CoinGenerator} and {@link Barry}.
 * It is responsible for managing the interactions between these classes, and
 * can also
 * notify the {@link GameLoop} if the game is over.
 * Entity handler represents the core of the game's entity managing
 * system.
 *
 * @author alessandro.valmori2@studio.unibo.it
 * @author gabriel.stira@studio.unibo.it
 * @author yukai.zhou@studio.unibo.it
 * 
 */
public class EntityHandler {
    /**
     * {@link ObstacleHandler}, the handler responsible for
     * obstacle loading, updating and collision.
     */
    private ObstacleHandler obstacleHandler;
    /**
     * {@link PowerUpHandler}, the handler responsible for creating,
     * updating and destroying powerups.
     */
    private PowerUpHandler powerUpHandler;
    /**
     * {@link PickUpHandler}, the handler responsible for creating and
     * updating. It is also responsible for handling collisions with playable
     * entities.
     */
    private PickUpHandler pickUpHandler;
    /** The Barry entity. */
    private Barry player;

    /** The class responsible for coin generation, collection and positioning. */
    private CoinGenerator coinHandler;

    /**
     * The set of unlocked items, retrieved by the {@link ShopController}
     * in order to assert the spawnable powerups/pickups.
     */
    private Set<Items> unlockedItems;

    /**
     * The set of the currently ACTIVE enitities
     * composed by the union of all the other handlers'
     * ACTIVE entities.
     */
    private Set<Entity> listOfEntities;
    /**
     * Wether the player is using a powerup.
     */
    private boolean isUsingPowerUp;

    /**
     * Wether the coins are already attached.
     */
    private boolean isCanvasAdded;

    private static final int TICKS_BEFORE_GAME_OVER = 100;

    private int ticks;

    /**
     * An initialization method which instanciates all other
     * sub-handlers.
     * 
     * @param shopController the {@link ShopController} passed in order to get the
     *                       set of unlocked {@link Items}.
     */

    public void initialize(final ShopController shopController) {
        final EntityModelGenerator entityGenerator = new EntityModelGeneratorImpl();
        this.unlockedItems = shopController.getUnlocked();
        this.listOfEntities = new HashSet<>();
        this.obstacleHandler = new ObstacleHandler();
        this.powerUpHandler = new PowerUpHandler();
        this.pickUpHandler = new PickUpHandler(this.unlockedItems);
        this.player = entityGenerator.generateBarry();
        this.coinHandler = new CoinGenerator(Optional.of(player.getHitbox()));
        this.isUsingPowerUp = false;
        ticks = 0;
    }

    /**
     * The main update method of this class, which gets executed
     * every frame.
     * It is responsible of updating all other handlers all together
     * 
     * @param entityGroup       the entityGroup passed to {@link CoinGenerator}
     * @param isSpaceBarPressed the boolean parameter passed to the handlers
     * @return wether false if the game is over, true otherwise.
     */
    public boolean update(final Group entityGroup, final boolean isSpaceBarPressed) {

        if (!player.isAlive()) {
            coinHandler.setPlayerHitbox(Optional.empty());
            this.ticks++;

            if (this.ticks >= TICKS_BEFORE_GAME_OVER) {
                return false; // GAME OVER
            }
        } else {
            player.update(isSpaceBarPressed);
        }
        coinHandler.updateCoin();
        if (!isCanvasAdded) {
            coinHandler.addCoinsView(entityGroup);
            isCanvasAdded = true;
        }
        if (!this.isUsingPowerUp && !this.player.hasShield() && this.pickUpHandler.getAllPickUps().isEmpty()
                && !this.unlockedItems.isEmpty()) {
            this.pickUpHandler.spawnPickUp();
        }
        final var obstacleHit = this.obstacleHandler
                .update(isUsingPowerUp ? Optional.of(this.powerUpHandler.getAllPowerUps().get(0).getHitbox())
                        : Optional.of(player.getHitbox()));

        if (obstacleHit.isPresent()) {
            if (this.isUsingPowerUp) {
                this.player.setEntityMovement(this.powerUpHandler.getAllPowerUps().get(0).getEntityMovement());
                this.powerUpHandler.destroyAllPowerUps();
                this.isUsingPowerUp = false;
                this.player.setEntityStatus(EntityStatus.ACTIVE);
                this.coinHandler.setPlayerHitbox(Optional.of(this.player.getHitbox()));

            } else {
                this.player.hit(obstacleHit.get());
            }
        }

        this.powerUpHandler.update(isSpaceBarPressed);

        if (this.pickUpHandler.update(Optional.of(player.getHitbox()))) {

            final PickUp pickUpPickedUp = this.pickUpHandler.getAllPickUps().get(0);

            switch (pickUpPickedUp.getPickUpType()) {
                case VEHICLE:
                    player.setEntityStatus(EntityStatus.INACTIVE);
                    final VehiclePickUp vehiclePickUp = (VehiclePickUp) pickUpPickedUp;
                    this.powerUpHandler.spawnPowerUp(vehiclePickUp.getVehicleSpawn());
                    this.isUsingPowerUp = true;
                    this.obstacleHandler.deactivateAllObstacles();
                    this.coinHandler.setPlayerHitbox(
                            Optional.of(this.powerUpHandler.getAllPowerUps().get(0).getHitbox()));
                    break;
                case SHIELD:

                    this.player.setShieldOn();

                default:
                    break;
            }
        }
        this.listOfEntities.clear();
        this.listOfEntities.addAll(this.powerUpHandler.getAllPowerUps());
        this.listOfEntities.addAll(this.pickUpHandler.getAllPickUps());
        this.listOfEntities.addAll(this.obstacleHandler.getAllObstacles());
        if (!this.player.getEntityStatus().equals(EntityStatus.INACTIVE)) {
            this.listOfEntities.add(this.player);
        } else {
            this.listOfEntities.remove(player);
        }
        return true;
    }

    /**
     * Retrieves the set of all the ACTIVE entities.
     * 
     * @return the set of all the ACTIVE entities.
     */
    public Set<Entity> getAllEntities() {
        return Collections.unmodifiableSet(this.listOfEntities);
    }

    /**
     * Stops the threads executing in {@link ObstacleHandker}
     * and in {@link CoinGenerator}.
     */
    public void stop() {
        this.obstacleHandler.over();
        this.coinHandler.stopGenerate();
    }

    /**
     * Starts the threads executing in {@link ObstacleHandler}
     * and in {@link CoinGenerator}.
     */
    public void start() {
        this.obstacleHandler.start();
        this.coinHandler.startGenerate();
    }

    /**
     * Resets {@link ObstacleHandler}
     * and {@link CoinGenerator}.
     */
    public void reset() {
        this.obstacleHandler.deactivateAllObstacles();
        this.coinHandler.clean();
    }

}
