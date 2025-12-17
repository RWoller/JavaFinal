public class RoomRoyalChamber extends Room {
    public RoomRoyalChamber() {
        super("RoomRoyalChamber", Strings.get("chamber_name"), Strings.get("chamber_desc"));
        addItem(new Item(Strings.get("rapier_name"), Strings.get("rapier_desc")));
    }

    @Override
    public void enter(Player player) {
        System.out.println(Strings.get("chambers_enter") + " " + getDescription());
    }

    @Override
    public String getHint() {
        return Strings.get("chambers_hint");
    }
}
