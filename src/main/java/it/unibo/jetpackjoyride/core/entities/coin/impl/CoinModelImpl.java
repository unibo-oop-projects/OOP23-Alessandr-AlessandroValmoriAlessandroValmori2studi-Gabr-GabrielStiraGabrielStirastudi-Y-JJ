package it.unibo.jetpackjoyride.core.entities.coin.impl;

import it.unibo.jetpackjoyride.core.entities.coin.api.CoinModel;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.hitbox.impl.CoinsHitbox;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;

public class CoinModelImpl implements CoinModel {

    private Pair<Double,Double> position;
    private CoinsHitbox coinHitbox;
    private int moveSpeed;
    private double coinHeight;
    private double coinWidth;
    private boolean isCollected;
    
    public CoinModelImpl(Pair<Double,Double> position, CoinsHitbox hitbox,double coinHeight, double coinWidth){
            this.position = position;
            this.coinHitbox = hitbox;
            this.coinHeight = coinHeight;
            this.coinWidth = coinWidth;
            this.isCollected = false;
            moveSpeed = GameInfo.moveSpeed.get();
    }

    public void updateCoinModel(){
        this.position = new Pair<Double,Double>(position.get1()-GameInfo.moveSpeed.get(), position.get2());
        coinHitbox.updateHitbox(position, 0.0);
    }

    public boolean isCollected(){
        return this.isCollected;
    }

    public void setCollectedState(boolean isCollected){
        this.isCollected = isCollected;
    }
  
    @Override
    public Pair<Double, Double> getPosition() {
        return position;
    }

    public void setPosition(Pair<Double,Double> position){
        this.position = position;
        this.coinHitbox.updateHitbox(position, 0.0);
    }
    @Override
    public double getHeight() {
        return this.coinHeight;
    }
    @Override
    public double getWidth() {
        return this.coinWidth;
    }

    public Hitbox geHitbox(){
        return this.coinHitbox;
    }

}
