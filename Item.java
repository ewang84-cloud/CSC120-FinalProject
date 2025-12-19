public class Item {
    private String name;
    private String description;
    private boolean usable;

    /**
     * Constructor
     * @param name Item name
     * @param description Item description
     * @param usable Whether the item can be used
     */
    public Item(String name, String description, boolean usable) {
        this.name = name;
        this.description = description;
        this.usable = usable;
    }

    /**
     * Use the item (just prints a message)
     */
    public void use() {
        if (usable) {
            System.out.println("You used the " + name + ".");
        } else {
            System.out.println("You can't use the " + name + ".");
        }
    }

    /**
     * accessor of the name of the item
     * @return the name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * accessor of the description of the item
     * @return the description of the item
     */
    public String getDescription() {
        return description;
    }

    /**
     * accessor of checking if the item is able to use or not
     * @return return true if the item could be used, flase if not
     */
    public boolean isUsable() {
        return usable;
    }
}
