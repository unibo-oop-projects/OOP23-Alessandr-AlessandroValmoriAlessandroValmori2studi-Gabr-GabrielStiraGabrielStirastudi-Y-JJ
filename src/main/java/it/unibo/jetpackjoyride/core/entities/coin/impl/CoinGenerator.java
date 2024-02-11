package it.unibo.jetpackjoyride.core.entities.coin.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.unibo.jetpackjoyride.core.entities.coin.api.CoinModel;
import it.unibo.jetpackjoyride.core.entities.coin.api.CoinView;
import it.unibo.jetpackjoyride.core.hitbox.impl.CoinsHitbox;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;

public final class CoinGenerator {

    private Canvas canvas;
    private GraphicsContext gc;
    private final List<Coin> coinList = new ArrayList<>();
    private final List<Coin> reusableCoin = new ArrayList<>();
    private CoinShape coinShape;
    private GameInfo gameInfo;

    private double mapHeight;
    private double mapWidth;

    private boolean screenChange;

    public CoinGenerator() {
        this.gameInfo = GameInfo.getInstance();
        this.canvas = new Canvas(gameInfo.getScreenWidth(), gameInfo.getScreenHeight());
        this.gc = canvas.getGraphicsContext2D();
        screenChange = false;
        coinShape = new CoinShape(gameInfo);
    }

    public void startGenerate() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> generateCoin()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void generateCoin() {
        List<Pair<Double, Double>> shapes = coinShape.regularShapes();

        for (Pair<Double, Double> position : shapes) {
            Coin coin;
            if (!reusableCoin.isEmpty()) {
                coin = reusableCoin.remove(0);
                coin.setPosition(position);
            } else {
                CoinModel model = new CoinModelImpl(position, new CoinsHitbox(position, 0.0), 30, 30);
                CoinView view = new CoinViewImpl(model);
                coin = new Coin(model, view, canvas.getGraphicsContext2D());
            }
            coinList.add(coin);
            coin.setVisible(true);
        }

    }

    public void renderCoin() {
        if (screenChange == true) {
            canvas.setHeight(mapHeight);
            canvas.setWidth(mapWidth);
            gc.clearRect(0, 0, mapWidth, mapHeight);
            screenChange = false;
        }
        for (Coin coin : coinList) {
            coin.render();
        }
    }

    public void updatPosition() {

        updateNewPos();

        Iterator<Coin> iterator = coinList.iterator();
        while (iterator.hasNext()) {
            Coin coin = iterator.next();
            coin.update();
            if (isOutofMap(coin.getPosition().get1())) {
                coin.setVisible(false);
                reusableCoin.add(coin);
                iterator.remove();
            }
        }

    }

    private boolean isOutofMap(double x) {
        return x < -gameInfo.getScreenWidth();
    }

    public Canvas getCanvas() {
        return this.canvas;
    }

    private void updateNewPos() {
        if (isScreenSizeChange()) {
            double newWidth = gameInfo.getScreenWidth();
            double newHeight = gameInfo.getScreenHeight();
            double ratioX = newWidth / mapWidth;
            double ratioY = newHeight / mapHeight;

            for (Coin coin : coinList) {
                var oldPosition = coin.getPosition();
                double newX = oldPosition.get1() * ratioX;
                double newY = oldPosition.get2() * ratioY;
                coin.setPosition(new Pair<>(newX, newY));
            }
            mapWidth = newWidth;
            mapHeight = newHeight;

        }

    }

    private boolean isScreenSizeChange() {
        double newWidth = gameInfo.getScreenWidth();
        double newHeight = gameInfo.getScreenHeight();
        screenChange = true;
        return newWidth != mapWidth || newHeight != mapHeight;
    }

}
