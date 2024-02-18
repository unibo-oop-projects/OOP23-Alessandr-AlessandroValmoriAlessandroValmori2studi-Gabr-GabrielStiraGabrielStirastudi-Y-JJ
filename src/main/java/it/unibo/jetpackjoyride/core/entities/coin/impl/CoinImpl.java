package it.unibo.jetpackjoyride.core.entities.coin.impl;

import it.unibo.jetpackjoyride.core.entities.coin.api.Coin;
import it.unibo.jetpackjoyride.core.entities.coin.api.CoinModel;
import it.unibo.jetpackjoyride.core.entities.coin.api.CoinView;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.hitbox.impl.HitboxImpl;
import it.unibo.jetpackjoyride.utilities.Pair;
import javafx.scene.canvas.GraphicsContext;

public final class CoinImpl implements Coin {
   
    private final CoinModel model;
    private final CoinView view;
    private final GraphicsContext gc;

    public CoinImpl(Pair<Double,Double> position,HitboxImpl hitbox,  GraphicsContext gc) {
        this.model = new CoinModelImpl(position, hitbox);
        this.view = new CoinViewImpl(this);
        this.gc = gc;
    }
    
    @Override
    public void updateModel() {
        this.model.updateCoinModel();
    }
    @Override
    public void render() {
        if(model.isCollected()){
            view.setVisible(false);
        }else{
            view.setVisible(true);
        }
        this.view.renderCoin(gc);
    }
    @Override
    public CoinModel getModel(){
        return this.model;
    }
    @Override
    public void setPosition(Pair<Double, Double> position) {
        this.model.setPosition(position);
    }
    @Override
    public void setCollectedState(boolean isCollected){
        this.model.setCollectedState(isCollected);
    }

}
