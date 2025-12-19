
import java.util.Scanner;

/**
 * Game class - main controller for the text adventure game
 */
public class Game {
    private Player player;
    private boolean isRunning;
    private Scanner scanner;

    /**
     * Constructor - initializes the game world
     */
    public Game() {
        scanner = new Scanner(System.in);
        initializeWorld();
        isRunning = true;
    }

    /**
     * Set up rooms, items, and player starting position
     */
    private void initializeWorld() {
        // Create rooms
        Room entrance = new Room("The Waiting Platform", "You are in a grand waiting platform with marble floors.");
        Room library = new Room("Library", "You are in a dusty library filled with old books.");
        Room kitchen = new Room("Kitchen", "You are in a kitchen. It smells like old food.");
        Room garden = new Room("Garden", "You are in a beautiful garden with flowers.");

        // Connect rooms
        entrance.setExit("north", library);
        entrance.setExit("east", kitchen);
        entrance.setExit("south", garden);

        library.setExit("south", entrance);
        kitchen.setExit("west", entrance);
        garden.setExit("north", entrance);

        // Create items
        Item key = new Item("key", "A rusty old key", false);
        Item book = new Item("book", "An ancient book", false);
        Item apple = new Item("apple", "A red apple", true);

        // Place items in rooms
        entrance.addItem(key);
        library.addItem(book);
        kitchen.addItem(apple);

        // Create player
        player = new Player(entrance);
    }

    /**
     * Main game loop
     */
    public void play() {
        printWelcome();

        while (isRunning) {
            System.out.print("\n> ");
            String input = scanner.nextLine().trim();
            processCommand(input);
        }

        scanner.close();
        System.out.println("Thanks for playing!");
    }

    /**
     * Print welcome message
     */
    private void printWelcome() {
        System.out.println("\n=================================");
        System.out.println("Welcome to One More Step");
        System.out.println("=================================\n");
        
        // Game story and premise
        System.out.println("You are an soul that just passed away from the earth");
        System.out.println("This is the middleplace from reallife to heaven, we call it the station");
        System.out.println("all people comes to the station and look for 3 memory items that carries their memories");
        System.out.println("Your objective is to collect three memory items in order to go to the next step. Good luck\n");
        
        System.out.println("Type 'help' for available commands.\n");
        System.out.println(player.getCurrentRoom().getFullDescription());
    }

    /**
     * Process player command
     * @param input Player's input string
     */
    private void processCommand(String input) {
        if (input.isEmpty()) {
            return;
        }

        String[] words = input.toLowerCase().split(" ", 2);
        String command = words[0];

        switch (command) {
            case "go":
                if (words.length < 2) {
                    System.out.println("Go where?");
                } else {
                    goRoom(words[1]);
                }
                break;

            case "look":
                System.out.println(player.getCurrentRoom().getFullDescription());
                break;

            case "take":
                if (words.length < 2) {
                    System.out.println("Take what?");
                } else {
                    takeItem(words[1]);
                }
                break;

            case "inventory":
                System.out.println(player.getInventoryString());
                break;

            case "use":
                if (words.length < 2) {
                    System.out.println("Use what?");
                } else {
                    useItem(words[1]);
                }
                break;

            case "help":
                printHelp();
                break;

            case "quit":
                isRunning = false;
                break;

            default:
                System.out.println("Unknown command. Type 'help' for available commands.");
                break;
        }
    }

    /**
     * Handle go command
     */
    private void goRoom(String direction) {
        if (player.move(direction)) {
            System.out.println(player.getCurrentRoom().getFullDescription());
        } else {
            System.out.println("You can't go that way.");
        }
    }

    /**
     * Handle take command
     */
    private void takeItem(String itemName) {
        if (player.takeItem(itemName)) {
            System.out.println("You picked up the " + itemName + ".");
        } else {
            System.out.println("There is no " + itemName + " here.");
        }
    }

    /**
     * Handle use command
     */
    private void useItem(String itemName) {
        if (!player.useItem(itemName)) {
            System.out.println("You don't have a " + itemName + ".");
        }
    }

    /**
     * Print help message
     */
    private void printHelp() {
        System.out.println("\nAvailable commands:");
        System.out.println("  go <direction>  - Move to another room (north/south/east/west)");
        System.out.println("  look            - Look around the current room");
        System.out.println("  take <item>     - Pick up an item");
        System.out.println("  inventory       - Check your inventory");
        System.out.println("  use <item>      - Use an item from your inventory");
        System.out.println("  help            - Show this help message");
        System.out.println("  quit            - Exit the game");
    }

    /**
     * Main method to start the game
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}