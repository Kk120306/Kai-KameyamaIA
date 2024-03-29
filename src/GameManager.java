// Technique : Java Build in classes
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

// Class responsible for handling and managing all the game data that the client inputs.
public class GameManager {
    private final List<Game> games; // List of all games
    Scanner scanner = new Scanner(System.in); // scanner
    private final String dataFilePath = "gamesData.txt"; // Used for when saving data
    private final Menu menu; // Call Menu for interface.



    public GameManager() {
        this.games = new ArrayList<>();
        loadData(); // loads in all the game data from previous sessions in .txt
        menu = new Menu();
    }
    // Technique : Switch Case
    // A second menu interface inside main that is responsible for all options (GAMES)
    public void chooseOption(){
        int selection = menu.getGameChoice();
        switch (selection) {
            case 1:
                addGame(); // adds games
                break;
            case 2:
                listGames(); // displays all games
                break;
            case 3:
                updateGameStats(); // updates or removes game
                break;
        }
    }

    // Techniques : Time, Date, While loop, if, try catch
    //method used to add game to the arraylist.
    public void addGame() {
        // Collecting and validating the date of the game
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date gameDate = null;
        dateFormat.setLenient(false);// Set the SimpleDateFormat to non-lenient mode - Makes sure that data makes sense in real life context
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // specifies format of date, used later for validation

        while (gameDate == null) {
            System.out.println("\nEnter the game date (yyyy-MM-dd): --Enter blank for main menu");
            String dateString = scanner.nextLine().trim(); //Trims date, removes any empty space after input

            if (dateString.isEmpty()) { //returns back to menu if input is empty
                System.out.println("Returning to main menu");
                return;
            }

            try {
                LocalDate parsedDate = LocalDate.parse(dateString, formatter);
                gameDate = java.sql.Date.valueOf(parsedDate); // Convert LocalDate to java.sql.Date
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter in the format yyyy-MM-dd.");
            }
        }

        // Collecting and validating the opponent's name
        String opponent = getValidName("Enter opponent's name:");

        // Technique : ArrayLists
        System.out.println("Enter the names of goal scorers (comma-separated):");
        List<String> goalScorers = new ArrayList<>();
        String input;
        do {
            input = scanner.nextLine();
            String[] scorerNames = input.split("\\s*,\\s*");

            for (String scorer : scorerNames) {
                if (isValidString(scorer)) { // Check if the names are valid
                    goalScorers.add(scorer); // Add the valid player names to the list
                } else {
                    System.out.println("Invalid name: " + scorer);
                    goalScorers.clear(); // Clear the list if an invalid name is encountered
                    break; // Exit the loop if an invalid name is encountered
                }
            }
        } while (goalScorers.isEmpty());

        String mvp = getValidName("Enter the name of the Most Valuable Player (MVP):"); // Collecting MVP names


        int totalGoals = getValidatedInteger("Enter total goals:");
        int totalAssists = getValidatedInteger("Enter total assists:");
        int yellowCards = getValidatedInteger("Enter total yellow cards:");
        int redCards = getValidatedInteger("Enter total red cards:");
        double possessionPercentage = getValidatedDoubleInput("Enter possession percentage:");
        // Technique : Encapsulation
        Game newGame = new Game(gameDate, opponent, goalScorers, mvp, totalGoals, totalAssists, yellowCards, redCards, possessionPercentage);
        games.add(newGame);
        System.out.println("New game added on " + dateFormat.format(gameDate));
    }

    // Method responsible for Validating the Name, ensuring in the right format and data type
    // Technique : If-else statements
    private String getValidName(String prompt) {
        String name;
        while (true) {
            System.out.println(prompt);
            name = scanner.nextLine().trim();
            if (isValidString(name)) { // using valid string method to verify
                break;
            } else {
                System.out.println("Name cannot be empty or Name cannot be other than letters. Please enter a valid name.");
            }
        }
        return name;
    }

    // responsible for verifying the string - used in several methods

    private static boolean isValidString(String str) {
        return str != null && !str.isEmpty() && str.matches("[a-zA-Z ]+"); //
    }


    // Method to validate integer input  (as an example for total goals)
    // Technique : while, if else, exception handling
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

    // method to validate double input like possesion
    // Technique : while, if-else
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

    // used to disiplay all the games
    // Technique : Java Class - Comparator
    public void listGames() {
        if (games.isEmpty()) {
            System.out.println("No games have been added yet.");
            return;
        }

        // Using the help of comparator to make syure that the dates are sorted in order.
        games.sort(Comparator.comparing(Game::getGameDate));

        // Print the details of each game using its toString method
        System.out.println("\n-- Games --");
        for (Game game : games) {
            System.out.println(game.toString());
        }
    }

    // uised to write all the data in to txt for future use
    // Technique : Java class - PrintWriter , try catch, for loop
    public void saveData() {
        try (PrintWriter out = new PrintWriter(new FileWriter(dataFilePath))) { // allwos for formating
            for (Game game : games) {
                out.println(gameToString(game));
            }
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }

    // Method to load game data from a file
    // Technique : java class - file, exception handling, if else
    public void loadData() {
        games.clear(); // first clears all exisitng in the system
        File file = new File(dataFilePath);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) { // if there is a fail that is created, dispaly
                    System.out.println("New data file created: " + dataFilePath);
                } else {
                    System.err.println("Failed to create new data file: " + dataFilePath);
                }
            } catch (IOException e) {
                System.err.println("Error creating new data file: " + e.getMessage());
                return;
            }
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) { // Used to read the input
            String line;
            while ((line = br.readLine()) != null) {
                Game game = stringToGame(line); // uses method to understand and parse data
                if (game != null) {
                    games.add(game);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
    }

    // Method responsible for converting strings to a Game object. Used for converting from txt file
    // Technique : Encapsulation
    private Game stringToGame(String data) {
        try {
            String[] parts = data.split(","); // splits the data when reading from a file
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Date gameDate = sdf.parse(parts[0]); // parses according to the set pattern of the dates
            String opponent = parts[1];
            List<String> goalScorers = Arrays.asList(parts[2].split(";")); //  semicolon-separated
            String mvp = parts[3];
            int totalGoals = Integer.parseInt(parts[4]); // all parses them into specific variablesl
            int totalAssists = Integer.parseInt(parts[5]);
            int yellowCards = Integer.parseInt(parts[6]);
            int redCards = Integer.parseInt(parts[7]);
            double possessionPercentage = Double.parseDouble(parts[8]);

            return new Game(gameDate, opponent, goalScorers, mvp, totalGoals, totalAssists, yellowCards, redCards, possessionPercentage); // stores as a game object and then stores it in to game
        } catch (Exception e) {
            System.err.println("Error parsing game from string: " + e.getMessage());
            return null;
        }
    }

    // Responsible for converting game objects into string to input into txt file
    private String gameToString(Game game) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(game.getGameDate()); // fomrats the data from the game object
        String goalScorersStr = String.join(";", game.getGoalScorers()); // make sure that the game scores are not seeperated by the commma as this ill impact the readability of the file

        // all attirbutes to comma seperated string
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

    // updates or removes game stats
    // Technique : if else,
    public void updateGameStats() {
        // Only run once
        listGames(); // Method to display all games

        System.out.println("\nEnter the date of the game you want to update (yyyy-MM-dd): --Enter 'exit' to return to main menu");
        String dateString = scanner.nextLine().trim();

        if (dateString.equalsIgnoreCase("exit")) {
            System.out.println("Returning to main menu.");
            return; // Return to main menu if user types 'exit'
        }

        Date gameDate = parseDate(dateString); // method  to parse dates
        if (gameDate == null) {
            System.out.println("Invalid date format. Please use the yyyy-MM-dd format.");
            return; // Return to the main menu if the date is invalid
        }

        Game gameToUpdate = findGameByDate(gameDate); // finds the game with the inputted date

        if (gameToUpdate != null) {
            String option = getValidatedString().toLowerCase(); // Convert input to lowercase
            if (option.equals("remove")) {
                games.remove(gameToUpdate);
                saveData(); // save data methods to save in a txt file
                System.out.println("Game removed successfully.");
            } else if (option.equals("update")) {
                updateGameDetails(gameToUpdate); // method to update details
                saveData(); // Save updated data
                System.out.println("Game stats updated for " + dateString + ".");
            } else {
                System.out.println("Invalid option, please enter 'remove' or 'update'.");
            }
        } else {
            System.out.println("Game not found. Please try again.");
        }
    }

    // Method responsible for updating game details if the client wishes to remove or update game stats
    // Technique : Encapsulation, setters,getters
    private void updateGameDetails(Game game) {
        // Update opponent
        String newOpponent = getValidName("Enter new opponent (Current: " + game.getOpponent() + "):");
        game.setOpponent(newOpponent);

        // Update goal scorers
        String goalScorersStr = getValidName("Enter goal scorers (comma-separated, Current: " + game.getGoalScorers().toString() + "):");
        List<String> newGoalScorers = Arrays.asList(goalScorersStr.split("\\s*,\\s*"));
        game.setGoalScorers(newGoalScorers);

        // Update MVP
        String newMvp = getValidName("Enter new MVP (Current: " + game.getMvp() + "):");
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

    }

    // responsible for finding the game using data input from users.
    // Technique : for loop, if loop
    private Game findGameByDate(Date date) {
        for (Game game : games) {
            if (game.getGameDate().equals(date)) {
                return game; // Return the found game
            }
        }
        return null; // Return null if no matching game is found
    }

    // used to part the dates into format
    private Date parseDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            System.err.println("Invalid date format. Please use the yyyy-MM-dd format.");
            return null; // Return null
        }
    }

    // validates if the string is present
    private String getValidatedString() {
        String input;
        while (true) {
            System.out.println("Please select if you would like to remove or update a game. Enter 'remove' or 'update'.");
            input = scanner.nextLine().trim(); // Trim to remove leading/trailing spaces

            if (!input.isEmpty()) {
                return input; // Return the input if it's not empty
            } else {
                System.out.println("Input cannot be empty. Please enter valid text.");
            }
        }
    }
}