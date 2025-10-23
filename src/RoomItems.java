public class RoomItems{

    super();
    private String roomNoun;
    private String roomActions;
    private String roomEvent;
    private ArrayList<String> itemName;
    private String item1, item2, item3;

    //for the room
    public RoomItems(String noun, String actions){
        //exit - quit early
        this.roomNoun = noun;
        //saver search - item to collect in room
        this.roomActions = actions;
    }

    //method for storing list
    public static String addRoomItems(){

    }

    //method for getting roomItem from list
    public static String getRoomItemFromList(){
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

    //method for setting a three item event
    public static String setRoomEventFromList(String item1, String item2, String item3){
        //create an event to use later in story
        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;

        if(item1 == null && item2 == null){
            //default this to receiving one value for item3 to create an event
            this.roomEven = item3;
            //key
        }

        //save key to userControls
        UserControls userControls = new UserControls();
        userControls.add(item3);

    }

    public static String
    //what I want the room to do for the user
    //providing its options, the program has to list these options to
    //interact with to continue

    //



}