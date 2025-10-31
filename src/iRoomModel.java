//interface iRoomModel{
//    //interface use is top-level interface that can only be
//    //public or package-private (default)
//    /*
//    Main implements iRoomModel and is going to be used for connecting
//    the controller when navigating which room is active in dialog with Main.java
//     */
//    enum RoomAction{CRASHING, SWISHING, CRANKING, FOOTSTEPS, ECHOES, DIZZY , REELING};
//    enum Room1{TORCH, KEY, HANDLE};
//    enum Room2{RIVET_GUN, GOLD_FOIL, FIBER_OPTIC_CABLE};
//    enum Room3{LEFT, RIGHT};
//
//    //static list
//    List<RoomAction> ROOM_ACTION_LIST = new ArrayList<>();
//
//    //page 44, using for Room 1 and Room 2 items for Room Action/Events
//    //abstract method to be implemented by classes
//    void getRoomAction(RoomAction action){
//        if(action instanceof Room1.TORCH){
//            //torch action from room if picked
//        }else if(action instanceof Room1.HANDLE){
//            //handle action from room if picked
//        }else if(action instanceof Room1.KEY){
//            //key action from room if picked
//        }else if(action instanceof Room2.FIBER_OPTIC_CABLE){
//            //fiber optic cable for room2 if picked
//            //its added to bag in another statement, this is the room reaction
//        }else if(action instanceof Room2.RIVET_GUN){
//            //rivet gun for room 2 if picked for room actions/events
//        }else if(action instanceof Room2.GOLD_FOIL){
//            //gold foil if picked for room actions in room 2
//            // create a special item?
//        }else{
//            throw new RunTimeException("Unsupported selection.");
//        }
//    }
//
//}