package it.unibo.jetpackjoyride.core.handler;

import java.util.List;

import it.unibo.jetpackjoyride.core.entities.entity.api.EntityGenerator;
import it.unibo.jetpackjoyride.core.entities.entity.impl.EntityGeneratorImpl;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp.PowerUpStatus;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp.PowerUpType;
import it.unibo.jetpackjoyride.core.movement.MovementGenerator;
import it.unibo.jetpackjoyride.core.movement.MovementGenerator.MovementChangers;
import it.unibo.jetpackjoyride.utilities.Pair;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PowerUpController {
    private PowerUp model;
    private PowerUpView view;
    private EntityGenerator entityGenerator;

    public PowerUpController() {
        entityGenerator = new EntityGeneratorImpl();
        /* 
        Image[] actualImage = new Image[24];

        for (int i = 0; i < 24; i++) {
            actualImage[i] = new Image(getClass().getClassLoader().getResource("sprites/entities/powerups/lilstomper/lilstomper_" + (i+1) + ".png").toExternalForm()); 
        }

        this.model = entityGenerator.generatePowerUp(PowerUpType.LILSTOMPER, new MovementGenerator(new Pair<>(500.0,650.0), new Pair<>(0.0,0.0), new Pair<>(0.0,0.0), new Pair<>(0.0,0.0)).setMovementChangers(List.of(MovementChangers.STATIC, MovementChangers.GRAVITY)),null);
        */
        
        Image[] actualImage = new Image[24];

        for (int i = 0; i < 5; i++) {
            actualImage[i] = new Image(getClass().getClassLoader().getResource("sprites/entities/powerups/mrcuddles/mrcuddles_" + (i+1) + ".png").toExternalForm()); 
        }

        this.model = entityGenerator.generatePowerUp(PowerUpType.MRCUDDLES, new MovementGenerator(new Pair<>(200.0,650.0), new Pair<>(0.0,0.0), new Pair<>(0.0,0.0), new Pair<>(0.0,0.0)).setMovementChangers(List.of(MovementChangers.STATIC, MovementChangers.INVERSEGRAVITY)),null);

        this.view = new PowerUpView(actualImage);
    }

    public void update(boolean isSpaceBarPressed, Group powerUpGroup) {
        this.model.update(isSpaceBarPressed);
        this.view.updateView(model);

        if(!powerUpGroup.getChildren().contains((Node)this.getImageView())) {
            powerUpGroup.getChildren().add((Node)this.getImageView());
        }
    
        if(this.getPowerUpModel().getPowerUpStatus().equals(PowerUpStatus.DESTROYED)) {
            powerUpGroup.getChildren().remove((Node)this.getImageView());
        }
    }

    public ImageView getImageView() {
        return this.view.getImageView();
    }

    public PowerUp getPowerUpModel() {
        return this.model;
    }
}