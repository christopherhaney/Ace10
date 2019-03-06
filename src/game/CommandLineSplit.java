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

        }
        catch(InterruptedException e) {
            System.err.println("Sleep delay error.");
        }
    }
}
