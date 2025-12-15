public class RoomLivingQuarters extends Room {
    public RoomLivingQuarters() {
        super("RoomLivingQuarters", "Living Quarters", "You stand in an oddly inviting living space with relics that would be worth a fortune and belongs in a museum.");
        addItem(new Item("Antique Cipher", "A curious cipher that was displayed as decor."));
        addNpc(new NPC("Generous Maiden") {
            @Override
            public String speak() {
                return "Welcome... please make yourself welcome, but be please carefully take what you need.";
            }
        });
    }

    @Override
    public void enter(Player player) {
        System.out.println("You step into the . " + getDescription());
    }

    @Override
    public String getHint() {
        return "Try examining the portrait or talking to the maiden.";
    }
}
