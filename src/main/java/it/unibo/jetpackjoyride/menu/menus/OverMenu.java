package it.unibo.jetpackjoyride.menu.menus;


import it.unibo.jetpackjoyride.core.GameLoop;
import it.unibo.jetpackjoyride.core.statistical.api.GameStatsController;
import it.unibo.jetpackjoyride.menu.buttoncommand.ButtonFactory;
import it.unibo.jetpackjoyride.menu.buttoncommand.api.Command;
import it.unibo.jetpackjoyride.menu.buttoncommand.impl.StartCommand;
import it.unibo.jetpackjoyride.menu.buttoncommand.impl.OpenShopCommand;
import it.unibo.jetpackjoyride.menu.buttoncommand.impl.RestartCommand;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController;
import it.unibo.jetpackjoyride.menu.shop.impl.ShopControllerImpl;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OverMenu extends GameMenu{

    private  VBox buttonsVBox;
    private final GameLoop gameLoop;
    private final GameStatsController gameStatsHandler;
    private final ShopController shopController;
     private WritableImage writableImage;

    public OverMenu(final Stage primaryStage,
                        final GameLoop gameLoop,
                        final GameStatsController gameStatsHandler) {
        super(primaryStage);
        this.gameLoop = gameLoop;
        writableImage = 
        new WritableImage((int)this.gameLoop.getScene().getWidth(), (int)this.gameLoop.getScene().getHeight());
        this.gameLoop.getScene().snapshot(writableImage);
        setMenuImage(writableImage);
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
        Command restartCommand = new RestartCommand(gameLoop,stage,this);
        Button restartButton = ButtonFactory.createButton("PlayAgain",e->restartCommand.execute(),220,120);
        Command openShopCommand = new OpenShopCommand(shopController, stage);
        Button  shopButton = ButtonFactory.createButton("Shop",e->openShopCommand.execute(),150,50);
        

        buttonsVBox.getChildren().addAll(restartButton,shopButton);
        addButtons(buttonsVBox);
    }

    public GameStatsController getGameStatsHandler(){
        return this.gameStatsHandler;
    }

    public void show(){
        this.stage.setScene(this.scene);
    }

}