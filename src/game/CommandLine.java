package game;

import java.util.Scanner;

public class CommandLine {
    Scanner keyboardScanner;
    Round round;

    public CommandLine() {
        keyboardScanner = new Scanner(System.in);
        round = new Round();
        round.roundBegin();
        System.out.println("Beginning round...");
        System.out.println("Player has: " + round.getPlayerHardValue());
        System.out.println("Player has: " + round.getPlayerHardValue());
        System.out.println("Dealer has: " + round.getPlayerHardValue());
        System.out.println("Top is: " + round.getDeck().getTop());
    }

    public void playGame() {
        playerHitPrint();
    }

    public void playerHitPrint() {
        if(keyboardScanner.next().equals("hit")) {
            round.playerHit();
            System.out.println("Hit!\nPlayer value is now: " + round.getPlayerHardValue());
            System.out.println("Dealer value is now: " + round.getDealerHardValue());
            System.out.println("Top is: " + round.getDeck().getTop());
        }
        else
            playerStandPrint();
    }

    public void playerStandPrint() {
        if(keyboardScanner.next().equals("stand"))
            round.playerStand();
    }

    public Boolean finishRound() {
        try {
            if(playerHardValue > 21) {
                System.out.println("Finish! Winner is: Dealer!");
                System.out.println("Player had: " + playerHardValue);
                System.out.println("Dealer had: " + dealerHardValue);
            }
            if(dealerHardValue <= 21 && playerHardValue <= 21) {
                if (dealerHardValue > playerHardValue) {
                    System.out.println("Finish! Winner is: Dealer!");
                    System.out.println("Player had: " + playerHardValue);
                    System.out.println("Dealer had: " + dealerHardValue);
                }
                else if (dealerHardValue < playerHardValue) {
                    System.out.println("Finish! Winner is: Player!");
                    System.out.println("Player had: " + playerHardValue);
                    System.out.println("Dealer had: " + dealerHardValue);
                }
                else {
                    System.out.println("Finish! Winner is: Tie!");
                    System.out.println("Player had: " + playerHardValue);
                    System.out.println("Dealer had: " + dealerHardValue);
                }
            }
            return null;
        }
        finally {
            resetRound();
        }
    }
}