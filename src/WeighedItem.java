/**
 * An item that is priced by weight.
 *
 * @author Calvin Lai
 * @version 1.02
 */
public class WeighedItem extends Item {

    double weight;
    double pricePerPound;
    int quantity = 1;
    boolean isWeighed = true;

    /**
     * The constructor for the class.
     * @param name the item's name
     * @param pricePerPound price per pound of the item
     * @param weight the weight of the item in pounds
     */
    WeighedItem(String name, double weight, double pricePerPound) {
        super(name);
        this.weight = weight;
        this.pricePerPound = pricePerPound;
    }

    /**
     * Get the item's total price.
     * @return the item's total price.
     */
    public double getTotal() {
        return pricePerPound * weight;
    }

    /**
     * Checks if the item is priced by weight or not.
     * @return true if priced by weight, else false
     */
    public boolean getIsWeighed() {
        return isWeighed;
    }

    /**
     * Getter for the quantity.
     * @return the item's quantity
     */
    public int getQuantity() {
        return this.quantity;
    }
}
