package it.unibo.jetpackjoyride.core.entities.barry.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.midi.Soundbank;

import it.unibo.jetpackjoyride.core.entities.barry.api.Barry;
import it.unibo.jetpackjoyride.core.entities.barry.api.Barry.BarryStatus;
import it.unibo.jetpackjoyride.core.hitbox.impl.PlayerHitbox;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class PlayerMover {

    private Barry model;
    private BarryView view;
    private Map<BarryStatus, List<Image>> statusMap = new HashMap<>();
    
      private final Map<BarryStatus, Integer> framesPerAnimation = new HashMap<>(){{
        put(BarryStatus.WALKING, 4);
        put(BarryStatus.BURNED, 4);
        put(BarryStatus.LAND, 4);
        put(BarryStatus.ZAPPED, 4);
        put(BarryStatus.FALLING, 2);
        put(BarryStatus.PROPELLING, 2);
        put(BarryStatus.HEAD_DRAGGING, 2);
      }};
     

    public PlayerMover() {
        this.model = new BarryImpl();
        this.buildMap();

        this.view = new BarryView(this.getSpritesForStatus());
    }

    private void buildMap() {

        List<Image> temp = new ArrayList<>();
        for (int i = 0; i < 4; i++) { // 28, barryBurned
            String imagePath = getClass().getClassLoader()
                    .getResource("sprites/entities/player/barryburned" + (i + 1) + ".png").toExternalForm();

            for (int j = 0; j < 7; j++) {
                temp.add(j, new Image(imagePath));

            }
        }
        this.statusMap.put(BarryStatus.BURNED, new ArrayList<>(temp));
        temp.clear();

        for (int i = 0; i < 2; i++) { // 14, barryjump
            String imagePath = getClass().getClassLoader()
                    .getResource("sprites/entities/player/barryjump" + (i + 1) + ".png").toExternalForm();

            for (int j = 0; j < 7; j++) {
                temp.add(j, new Image(imagePath));
            }
        }
        this.statusMap.put(BarryStatus.PROPELLING, new ArrayList<>(temp));
        temp.clear();

        for (int i = 0; i < 2; i++) { // 14, barryheaddragging
            String imagePath = getClass().getClassLoader()
                    .getResource("sprites/entities/player/barryjump" + (i + 1) + ".png").toExternalForm();

            for (int j = 0; j < 7; j++) {
                temp.add(j, new Image(imagePath));
            }
        }
        this.statusMap.put(BarryStatus.HEAD_DRAGGING, new ArrayList<>(temp));
        temp.clear();

        for (int i = 0; i < 2; i++) { // 14, barryfall
            String imagePath = getClass().getClassLoader()
                    .getResource("sprites/entities/player/barryfall" + (i + 1) + ".png").toExternalForm();

            for (int j = 0; j < 7; j++) {
                temp.add(j, new Image(imagePath));
            }
        }
        this.statusMap.put(BarryStatus.FALLING, new ArrayList<>(temp));
        temp.clear();

        for (int i = 0; i < 4; i++) { // 28, barryland
            String imagePath = getClass().getClassLoader()
                    .getResource("sprites/entities/player/barryland" + (i + 1) + ".png").toExternalForm();

            for (int j = 0; j < 7; j++) {
                temp.add(j, new Image(imagePath));
            }
        }
        this.statusMap.put(BarryStatus.LAND, new ArrayList<>(temp));
        temp.clear();

        for (int i = 0; i < 4; i++) { // 28, barrywalk
            String imagePath = getClass().getClassLoader()
                    .getResource("sprites/entities/player/barrywalk" + (i + 1) + ".png").toExternalForm();
            for (int j = 0; j < 7; j++) {
                temp.add(j, new Image(imagePath));
            }
        }
        this.statusMap.put(BarryStatus.WALKING, new ArrayList<>(temp));
        temp.clear();

        for (int i = 0; i < 4; i++) { // 28, barryzapped
            String imagePath = getClass().getClassLoader()
                    .getResource("sprites/entities/player/barryzapped" + (i + 1) + ".png").toExternalForm();

            for (int j = 0; j < 7; j++) {
                temp.add(j, new Image(imagePath));
            }
        }
        this.statusMap.put(BarryStatus.ZAPPED, new ArrayList<>(temp));
        temp.clear();

    }

    private List<Image> getSpritesForStatus() {
       
        return this.statusMap.get(this.model.getBarryStatus());

    }

    public void move(boolean pressed) {
        this.model.move(pressed);

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

    public PlayerHitbox getHitbox() {
        return this.model.getHitbox();
    }

}
