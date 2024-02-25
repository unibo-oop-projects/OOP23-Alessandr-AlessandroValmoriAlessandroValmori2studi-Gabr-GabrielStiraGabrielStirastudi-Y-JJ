package it.unibo.jetpackjoyride.menu.buttoncommand;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * A factory class for creating buttons.
 * @author yukai.zhou@studio.unibo.it
 */
public final class ButtonFactory {

    private static final Logger LOGGER = Logger.getLogger(ButtonFactory.class.getName());

    private ButtonFactory() {

    }

    /**
     * Creates a button with the specified name(or it can be a path), action, width, and height.
     *
     * @param name   the name of the button
     * @param action the action to perform when the button is clicked
     * @param x      the width of the button
     * @param y      the height of the button
     * @return the created button
     */
     public static Button createButton(final String name, final EventHandler<ActionEvent> action, 
                                       final double x, final double y) {
        final Button button = new Button();
        button.setOnAction(action);
        button.setPrefWidth(x);
        button.setPrefHeight(y);
        button.setFocusTraversable(false);

        if (loadImageFromResources(name).isPresent()) {
            final Image img = loadImageFromResources(name).get();
            final ImageView imageView = new ImageView(img);
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

    /**
     * Loads an image from resources with the given name.
     *
     * @param imageName the name of the image
     * @return an Optional containing the loaded image, or empty if the image could not be loaded
     */
    private static Optional<Image> loadImageFromResources(final String imageName) {
        try {
            final String imagePath = "/buttons/" + imageName + ".png";
            final Image image = new Image(ButtonFactory.class.getResourceAsStream(imagePath));
            return Optional.of(image);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading image: " + imageName, e);
            return Optional.empty();
        }
     }
    }
