public class Application {
    private final StatManager statManager;
    private final Menu menu;
    private final GameManager gameManager;

    // loading all the classes
    public Application() {
        statManager = new StatManager(); // loads statManager for when user wishes to perform fucntionalities
        menu = new Menu(); // used to display menu interface
        statManager.loadData(); // Loads previous session data from player_stats.csv
        gameManager = new GameManager();
    }

    // Used for the Menu Interface
    // Technique : Switch case, while loop
    public void start() {
        while (true) { // Infinite loop until the user presses exit option
            menu.displayMenu(); // displays the menu interface
            int choice = menu.getChoice(); // Using another method for the input so cleaner

            switch (choice) { // simpler than using if and else statment / easier to understand.
                case 1:
                    statManager.addNewPlayer(); // adds new player to the class
                    break;
                case 2:
                    statManager.viewPlayerStats(); // displays player stats of the users choice
                    break;
                case 3:
                    statManager.updatePlayerStats(); // updates the statistics of each player
                    break;
                case 4:
                    statManager.comparePlayerStats(); // user can choose selected amount of players that they wish to compare
                    break;
                case 5:
                    statManager.generateLineup(); // generates a lineup with a fomration of the users choice
                    break;
                case 6:
                    statManager.exportStatsToCSV("players_stats.csv"); // exports all stats to a csv called player_stats.csv
                    System.out.println("Player statistics exported to players_stats.csv");
                    break;
                case 7:
                    statManager.listPlayersByPosition(); // uses recursive bubble sort to sort by position
                    break;
                case 8:
                    statManager.resetData(); // completely resets all the data inthe CSV file if the user wants to
                    break;
                case 9:
                    System.out.println("\n1. Add New Game"); // display the menu options for game modification -- used new menu as will be unorgianised with too many options
                    System.out.println("2. View All Games");
                    System.out.println("3. Update Game");
                    gameManager.chooseOption();
                    break;
                case 10:
                    statManager.saveData(); //saves all data in .txt
                    gameManager.saveData(); // saves the game data to game txt
                    System.out.println("Exiting the Football Statistics Application.");
                    System.exit(0); // exits
                    break;
                case 11:
                    menu.getManual();
                }
            }
        }
    }

