package it.unibo.jetpackjoyride.core.entities.coin.impl;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;

import it.unibo.jetpackjoyride.core.entities.coin.api.CoinView;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Implementation of the CoinView interface.
 * @author yukai.zhou@studio.unibo.it
 */
public final class CoinViewImpl implements CoinView {

   private static final int POSITION = 0;
   private static final int SIZE = 1;
   private static final int NUM_OF_FRAMES = 35;
   private static final int NUM_OF_COIN_IMAGES = 7;
   private static final int NUM_OF_FRAMES_FOR_IMAGE = 5;

   private Image[] coinFrames;
   private int currentCoinframe = 0;

   private boolean isOnScreen;

   /**
     * Constructs a CoinViewImpl with the given Coin controller.
     */
   public CoinViewImpl() {
      this.coinFrames = new Image[NUM_OF_FRAMES];
      isOnScreen = false;
      loadCoinImages();
   }

   @Override
   public void renderCoin(final GraphicsContext gc, final List<Pair<Double, Double>> modelData) {
      double moveSpeed = GameInfo.MOVE_SPEED.get();
      double posX = modelData.get(POSITION).get1();
      double posY = modelData.get(POSITION).get2();
      double width = modelData.get(SIZE).get1();
      double height = modelData.get(SIZE).get2();
      if (isOnScreen) {
         gc.clearRect(posX + moveSpeed, posY, width, height);
         gc.drawImage(coinFrames[currentCoinframe], posX, posY, width, height);
         updateFrame();
      } else {
         gc.clearRect(posX + moveSpeed, posY, width, height);
      }
   }

   @Override
   public void setVisible(final boolean isOnScreen) {
      this.isOnScreen = isOnScreen;
   }

   private void loadCoinImages() {
      int index = 0;
      for (int i = 0; i < NUM_OF_COIN_IMAGES; i++) {
         String path = "sprites/entities/coins/coin" + (i) + ".png";
         try {
            URL coinImageUrl = getClass().getClassLoader().getResource(path);
            if (coinImageUrl == null) {
               throw new FileNotFoundException("Coin Image was not found: " + path);
            }
            String url = coinImageUrl.toExternalForm();
            Image coinImage = new Image(url);
            for (int j = 0; j < NUM_OF_FRAMES_FOR_IMAGE; j++) {
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
