public class Room1{

    //Dungeon - player wakes up here
    private boolean userSelection = false;

    super();
    //constructor

    public Room1(boolean userSelection){
        setUserSelection(userSelection);

        //do what is needed


    }




    //close the userSelection and send back for another
    setUserSelection(false);

    public getUserSelection(){
        return  userSelection;
    }

    public setUserSelection(){
        this.userSelection = true;
    }




}