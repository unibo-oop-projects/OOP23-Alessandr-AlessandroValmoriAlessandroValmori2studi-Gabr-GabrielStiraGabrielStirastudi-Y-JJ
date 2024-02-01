package it.unibo.jetpackjoyride.core.entities.barry.impl;

import it.unibo.jetpackjoyride.core.entities.barry.api.Barry;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class PlayerMover {
    
    private Barry model;
    private BarryView view;

    public PlayerMover(){
        this.model= new BarryImpl();
        this.view= new BarryView(model);
    }

    public void move(boolean pressed){
        this.model.move(pressed);
    }

    public void updateView(Pane root) {
      this.view.update(model);
      if(!root.getChildren().contains((Node)this.view.getImageView())) {
     root.getChildren().add((Node)this.view.getImageView());
      }
         
          
    }
    
}
