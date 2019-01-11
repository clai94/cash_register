/**
 * A item priced by quantity.
 *
 * @author Calvin Lai
 * @version 1.05
 */
public class QuantifiedItem extends Item {

    int quantity;
    private double pricePerItem;
    private boolean bogo;

    /**
     * The constructor for the class.
     * @param name the name of the item
     * @param quantity the quantity bought
     * @param pricePerItem the price per item
     */
    QuantifiedItem(String name, int quantity, double pricePerItem, boolean bogo) {
        super(name);
        this.quantity = quantity;
        this.pricePerItem = pricePerItem;
        this.bogo = bogo;
    }

    /**
     * Get the item's total price.
     * @return the item's total price.
     */
    public double getTotal() {
        int temp = quantity;
        if (bogo) {
            double units = 0;
            while (temp % 3 != 0) {
                if (temp >= 3) {
                    units += 2;
                    temp -= 3;
                } else {
                    units += temp;
                    break;
                }
            }
            if (temp % 3 == 0) {
                units = temp - (temp / 3);
            }
            return pricePerItem * units;
        }
        return pricePerItem * quantity;
    }

    /**
     * Getter for the quantity.
     * @return the item's quantity
     */
    public int getQuantity() {
        return quantity;
    }
}
