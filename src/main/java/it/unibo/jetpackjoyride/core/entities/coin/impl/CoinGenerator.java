package it.unibo.jetpackjoyride.core.entities.coin.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import it.unibo.jetpackjoyride.core.entities.coin.api.Coin;
import it.unibo.jetpackjoyride.core.entities.coin.api.CoinModel;
import it.unibo.jetpackjoyride.core.entities.coin.api.CoinShapeFactory;
import it.unibo.jetpackjoyride.core.entities.coin.api.CoinView;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.hitbox.impl.CoinsHitbox;
import it.unibo.jetpackjoyride.core.statistical.api.GameStatsModel;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.util.Duration;

public final class CoinGenerator {

    private static final int MAX_REUSABLE_COINS = 100; 
    private static final double PROBABILITY_BASE = 0.3;
    private static final double PROBABILITY_RATE = 0.15;
    private static final int COIN_WIDTH = 30;
    private static final int COIN_HEIGHT = 30;

    private final Canvas canvas;
    private final Timeline timeline;
    private final List<Coin> coinList = new ArrayList<>();
    private final List<Coin> reusableCoin = new ArrayList<>();
    private final CoinShapeFactory coinShapeFactory;
    private final GameInfo gameInfo;
    private final Hitbox playeHitbox;
    private final GameStatsModel gameStatsModel;
    private final int initialSpeed = GameInfo.moveSpeed.get();

    private final Random random = new Random();

    public CoinGenerator(Hitbox playeHitbox, GameStatsModel gameStatsModel){
        this.gameInfo = GameInfo.getInstance();
        this.playeHitbox = playeHitbox;
        this.gameStatsModel = gameStatsModel;
        this.canvas = new Canvas(gameInfo.getScreenWidth(), gameInfo.getScreenHeight());
        this.coinShapeFactory = new CoinShapeFactoryImpl(gameInfo);
        timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> generateCoin()));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void startGenerate() {
        timeline.play();
    }

    public void stopGenerate(){
        timeline.stop();
    }

    private void generateCoin() {
        if(generateOrNot()){
            List<Pair<Double, Double>> shapes = coinShapeFactory.regularShapes();
             for (Pair<Double, Double> position : shapes) {
            Coin coin;
            if (!reusableCoin.isEmpty()) {
                coin = reusableCoin.remove(0);
                coin.setPosition(position);
            } else {
                CoinModel model = new CoinModelImpl(position, new CoinsHitbox(position, 0.0), COIN_WIDTH, COIN_HEIGHT);
                CoinView view = new CoinViewImpl(model);
                coin = new CoinImpl(model, view, canvas.getGraphicsContext2D());
            }
            coinList.add(coin);
            coin.setVisible(true);
        }
        }
    }

    private boolean generateOrNot(){
        double probabilityInfluenBySpeed = PROBABILITY_BASE + (GameInfo.moveSpeed.get()-initialSpeed) * PROBABILITY_RATE;
        System.out.println(probabilityInfluenBySpeed);
        return random.nextDouble() < probabilityInfluenBySpeed;
    }

    public void renderCoin() {
        if (isScreenSizeChange()) {
            canvas.setHeight(gameInfo.getScreenHeight());
            canvas.setWidth(gameInfo.getScreenWidth());
            canvas.getGraphicsContext2D().clearRect(0, 0, gameInfo.getScreenWidth(), gameInfo.getScreenHeight());
        }
        for (Coin coin : coinList) {
            coin.render();
        }
    }

    public void updatPosition() {

        updateNewPos();
        checkCollision();

       
        while (reusableCoin.size() > MAX_REUSABLE_COINS) {
            reusableCoin.remove(reusableCoin.size()-1);
        }

        Iterator<Coin> iterator = coinList.iterator();
        while (iterator.hasNext()) {
            Coin coin = iterator.next();
            coin.update();
            if (isOutofMap(coin.getPosition().get1())) {
                reusableCoin.add(coin);
                coin.setCollectedState(false);
                iterator.remove();
            }
        }
    
    }
    
    public Canvas getCanvas(){
        return this.canvas;
    }

    private void updateNewPos() {
        if (isScreenSizeChange()) {
            double ratioX = gameInfo.getScreenWidth() / canvas.getWidth();
            double ratioY = gameInfo.getScreenHeight() / canvas.getHeight();

            for (Coin coin : coinList) {
                var oldPosition = coin.getPosition();
                coin.setPosition(new Pair<>(oldPosition.get1() * ratioX, oldPosition.get2() * ratioY));
            }
        }

    }

    private boolean isScreenSizeChange() {
        return canvas.getWidth() != gameInfo.getScreenWidth() || canvas.getHeight() != gameInfo.getScreenHeight();
    }

    private void checkCollision(){
        List<Coin> sortedList = coinList.stream()
                        .filter(p->p.getPosition().get1() < gameInfo.getScreenWidth()/2)
                        .sorted(Comparator.comparingDouble(p->p.getPosition().get1()))
                        .collect(Collectors.toList());

        for (Coin coin : sortedList) {
              for (var vertex : playeHitbox.getHitboxVertex()) {
                    if(coin.geHitbox().isTouching(vertex)){
                        coin.setVisible(false);
                        if(!coin.isCollected()){
                            gameStatsModel.updateCoins(1);
                            coin.setCollectedState(true);
                        }
                        break;
                    }
              }
        }
    }

    private boolean isOutofMap(double x){
        return x < -gameInfo.getScreenWidth();
    }


}
