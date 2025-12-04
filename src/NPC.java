import java.util.ArrayList;
import java.util.List;

/**
 * Non-player character with dialog and inventory.
 */
public class NPC {
    protected String name;
    protected final List<Item> inventory = new ArrayList<>();
    protected int health;

    public NPC(String name) {
        this.name = name == null ? "NPC" : name;
        this.health = 100;
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    public void healHealth(int amount) {
        this.health += amount;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public String getName() { return name; }
    public List<Item> getInventory() { return inventory; }
    public int getHealth() { return health; }

    public void addItem(Item item) {
        if (item != null) inventory.add(item);
    }

    public void removeItem(Item item) {
        inventory.remove(item);
    }

    public String speak() {
        return "They have nothing to say.";
    }
}
