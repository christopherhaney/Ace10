package game;

public class Round {
    private int dealerHardValue;
    private int dealerSoftValue;
    private int playerHardValue;
    private int playerSoftValue;
    private int currentCardValue;
    private Deck deck;

    public enum Winner
    {
        PLAYER, DEALER, PUSH
    }

    public Round() {
        deck = new Deck(1);
        deck.shuffle();
        dealerHardValue = 0;
        playerHardValue = 0;
        currentCardValue = 0;
    }

    public void roundBegin() {
        dealerHardValue += deck.draw().getValue();
        playerHardValue += deck.draw().getValue();
        dealerHardValue += deck.draw().getValue();
        playerHardValue += deck.draw().getValue();
        blackJackCheck();
    }

    public void blackJackCheck() {
        if(playerHardValue == 21 || dealerHardValue == 21) {
            finishRound();
        }
    }

    public void playerHit() {
        currentCardValue = isAceEleven(deck.draw().getValue());
        playerHardValue += currentCardValue;
        if(playerHardValue >= 21) {
            finishRound();
        }
    }

    public void playerStand() {
        dealerDraws();
    }

    /**
     *
     */
    public void dealerDraws() {
        while(dealerHardValue < 17)
            dealerHardValue += isAceEleven(deck.draw().getValue());
        finishRound();
    }

    /**
     * Check who won and payout accordingly
     */
    public Winner finishRound() {
        try {
            if(playerHardValue > 21) {
                System.out.println("Finish! Winner is: Dealer!");
                System.out.println("Player had: " + playerHardValue);
                System.out.println("Dealer had: " + dealerHardValue);
                return Winner.DEALER;
            }
            if(dealerHardValue <= 21 && playerHardValue <= 21) {
                if (dealerHardValue > playerHardValue) {
                    System.out.println("Finish! Winner is: Dealer!");
                    System.out.println("Player had: " + playerHardValue);
                    System.out.println("Dealer had: " + dealerHardValue);
                    return Winner.DEALER;
                }
                else if (dealerHardValue < playerHardValue) {
                    System.out.println("Finish! Winner is: Player!");
                    System.out.println("Player had: " + playerHardValue);
                    System.out.println("Dealer had: " + dealerHardValue);
                    return Winner.PLAYER;
                }
                else {
                    System.out.println("Finish! Winner is: Player!");
                    System.out.println("Player had: " + playerHardValue);
                    System.out.println("Dealer had: " + dealerHardValue);
                    return Winner.PUSH;
                }
            }
            return Winner.PUSH;
        }
        finally {
            resetRound();
        }
    }

    public void resetRound() {
        dealerHardValue = 0;
        playerHardValue = 0;
        currentCardValue = 0;
        if(deck.getTop() > (deck.getDeckSize() / 4)) { //OVER FOUR FOR NOW, LET PLAYER CHANGE/CHECK STANDARD SHUFFLE RATE LATER
            deck.shuffle();
            deck.resetTop();
        }
        roundBegin();
    }

    public int getPlayerHardValue() {return playerHardValue; }

    public int getDealerHardValue() { return dealerHardValue; }

    public Deck getDeck() { return deck; }

    /**
     * When an ace is hit, determine whether it should be worth 11 or 1
     */
    public int isAceEleven(int cardValue) {
        if(cardValue == 11) {
            if(playerHardValue + cardValue > 21)
                return 1;
        }
        return 11;
    }
}
