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
    ArrayList<ArrayList<Card>> allPlayerHands = new ArrayList<>(1); //Initial capacity
    ArrayList<Card> dealerHand = new ArrayList<>(); //In standard hand, first card is always hidden,

    public enum Winner {
        PLAYER,
        DEALER,
        PUSH
    }

    public Round(int totalDecks) {
        deck = new Deck(totalDecks);
        deck.shuffle();
        allPlayerHands.add(0,new ArrayList<>());
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
        blackjackCheck(dealerSoftValue,playerSoftValue);
    }

    /**
     *
     * NOTE: THIS CURRENTLY DOESN'T ACCOUNT FOR CASES WHERE THE SAME HAND CAN BE SPLIT MULTIPLE TIMES
     * @param handToSplitIndex
     */
    public void splitHand(int handToSplitIndex) {
            allPlayerHands.add(new ArrayList<>()); //Create a new ArrayList (hand) for all player hands to ensure allPlayerHands can store the split
            ArrayList<Card> tempSplitHand = new ArrayList<>(); //Then create a temp list to store the split hand
            tempSplitHand.add(allPlayerHands.get(handToSplitIndex).remove(1)); //Move the last card from the hand-to-split into the temp hand
            allPlayerHands.get(handToSplitIndex).add(deck.draw()); //Draw a card for the original hand
            tempSplitHand.add(deck.draw()); //Then draw a card for the split hand
            if(!allPlayerHands.get(handToSplitIndex+1).isEmpty()) { //If the hand at the next index isn't empty, to keep the hands in order...
                //Move hand at handToSplitIndex+1 to handToSplitIndex+2
                ArrayList<Card> handToSwap = allPlayerHands.get(handToSplitIndex+1);
                allPlayerHands.get(handToSplitIndex+2).clear();
                allPlayerHands.get(handToSplitIndex+2).addAll(handToSwap);
            }
            allPlayerHands.get(handToSplitIndex+1).addAll(tempSplitHand); //Add the temp list to the now vacant handToSplitIndex+1 to complete the split
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
            if((currentPlayerCard.getValue() + playerSoftValue) > 21) {
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
        while(true) { //ALLOW PLAYER TO CHANGE VALUES DEALER STANDS ON!!!!!!!!
            if(dealerHardValue > 16 || dealerSoftValue > 17) {
                break;
            }
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
        //If player busts dealer always wins
        if(finalPlayerValue > 21) {
            return Winner.DEALER;
        }
        //Otherwise if the dealer busts and the player doesn't the player always wins
        else if(finalDealerValue > 21) {
            return Winner.PLAYER;
        }
        if(finalPlayerValue > finalDealerValue) {
            return Winner.PLAYER;
        }
        if(finalPlayerValue < finalDealerValue){
            return Winner.DEALER;
        }
        return Winner.PUSH; //If finalPlayerValue and finalDealerValue are equal push will be returned
    }

    /**
     * Reset variables and hands before beginning another round
     * @return true if shuffling deck to engage print statement/animation, false otherwise
     */
    public boolean resetRound() {
        dealerHardValue = dealerSoftValue = playerHardValue = playerSoftValue = finalPlayerValue = finalDealerValue = 0;
        isPlayerBlackjack = isDealerBlackjack = false;
        allPlayerHands.get(0).clear();
        allPlayerHands.get(1).clear();
        dealerHand.clear();
        playerHitCount = 0;
        if(deck.getTop() > (deck.getDeckSize() / 4)) { //OVER FOUR FOR NOW, LET PLAYER CHANGE/CHECK STANDARD SHUFFLE RATE LATER
            deck.shuffle();
            return true;
        }
        return false;
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

    public ArrayList<Card> getPlayerHand(int i) {
        return allPlayerHands.get(i);
    }

    public ArrayList<ArrayList<Card>> getAllPlayerHands() {
        return allPlayerHands;
    }

    public ArrayList<Card> getDealerHand() {
        return dealerHand;
    }
}