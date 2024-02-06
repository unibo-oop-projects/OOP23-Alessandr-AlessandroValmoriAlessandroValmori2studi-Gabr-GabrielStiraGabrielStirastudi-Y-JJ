package it.unibo.jetpackjoyride.core.entities.coin.impl;

import it.unibo.jetpackjoyride.core.entities.coin.api.CoinModel;
import it.unibo.jetpackjoyride.core.hitbox.impl.CoinsHitbox;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;

public class CoinModelImpl implements CoinModel{

    private Pair<Double,Double> position;
    private CoinsHitbox hitbox;
    private int moveSpeed;
    private double coinHeight;
    private double coinWidth;
    
    public CoinModelImpl(Pair<Double,Double> position, CoinsHitbox hitbox ,double coinHeight, double coinWidth){
            this.position = position;
            this.hitbox = hitbox;
            this.coinHeight = coinHeight;
            this.coinWidth = coinWidth;
            moveSpeed = GameInfo.moveSpeed.get();
    }

    public void updateCoinModel(){
        this.position = new Pair<Double,Double>(position.get1()-moveSpeed, position.get2());
    }

    public Pair<Double,Double> getPosition(){
        return position;
    }

    public void setPosition( Pair<Double,Double> position){
        this.position = position;
    }

    public double getHeight(){
        return this.coinHeight;
    }

    public double getWidth(){
        return this.coinWidth;
    }
}
