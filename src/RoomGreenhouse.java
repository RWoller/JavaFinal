public class RoomGreenhouse extends Room {
    public RoomGreenhouse() {
        super("RoomGreenhouse", "Greenhouse", "A very eerie place with cold damp cobblestone floors and overgrown plantlife.");
        addItem(new Item("Planting Almanac", "A heavy journal with strange logs for disturbing sightings and plant creatures"));
        addNpc(new NPC("Groundskeeper") {
            @Override
            public String speak() {
                return "Some plants... hide secrets... to truth that might want to be dampened.";
            }
        });
    }

    @Override
    public void enter(Player player) {
        System.out.println("You enter the Greenhouse. " + getDescription());
    }

    @Override
    public String getHint() {
        return "Look for things that might not be invited here.";
    }
}
