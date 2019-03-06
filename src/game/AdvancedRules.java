package game;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;

import java.util.ArrayList;

public class AdvancedRules {
    public boolean doubleDownEnabled; //Gives the player the option to double their bet and hit one more card on their initial hand
    public boolean insuranceEnabled; //Gives the player the option to place a 2:1 insurance bet if the dealer's face card is ACE
    public boolean surrenderEnabled; //Gives the player the option to surrender after the initial draw
    public boolean splitEnabled; //Gives the player the option to split if initial hand is two cards of the same rank
    public boolean isPlayerBlackjack;
    public boolean isDealerBlackjack;
    public boolean isOriginalBlackjack;

    public boolean splitCheck(ArrayList<Card> playerHand) {
        if(playerHand.get(0).getValue() == playerHand.get(1).getValue()) {
            return true;
        }
        return false;
    }

    public boolean insuranceCheck(Card dealerFaceCard) {
        if(dealerFaceCard.getRank().equals("ACE")) {
            return true; 
        }
        return false;
    }

    public void blackjackCheck(int dealerSoftValue, int playerSoftValue) {
        if(playerSoftValue == 21) {
            isPlayerBlackjack = true;
        }
        if(dealerSoftValue == 21){
            isDealerBlackjack = true;
        }
        if(playerHand.contains(SingleDeck.getSingleDeckCard("ACE","SPADES")) && playerHand.contains(SingleDeck.getSingleDeckCard("JACK","SPADES")) || playerHand.contains(SingleDeck.getSingleDeckCard("JACK","CLUBS"))) {
            isOriginalBlackjack = true;
        }
    }

    //Bizarre rules for fun
    //public boolean splitsEnabled;
}