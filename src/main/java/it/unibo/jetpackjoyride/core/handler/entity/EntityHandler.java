package it.unibo.jetpackjoyride.core.handler.entity;

import it.unibo.jetpackjoyride.core.entities.barry.api.Barry;
import it.unibo.jetpackjoyride.core.entities.barry.impl.BarryImpl;
import it.unibo.jetpackjoyride.core.entities.coin.impl.CoinGenerator;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityStatus;
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp;
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp.PickUpType;
import it.unibo.jetpackjoyride.core.entities.pickups.impl.VehiclePickUp;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp.PowerUpType;
import it.unibo.jetpackjoyride.core.handler.obstacle.ObstacleHandler;
import it.unibo.jetpackjoyride.core.handler.pickup.PickUpHandler;
import it.unibo.jetpackjoyride.core.handler.powerup.PowerUpHandler;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.hitbox.impl.HitboxImpl;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController.Items;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.MovementChangers;
import it.unibo.jetpackjoyride.utilities.Pair;

import java.util.*;
import java.util.stream.Collectors;

import javafx.scene.Group;

public class EntityHandler {
    private final static Integer BASEPICKUPSPAWNCHANCE = 100;
    private ObstacleHandler obstacleHandler;
    private PowerUpHandler powerUpHandler;
    private PickUpHandler pickUpHandler;
    private Barry player;
    private CoinGenerator coinHandler;

    private Set<Items> unlockedItems;

    private Set<Entity> listOfEntities;

    private boolean isUsingPowerUp;
    private boolean isCanvasAdded;

    public void initialize(final ShopController shopController) {
        this.obstacleHandler = new ObstacleHandler();
        this.powerUpHandler = new PowerUpHandler();
        this.pickUpHandler = new PickUpHandler();
        Movement barryMovement = new Movement.Builder().setPosition(100.0,0.0).setMovementChangers(List.of(MovementChangers.GRAVITY, MovementChangers.BOUNDS)).build();
        Hitbox barryHitbox = new HitboxImpl(barryMovement.getPosition(), new Pair<>(GameInfo.getInstance().getDefaultWidth()/17, GameInfo.getInstance().getScreenHeight()/7), 0.0);
        this.player = new BarryImpl(barryMovement, barryHitbox);
        this.coinHandler = new CoinGenerator(Optional.of(player.getHitbox()));

        this.listOfEntities = new HashSet<>();

        this.unlockedItems = shopController.getUnlocked();

        this.obstacleHandler.initialize();
        this.isUsingPowerUp = false;
    }

    public boolean update(final Group entityGroup, final boolean isSpaceBarPressed) {

        player.update(isSpaceBarPressed);
        if(!player.isAlive()){
            coinHandler.setPlayerHitbox(Optional.empty());
            return false;
        }

        coinHandler.updatPosition();
        coinHandler.renderCoin();

        if (!isCanvasAdded) {
            coinHandler.addCoinsView(entityGroup);
            isCanvasAdded = true;
        }

        if (!this.isUsingPowerUp && this.pickUpHandler.getAllPickUps().isEmpty()) {
            this.spawnVehiclePickUp(this.unlockedItems);
        }

        final var obstacleHit = this.obstacleHandler.update(isUsingPowerUp ? Optional.of(this.powerUpHandler.getAllPowerUps().get(0).getHitbox()): Optional.of(player.getHitbox()));
        
        if (obstacleHit.isPresent()) {
            if (this.isUsingPowerUp) {
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
                    this.spawnPowerUp(vehiclePickUp.getVehicleSpawn());
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
        if(!this.player.getEntityStatus().equals(EntityStatus.INACTIVE)){
        this.listOfEntities.add(this.player);
        }else{
            this.listOfEntities.remove(player);
        }
        return true;
    }

    public Set<Entity> getAllEntities() {
        return this.listOfEntities;
    }

    private void spawnShieldPickUp(final Set<Items> unlockedItems) {
        Integer random = new Random().nextInt(BASEPICKUPSPAWNCHANCE);
        if (random != 0 || unlockedItems.isEmpty()) {
            return;
        }

        final boolean isShieldUnlocked = unlockedItems.stream().filter(i -> i.getCorresponding().isEmpty()).count() > 0;
        if(isShieldUnlocked) {
            this.pickUpHandler.spawnPickUp(PickUpType.SHIELD);
        }
    }

    private void spawnVehiclePickUp(final Set<Items> unlockedPowerUps) {
        Random rand = new Random();
        if (rand.nextInt(BASEPICKUPSPAWNCHANCE) != 0 || unlockedPowerUps.isEmpty()
                || !unlockedPowerUps.stream().filter(i -> i.getCorresponding().isPresent()).findAny().isPresent()) {
            return;
        }
        final List<PowerUpType> listOfPossibleSpawns = unlockedPowerUps.stream().peek(e -> System.out.println(e))
                .filter(i -> i.getCorresponding().isPresent()).map(p -> p.getCorresponding().get())
                .collect(Collectors.toList());
        this.pickUpHandler.spawnPickUp(PickUpType.VEHICLE);
        final VehiclePickUp vehiclePickUp = (VehiclePickUp) this.pickUpHandler.getAllPickUps().get(0);
        vehiclePickUp.setVehicleSpawn(listOfPossibleSpawns.get(rand.nextInt(listOfPossibleSpawns.size())));
    }

    private void spawnPowerUp(final PowerUpType powerUpType) {
        this.powerUpHandler.spawnPowerUp(powerUpType);
    }

    public void stop() {
        this.obstacleHandler.over();
        this.coinHandler.stopGenerate();
    }

    public void start() {
        this.obstacleHandler.start();
        this.coinHandler.startGenerate();
    }

    public void reset() {
        this.obstacleHandler.deactivateAllObstacles();
        this.coinHandler.clean();
    }

}
