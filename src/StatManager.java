import edu.princeton.cs.introcs.StdDraw;

import java.util.*;
import java.io.*;
import java.util.stream.Collectors;

// importing all necessary java utils for the class

public class StatManager {
    private final List<Player> players; // used linklist over normal arrays as more efficient for insertion and deletions
    private final Scanner scanner; // Final : ensures input only comes from one source

    private final Map<String, Player> playerMap; // Easier and simpler when looking up the player names
    private final String dataFilePath = "players_data.txt"; // Final as I dotn want this thing to change with the utilisation of the program



    public StatManager() {
        players = new LinkedList<>();
        scanner = new Scanner(System.in);
        playerMap = new HashMap<>();
        loadData();

    }
 // method to add new players to the sytesm
 // Technique : for loop, if else, ArrayList
    public void displayCurrentList() {
        System.out.println("\nCurrent players in the system:");
        if (playerMap.isEmpty()) {
            System.out.println("No players currently in the system.");
        } else {
            // Convert the keySet into a List for sorting, Need this as will be using hashmaps to retrive players.
            List<String> players = new ArrayList<>(playerMap.keySet());

            // Implementing selection sort to sort player names alphabetically
            for (int i = 0; i < players.size() - 1; i++) {
                // Find the index of the minimum element
                int minIndex = i;
                for (int j = i + 1; j < players.size(); j++) {
                    if (players.get(j).compareTo(players.get(minIndex)) < 0) {
                        minIndex = j;
                    }
                }

                // Swap the found minimum element with the first element, using temp as this data will be tim eit is swapped
                String temp = players.get(minIndex);
                players.set(minIndex, players.get(i));
                players.set(i, temp);
            }

            // Print the sorted list of names
            for (String playerName : players) {
                System.out.println(playerName);
            }
        }
    }

    // Technique : Encapsulation
    public void addNewPlayer() {

        displayCurrentList();

        String name;
        while (true) {
            System.out.println("\nEnter Player's Full Name (First and Last): --Enter blank for main menu");
            name = scanner.nextLine().trim();

            if (name.isEmpty()){ //  if the string is empty, will return back to the main menu.
                System.out.println("Returning to main menu");
                return;
            }

            // Check if the name contains both first and last name
            if (!name.contains(" ")) {
                System.out.println("Please enter both first and last name.");
                continue;
            }



            // Split the name into first and last names and capitalize them
            String[] names = name.split(" ");
            String firstName = capitalizeFirstLetter(names[0]);
            String lastName = capitalizeFirstLetter(names[1]);
            name = firstName + " " + lastName;

            // Check if a player with this formatted name already exists
            if (playerMap.containsKey(name.toLowerCase())) {
                System.out.println("A player with this name already exists. Please enter a different name.");
                continue;
            }

            if(!name.matches("[a-zA-Z ]+")){
                System.out.println("Make sure that the name is valid. Dont input numbers or other types other than characters");
                continue;
            }

            // Confirmation of entered name
            System.out.println("You entered '" + name + "'. Is this correct? (yes/no)");
            String confirmation = scanner.nextLine();
            if (confirmation.equalsIgnoreCase("yes")) {
                break; // Exit the loop if the name is confirmed
            }
        }

        //used to repeat until a valid input of U16 or U19 is identified by the user for the team.
        String team;
        do {
            System.out.println("Enter Player's Team (U16/U19):");
            team = scanner.nextLine().toLowerCase(); // Convert input to lowercase

            if (!team.equals("u16") && !team.equals("u19")) { // Compare with lowercase values
                System.out.println("Invalid input. Please enter 'U16' or 'U19'.");
            }
        } while (!team.equals("u16") && !team.equals("u19"));


        String position = getValidatedPositionInput(); // used a seperate method to validate as this method will beconme quite confusing
// uses the same methods to ensure that these values are in the desired range.
        double shotPercentage = getValidatedDoubleInput("Enter Shot Percentage:");
        double conversionRate = getValidatedDoubleInput("Enter Conversion Rate:");
        double passPercentage = getValidatedDoubleInput("Enter Pass Percentage:");
        double dribblePercentage = getValidatedDoubleInput("Enter Dribble Percentage:");
        double crossesPercentage = getValidatedDoubleInput("Enter Crosses Percentage:");
        double tacklesPercentage = getValidatedDoubleInput("Enter Tackles Percentage:");

        //adds all of this into list and map and also creates a player object based of allgathered information
        Player newPlayer = new Player(name, team, position, shotPercentage, conversionRate, passPercentage, dribblePercentage, crossesPercentage, tacklesPercentage);
        players.add(newPlayer);
        playerMap.put(name.toLowerCase(), newPlayer); // Add player to the HashMap
        System.out.println("New player added: " + name); // lets user know that they have been added.
    }

    private String capitalizeFirstLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    // method to ensure that double input in within a desired range of 0 - 100
    // Technique : if else, while loops
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

// allows the user to view player stats.
// Technique : do while, Hashmaps, if else
    public void viewPlayerStats() {
        displayCurrentList();

        boolean searching = true;
        while (searching) {
            System.out.println("\nEnter Player's Name to view stats: --Enter blank for main menu");
            String name = scanner.nextLine().trim(); // Trim to remove leading/trailing spaces
            if (name.isEmpty()) {
                break; // Exit the loop if the name is empty
            }

            Player player = playerMap.get(name.toLowerCase());

            if (player != null) {
                System.out.println(player);
            } else {
                System.out.println("Player not found.");
            }

            String response;

            do {
                System.out.println("Do you want to search for another player? (yes/no)");
                response = scanner.nextLine();
                if (!response.equalsIgnoreCase("yes") && !response.equalsIgnoreCase("no")) {
                    System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                }
            } while (!response.equalsIgnoreCase("yes") && !response.equalsIgnoreCase("no"));

            if (response.equalsIgnoreCase("no")) {
                searching = false;
            }
        }
    }
    // Technique : while, if else
    // used for the other methods. can verify that the position is within the range (defender/midfielder/attacker)
     private String getValidatedPositionInput() {
         String position;
         while (true) {
             System.out.println("Enter Player's Position (Defender/Midfielder/Attacker):");
             position = scanner.nextLine().toLowerCase(); // Convert input to lowercase
             if (position.equals("defender") || position.equals("midfielder") || position.equals("attacker")) {
                 // Capitalize the first letter and concatenate with the rest of the string
                 return position.substring(0, 1).toUpperCase() + position.substring(1); // makes sure that the sorting algorithm works properly
             } else {
                 System.out.println("Invalid input. Please enter 'Defender', 'Midfielder', or 'Attacker'.");
             }
         }
     }

    // Technique : if else, while,encapsulation, hashmaps,
    // Used to update the player statistics
    public void updatePlayerStats() {

        displayCurrentList();

        System.out.println("\nEnter the name of the player whose stats you want to update:  --Enter blank for main menu");
        String name = scanner.nextLine();
        Player playerToUpdate = playerMap.get(name.toLowerCase()); // uses map to easily get the player names with less time;



        if (playerToUpdate != null) { // if the player is valid
            String option ="";
            while (!option.equals("remove") && !option.equals("update")) {
                System.out.println("Please select if you would like to remove or update a player. Enter 'remove' or 'update'.");
                option = scanner.nextLine().toLowerCase();

                if (option.equals("remove")) { // if ocmmand is to remove, removing playe from list, map and saving data so it can't be accessed in current session
                    players.remove(playerToUpdate);
                    playerMap.remove(name.toLowerCase());
                    saveData();
                    System.out.println("Player removed successfully.");
                } else if (option.equals("update")) { // if update using player setters and getters to change the player stastistics.
                    System.out.println("Updating statistics for " + playerToUpdate.getName() + ".");

                    playerToUpdate.setShotPercentage(getValidatedDoubleInput("Enter new Shot Percentage (Current: " + playerToUpdate.getShotPercentage() + "):"));
                    playerToUpdate.setConversionRate(getValidatedDoubleInput("Enter new Conversion Rate (Current: " + playerToUpdate.getConversionRate() + "):"));
                    playerToUpdate.setPassPercentage(getValidatedDoubleInput("Enter new Pass Percentage (Current: " + playerToUpdate.getPassPercentage() + "):"));
                    playerToUpdate.setDribblePercentage(getValidatedDoubleInput("Enter new Dribble Percentage (Current: " + playerToUpdate.getDribblePercentage() + "):"));
                    playerToUpdate.setCrossesPercentage(getValidatedDoubleInput("Enter new Crosses Percentage (Current: " + playerToUpdate.getCrossesPercentage() + "):"));
                    playerToUpdate.setTacklesPercentage(getValidatedDoubleInput("Enter new Tackles Percentage (Current: " + playerToUpdate.getTacklesPercentage() + "):"));

                    System.out.println("Player stats updated for " + playerToUpdate.getName() + ".");
                } else {
                    System.out.println("Invalid option, please enter 'remove' or 'update'.");
                }
            }
        } else {
            System.out.println("Player not found.");
        }
    }
    // Technique : if else, ArrayList, exception
    // used to compare the stats of the players. user can pick how many players up to 4 they can choose
    public void comparePlayerStats() {

        displayCurrentList();


        // makes sure that the value is less than 4
        System.out.println("\nEnter the number of players you want to compare (up to 4): --Enter blank for main menu");

        int numPlayers;
        while (true) {
            String input = scanner.nextLine();

            // Check for empty input to return to the main menu
            if (input.isEmpty()) {
                System.out.println("Returning to main menu.");
                return;
            }

            try {
                numPlayers = Integer.parseInt(input);
                if (numPlayers > 1 && numPlayers <= 4) {
                    break; // Valid input, exit the loop
                } else {
                    System.out.println("Please enter a number between 2 and 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        // Initializing arraylist over linkedlists as there is not many insertions or deltions being made
        ArrayList<Player> playersToCompare = new ArrayList<>();
        // looping thorugh the number of players chosen
        for (int i = 0; i < numPlayers; i++) {
            while (true) {
                System.out.println("Enter the name of player " + (i + 1) + " (or type 'exit' to return to menu):");
                String playerName = scanner.nextLine();
                if (playerName.equalsIgnoreCase("exit")) return;
                // gathering the name of the player. Used Hashmap
                Player player = playerMap.get(playerName.toLowerCase()); // Use HashMap for quick lookup
                if (player != null) {
                    playersToCompare.add(player); // Adds the found players to the list to use for the next method.
                    break;
                }
                System.out.println("Player '" + playerName + "' not found.");
            }
        }

        printPlayerComparison(playersToCompare); // uses methods to print the comparison in a valid format.
    }
    // Technique : String formatting
    //Used to format the player comparioson that is being displayed, Allows for a cleaner system that the user cann use.
    private void printPlayerComparison(ArrayList<Player> players) {

        System.out.println("Comparing stats:");
        System.out.println("Name | Team | Position | Shot % | Conversion % | Pass % | Dribble % | Crosses % | Tackles %");
        System.out.println("-----------------------------------------------------------------------------------------");

        for (Player player : players) {
            System.out.println(player.getName() + " | " +
                    player.getTeam() + " | " +
                    player.getPosition() + " | " +
                    player.getShotPercentage() + "% | " +
                    player.getConversionRate() + "% | " +
                    player.getPassPercentage() + "% | " +
                    player.getDribblePercentage() + "% | " +
                    player.getCrossesPercentage() + "% | " +
                    player.getTacklesPercentage() + "%");
        }
    }

    //method to export the data to CSV file
    // Technique : Java Class - PrintWriter, Exception, for loops
    public void exportStatsToCSV(String filename) {
        // PrintWriter used in order to write a formatted text into a file.
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) { //attemps to create a file but can disply a error with catch is it dosent work
            // Write the header line for the CSV file
            pw.println("Name,Team,Position,Shot Percentage,Conversion Rate,Pass Percentage,Dribble Percentage,Crosses Percentage,Tackles Percentage");
            players.sort(Comparator.comparing(Player::getName)); // makes sure CSV is in alphabeticall order
            // Write the player data
            for (Player player : players) {
                pw.println(playerToCSVString(player));
            }

            System.out.println("Data successfully exported to " + filename);
        } catch (IOException e) { // when the file cannot be created for any reason such as :it already exists etc.
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }



    // Technique : String formatting
    private String playerToCSVString(Player player) { // helps format all the data that is going to be exported.
        return player.getName() + "," +
                player.getTeam() + "," +
                player.getPosition() + "," +
                player.getShotPercentage() + "," +
                player.getConversionRate() + "," +
                player.getPassPercentage() + "," +
                player.getDribblePercentage() + "," +
                player.getCrossesPercentage() + "," +
                player.getTacklesPercentage();
    }
    // Technique : arrays, Lists, stdDraw,
    // Generates a 11 aside linup with custom linup choice using the best player statistics.
    public void generateLineup() {
        System.out.println("Enter the desired formation (excluding goalkeeper, e.g., '4-4-2', '3-5-2'): ");
        String formation = scanner.nextLine();

        String[] parts = formation.split("-"); //verifies if the formation is valid
        if (parts.length != 3) {
            System.out.println("Invalid formation. Please use the format 'X-X-X'.");
            return;
        }

        int numDefenders;
        int numMidfielders;
        int numAttackers;

        try { //  it takes the parts array that was made from the formation and then settting the data as that length
            numDefenders = Integer.parseInt(parts[0]);
            numMidfielders = Integer.parseInt(parts[1]);
            numAttackers = Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format in formation."); // used for instance were the fomraiton is in a format like (4-a-b) where it wont work as its not a integer
            return;
        }

        if (numDefenders + numMidfielders + numAttackers != 10) { // verifies if it adds up to 10
            System.out.println("The total number of defenders, midfielders, and attackers must add up to 10.");
            return;
        }

        if (players.size() < numDefenders + numMidfielders + numAttackers + 1) { // +1 for the goalkeeper
            System.out.println("Not enough players to form the lineup. At least " + (numDefenders + numMidfielders + numAttackers + 1) + " players are needed.");
            return;
        }

        int numSubstitutesPerPosition = 2; // 2 subs for each position

        List<Player> defenders = filterPlayersByPosition("Defender"); // fills the list with all players that are in that position
        List<Player> midfielders = filterPlayersByPosition("Midfielder");
        List<Player> attackers = filterPlayersByPosition("Attacker");

        List<Player> selectedDefenders = selectBestPlayers(defenders, numDefenders + numSubstitutesPerPosition, "Defender"); // selects the best players of the players by using comparator for specific statistics that are relevant to that position
        List<Player> selectedMidfielders = selectBestPlayers(midfielders, numMidfielders + numSubstitutesPerPosition, "Midfielder");
        List<Player> selectedAttackers = selectBestPlayers(attackers, numAttackers + numSubstitutesPerPosition, "Attacker");

        System.out.println(formation + " Lineup "); // uses print players to list all the payers that atre going to be in the starting linup
        printSelectedPlayers("\nDefenders", selectedDefenders, numDefenders);
        printSelectedPlayers("\nMidfielders", selectedMidfielders, numMidfielders);
        printSelectedPlayers("\nAttackers", selectedAttackers, numAttackers);


        System.out.println("\nSubstitutes:"); // lists out all of the substitutes.
        printSubstitutes("\nDefender Substitutes", selectedDefenders, numDefenders);
        printSubstitutes("\nMidfielder Substitutes", selectedMidfielders, numMidfielders);
        printSubstitutes("\nAttacker Substitutes", selectedAttackers, numAttackers);

        List<Player> allSubstitutes = new ArrayList<>(); // fill out  this list with all substitutes

        // makes sure there is no issues with the size of arrays etc
        if (selectedDefenders.size() > numDefenders) // if there are more excess defender, add to sub
            allSubstitutes.addAll(selectedDefenders.subList(numDefenders, selectedDefenders.size()));
        if (selectedMidfielders.size() > numMidfielders)
            allSubstitutes.addAll(selectedMidfielders.subList(numMidfielders, selectedMidfielders.size()));
        if (selectedAttackers.size() > numAttackers)
            allSubstitutes.addAll(selectedAttackers.subList(numAttackers, selectedAttackers.size()));

        // Visualization
        LineupVisualizer visualizer = new LineupVisualizer(); // create an instance of lineupvisualiser
        visualizer.drawField();
        visualizer.drawFormation(selectedDefenders.subList(0, Math.min(numDefenders, selectedDefenders.size())),
                selectedMidfielders.subList(0, Math.min(numMidfielders, selectedMidfielders.size())),
                selectedAttackers.subList(0, Math.min(numAttackers, selectedAttackers.size())));
        visualizer.drawSubstitutes(allSubstitutes);
        StdDraw.show(); // display on screen when ran
        System.out.println("\nLineup generated successfully.");
    }

    // Technique : If else
    // used by the generate lineup method in order to liist out all the substitues from the list.
    private void printSubstitutes(String positionGroup, List<Player> players, int numStarters) {
        if (players.size() <= numStarters) {
            System.out.println(positionGroup + ": No substitutes available");
        } else {
            System.out.println(positionGroup + ":");
            for (int i = numStarters; i < Math.min(numStarters + 2, players.size()); i++) {
                System.out.println(players.get(i).getName());
            }
        }
    }

    // used by the generate linup method in order to create lists specific to the posions.
    // Technique : ArrayList, For loop, if else
    private List<Player> filterPlayersByPosition(String position) {
        // Filter players by the given position
        List<Player> filteredPlayers = new ArrayList<>(); // creating a new arraylist (used over arrays as the size can be dynamic depending on the amoutn of players in the system)
        for (Player player : players) {
            if (player.getPosition().equalsIgnoreCase(position)) {
                filteredPlayers.add(player); // adds to the array once the players position is valid.
            }
        }
        return filteredPlayers;
    }
    // Technique : If else, For loop
    // used by the generate linup method to print out all the selected starting playres.
    private void printSelectedPlayers(String positionGroup, List<Player> players, int numStarters) {
        if (players.size() < numStarters) {
            System.out.println(positionGroup + ": Not enough players to form the lineup.");
            return;
        }
        System.out.println(positionGroup + ":");
        for (int i = 0; i < numStarters; i++) { // itterates for the amount of starting per position that was previously declared in the seperate method.
            System.out.println(players.get(i).getName());
        }
    }

    // Technique : Java class - Comparator, ArrayLists, Java Class - Stream
    // method used to select the most suitabel player for each position for the starting lineup
    private List<Player> selectBestPlayers(List<Player> players, int numberToSelect, String position) {
        if (players.size() < numberToSelect) { // makes sure to verify that there is enough players to fill up starters for the position.
            System.out.println("Not enough players in position: " + position);
            return new ArrayList<>();
        }

        Comparator<Player> comparator; // comparator being used as I want to compare each psotion players with seperate data sets that are relevant to their position.
        switch (position.toLowerCase()) { // using switch case to differentiate between each position.
            case "defender": // using compare double method from java to compare the specific stastitsics of the players.
                    comparator = Comparator.comparingDouble(p -> p.getTacklesPercentage() + p.getDribblePercentage());
                    break;
            case "midfielder": // it will choose players with higher pass and dribble percentage
                    comparator = Comparator.comparingDouble(p -> p.getPassPercentage() + p.getDribblePercentage());
                    break;
            case "attacker":
                // p represents eac individusal player in the comparator, it compared by using getters.
                    comparator = Comparator.comparingDouble(p -> p.getShotPercentage() + p.getConversionRate());
                    break;
            default:
                System.out.println("Unknown position: " + position);
                return new ArrayList<>();
        }

        return players.stream() // using stream java class in order to sort
                .sorted(comparator.reversed()) // this meaks sure that the player are sorted by position and using a descneding order as a higher % means better performacne.
                .limit(numberToSelect) // this meaks sure that the selected players are only within position limits.
                .collect(Collectors.toList()); // This transomrs ikt back to a list where its able to gather playres
    }
    // Technique : Java Class - PrintWriter, Exception handling, for loops
    // Method used by the system to save all the data in the txt file for future use.
    public void saveData() {
        try (PrintWriter out = new PrintWriter(new FileWriter(dataFilePath))) { // uses printwriter java class inorder to return a fomratted version of the infomraiton in a file
            for (Player player : players) {
                out.println(playerToString(player));
            }
        } catch (IOException e) { //  if file path cant be found thorughts a exception error
            System.err.println("Error saving data: " + e.getMessage());
        }
    }
    // Technique : Exception handling, if else, Java Class: File,BufferReader, Hashmaps, Lists
    // used to load the previoous session data to the current session.
    public void loadData() {
        players.clear(); // clears out the list so that the existing players dont override.
        File file = new File(dataFilePath);

        // Check if the file exists, and if not, create a new file
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("New data file created: " + dataFilePath);
                } else {
                    System.err.println("Failed to create new data file: " + dataFilePath); // this is thrown in instaences such as the file already exisits.
                }
            } catch (IOException e) { // if error occurs when creating.
                System.err.println("Error creating new data file: " + e.getMessage());
                return;
            }
        }

        // Proceed to load data from the file
        try (BufferedReader br = new BufferedReader(new FileReader(file))) { //using bufferreader java IO to read text from file.
            String line;
            while ((line = br.readLine()) != null) {
                Player player = stringToPlayer(line);
                if (player != null) {
                    playerMap.put(player.getName().toLowerCase(), player); // adding the players to the hashmap
                    players.add(player); // adding players to the list
                }
            }
        } catch (IOException e) { //  if it is unable to read the file, throw exception error
            System.err.println("Error loading data: " + e.getMessage());
        }
    }

    // Formating the players when storing in a file
    // Technique : String Formating
    private String playerToString(Player player) {
        // Convert a Player object to a string format for file storage
        return player.getName() + "," + player.getTeam() + "," + player.getPosition() + "," +
                player.getShotPercentage() + "," + player.getConversionRate() + "," +
                player.getPassPercentage() + "," + player.getDribblePercentage() + "," +
                player.getCrossesPercentage() + "," + player.getTacklesPercentage();
    }

    // reading the file and converting it into a player obejct
    // Technique : Encapsulation
    private Player stringToPlayer(String data) {
        // Convert a string from file to a Player object
        String[] values = data.split(",");
        if (values.length == 9) {
            return new Player(values[0], values[1], values[2],
                    Double.parseDouble(values[3]), Double.parseDouble(values[4]), // conbers the string values into a double
                    Double.parseDouble(values[5]), Double.parseDouble(values[6]),
                    Double.parseDouble(values[7]), Double.parseDouble(values[8]));
        }
        return null;
    }

    //Method used to clear files when clear function is called
    public void resetData() {
        players.clear(); // Clear the list of players
        playerMap.clear();
        // Overwrite the file with the empty list
        saveData();
    }

    // Method that calls the recursive bubble sort to organise by position
    // Technique : for loops
    public void listPlayersByPosition() {
        recursiveBubbleSort(players, players.size());
        // Now list the players by position
        for (Player player : players) {
            System.out.println(player.getPosition() + ": " + player.getName());
        }
    }
    // Recursive bubble sort
    // Technique : for loop, if else
    private void recursiveBubbleSort(List<Player> players, int n) {
        if(n==0){
            System.out.println("No players in the list");
            return;
        }

        if (n == 1) return;

        for (int j = 0; j < n - 1; j++) {
            if (players.get(j).getPosition().compareTo(players.get(j + 1).getPosition()) > 0) {
                // Swap players[j] and players[j + 1]
                Player temp = players.get(j);
                players.set(j, players.get(j + 1));
                players.set(j + 1, temp);
            }
        }
        recursiveBubbleSort(players, n - 1); // Recursive call
    }

}




