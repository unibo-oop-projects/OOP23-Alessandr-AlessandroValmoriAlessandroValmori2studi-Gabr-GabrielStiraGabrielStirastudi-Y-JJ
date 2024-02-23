package it.unibo.jetpackjoyride.menu.shop.impl;

import java.io.IOException;
import it.unibo.jetpackjoyride.menu.menus.api.GameMenu;
import it.unibo.jetpackjoyride.menu.shop.api.BackToMenuObs;
import it.unibo.jetpackjoyride.menu.shop.api.CharacterObs;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController;
import it.unibo.jetpackjoyride.menu.shop.api.ShopItemPurchaseObs;
import javafx.stage.Stage;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.*;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileReader;

/**
 * Controller class for the shop menu.
 * This class manages the interaction between the shop model and view.
 */
public final class ShopControllerImpl implements ShopController {

    private final ShopView view;
    private final String SHOP_DATA_PATH = System.getProperty("user.home")+File.separator + "jetpackJoyride"+File.separator +"shopdata.txt";
    private Set<Items> unlockedSet = new HashSet<>();
    
    
    private final GameMenu gameMenu;

    /**
     * Constructs a new ShopControllerImpl instance.
     *
     * @param primaryStage The primary stage of the application.
     * @param gameMenu     The game menu associated with the shop.
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    public ShopControllerImpl(final Stage primaryStage, final GameMenu gameMenu) {
        
        this.gameMenu = gameMenu;
        readFromFile();
        this.view = new ShopView(this, primaryStage);

        ShopItemPurchaseObs shopItemPurchaseObs = new ShopItemPurchaseObsImpl(this);
        BackToMenuObs backToMenuObs = new BackToMenuObsImpl(this);
        CharacterObs charObs = new CharacterImpl(this);

        // Register observers with ShopView
        this.view.addBuyObs(shopItemPurchaseObs);
        this.view.addBackToMenuObs(backToMenuObs);
        this.view.addCharObs(charObs);
    }

    /**
     * Retrieves the scene of the shop menu.
     * 
     * @return The scene of the shop menu.
     */
    @Override
    public void showTheShop() {
        this.view.setSceneOnStage();
    }



    @Override
    public void backToMenu() {
        this.save();
        gameMenu.showMenu();
    }

    private void readFromFile() {
    
        
        try (BufferedReader reader = new BufferedReader(new FileReader(SHOP_DATA_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) { // Check if the line is not empty or whitespace
                    for(var item : Items.values()){
                        if(item.toString().equals(line.trim())){
                            this.unlockedSet.add(item);
                        }
                    }
                }
            }
      
           
        } catch (IOException e) {
            System.err.println("Failed to read from file: " + e.getMessage());
           
        }
    }

    

    
    

    @Override
    public void updateView() {
        this.view.update();
    }

    

    @Override
    public Set<Items> getUnlocked() {
         return Collections.unmodifiableSet(this.unlockedSet);
    }

    @Override
    public void unlock(Items item) {
        this.unlockedSet.add(item);
    }

    @Override
    public void save() {
        File file = new File(SHOP_DATA_PATH);
        File parentDir = file.getParentFile();
        if (!new File(SHOP_DATA_PATH).getParentFile().exists()){
            parentDir.mkdirs();
        } 
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SHOP_DATA_PATH))) {
            for (var item : this.unlockedSet) {
                writer.write(item.toString()); // Write the word
                writer.newLine(); // Write a newline character
            }
            System.out.println("Words saved to file successfully.");
        } catch (IOException e) {
            
        }
    }
 
}
