package it.unibo.jetpackjoyride.core.entities.barry.impl;

import it.unibo.jetpackjoyride.core.entities.barry.api.Barry;
import it.unibo.jetpackjoyride.core.entities.barry.api.BarryController;
import it.unibo.jetpackjoyride.core.entities.obstacle.impl.ObstacleImpl;
import it.unibo.jetpackjoyride.utilities.Pair;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


public class BarryControllerImpl implements BarryController{

    private Barry barryModel = new BarryImpl();
    private BarryView barryView= new BarryView();

    @Override
    public void controlPlayer(boolean pressed) {
    if(pressed){
        this.barryModel.propel();
    }
    else{
        this.barryModel.fall();
    }


    }
    @Override
    public Pair<Double, Double> getPos(){
        return this.barryModel.getPosition();
    }

    public void updateView(Pane root) {
        root.getChildren().add((Node)barryView.getImageView());
        this.barryView.updateView();
    }

    
    public ImageView getBarryView() {
        return this.barryView.getImageView();
    }

    public Barry getBarryModel() {
        return this.barryModel;
    }
    

    
    
}
