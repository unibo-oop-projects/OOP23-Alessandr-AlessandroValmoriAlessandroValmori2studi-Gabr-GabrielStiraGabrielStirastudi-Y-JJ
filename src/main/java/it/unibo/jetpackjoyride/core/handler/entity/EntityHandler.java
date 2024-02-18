package it.unibo.jetpackjoyride.core.handler.entity;

import it.unibo.jetpackjoyride.core.entities.barry.impl.PlayerMover;
import it.unibo.jetpackjoyride.core.entities.coin.impl.CoinGenerator;
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp;
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp.PickUpType;
import it.unibo.jetpackjoyride.core.entities.pickups.impl.VehiclePickUp;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp.PowerUpType;
import it.unibo.jetpackjoyride.core.handler.obstacle.ObstacleHandler;
import it.unibo.jetpackjoyride.core.handler.pickup.PickUpHandler;
import it.unibo.jetpackjoyride.core.handler.powerup.PowerUpHandler;
import it.unibo.jetpackjoyride.core.statistical.api.GameStatsController;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController.Items;

import java.util.*;
import java.util.stream.Collectors;

import javafx.scene.Group;

public class EntityHandler {
    private final Integer BASEPICKUPSPAWNCHANCE = 100;
    private ObstacleHandler obstacleHandler;
    private PowerUpHandler powerUpHandler;
    private PickUpHandler pickUpHandler;
    private PlayerMover playerHandler;
    private CoinGenerator coinHandler;

    private Set<Items> unlockedPowerUps;

    private boolean isUsingPowerUp;
    private boolean isCanvasAdded = false;

    public void initialize(final GameStatsController gameStatsHandler) {
        this.obstacleHandler = new ObstacleHandler();
        this.powerUpHandler = new PowerUpHandler();
        this.pickUpHandler = new PickUpHandler();
        this.playerHandler = new PlayerMover(gameStatsHandler);
        this.coinHandler = new CoinGenerator(playerHandler.getHitbox(),gameStatsHandler.getGameStatsModel());

        this.unlockedPowerUps = gameStatsHandler.getGameStatsModel().getUnlocked();

        this.obstacleHandler.initialize();
        this.isUsingPowerUp = false;
    }

    public void update(final Group entityGroup, final boolean isSpaceBarPressed) {
        playerHandler.move(isSpaceBarPressed);
        playerHandler.updateView(entityGroup);
        coinHandler.updatPosition();
        coinHandler.renderCoin();

        if (!isCanvasAdded) {
            entityGroup.getChildren().add(coinHandler.getCanvas()); 
            isCanvasAdded = true;
        }

        if(!this.isUsingPowerUp && this.pickUpHandler.getAllPickUps().isEmpty()) {
            this.spawnVehiclePickUp(this.unlockedPowerUps);
        }

        var obstacleHit = this.obstacleHandler.update(entityGroup, isUsingPowerUp ? this.powerUpHandler.getAllPowerUps().get(0).getEntityModel().getHitbox() : playerHandler.getHitbox());
        if(obstacleHit.isPresent()) {
            if(this.isUsingPowerUp) {
                this.powerUpHandler.destroyAllPowerUps();
                this.isUsingPowerUp = false;
                this.playerHandler.activate();
                this.coinHandler.setPlayerHitbox(this.playerHandler.getHitbox());
            }
            else{
                this.playerHandler.hit(obstacleHit.get());
            }
        }

        this.powerUpHandler.update(entityGroup, isSpaceBarPressed);

        if(this.pickUpHandler.update(entityGroup, playerHandler.getHitbox())) {
            playerHandler.deactivate();
            final PickUp pickUpPickedUp = this.pickUpHandler.getAllPickUps().get(0).getEntityModel();

            switch (pickUpPickedUp.getPickUpType()) {
                case VEHICLE:
                    final VehiclePickUp vehiclePickUp = (VehiclePickUp)pickUpPickedUp;
                    this.spawnPowerUp(vehiclePickUp.getVehicleSpawn());
                    this.isUsingPowerUp = true;
                    this.obstacleHandler.deactivateAllObstacles();
                    this.coinHandler.setPlayerHitbox(this.powerUpHandler.getAllPowerUps().get(0).getEntityModel().getHitbox());
                    break;
                default:
                    break;
            }
        }
    }

    private void spawnVehiclePickUp(final Set<Items> unlockedPowerUps) {
        Integer random = new Random().nextInt(BASEPICKUPSPAWNCHANCE);
        if(random != 0 || unlockedPowerUps.isEmpty() || !unlockedPowerUps.stream().filter(i -> i.getCorresponding().isPresent()).findAny().isPresent()) {
            return;
        }

        final List<PowerUpType> listOfPossibleSpawns = unlockedPowerUps.stream().filter(i -> i.getCorresponding().isPresent()).map(p -> p.getCorresponding().get()).collect(Collectors.toList());
        random = new Random().nextInt(listOfPossibleSpawns.size());

        this.pickUpHandler.spawnPickUp(PickUpType.VEHICLE);
        final VehiclePickUp vehiclePickUp = (VehiclePickUp)this.pickUpHandler.getAllPickUps().get(0).getEntityModel();
        vehiclePickUp.setVehicleSpawn(listOfPossibleSpawns.get(random));
    }

    private void spawnPowerUp(final PowerUpType powerUpType) {
        this.powerUpHandler.spawnPowerUp(powerUpType);
    }

    public void stop() {
        this.obstacleHandler.over();
        this.coinHandler.stopGenerate();
    }

    public void start(){
        this.obstacleHandler.start();
        this.coinHandler.startGenerate();
    }

    public void reset(){
        this.obstacleHandler.deactivateAllObstacles();
        this.coinHandler.clean();
    }

}
