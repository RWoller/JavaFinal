//Note: Clean the implement and extends to create hierarchy synchrocity
public class RoomItems implements iRoomModel extends RoomModel{

    super();
    //suggested by group - using for creating a special item for bag
    private String roomNoun;
    private String roomAction;
    private ArrayList<String> itemName;
    private String item1, item2, item3;

    //for the room
    public RoomItems(String noun, String action) {
        //what is action and where is it coming from?

       if(action == null){
           this.roomNoun = noun;
       }
       //what is noun and what is happening with it?
       if(noun == null){
           System.out.println("RoomItems.java = Line 16. The action must belong to a noun!");
            this.roomAction = action;
       }

       //building requirements for a random special room item from interface
       if(action!=null && noun!=null){

           itemName.add(getRoomItemFromList());
       }


    }

    //method for storing list
    public static void addRoomItems() {
        itemName.add(this);

    }

    //method for getting roomItem from list
    public static String getRoomItemFromList() {
        itemName = new ArrayList<String>();
        System.out.println("These are the items in your bag: ");
        for(item : itemName){
            System.out.println(item);
        }

    }

    //I wanted to try a different version of nesting with an interface with enums to override an advanced room item
    enum AdvancedRoomItem implements iRoomModel{
        MULTIPLY("multiplies your item amount by 2!");
        @Override
        public String dialog1(){
            return "You get this item x2!"
        }
    }
}