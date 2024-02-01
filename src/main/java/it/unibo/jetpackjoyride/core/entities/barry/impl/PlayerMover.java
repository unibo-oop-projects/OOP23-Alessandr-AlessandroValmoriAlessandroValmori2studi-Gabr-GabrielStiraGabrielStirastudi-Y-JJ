package it.unibo.jetpackjoyride.core.entities.barry.impl;

import it.unibo.jetpackjoyride.core.entities.barry.api.Barry;
import it.unibo.jetpackjoyride.core.entities.obstacle.impl.ObstacleImpl;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class PlayerMover {

    private Barry model;
    private BarryView view;
    private Image[] images;
    private boolean playerStatusChanged = false;

    public PlayerMover() {
        this.model = new BarryImpl();
        this.images = this.imagesArray();
        this.view = new BarryView(this.getSpritesForStatus());
    }



    private Image[] imagesArray() {

        int index = 0;

        Image[] images = new Image[140]; // STORES ALL THE PLAYER SPRITES

        // 0 - 27 burned
        // 28 -41 jump
        // 42 - 55 fall
        // 56 - 83 land
        // 84 - 111 walk
        // 111 - 139 zapped

        for (int i = 0; i < 4; i++) { // 28, barryBurned
            String imagePath = getClass().getClassLoader()
                    .getResource("sprites/entities/player/barryburned" + (i + 1) + ".png").toExternalForm();

            for (int j = 0; j < 7; j++) {
                images[index] = new Image(imagePath);
                index++;
            }
        }

        for (int i = 0; i < 2; i++) { // 14, barryjump
            String imagePath = getClass().getClassLoader()
                    .getResource("sprites/entities/player/barryjump" + (i + 1) + ".png").toExternalForm();

            for (int j = 0; j < 7; j++) {
                images[index] = new Image(imagePath);
                index++;
            }
        }

        for (int i = 0; i < 2; i++) { // 14, barryfall
            String imagePath = getClass().getClassLoader()
                    .getResource("sprites/entities/player/barryfall" + (i + 1) + ".png").toExternalForm();

            for (int j = 0; j < 7; j++) {
                images[index] = new Image(imagePath);
                index++;
            }
        }

        for (int i = 0; i < 4; i++) { // 28, barryland
            String imagePath = getClass().getClassLoader()
                    .getResource("sprites/entities/player/barryland" + (i + 1) + ".png").toExternalForm();

            for (int j = 0; j < 7; j++) {
                images[index] = new Image(imagePath);
                index++;
            }
        }

        for (int i = 0; i < 4; i++) { // 28, barrywalk
            String imagePath = getClass().getClassLoader()
                    .getResource("sprites/entities/player/barrywalk" + (i + 1) + ".png").toExternalForm();

            for (int j = 0; j < 7; j++) {
                images[index] = new Image(imagePath);
                index++;
            }
        }

        for (int i = 0; i < 4; i++) { // 28, barryzapped
            String imagePath = getClass().getClassLoader()
                    .getResource("sprites/entities/player/barryzapped" + (i + 1) + ".png").toExternalForm();

            for (int j = 0; j < 7; j++) {
                images[index] = new Image(imagePath);
                index++;
            }
        }

        return images;

    }

    private Image[] getSpritesForStatus(){

        Image[] actualImages;
        int i=0;
        int k=0;

        switch(this.model.getBarryStatus()){

            case WALKING: 

            //84-111 
            actualImages = new Image[28];
            for(i=84;  i<112 ; i++){
                actualImages[k]=images[i];
                k++;
            }
            break;

            case PROPELLING: 
            case HEAD_DRAGGING:
            

            //84-111 
            actualImages = new Image[14];
            for(i=28;  i<42 ; i++){
                actualImages[k]=images[i];
                k++;
            }
            break;

            case FALLING: 

            //84-111 
            actualImages = new Image[14];
            for(i=42;  i<56 ; i++){
                actualImages[k]=images[i];
                k++;
            }
            break;



            default:
                    throw new IllegalStateException();

        }

        return actualImages;
    }

    public void move(boolean pressed) {
        this.model.move(pressed);
        //System.out.println(model.getBarryStatus());
    }

    public void updateView(Pane root) {
        this.view.update(model);
        this.view.setCurrentImages(this.getSpritesForStatus(), this.model.getBarryStatus());
        if (!root.getChildren().contains((Node) this.view.getImageView())) {
            root.getChildren().add((Node) this.view.getImageView());
        }

    }

    public ImageView getImageView() {
        return this.view.getImageView();
    }

    public Barry getBarryModel() {
        return this.model;
    }

}
