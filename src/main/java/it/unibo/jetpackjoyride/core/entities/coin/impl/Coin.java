package it.unibo.jetpackjoyride.core.entities.coin.impl;

import it.unibo.jetpackjoyride.core.entities.coin.api.CoinCotroller;
import it.unibo.jetpackjoyride.core.entities.coin.api.CoinModel;
import it.unibo.jetpackjoyride.core.entities.coin.api.CoinView;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.utilities.Pair;
import javafx.scene.canvas.GraphicsContext;

public final class Coin implements CoinCotroller {

    private CoinModel model;
    private CoinView view;
    private GraphicsContext gc;

    public Coin(CoinModel model, CoinView view, GraphicsContext gc) {
        this.model = model;
        this.view = view;
        this.gc = gc;
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
