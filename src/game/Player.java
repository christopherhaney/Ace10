package game;

public class Player {
    public int totalMoney;
    public int totalRounds;
    public int totalGames;

    public Player(int totalMoney) {
        this.totalMoney = totalMoney;
    }

    public int getTotalMoney() {
        return totalMoney;
    }
}