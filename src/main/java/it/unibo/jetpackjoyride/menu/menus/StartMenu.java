package it.unibo.jetpackjoyride.menu.menus;

import it.unibo.jetpackjoyride.core.GameLoop;
import it.unibo.jetpackjoyride.core.statistical.api.GameStatsController;
import it.unibo.jetpackjoyride.menu.buttoncommand.ButtonFactory;
import it.unibo.jetpackjoyride.menu.buttoncommand.api.Command;
import it.unibo.jetpackjoyride.menu.buttoncommand.impl.OpenShopCommand;
import it.unibo.jetpackjoyride.menu.buttoncommand.impl.StartCommand;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController;
import it.unibo.jetpackjoyride.menu.shop.impl.ShopControllerImpl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.image.Image;


public final class StartMenu extends GameMenu{

    private GameLoop gameLoop;
      private final ShopController shopController;

    public StartMenu(Stage primaryStage,final GameStatsController gameStatsController) {
        super(primaryStage, gameStatsController);
        Image menuImage = new Image(getClass().getClassLoader().getResource("menuImg/menuimg.png").toExternalForm());
        setMenuImage(menuImage);
        shopController = new ShopControllerImpl(primaryStage, this);
        initializeGameMenu();
        primaryStage.setMinHeight(gameInfo.getDefaultHeight());
        primaryStage.setMinWidth(gameInfo.getDefaultWidth());
        setGameStagePosition();
        stageCloseAction();
    }

    @Override
    protected void initializeGameMenu(){
        this.gameLoop = new GameLoop(this.stage,getGameStatsHandler());
        VBox buttonsRoot = new VBox(20);
        buttonsRoot.setAlignment(Pos.CENTER);

        Command startCommand = new StartCommand(this.gameLoop, stage,this);
        Button startButton = ButtonFactory.createButton("PlayGame",
        e->{startCommand.execute();setGameStagePosition();},150,50);
        Command openShopCommand = new OpenShopCommand(shopController, stage);
        Button  shopButton = ButtonFactory.createButton("Shop",e->openShopCommand.execute(),150,50);

        buttonsRoot.getChildren().addAll(startButton,shopButton);

        StackPane.setMargin(buttonsRoot, new Insets(this.scene.getHeight()/2, 0, 0, 0));
        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            StackPane.setMargin(buttonsRoot, new Insets(newVal.doubleValue()/2, 0, 0, 0));
        });
        
        addButtons(buttonsRoot);
    }

    @Override
    protected void stageCloseAction(){
        stage.setOnCloseRequest(event -> {
            this.gameLoop.endLoop();
            defaultCloseAction();
        });
    }
   
}
