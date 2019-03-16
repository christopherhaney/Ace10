package game;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Round extends AdvancedRules {
    private int dealerHardValue;
    private int dealerSoftValue;
    private int finalDealerValue;
    private int[] playerHardValue = new int[10];
    private int[] playerSoftValue = new int[10];
    private int[] finalPlayerValue = new int[10];
    private int[] playerHitCount = new int[10]; //Used to determine if split or double down is allowed
    private int currentHandIndex;
    private Deck deck;
    private Card currentPlayerCard; //The current card that will be dealt to the player
    private Card currentDealerCard; //The current card that will be dealt to the dealer
    LinkedList<ArrayList<Card>> allPlayerHands = new LinkedList<>();
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
    }

    public void roundBegin() {
        for(int i = 0; i < 2; i++) {
            currentPlayerCard = deck.draw();
            allPlayerHands.get(0).add(currentPlayerCard);
            playerHardValue[0] += aceValueHard(currentPlayerCard);
            playerSoftValue[0] += aceValueSoft(currentPlayerCard,playerSoftValue[0]);
            currentDealerCard = deck.draw();
            dealerHand.add(currentDealerCard);
            dealerHardValue += aceValueHard(currentDealerCard);
            dealerSoftValue += aceValueSoft(currentDealerCard,dealerSoftValue);
        }
        insuranceCheck(dealerHand.get(1));
        blackjackCheck(dealerSoftValue,playerSoftValue[0]);
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
    public int aceValueSoft(Card currentPlayerCard, int currentHandSoftValue) {
        if(currentPlayerCard.getRank().equals("ACE")) {
            if((currentPlayerCard.getValue() + currentHandSoftValue) > 21) {
                return 1; //In the event the hand has more than one ace, subsequent aces will need to be worth 1
            }
            return 11;
        }
        return currentPlayerCard.getValue(); //If it's not an ace just return the normal value
    }

    public void playerHit(int currentHandIndex) {
        currentPlayerCard = deck.draw();
        allPlayerHands.get(currentHandIndex).add(currentPlayerCard);
        playerHitCount[currentHandIndex]++;
        playerHardValue[currentHandIndex] += aceValueHard(currentPlayerCard);
        playerSoftValue[currentHandIndex] += aceValueSoft(currentPlayerCard,playerSoftValue[currentHandIndex]);
    }

    /**
     * If the player stands, let the dealer draw
     */
    public void playerStand() {
        currentHandIndex++;
        if(currentHandIndex == allPlayerHands.size()) {
            dealerDraws();
        }
    }

    /**
     *
     * @param handToSplitIndex
     */
    public void splitHand(int handToSplitIndex) {
        allPlayerHands.add(handToSplitIndex + 1,new ArrayList<>()); //Create a new ArrayList (hand) for allPlayerHands to ensure it can store the split
        allPlayerHands.get(handToSplitIndex + 1).add(allPlayerHands.get(handToSplitIndex).remove(1)); //Move the last card from the hand-to-split into the next hand
        allPlayerHands.get(handToSplitIndex).add(deck.draw()); //Draw a card for the original hand
        allPlayerHands.get(handToSplitIndex + 1).add(deck.draw()); //Then draw a card for the split hand
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
            dealerSoftValue += aceValueSoft(currentDealerCard,dealerSoftValue);
        }
    }

    public void calculateFinalPlayerValue(int currentHandIndex) {
        if(playerSoftValue[currentHandIndex] > playerHardValue[currentHandIndex] && playerSoftValue[currentHandIndex] <= 21) {
            finalPlayerValue[currentHandIndex] = playerSoftValue[currentHandIndex];
        }
        else {
            finalPlayerValue[currentHandIndex] = playerHardValue[currentHandIndex];
        }
    }

    public void calculateFinalDealerValue() {
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
    public void resetRound() {
        dealerHardValue = dealerSoftValue = finalDealerValue = currentHandIndex = 0;
        Arrays.fill(playerHardValue, 0); Arrays.fill(playerSoftValue,0); Arrays.fill(finalPlayerValue,0); Arrays.fill(playerHitCount,0);
        isPlayerBlackjack = isDealerBlackjack = false;
        allPlayerHands.clear();
        allPlayerHands.add(new ArrayList<>()); //Clear removes inner linked lists, so you must add a new one so that future rounds can start
        dealerHand.clear();
    }

    public boolean canShuffle() {
        if(deck.getTop() > (deck.getDeckSize() / 4)) { //OVER FOUR FOR NOW, LET PLAYER CHANGE/CHECK STANDARD SHUFFLE RATE LATER
            deck.shuffle();
            return true;
        }
        return false;
    }

    public int getPlayerHardValue(int currentHandIndex) {
        return playerHardValue[currentHandIndex];
    }

    public int getPlayerSoftValue(int currentHandIndex) {
        return playerSoftValue[currentHandIndex];
    }


    public int getFinalPlayerValue(int currentHandIndex) {
        return finalPlayerValue[currentHandIndex];
    }

    public int getFinalDealerValue() {
        return finalDealerValue;
    }

    public ArrayList<Card> getPlayerHand(int currentHandIndex) {
        return allPlayerHands.get(currentHandIndex);
    }

    public LinkedList<ArrayList<Card>> getAllPlayerHands() {
        return allPlayerHands;
    }

    public ArrayList<Card> getDealerHand() {
        return dealerHand;
    }

    public int getCurrentHandIndex() {
        return currentHandIndex;
    }

    public void setCurrentHandIndex(int resetIndex) {
        currentHandIndex = resetIndex;
    }
}