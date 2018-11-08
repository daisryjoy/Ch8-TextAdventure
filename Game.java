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
        outside = new Room("outside the front of the house");
        entrance = new Room("in the entrance of the house");
        sideRoom = new Room("in the side room");
        kitchen = new Room("in the kitchen.");
        pantry = new Room("in the pantry");
        closet = new Room("in the closet");
        bathroom = new Room("in the bathroom");
        bedroom = new Room("in the bedroom");
        storageArea = new Room("in the storage area...\n" 
        +"Are you a slob? It's hecka messy in here");
        
        office = new Room("in the office...\n" 
        + "Are you even productive?");
        
        backdoor = new Room("by the backdoor...\n" 
        + "Are you going outside?");
        
        gate = new Room("outside in front of the back gate...\n" 
        + "You're locked in. Don't die.");
        
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
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
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
}
