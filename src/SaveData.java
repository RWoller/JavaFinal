/**
 * Simple data holder used for DB load/save operations.
 */
public class SaveData {
    public final String playerName;
    public final String currentRoom;
    public final String inventory;

    public SaveData(String playerName, String currentRoom, String inventory) {
        this.playerName = playerName;
        this.currentRoom = currentRoom;
        this.inventory = inventory;
    }
}
