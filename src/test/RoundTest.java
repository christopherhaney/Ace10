package test;
import game.Card;
import game.Deck;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.concurrent.ThreadLocalRandom;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class RoundTest {
    int randomCutIndex = ThreadLocalRandom.current().nextInt(0,52); //Random int used to cut deck for all tests
    static Deck[] allDecksStandard = new Deck[8]; //Standard 1-8 deck shoes in order of suit, then rank (repeats every 52 cards)
    static Deck[] allDecksShuffled = new Deck[8]; //Randomly shuffled 1-8 deck shoes used for all tests

    /**
     * Method to initialize standard and shuffled deck arrays for test methods
     */
    @BeforeAll
    public static void initializeDecks() {
        for(int i = 0; i < 8; i++) {
            allDecksStandard[i] = new Deck(i+1);
            allDecksShuffled[i] = new Deck(i+1);
            allDecksShuffled[i].shuffle();
        }
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
