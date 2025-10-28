public class RoomItems {

    super();
    private String roomNoun;
    private String roomAction;
    private String roomEvent;
    private ArrayList<String> itemName;
    private String item1, item2, item3;

    //for the room
    public RoomItems(String noun, String actions) {
       if(action == null){
           this.roomNoun = noun;
       }
       if(noun == null){
           System.out.println("RoomItems.java = Line 16. The action must belong to a noun!");

       }
    }

    //method for storing list
    public static String addRoomItems() {
        itemName.add(this);
        return itemName;

    }

    //method for getting roomItem from list
    public static String getRoomItemFromList() {
        //helps with duplicate code and organizing an index to stay within
        //as validation when using one list
        //3 || null + 1 || null + 1 items from list
        //maximum combo item (using 3 singular items to build one large item)
        //example: key, door, keyhole = 1 event

        //example: key = 1 item
        //example: if key is only picked, save key (this will be a reoccurence)
        //example: if key and door is only picked, save key, door in list and use
        //as an option to exit or win when done at a specific time in storyline!


    }
}