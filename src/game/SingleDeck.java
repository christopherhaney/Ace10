package game;

public class SingleDeck {
    private static final Card[] singleDeck = {
            new Card(2, "TWO", "CLUBS","\uD83C\uDCD2"),
            new Card(3, "THREE", "CLUBS","\uD83C\uDCD3"),
            new Card(4, "FOUR", "CLUBS","\uD83C\uDCD4"),
            new Card(5, "FIVE", "CLUBS","\uD83C\uDCD5"),
            new Card(6, "SIX", "CLUBS","\uD83C\uDCD6"),
            new Card(7, "SEVEN", "CLUBS","\uD83C\uDCD7"),
            new Card(8, "EIGHT", "CLUBS","\uD83C\uDCD8"),
            new Card(9, "NINE", "CLUBS", "\uD83C\uDCD9"),
            new Card(10, "TEN", "CLUBS","\uD83C\uDCDA"),
            new Card(10, "JACK", "CLUBS","\uD83C\uDCDB"),
            new Card(10, "QUEEN", "CLUBS","\uD83C\uDCDE"),
            new Card(10, "KING", "CLUBS","\uD83C\uDCDE"),
            new Card(11, "ACE", "CLUBS","\uD83C\uDCD1"),
            new Card(2, "TWO", "DIAMONDS",	"\uD83C\uDCC2"),
            new Card(3, "THREE", "DIAMONDS",	"\uD83C\uDCC3"),
            new Card(4, "FOUR", "DIAMONDS",	"\uD83C\uDCC4"),
            new Card(5, "FIVE", "DIAMONDS",	"\uD83C\uDCC5"),
            new Card(6, "SIX", "DIAMONDS",	"\uD83C\uDCC6"),
            new Card(7, "SEVEN", "DIAMONDS",	"\uD83C\uDCC7"),
            new Card(8, "EIGHT", "DIAMONDS",	"\uD83C\uDCC8"),
            new Card(9, "NINE", "DIAMONDS",	"\uD83C\uDCC9"),
            new Card(10, "TEN", "DIAMONDS",	"\uD83C\uDCCA"),
            new Card(10, "JACK", "DIAMONDS",	"\uD83C\uDCCB"),
            new Card(10, "QUEEN", "DIAMONDS",	"\uD83C\uDCCD"),
            new Card(10, "KING", "DIAMONDS",	"\uD83C\uDCCE"),
            new Card(11, "ACE", "DIAMONDS", "\uD83C\uDCC1"),
            new Card(2, "TWO", "HEARTS","\uD83C\uDCB2"),
            new Card(3, "THREE", "HEARTS","\uD83C\uDCB3"),
            new Card(4, "FOUR", "HEARTS","\uD83C\uDCB4"),
            new Card(5, "FIVE", "HEARTS","\uD83C\uDCB5"),
            new Card(6, "SIX", "HEARTS","\uD83C\uDCB6"),
            new Card(7, "SEVEN", "HEARTS","\uD83C\uDCB7"),
            new Card(8, "EIGHT", "HEARTS","\uD83C\uDCB8"),
            new Card(9, "NINE", "HEARTS","\uD83C\uDCB9"),
            new Card(10, "TEN", "HEARTS","\uD83C\uDCBA"),
            new Card(10, "JACK", "HEARTS","\uD83C\uDCBB"),
            new Card(10, "QUEEN", "HEARTS","\uD83C\uDCBD"),
            new Card(10, "KING", "HEARTS","\uD83C\uDCBE"),
            new Card(11, "ACE", "HEARTS","\uD83C\uDCB1"),
            new Card(2, "TWO", "SPADES","\uD83C\uDCA2"),
            new Card(3, "THREE", "SPADES","\uD83C\uDCA3"),
            new Card(4, "FOUR", "SPADES","\uD83C\uDCA4"),
            new Card(5, "FIVE", "SPADES","\uD83C\uDCA5"),
            new Card(6, "SIX", "SPADES","\uD83C\uDCA6"),
            new Card(7, "SEVEN", "SPADES","\uD83C\uDCA7"),
            new Card(8, "EIGHT", "SPADES","\uD83C\uDCA8"),
            new Card(9, "NINE", "SPADES","\uD83C\uDCA9"),
            new Card(10, "TEN", "SPADES","\uD83C\uDCAA"),
            new Card(10, "JACK", "SPADES","\uD83C\uDCAB"),
            new Card(10, "QUEEN", "SPADES","\uD83C\uDCAD"),
            new Card(10, "KING", "SPADES","\uD83C\uDCAE"),
            new Card(11, "ACE", "SPADES","\uD83C\uDCA1")
    };

    public static Card[] getSingleDeck() {
        return singleDeck;
    }

    public static Card getSingleDeckCard(String rank, String suit) {
        for(int i = 0; i < singleDeck.length; i++) {
            if(singleDeck[i].getRank().equals(rank) && singleDeck[i].getSuit().equals(suit)) {
                return singleDeck[i];
            }
        }
        return singleDeck[0]; //If card wasn't found
    }
}