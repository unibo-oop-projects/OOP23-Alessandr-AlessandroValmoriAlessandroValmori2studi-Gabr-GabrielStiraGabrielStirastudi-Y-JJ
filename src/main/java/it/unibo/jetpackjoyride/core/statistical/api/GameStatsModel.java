package  it.unibo.jetpackjoyride.core.statistical.api;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController.Items;


import java.io.Serializable;
import java.util.Set;


public interface GameStatsModel extends Serializable {


     int getBestDistance();

     int getcurrentDistance();

     int getTotCoins();

     void updateCoins(int coin);

     void addDistance();
    
     void updateDate();

     Set<Items> getUnlocked();

     void unlock(Set<Items> items);

     void setShield(boolean isShieldEquipped);

     void addShields(int num);

     int getNumOfShields();

     boolean isShieldEquipped();




          
}