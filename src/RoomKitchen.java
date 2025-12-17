public class RoomKitchen extends Room {
    public RoomKitchen() {
        super("RoomKitchen", Strings.get("kitchen_name"), Strings.get("kitchen_desc"));
        addItem(new Item(Strings.get("gunky_key_name"), Strings.get("gunky_key_desc")));
        addItem(new Item(Strings.get("soap_name"), Strings.get("soap_desc")));
        addItem(new Item(Strings.get("rag_name"), Strings.get("rag_desc")));
    }

    @Override
    public void enter(Player player) {
        System.out.println(Strings.get("kitchen_enter") + " " + getDescription());
    }

    @Override
    public String getHint() {
        return Strings.get("kitchen_hint");
    }
}
