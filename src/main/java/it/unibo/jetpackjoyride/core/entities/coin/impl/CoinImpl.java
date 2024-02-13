package it.unibo.jetpackjoyride.core.entities.coin.impl;

import it.unibo.jetpackjoyride.core.entities.coin.api.Coin;
import it.unibo.jetpackjoyride.core.entities.coin.api.CoinModel;
import it.unibo.jetpackjoyride.core.entities.coin.api.CoinView;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.utilities.Pair;
import javafx.scene.canvas.GraphicsContext;

public final class CoinImpl implements Coin {

    private CoinModel model;
    private CoinView view;
    private GraphicsContext gc;
    private boolean isCollected;

    public CoinImpl(CoinModel model, CoinView view, GraphicsContext gc) {
        this.model = model;
        this.view = view;
        this.gc = gc;
        isCollected = false;
    }

    public void update() {
        this.model.updateCoinModel();
    }

    public void render() {
        this.view.renderCoin(gc);
    }

    public void setPosition(Pair<Double, Double> position) {
        this.model.setPosition(position);
    }

    public boolean isCollected(){
        return this.model.isCollected();
    }

    public void setCollectedState(boolean isCollected){
        this.model.setCollectedState(isCollected);
    }

    public void setVisible(boolean isvisible){
        this.view.setVisible(isvisible);
    }

    public Pair<Double, Double> getPosition() {
        return this.model.getPosition();
    }

    public Hitbox geHitbox(){
        return this.model.geHitbox();
    }

}
