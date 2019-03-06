package game;

import java.util.concurrent.TimeUnit;

public class CommandLineSplit {
    Round round;

    public void printInitialSplit(Round round) {
        this.round = round;
        int i = 0;
        int j = 0;
        try {
            while(i < round.getAllPlayerHands().size()) {
                System.out.print("Player Hand " + (i+1) + ":");
                TimeUnit.SECONDS.sleep(1);
                while(j < round.getPlayerHand(i).size()) {
                    System.out.print(round.getPlayerHand(i).get(j).getUnicodeString());
                    j++;
                    TimeUnit.SECONDS.sleep(1);
                }
                i++;
                j = 0;
                TimeUnit.SECONDS.sleep(1);
                System.out.print("\n");
            }
            System.out.print("\n");
            multiSplitCheck();
        }
        catch(InterruptedException e) {
            System.err.println("Sleep delay error.");
        }
    }

    public void multiSplitCheck() {
        while(true) {
            for(int i = 0; i < round.getAllPlayerHands().size(); i++) {
                if(round.splitCheck(round.getPlayerHand(i))) {
                    System.out.print("You can split again! Type 'y' if you wish to split hand " + (i+1) + ", otherwise type 'n': ");
                }
            }
            break;
        }
    }
}
