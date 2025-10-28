public class UserControls{

    Main main = new Main();

    super();

    //information on item
    private String inspect;
    //information from inspecting item
    private String inspection = null;
    //storing the item
    private String take;
    //matching it with room item pair and continuing story
    private String use;
    //taking an item to store flag
    private boolean valid = false;
    //storing the items for user controls inventory
    private ArrayList<String> userItems;

    //constructor for checking out an item to keep
    public UserControls(boolean valid) {
        this.inspect = inspect;
        this.take = take;
        //default is false - reject item
        this.valid = valid;

        if(valid = true){

            //adding item to array list
            List<String> userItems = getInspection();

            //exchange "take" for what the user is taking in this round
            take = main(new EnterDialog());


            //add it to the list
            userItems.add(take);

            //get the approved inspection list
            System.out.println("UserControls - checking item information and storing item for: " + userItems);

            //

        }
        if(valid = false){
            System.out.println("UserControls - false checking item information and rejecting item.")
        }
        //close this control
        valid = false;
    }

    //getting the entire list from here to send to Room1
    public static List<String> getInspection(){
        ArrayList<String> userItems = new ArrayList<>();
        userItems.add("torch");
        userItems.add("handle");
        userItems.add("key");

        return userItems;
    }

    //
    public static void setInspection(String inspection){

    }


}