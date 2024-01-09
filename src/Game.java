import java.util.Date;
import java.util.List;

public class Game {
    private final Date gameDate; //date of the using date class
    private String opponent; // opponent the clients team palyed
    private List<String> goalScorers; // Players who scored in the game
    private String mvp; // Most Valuable Player
    private int totalGoals; // Total goals in the game
    private int totalAssists; // Total assists in the game
    private int yellowCards; // Total yellow cards in the game
    private int redCards; // Total red cards in the game
    private double possessionPercentage; // Possession percentage

    // You might want more detailed stats, perhaps split between teams or individual player performances.

    // Constructor
    public Game(Date gameDate, String opponent, List<String> goalScorers, String mvp, int totalGoals, int totalAssists, int yellowCards, int redCards, double possessionPercentage) {
        this.gameDate = gameDate;
        this.opponent = opponent;
        this.goalScorers = goalScorers;
        this.mvp = mvp;
        this.totalGoals = totalGoals;
        this.totalAssists = totalAssists;
        this.yellowCards = yellowCards;
        this.redCards = redCards;
        this.possessionPercentage = possessionPercentage;
    }

    public Date getGameDate() {
        return gameDate;
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public List<String> getGoalScorers() {
        return goalScorers;
    }

    public void setGoalScorers(List<String> goalScorers) {
        this.goalScorers = goalScorers;
    }

    public String getMvp() {
        return mvp;
    }

    public void setMvp(String mvp) {
        this.mvp = mvp;
    }

    public int getTotalGoals() {
        return totalGoals;
    }

    public void setTotalGoals(int totalGoals) {
        this.totalGoals = totalGoals;
    }

    public int getTotalAssists() {
        return totalAssists;
    }

    public void setTotalAssists(int totalAssists) {
        this.totalAssists = totalAssists;
    }

    public int getYellowCards() {
        return yellowCards;
    }

    public void setYellowCards(int yellowCards) {
        this.yellowCards = yellowCards;
    }

    public int getRedCards() {
        return redCards;
    }

    public void setRedCards(int redCards) {
        this.redCards = redCards;
    }

    public double getPossessionPercentage() {
        return possessionPercentage;
    }

    public void setPossessionPercentage(double possessionPercentage) {
        this.possessionPercentage = possessionPercentage;
    }

    @Override
    public String toString() {
        return "\nGame" +
                " Game Date=" + gameDate +
                ", opponent='" + opponent + '\'' +
                ", goalScorers=" + goalScorers +
                ", mvp='" + mvp + '\'' +
                ", totalGoals=" + totalGoals +
                ", totalAssists=" + totalAssists +
                ", yellowCards=" + yellowCards +
                ", redCards=" + redCards +
                ", possessionPercentage=" + possessionPercentage +
                '}';
    }
}