package org.sillylossy.games.common.ui.images;

import org.sillylossy.games.common.cards.Card;
import org.sillylossy.games.common.cards.Deck;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.InputStream;
import java.util.Map;

/**
 * Utility that controls image files.
 */
class CardImageController {

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
    CardImageController() {
        loadCardImages();
    }

    /**
     * Scales an image of card back to desired height.
     *
     * @param height desired image height
     * @return UI object with back image of desired height
     */
    Image getBackImage(int height) {
        return cardBackImage.getScaledInstance(-1, height, Image.SCALE_SMOOTH);
    }

    /**
     * Scales an image of specified card to desired height.
     *
     * @param card   card that needs to be imaged
     * @param height desired image height
     * @return UI object with card image of desired height
     */
    Image getCardImage(Card card, int height) {
        return cardImages.get(card).getScaledInstance(-1, height, Image.SCALE_SMOOTH);
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
