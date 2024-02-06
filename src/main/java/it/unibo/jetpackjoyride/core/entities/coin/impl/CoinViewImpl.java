package it.unibo.jetpackjoyride.core.entities.coin.impl;

import java.io.FileNotFoundException;
import java.net.URL;

import it.unibo.jetpackjoyride.core.entities.coin.api.CoinModel;
import it.unibo.jetpackjoyride.core.entities.coin.api.CoinView;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class CoinViewImpl implements CoinView{

   private int numOfFrames = 35;
   private Image[] coinFrames;
   private CoinModel model;
   private int currentCoinframe=0;

   private boolean isOnScreen;

   public CoinViewImpl(CoinModel model){
      this.model = model;
      this.coinFrames = new Image[numOfFrames];
      isOnScreen = false;
      loadCoinImages();
   }

   public void renderCoin(GraphicsContext gc){
       if(isOnScreen){
         double moveSpeed = GameInfo.moveSpeed.get();
         gc.clearRect(model.getPosition().get1()+moveSpeed, model.getPosition().get2(), model.getWidth(), model.getHeight());
         gc.drawImage(coinFrames[currentCoinframe],
         model.getPosition().get1(),
         model.getPosition().get2(), model.getWidth(), model.getHeight());
         updateFrame();
       }    
   }

   public void setVisible(boolean isOnScreen){
       this.isOnScreen = isOnScreen;
   }
     
   private void loadCoinImages(){
      int index = 0;
      for(int i = 0 ; i < 7 ; i++){
         String path = "sprites/entities/coins/coin" + (i) + ".png";
         try {
            URL coinImageUrl = getClass().getClassLoader().getResource(path);
            if(coinImageUrl == null){
                throw new FileNotFoundException("Coin Image was not found: " + path);
            }
            String url = coinImageUrl.toExternalForm();
            Image coinImage = new Image(url);
            for(int j = 0 ; j< 5; j++){
               coinFrames[index] = coinImage;
               index++;
            }
           

        } catch (FileNotFoundException e) {
            System.err.println("Error message :" + e.getMessage());
        }
      }
      
   }

   private void updateFrame(){
      this.currentCoinframe = (currentCoinframe+1)%coinFrames.length;
   }
}