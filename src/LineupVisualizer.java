import edu.princeton.cs.introcs.StdDraw;
import java.awt.*;
import java.util.List;
// Technique : StdDraw Princeton Univeristy
public class LineupVisualizer {

    // method is used to create the outline of the field.
    public void drawField() {
        StdDraw.setCanvasSize(1000, 600); // sets the size of the canvas
        StdDraw.setXscale(0, 100); //setting X -a x
        StdDraw.setYscale(0, 60);   // setting Y - ax

        // Field background
        StdDraw.setPenColor(StdDraw.GREEN); // green colour
        StdDraw.filledRectangle(50, 30, 48, 28); // Drawing the filled rectangle

        // Draw field lines
        StdDraw.setPenColor(StdDraw.WHITE); // white marking
        StdDraw.rectangle(50, 30, 48, 28);

    }
//Responsibel for drawing the formation on the field
    public void drawFormation(List<Player> defenders, List<Player> midfielders, List<Player> attackers) {
        double defenderX = 15;
        double midfielderX = 30;
        double attackerX = 45;

        // Draw position labels
        drawLabel("Defenders", defenderX, 55);
        drawLabel("Midfielders", midfielderX, 55);
        drawLabel("Attackers", attackerX, 55);

        // Draw players horizontally
        drawPlayers(defenders, defenderX);
        drawPlayers(midfielders, midfielderX);
        drawPlayers(attackers, attackerX);
    }

    // responsible for drawing the players and their names
    private void drawPlayers(List<Player> players, double x) {
        if (players.isEmpty()) return; // makes sure to exit if there is n oplaeyrs

        double spacing = ((double) 50 - (double) 5) / Math.max(1, players.size() - 1);

        for (int i = 0; i < players.size(); i++) {
            double y = (double) 5 + i * spacing;
            drawPlayer(x, y, players.get(i).getName());
        }
    }

    // method responsibel for drawing a single player
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

    // resp[onsible for drawing the semi transparent black label behind the text
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

    // draws the subsittues in the corner of the field

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
