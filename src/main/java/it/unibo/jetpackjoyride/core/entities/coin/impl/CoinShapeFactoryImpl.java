package it.unibo.jetpackjoyride.core.entities.coin.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import it.unibo.jetpackjoyride.core.entities.coin.api.CoinShapeFactory;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;

/**
 * The CoinShapeFactoryImpl class implements the CoinShapeFactory interface to generate regular shapes for coins.
 * @author yukai.zhou@studio.unibo.it
 */
public final class CoinShapeFactoryImpl implements CoinShapeFactory {

    private static final int BASE_LENGTH_STRAIGHT_LINE = 10;
    private static final int BASE_LENGTH_MULTI_STRAIGHT_LINE = 8;
    private static final int BASE_NUM_OF_COINS_STEPPED = 3;
    private static final int BASE_STEPPES_STEPPED = 4;
    private static final int BASE_NUM_OF_COINS_PRISMATIC = 6;
    private static final int BASE_LINE_PRISMATIC = 3;
    private static final double MIN_SIZE_RATE = 0.2;
    private static final double MAX_SIZE_RATE = 0.9;
    private static final int SPACECING = 50;
    private double minY;
    private double maxY; 

    private final GameInfo gameInfo;
    private final Random random;
    private final Map<String, List<Pair<Double, Double>>> cachedRegularShapes;

    /**
     * Constructs a CoinShapeFactoryImpl object.
     * Initialize necessary information.
     */
    public CoinShapeFactoryImpl() {
        this.gameInfo = GameInfo.getInstance();
        random = new Random();
        minY = gameInfo.getDefaultHeight() * MIN_SIZE_RATE;
        maxY = gameInfo.getDefaultHeight() * MAX_SIZE_RATE; 
        this.cachedRegularShapes = new HashMap<>();
        loadInitialShapes();
    }

    @Override
    public List<Pair<Double, Double>> regularShapes() {
        final List<String> keys = new ArrayList<>(cachedRegularShapes.keySet());
        final String randomKey = keys.get(random.nextInt(keys.size()));
        return randomYwithoutOutofmap(cachedRegularShapes.get(randomKey));
    }

    /**
     * Generates a straight line shape for coins.
     *
     * @param numOfCoins the number of coins in the straight line
     * @param startX     the x-coordinate of the starting position of the straight line
     * @param startY     the y-coordinate of the starting position of the straight line
     * @return a list of pairs representing the positions of coins in the straight line
     */
    private  List<Pair<Double, Double>> straightLine(final int numOfCoins, final double startX, final double startY) {
                final List<Pair<Double, Double>> outlist = new ArrayList<>();
                for (int i = 0; i < numOfCoins; i++) {
                    outlist.add(new Pair<Double, Double>(startX + (i * SPACECING), startY));
                }
                return outlist;
            }

    /**
     * Generates multiple straight lines for coins.
     *
     * @param numOfCoins the number of coins in each straight line
     * @param startX     the x-coordinate of the starting position of the first straight line
     * @param startY     the y-coordinate of the starting position of the first straight line
     * @param n          the number of straight lines to generate
     * @return a list of pairs representing the positions of coins in the multiple straight lines
     */

    private  List<Pair<Double, Double>> multiStraightLine(final int numOfCoins, 
        final double startX, final double startY, final int n) {
                final List<Pair<Double, Double>> outlist = 
                new ArrayList<>(straightLine(numOfCoins, startX, startY));
                for (int i = 1; i < n; i++) {
                    outlist.addAll(straightLine(numOfCoins, startX, startY + (i * SPACECING)));
                }
                return outlist;
            } 


    /**
     * Generates a stepped shape for coins.
     *
     * @param numOfCoins the number of coins in each step
     * @param steps      the number of steps
     * @param startX     the x-coordinate of the starting position of the stepped shape
     * @param startY     the y-coordinate of the starting position of the stepped shape
     * @param up         a boolean indicating whether the steps go up or down
     * @return a list of pairs representing the positions of coins in the stepped shape
     */
    private  List<Pair<Double, Double>> stepped(final int numOfCoins, final int steps, 
    final double startX, final double startY, final boolean up) {
                final double posY = startY;
                final double posX = startX;
                final List<Pair<Double, Double>> outlist = 
                new ArrayList<>(straightLine(numOfCoins, posX, posY));
                for (int i = 0; i < steps; i++) {
                    final var lastpos = outlist.get(outlist.size() - 1);
                    final double nextPosY =  up ? lastpos.get2() - SPACECING : lastpos.get2() + SPACECING;
                    outlist.addAll(straightLine(numOfCoins, lastpos.get1(), nextPosY));
                }
                return outlist;
            }

    /**
     * Generates a prismatic shape for coins.
     *
     * @param numOfCoins the number of coins in each section of the prismatic shape
     * @param n          the number of sections
     * @return a list of pairs representing the positions of coins in the prismatic shape
     */
    private  List<Pair<Double, Double>> prismatic(final int numOfCoins, final int n) {
                final double posX = gameInfo.getScreenWidth();
                final double posY = minY + random.nextDouble() * (maxY - minY);
                final List<Pair<Double, Double>> outlist = 
                new ArrayList<>(straightLine(numOfCoins, posX, posY));
                final var firstPair = outlist.get(0);
                for (int i = 1; i < n; i++) {
                   final var num = numOfCoins - (i * 2);
                   if (num > 0) {
                    outlist.addAll(straightLine(num, firstPair.get1() + SPACECING * i, firstPair.get2() + SPACECING * i));
                    outlist.addAll(straightLine(num, firstPair.get1() + SPACECING * i, firstPair.get2() - SPACECING * i));
                   }
                }
                return outlist;
            }

    /**
     * Loads initial shapes in cached Map for coins.
     */
    private void loadInitialShapes() {
        final double posX = gameInfo.getScreenWidth();
        final double initialY = 0;
        cachedRegularShapes.put("straightLine", straightLine(BASE_LENGTH_STRAIGHT_LINE, posX, initialY));
        cachedRegularShapes.put("multiStraightLine", multiStraightLine(BASE_LENGTH_MULTI_STRAIGHT_LINE, posX, initialY, 2));
        cachedRegularShapes.
        put("steppedUp", stepped(BASE_NUM_OF_COINS_STEPPED, BASE_STEPPES_STEPPED, posX, initialY, true));
        cachedRegularShapes.
        put("steppedDown", stepped(BASE_NUM_OF_COINS_STEPPED, BASE_STEPPES_STEPPED, posX, initialY, false));
        cachedRegularShapes.put("prismatic", prismatic(BASE_NUM_OF_COINS_PRISMATIC, BASE_LINE_PRISMATIC));
    }


    /**
     * Randomizes the y-coordinate of the coins without going out of the map.
     *
     * @param cachedShape a list of pairs representing the positions of coins in a shape
     * @return a list of pairs representing the positions of coins with randomized y-coordinates
     */
    private List<Pair<Double, Double>> randomYwithoutOutofmap(final List<Pair<Double, Double>> cachedShape) {
        final double minY1 = cachedShape.stream().mapToDouble(p -> p.get2()).min().getAsDouble();
        final double maxY1 = cachedShape.stream().mapToDouble(p -> p.get2()).max().getAsDouble();

        final double oldX = cachedShape.get(0).get1();
        final double newStartY = minY + random.nextDouble() * (maxY - minY - (maxY1 - minY1));
        final double newStartX = gameInfo.getScreenWidth() - oldX;

        return cachedShape.stream()
                          .map(pos -> new Pair<Double, Double>(pos.get1() + newStartX, pos.get2() - minY1 + newStartY))
                          .collect(Collectors.toList());
    }
}
