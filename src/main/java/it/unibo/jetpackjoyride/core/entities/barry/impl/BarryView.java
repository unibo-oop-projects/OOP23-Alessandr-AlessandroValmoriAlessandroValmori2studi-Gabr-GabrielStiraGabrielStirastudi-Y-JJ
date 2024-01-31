package it.unibo.jetpackjoyride.core.entities.barry.impl;


import it.unibo.jetpackjoyride.core.entities.barry.api.BarryController;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BarryView {

     private ImageView imageView;
    private Image image;

    private BarryController controller = new BarryControllerImpl();


    public BarryView() {
        String imagePath = getClass().getClassLoader().getResource("background/background1.png").toExternalForm();
        this.image= new Image(imagePath);
        imageView = new ImageView();
        imageView.setImage(image);
        
    }

     public void updateView() {
        imageView.setX(controller.getPos().get1());
        imageView.setX(controller.getPos().get2());
        

        double width=30;
        double height=50;

        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        imageView.setImage(image);
        

     }

     public ImageView getImageView() {
        return imageView;
    }
}
