package game;

public class SingleDeck {

    private static final Card[] singleDeck = {
            new Card(2, "TWO", "CLUBS"),
            new Card(3, "THREE", "CLUBS"),
            new Card(4, "FOUR", "CLUBS"),
            new Card(5, "FIVE", "CLUBS"),
            new Card(6, "SIX", "CLUBS"),
            new Card(7, "SEVEN", "CLUBS"),
            new Card(8, "EIGHT", "CLUBS"),
            new Card(9, "NINE", "CLUBS"),
            new Card(10, "TEN", "CLUBS"),
            new Card(10, "JACK", "CLUBS"),
            new Card(10, "KING", "CLUBS"),
            new Card(10, "QUEEN", "CLUBS"),
            new Card(11, "ACE", "CLUBS"),
            new Card(2, "TWO", "DIAMONDS"),
            new Card(3, "THREE", "DIAMONDS"),
            new Card(4, "FOUR", "DIAMONDS"),
            new Card(5, "FIVE", "DIAMONDS"),
            new Card(6, "SIX", "DIAMONDS"),
            new Card(7, "SEVEN", "DIAMONDS"),
            new Card(8, "EIGHT", "DIAMONDS"),
            new Card(9, "NINE", "DIAMONDS"),
            new Card(10, "TEN", "DIAMONDS"),
            new Card(10, "JACK", "DIAMONDS"),
            new Card(10, "KING", "DIAMONDS"),
            new Card(10, "QUEEN", "DIAMONDS"),
            new Card(11, "ACE", "DIAMONDS"),
            new Card(2, "TWO", "HEARTS"),
            new Card(3, "THREE", "HEARTS"),
            new Card(4, "FOUR", "HEARTS"),
            new Card(5, "FIVE", "HEARTS"),
            new Card(6, "SIX", "HEARTS"),
            new Card(7, "SEVEN", "HEARTS"),
            new Card(8, "EIGHT", "HEARTS"),
            new Card(9, "NINE", "HEARTS"),
            new Card(10, "TEN", "HEARTS"),
            new Card(10, "JACK", "HEARTS"),
            new Card(10, "KING", "HEARTS"),
            new Card(10, "QUEEN", "HEARTS"),
            new Card(11, "ACE", "HEARTS"),
            new Card(2, "TWO", "SPADES"),
            new Card(3, "THREE", "SPADES"),
            new Card(4, "FOUR", "SPADES"),
            new Card(5, "FIVE", "SPADES"),
            new Card(6, "SIX", "SPADES"),
            new Card(7, "SEVEN", "SPADES"),
            new Card(8, "EIGHT", "SPADES"),
            new Card(9, "NINE", "SPADES"),
            new Card(10, "TEN", "SPADES"),
            new Card(10, "JACK", "SPADES"),
            new Card(10, "KING", "SPADES"),
            new Card(10, "QUEEN", "SPADES"),
            new Card(11, "ACE", "SPADES")
    };

    public static Card[] getSingleDeck() {
        return singleDeck;
    }
}