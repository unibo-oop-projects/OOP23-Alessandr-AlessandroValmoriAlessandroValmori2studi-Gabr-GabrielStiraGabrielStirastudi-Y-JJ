package it.unibo.jetpackjoyride.core.entities.coin.impl;

import java.util.List;

import it.unibo.jetpackjoyride.core.entities.coin.api.Coin;
import it.unibo.jetpackjoyride.core.entities.coin.api.CoinModel;
import it.unibo.jetpackjoyride.core.entities.coin.api.CoinView;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.hitbox.impl.HitboxImpl;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;
import javafx.scene.canvas.GraphicsContext;

/**
 * Implementation of the Coin Controller interface.
 * @author yukai.zhou@studio.unibo.it
 */
public final class CoinImpl implements Coin {

    private static final int ONE_COIN = 1;
    private static final int NO_COINS = 0;

    private final CoinModel model;
    private final CoinView view;

    /**
     * Constructs a CoinImpl with the given position, hitbox, and graphics context.
     *
     * @param position the position of the coin
     * @param hitbox the hitbox of the coin
     */
    public CoinImpl(final Pair<Double, Double> position, final HitboxImpl hitbox) {
        this.model = new CoinModelImpl(position, hitbox);
        this.view = new CoinViewImpl();
    }

    @Override
    public void updateModel() {
        final Pair<Double, Double> currentPosition = this.model.getPosition();
        final Pair<Double, Double> newPosition = new Pair<>(
            currentPosition.get1() - GameInfo.MOVE_SPEED.get(), 
            currentPosition.get2()
        );
        this.model.setPosition(newPosition);
        this.model.geHitbox().updateHitbox(newPosition, 0.0);
    }

    @Override
    public void render(final GraphicsContext gc) {
        if (model.isCollected()) {
            view.setVisible(false);
        } else {
            view.setVisible(true);
        }
        this.view.renderCoin(gc, this.getModelData());
    }
    @Override
    public  List<Pair<Double, Double>> getModelData() {
        return List.of(model.getPosition(), model.getSize(), 
        model.geHitbox().getHitboxPosition(), model.geHitbox().getHitboxPosition());
    }

    @Override
    public void setPosition(final Pair<Double, Double> position) {
        this.model.setPosition(position);
    }

    @Override
    public Integer checkCollision(final Hitbox playHitbox) {
        if (model.geHitbox().isTouching(playHitbox) && !model.isCollected()) {
                model.setCollectedState(true);
                return ONE_COIN;
        }
        return NO_COINS;
    }

    @Override
    public void setCollectedState(final boolean isCollected) {
           this.model.setCollectedState(isCollected);
    }
}
