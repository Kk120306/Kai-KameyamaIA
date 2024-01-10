import edu.princeton.cs.introcs.StdDraw;
import java.util.List;

public class LineupVisualizer {

    private void drawSoccerField() {
    // Draw the green field
    StdDraw.setPenColor(StdDraw.GREEN);
    StdDraw.filledRectangle(50, 50, 40, 30);

    // Draw the midfield line
    StdDraw.setPenColor(StdDraw.WHITE);
    StdDraw.line(50, 20, 50, 80);

    // Draw the center circle
    StdDraw.circle(50, 50, 5);

    // Draw goal lines
    StdDraw.rectangle(10, 50, 5, 10);  // Left goal
    StdDraw.rectangle(90, 50, 5, 10);  // Right goal
}

    private void drawPlayersOnField(List<Player> defenders, List<Player> midfielders, List<Player> attackers, List<Player> substitutes) {
    drawPositionGroup(defenders, 20, 50, false); // Position the defenders
    drawPositionGroup(midfielders, 50, 50, false); // Position the midfielders
    drawPositionGroup(attackers, 80, 50, false); // Position the attackers
    drawPositionGroup(substitutes, 50, 10, true); // Position the substitutes below the field
}

private void drawPositionGroup(List<Player> players, int xPosition, int baseYPosition, boolean isSubstitute) {
    int yStep = (isSubstitute ? 5 : 100 / (players.size() + 1));
    int yPos = baseYPosition;

    for (Player player : players) {
        if (!isSubstitute) {
            yPos += yStep; // Update position for each player
        }
        drawPlayer(player, xPosition, yPos, isSubstitute);
        if (isSubstitute) {
            yPos += yStep; // Update position for each substitute
        }
    }
}

private void drawPlayer(Player player, int x, int y, boolean isSubstitute) {
    StdDraw.setPenColor(isSubstitute ? StdDraw.RED : StdDraw.BLUE);
    StdDraw.filledCircle(x, y, 2);
    StdDraw.text(x, y - (isSubstitute ? 3 : 5), player.getName());
}
}
