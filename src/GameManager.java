import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class GameManager {
    private final List<Game> games; // List of all games
    Scanner scanner = new Scanner(System.in);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final String dataFilePath = "gamesData.txt";

    public GameManager() {
        this.games = new ArrayList<>();
        loadData();
    }

    public void addGame() {
        // Collecting and validating the date of the game
        Date gameDate = null;
        while (gameDate == null) {
            System.out.println("\nEnter the game date (yyyy-MM-dd): --Enter blank for main menu");
            String dateString = scanner.nextLine().trim();
            if (dateString.isEmpty()) {
                System.out.println("Returning to main menu");
                return; // Return to main menu if input is empty
            }
            try {
                gameDate = dateFormat.parse(dateString); // Parse the date
            } catch (Exception e) {
                System.out.println("Invalid date format. Please enter in the format yyyy-MM-dd.");
            }
        }

        // Collecting and validating the opponent's name
        String opponent;
        while (true) {
            System.out.println("Enter opponent's name:");
            opponent = scanner.nextLine().trim();
            if (!opponent.isEmpty()) {
                break;
            } else {
                System.out.println("Opponent's name cannot be empty. Please enter a valid name.");
            }
        }

        // Collecting and validating the names of goal scorers (assuming they are comma-separated)
        System.out.println("Enter the names of goal scorers (comma-separated):");
        List<String> goalScorers = new ArrayList<>(Arrays.asList(scanner.nextLine().split("\\s*,\\s*")));

        // Collecting and validating MVP name
        String mvp;
        while (true) {
            System.out.println("Enter the name of the Most Valuable Player (MVP):");
            mvp = scanner.nextLine().trim();
            if (!mvp.isEmpty()) {
                break;
            } else {
                System.out.println("MVP's name cannot be empty. Please enter a valid name.");
            }
        }


        int totalGoals = getValidatedInteger("Enter total goals:");
        int totalAssists = getValidatedInteger("Enter total assists:");
        int yellowCards = getValidatedInteger("Enter total yellow cards:");
        int redCards = getValidatedInteger("Enter total red cards:");
        double possessionPercentage = getValidatedDoubleInput("Enter possession percentage:");

        Game newGame = new Game(gameDate, opponent, goalScorers, mvp, totalGoals, totalAssists, yellowCards, redCards, possessionPercentage); // Make sure your Game constructor matches these parameters
        games.add(newGame);
        System.out.println("New game added on " + dateFormat.format(gameDate));
    }

    // Additional methods like listAllGames, findGameByDate, etc.

    // Utility method for validated numeric input (as an example for total goals)
    private int getValidatedInteger(String prompt) {
        int number;
        while (true) {
            System.out.println(prompt);
            try {
                number = Integer.parseInt(scanner.nextLine());
                if (number >= 0 && number <= 20) {
                    return number; // Return the number if it's within the range
                } else {
                    System.out.println("Invalid input. Please enter an integer between 0 and 20.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    public double getValidatedDoubleInput(String txt) {
        double value;
        while (true) {
            System.out.println(txt);
            if (scanner.hasNextDouble()) { // makes sure that the value is a double and no other data type
                value = scanner.nextDouble();
                if (value >= (double) 0 && value <= (double) 100) {
                    scanner.nextLine();
                    return value;
                } else { // loops until within a desired range
                    System.out.println("Please enter a value between " + (double) 0 + " and " + (double) 100 + ".");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
            scanner.nextLine(); // Makes sure that the program dosent loop out if there is any remaining input by the user from previous.
        }
    }

    public void listGames() {
        if (games.isEmpty()) {
            System.out.println("No games have been added yet.");
            return;
        }

        // Sort the games by date using a lambda expression
        games.sort(Comparator.comparing(Game::getGameDate));

        // Print the details of each game using its toString method
        for (Game game : games) {
            System.out.println(game.toString());
        }
    }
    public void saveData() {
        try (PrintWriter out = new PrintWriter(new FileWriter(dataFilePath))) {
            for (Game game : games) {
                out.println(gameToString(game));
            }
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }

    // Method to load game data from a file
    public void loadData() {
        games.clear();
        File file = new File(dataFilePath);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("New data file created: " + dataFilePath);
                } else {
                    System.err.println("Failed to create new data file: " + dataFilePath);
                }
            } catch (IOException e) {
                System.err.println("Error creating new data file: " + e.getMessage());
                return;
            }
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                Game game = stringToGame(line);
                if (game != null) {
                    games.add(game);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
    }

    private Game stringToGame(String data) {
        try {
            String[] parts = data.split(",");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Date gameDate = sdf.parse(parts[0]);
            String opponent = parts[1];
            List<String> goalScorers = Arrays.asList(parts[2].split(";")); // Assuming semicolon-separated
            String mvp = parts[3];
            int totalGoals = Integer.parseInt(parts[4]);
            int totalAssists = Integer.parseInt(parts[5]);
            int yellowCards = Integer.parseInt(parts[6]);
            int redCards = Integer.parseInt(parts[7]);
            double possessionPercentage = Double.parseDouble(parts[8]);

            return new Game(gameDate, opponent, goalScorers, mvp, totalGoals, totalAssists, yellowCards, redCards, possessionPercentage);
        } catch (Exception e) {
            System.err.println("Error parsing game from string: " + e.getMessage());
            return null;
        }
    }

    private String gameToString(Game game) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(game.getGameDate());
        String goalScorersStr = String.join(";", game.getGoalScorers());

        // Concatenating all attributes into a comma-separated string
        return dateStr + "," +
                game.getOpponent() + "," +
                goalScorersStr + "," +
                game.getMvp() + "," +
                game.getTotalGoals() + "," +
                game.getTotalAssists() + "," +
                game.getYellowCards() + "," +
                game.getRedCards() + "," +
                game.getPossessionPercentage();
    }
    public void updateGameStats() {
        // Only run once for each invocation.
        listGames(); // Method to display all games

        System.out.println("\nEnter the date of the game you want to update (yyyy-MM-dd): --Enter 'exit' to return to main menu");
        String dateString = scanner.nextLine().trim();

        if (dateString.equalsIgnoreCase("exit")) {
            System.out.println("Returning to main menu.");
            return; // Return to main menu if user types 'exit'
        }

        Date gameDate = parseDate(dateString); // Ensure you have a method to parse dates
        if (gameDate == null) {
            System.out.println("Invalid date format. Please use the yyyy-MM-dd format.");
            return; // Return to the main menu if the date is invalid
        }

        Game gameToUpdate = findGameByDate(gameDate);

        if (gameToUpdate != null) {
            String option = getValidatedString("Please select if you would like to remove or update a game. Enter 'remove' or 'update'.");
            if (option.equals("remove")) {
                games.remove(gameToUpdate);
                saveData(); // Assuming saveData method exists
                System.out.println("Game removed successfully.");
            } else if (option.equals("update")) {
                updateGameDetails(gameToUpdate); // Assuming you have a method to update details
                saveData(); // Save updated data
                System.out.println("Game stats updated for " + dateString + ".");
            } else {
                System.out.println("Invalid option, please enter 'remove' or 'update'.");
            }
        } else {
            System.out.println("Game not found. Please try again.");
        }
        // Method ends here, returning control to the main loop
    }

    private void updateGameDetails(Game game) {
        // Update opponent
        String newOpponent = getValidatedString("Enter new opponent (Current: " + game.getOpponent() + "):");
        game.setOpponent(newOpponent);

        // Update goal scorers
        String goalScorersStr = getValidatedString("Enter goal scorers (comma-separated, Current: " + game.getGoalScorers().toString() + "):");
        List<String> newGoalScorers = Arrays.asList(goalScorersStr.split("\\s*,\\s*"));
        game.setGoalScorers(newGoalScorers);

        // Update MVP
        String newMvp = getValidatedString("Enter new MVP (Current: " + game.getMvp() + "):");
        game.setMvp(newMvp);

        // Update total goals
        int newTotalGoals = getValidatedInteger("Enter new total goals (Current: " + game.getTotalGoals() + "):");
        game.setTotalGoals(newTotalGoals);

        // Update total assists
        int newTotalAssists = getValidatedInteger("Enter new total assists (Current: " + game.getTotalAssists() + "):");
        game.setTotalAssists(newTotalAssists);

        // Update yellow cards
        int newYellows = getValidatedInteger("Enter new number of yellow cards (Current: " + game.getYellowCards() + "):");
        game.setYellowCards(newYellows);

        // Update red cards
        int newReds = getValidatedInteger("Enter new number of red cards (Current: " + game.getRedCards() + "):");
        game.setRedCards(newReds);

        // Update possession percentage
        double newPossession = getValidatedDoubleInput("Enter new possession percentage (Current: " + game.getPossessionPercentage() + "):");
        game.setPossessionPercentage(newPossession);

        // ... include other attributes as needed ...
    }

    private Game findGameByDate(Date date) {
        for (Game game : games) {
            if (game.getGameDate().equals(date)) {
                return game; // Return the found game
            }
        }
        return null; // Return null if no matching game is found
    }

    private Date parseDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            System.err.println("Invalid date format. Please use the yyyy-MM-dd format.");
            return null; // Return null or handle this case as you see fit
        }
    }

    private String getValidatedString(String prompt) {
        String input;
        while (true) {
            System.out.println(prompt);
            input = scanner.nextLine().trim(); // Trim to remove leading/trailing spaces

            if (!input.isEmpty()) {
                return input; // Return the input if it's not empty
            } else {
                System.out.println("Input cannot be empty. Please enter valid text.");
            }
        }
    }





}