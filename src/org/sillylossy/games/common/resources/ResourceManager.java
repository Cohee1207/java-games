package org.sillylossy.games.common.resources;

import org.sillylossy.games.common.Main;
import org.sillylossy.games.common.cards.Card;
import org.sillylossy.games.common.cards.Deck;
import org.sillylossy.games.common.ui.CardPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Utility that controls image files.
 */
public class ResourceManager {

    /**
     * Image file extension.
     */
    private static final String IMAGE_EXT = ".png";
    private static ResourceManager instance = new ResourceManager();
    /**
     * A card back image
     */
    private Image cardBackImage = loadCardBackImage();
    /**
     * Map of cards and resources.
     */
    private Map<Card, Image> cardImages = loadCardImages();

    private ResourceManager() {

    }

    public static ResourceManager getInstance() {
        return instance;
    }

    public Image getGameIcon(String name) throws java.io.IOException {
        return ImageIO.read(ResourceManager.class.getResourceAsStream("icons/" + name + IMAGE_EXT));
    }

    public String getRules(String name) {
        InputStream inputStream = ResourceManager.class.getResourceAsStream("rules/" + name + ".html");
        try (BufferedInputStream bis = new BufferedInputStream(inputStream);
             ByteArrayOutputStream buf = new ByteArrayOutputStream()) {
            int result = bis.read();
            while (result != -1) {
                buf.write((byte) result);
                result = bis.read();
            }
            return buf.toString();
        } catch (IOException e) {
            return e.toString();
        }
    }

    /**
     * Scales an image of card back to desired height.
     *
     * @return UI object with back image of desired height
     */
    public Image getBackImage() {
        if (Main.getUI().getMainPanel().getGamePanel() instanceof CardPanel) {
            CardPanel panel = (CardPanel) Main.getUI().getMainPanel().getGamePanel();
            return cardBackImage.getScaledInstance(-1, (panel).getImageHeight(), Image.SCALE_SMOOTH);
        }
        return cardBackImage;
    }

    /**
     * Scales an image of specified card to desired height.
     *
     * @param card card that needs to be imaged
     * @return UI object with card image of desired height
     */
    public Image getCardImage(Card card) {
        if (Main.getUI().getMainPanel().getGamePanel() instanceof CardPanel) {
            CardPanel panel = (CardPanel) Main.getUI().getMainPanel().getGamePanel();
            Image img = cardImages.get(card);
            return img.getScaledInstance(-1, panel.getImageHeight(), Image.SCALE_SMOOTH);
        }
        return cardImages.get(card);
    }

    /**
     * Loads all card resources from files and maps them to "Card" objects.
     */
    private Map<Card, Image> loadCardImages() {
        try {
            cardImages = new java.util.HashMap<>();
            for (Card card : Deck.FULL_DECK) {
                String path = "cards/" + card.toString() + IMAGE_EXT;
                InputStream stream = this.getClass().getResourceAsStream(path);
                Image img = ImageIO.read(stream);
                cardImages.put(card, img);
            }
            return cardImages;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Image loadCardBackImage() {
        try {
            String path = "cards/back" + IMAGE_EXT;
            InputStream stream = this.getClass().getResourceAsStream(path);
            return ImageIO.read(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
