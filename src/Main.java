import java.util.Scanner;

public class Main {
    private ArrayList<String> userItems;
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("This is the tab testing version.");
        Room1 room1 = new Room1();
        UserControls userControls = new UserControls();
        RoomItems roomItems = new RoomItems();

        String Scene = "Dust settles like a thick smog as you arrive to greet the worst day" +
                "of your life. In this dark, danky room, a warm wind escaping a freshly opened " +
                "door in the distance blows out the torch " +
                "and slides a thin key under the door, do you take the torch and key?";
        String ExitScene = "You sit down and watch a thin heap of clothes jump up out of the corner, grab that" +
                "key, torch, shimmy between the bars with wide eyes and sun burnt skin like your crazy..." +
                "sitting down like that...";
        String EnterScene = "...fast enough to beat the heap of clothes sitting in the corner? The reaction was " +
                "simultaneous but the frail prisoner fell back as you collected the items. No trust is built as the" +
                "ghost escapes through the bars, as frail as they are, and never turn back to help you."
       System.out.println(Scene);
       System.out.print("Enter yes or no: ");
       String scene1 = scanner.nextLine().trim().toLowerCase();
       if(scene1.equals("yes")){
           public class EnterDialog{
               //the user now has a key and a torch, add them
            System.out.println(EnterScene);
            System.out.print();
            System.out.println("****************");
            System.out.print();
            System.out.println("After using the key to get out of the dungeon, the torch was used to pry open the next " +
                    "door as the escape lead me into this next room. Strange, I thought I was underground, it looks like" +
                    "something from outter space? Looking around, I find a suit, get it working, and grab a bag. There's a " +
                    "terminal with a blinking light...(Pick 1 item: banana rivet gun, gold foil, fiber optic cable");
               String enterScene = scanner.nextLine().trim().toUpperCase().replace(' ', '_');
               //enum
               Grade enterGrade = Grade.enterScene;
            switch(enterGrade){
                   case BANANA_RIVET_GUN:
                       System.out.println("Good choice, what if that ghost comes back?");
                       break;
                   case GOLD_FOIL:
                       System.out.println("I think we can use this, let's follow the wiring diagram too.");
                       break;
                   case FIBER_OPTIC_CABLE:
                       System.out.println("Well, it's not like the old stuff. C'mon, turn it on and let's get out of here!");
                       break;
                   default: System.out.println("Just leave the bag empty, we don't need this stuff. We gotta find a way out of here!");
               }

               //send to room2


           }else{

               //sit back down and wait as the game shifts to the mate who picked it up and ran!
               System.out.println(ExitScene);
               System.out.println("...The end...");
           }
       }




        //setup the override here for room

        //setup the override here for userControls



        //need to parse user inputs

        /*
        Apache Commons return all of the instance variables in a String
        this has a caller ID -
        public String toString(){
            return
            ToStringBuilder.reflectionToString(this);
            }


         formatted version of constructor? for user items in bag?
         @Override public String toString(){
            return
         ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
         }

         //does not override equals() from Object, it overloads that method,
         //which is not intended - this is to transfer objects in enum to bag with item types
         //if this needs to get complicated later
         public boolean equals(DifferentTypeObject becauseItsAnObj){
            if(becauseItsAnObj == null){
                return false;
             }
             //the conversion begins here to accept it as an object to be included based on model
             return this.idNumber == becauseItsAnObj.idNumber;

         */
        //each room has unique setup for dialog and options

        //main follows prompt flags set for timing and race issues

        //exception handles for following storyline
        //exception handles for user selections

        //the user does not do a "return" or "back" feature


        scanner.close();


    }

    //empty bag that holds users items
    public ArrayList<String> getUserItems(){
        ArrayList<String> userItems = new ArrayList<>();
        counter = 0;
        for(int i = 0; i <= userItems.length; i++){
            counter = index;


        }
        return userItems;
    }

    public ArrayList<String> setUserItems(String selection){

        ArrayList<String> userItems = new ArrayList<>();
        //display what the indexes are in the bag to the user using console

    }



}