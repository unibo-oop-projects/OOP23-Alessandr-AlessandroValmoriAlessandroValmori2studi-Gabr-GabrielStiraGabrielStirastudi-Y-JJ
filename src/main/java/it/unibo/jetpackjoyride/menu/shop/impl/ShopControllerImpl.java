package it.unibo.jetpackjoyride.menu.shop.impl;

import java.io.IOException;
import it.unibo.jetpackjoyride.menu.menus.api.GameMenu;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController;
import it.unibo.jetpackjoyride.utilities.exceptions.DirectoryCreationException;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.util.Set;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Collections;
import java.util.HashSet;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller class for the shop menu.
 * This class manages the interaction between the shop model and view.
 * 
 * @author alessandro.valmori2@studio.unibo.it
 */
public final class ShopControllerImpl implements ShopController {
    /** The view component of the shop . */
    private final ShopView view;
    /** The path of the file where the unlocked items are stored as text . */
    private static final String SHOP_DATA_PATH = System.getProperty("user.home") + File.separator + "jetpackJoyride"
            + File.separator + "shopdata.txt";
    /** The set of unlocked items . */
    private Set<Items> unlockedSet = new HashSet<>();

    /** The main menu of the application . */
    private final GameMenu gameMenu;

    private static final Logger LOGGER = Logger.getLogger(ShopControllerImpl.class.getName());

    /**
     * Constructs a new ShopControllerImpl instance.
     *
     * @param primaryStage The primary stage of the application.
     * @param gameMenu     The game menu associated with the shop.
     */
    @SuppressFBWarnings(value = "EI2", justification = "GameMenu object is use for the shop to return to the last menu")
    public ShopControllerImpl(final Stage primaryStage, final GameMenu gameMenu) {
        this.gameMenu = gameMenu;
        readFromFile(SHOP_DATA_PATH);
        this.view = new ShopView(this, primaryStage);
        this.view.addBuyObs(new ShopItemPurchaseObsImpl(this));
        this.view.addBackToMenuObs(new BackToMenuObsImpl(this));
        this.view.addCharObs(new CharacterImpl(this));
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
     * not exist or if the text file contains strings
     * that do not associate with an item, the unlocked set field of this class is initialized.
     */
    private void readFromFile(final String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            reader.lines().forEach(item -> this.unlockedSet.add(Items.valueOf(item)));
        } catch (IOException | IllegalArgumentException e) {
            this.unlockedSet = new HashSet<>();
        }

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
        final File file = new File(SHOP_DATA_PATH);
        final File parentDir = file.getParentFile();
        if (!file.getParentFile().exists()) {
            try {
                if (!parentDir.mkdirs()) {
                    throw new DirectoryCreationException("Failed to create directory, may not have permission");
                }
            } catch (DirectoryCreationException e1) {
                LOGGER.log(Level.SEVERE, "Failed to create the directory {0}", parentDir);
            }
        }
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(SHOP_DATA_PATH), Charset.defaultCharset()))) {
            for (final var item : this.unlockedSet) {
                writer.write(item.toString()); // Write the word
                writer.newLine(); // Write a newline character
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error saving Items to file", e);
        }

    }

}
