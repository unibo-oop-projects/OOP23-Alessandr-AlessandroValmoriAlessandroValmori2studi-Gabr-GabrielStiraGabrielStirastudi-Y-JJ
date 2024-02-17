package it.unibo.jetpackjoyride.core.entities.coin.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import it.unibo.jetpackjoyride.core.entities.coin.api.CoinShapeFactory;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;

public final class CoinShapeFactoryImpl implements CoinShapeFactory {

    private final int SPACE = 50;
    private double minY ;
    private double maxY ; 

    private GameInfo gameInfo;
    private Random random;
    private Map<String,List<Pair<Double, Double>>> cachedRegularShapes;

    public CoinShapeFactoryImpl(final GameInfo gameInfo) {
        this.gameInfo = gameInfo;
        random = new Random();
        minY = gameInfo.getScreenHeight() * 0.1;
        maxY = gameInfo.getScreenHeight() * 0.9; 
        this.cachedRegularShapes = new HashMap<>();
        loadInitialShapes();
    }

    @Override
    public List<Pair<Double, Double>> regularShapes() {
        updateDimension();
        List<String> keys = new ArrayList<>(cachedRegularShapes.keySet());
        String randomKey = keys.get(random.nextInt(keys.size()));
        return randomYwithoutOutofmap(cachedRegularShapes.get(randomKey));
    }

    
    private  List<Pair<Double, Double>> straightLine(int numOfCoins, double startX, double startY) {
                List<Pair<Double, Double>> outlist = new ArrayList<>();
                for (int i = 0; i < numOfCoins; i++) {
                    outlist.add(new Pair<Double, Double>(startX+(i*SPACE), startY));
                }
                return outlist;
            }
    
    private  List<Pair<Double, Double>> multiStraightLine(int numOfCoins, double startX, double startY,int n) {  
                List<Pair<Double, Double>> outlist = 
                new ArrayList<>(straightLine(numOfCoins,startX,startY));
                for(int i = 1 ; i< n ; i++){
                    outlist.addAll(straightLine(numOfCoins, startX, startY+(i*SPACE)));
                }
                return outlist;
            }         
      
    
    private  List<Pair<Double, Double>> stepped(int numOfCoins,int steps,double startX, double startY,boolean up) {
                double posY = startY;
                double posX = startX;
                List<Pair<Double, Double>> outlist = 
                new ArrayList<>(straightLine(numOfCoins,posX,posY));
                for(int i = 0 ; i< steps ; i++){
                    var lastpos = outlist.get(outlist.size()-1);
                    double nextPosY =  up ? lastpos.get2() -SPACE : lastpos.get2() + SPACE;
                    outlist.addAll(straightLine(numOfCoins, lastpos.get1(), nextPosY));
                }
                return outlist;
            }        

    
    private  List<Pair<Double, Double>> prismatic(int numOfCoins,int n) {
                double posX = gameInfo.getScreenWidth();
                double posY = minY + random.nextDouble() * (maxY-minY);
                List<Pair<Double, Double>> outlist = 
                new ArrayList<>(straightLine(numOfCoins,posX,posY));
                var firstPair = outlist.get(0);
                for(int i = 1 ; i< n ; i++){
                   var num = numOfCoins-(i*2);
                   if(num > 0){
                    outlist.addAll(straightLine(num,firstPair.get1()+SPACE*i , firstPair.get2()+SPACE*i));
                    outlist.addAll(straightLine(num,firstPair.get1()+SPACE*i , firstPair.get2()-SPACE*i));
                   }             
                }          
                return outlist;
            }         
       

    private void loadInitialShapes(){
        double posX = gameInfo.getScreenWidth();
        double initialY = 0;
        cachedRegularShapes.put("straightLine", straightLine(10, posX, initialY));
        cachedRegularShapes.put("multiStraightLine", multiStraightLine(8, posX, initialY, 2));
        cachedRegularShapes.put("steppedUp", stepped(3, 4, posX, initialY, true));
        cachedRegularShapes.put("steppedDown", stepped(3, 4, posX, initialY, false));
        cachedRegularShapes.put("prismatic", prismatic(6, 3));
    }


    private List<Pair<Double,Double>> randomYwithoutOutofmap(List<Pair<Double,Double>> cachedShape){
        double minY1 = Double.MAX_VALUE;
        double maxY1 = Double.MIN_VALUE;
        double oldX = cachedShape.get(0).get1();
        for (Pair<Double,Double> pair : cachedShape) {
             minY1 = Math.min(minY1, pair.get2());
             maxY1 = Math.max(maxY1, pair.get2());
        }

        double newStartY = minY + random.nextDouble() * (maxY - minY-(maxY1-minY1));
        double newStartX = gameInfo.getScreenWidth() - oldX;
        List<Pair<Double,Double>> outList = new ArrayList<>();
        for (Pair<Double,Double> pos : cachedShape) {
             outList.add(new Pair<Double,Double>(pos.get1()+newStartX, pos.get2()-minY1+newStartY));
        }
        return outList;
    }


    private void updateDimension(){
        minY = gameInfo.getScreenHeight() * 0.1;
        maxY = gameInfo.getScreenHeight() * 0.9; 
    }
}
    

