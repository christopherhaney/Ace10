package game;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class CommandLineSplit {
    Round round;
    Scanner keyboardScanner;
    int currentSplitPromptIndex;

    public void printInitialSplit(Round round,Scanner keyboardScanner) {
        this.round = round;
        this.keyboardScanner = keyboardScanner;
        try {
            System.out.print("Player Hand 1: " + round.getPlayerHand(0).get(0).getUnicodeString());
            TimeUnit.SECONDS.sleep(1);
            System.out.print(round.getPlayerHand(0).get(1).getUnicodeString());
            TimeUnit.SECONDS.sleep(1);
            System.out.print("\nPlayer Hand 2: " + round.getPlayerHand(1).get(0).getUnicodeString());
            TimeUnit.SECONDS.sleep(1);
            System.out.print(round.getPlayerHand(1).get(1).getUnicodeString());
            TimeUnit.SECONDS.sleep(1);
            System.out.print("\n\n");
        } catch (InterruptedException e) {
            System.err.println("Sleep delay error.");
        }
    }

    public void printLaterSplits() {
        for(int l = 0; l < round.getAllPlayerHands().size(); l++) {
            System.out.print("Hand " + (l+1) + ": ");
            for(int m = 0; m < round.getPlayerHand(l).size(); m++) {
                System.out.print(round.getPlayerHand(l).get(m).getUnicodeString());
            }
            System.out.print("\n");
        }
        while(currentSplitPromptIndex < round.getAllPlayerHands().size()) {
            if(round.splitCheck(round.getPlayerHand(currentSplitPromptIndex))) {
                printSplitPrompt(currentSplitPromptIndex);
                currentSplitPromptIndex++;
            }
        }
    }

    public void printSplitPrompt(int i) {
        System.out.print("You can split again! Type 'sp2' if you wish to split hand " + (i + 1) + ", otherwise type 'nsp2': ");
        String currentToken = keyboardScanner.nextLine().toLowerCase();
        if(currentToken.equals("sp2")) {
            round.splitHand(i);
            currentSplitPromptIndex = 0;
            printLaterSplits();
        }
    }
}