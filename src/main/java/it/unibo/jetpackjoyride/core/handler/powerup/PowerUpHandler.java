package it.unibo.jetpackjoyride.core.handler.powerup;

import java.util.ArrayList;
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

public class PowerUpHandler {
    private List<PowerUpController> listOfControllers;
    private EntityGenerator entityGenerator;

    public PowerUpHandler() {
        this.listOfControllers = new ArrayList<>();
        entityGenerator = new EntityGeneratorImpl();
        this.init();
    }

    public void update(boolean isSpaceBarPressed, Group powerUpGroup) {
        var iterator = listOfControllers.iterator();
        while(iterator.hasNext()) {
            var controller = iterator.next();

            controller.update(isSpaceBarPressed, powerUpGroup);

            if(!powerUpGroup.getChildren().contains((Node)controller.getImageView())) {
                powerUpGroup.getChildren().add((Node)controller.getImageView());
            }

            if(controller.getPowerUpModel().getPowerUpStatus().equals(PowerUpStatus.DESTROYED)) {
                powerUpGroup.getChildren().remove((Node)controller.getImageView());
            }
    
            if(controller.getPowerUpModel().getPowerUpStatus().equals(PowerUpStatus.DESTROYED)) {
                powerUpGroup.getChildren().remove((Node)controller.getImageView());
                iterator.remove();  
            }
        }  
    }

    private void init() {

        /*List<PowerUp> model = entityGenerator.generatePowerUp(PowerUpType.LILSTOMPER, new MovementGenerator(new Pair<>(500.0,650.0), new Pair<>(0.0,0.0), new Pair<>(0.0,0.0), new Pair<>(0.0,0.0)).setMovementChangers(List.of(MovementChangers.GRAVITY, MovementChangers.INITIALLYSTILL, MovementChangers.BOUNDS)),null);
        
        Image[] actualImage = new Image[24];
        for (int i = 0; i < 24; i++) {
            actualImage[i] = new Image(getClass().getClassLoader().getResource("sprites/entities/powerups/lilstomper/lilstomper_" + (i+1) + ".png").toExternalForm()); 
        }
        PowerUpView view = new PowerUpView(actualImage);

        PowerUpController powerup = new PowerUpController(model.get(0), view);

        listOfControllers.add(powerup);*/
        
              
        List<PowerUp> model = entityGenerator.generatePowerUp(PowerUpType.MRCUDDLES, new MovementGenerator(new Pair<>(400.0,400.0), new Pair<>(0.0,0.0), new Pair<>(0.0,0.0), new Pair<>(0.0,0.0)).setMovementChangers(List.of(MovementChangers.INITIALLYSTILL,MovementChangers.INVERSEGRAVITY, MovementChangers.BOUNDS)),null);
        Image[] actualImage = new Image[6];
        
        for (int i = 0; i < 6; i++) {
            actualImage[i] = new Image(getClass().getClassLoader().getResource("sprites/entities/powerups/mrcuddles/mrcuddles_" + (i+1) + ".png").toExternalForm()); 
        }

        List<PowerUpController> powerup = new ArrayList<>();
        for(int i=0; i<model.size(); i++) {
            PowerUpView view = new PowerUpView(actualImage);
            powerup.add(new PowerUpController(model.get(i), view));

        }

        listOfControllers.addAll(powerup);
        
    }
}