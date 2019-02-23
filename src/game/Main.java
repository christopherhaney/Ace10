package game;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner keyboardScanner = new Scanner(System.in);
        Round round = new Round();
        round.roundBegin();
        System.out.println("Welcome to Ace10!");
        System.out.println("Player has: " + round.getPlayerHardValue());
        System.out.println("Dealer has: " + round.getPlayerHardValue());
        System.out.println("Top is: " + round.getDeck().getTop());
        while(!keyboardScanner.next().equals("exit")) {
            if(keyboardScanner.next().equals("hit")) {
                round.playerHit();
                System.out.println("Hit!\nPlayer value is now: " + round.getPlayerHardValue());
                System.out.println("Dealer value is now: " + round.getDealerHardValue());
                System.out.println("Top is: " + round.getDeck().getTop());
            }
            else if(keyboardScanner.next().equals("stand"))
                round.playerStand();
        }
    }
}