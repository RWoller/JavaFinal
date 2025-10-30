import java.util.Scanner;

public class Main implements iRoomModel{
/*
OCP: Oracle Certified Professional Java SE 8 Programmer II Study Guide: Exam 1Z0-809
Brooke Metoxen-Smith
NWTC - Programming in Java Part 2
October 29, 2025
Project 1
 */

    /**
     * GitHub Test push
     * 10/30/25
     * @author Rafael Lakard
     */



    public static void main(String[] args){

        private final String description;
        Room1 room1 = new Room1();
        //this method runs dialog for room1 and entrance into room2
        room1.Room1();

        //using the interface
        public enum SimpleStart{
            SELECTION("selection"){
                @Override
                public getRoomAction(RoomAction action){
                    return SELECTION;
                }
            },
            HEADER("header"){
                @Override
                public getRoomAction(RoomAction action){
                    return HEADER;
                }
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
        int counter = 0;
        for(int i = 0; i <= userItems.size(); i++){
            //counter = index
            System.out.println("Items in bag:");
            for(String item : userItems){
                System.out.println(counter + item);
            }


        }
        return userItems;
    }




}