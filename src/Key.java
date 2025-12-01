public class Key extends Item implements Interactable{
    public Key(String name, String description, String examine, String use, boolean visible, String keyId) {
        super(name, description, examine, use, visible, true);
        this.keyId = keyId;
    }
    private final String keyId;

    public String getDoor() {
        return keyId;
    }

    @Override
    public boolean useOn(RoomObject target, Player player, Room room) {
        if (target instanceof Door door) {
            if (keyId.equals(door.getKeyId())){
                door.unlock();
                System.out.println(door.getName() + " has been unlocked.");

                String nextRoomDirection = room.getExitDirection(door);
                player.move(nextRoomDirection);
                return true;
            } else {
                System.out.println(name = "can't unlock " + door.getKeyId() + ". Try another way.");
                return false;
            }
        } else {
            return false;
        }
    }
}
