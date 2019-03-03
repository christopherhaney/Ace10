package game;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class CommandLine  {
    Scanner keyboardScanner;
    Round round;
    private int totalMoney; //Starts as the money the player enters the game with, if it reaches 0 end game, if player quits add to database
    int totalDecks;

    public CommandLine() {
        keyboardScanner = new Scanner(System.in);
        System.out.println("Welcome to Ace10! How many decks would you like to use? (1-8)");
        totalMoney = 100;
        round = new Round(keyboardScanner.nextInt());
        round.roundBegin();
        beginRound();
    }

    public void playGame() {
        String currentToken = keyboardScanner.nextLine().toLowerCase();
        if(currentToken.equals("hit")) {
            playerHitPrint();
        }
        else if(currentToken.equals("stand")) {
            playerStandPrint();
        }
        else if(currentToken.equals("yes")) {
            round.roundBegin();
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
        if(round.getPlayerHardValue() > 21) {
            System.out.println("\nBust!\n");
            finishRound();
        }
        else {
            System.out.println("Player Hard Value: " + round.getPlayerHardValue() + "\n");
            System.out.println("\nDealer's face card: " + round.getDealerHand().get(1).getRank() + " of " + round.getDealerHand().get(1).getSuit());
            System.out.println("Dealer's revealed total: " + round.getDealerHand().get(1).getValue());
        }
    }

    public void playerStandPrint() {
        System.out.println("Stand!\n");
        round.playerStand();
        finishRound();
    }

    /**
     * Print the player's soft value if <= 21 or != to the hard value
     */
    public void printSoftValue() {
        if(round.getPlayerSoftValue() <= 21 && round.getPlayerSoftValue() != round.getPlayerHardValue()) {
            System.out.println("Player Soft Value: " + round.getPlayerSoftValue());
        }
    }

    public void printPlayerCards() {
        int i = 0;
        try {
            while(i < round.getPlayerHand().size()) {
                System.out.println("Player Card " + (i + 1) + ": " + round.getPlayerHand().get(i).getRank() + " of " + round.getPlayerHand().get(i).getSuit());
                i++;
                TimeUnit.SECONDS.sleep(1);
            }
        }
        catch(InterruptedException e) {
            System.err.println("Sleep delay error.");
        }
    }

    public void printDealerCards() {
        int i = 2;
        System.out.println("Dealer's face card: " + round.getDealerHand().get(1).getRank() + " of " + round.getDealerHand().get(1).getSuit());
        try {
            TimeUnit.SECONDS.sleep(1);
            System.out.println("Dealer's revealed card: " + round.getDealerHand().get(0).getRank() + " of " + round.getDealerHand().get(0).getSuit());
            while(i < round.getDealerHand().size()) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("Dealer Card " + (i + 1) + ": " + round.getDealerHand().get(i).getRank() + " of " + round.getDealerHand().get(i).getSuit());
                i++;
            }
            System.out.println("Final dealer value is: " + round.getFinalDealerValue());
            TimeUnit.SECONDS.sleep(1);
            System.out.println("Final player value is: " + round.getFinalPlayerValue() + "\n");
            TimeUnit.SECONDS.sleep(1);
        }
        catch(InterruptedException e) {
            System.err.println("Sleep delay error.");
        }
    }

    public void finishRound() {
        round.calculateFinalValues();
        Boolean finalWinner = round.checkWinner(round.getFinalPlayerValue(),round.getFinalDealerValue());
        round.payOut(finalWinner);
        printDealerCards();
        if(finalWinner) {
            System.out.println("You win! Bet returned.\nTotal money is now: " + totalMoney + "\n");
        }
        else if(!finalWinner) {
            System.out.println("Dealer wins! Bet lost.\nTotal money is now: " + totalMoney + "\n");
        }
        else if(finalWinner == null) {
            System.out.println("Push! Bet returned.\nTotal money is now: " + totalMoney + "\n");
        }
        round.resetRound();
        System.out.println("Play again? 'Yes' or 'No'");
    }
}