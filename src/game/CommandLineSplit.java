package game;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class CommandLineSplit {
    Round round;
    Scanner keyboardScanner;

    public void printInitialSplit(Round round, Scanner keyboardScanner) {
        this.round = round;
        this.keyboardScanner = keyboardScanner;
        int i = 0;
        int j = 0;
        try {
            while (i < round.getAllPlayerHands().size()) {
                System.out.print("Player Hand " + (i + 1) + ":");
                TimeUnit.SECONDS.sleep(1);
                while (j < round.getPlayerHand(i).size()) {
                    System.out.print(round.getPlayerHand(i).get(j).getUnicodeString());
                    j++;
                    TimeUnit.SECONDS.sleep(1);
                }
                TimeUnit.SECONDS.sleep(1);
                System.out.print("\n");
                if(round.splitCheck(round.getPlayerHand(i))) {
                    printSplitPrompt(i);
                    break;
                }
                i++;
                j = 0;
            }
            System.out.print("\n");
        } catch (InterruptedException e) {
            System.err.println("Sleep delay error.");
        }
    }

    public void printLaterSplits() {
        int i = 0;
        int j = 0;
        while (i < round.getAllPlayerHands().size()) {
            System.out.print("Player Hand " + (i + 1) + ":");
            while (j < round.getPlayerHand(i).size()) {
                System.out.print(round.getPlayerHand(i).get(j).getUnicodeString());
                j++;
            }
            System.out.print("\n");
            if(round.splitCheck(round.getPlayerHand(i))) {
                printSplitPrompt(i);
                break;
            }
            i++;
            j = 0;
        }
    }

    public void printSplitPrompt(int i) {
        System.out.print("You can split again! Type 'y' if you wish to split hand " + (i + 1) + ", otherwise type 'n': ");
        String currentToken = keyboardScanner.nextLine().toLowerCase();
        if(currentToken.equals("y")) {
            round.splitHand(i);
            printLaterSplits();
            CommandLine.currentBet -= 10;
        }
    }
}