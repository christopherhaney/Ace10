package game;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class CommandLine {
    Scanner keyboardScanner;
    Round round;
    int totalMoney;
    int totalDecks;

    public CommandLine() {
        keyboardScanner = new Scanner(System.in);
        totalMoney = 100;
        round = new Round(1);
        round.roundBegin();
        beginRound();
    }

    public void playGame() {
        String currentToken = keyboardScanner.nextLine();
        if(currentToken.equals("hit")) {
            playerHitPrint();
        }
        else if(currentToken.equals("stand")) {
            playerStandPrint();
        }
        else if(currentToken.equals("yes")) {
            beginRound();
        }
        else if(currentToken.equals("no")) {
            System.out.println("Thanks for playing!");
            System.exit(0);
        }
    }

    public void beginRound() {
        System.out.println("Beginning round...\n");
        printPlayerCards();
        if(round.getPlayerSoftValue() == round.getPlayerHardValue()) {
            System.out.println("Player Hard Value: " + round.getPlayerHardValue());
        }
        else {
            System.out.println("Player Soft Value: " + round.getPlayerSoftValue());
            System.out.println("Player Hard Value: " + round.getPlayerHardValue());
        }
        System.out.println("\nDealer's face card: " + round.getDealerHand().get(1).getRank() + " of " + round.getDealerHand().get(1).getSuit());
        System.out.println("Dealer's revealed total: " + round.getDealerHand().get(1).getValue());
    }

    public void playerHitPrint() {
        round.playerHit();
        System.out.println("Hit!\n");
        printPlayerCards();
        printSoftValue();
        System.out.println("Player Hard Value: " + round.getPlayerHardValue() + "\n");
        System.out.println("\nDealer's face card: " + round.getDealerHand().get(1).getRank() + " of " + round.getDealerHand().get(1).getSuit());
        System.out.println("Dealer's revealed total: " + round.getDealerHand().get(1).getValue());
    }

    public void playerStandPrint() {
        System.out.println("Stand!\n");
        round.playerStand();
        finishRound();
    }

    /**
     * Method to print the player's soft value if <= 21 OR != to the hard value
     */
    public void printSoftValue() {
        if(round.getPlayerSoftValue() <= 21 && round.getPlayerSoftValue() != round.getPlayerHardValue()) {
            System.out.println("Player Soft Value: " + round.getPlayerSoftValue());
        }
    }

    public void printPlayerCards() {
        int i = 0;
        while(i < round.getPlayerHand().size()) {
            System.out.println("Player Card " + (i+1) + ": " + round.getPlayerHand().get(i).getRank() + " of " + round.getPlayerHand().get(i).getSuit());
            i++;
        }
    }

    public void printDealerCards() {
        int i = 2;
        System.out.println("\nDealer's face card: " + round.getDealerHand().get(1).getRank() + " of " + round.getDealerHand().get(1).getSuit());
        System.out.println("\nDealer's revealed card: " + round.getDealerHand().get(0).getRank() + " of " + round.getDealerHand().get(0).getSuit());
        while(i < round.getDealerHand().size()) {
            System.out.println("Dealer Card " + (i+1) + ": " + round.getDealerHand().get(i).getRank() + " of " + round.getDealerHand().get(i).getSuit());
            i++;
        }
    }

    public void finishRound() {
        round.calculateFinalValues();
        Boolean finalWinner = round.checkWinner(round.getFinalPlayerValue(),round.getFinalDealerValue());
        round.payOut(finalWinner);
        if(finalWinner) {
            System.out.println("You win! Bet returned.\nTotal money is now: " + totalMoney + "\n");
        }
        else if(!finalWinner) {
            System.out.println("Dealer wins! Bet lost.\nTotal money is now: " + totalMoney + "\n");
        }
        else if(finalWinner == null) {
            System.out.println("Push! Bet returned.\nTotal money is now: " + totalMoney + "\n");
        }
        System.out.println("Play again? 'yes' or 'no");
        //round.resetRound();
    }
}