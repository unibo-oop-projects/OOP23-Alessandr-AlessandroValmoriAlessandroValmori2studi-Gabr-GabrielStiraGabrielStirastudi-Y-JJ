package it.unibo.jetpackjoyride.menu.shop.api;

import javafx.scene.Scene;
import java.util.Set;


public interface ShopController {

    enum Items{
        MRCUDDLES(20), SHIELD(20), STOMPER(20), PROFITBIRD(20);

        private final int cost;

        Items(int cost){
            this.cost = cost;
        }

        public int getItemCost(){
            return this.cost;
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

    

}
