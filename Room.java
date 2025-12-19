import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Room {
    private String name;
    private String description;
    private Map<String, Room> exits;
    private List<Item> items; 

    /**
     * constructor
     * @param name name of the room
     * @param description description of the room, what's in it and what it is
     */
    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        this.exits = new HashMap<>();
        this.items = new ArrayList<>();
    }

    /**
     * setting the rooms in certain direction
     * @param direction directions like north, east, south, west
     * @param room the room in that direction
     */
    public void setExit(String direction, Room room) {
        exits.put(direction.toLowerCase(), room);
    }

    /**
     * getting the room in a certain direction
     * @param direction the direction
     * @return return the room in this certain direction, return null if there's none
     */
    public Room getExit(String direction) {
        return exits.get(direction.toLowerCase());
    }

    /**
     * get all the directions that has a exit to another room
     * @return the string representing the direction, like"north"
     */
    public String getExitString() {
        if (exits.isEmpty()) {
            return "There's no exit";
        }
        return String.join(", ", exits.keySet());
    }

    /**
     * add an item to the room
     * @param item the item that you're adding
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * remove an item from the room
     * @param itemName the item
     * @return the item that has been removed, return null if the item is not in the room in the first place
     */
    public Item removeItem(String itemName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                items.remove(item);
                return item;
            }
        }
        return null;
    }

    /**
     * check is a certain room has a certain item
     * @param itemName the item that you're checking
     * @return if there's this item in the room, return true
     */
    public boolean hasItem(String itemName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * get the describtion of all the items in this room
     * @return the list of the items
     */
    public String getItemsString() {
        if (items.isEmpty()) {
            return "There's no item in this room";
        }
        StringBuilder sb = new StringBuilder("There are:");
        for (int i = 0; i < items.size(); i++) {
            sb.append(items.get(i).getName());
            if (i < items.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    /**
     * Get the full description of a certian roon, including the items and exits in it
     * @return this full description
     */
    public String getFullDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=== ").append(name).append(" ===\n");
        sb.append(description).append("\n\n");
        sb.append(getItemsString()).append("\n");
        sb.append("exits: ").append(getExitString()).append("\n");
        return sb.toString();
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Item> getItems() {
        return new ArrayList<>(items); 
    }
}