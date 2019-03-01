package game;

import java.util.ArrayList;

public class AdvancedRules {
    public boolean doubleDownEnabled; //Gives the player the option to double their bet and hit one more card on their initial hand
    public boolean insuranceEnabled; //Gives the player the option to place a 2:1 insurance bet if the dealer's face card is ACE
    public boolean surrenderEnabled; //Gives the player the option to surrender after the initial draw
    public boolean splitEnabled; //Gives the player the option to split if initial hand is two cards of the same rank
    public boolean standardBlackjackPayout; //3:2 if true, 6:5 if false

    public void doubleDown(int currentBet, int playerHitCount) {
        if(playerHitCount == 0) {
            currentBet = currentBet * 2;
            //subtract currentBet again from totalMoney
        }
    }

    public void takeInsurance() {

    }

    public void insuranceCheck(Card dealerFaceCard) {
        if(dealerFaceCard.getRank().equals("ACE")) {
            insuranceEnabled = true; //Once insuranceEnabled is true, immediately show user popup asking if they want to take it
        }
    }

    public boolean isBlackJack(int dealerSoftValue, int playerSoftValue) {
        if(dealerSoftValue == 21 || playerSoftValue == 21) {
            return true;
        }
        return false;
    }

    public void splitCheck(ArrayList<Card> playerHand, int playerHitCount) {
        if(playerHand.get(0).getRank() == playerHand.get(1).getRank() && playerHitCount == 0) {
            splitEnabled = true;
        }
    }

    public void splitHand(ArrayList<ArrayList<Card>> allPlayerHands, Deck deck) {
        allPlayerHands.get(1).add(allPlayerHands.get(0).remove(1));
        for(int i = 0; i < 2; i++) {
            allPlayerHands.get(i).add(deck.draw());
        }
    }

    //Bizarre rules for fun
    //public boolean splitsEnabled;
}