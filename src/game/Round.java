package game;
import java.util.ArrayList;

public class Round extends AdvancedRules {
    private int dealerHardValue;
    private int dealerSoftValue;
    private int playerHardValue;
    private int playerSoftValue;
    private int finalPlayerValue;
    private int finalDealerValue;
    private int playerHitCount; //Used to determine if split or double down is allowed
    private Deck deck;
    private Card currentPlayerCard; //The current card that will be dealt to the player
    private Card currentDealerCard; //The current card that will be dealt to the dealer
    ArrayList<ArrayList<Card>> allPlayerHands = new ArrayList<>(2); //Initial capacity of only two hands since multi-splits are very rare
    ArrayList<Card> dealerHand = new ArrayList<>(); //In standard hand, first card is always hidden,

    public enum Winner {
        PLAYER,
        DEALER,
        PUSH;
    }

    public Round(int totalDecks) {
        deck = new Deck(totalDecks);
        deck.shuffle();
        allPlayerHands.add(0,new ArrayList<>());
        allPlayerHands.add(1,new ArrayList<>());
        dealerHardValue = 0;
        playerHardValue = 0;
        playerHitCount = 0;
    }

    public void roundBegin() {
        for(int i = 0; i < 2; i++) {
            currentPlayerCard = deck.draw();
            allPlayerHands.get(0).add(currentPlayerCard);
            playerHardValue += aceValueHard(currentPlayerCard);
            playerSoftValue += aceValueSoft(currentPlayerCard);
            currentDealerCard = deck.draw();
            dealerHand.add(currentDealerCard);
            dealerHardValue += aceValueHard(currentDealerCard);
            dealerSoftValue += aceValueSoft(currentDealerCard);
        }
        insuranceCheck(dealerHand.get(1));
        blackJackCheck(dealerSoftValue,playerSoftValue);
        splitCheck(allPlayerHands.get(0));
    }

    /**
     * When an ace is hit, make it worth 1 instead of 11 for the hard counter
     */
    public int aceValueHard(Card currentPlayerCard) {
        if(currentPlayerCard.getRank().equals("ACE")) {
            return 1;
        }
        return currentPlayerCard.getValue(); //If it's not an ace just return the normal value
    }

    /**
     * When an ace is hit, determine whether it should be worth 11 or 1 for the soft counter
     */
    public int aceValueSoft(Card currentPlayerCard) {
        if(currentPlayerCard.getRank().equals("ACE")) {
            if(currentPlayerCard.getValue() + playerSoftValue > 21) {
                return 1; //In the event the hand has more than one ace, subsequent aces will need to be worth 1
            }
            return 11;
        }
        return currentPlayerCard.getValue(); //If it's not an ace just return the normal value
    }

    public void playerHit() {
        currentPlayerCard = deck.draw();
        allPlayerHands.get(0).add(currentPlayerCard);
        playerHitCount++;
        playerHardValue += aceValueHard(currentPlayerCard);
        playerSoftValue += aceValueSoft(currentPlayerCard);
    }

    /**
     * If the player stands, let the dealer draw
     */
    public void playerStand() {
        dealerDraws();
    }

    /**
     * Dealer draws after player reaches 21, busts or stands
     */
    public void dealerDraws() {
        while(dealerHardValue < 16 || dealerSoftValue < 17) { //ALLOW PLAYER TO CHANGE VALUES DEALER STANDS ON!!!!!!!!
            currentDealerCard = deck.draw();
            dealerHand.add(currentDealerCard);
            dealerHardValue += aceValueHard(currentDealerCard);
            dealerSoftValue += aceValueSoft(currentDealerCard);
        }
    }

    public void calculateFinalValues() {
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
    }

    /**
     * Check whether the dealer or player one
     * Note: Uses custom Winner enum instead of boolean since there are three win states
     * @return Winner.PLAYER if player wins, Winner.DEALER if dealer wins, Winner.PUSH if push
     */
    public Winner checkWinner(int finalPlayerValue, int finalDealerValue) {
        if(finalPlayerValue > 21) {
            return Winner.DEALER;
        }
        else if(finalDealerValue > 21) {
            return Winner.PLAYER;
        }
        if(finalPlayerValue <= 21 && finalDealerValue <= 21) {
            if(finalPlayerValue > finalDealerValue) {
                return Winner.PLAYER;
            }
            else if(finalPlayerValue < finalDealerValue){
                return Winner.DEALER;
            }
        }
        return Winner.PUSH;
    }

    public void payOut(Winner roundWinner) {
        if(roundWinner == Winner.PLAYER) {
            if(playerSoftValue == 21 && allPlayerHands.get(0).size() == 2) {
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
        else if(roundWinner == Winner.DEALER) {
            //Lose
        }
        else if(roundWinner == Winner.PUSH) {
            //Push. give bet back to player
        }
    }

    public void resetRound() {
        dealerHardValue = dealerSoftValue = playerHardValue = playerSoftValue = finalPlayerValue = finalDealerValue = 0;
        isPlayerBlackjack = isDealerBlackjack = false;
        if(deck.getTop() > (deck.getDeckSize() / 4)) { //OVER FOUR FOR NOW, LET PLAYER CHANGE/CHECK STANDARD SHUFFLE RATE LATER
            deck.shuffle();
        }
        allPlayerHands.get(0).clear();
        allPlayerHands.get(1).clear();
        dealerHand.clear();
        playerHitCount = 0;
    }

    public int getPlayerHardValue() {
        return playerHardValue;
    }

    public int getDealerHardValue() {
        return dealerHardValue;
    }

    public int getPlayerSoftValue() {
        return playerSoftValue;
    }

    public int getDealerSoftValue() {
        return dealerSoftValue;
    }

    public int getplayerHitCount() {
        return playerHitCount;
    }

    public int getFinalPlayerValue() {
        return finalPlayerValue;
    }

    public int getFinalDealerValue() {
        return finalDealerValue;
    }

    public ArrayList<Card> getPlayerHand() {
        return allPlayerHands.get(0);
    }

    public ArrayList<Card> getDealerHand() {
        return dealerHand;
    }
}