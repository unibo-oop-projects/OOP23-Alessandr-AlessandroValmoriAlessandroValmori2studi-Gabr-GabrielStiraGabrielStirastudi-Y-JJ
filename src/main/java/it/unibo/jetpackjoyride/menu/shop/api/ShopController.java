package it.unibo.jetpackjoyride.menu.shop.api;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.Set;
import java.util.Optional;

import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp.PowerUpType;


public interface ShopController {

    enum Items{
        MRCUDDLES(20, Optional.of(PowerUpType.MRCUDDLES)), 
        SHIELD(20, Optional.empty()), 
        STOMPER(20, Optional.of(PowerUpType.LILSTOMPER)), 
        PROFITBIRD(20, Optional.of(PowerUpType.PROFITBIRD)),
        DUKE(666, Optional.of(PowerUpType.DUKEFISHRON));

        private final int cost;
        private final Optional<PowerUpType> powerup;

        Items(int cost, Optional<PowerUpType> powerup){
            this.cost = cost;
            this.powerup = powerup;
        }

        public int getItemCost(){
            return this.cost;
        }

        public Optional<PowerUpType> getCorresponding(){
            return this.powerup;
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

    

    

}
