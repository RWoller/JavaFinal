import java.util.ArrayList;
import java.util.List;

public class NPC {
    protected String name;
    protected List<RoomObject> inventory;
    protected int health;

    public void takeDamage(int damage) {
        health -= damage;
    }

    public void healHealth(int health) {
        this.health += health;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public String getName() { return name; }
    public List<RoomObject> getInventory() { return inventory; }
    public int getHealth() { return health; }

    public NPC(String name) {
        this.name = name;
        this.inventory = new ArrayList<>();
        this.health = 100;
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public void removeItem(Item item) {
        inventory.remove(item);
    }
}
