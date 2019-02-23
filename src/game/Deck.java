package game;
import java.util.concurrent.ThreadLocalRandom;
import static game.SingleDeck.getSingleDeck;

public class Deck {
    private Card[] cardDeck; //The deck that the user will play the game with, 1-8x standard 52 decks
    //private final String[] rankArray = {"TWO","THREE","FOUR","FIVE","SIX","SEVEN","EIGHT","NINE","TEN","JACK","QUEEN","KING","ACE"};
    //private final String[] suitArray = {"CLUBS","DIAMONDS","HEARTS","SPADES"};
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
        for(int i = 0; i < numDecks; i++)
            System.arraycopy(getSingleDeck(),0,cardDeck,52*i,getSingleDeck().length);
    }

    public Card draw() {
        try { return cardDeck[top]; }
        finally { top++; }
    }

    /**
     * Shuffle (randomize order of) a game.Card[] deck using Fisher-Yates algo
     */
    public void shuffle() {
        for(int i = cardDeck.length - 1; i > 0; i--) {
            int randomIndex = ThreadLocalRandom.current().nextInt(i + 1);
            while(randomIndex == i)
                randomIndex = ThreadLocalRandom.current().nextInt(i + 1);
            //Swap elements at i and randomIndex
            Card a = cardDeck[randomIndex];
            cardDeck[randomIndex] = cardDeck[i];
            cardDeck[i] = a;
        }
    }

    public int getTop() { return top; }

    public int getDeckSize() { return cardDeck.length; }

    public int getNumDecks() { return numDecks; }

    public void resetTop() { top = 0; }
}