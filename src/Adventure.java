/**
 *Name: Roger Miao
 *Date Created: 4/20/2015
 *HW #3
 *Program Description: Text adventure game where you have to collect GEMs to win. You can move, look, collect, and more!! Ages 6+!!
 */
import java.util.Scanner;

public class Adventure {
   public static Scanner kb = new Scanner(System.in);

   //These will be used to determine how much space is needed in the array.
   static final int NUMBER_OF_ROOMS = 6;
   static final int NUMBER_OF_ATTRIBUTES = 5;

   //Same here
   static final int NUMBER_OF_ITEMS = 1;
   static final int NUMBER_OF_PROPERTIES = 1;

   //Labels to use for our character's array
   static final int NUMBER_OF_CHARACTER_ATTRIBUTES = 2;

   //Our character
   public static String[] character = new String[NUMBER_OF_CHARACTER_ATTRIBUTES];

   //Our map, defined here outside of any method, so we can access it inside all of them.
   public static String[][] rooms = new String[NUMBER_OF_ROOMS][NUMBER_OF_ATTRIBUTES];

   //Items that can be LOOKed at otherwise interacted with.
   public static String[][] items = new String[NUMBER_OF_ITEMS][NUMBER_OF_PROPERTIES];

   public static int[] inventory = new int[NUMBER_OF_ITEMS];

   //Constants to give us cleaner naming instead of using numbers to index our arrays
   static final int NAME = 0;
   static final int DESCRIPTION = 1;
   static final int EXITS = 2;
   static final int ITEMS = 3;
   static final int ITEMCOUNT = 4;


   //Labels for items
   static final int GEM = 0;

   //Labels for rooms
   static final int LOBBY = 0;
   static final int HALLWAY = 1;
   static final int BEDROOM = 2;
   static final int SMELLY = 3;
   static final int SKETTIOS = 4;
   static final int RITUAL = 5;

   //Labels for our character array
   //static final int INVENTORY = 1;

   //For keeping track of our current position on the map.
   static String currentRoom = "lobby";

   static boolean gameOver = false;
   static String introduction = "You walk into the mansion and immediately the door slams behind you.\nA voice plays over the intercom.\n\"Collect four Gems if you wish to leave...\"\nYou had better LOOK around for some clues.\nYou should also periodically CHECK your GEMs.";

   //First we create the map in the computer memory, then prompt the user for input repeatedly to take in commands.
   public static void main(String[] args){
      createCharacter();
      createMap();
      System.out.println(introduction);
      while(!gameOver){
         getCommand();
      }
   }

   //create the character
   public static void createCharacter() {
      System.out.println("Enter your name:");
      character[0] = kb.nextLine();
      System.out.println("Hello " + character[0] + ".");
   }

   //Get commands as strings, I prompt the user with a ">"
   private static void getCommand() {
      System.out.print("> ");
      String command = kb.nextLine();
      //this only happens if they typed at least two words:
      if(command.contains(" ")){
         String[] input = command.split(" ");
         command = input[0];
         String target = input[1];
      
         doCommand(command, target);
      }
      else{
         doCommand(command);
      }
   }

   //This method creates all the rooms and their characteristics.
   private static void createMap() {
      //Bedroom descriptions
      rooms[LOBBY][NAME] = "lobby";
      rooms[LOBBY][DESCRIPTION] = "You find yourself stuck in the Lobby.\nThere is a door that leads to the HALLWAY, the SMELLY room, the RITUAL room and the SKETTIOS room.\nYou should GO to one of those rooms.\n";
      rooms[LOBBY][EXITS] = "hallway, smelly, skettios, ritual";
      rooms[LOBBY][ITEMS] = "";
      rooms[LOBBY][ITEMCOUNT] = "no";
      //Hallway Description
      rooms[HALLWAY][NAME] = "hallway";
      rooms[HALLWAY][DESCRIPTION] = "A boring hallway."
             +"\nWait...Is that?..A GEM on the floor! GET it and go to the next room.\nYou can go to the BEDROOM or head back to the LOBBY.\n";
      rooms[HALLWAY][EXITS] = "bedroom, lobby";
      rooms[HALLWAY][ITEMS] = "gem";
      rooms[HALLWAY][ITEMCOUNT] = "yes";
      //BEDROOM descriptions
      rooms[BEDROOM][NAME] = "bedroom";
      rooms[BEDROOM][DESCRIPTION] = "This room... I have a bad feeling about this room..\nHey! There's another GEM!\nYou can go back to the HALLWAY.\n";
      rooms[BEDROOM][EXITS] = "hallway";
      rooms[BEDROOM][ITEMS] = "gem";
      rooms[BEDROOM][ITEMCOUNT] = "yes";
      //SMELLY descriptions
      rooms[SMELLY][NAME] = "smelly";
      rooms[SMELLY][DESCRIPTION] = "Ugh... This room smells like a lot of Hooplah...\nYou can go back to the LOBBY\n";
      rooms[SMELLY][EXITS] = "lobby";
      rooms[SMELLY][ITEMS] = "gem";
      rooms[SMELLY][ITEMCOUNT] = "yes";
      //Skettios descriptions
      rooms[SKETTIOS][NAME] = "skettios";
      rooms[SKETTIOS][DESCRIPTION] = "There is a lot of treasure here. There has to be a GEM in here...\nYou can go back to the LOBBY.\n";
      rooms[SKETTIOS][EXITS] = "lobby";
      rooms[SKETTIOS][ITEMS] = "gem";
      rooms[SKETTIOS][ITEMCOUNT] = "yes";
      //ENDING room description
      rooms[RITUAL][NAME] = "ritual";
      rooms[RITUAL][DESCRIPTION] = "This room is really mysterious...\nMaybe you can END it all here..\nIf not.. You can go back to the LOBBY.\n";
      rooms[RITUAL][EXITS] = "lobby";
      rooms[RITUAL][ITEMS] = "";
      rooms[RITUAL][ITEMCOUNT] = "no";
   }

   //If they didn't type a target, pass a blank one.
   private static void doCommand(String command) {
      doCommand(command, "");
   }

   //Trigger the matching function.
   public static void doCommand(String command, String target){
      command = command.toLowerCase();
      target = target.toLowerCase();
   
      switch(command){
         case "look":
            if(target.length() > 0){
               look(target);
            }
            else{
               look();
            }
            break;
         case "go":
            if(target.length() > 0){
               move(target);
            }
            else{
               System.out.println("Where to?");
               String whereTo = kb.nextLine();
               move(whereTo);
            }
            break;
         case "check":
            if(target.length() > 0) {
               check(target);
            }
            break;
         case "get":
            if(target.length() > 0) {
               get(target);
            }
            break;
         case "end":
            endConditions();
            break;
         default:
            System.out.println("You can't do that.");
      }
   }

   //Move our character to a destination by checking current index and checking if the current room contains the exit they're trying to got to
   public static void move(String destination){
      boolean found = false;
      int index = getRoomNumber(currentRoom);
      int index2 = getRoomNumber(destination);
      if(rooms[index][EXITS].contains(destination)){
         currentRoom = rooms[index2][NAME];
         found = true;
      }
      if (found) {
         System.out.println("You enter a new room. You should probably LOOK around.");
      }
   
      if(!found){
         System.out.println("You can't go there.");
      }
      else{
      }
   
   }
   //checks inventory for how many GEMS you have
   public static void check(String item){
      if (item.equalsIgnoreCase("gem") && inventory[GEM] == 1) {
         System.out.println("You have " + inventory[GEM] + " gem.");
      } 
      else if (item.equalsIgnoreCase("gem") && inventory[GEM] == 0) {
         System.out.println("You have " + inventory[GEM] + " gems.");
      } 
      else {
         System.out.println("You have " + inventory[GEM] + " gems.");
      }
   }

   //Looks through the rooms and returns the index of our destination.
   private static int getRoomNumber(String destination) {
      destination = destination.toLowerCase();
      switch(destination) {
         case "lobby":
            return LOBBY;
         case "hallway":
            return HALLWAY;
         case "bedroom":
            return BEDROOM;
         case "smelly":
            return SMELLY;
         case "skettios":
            return SKETTIOS;
         case "ritual":
            return RITUAL;
         default:
            return -1;
      }
   }


   //Prints the description of the item you are LOOKing at
   public static void look(String item){
      for(int itemNum = 0; itemNum < items.length ; itemNum++){
         if(item.equalsIgnoreCase(items[itemNum][NAME])){
            System.out.println(items[itemNum][DESCRIPTION]);
         }
      }
   }

   //Prints the description of the room, the items list, and the exits list.
   public static void look(){
      int CURRENT_ROOM_INDEX = getRoomNumber(currentRoom);
      System.out.print(rooms[CURRENT_ROOM_INDEX][DESCRIPTION]);
   }

   //Add the item to our inventory, if it is in the room.
   public static void get(String item){
      if (rooms[getRoomNumber(currentRoom)][ITEMCOUNT].equals("yes")) {
         if (item.equalsIgnoreCase(rooms[getRoomNumber(currentRoom)][ITEMS])) {
            inventory[GEM]++;
            rooms[getRoomNumber(currentRoom)][ITEMCOUNT] = "no";
            System.out.println("You picked up a GEM.");
         }
      } 
      else {
         System.out.println("There is no GEM in this room.");
      }
   }
   //ending conditions for the game
   public static void endConditions() {
      int index = getRoomNumber(currentRoom);
      if(index == RITUAL && inventory[GEM] == 4) {
         System.out.println("So you gathered four gems? I guess I'll let you live. This time...");
         gameOver = true;
      } 
      else {
         System.out.println("You do not have enough GEMS.");
      }
   }

   //Remove the item from our inventory
   public static void put(String item){
   }
}