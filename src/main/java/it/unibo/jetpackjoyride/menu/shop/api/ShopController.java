package it.unibo.jetpackjoyride.menu.shop.api;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.Set;
import java.util.Optional;

import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp.PowerUpType;


public interface ShopController {

    enum Items{
        MRCUDDLES(20, Optional.of(PowerUpType.MRCUDDLES), Optional.of(0), Optional.of("MR CUDDLES\n Too cool not to buy")), 
        SHIELD(20, Optional.empty(), Optional.of(3), Optional.of("SHIELD\n A consumable equippable shield")), 
        STOMPER(20, Optional.of(PowerUpType.LILSTOMPER), Optional.of(1),Optional.of("STOMPER\n Clumsy but robust vehicle")), 
        PROFITBIRD(20, Optional.of(PowerUpType.PROFITBIRD), Optional.of(2), Optional.of("PROFIT BIRD\n Greedy bird, moves like flappy bird")),
        DUKE(666, Optional.of(PowerUpType.DUKEFISHRON), Optional.empty(), Optional.empty());

        private final int cost;
        private final Optional<Integer> order;
        private final Optional<PowerUpType> powerup;
        private final Optional<String> description;

        Items(final int cost, final Optional<PowerUpType> powerup, final Optional<Integer> order, final Optional<String> description){
            this.cost = cost;
            this.powerup = powerup;
            this.order = order;
            this.description = description;
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

        public Optional<String> getDescription(){
            return this.description;
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
