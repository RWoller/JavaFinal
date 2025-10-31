////Using the Abstract Method criteria by extending an abstract class to use its method
////in Main.java
//public class Room1 implements iRoomModel extends RoomModel{
//
//    private ArrayList<String> userItems;
//    super();
//
//    //constructor
//    public Room1(){
//        //bag of user items
//        userItems = new ArrayList<String>();
//
//    }
//
//    public void Room1() {
//
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("This is the tab testing version.");
//
//        /*
//        Criteria: atleast 6 different classes and demo inheritance
//         */
//
//
//        String Scene = "Dust settles like a thick smog as you arrive to greet the worst day" +
//                "of your life. In this dark, danky room, a warm wind escaping a freshly opened " +
//                "door in the distance blows out the torch " +
//                "and slides a thin key under the door, do you take the torch and key?";
//        String ExitScene = "You sit down and watch a thin heap of clothes jump up out of the corner, grab that" +
//                "key, torch, shimmy between the bars with wide eyes and sun burnt skin like your crazy..." +
//                "sitting down like that...";
//        String EnterScene = "...fast enough to beat the heap of clothes sitting in the corner? The reaction was " +
//                "simultaneous but the frail prisoner fell back as you collected the items. No trust is built as the" +
//                "ghost escapes through the bars, as frail as they are, and never turn back to help you."
//        System.out.println(Scene);
//        System.out.print("Enter yes or no: ");
//        String scene1 = scanner.nextLine().trim().toLowerCase();
//
//        //first branch with close
//        if(scene1.equals("yes")){
//
//            //local class separating dialog that sends to separate parts of room
//            public class EnterDialog{
//                //the user now has a key and a torch, add them
//
//            System.out.println(EnterScene);
//            System.out.print();
//            System.out.println("****************");
//            System.out.print();
//            System.out.println("After using the key to get out of the dungeon, the torch was used to pry open the next " +
//                    "door as the escape lead me into this next room. Strange, I thought I was underground, it looks like" +
//                    "something from outter space? Looking around, I find a suit, get it working, and grab a bag. There's a " +
//                    "terminal with a blinking light...(Pick 1 item: rivet gun, gold foil, fiber optic cable");
//            String enterScene = scanner.nextLine().trim().toUpperCase().replace(' ', '_');
//
//
//            switch(spaceItem){
//                    case RIVET_GUN:
//                        System.out.println("Good choice, what if that ghost comes back?");
//                        break;
//                    case GOLD_FOIL:
//                        System.out.println("I think we can use this, let's follow the wiring diagram too.");
//                        break;
//                    case FIBER_OPTIC_CABLE:
//                        System.out.println("Well, it's not like the old stuff. C'mon, turn it on and let's get out of here!");
//                        break;
//                    default: System.out.println("Just leave the bag empty, we don't need this stuff. We gotta find a way out of here!");
//                }
//
//                //add item to user bag - can only pick 1
//               if(enterScene.equals(spaceItem)){
//                   //adds item to bag
//                    userItems.add(spaceItem);
//                }
//
//                }else{
//                    System.out.println("Must be empty - let's just check out the next room.");
//                    userItems.add("empty bag");
//                }
//
//            }else{
//
//                //game over
//                System.out.println(ExitScene);
//                System.out.println("...Boom!...");
//            }
//        }
//
//
//
//
//
//}