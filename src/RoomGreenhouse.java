public class RoomGreenhouse extends Room {
    public RoomGreenhouse() {
        super("RoomGreenhouse", Strings.get("greenhouse_name"), Strings.get("greenhouse_description"));
        addItem(new Item(Strings.get("planting_almanac_name"), Strings.get("planting_almanac_desc")));
        addNpc(new NPC(Strings.get("groundskeeper_name")) {
            @Override
            public String speak() {
                return Strings.get("groundskeeper_speak");
            }
        });
    }

    @Override
    public void enter(Player player) {
        System.out.println(Strings.get("greenhouse_enter") + " " + getDescription());
    }

    @Override
    public String getHint() {
        return Strings.get("greenhouse_hint");
    }
}
