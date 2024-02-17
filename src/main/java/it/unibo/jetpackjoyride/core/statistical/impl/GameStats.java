package it.unibo.jetpackjoyride.core.statistical.impl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Set;

import javax.sound.midi.Soundbank;

import java.util.HashSet;
import java.io.ObjectOutputStream;
import it.unibo.jetpackjoyride.core.statistical.api.GameStatsModel;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController.Items;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import java.util.Collections;

public class GameStats implements GameStatsModel {

    private static final long serialVersionUID = 48181123264L;

    private int bestDistance;
    private int totCoins;
    private int currentDistance;
    private Set<Items> unlockedSet = new HashSet<>();
    private boolean isShieldEquipped;
    private int numOfShields;
    
    public GameStats(){
        this.numOfShields=0;
        this.isShieldEquipped= false;
        this.bestDistance = 0;
        this.currentDistance = 0;
        this.totCoins = 50;
        
    }

    public static void writeToFile(GameStatsModel stats, String filename) throws IOException {
     
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(stats);
        }
    }

  
    public static GameStatsModel readFromFile(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (GameStatsModel) in.readObject();
        }
    }

    public int getTotCoins(){
        return this.totCoins;
    }

    public void updateCoins(int coin){
        this.totCoins = totCoins + coin;
    }

    public int getBestDistance(){
        return bestDistance;
    }

    private void setBestDistance(){
        if(currentDistance > bestDistance){
             bestDistance = currentDistance;
        }
    }

    public void addDistance(){
        this.currentDistance = currentDistance +GameInfo.moveSpeed.get();
    }

    public void updateDate(){
        this.setBestDistance();
        this.currentDistance = 0;
    }

    @Override
    public String toString() {
        return "GameStats{" +
                " best distance=" + bestDistance +
                '}';
    }


    @Override
    public int getcurrentDistance() {
        return this.currentDistance;
    }

    @Override
    public Set<Items> getUnlocked() {
        return this.unlockedSet;
    }

    @Override
    public void unlock(Set<Items> items) {
        this.unlockedSet.addAll(items);
    }

    

    @Override
    public void addShields(int num) {
        System.out.println("CALLED");
        this.numOfShields = num;
    }

    @Override
    public int getNumOfShields() {
        return this.numOfShields;
    }

    @Override
    public boolean isShieldEquipped() {
        return this.isShieldEquipped;
    }

    @Override
    public void setShield(boolean isShieldEquipped) {
        this.isShieldEquipped = isShieldEquipped;
    }

    
    

   
}
