package  it.unibo.jetpackjoyride.core.statistical.api;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController.Items;


import java.io.Serializable;


public interface GameStatsModel extends Serializable {


     int getBestDistance();

     int getcurrentDistance();

     int getTotCoins();

     void updateCoins(int coin);

     void addDistance();
    
     void updateDate();

     void setEquipped(Items item);

     Items getEquipped();


          
}