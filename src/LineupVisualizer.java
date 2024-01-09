import edu.princeton.cs.introcs.StdDraw;
import java.util.List;

public class LineupVisualizer {

    public void drawField() {
        StdDraw.setCanvasSize(800, 600);
        StdDraw.setXscale(0, 100);
        StdDraw.setYscale(0, 100);

        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.filledRectangle(50, 50, 40, 30);
    }

    public void drawPlayer(double x, double y, String name) {
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledCircle(x, y, 2);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(x, y - 3, name);
    }

    public void drawFormation(List<Player> defenders, List<Player> midfielders, List<Player> attackers) {
        double defenderY = 70;
        double midfielderY = defenderY - 20; // Adjust as needed
        double attackerY = midfielderY - 20; // Adjust as needed
        int spacing = 10;

        // Draw defenders
        for (int i = 0; i < defenders.size(); i++) {
            drawPlayer(10 + i * spacing, defenderY, defenders.get(i).getName());
        }

        // Draw midfielders
        for (int i = 0; i < midfielders.size(); i++) {
            drawPlayer(10 + i * spacing, midfielderY, midfielders.get(i).getName());
        }

        // Draw attackers
        for (int i = 0; i < attackers.size(); i++) {
            drawPlayer(10 + i * spacing, attackerY, attackers.get(i).getName());
        }
    }

}
