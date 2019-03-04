package game;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class CommandLine  {
    Scanner keyboardScanner;
    Round round;
    private int totalMoney; //Starts as the money the player enters the game with, if it reaches 0 end game, if player quits add to database
    private int totalDecks;

    public CommandLine() {
        keyboardScanner = new Scanner(System.in);
        System.out.println("Welcome to Ace10! How many decks would you like to use? (1-8)");
        while(keyboardScanner.nextInt() < 1 || keyboardScanner.nextInt() > 8) {
            System.out.println("Deck size must be between 1 and 8.");
        }
        totalDecks = keyboardScanner.nextInt();
        System.out.println("How much money would you like to bet? (Must be between '$1' and '$50000')");
        while(keyboardScanner.nextInt() < 1 || keyboardScanner.nextInt() > 8) {
            System.out.println("Money must be between $1 and $50000.");
        }
        totalMoney = keyboardScanner.nextInt();
        round = new Round(totalDecks);
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
            round.resetRound();
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
        System.out.println("\nDealer's face card: " + readableCardString(round.getDealerHand().get(1)));
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
            System.out.println("\nDealer's face card: " + readableCardString(round.getDealerHand().get(1)));
            System.out.println("Dealer's revealed total: " + round.getDealerHand().get(1).getValue());
        }
        System.out.println("Would you like to hit or stand? Type 'Hit' or 'Stand'");
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
                System.out.println("Player Card " + (i + 1) + ": " + readableCardString(round.getPlayerHand().get(i)));
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
        System.out.println("Dealer's face card: " + readableCardString(round.getDealerHand().get(1)));
        try {
            TimeUnit.SECONDS.sleep(1);
            System.out.println("Dealer's revealed card: " + readableCardString(round.getDealerHand().get(0)));
            while(i < round.getDealerHand().size()) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("Dealer Card " + (i + 1) + ": " + readableCardString(round.getDealerHand().get(i)));
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
        Round.Winner finalWinner = round.checkWinner(round.getFinalPlayerValue(),round.getFinalDealerValue());
        round.payOut(finalWinner);
        printDealerCards();
        if(finalWinner == Round.Winner.PLAYER) {
            System.out.println("You win!\nTotal money is now: " + totalMoney + "\n");
        }
        else if(finalWinner == Round.Winner.DEALER) {
            System.out.println("Dealer wins! Bet lost.\nTotal money is now: " + totalMoney + "\n");
        }
        else if(finalWinner == Round.Winner.PUSH) {
            System.out.println("Push! Bet returned.\nTotal money is now: " + totalMoney + "\n");
        }
        System.out.println("Play again? 'Yes' or 'No'");
    }

    public String readableCardString(Card currentCard) {
        return currentCard.getRank() + " of " + currentCard.getSuit() + " " + currentCard.getUnicodeString();
    }
}