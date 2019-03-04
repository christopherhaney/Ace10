package test;
import game.Card;
import game.Deck;
import game.SingleDeck;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.concurrent.ThreadLocalRandom;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class RoundTest {
    int randomCutIndex = ThreadLocalRandom.current().nextInt(0,52); //Random int used to cut deck for all tests
    static Deck[] roundWinDeck;
    static Deck[] roundLossDeck;
    static Deck[] roundPushDeck;
    static Deck[] roundPlayerBlackjackDeck;
    static Deck[] roundDealerBlackjackDeck;
    private Deck[] roundDealerSoft21Deck;

    /**
     * Method to initialize standard and shuffled deck arrays for test methods
     */
    @BeforeAll
    public void initializeDecks() {
        for(int i = 0; i < 8; i++) {
            roundWinDeck[i] = new Deck(i+1);
            //roundLossDeck;
            //roundPushDeck;
            roundPlayerBlackjackDeck[i] = new Deck(i+1);
            roundDealerBlackjackDeck[i].shuffle();
        }
        //roundDealerSoft21Deck = {SingleDeck.getSingleDeckCard("NINE","SPADES"),SingleDeck.getSingleDeckCard("FOUR","HEARTS")};
    }

    /**
     * Test to verify shuffle is completely distinct, MAY NEED TO IMPROVE SHUFFLE ALGO
     @Test
     public void test_distinctShuffle(Deck tempShuffled, Deck tempStandard) {
     for(int i = 0; i < tempShuffled.getDeckSize(); i++) {
     assertNotEquals(tempShuffled.getCard(i), tempStandard.getCard(i));
     }
     }
     */
}
