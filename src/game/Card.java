package game;

public class Card {
    private int value; //The card's numeric value, 1-10 for numbered cards, 10 for face cards, 11 for aces
    private String rank; //The card's rank, ONE through TEN, JACK, QUEEN, KING, ACE
    private String suit; //The card's suit, CLUBS, HEARTS, SPADES, DIAMONDS

    /**
     * Default card constructor that takes in a value and sets it's rank accordingly
     * @param value
     */
    public Card (int value, String rank, String suit) {
        this.value = value;
        this.rank = rank;
        this.suit = suit;
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
}