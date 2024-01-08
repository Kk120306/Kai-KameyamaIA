import java.util.Scanner;
public class Menu {
    private Scanner scanner;
    public Menu() {
        scanner = new Scanner(System.in); // will be used when gathering the number choice from user
    }

    public void displayMenu(){
        System.out.println("\nFootball Statistics Application");
        System.out.println("1. Add New Player");
        System.out.println("2. View Player Statistics");
        System.out.println("3. Update Player Statistics");
        System.out.println("4. Compare Player Statistics");
        System.out.println("5. Generate Lineup");
        System.out.println("6. Export Player Statistics to CSV");
        System.out.println("7. List Players by Position");
        System.out.println("8. Clear data");
        System.out.println("9. Manage Games");
        System.out.println("10. Exit");
        System.out.println("11. Help");
    }



    public int getChoice(){
        int choice; // doing this so that I dont have to write scanner.nextInt() every time in loop
        while (true) { // Loop indefinitely until a valid input is received
            System.out.print("Enter a value (1-11): ");
            if (scanner.hasNextInt()) { //verifies if the input is a number
                choice = scanner.nextInt(); // Read the input
                if (choice > 0 && choice < 12) { // Check if the input is within the valid range
                    return choice; // Return the valid input
                } else { //else statement if value is not in the desired range
                    System.out.println("Invalid choice. Please select a value within the range 1 to 11.");
                }
            } else { // else statement if no value is inputted.
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Removing the non integer output.
            }

        }
    }

    public int getGameChoice() {
        int choice; // doing this so that I dont have to write scanner.nextInt() every time in loop
        while (true) { // Loop indefinitely until a valid input is received
            System.out.print("Enter a value (1-3): ");
            if (scanner.hasNextInt()) { //verifies if the input is a number
                choice = scanner.nextInt(); // Read the input
                if (choice > 0 && choice < 4) { // Check if the input is within the valid range
                    return choice; // Return the valid input
                } else { //else statement if value is not in the desired range
                    System.out.println("Invalid choice. Please select a value within the range 1 to 3.");
                }
            } else { // else statement if no value is inputted.
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Removing the non integer output.
            }

        }
    }

    public void getManual(){
        System.out.println("\n -- USER MANUAL --");

        System.out.println("\nOption 1: Launching the Application:\n" +
                "\n" +
                "Double-click the application icon to launch.\n" +
                "The main menu will appear with various options.\nOption 2: Adding a New Player:\n" +
                "\n" +
                "Select \"Add New Player\" from the main menu.\n" +
                "Enter the player's full name, team (U16 or U19), position, and statistical data as prompted.\n" +
                "Confirm the details to add the player to your team roster.\n" +
                "\nOption 3: Viewing Player Statistics:\n" +
                "\n" +
                "Select \"View Player Statistics\" from the main menu.\n" +
                "Enter the name of the player whose statistics you wish to view.\n" +
                "The player's statistics will be displayed. You can choose to view another player's stats or return to the main menu.\n" +
                "\nOption 4: Updating Player Statistics:\n" +
                "\n" +
                "Choose \"Update Player Stats\" from the main menu.\n" +
                "Enter the player's name and the specific date for the statistics you want to update. Choose to remove or update.\n" +
                "Provide the new statistical values as prompted.\n" +
                "\nOption 5: Generating a Lineup:\n" +
                "\n" +
                "Select \"Generate Lineup\" from the main menu.\n" +
                "Choose the team (U16 or U19) for which you want to generate a lineup.\n" +
                "Enter the desired formation (e.g., 4-4-2).\n" +
                "The application will display the best lineup based on player statistics.\n" +
                "\nOption 6: Comparing Players:\n" +
                "\n" +
                "Choose \"Compare Player Stats\" from the main menu.\n" +
                "Follow the prompts to enter the names of the players you wish to compare.\n" +
                "The application will display a side-by-side comparison of their statistics.\n" +
                "\nOption 7: Exporting Data:\n" +
                "\n" +
                "Select \"Export Stats to CSV\" from the main menu.\n" +
                "Enter the filename for the CSV file.\n" +
                "The player data will be exported to the specified file.\n" +
                "\nOption 8: Clear data\n" +
                "Clears exsiting data of players in the system."+
                "\nOption 9: Manage Games\n" +
                "Add games with game statistics.");

    }


}
