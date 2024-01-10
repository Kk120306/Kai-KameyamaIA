import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.util.List;
public class LineupVisualizer {

    public void drawField() {
        StdDraw.setCanvasSize(1000, 600);
        StdDraw.setXscale(0, 100);
        StdDraw.setYscale(0, 60);

        // Field background
        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.filledRectangle(50, 30, 48, 28);

        // Draw field lines
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.rectangle(50, 30, 48, 28);
        // Additional field markings...
    }

    public void drawFormation(List<Player> defenders, List<Player> midfielders, List<Player> attackers) {
        double defenderX = 15;
        double midfielderX = 30;
        double attackerX = 45;

        // Draw position labels
        drawLabel("Defenders", defenderX, 55);
        drawLabel("Midfielders", midfielderX, 55);
        drawLabel("Attackers", attackerX, 55);

        // Draw players horizontally
        drawPlayers(defenders, defenderX, StdDraw.BLUE, 5, 50);
        drawPlayers(midfielders, midfielderX, StdDraw.RED, 5, 50);
        drawPlayers(attackers, attackerX, StdDraw.YELLOW, 5, 50);
    }

    private void drawPlayers(List<Player> players, double x, Color color, double startY, double endY) {
        if (players.isEmpty()) return;

        double spacing = (endY - startY) / Math.max(1, players.size() - 1);

        for (int i = 0; i < players.size(); i++) {
            double y = startY + i * spacing;
            drawPlayer(x, y, players.get(i).getName());
        }
    }

    private void drawPlayer(double x, double y, String name) {
        // Draw player dot
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledCircle(x, y, 1);

        // Draw text background for visibility
        StdDraw.setPenColor(new Color(0, 0, 0, 150)); // Semi-transparent black
        StdDraw.filledRectangle(x, y - 2, name.length() * 0.3, 1.2);

        // Draw player name
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(new Font("Arial", Font.BOLD, 10));
        StdDraw.text(x, y - 2, name);
    }

    private void drawLabel(String label, double x, double y) {
        // Calculate the size of the background based on the text
        double textWidth = label.length() * 0.6; // Adjust multiplier as needed
        double textHeight = 1.5;

        // Draw semi-transparent background for label
        StdDraw.setPenColor(new Color(0, 0, 0, 150)); // Semi-transparent black
        StdDraw.filledRectangle(x, y, textWidth, textHeight);

        // Draw text
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(new Font("Arial", Font.BOLD, 12)); // Reduced font size
        StdDraw.text(x, y, label);
    }

    public void drawSubstitutes(List<Player> substitutes) {
        double x = 80; // Adjust X-coordinate for substitutes for better visibility
        double startY = 55;
        double spacing = 1.5; // Adjust spacing as needed

        drawLabel("Substitutes", x, startY + 3); // Label for substitutes

        StdDraw.setPenColor(StdDraw.WHITE);
        for (int i = 0; i < substitutes.size(); i++) {
            double y = startY - i * spacing;
            if (y < 0) break; // Stop if running out of space
            StdDraw.text(x, y, substitutes.get(i).getName());
        }
    }
}
