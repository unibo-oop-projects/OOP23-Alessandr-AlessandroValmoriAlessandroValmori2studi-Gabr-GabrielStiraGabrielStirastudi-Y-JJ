package it.unibo.jetpackjoyride.core.entities.coin.api;

import java.util.List;

import it.unibo.jetpackjoyride.utilities.Pair;

public interface CoinShapeFactory {

    List<Pair<Double, Double>> regularShapes(int randomNum);

    List<Pair<Double, Double>> straightLine(int numOfCoins,double width, double height);

    List<Pair<Double, Double>> multiStraightLine(int numOfCoins, double width, double height,int n);

    List<Pair<Double, Double>> stepped(int numOfCoins ,int steps,double width, double height,boolean upOrDown);

    List<Pair<Double, Double>> prismatic(int numOfCoins,int n);

 
    
}
