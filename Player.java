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
    private int maxWeight = 0; 
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
     * Player can take an item and adds item to inventory
     * 
     */ 
    /*
    public void take(String itemName){
        Item itemTake = currentRoom.getItem(itemName); 
        if((maxWeight + itemTake.getWeight())< 3500){  
        currentRoom.removeItem(itemTake); 
        inventory.add(itemTake);
        maxWeight += itemTake.getWeight(); 
        System.out.println("Item taken:" + itemTake.getName()); 
        
    }else if ((maxWeight + itemTake.getWeight()) >= 3500){
        System.out.println("You can not carry this item " +
        "because you're too weak and can only handle too much.");
    }else{
        System.out.println("There is no item.");
    }}
  
 
     //Player can drop item and removes item from inventory.
    
    public void drop(String itemName){
        Item itemDrop = getItem(itemName); 
        if(inventory.isEmpty()){
        System.out.println("There are no items in your inventory");
    } else {
        System.out.println("You dropped the item:" + itemDrop.getName());
        inventory.remove(itemDrop);
        maxWeight -= itemDrop.getWeight(); 
    }
}
*/ 
    /**
     * Adds item into inventory and adds weight of object into maxWeight. 
     */
    public void addItem(Item addedItem)
    {   if(maxWeight < 3500){
        maxWeight += addedItem.getWeight(); 
        inventory.add(addedItem);
        } else if (maxWeight >= 3500)
        {System.out.println("You can not carry this item " +
        "because you're too weak and can only handle too much.");
    }else {System.out.println("There is no item.");}
}
 
    /**
     * Drops item from inventory and removes weight of object from maxWeight. 
     */
    public void removeItem(Item removedItem)
    {   
        maxWeight -= removedItem.getWeight(); 
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
        System.out.println("Total Weight: "+  maxWeight); 
    }
    }
     


    



