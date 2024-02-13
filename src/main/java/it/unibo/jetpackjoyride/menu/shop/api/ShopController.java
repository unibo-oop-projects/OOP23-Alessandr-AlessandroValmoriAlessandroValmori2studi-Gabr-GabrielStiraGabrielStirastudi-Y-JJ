package it.unibo.jetpackjoyride.menu.shop.api;

import javafx.scene.Scene;
import java.util.Optional;;

public interface ShopController {

    enum Items{
        MRCUDDLES(20), SHIELD(20), STOMPER(20);

        private int cost;

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

    void equip(Items item);

    int retrieveBalance();

    Optional<Items> getEquipped();

}
