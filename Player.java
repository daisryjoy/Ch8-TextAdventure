import java.util.HashMap;
import java.util.*;
/**
 * Player class will manage the player's name, location and inventory.
 *
 * @author Daisry Joy Ladignon
 * @version October 29, 2018
 */
public class Player
{
    // instance variables - replace the example below with your own
    public String name; 
    private int health; 
    private ArrayList<Item> inventory;  
    private Room currentRoom;
    //private Item item; 
    private int maxWeight = 3500; 
    private int weight; 
    /**
     * Constructor for objects of class Player
     * @param String name for player. 
     */
    public Player(String player, Room currentRoom)
    {
        name = player;
        this.health = 10;
        inventory = new ArrayList<Item>(); 
        this.currentRoom = currentRoom;
    }
    
/**
 * Return player name. 
 */
public String getName(){
    return name; 
}

/**
 * Return player health. 
 */
public void getHealth(){
    System.out.println("Health: " + health);
    //return health;
}

/**
     * Method to set the player's current room
     * 
     * @param Room currentRoom of player
     */
    public void setRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }
    
    /**
     * Method to return the player's current room
     * 
     * @return Room currentRoom of player
     */
    public Room getRoom() {
        return currentRoom;
    }
    
    /**
     * Adds item to inventory and adds weight of object to weight. 
     */
    public void addItem(Item addedItem)
    {   if(weight < maxWeight){
        
        if(addedItem.getWeight() == 500){ // compare weight to weight of knife
        System.out.println("Bloody hell! You stabbed yourself.");
        weight += addedItem.getWeight(); 
        inventory.add(addedItem); 
        health -= 3; 
        } else if(addedItem.getWeight() == 300) // weight of candlestick
        {
        System.out.println("Bloody hell! You burned yourself.");
        weight += addedItem.getWeight(); 
        inventory.add(addedItem); 
        health -= 1; 
        }
        else{
        weight += addedItem.getWeight(); 
        inventory.add(addedItem);
        }
        }else if (weight >= 3500)
        {
        System.out.println("You can not carry this item " +
        "because you're too weak and can only handle too much.");
    
        }else {System.out.println("There is no item.");}
}
 
    /**
     * Drops item from inventory and removes weight of object from weight. 
     */
    public void removeItem(Item removedItem)
    {   
        weight -= removedItem.getWeight(); 
        inventory.remove(removedItem);
    }
    
    
/**
 * Returns an Item object in Player inventory
 */
    public Item getItem(String itemName){
        Item returnItem = null;
        for(Item item: inventory){
            if(itemName.equals(item.getName())){
                returnItem = item;
        }   
    }
    return returnItem;
}
    
    /**
     * Prints items in inventory 
     */
    public void printItems(){
        System.out.println("Your inventory contains the following items: \n"); 
        for(Item item: inventory){
            System.out.println(item.getName());
        }
        System.out.println("Total Weight: "+  weight); 
    }
    /**
     * Eat the cookie 
     */
    public void eatCookie()
    {   if (weight < 2000)
        {
            System.out.println("You have eaten a Magic Cookie! " +
            "By eating this cookie, " + "you added 1500 to your max inventory weight! "+
        "And have gained + 2 to your health.");
            maxWeight += 1500;
            health += 2; 
        } else {
            System.out.println("You can not eat the Magic Cookie. Lose some weight first.");
        }
        
    }
}
     


    



