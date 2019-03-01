package game;
import java.util.ArrayList;

public class Round extends AdvancedRules {
    private int dealerHardValue;
    private int dealerSoftValue;
    private int playerHardValue;
    private int playerSoftValue;
    private int playerHitCount; //Used to determine if split or double down is allowed
    private Deck deck;
    private Card currentPlayerCard; //The current card that will be dealt to the player
    private Card currentDealerCard; //The current card that will be dealt to the dealer
    ArrayList<ArrayList<Card>> allPlayerHands = new ArrayList<>(2); //Initial capacity of only two hands since multi-splits are very rare
    ArrayList<Card> dealerHand = new ArrayList<>(); //In standard hand, first card is always hidden,

    public Round() {
        deck = new Deck(1);
        deck.shuffle();
        dealerHardValue = 0;
        playerHardValue = 0;
        playerHitCount = 0;
    }

    public void roundBegin() {
        for(int i = 0; i < 2; i++) {
            currentPlayerCard = deck.draw();
            allPlayerHands.get(0).add(currentPlayerCard);
            playerHardValue += aceHard(currentPlayerCard);
            playerSoftValue += currentPlayerCard.getValue();
            currentDealerCard = deck.draw();
            dealerHand.add(currentDealerCard);
            dealerHardValue += aceHard(currentPlayerCard);
            dealerSoftValue += currentPlayerCard.getValue();
        }
        insuranceCheck(dealerHand.get(1));
        if(isBlackJack(dealerSoftValue,playerSoftValue)) {
            finishRound();
        }
        splitCheck(allPlayerHands.get(0),playerHitCount);
    }

    /**
     * When an ace is hit, return 1 for the hard value
     */
    public int aceHard(Card currentPlayerCard) {
        if(currentPlayerCard.getRank().equals("ACE")) {
            return 1;
        }
        return currentPlayerCard.getValue();
    }

    public void playerHit() {
        currentPlayerCard = deck.draw();
        playerHitCount++;
        playerHardValue += aceHard(currentPlayerCard);
        playerHardValue += currentPlayerCard.getValue();
        if(playerHardValue >= 21 || playerSoftValue == 21) {
            dealerDraws();
        }
    }

    /**
     * If the player stands, let the dealer draw
     */
    public void playerStand() {
        dealerDraws();
    }

    /**
     * Dealer draws after player
     */
    public void dealerDraws() {
        while(dealerHardValue < 17 || dealerSoftValue < 18) { //ALLOW PLAYER TO CHANGE VALUES DEALER STANDS ON!!!!!!!!
            currentDealerCard = deck.draw();
            dealerHardValue += aceHard(currentDealerCard);
            dealerSoftValue += currentDealerCard.getValue();
        }
        finishRound();
    }

    public void finishRound() {
        try {
            int finalPlayerValue;
            int finalDealerValue;
            if(playerSoftValue > playerHardValue && playerSoftValue <= 21) {
                finalPlayerValue = playerSoftValue;
            }
            else {
                finalPlayerValue = playerHardValue;
            }
            if(dealerSoftValue > dealerHardValue && dealerSoftValue <= 21) {
                finalDealerValue = dealerSoftValue;
            }
            else {
                finalDealerValue = dealerHardValue;
            }
            payOut(checkWinner(finalPlayerValue,finalDealerValue));
        }
        finally {
            resetRound();
        }
    }

    /**
     * Check whether the dealer or player one
     * Note: Boolean is used so that null represents a tie (push)
     * @return true if player wins, false if dealer wins, null if push
     */
    public Boolean checkWinner(int finalPlayerValue, int finalDealerValue) {
        if(finalPlayerValue > 21) {
            return false;
        }
        else if(finalDealerValue > 21) {
            return true;
        }
        if(finalPlayerValue <= 21 && finalDealerValue <= 21) {
            if (finalPlayerValue > finalDealerValue) {
                return true;
            }
            else {
                return false;
            }
        }
        return null;
    }

    public void payOut(Boolean isWinner) {
        if(isWinner) {
            if(dealerSoftValue == 21) {
                if(standardBlackjackPayout) {
                    //3:2 blackjack payout
                }
                else {
                    //6:5 blackjack payout
                }
            }
            else {
                //standard 2:1 payout
            }
        }
        else if(!isWinner) {
            //Keep bet
        }
        else if(isWinner == null) {
            //Push. give bet back to player
        }
    }

    public void resetRound() {
        dealerHardValue = 0;
        dealerSoftValue = 0;
        playerHardValue = 0;
        playerSoftValue = 0;
        if(deck.getTop() > (deck.getDeckSize() / 4)) { //OVER FOUR FOR NOW, LET PLAYER CHANGE/CHECK STANDARD SHUFFLE RATE LATER
            deck.shuffle();
        }
        allPlayerHands.clear();
        dealerHand.clear();
        playerHitCount = 0;
        roundBegin();
    }

    public int getPlayerHardValue() {
        return playerHardValue;
    }

    public int getDealerHardValue() {
        return dealerHardValue;
    }

    public int getplayerHitCount() {
        return playerHitCount;
    }

    public Deck getDeck() {
        return deck;
    }
}