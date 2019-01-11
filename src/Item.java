/**
 * Basic item class.
 *
 * @author Calvin Lai
 * @version 1.04
 */
public class Item {

    String name;
    double total;
    int amount = 0;
    boolean isWeighed;

    /**
     *
     * @param name the item's name
     */
    Item(String name) {
        this.name = name;
    }

    /**
     * Getter for the name.
     * @return the item name
     */
    String getName() {
        return name;
    }

    /**
     * Getter for the total.
     * @return the item's total
     */
    public double getTotal() {
        return total;
    }

    /**
     * Getter for the item amount.
     * @return the amount
     */
    public int getQuantity() {
        return amount;
    }

    /**
     * Getter for isWeighed property.
     * @return true if the item is priced by weight else false
     */
    public boolean getIsWeighed() {
        return isWeighed;
    }
}
