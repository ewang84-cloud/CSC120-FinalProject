import java.util.ArrayList;
import java.util.List;

public class Player {
    private Room currentRoom;
    private List<Item> inventory;

    /**
     * Constructor
     * @param startRoom The starting room of this player
     */
    public Player(Room startRoom) {
        this.currentRoom = startRoom;
        this.inventory = new ArrayList<>();
    }

    /**
     * Move to another room in a given direction
     * @param direction Direction to move
     * @return return true if move was successful, false otherwise
     */
    public boolean move(String direction) {
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom != null) {
            currentRoom = nextRoom;
            return true;
        }
        return false;
    }

    /**
     * Pick up an item from the current room
     * @param itemName Name of the item
     * @return true if successful, false otherwise
     */
    public boolean takeItem(String itemName) {
        Item item = currentRoom.removeItem(itemName);
        if (item != null) {
            inventory.add(item);
            return true;
        }
        return false;
    }

    /**
     * Use an item from inventory
     * @param itemName Name of the item to use
     * @return true if successful, false otherwise
     */
    public boolean useItem(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                item.use();
                return true;
            }
        }
        return false;
    }

    /**
     * Get the whole inventory as a string
     * @return String describing inventory contents
     */
    public String getInventoryString() {
        if (inventory.isEmpty()) {
            return "Your inventory is empty.";
        }
        StringBuilder sb = new StringBuilder("Inventory: ");
        for (int i = 0; i < inventory.size(); i++) {
            sb.append(inventory.get(i).getName());
            if (i < inventory.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    /**
     * accessor of the current room that the player is in
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * accessor of the inventory of items that the player has
     */
    public List<Item> getInventory() {
        return new ArrayList<>(inventory);
    }
}
