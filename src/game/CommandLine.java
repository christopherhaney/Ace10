package game;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class CommandLine extends CommandLineSplit {
    Scanner keyboardScanner;
    Round round;
    private double totalMoney; //Starts as the money the player enters the game with, if it reaches 0 end game, if player quits add values to database
    public int currentBet;
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
            printInitialSplit(round,keyboardScanner);
            if(round.splitCheck(round.getPlayerHand(0))) {
                printSplitPrompt(0);
            }
            else if(round.splitCheck(round.getPlayerHand(1)) && !round.splitCheck(round.getPlayerHand(0))) {
                printSplitPrompt(1);
            }
            System.out.print("\nHow would you like to start? Type 'h' to hit, 's' to stand or 'd' to double down: ");
        }
        else if(currentToken.equals("y")) {
            keyboardScanner.reset();
            round.resetRound();
            if(round.canShuffle()) {
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
        System.out.print("Place your bet! (Must be between '1' and your current total): $");
        while(keyboardScanner.hasNext()) {
            if(keyboardScanner.hasNextInt()) {
                currentBet = keyboardScanner.nextInt();
                if(currentBet < 1 || currentBet > totalMoney) {
                    System.out.print("Bet must be between '$1' and your current total): $");
                }
                else {
                    break;
                }
            }
            else {
                keyboardScanner.nextLine();
            }
        }
        totalMoney -= currentBet;
        System.out.println("Beginning round...\n");
        printPlayerCards(round.getCurrentHandIndex());
        printSoftValue();
        printInitialDealerCards();
        specialBeginRoundChecks();
    }

    public void specialBeginRoundChecks() {
        if(round.isPlayerBlackjack) {
            round.playerStand();
            finishRound();
        }
        else if(round.isDealerBlackjack) {
            finishRound();
        }
        else if(round.splitCheck(round.getPlayerHand(0))) {
            System.out.print("\nYou can split your hand! Type 'h' to hit, 's' to stand, 'd' to double down or 'sp' to split your hand: ");
        }
        else {
            System.out.print("\nHow would you like to start? Type 'h' to hit, 's' to stand or 'd' to double down: ");
        }
    }

    public void playerHitPrint() {
        round.playerHit(round.getCurrentHandIndex());
        System.out.println("Hit!\n");
        printPlayerCards(round.getCurrentHandIndex());
        printSoftValue();
        if(round.getPlayerHardValue(round.getCurrentHandIndex()) > 21) {
            System.out.println("Bust!");
            round.dealerDraws();
            finishRound();
        }
        else if(round.getPlayerHardValue(round.getCurrentHandIndex()) == 21 || round.getPlayerSoftValue(round.getCurrentHandIndex()) == 21) {
            round.dealerDraws();
            finishRound();
        }
        else {
            quickPrintInitialDealerCards();
            System.out.print("\nWould you like to hit or stand? Type 'h' for hit or 's' for stand: ");
        }
    }

    /**
     *
     */
    public void playerDoubleDownPrint() {
        if((currentBet * 2) > totalMoney) {
            System.out.print("Double down exceeds bankroll. Choose another option: ");
        }
        else {
            System.out.println("Double down!!\n");
            totalMoney -= currentBet;
            currentBet += currentBet;
            round.playerHit(round.getCurrentHandIndex());
            printPlayerCards(round.getCurrentHandIndex());
            printSoftValue();
            round.dealerDraws();
            finishRound();
        }
    }

    /**
     *
     */
    public void playerStandPrint() {
        System.out.println("Stand!");
        round.playerStand();
        if(round.getCurrentHandIndex() == round.getAllPlayerHands().size()) {
            finishRound();
        }
        else {

        }
    }

    /**
     * Print the player's soft value if <= 21 or != to the hard value
     */
    public void printSoftValue() {
        if(round.getPlayerSoftValue(0) == round.getPlayerHardValue(0)
                && round.getPlayerHardValue(0) < 21 || round.getPlayerSoftValue(0) > 21) {
            System.out.println("\nPlayer Hard Value: " + round.getPlayerHardValue(0));
        }
        else if(round.getPlayerSoftValue(0) == 21 || round.getPlayerHardValue(0) == 21) {
            System.out.println("\n21!");
        }
        else if(round.getPlayerHardValue(0) > 21) {
            System.out.println("\nBust!");
        }

        else {
            System.out.println("\nPlayer Soft Value: " + round.getPlayerSoftValue(0));
            System.out.println("Player Hard Value: " + round.getPlayerHardValue(0));
        }

    }

    public void printPlayerCards(int currentHandIndex) {
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

    public void quickPrintInitialDealerCards() {
            System.out.print("Dealer's Cards:");
            System.out.print(round.getDealerHand().get(0).getUnicodeString());
            System.out.print("\uD83C\uDCA0");
            System.out.println("\nDealer's revealed total: " + round.getDealerHand().get(0).getValue());
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
                System.out.println("Final player value is: " + round.getFinalPlayerValue(0) + "\n");
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
        round.calculateFinalPlayerValue(round.getCurrentHandIndex());
        round.calculateFinalDealerValue();
        Round.Winner finalWinner = round.checkWinner(round.getFinalPlayerValue(0),round.getFinalDealerValue());
        printAllDealerCards();
        if(finalWinner == Round.Winner.PLAYER) {
            if(round.isPlayerBlackjack) {
                totalMoney += ((double)currentBet * 2.5); //ALLOW PLAYER TO SET CUSTOM PAYOUT, 2.5 = 3:2, 2.2 = 6:5, FIND A WAY TO MULTIPLY RATIOS EASIER
                System.out.print("Blackjack!!! Total money is now: $" + totalMoney + "\n");
            }
            else {
                totalMoney += (currentBet * 2);
                System.out.print("You win!\n");
            }
        }
        else if(finalWinner == Round.Winner.DEALER) {
            if(round.isDealerBlackjack) {
                System.out.print("Dealer blackjack!!! Bet lost.\n");
            }
            else {
                System.out.print("Dealer wins! Bet lost.\n");
            }
        }
        else if(finalWinner == Round.Winner.PUSH) {
            totalMoney += currentBet;
            if(round.isDealerBlackjack && round.isPlayerBlackjack) {
                System.out.print("Double blackjack!!! Tough luck. Bet returned.\n");
            }
            System.out.print("Push! Bet returned.\n");
        }
        System.out.println("Total money is now: " + NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(totalMoney));
        if(totalMoney == 0) {
            try {
                System.out.println("\nNo money remaining. Game over. Thanks for playing!");
                TimeUnit.SECONDS.sleep(1);
                System.exit(0);
            }
            catch(InterruptedException e) {
                System.err.println("Sleep delay error.");
            }
        }
        else {
            System.out.print("\nPlay again? 'y' for yes or 'n' for no: ");
        }
    }
}