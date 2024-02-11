package it.unibo.jetpackjoyride.core.entities.coin.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;

public final class CoinShape {

    private GameInfo gameInfo;
    private Random random;

    public CoinShape(final GameInfo gameInfo) {
        this.gameInfo = gameInfo;
        random = new Random();
    }

    public List<Pair<Double, Double>> regularShapes() {
        return straightLine();
    }

    private List<Pair<Double, Double>> straightLine() {
        int numOfCoins = 15;
        double posY = gameInfo.getScreenHeight() * 0.9 * random.nextDouble();
        double posX = gameInfo.getScreenWidth();
        List<Pair<Double, Double>> outlist = new ArrayList<>();
        for (int i = 0; i < numOfCoins; i++) {
            outlist.add(new Pair<Double, Double>(posX, posY));
            posX = posX + 50;
        }
        return outlist;
    }

}
