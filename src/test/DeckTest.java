package test;
import game.Card;
import game.Deck;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.concurrent.ThreadLocalRandom;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeckTest {
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
     * Test to verify all cards are present after 1-8 deck creation and shuffle, and 1-deck cut
     */
    @Test
    public void test_deckIntact() {
        for(int i = 0; i < 8; i++) {
            test_validDeck(allDecksStandard[i]);
            test_validDeck(allDecksShuffled[i]);
            //test_distinctShuffle(allDecksShuffled[i],allDecksStandard[i]);
        }
        allDecksShuffled[0].cut(randomCutIndex); //Only need to cut 1-deck shoe
        test_validDeck(allDecksShuffled[0]);
    }

    /**
     * Test to verify 1-8 deck shoes have every card they're supposed to (correct amount of values and suits)
     */
    public void test_validDeck(Deck temp) {
        temp.setTop(0);
        int numDecks = temp.getNumDecks();
        int[] numberOfCards = new int[14]; //At each index: Two through 10, Ace (11), clubs, diamonds, hearts, spades
        for(int j = 0; j < 52 * numDecks; j++) {
            Card currentCard = temp.draw();
            int currentValue = currentCard.getValue();
            String currentSuit = currentCard.getSuit();
            if(currentSuit.equals("CLUBS"))
                numberOfCards[10]++;
            else if(currentSuit.equals("DIAMONDS"))
                numberOfCards[11]++;
            else if(currentSuit.equals("HEARTS"))
                numberOfCards[12]++;
            else if(currentSuit.equals("SPADES"))
                numberOfCards[13]++;
            numberOfCards[currentValue-2]++;
        }
        for(int k = 0; k < 8; k++) {
            assertEquals(numDecks * 4, numberOfCards[k]);
        }
        assertEquals(numDecks * 16,numberOfCards[8]);
        assertEquals(numDecks * 4,numberOfCards[9]);
        assertEquals(numDecks * 13,numberOfCards[10]);
        assertEquals(numDecks * 13,numberOfCards[11]);
        assertEquals(numDecks * 13,numberOfCards[12]);
        assertEquals(numDecks * 13,numberOfCards[13]);
    }

    /**
     * Test to verify 1 deck cuts correctly
     */
    @Test
    public void test_validCut() {
        Card[] tempArray = allDecksShuffled[0].getCopyOfDeck();
        allDecksShuffled[0].cut(randomCutIndex);
        for(int i = 0; i < randomCutIndex+1; i++) {
            assertEquals(tempArray[i], allDecksShuffled[0].getCard(51-randomCutIndex));
            randomCutIndex--;
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