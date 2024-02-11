package  it.unibo.jetpackjoyride.core.statistical.api;


import java.io.Serializable;


public interface GameStatsModel extends Serializable {


     int getBestDistance();

     int getcurrentDistance();

     int getTotCoins();

     void updateCoins(int coin);

     void addDistance();
    
     void updateDate();
          
}