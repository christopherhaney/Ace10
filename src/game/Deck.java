package game;
import java.util.concurrent.ThreadLocalRandom;
import static game.SingleDeck.getSingleDeck;

public class Deck {
    private Card[] cardDeck; //The deck that the user will play the game with, 1-8x standard 52 decks
    private int top; //Index of current card to be drawn
    private int numDecks;

    /**
     * Constructor for a 1-8 deck shoe of cards
     * @param numDecks
     */
    public Deck(int numDecks) {
        this.numDecks = numDecks;
        top = 0;
        cardDeck = new Card[52 * numDecks];
        for(int i = 0; i < numDecks; i++) {
            System.arraycopy(getSingleDeck(), 0, cardDeck, 52 * i, getSingleDeck().length);
        }
    }

    public Card draw() {
        top++;
        return cardDeck[top-1];
    }

    /**
     * Shuffle (randomize order of) a game.Card[] deck using Fisher-Yates algorithm
     */
    public void shuffle() {
        for(int i = cardDeck.length - 1; i > 0; i--) {
            int randomIndex = ThreadLocalRandom.current().nextInt(i + 1);
            //Swap elements at i and randomIndex
            Card a = cardDeck[randomIndex];
            cardDeck[randomIndex] = cardDeck[i];
            cardDeck[i] = a;
        }
        top = 0;
    }

    /**
     *
     * @param cutIndex: The index at which to cut the deck
     *                NOTE: THE PLAYER WILL ENTER INDEX+1 FOR USER CONVENIENCE, ALLOWED FOR 1 DECK ONLY
     *                Note: We use 51-cutIndex instead of cardDeck.length so that this method fails for multideck shoes
     */
    public void cut(int cutIndex) {
        Card[] cutCardArray = new Card[cutIndex + 1];
        System.arraycopy(cardDeck,0,cutCardArray,0,cutCardArray.length); //Copy elements to be cut into temp array
        System.arraycopy(cardDeck,cutIndex + 1,cardDeck,0,cardDeck.length-cutCardArray.length); //Move elements cutIndex cards back in original array
        System.arraycopy(cutCardArray,0,cardDeck,51 - cutIndex,cutCardArray.length); //Copy temp array to end of original array
    }

    public int getTop() {
        return top;
    }

    public void setTop(int value) {
        top = value;
    }

    public int getDeckSize() {
        return cardDeck.length;
    }

    public int getNumDecks() {
        return numDecks;
    }

    public Card[] getCopyOfDeck() {
        Card[] temp = new Card[cardDeck.length];
        for(int i = temp.length - 1; i >= 0; i--) {
            Card p = cardDeck[i];
            if (p != null) {
                temp[i] = cardDeck[i];
            }
        }
        return temp;
    }

    public Card getCard(int i) {
        return cardDeck[i];
    }
}