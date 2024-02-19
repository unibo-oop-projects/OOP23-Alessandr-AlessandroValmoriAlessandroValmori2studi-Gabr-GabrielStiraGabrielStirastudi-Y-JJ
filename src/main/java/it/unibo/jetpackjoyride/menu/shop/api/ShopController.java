package it.unibo.jetpackjoyride.menu.shop.api;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.Set;
import java.util.Optional;

import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp.PowerUpType;


public interface ShopController {

    enum Items{
        MRCUDDLES(20, Optional.of(PowerUpType.MRCUDDLES), Optional.of(1)), 
        SHIELD(20, Optional.empty(), Optional.of(4)), 
        STOMPER(20, Optional.of(PowerUpType.LILSTOMPER), Optional.of(2)), 
        PROFITBIRD(20, Optional.of(PowerUpType.PROFITBIRD), Optional.of(3)),
        DUKE(666, Optional.of(PowerUpType.DUKEFISHRON), Optional.empty());

        private final int cost;
        private final Optional<Integer> order;
        private final Optional<PowerUpType> powerup;

        Items(int cost, Optional<PowerUpType> powerup, Optional<Integer> order){
            this.cost = cost;
            this.powerup = powerup;
            this.order = order;
        }

        public int getItemCost(){
            return this.cost;
        }

        public Optional<PowerUpType> getCorresponding(){
            return this.powerup;
        }

        public Optional<Integer> getOrder(){
            return this.order;
        }
    }

    Scene getScene();

    void backToMenu();

    void buy(Items item);

    void toggleEquipUnequipShield();

    int retrieveBalance();

    int getNumOfShields();

    boolean isShieldEquipped();

    Set<Items> getUnlocked();

    void type(KeyCode code);

    void save();

    

    

}
