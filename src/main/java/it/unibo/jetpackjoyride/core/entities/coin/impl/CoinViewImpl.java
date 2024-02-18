package it.unibo.jetpackjoyride.core.entities.coin.impl;

import java.io.FileNotFoundException;
import java.net.URL;

import it.unibo.jetpackjoyride.core.entities.coin.api.Coin;
import it.unibo.jetpackjoyride.core.entities.coin.api.CoinView;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class CoinViewImpl implements CoinView {

   private int numOfFrames = 35;
   private Image[] coinFrames;
   private Coin controller;
   private int currentCoinframe = 0;

   private boolean isOnScreen;

   public CoinViewImpl(Coin controller) {
      this.controller = controller;
      this.coinFrames = new Image[numOfFrames];
      isOnScreen = false;
      loadCoinImages();
   }

   @Override
   public void renderCoin(GraphicsContext gc) {
      double moveSpeed = GameInfo.moveSpeed.get();
      double posX = controller.getModel().getPosition().get1();
      double posY = controller.getModel().getPosition().get2();
      double width = controller.getModel().getSize().get1();
      double height = controller.getModel().getSize().get2();
      if (isOnScreen) {
         gc.clearRect(posX + moveSpeed, posY, width,height);
         gc.drawImage(coinFrames[currentCoinframe],posX, posY,width, height);
         updateFrame();
      }else{
         gc.clearRect(posX+moveSpeed,posY,width,height);
      }
   }

   @Override
   public void setVisible(boolean isOnScreen) {
      this.isOnScreen = isOnScreen;
   }

   private void loadCoinImages() {
      int index = 0;
      for (int i = 0; i < 7; i++) {
         String path = "sprites/entities/coins/coin" + (i) + ".png";
         try {
            URL coinImageUrl = getClass().getClassLoader().getResource(path);
            if (coinImageUrl == null) {
               throw new FileNotFoundException("Coin Image was not found: " + path);
            }
            String url = coinImageUrl.toExternalForm();
            Image coinImage = new Image(url);
            for (int j = 0; j < 5; j++) {
               coinFrames[index] = coinImage;
               index++;
            }

         } catch (FileNotFoundException e) {
            System.err.println("Error message :" + e.getMessage());
         }
      }

   }

   private void updateFrame() {
      this.currentCoinframe = (currentCoinframe + 1) % coinFrames.length;
   }
}