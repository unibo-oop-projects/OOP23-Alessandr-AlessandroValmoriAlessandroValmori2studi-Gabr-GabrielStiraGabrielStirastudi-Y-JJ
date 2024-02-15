package it.unibo.jetpackjoyride.menu.buttonCommand;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ButtonFactory {
     public static Button createButton(final String name,final EventHandler<ActionEvent> action, double x,double y) {
         final Button button = new Button();
        button.setOnAction(action);
        button.setPrefWidth(x);
        button.setPrefHeight(y);

        if (loadImageFromResources(name).isPresent()) {
            Image img = loadImageFromResources(name).get();
            ImageView imageView = new ImageView(img);
            imageView.setFitWidth(x);
            imageView.setFitHeight(y);
            imageView.setPreserveRatio(false);
            button.setGraphic(imageView);
            button.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-border-width: 0;");
        } else {
            button.setText(name);
        }
        return button;
    }

    private static Optional<Image> loadImageFromResources(String imageName) {
        try {  
            String imagePath = "/buttons/" + imageName + ".png";
            Image image = new Image(ButtonFactory.class.getResourceAsStream(imagePath));
            return Optional.of(image);
        } catch (Exception e) {
            return Optional.empty();
        }
     }
    }

