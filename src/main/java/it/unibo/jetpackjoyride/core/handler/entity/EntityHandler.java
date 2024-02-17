package it.unibo.jetpackjoyride.core.handler.entity;

import it.unibo.jetpackjoyride.core.entities.barry.impl.PlayerMover;
import it.unibo.jetpackjoyride.core.entities.coin.impl.CoinGenerator;
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp;
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
import javafx.scene.Node;

public class EntityHandler {
    private ObstacleHandler obstacleHandler;
    private PowerUpHandler powerUpHandler;
    private PickUpHandler pickUpHandler;
    private PlayerMover playerHandler;
    private CoinGenerator coinHandler;

    private Set<Items> unlockedPowerUps;

    private boolean isUsingPowerUp;
    private Integer counter;

    public enum Event {
        BARRYHIT, POWERUPHIT, NONE, POWERUPSPAWNED, PICKUPPICKEDUP
    }


    public void initialize(final GameStatsController gameStatsHandler) {
        this.obstacleHandler = new ObstacleHandler();
        this.powerUpHandler = new PowerUpHandler();
        this.pickUpHandler = new PickUpHandler();
        this.playerHandler = new PlayerMover();
        this.coinHandler = new CoinGenerator(playerHandler.getHitbox(),gameStatsHandler.getGameStatsModel());

        this.unlockedPowerUps = gameStatsHandler.getGameStatsModel().getUnlocked();

        this.obstacleHandler.initialize();
        this.isUsingPowerUp = false;
        this.counter = 0;
    }

    public void update(final Group entityGroup, final boolean isSpaceBarPressed) {
        playerHandler.move(isSpaceBarPressed);
        playerHandler.updateView(entityGroup);
        coinHandler.updatPosition();
        coinHandler.renderCoin();

        if (!entityGroup.getChildren().contains((Node) coinHandler.getCanvas())) {
            entityGroup.getChildren().add(coinHandler.getCanvas()); 
        }

        if(!this.isUsingPowerUp && this.counter % 500 == 0) {//Every 500m spawns a pickUp if Barry is not using a powerUp
            this.spawnVehiclePickUp(this.unlockedPowerUps);
        }

        if(this.obstacleHandler.update(entityGroup, isUsingPowerUp ? this.powerUpHandler.getAllPowerUps().get(0).getEntityModel().getHitbox() : playerHandler.getHitbox()).isPresent()) {
            if(this.isUsingPowerUp) {
                this.powerUpHandler.destroyAllPowerUps();
                this.isUsingPowerUp = false;
            }
        }

        this.powerUpHandler.update(entityGroup, isSpaceBarPressed);

        if(this.pickUpHandler.update(entityGroup, playerHandler.getHitbox())) {
            final PickUp pickUpPickedUp = this.pickUpHandler.getAllPickUps().get(0).getEntityModel();

            switch (pickUpPickedUp.getPickUpType()) {
                case VEHICLE:
                    final VehiclePickUp vehiclePickUp = (VehiclePickUp)pickUpPickedUp;
                    this.spawnPowerUp(vehiclePickUp.getVehicleSpawn());
                    this.isUsingPowerUp = true;
                    this.obstacleHandler.deactivateAllObstacles();
                    break;
                default:
                    break;
            }
        }

        this.counter++;
    }

    private void spawnVehiclePickUp(final Set<Items> unlockedPowerUps) {
        if(unlockedPowerUps.isEmpty() || !unlockedPowerUps.stream().filter(i -> i.getCorresponding().isPresent()).findAny().isPresent()) {
            return;
        }

        final List<PowerUpType> listOfPossibleSpawns = unlockedPowerUps.stream().filter(i -> i.getCorresponding().isPresent()).map(p -> p.getCorresponding().get()).collect(Collectors.toList());
        final Integer random = new Random().nextInt(listOfPossibleSpawns.size());

        this.pickUpHandler.spawnVehiclePickUp(listOfPossibleSpawns.get(random));
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
