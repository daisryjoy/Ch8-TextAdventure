import java.util.Random;
import java.util.Stack;
import java.util.Scanner;
/**
 *  This class is the main class of the "Le Désespoir!" application. 
 *  "Le Désespoir!" is a very simple, text based adventure game.  Users 
 *  can walk around and participate in the game.
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Daisry Joy Ladignon 
 * @version 2018.10.27
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    Random rand = new Random();
    private Room prevRoom;
    private Stack roomStack = new Stack();
    private Player player; 
    
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, entrance, sideRoom, kitchen, pantry, closet, 
        bathroom, bedroom, storageArea, backdoor, gate, office;
      
        // create the rooms
        outside = new Room("outside the front of the house. There is a shovel on the ground.");
        entrance = new Room("in the entrance of the house. " +
        "There's a candlestick resting on the table in front of you.");
        sideRoom = new Room("in the side room. There is a rack.");
        kitchen = new Room("in the kitchen. There's a knife. ");
        pantry = new Room("in the pantry. There's a box of pasta. There is also a magic cookie." +
        "If you eat the cookie you will be able to carry more weight.");
        closet = new Room("in the closet. There's a hanger. ");
        bathroom = new Room("in the bathroom. There is a toothbrush. ");
        bedroom = new Room("in the bedroom. There is a chair. ");
        storageArea = new Room("in the storage area...\n" 
        +"Are you a slob? It's hecka messy in here. "+
        "There's an item in front of you, but it's a mystery item.");
        
        office = new Room("in the office...\n" 
        + "Are you even productive? There is a pen.");
        
        backdoor = new Room("by the backdoor...\n" 
        + "Are you going outside? There is a flashlight.");
        
        gate = new Room("outside in front of the back gate...\n" 
        + "You're locked in. Don't die. There is a rope on the gate.");
        
        
        //initialise items 
        Item shovel = new Item("shovel", "It's a muddy shovel", 700); //item outside
        Item candlestick = new Item("candlestick", "It's a candlestick", 300); //item in entrance
        Item rack = new Item("rack", "It's a rack", 2000); //item in sideRoom
        Item knife = new Item("knife", "It's a knife", 500); //item in kitchen
        Item pasta = new Item("pasta", "It's some uncook elbow pasta", 100); //item in pantry 
        Item hanger = new Item("hanger", "It's a coat hanger", 200); //item in closet
        Item toothbrush = new Item("toothbrush", "It's a toothbrush", 200); //item in bathroom
        Item chair = new Item("chair", "It's a chair", 700); //item in bedroom
        Item mysteryThing = new Item("unknown item", "What is it?",+
        rand.nextInt((5000-1) +1 + 1)); //item in storageArea
        Item pen = new Item("pen", "It's a pen", 200); //item in office
        Item flashlight = new Item("flashlight", "It's a flashlight", 600); //item by backdoor
        Item rope = new Item("rope", "It's a rope", 500); //item by gate
        Item magicCookie = new Item("cookie", "It's a magic cookie. By eating this cookie, "
        + "you will add 1500 to your max invetory weight! ", 0); // item hidden in pantry
        
        
        //add items to the rooms
        outside.addItem(shovel); 
        entrance.addItem(candlestick); 
        sideRoom.addItem(rack); 
        kitchen.addItem(knife); 
        pantry.addItem(pasta); 
        closet.addItem(hanger); 
        bathroom.addItem(toothbrush); 
        bedroom.addItem(chair); 
        storageArea.addItem(mysteryThing); 
        office.addItem(pen); 
        backdoor.addItem(flashlight); 
        gate.addItem(rope); 
        
        
        // initialise room exits
        outside.setExit("northeast", entrance);
        outside.setExit("northwest", sideRoom);

        entrance.setExit("north", kitchen);
        entrance.setExit("west", sideRoom);
        entrance.setExit("northwest", pantry);
        entrance.setExit("south", outside);
        
        
        sideRoom.setExit("south", outside);
        sideRoom.setExit("east", entrance);
        sideRoom.setExit("north", pantry);
        sideRoom.setExit("northeast", kitchen);
        
        kitchen.setExit("northeast", closet);
        kitchen.setExit("northwest", bathroom);
        kitchen.setExit("south", entrance);
        kitchen.setExit("southwest", sideRoom);
        kitchen.setExit("west", pantry);
        
        pantry.setExit("northwest", bedroom);
        pantry.setExit("northeast", bathroom);
        pantry.setExit("south", sideRoom);
        pantry.setExit("east", kitchen);
        
        closet.setExit("north", storageArea);
        closet.setExit("west", bathroom);
        closet.setExit("south", kitchen);
 
        bathroom.setExit("east", closet);
        bathroom.setExit("southeast", kitchen);
        bathroom.setExit("southwest", pantry);
        bathroom.setExit("west", bedroom);
        bathroom.setExit("northeast", storageArea);
        bathroom.setExit("northwest", office);
        
        bedroom.setExit("northwest", backdoor);
        bedroom.setExit("northeast", office);
        bedroom.setExit("east", bathroom);
        bedroom.setExit("south", pantry);
  
        storageArea.setExit("southeast", closet);
        storageArea.setExit("southwest", bathroom);
        storageArea.setExit("west", office);
        storageArea.setExit("north", gate);

        office.setExit("east", storageArea);
        office.setExit("southeast", bathroom);
        office.setExit("southwest", bedroom);
        office.setExit("west", backdoor);
        office.setExit("north", gate);
        
        backdoor.setExit("east", office);
        backdoor.setExit("south", bedroom);
        backdoor.setExit("north", gate);
        
        gate.setExit("south", office);
        backdoor.setExit("southeast", storageArea);
        backdoor.setExit("southWest", backdoor);
        
        
        
        currentRoom = outside;  // start game outside
        prevRoom = null; 
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        String name; // player name 
        System.out.println("Enter player's name: "); 
        Scanner sc = new Scanner(System.in);
        name = sc.nextLine(); 
        player = new Player(name, currentRoom); 
        
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to Le Désespoir!");
        System.out.println("Le Désespoir! is an adventure game " +
        "in which you test your abilities during a panic of despair");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
                
            case LOOK: 
                look(); 
                break;
                
            case EAT: 
                System.out.println("You gained energy! JUST KIDDING. You're always tired.");
                break;
                
            case BACK:
                back(); 
                break;
                
            case TAKE:
                take(command);
                break; 
                
            case DROP: 
                drop(command);
                break; 
                
            case ITEMS:
                player.printItems();
                break;
                
            case MAGICCOOKIE:
                player.eatCookie(); 
                    break; 
                    
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /**
     * Describes current room status. 
     */
    private void look(){
        System.out.println(currentRoom.getLongDescription());
    }
    
    
    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     * @param command The command to be processed.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            prevRoom = currentRoom;
            roomStack.push (prevRoom); 
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    /**
     * Returns player to the previous room. 
     */
    private void back(){
        if(roomStack.empty()){
        currentRoom = prevRoom; 
        System.out.println(currentRoom.getLongDescription());
    } else {
         currentRoom = (Room) roomStack.pop();
         System.out.println(currentRoom.getLongDescription());
    }
    }
    
    /**
     * Take command
     */
    private void take(Command command){
        if(!command.hasSecondWord()){
            //if there is no second word, we don't know what to take
            System.out.println("What do you want to take?");
            return;
        }
        String itemName = command.getSecondWord(); 
        System.out.println("You have added " +  itemName + " to your inventory.");
        Item newItem; 
        newItem = currentRoom.getItem(itemName); 
        currentRoom.removeItem(newItem);
        player.addItem(newItem); 
    }
    
    /**
     * Drop command
     */
     private void drop(Command command){
        if(!command.hasSecondWord()){
            //if there is no second word, we don't know what to take
            System.out.println("What do you want to drop?");
            return;
        }
        String itemName = command.getSecondWord(); 
        System.out.println("You have dropped " +  itemName + " from your inventory.");
        Item newItem; 
        newItem = currentRoom.getItem(itemName); 
        currentRoom.addItem(newItem);
        player.addItem(newItem);
    }
}
