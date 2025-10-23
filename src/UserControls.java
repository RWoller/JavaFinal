public class UserControls{

    super();

    //information on item
    private String inspect;
    //storing the item
    private String take;
    //matching it with room item pair and continuing story
    private String use;
    //taking an item to store flag
    private boolean valid = false;
    //storing the items for user controls inventory
    private ArrayList<String> userItems;

    //constructor for checking out an item to keep
    public UserControls(String inspect, String take, boolean valid) {
        this.inspect = inspect;
        this.take = take;
        this.valid = valid;

        //get information to reveal inspect about the RoomItems (class)
        RoomItems roomItems = new RoomItems();



        if(valid = true){
            System.out.println("UserControls - checking item and storing item.");
            //adding item to array list
            userItems.append(take);

        }
    }


}