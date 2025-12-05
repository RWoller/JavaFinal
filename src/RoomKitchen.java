public class RoomKitchen extends Room {

    public RoomKitchen() {
        super("Kitchen", "You see a deep sink and dusty cupboards. There's a greasy old cookbook on the counter.");

        // Instead of adding Cookbook as a plain Item,
        // we make a Container<Item> that *contains* the Gunky Key.

        // Create the key that is hidden in the cookbook
        Item gunkyKey = new Item(
                "Gunky Key",
                "A metal key caked in gunk. It won't fit in a lock yet.",
                false
        );

        // Create the cookbook container
        Container<Item> cookbook = new Container<>(
                "Cookbook",
                "A greasy old cookbook. Some pages look stuck together.",
                gunkyKey
        );

        // Add the container (the cookbook itself) to the room
        addItem(cookbook);

        // Add the other kitchen items normally
        addItem(new Item("Soap", "Cuts through grease and grime.", true));
        addItem(new Item("Sink", "Old but still has running water.", true));
        addItem(new Item("Rag", "Old but still absorbent.", true));
    }

    @Override
    public void enter(Player player) {
        System.out.println(getDescription());
    }
}
