
/**
 * Item class to manage items found in rooms.
 *
 * @Daisry Joy Ladignon
 * @2018.10.27
 */
public class Item
{
   private String name, description;
   private int weight;


   /**
    * Constructor for objects of Item class. 
    */
   public Item(String name, String description, int weight)
   {
      this.name = name;
      this.description = description;
      this.weight = weight;
      
   }

   /**
    * Get item's name.
    */
   public String getName()
   {
      return name;
   }

   /**
    * Gives a description of the items in the room.
    * @return A description of the item with weight. 
    */
   public String getDescription()
   {
      String itemDescrip = "This room contains: " ; 
      itemDescrip = itemDescrip + this.description 
      + " \n Item's weight: " + this.weight;
      return itemDescrip;
   }

   /**
    * Get item's weight.
    */
   public int getWeight()
   {
      return weight;
   }
   
  
}