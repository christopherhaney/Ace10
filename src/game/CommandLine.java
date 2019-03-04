package game;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class CommandLine  {
    Scanner keyboardScanner;
    Round round;
    private int totalMoney; //Starts as the money the player enters the game with, if it reaches 0 end game, if player quits add to database
    private int currentBet;
    private int totalDecks;

    public CommandLine() {
        keyboardScanner = new Scanner(System.in);
        System.out.print("Welcome to Ace10!\nHow many decks would you like to use? Please enter a number between 1 and 8: ");
        totalDecks = keyboardScanner.nextInt();
        while(totalDecks < 1 || totalDecks > 8) {
            System.out.println("Deck size must be between 1 and 8.\n");
            System.out.print("How many decks would you like to use? Please enter a number between 1 and 8: ");
            totalDecks = keyboardScanner.nextInt();
        }
        System.out.print("How much money would you like to bet? (Must be between '$1' and '$50000'): $");
        totalMoney = keyboardScanner.nextInt();
        while(totalMoney < 1 || totalMoney > 50000) {
            System.out.println("Money must be between $1 and $50000.\n");
            System.out.print("How much money would you like to bet? (Must be between '$1' and '$50000'): $");
            totalMoney = keyboardScanner.nextInt();
        }
        round = new Round(totalDecks);
        round.roundBegin();
        beginRound();
    }

    public void playGame() {
        String currentToken = keyboardScanner.nextLine().toLowerCase();
        if(currentToken.equals("h")) {
            playerHitPrint();
        }
        else if(currentToken.equals("s")) {
            playerStandPrint();
        }
        else if(currentToken.equals("y")) {
            round.resetRound();
            round.roundBegin();
            beginRound();
        }
        else if(currentToken.equals("n")) {
            System.out.println("Thanks for playing!");
            System.exit(0);
        }
    }

    public void beginRound() {
        System.out.print("Place your bet! (Must be between '$1' and your current total): $");
        currentBet = keyboardScanner.nextInt();
        while(currentBet < 1 || currentBet > totalMoney) {
            System.out.print("Bet must be between '$1' and your current total): $");
            currentBet = keyboardScanner.nextInt();
        }
        System.out.println("Beginning round...\n");
        printPlayerCards();
        printSoftValue();
        printInitialDealerCards();
        if(round.isPlayerBlackjack) {
            round.playerStand();
            finishRound();
        }
        if(round.isDealerBlackjack) {
            finishRound();
        }
        else {
            System.out.print("\nWould you like to hit or stand? Type 'h' for hit or 's' for stand: ");
        }
    }

    public void playerHitPrint() {
        round.playerHit();
        System.out.println("Hit!\n");
        printPlayerCards();
        printSoftValue();
        if(round.getPlayerHardValue() > 21) {
            System.out.println("\nBust!");
            round.dealerDraws();
            finishRound();
        }
        else if(round.getPlayerHardValue() == 21 || round.getPlayerSoftValue() == 21) {
            round.dealerDraws();
            finishRound();
        }
        else {
            printInitialDealerCards();
            System.out.print("\nWould you like to hit or stand? Type 'h' for hit or 's' for stand: ");
        }
    }

    public void playerStandPrint() {
        System.out.println("Stand!");
        round.playerStand();
        finishRound();
    }

    /**
     * Print the player's soft value if <= 21 or != to the hard value
     */
    public void printSoftValue() {
        if(round.getPlayerSoftValue() == round.getPlayerHardValue()) {
            System.out.println("\nPlayer Hard Value: " + round.getPlayerHardValue());
        }
        else {
            System.out.println("\nPlayer Soft Value: " + round.getPlayerSoftValue());
            System.out.println("Player Hard Value: " + round.getPlayerHardValue());
        }
    }

    public void printPlayerCards() {
        int i = 0;
        try {
            System.out.print("Player's Cards:");
            TimeUnit.SECONDS.sleep(1);
            while(i < round.getPlayerHand().size()) {
                System.out.print(round.getPlayerHand().get(i).getUnicodeString());
                i++;
                TimeUnit.SECONDS.sleep(1);
            }
            TimeUnit.SECONDS.sleep(1);
        }
        catch(InterruptedException e) {
            System.err.println("Sleep delay error.");
        }
    }

    public void printAllDealerCards() {
        try {
            int i = 0;
            System.out.print("\nDealer's Cards: ");
            while(i < round.getDealerHand().size()) {
                TimeUnit.SECONDS.sleep(1);
                System.out.print(round.getDealerHand().get(i).getUnicodeString());
                i++;
            }
            if(round.getFinalDealerValue() > 21) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("\nDealer busted!\n");
                TimeUnit.SECONDS.sleep(1);
            }
            else {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("\nFinal dealer value is: " + round.getFinalDealerValue());
                System.out.println("Final player value is: " + round.getFinalPlayerValue() + "\n");
                TimeUnit.SECONDS.sleep(1);
            }
        }
        catch(InterruptedException e) {
            System.err.println("Sleep delay error.");
        }
    }

    public void printInitialDealerCards() {
        try {
            int i = 1;
            System.out.print("Dealer's Cards: ");
            System.out.print("\uD83C\uDCA0");
            TimeUnit.SECONDS.sleep(1);
            while(i < round.getDealerHand().size()) {
                System.out.print(round.getDealerHand().get(i).getUnicodeString());
                i++;
                TimeUnit.SECONDS.sleep(1);
            }
            TimeUnit.SECONDS.sleep(1);
            System.out.println("\nDealer's revealed total: " + round.getDealerHand().get(1).getValue());
        }
        catch(InterruptedException e) {
            System.err.println("Sleep delay error.");
        }
    }

    /**
     *
     */
    public void finishRound() {
        round.calculateFinalValues();
        Round.Winner finalWinner = round.checkWinner(round.getFinalPlayerValue(),round.getFinalDealerValue());
        round.payOut(finalWinner);
        printAllDealerCards();
        if(finalWinner == Round.Winner.PLAYER && round.isPlayerBlackjack) {
            totalMoney += ((currentBet * 3)/2); //ALLOW PLAYER TO SET CUSTOM PAYOUT
            System.out.println("Blackjack!!! Total money is now: $" + totalMoney + "\n");
        }
        if(finalWinner == Round.Winner.PLAYER) {
            if(round.isPlayerBlackjack) {
                totalMoney += ((currentBet * 3)/2); //ALLOW PLAYER TO SET CUSTOM PAYOUT
                System.out.println("Blackjack!!! Total money is now: $" + totalMoney + "\n");
            }
            else {
                totalMoney += currentBet;
                System.out.println("You win!\nTotal money is now: $" + totalMoney + "\n");
            }
        }
        else if(finalWinner == Round.Winner.DEALER) {
            if(round.isDealerBlackjack) {
                totalMoney -= currentBet;
                System.out.println("Dealer blackjack!!! Bet lost.\nTotal money is now: $" + totalMoney + "\n");
            }
            else {
                totalMoney -= currentBet;
                System.out.println("Dealer wins! Bet lost.\nTotal money is now: $" + totalMoney + "\n");
            }
        }
        else if(finalWinner == Round.Winner.PUSH) {
            if(round.isDealerBlackjack && round.isPlayerBlackjack) {
                System.out.println("Double blackjack!!! Tough luck. Bet returned.\nTotal money is now: $" + totalMoney + "\n");
            }
            System.out.println("Push! Bet returned.\nTotal money is now: $" + totalMoney + "\n");
        }
        System.out.print("Play again? 'y' for yes or 'n' for no: ");
    }
}