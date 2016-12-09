package org.sillylossy.games.common.ui.images;

import org.sillylossy.games.common.cards.Card;
import org.sillylossy.games.common.cards.Deck;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.util.Map;

/**
 * Utility that controls image files.
 */
public class ImageController {

    /**
     * Image file extension.
     */
    private static final String IMAGE_EXT = ".png";

    /**
     * A card back image
     */
    private Image cardBackImage;

    /**
     * Map of cards and images.
     */
    private Map<Card, Image> cardImages;

    /**
     * Creates controller and loads images.
     */
    public ImageController() {
        loadCardImages();
    }

    /**
     * Scales an image of card back to desired height.
     *
     * @param height desired image height
     * @return UI object with back image of desired height
     */
    public JLabel getBackImage(int height) {
        return new JLabel(new ImageIcon(cardBackImage.getScaledInstance(-1, height, Image.SCALE_SMOOTH)));
    }

    /**
     * Scales an image of specified card to desired height.
     *
     * @param card   card that needs to be imaged
     * @param height desired image height
     * @return UI object with card image of desired height
     */
    public JLabel getCardImage(Card card, int height) {
        return new JLabel(new ImageIcon(cardImages.get(card).getScaledInstance(-1, height, Image.SCALE_SMOOTH)));
    }

    /**
     * Loads all card images from resources and maps them to "Card" objects.
     */
    private void loadCardImages() {
        try {
            cardImages = new java.util.HashMap<>();
            for (Card card : Deck.INITIAL_DECK) {
                String path = card.toString() + IMAGE_EXT;
                InputStream stream = this.getClass().getResourceAsStream(path);
                Image img = ImageIO.read(stream);
                cardImages.put(card, img);
            }
            String path = "back" + IMAGE_EXT;
            InputStream stream = this.getClass().getResourceAsStream(path);
            cardBackImage = ImageIO.read(stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
