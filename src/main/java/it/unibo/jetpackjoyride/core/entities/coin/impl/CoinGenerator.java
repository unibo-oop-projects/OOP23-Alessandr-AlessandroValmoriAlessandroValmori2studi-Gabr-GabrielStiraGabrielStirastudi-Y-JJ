package it.unibo.jetpackjoyride.core.entities.coin.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import it.unibo.jetpackjoyride.core.entities.coin.api.Coin;
import it.unibo.jetpackjoyride.core.entities.coin.api.CoinShapeFactory;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.hitbox.impl.HitboxImpl;
import it.unibo.jetpackjoyride.core.statistical.api.GameStatsModel;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.util.Duration;

/**
 * The class is responsible for generating coins during gameplay.
 * @author yukai.zhou@studio.unibo.it
 */
public final class CoinGenerator {

    private static final int MAX_REUSABLE_COINS = 50; 
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
    private final GameStatsModel gameStatsModel;
    private final int initialSpeed = GameInfo.moveSpeed.get();
    private final Random random = new Random();

    private Hitbox playeHitbox;

    /**
     * Constructor of the CoinGenerator .
     *
     * @param playeHitbox    the hitbox use to check collision
     * @param gameStatsModel the game statistics infomation
     */
    public CoinGenerator(Hitbox playeHitbox, GameStatsModel gameStatsModel){
        this.gameInfo = GameInfo.getInstance();
        this.playeHitbox = playeHitbox;
        this.gameStatsModel = gameStatsModel;
        this.canvas = new Canvas(gameInfo.getScreenWidth(), gameInfo.getScreenHeight());
        this.coinShapeFactory = new CoinShapeFactoryImpl();
        timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> generateCoin()));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    /**
     * Starts the generation of coins.
     */
    public void startGenerate() {
        timeline.play();
    }

    /**
     * Stops the generation of coins.
     */
    public void stopGenerate() {
        timeline.stop();
    }

    /**
     * Cleans up the generated coins and Canvas.
     */
    public void clean(){
        this.coinList.clear();
        this.canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    /**
     * Sets actual the hitbox of the player.
     *
     * @param playerHitbox the hitbox of the player
     */
    public void setPlayerHitbox(Hitbox playerHitbox){
         this.playeHitbox = playerHitbox;
    }

    /**
     * A method to get the canvas containing the coins.
     *
     * @return the canvas containing the coins
     */
    public Canvas getCanvas(){
        return this.canvas;
    }

    /**
     * The method use to generating coins.
     */
    private void generateCoin() {
        if(generateOrNot()){
            List<Pair<Double, Double>> shapes = coinShapeFactory.regularShapes();
             for (Pair<Double, Double> position : shapes) {
            Coin coin;
            if (!reusableCoin.isEmpty()) {
                coin = reusableCoin.remove(0);
                coin.setPosition(position);
                
            } else {
                coin = new CoinImpl(position, 
                new HitboxImpl(position, new Pair<>(Double.valueOf(COIN_WIDTH), Double.valueOf(COIN_HEIGHT))), 
                canvas.getGraphicsContext2D());
            }
            coinList.add(coin);
        }
        }
    }

    private boolean generateOrNot(){
        double probabilityInfluenBySpeed = PROBABILITY_BASE + (GameInfo.moveSpeed.get()-initialSpeed) * PROBABILITY_RATE;
        return random.nextDouble() < probabilityInfluenBySpeed;
    }

    /**
     * Renders the coins on the canvas.
     */
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

    /**
     * Updates the position of the coins.
     */
    public void updatPosition() {

        updateNewPos();
        checkCollision();

       
        while (reusableCoin.size() > MAX_REUSABLE_COINS) {
            reusableCoin.remove(reusableCoin.size()-1);
        }

        Iterator<Coin> iterator = coinList.iterator();
        while (iterator.hasNext()) {
            Coin coin = iterator.next();
            coin.updateModel();
            if (isOutofMap(coin.getModel().getPosition().get1())) {
                reusableCoin.add(coin);
                coin.setCollectedState(false);
                iterator.remove();
            }
        }
    
    }
    
    /**
     * Updates the position of the coins based on changes in the screen size.
     * If the screen size has changed, adjusts the positions of the coins accordingly.
     */
    private void updateNewPos() {
        if (isScreenSizeChange()) {
            double ratioX = gameInfo.getScreenWidth() / canvas.getWidth();
            double ratioY = gameInfo.getScreenHeight() / canvas.getHeight();

            for (Coin coin : coinList) {
                var oldPosition = coin.getModel().getPosition();
                coin.setPosition(new Pair<>(oldPosition.get1() * ratioX, oldPosition.get2() * ratioY));
            }
        }

    }

    /**
     * Checks if the screen size has changed.
     *
     * @return true if the screen size has changed, false otherwise
     */
    private boolean isScreenSizeChange() {
        return canvas.getWidth() != gameInfo.getScreenWidth() || canvas.getHeight() != gameInfo.getScreenHeight();
    }

    /**
     * Checks for collisions between coins and the player.
     * If a collision occurs, updates the game statistics accordingly.
     */
    private void checkCollision(){
        List<Coin> sortedList = coinList.stream()
                        .filter(p->p.getModel().getPosition().get1() < gameInfo.getScreenWidth()/2)
                        .sorted(Comparator.comparingDouble(p->p.getModel().getPosition().get1()))
                        .collect(Collectors.toList());

        for (Coin coin : sortedList) {
              if(coin.getModel().geHitbox().isTouching(playeHitbox)) {
                if(!coin.getModel().isCollected()){
                    gameStatsModel.updateCoins(1);
                    coin.setCollectedState(true);
                }
            }
        }
    }

    /**
     * Checks if a coin is out of the visible area of the screen.
     *
     * @param x the x-coordinate of the coin
     * @return true if the coin is out of the visible area, false otherwise
     */
    private boolean isOutofMap(double x){
        return x < -gameInfo.getScreenWidth();
    }


}
