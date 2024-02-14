package it.unibo.jetpackjoyride.menu;

import java.io.FileNotFoundException;
import java.net.URL;

import it.unibo.jetpackjoyride.core.GameLoop;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class GameOverMenu {

    private VBox buttonsVBox;
    private GameInfo gameInfo = GameInfo.getInstance();

    public GameOverMenu(GameLoop gameLoop){
        buttonsVBox = new VBox();
        buttonsVBox.setPrefWidth(gameInfo.getScreenWidth());
        buttonsVBox.setPrefHeight(gameInfo.getScreenHeight());
        buttonsVBox.setAlignment(Pos.CENTER);
        buttonsVBox.setSpacing(10);
        buttonsVBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");
        addButtons();
    }


    public VBox getVBox(){
        return this.buttonsVBox;
    }

    public void setVisible(boolean isVisble){
         this.buttonsVBox.setVisible(isVisble);
    }

      private void addButtons() {
        final Button restartButton = createButton("Restart", "buttons/PlayAgain.png", e -> {

        });
        final Button shopButton = createButton("Shop",  "buttons/Shop.png", e -> {
           
        });
        buttonsVBox.getChildren().addAll(restartButton, shopButton);
    }

    private Button createButton(final String name,final String path, final EventHandler<ActionEvent> action) {
        final Button button = new Button();
        try {
            URL buttonsUrl = getClass().getClassLoader().getResource(path);
            if (buttonsUrl == null) {
                throw new FileNotFoundException("buttons Image was not found: " + path);
            }
            String url = buttonsUrl.toExternalForm();
            Image buttonImage = new Image(url);
            ImageView buttonsImageView = new ImageView(buttonImage);
            button.setGraphic(buttonsImageView);
            button.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-border-width: 0;");
            
        } catch (FileNotFoundException e) {
            System.err.println("Error message :" + e.getMessage());
            button.setText(name);
        }
        button.setOnAction(action);
        return button;
    }
     
}