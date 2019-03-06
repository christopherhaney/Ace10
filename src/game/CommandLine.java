package game;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class CommandLine extends CommandLineSplit  {
    Scanner keyboardScanner;
    Round round;
    private int totalMoney; //Starts as the money the player enters the game with, if it reaches 0 end game, if player quits add values to database
    private int currentBet;
    private int totalDecks;

    public CommandLine() {
        keyboardScanner = new Scanner(System.in);
        System.out.print("Welcome to Ace10!\nHow many decks would you like to use? Please enter a number between 1 and 8: ");
        totalDecks = keyboardScanner.nextInt();
        round = new Round(totalDecks);
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
        else if(currentToken.equals("d")) {
            playerDoubleDownPrint();
        }
        else if(currentToken.equals("sp")) {
            round.splitHand(0); //FIGURE OUT HOW TO ADJUST INDEX FOR MULTISPLITS
            printInitialSplit(round);
        }
        else if(currentToken.equals("y")) {
            if(round.resetRound()) {
                System.out.println("Shuffling deck...");
            }
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
        totalMoney -= currentBet;
        System.out.println("Beginning round...\n");
        printPlayerCards();
        printSoftValue();
        printInitialDealerCards();
        specialBeginRoundChecks();
    }

    public void specialBeginRoundChecks() {
        if(round.isPlayerBlackjack) {
            round.playerStand();
            finishRound();
        }
        if(round.isDealerBlackjack) {
            finishRound();
        }
        if(round.splitCheck(round.getPlayerHand(0))) {
            System.out.print("\nYou can split your hand! Type 'h' to hit, 's' to stand, 'd' to double down or 'sp' to split your hand: ");
        }
        else {
            System.out.print("\nHow would you like to start? Type 'h' to hit, 's' to stand or 'd' to double down: ");
        }
    }

    public void playerHitPrint() {
        round.playerHit();
        System.out.println("Hit!\n");
        printPlayerCards();
        printSoftValue();
        if(round.getPlayerHardValue() > 21) {
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

    /**
     *
     */
    public void playerDoubleDownPrint() {
        System.out.println("Double down!!\n");
        totalMoney -= currentBet;
        currentBet += currentBet;
        round.playerHit();
        printPlayerCards();
        printSoftValue();
        round.dealerDraws();
        finishRound();
    }

    /**
     *
     */
    public void playerStandPrint() {
        System.out.println("Stand!");
        round.playerStand();
        finishRound();
    }

    /**
     * Print the player's soft value if <= 21 or != to the hard value
     */
    public void printSoftValue() {
        if(round.getPlayerSoftValue() == round.getPlayerHardValue() && round.getPlayerHardValue() < 21 || round.getPlayerSoftValue() > 21) {
            System.out.println("\nPlayer Hard Value: " + round.getPlayerHardValue());
        }
        else if(round.getPlayerSoftValue() == 21 || round.getPlayerHardValue() == 21) {
            System.out.println("\n21!");
        }
        else if(round.getPlayerHardValue() > 21) {
            System.out.println("\nBust!");
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
            while(i < round.getPlayerHand(0).size()) {
                System.out.print(round.getPlayerHand(0).get(i).getUnicodeString());
                i++;
                TimeUnit.SECONDS.sleep(1);
            }
            TimeUnit.SECONDS.sleep(1);
        }
        catch(InterruptedException e) {
            System.err.println("Sleep delay error.");
        }
    }

    public void printInitialDealerCards() {
        try {
            int i = 1;
            System.out.print("Dealer's Cards:");
            TimeUnit.SECONDS.sleep(1);
            System.out.print(round.getDealerHand().get(0).getUnicodeString());
            TimeUnit.SECONDS.sleep(1);
            System.out.print("\uD83C\uDCA0");
            TimeUnit.SECONDS.sleep(1);
            System.out.println("\nDealer's revealed total: " + round.getDealerHand().get(0).getValue());
            TimeUnit.SECONDS.sleep(1);
        }
        catch(InterruptedException e) {
            System.err.println("Sleep delay error.");
        }
    }

    /**
     *
     */
    public void printAllDealerCards() {
        try {
            int i = 2;
            System.out.print("\nDealer's Cards:" + round.getDealerHand().get(0).getUnicodeString() + "\uD83C\uDCA0");
            System.out.print("\r");
            TimeUnit.SECONDS.sleep(1);
            System.out.print("Dealer's Cards:" + round.getDealerHand().get(0).getUnicodeString() + round.getDealerHand().get(1).getUnicodeString());
            while(i < round.getDealerHand().size()) {
                TimeUnit.SECONDS.sleep(1);
                System.out.print(round.getDealerHand().get(i).getUnicodeString());
                i++;
            }
            if(round.getFinalDealerValue() > 21) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("\nDealer busts!\n");
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

    /**
     *
     */
    public void finishRound() {
        round.calculateFinalValues();
        Round.Winner finalWinner = round.checkWinner(round.getFinalPlayerValue(),round.getFinalDealerValue());
        printAllDealerCards();
        if(finalWinner == Round.Winner.PLAYER) {
            if(round.isPlayerBlackjack) {
                totalMoney += (currentBet * 2.5); //ALLOW PLAYER TO SET CUSTOM PAYOUT, 2.5 = 3:2, 2.2 = 6:5, FIND A WAY TO MULTIPLY RATIOS EASIER
                System.out.println("Blackjack!!! Total money is now: $" + totalMoney + "\n");
            }
            else {
                totalMoney += (currentBet * 2);
                System.out.println("You win!\nTotal money is now: $" + totalMoney + "\n");
            }
        }
        else if(finalWinner == Round.Winner.DEALER) {
            if(round.isDealerBlackjack) {
                System.out.println("Dealer blackjack!!! Bet lost.\nTotal money is now: $" + totalMoney + "\n");
            }
            else {
                System.out.println("Dealer wins! Bet lost.\nTotal money is now: $" + totalMoney + "\n");
            }
        }
        else if(finalWinner == Round.Winner.PUSH) {
            totalMoney += currentBet;
            if(round.isDealerBlackjack && round.isPlayerBlackjack) {
                System.out.println("Double blackjack!!! Tough luck. Bet returned.\nTotal money is now: $" + totalMoney + "\n");
            }
            System.out.println("Push! Bet returned.\nTotal money is now: $" + totalMoney + "\n");
        }
        System.out.print("Play again? 'y' for yes or 'n' for no: ");
    }
}