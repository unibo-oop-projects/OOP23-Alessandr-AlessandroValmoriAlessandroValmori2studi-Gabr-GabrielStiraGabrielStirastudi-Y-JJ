package it.unibo.jetpackjoyride.core.entities.coin.impl;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.logging.Logger;

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

   private static final Logger LOGGER = Logger.getLogger(CoinViewImpl.class.getName());

   private static final int POSITION = 0;
   private static final int SIZE = 1;
   private static final int NUM_OF_FRAMES = 35;
   private static final int NUM_OF_COIN_IMAGES = 7;
   private static final int NUM_OF_FRAMES_FOR_IMAGE = 5;
   private static final double CLEAN_RATIO = 1.5;

   private Image[] coinFrames;
   private int currentCoinframe;

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
      final double canvasWidth = gc.getCanvas().getWidth();
      final double canvasHeight = gc.getCanvas().getHeight();

      final double minScreenWidth = GameInfo.getInstance().getDefaultHeight();
      final double minScreenHeight = GameInfo.getInstance().getDefaultHeight();

      final double widthRatio = canvasWidth / minScreenWidth;
      final double heightRatio = canvasHeight / minScreenHeight;
      final double minRatio = Math.min(widthRatio, heightRatio);
      final double moveSpeed = GameInfo.MOVE_SPEED.get();

      final double size = modelData.get(SIZE).get1() * minRatio;

      final double posX = modelData.get(POSITION).get1() * minRatio;
      final double posY = modelData.get(POSITION).get2() * minRatio;
      final boolean isOutofScreen = !(posX + size <= canvasWidth && posY + size <= canvasHeight && posX >= 0 && posY >= 0);

      if (isOnScreen && !isOutofScreen) {
         gc.clearRect(posX + moveSpeed, posY, size * CLEAN_RATIO, size * CLEAN_RATIO);
         gc.drawImage(coinFrames[currentCoinframe], posX, posY, size, size);
         updateFrame();
      } else {
         gc.clearRect(posX + moveSpeed, posY, size * CLEAN_RATIO, size * CLEAN_RATIO);
      }
   }

   @Override
   public void setVisible(final boolean isOnScreen) {
      this.isOnScreen = isOnScreen;
   }

   private void loadCoinImages() {
      int index = 0;
      for (int i = 0; i < NUM_OF_COIN_IMAGES; i++) {
         final String path = "sprites/entities/coins/coin" + (i) + ".png";
         try {
            final URL coinImageUrl = getClass().getClassLoader().getResource(path);
            if (coinImageUrl == null) {
               throw new FileNotFoundException("Coin Image was not found: " + path);
            }
            final String url = coinImageUrl.toExternalForm();
            final Image coinImage = new Image(url);
            for (int j = 0; j < NUM_OF_FRAMES_FOR_IMAGE; j++) {
               coinFrames[index] = coinImage;
               index++;
            }

         } catch (FileNotFoundException e) {
            LOGGER.severe("Error message: " + e.getMessage());
         }
      }

   }

   private void updateFrame() {
      this.currentCoinframe = (currentCoinframe + 1) % coinFrames.length;
   }
}
