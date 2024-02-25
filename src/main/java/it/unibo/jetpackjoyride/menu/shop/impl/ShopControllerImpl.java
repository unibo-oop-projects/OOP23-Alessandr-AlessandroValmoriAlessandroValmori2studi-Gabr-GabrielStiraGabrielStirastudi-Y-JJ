package it.unibo.jetpackjoyride.menu.shop.impl;

import java.io.IOException;
import it.unibo.jetpackjoyride.menu.menus.api.GameMenu;
import it.unibo.jetpackjoyride.menu.shop.api.BackToMenuObs;
import it.unibo.jetpackjoyride.menu.shop.api.CharacterObs;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController;
import it.unibo.jetpackjoyride.menu.shop.api.ShopItemPurchaseObs;
import it.unibo.jetpackjoyride.utilities.exceptions.DirectoryCreationException;
import javafx.stage.Stage;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.util.Set;
import java.util.Collections;
import java.util.HashSet;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

/**
 * Controller class for the shop menu.
 * This class manages the interaction between the shop model and view.
 */
public final class ShopControllerImpl implements ShopController {
    /** The view component of the shop */
    private final ShopView view;
    /** The path of the file where the unlocked items are stored as text .*/
    private static final String SHOP_DATA_PATH = System.getProperty("user.home") + File.separator + "jetpackJoyride"
            + File.separator + "shopdata.txt";
    /** The set of unlocked items .*/
    private Set<Items> unlockedSet = new HashSet<>();

    /** The main menu of the application .*/
    private final GameMenu gameMenu;

    /**
     * The observers made to handle the event that occur in the {@link ShopView} GUI.
     */
    private ShopItemPurchaseObs shopItemPurchaseObs;
    private BackToMenuObs backToMenuObs;
    private CharacterObs charObs;

    /**
     * Constructs a new ShopControllerImpl instance.
     *
     * @param primaryStage The primary stage of the application.
     * @param gameMenu     The game menu associated with the shop.
     */
    public ShopControllerImpl(final Stage primaryStage, final GameMenu gameMenu) {

        this.gameMenu = gameMenu;
        readFromFile();
        this.view = new ShopView(this, primaryStage);
        this.shopItemPurchaseObs = new ShopItemPurchaseObsImpl(this);
        this.backToMenuObs = new BackToMenuObsImpl(this);
        this.charObs = new CharacterImpl(this);
        this.view.addBuyObs(shopItemPurchaseObs);
        this.view.addBackToMenuObs(backToMenuObs);
        this.view.addCharObs(charObs);
    }

    @Override
    public void showTheShop() {
        this.view.setSceneOnStage();
    }

    @Override
    public void backToMenu() {
        this.save();
        gameMenu.showMenu();
    }

    /**
     * Method used to read from file the set of unlocked items, if the file does
     * not exist, the unlocked set field of this class is initialized.
     */
    private void readFromFile() {

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(SHOP_DATA_PATH), Charset.defaultCharset()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) { // Check if the line is not empty or whitespace
                    for (var item : Items.values()) {
                        if (item.toString().equals(line.trim())) {
                            this.unlockedSet.add(item);
                        }
                    }
                }
            }

        } catch (IOException e) {
            this.unlockedSet = new HashSet<>();
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
    public void unlock(final Items item) {
        this.unlockedSet.add(item);
    }

    @Override
    public void save() {
        File file = new File(SHOP_DATA_PATH);
        File parentDir = file.getParentFile();
        if (!file.getParentFile().exists()) {
            try {
                if (!parentDir.mkdirs()) {
                    throw new DirectoryCreationException("Failed to create directory, may not have permission");
                }
            } catch (DirectoryCreationException e1) {
                e1.getStackTrace();
            }
        }
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(SHOP_DATA_PATH), Charset.defaultCharset()))) {
            for (var item : this.unlockedSet) {
                writer.write(item.toString()); // Write the word
                writer.newLine(); // Write a newline character
            }
            System.out.println("Words saved to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
