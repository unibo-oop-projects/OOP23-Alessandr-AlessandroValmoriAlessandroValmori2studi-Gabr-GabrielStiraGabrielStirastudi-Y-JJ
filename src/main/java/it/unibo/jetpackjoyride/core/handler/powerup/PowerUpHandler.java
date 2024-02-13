package it.unibo.jetpackjoyride.core.handler.powerup;

import java.util.ArrayList;
import java.util.List;

import it.unibo.jetpackjoyride.core.entities.entity.api.EntityGenerator;
import it.unibo.jetpackjoyride.core.entities.entity.impl.EntityGeneratorImpl;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp.PowerUpStatus;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp.PowerUpType;
import it.unibo.jetpackjoyride.core.movement.Movement.MovementChangers;
import it.unibo.jetpackjoyride.core.movement.MovementImpl;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;

public final class PowerUpHandler {
    private List<PowerUpController> listOfControllers;
    private EntityGenerator entityGenerator;

    public PowerUpHandler() {
        this.listOfControllers = new ArrayList<>();
        entityGenerator = new EntityGeneratorImpl();
        this.init();
    }

    public void update(final boolean isSpaceBarPressed, final Group powerUpGroup) {
        var iterator = listOfControllers.iterator();
        while (iterator.hasNext()) {
            var controller = iterator.next();

            controller.update(isSpaceBarPressed, powerUpGroup);

            if (!powerUpGroup.getChildren().contains((Node) controller.getImageView())) {
                powerUpGroup.getChildren().add((Node) controller.getImageView());
            }

            if (controller.getPowerUpModel().getPowerUpStatus().equals(PowerUpStatus.DESTROYED)) {
                powerUpGroup.getChildren().remove((Node) controller.getImageView());
            }

            if (controller.getPowerUpModel().getPowerUpStatus().equals(PowerUpStatus.DESTROYED)) {
                powerUpGroup.getChildren().remove((Node) controller.getImageView());
                iterator.remove();
            }
        }
    }

    private void init() {

        Double screenSizeX = GameInfo.getInstance().getScreenWidth();
        Double screenSizeY = GameInfo.getInstance().getScreenHeight();

        /*List<PowerUp> stomperModel = entityGenerator.generatePowerUp(PowerUpType.LILSTOMPER,
                new MovementImpl(new Pair<>(screenSizeX / 4, screenSizeY - screenSizeY / 8), new Pair<>(0.0, 0.0),
                        new Pair<>(0.0, 0.0), new Pair<>(0.0, 0.0),
                        List.of(MovementChangers.GRAVITY, MovementChangers.BOUNDS)),
                null);

        Image[] stomperActualImage = new Image[24];
        for (int i = 0; i < 24; i++) {
            stomperActualImage[i] = new Image(getClass().getClassLoader()
                    .getResource("sprites/entities/powerups/lilstomper/lilstomper_" + (i + 1) + ".png")
                    .toExternalForm());
        }
        PowerUpView stomperView = new PowerUpView(stomperActualImage);

        PowerUpController stomperPowerup = new PowerUpController(stomperModel.get(0), stomperView);

        listOfControllers.add(stomperPowerup);*/

        List<PowerUp> model = entityGenerator.generatePowerUp(PowerUpType.MRCUDDLES,
                new MovementImpl(new Pair<>(screenSizeX/5, screenSizeY/8), new Pair<>(0.0, 0.0), new Pair<>(0.0, 0.0),
                        new Pair<>(0.0, 0.0), List.of(MovementChangers.INVERSEGRAVITY, MovementChangers.BOUNDS)),
                null);
        Image[] actualImage = new Image[6];

        for (int i = 0; i < 6; i++) {
            actualImage[i] = new Image(getClass().getClassLoader()
                    .getResource("sprites/entities/powerups/mrcuddles/mrcuddles_" + (i + 1) + ".png").toExternalForm());
        }

        List<PowerUpController> powerup = new ArrayList<>();
        for (int i = 0; i < model.size(); i++) {
            PowerUpView view = new PowerUpView(actualImage);
            powerup.add(new PowerUpController(model.get(i), view));

        }

        listOfControllers.addAll(powerup);

    }
}
