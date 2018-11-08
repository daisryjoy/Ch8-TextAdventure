
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

   private boolean canTake;

   public Item(String name, String description, int weight, boolean canTake)
   {
      this.name = name;
      this.description = description;
      this.weight = weight;
      this.canTake = canTake;

   }

   /**
    * Get item's name.
    */
   public String getName()
   {
      return name;
   }

   /**
    * Get item's description.
    */
   public String getDescription()
   {
      return description;
   }

   /**
    * Get item's weight.
    */
   public int getWeight()
   {
      return weight;
   }
   
   /**
    * Allow player to take item. 
    */
   public boolean getCanTake()
   {
      return canTake;
   }
}