public class Player {
    private String name; // e.g., Jerome
    private String team; // e.g., "U16", "U19"
    private String position; // e.g., "Defender", "Midfielder", "Attacker"
    private double shotPercentage; // all below are from 0.0 - 100.0
    private double conversionRate;
    private double passPercentage;
    private double dribblePercentage;
    private double crossesPercentage;
    private double tacklesPercentage;

    // Constructor
    public Player(String name, String team, String position, double shotPercentage, double conversionRate, double passPercentage, double dribblePercentage, double crossesPercentage, double tacklesPercentage) {
        this.name = name;
        this.team = team;
        this.position = position;
        this.shotPercentage = shotPercentage;
        this.conversionRate = conversionRate;
        this.passPercentage = passPercentage;
        this.dribblePercentage = dribblePercentage;
        this.crossesPercentage = crossesPercentage;
        this.tacklesPercentage = tacklesPercentage;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getShotPercentage() {
        return shotPercentage;
    }

    public void setShotPercentage(double shotPercentage) {
        this.shotPercentage = shotPercentage;
    }

    public double getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(double conversionRate) {
        this.conversionRate = conversionRate;
    }

    public double getPassPercentage() {
        return passPercentage;
    }

    public void setPassPercentage(double passPercentage) {
        this.passPercentage = passPercentage;
    }

    public double getDribblePercentage() {
        return dribblePercentage;
    }

    public void setDribblePercentage(double dribblePercentage) {
        this.dribblePercentage = dribblePercentage;
    }

    public double getCrossesPercentage() {
        return crossesPercentage;
    }

    public void setCrossesPercentage(double crossesPercentage) {
        this.crossesPercentage = crossesPercentage;
    }

    public double getTacklesPercentage() {
        return tacklesPercentage;
    }

    public void setTacklesPercentage(double tacklesPercentage) {
        this.tacklesPercentage = tacklesPercentage;
    }

    @Override // overrides the method from the superclass
    public String toString() {
        return "Player{" + "name='" + name + '\'' + ", team='" + team + '\'' +  ", position='" + position + '\'' + ", shotPercentage=" + shotPercentage +  ", conversionRate=" + conversionRate + ", passPercentage=" + passPercentage + ", dribblePercentage=" + dribblePercentage + ", crossesPercentage=" + crossesPercentage + ", tacklesPercentage=" + tacklesPercentage + '}';
    }
}
