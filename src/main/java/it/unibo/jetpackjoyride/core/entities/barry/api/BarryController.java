package it.unibo.jetpackjoyride.core.entities.barry.api;

import it.unibo.jetpackjoyride.core.entities.obstacle.impl.ObstacleImpl;
import it.unibo.jetpackjoyride.utilities.Pair;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public interface BarryController {
    
  public void controlPlayer(boolean pressed);

  public Pair<Double, Double> getPos();

  public void updateView(Pane root);

   public ImageView getBarryView();

    public Barry getBarryModel();

}
