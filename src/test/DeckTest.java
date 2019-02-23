package test;
import game.Card;
import game.Deck;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeckTest {

    /**
     * Make sure 1-8 deck shoes have every card they're supposed to (correct number of ranks and suits)
     */
    @Test
    public void test_validDeckCreation() {
        for(int i = 1; i < 9; i++)
        {
            Deck temp = new Deck(i);
            int[] correctAmounts = new int[14]; //At each index: Two through 10, Ace (11), clubs, diamonds, hearts, spades
            for(int j = 0; j < 52 * i; j++) {
                Card currentCard = temp.draw();
                int currentValue = currentCard.getValue();
                String currentSuit = currentCard.getSuit();
                if(currentSuit.equals("CLUBS"))
                    correctAmounts[10]++;
                else if(currentSuit.equals("DIAMONDS"))
                    correctAmounts[11]++;
                else if(currentSuit.equals("HEARTS"))
                    correctAmounts[12]++;
                else if(currentSuit.equals("SPADES"))
                    correctAmounts[13]++;
                for(int k = 2; k < 12; k++)
                    if(currentValue == k)
                        correctAmounts[k-2]++;
            }
            for(int l = 0; l < 8; l++)
                assertEquals(i * 4,correctAmounts[l]);
            assertEquals(i * 16,correctAmounts[8]);
            assertEquals(i * 4,correctAmounts[9]);
            assertEquals(i * 13,correctAmounts[10]);
            assertEquals(i * 13,correctAmounts[11]);
            assertEquals(i * 13,correctAmounts[12]);
            assertEquals(i * 13,correctAmounts[13]);
        }
    }
}
