package it.unibo.jetpackjoyride.menu;


import it.unibo.jetpackjoyride.core.GameLoop;
import it.unibo.jetpackjoyride.core.statistical.api.GameStatsController;
import it.unibo.jetpackjoyride.menu.buttonCommand.ButtonFactory;
import it.unibo.jetpackjoyride.menu.buttonCommand.api.Command;
import it.unibo.jetpackjoyride.menu.buttonCommand.impl.StartCommand;
import it.unibo.jetpackjoyride.menu.buttonCommand.impl.openShopCommand;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController;
import it.unibo.jetpackjoyride.menu.shop.impl.ShopControllerImpl;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameOverMenu extends BaseMenu{

    private  VBox buttonsVBox;
    private final GameLoop gameLoop;
    private final GameStatsController gameStatsHandler;
    private final ShopController shopController;

    public GameOverMenu(final Stage primaryStage,final Image image,
                        final GameLoop gameLoop,
                        final GameStatsController gameStatsHandler) {
        super(primaryStage);
        setMenuImage(image);
        this.gameLoop = gameLoop;
        this.gameStatsHandler = gameStatsHandler;
        shopController = new ShopControllerImpl(primaryStage, this);
        initializeGameMenu();
    }

    @Override
    protected void initializeGameMenu(){
        buttonsVBox = new VBox();
        buttonsVBox.setPrefWidth(gameInfo.getScreenWidth());
        buttonsVBox.setPrefHeight(gameInfo.getScreenHeight());
        buttonsVBox.setAlignment(Pos.CENTER);
        buttonsVBox.setSpacing(20);
        buttonsVBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");
        Command restartCommand = new StartCommand(gameLoop, stage,this);
        Button restartButton = ButtonFactory.createButton("PlayAgain",e->restartCommand.execute(),220,120);
        Command openShopCommand = new openShopCommand(shopController, stage);
        Button  shopButton = ButtonFactory.createButton("Shop",e->openShopCommand.execute(),150,50);
        

        buttonsVBox.getChildren().addAll(restartButton,shopButton);
        addButtons(buttonsVBox);
    }

    public GameStatsController getGameStatsHandler(){
        return this.gameStatsHandler;
    }

}