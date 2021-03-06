package game;

public class Card {
    private int value; //The card's numeric value, 1-10 for numbered cards, 10 for face cards, 11 for aces
    private String rank; //The card's rank, ONE through TEN, JACK, QUEEN, KING, ACE
    private String suit; //The card's suit, CLUBS, HEARTS, SPADES, DIAMONDS
    private String unicodeString; //Java UTF-8 character of the card

    /**
     * Default card constructor
     * @param value: The numeric value of the card
     * @param rank: The string rank of the card in ascending order of value (TWO through ACE)
     * @param suit: The card's suit (HEARTS,CLUBS,DIAMONDS,SPADES)
     */
    public Card (int value, String rank, String suit,String unicodeString) {
        this.value = value;
        this.rank = rank;
        this.suit = suit;
        this.unicodeString = unicodeString;
    }

    public int getValue() {
        return value;
    }

    public String getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }

    public String getUnicodeString() {
        return unicodeString;
    }
}