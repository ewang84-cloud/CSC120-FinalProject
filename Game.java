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
        Room waitingPlatform = new Room("The Waiting Platform", "You are on a grand waiting platform with marble floors. A gentle mist surrounds you.");
        Room library = new Room("Memory Library", "You are in a dusty library filled with old books. Each book seems to hold someone's forgotten story.");
        Room garden = new Room("Quiet Garden", "You are in a peaceful garden with wilting flowers. Time feels different here.");
        Room kitchen = new Room("Childhood Kitchen", "You are in a warm kitchen. It smells like home-cooked meals from long ago.");
        Room musicRoom = new Room("Echo Chamber", "You are in a room filled with old instruments. Faint melodies echo in the air.");
        Room attic = new Room("Forgotten Attic", "You are in a dusty attic full of abandoned belongings. Sunlight filters through a small window.");

        // Connect rooms - more interesting layout
        // Waiting Platform connects to: north (library), south (garden), east (kitchen)
        waitingPlatform.setExit("north", library);
        waitingPlatform.setExit("south", garden);
        waitingPlatform.setExit("east", kitchen);

        // Library connects to: south (platform), west (music room)
        library.setExit("south", waitingPlatform);
        library.setExit("west", musicRoom);

        // Garden connects to: north (platform)
        garden.setExit("north", waitingPlatform);

        // Kitchen connects to: west (platform), north (attic)
        kitchen.setExit("west", waitingPlatform);
        kitchen.setExit("north", attic);

        // Music Room connects to: east (library)
        musicRoom.setExit("east", library);

        // Attic connects to: south (kitchen)
        attic.setExit("south", kitchen);

        // Create 4 items (player only needs 3)
        Item locket = new Item("locket", "A silver locket with a faded photograph inside", false);
        Item letter = new Item("letter", "An old handwritten letter, slightly torn at the edges", false);
        Item photograph = new Item("photograph", "A black and white photograph of smiling faces", false);
        Item musicBox = new Item("music-box", "A small music box that plays a familiar tune", true);

        // Place items in different rooms
        library.addItem(letter);
        garden.addItem(photograph);
        musicRoom.addItem(musicBox);
        attic.addItem(locket);

        // Create player
        player = new Player(waitingPlatform);
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
        System.out.println("You are a soul that just passed away from the earth.");
        System.out.println("This is the middle place between real life and heaven - we call it the Station.");
        System.out.println("All people come to the Station and look for 3 memory items that carry their memories.");
        System.out.println("Your objective is to collect ANY THREE memory items in order to move on. Good luck!\n");
        
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
            // Check if player has collected 3 items
            if (player.getInventorySize() >= 3) {
                System.out.println("\n🎉 Congratulations! You have collected 3 memory items!");
                System.out.println("Type 'quit' to leave this place and move on to the next step.\n");
            }
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