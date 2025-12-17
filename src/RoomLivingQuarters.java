public class RoomLivingQuarters extends Room {
    public RoomLivingQuarters() {
        super("RoomLivingQuarters", Strings.get("quarters_name"), Strings.get("quarters_desc"));
        addItem(new Item(Strings.get("cipher_name"), Strings.get("cipher_desc")));
        addNpc(new NPC(Strings.get("maiden_name")) {
            @Override
            public String speak() {
                return Strings.get("maiden_speak");
            }
        });
    }

    @Override
    public void enter(Player player) {
        System.out.println(Strings.get("quarters_enter") + " " + getDescription());
    }

    @Override
    public String getHint() {
        return Strings.get("quarters_hint");
    }
}
