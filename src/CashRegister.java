import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/*
    Below is a description of a component we would like you to design and implement in the OO language of your choosing.
    How fast you do this assignment is not heavily weighted but we would like you to submit your results within 48 hours at the latest.

    If you cannot complete in the time, just give us a portion you think you can complete.
    The specs are vague so you can list any assumptions that you had when you send the test back in.
    In this sample you will implement a grocery store cash register.
    Given a number of items you will be required to calculate the total bill. Items are priced a few of different ways:
    A given price for each item, eg. Boxes of Cheerios are $6.99 each
    A given price by weight, eg. Apples are $2.49 per pound
    Items can be go on sale where you might receive bulk discounts, eg.
    Buy two get one free sales are also available

    Coupons are available to get money off the bill when the total amount is above a threshold, eg.
    $5 off when you spend $100 or more.

    The goal of this exercise is to evaluate your coding style and OO design skills. Please send a zip file of all the source code and project files.
 */

/**
 * A cash register program.
 *
 * @author Calvin Lai
 * @version 1.21
 */
public class CashRegister {

    private static final double FRUIT_LOOPS_PRICE = 3.99; // price of fruit loops
    private static final double MILK_PRICE = 4.79; // price of milk
    private static final double CHIP_PRICE = 1.79; // price of chips
    private static final double APPLE_PRICE = 0.69; // price for apples
    private static final double ORANGE_PRICE = 1.96; // price for oranges
    private static final double BANANA_PRICE = 1.29; // price for bananas
    private static final double GST = 0.05; // 5% GST tax
    private static final double PST = 0.07; // 7% PST tax

    private static Scanner scan = new Scanner(System.in); // scanner for user input

    /**
     * Main driver program.
     * @param args command line arguments
     */
    public static void main(String[] args) {

        ArrayList<Item> groceries = new ArrayList<>();
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        boolean complete = false;
        Item temp;

        String startMsg = "************ FineDirect Dummy Register ************";
        String menu = "\nChoose from the following options: \n0: Add a quantified item. \n1: Weigh an item."
                + "\n2: Remove an Item \n3: See current list\n4: Complete Sale / Total\n5: See Limited Offers";

        System.out.println(startMsg);

        while (!complete) {
            System.out.println(menu);
            int input = (int) getOption();
            switch(input) {
                case 0:
                    // quantified items
                    String quantityMsg = "Select from the following\n1: Fruit Loops, price per item: "
                            + formatter.format(FRUIT_LOOPS_PRICE) + "\n2: 2L 3.25% Milk Carton, price per item: "
                            + formatter.format(MILK_PRICE) + "\n3: Potato Chips, price per item: "
                            + formatter.format(CHIP_PRICE);
                    String howMany = "How many would you like to add?\nEnter a quantity: ";
                    System.out.println(quantityMsg);
                    int choice = (int) getOption();
                    int quantity;

                    switch(choice) {
                        case 1:
                            System.out.print(howMany);
                            quantity = (int) getOption();
                            temp = new QuantifiedItem("Fruit Loops", quantity, FRUIT_LOOPS_PRICE, true);
                            addItem(groceries, temp);
                            break;
                        case 2:
                            System.out.print(howMany);
                            quantity = (int) getOption();
                            temp = new QuantifiedItem("2L Milk Carton", quantity, MILK_PRICE, false);
                            addItem(groceries, temp);
                            break;
                        case 3:
                            System.out.print(howMany);
                            quantity = (int) getOption();
                            temp = new QuantifiedItem("Potato Chips", quantity, CHIP_PRICE, true);
                            addItem(groceries, temp);
                            break;
                        default:
                            String errMsg = "Input not recognized, returning to menu.";
                            System.out.println(errMsg);
                            break;
                    }
                    break;
                case 1:
                    // weighted items
                    String weighedMsg = "Select from the following\n1: Apples, price per lb: " + APPLE_PRICE
                            + "\n2: Oranges, price per lb: " + ORANGE_PRICE + "\n3: Bananas, price per lb: "
                            + BANANA_PRICE;
                    String howHeavy = "Enter a weight in lbs: ";
                    double weight;

                    System.out.println(weighedMsg);
                    int weightChoice = (int) getOption();

                    switch(weightChoice) {
                        case 1:
                            System.out.print(howHeavy);
                            weight = getOption();
                            temp = new WeighedItem("Apples", weight, APPLE_PRICE);
                            addItem(groceries, temp);
                            break;
                        case 2:
                            System.out.print(howHeavy);
                            weight = getOption();
                            temp = new WeighedItem("Oranges", weight, ORANGE_PRICE);
                            addItem(groceries, temp);
                            break;
                        case 3:
                            System.out.print(howHeavy);
                            weight = getOption();
                            temp = new WeighedItem("Bananas", weight, BANANA_PRICE);
                            addItem(groceries, temp);
                            break;
                        default:
                            String errMsg = "Input not recognized, returning to menu.";
                            System.out.println(errMsg);
                            break;
                    }
                    break;
                case 2:
                    // remove an item
                    String errorMsg = "Nothing to remove!";
                    if (groceries.isEmpty()) {
                        System.out.println(errorMsg);
                    } else {
                        removeItem(groceries);
                    }
                    break;
                case 3:
                    // display all items
                    displayList(groceries);
                    break;
                case 4:
                    // complete transaction
                    scan.nextLine();
                    complete = true;
                    break;
                case 5:
                    // limited offer
                    String limitedOffer = "Get $5 off your purchase today when you spend $100 or more! See a store " +
                            "store associate for more details.\n" +
                            "Save on Boxes of Fruit Loops cereal and Generic Brand Potato Chips!\nBuy 3 " +
                            "for the price of 2 on these select items. Limited time offer, restrictions apply.";
                    System.out.println(limitedOffer);
                    break;
                default:
                    String errMsg = "Input not recognized, returning to menu.\n";
                    System.out.println(errMsg);
                    break;
            }
        }
        makeSale(groceries);
    }

    /**
     * Checks the grocery list for the existing item.
     * @param list the grocery list
     * @param item the item to add
     */
    private static void addItem(ArrayList<Item> list, Item item) {
        String err = "Item could not be added. (Invalid quantity or weight?)";
        if (item.getIsWeighed()) {
            if (((WeighedItem) item).weight <= 0) {
                System.out.println(err);
                return;
            }
        } else {
            if (((QuantifiedItem) item).quantity <= 0) {
                System.out.println(err);
                return;
            }
        }
        for (Item i: list) {
            if (i.name.equals(item.name)) {
                if (i.getIsWeighed()) {
                    ((WeighedItem) i).weight += ((WeighedItem) item).weight;
                } else {
                    ((QuantifiedItem)i).quantity += ((QuantifiedItem) item).quantity;
                }
                return;
            }
        }
        list.add(item);
    }

    /**
     * Completes the transaction. Summarizes the sale by printing subtotal, total, taxes, and total quantity sold.
     * @param list the finalized grocery list
     */
    private static void makeSale(ArrayList<Item> list) {
        double subTotal = 0;
        int itemCount = 0;
        char[] line = new char[51];
        Arrays.fill(line, '-');
        String startMsg = "************ FineDirect Dummy Register ************";
        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        System.out.println(startMsg);

        for (Item i : list) {
            subTotal += i.getTotal();
            itemCount += i.getQuantity();
        }

        displayList(list);

        double taxGST = subTotal * GST;
        double taxPST = subTotal * PST;
        double total = subTotal + taxGST + taxPST;

        System.out.println(line);
        System.out.printf("%-10s%41s\n%-10s%15s%26s\n%-10s%15s%26s\n", "SubTotal", formatter.format(subTotal),
                "PST:", "7.0%", formatter.format(taxPST), "GST:", "5.0%",formatter.format(taxGST));
        System.out.println(new String(line));
        if ((int) total >= 100) {
            System.out.printf("%-10s%41s\n%-10s%41s\n%-20s%31s\n%-20s%30d",
                    "Total:", formatter.format(total), "Discount:", "-$5.00", "After Discount:",
                    formatter.format(total - 5), "Number of items sold:", itemCount);
        } else {
            System.out.printf("%-10s%41s\n%-20s%30d", "Total:", formatter.format(total), "Number of items sold:",
                    itemCount);
        }
    }
    //"Discount applied! You saved $5 off your purchase."

    /**
     * Prints all items currently in the list.
     * @param list the grocery list to check
     */
    private static void displayList(ArrayList<Item> list) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        char[] line = new char[51];
        Arrays.fill(line, '-');
        int size = list.size();

        System.out.println("******************** Item List ********************\n");
        System.out.printf("%-4s%-11s%20s%16s\n","No.", "Item Name", "Quantity/Weight", "Price");
        System.out.println(line);

        for (int i = 0; i < size; i++) {
            if (list.get(i).getIsWeighed()) {
                System.out.printf("%-4d%-11s%10.2flbs%23s\n", i, list.get(i).getName(),
                        (((WeighedItem) list.get(i)).weight), formatter.format(list.get(i).getTotal()));
            } else {
                System.out.printf("%-4d%-14s%10s%23s\n", i, list.get(i).getName(),
                        (((QuantifiedItem) list.get(i)).quantity), formatter.format(list.get(i).getTotal()));
            }
        }
        System.out.println(line);
    }

    /**
     * Removes an item specified by name from the list.
     * @param list the list to remove from
     */
    private static void removeItem(ArrayList<Item> list) {
        String instruction = "Select the item you wish to remove, enter -1 to cancel.";
        String errMsg = "Invalid item, returning to main menu.";
        String cancelMsg = "Canceling, returning to main menu.";

        displayList(list);

        System.out.println(instruction);
        int choice = (int) getOption();
        if (choice == -1) {
            System.out.println(cancelMsg);
        } else if (choice >= 0 && choice < list.size()) {
            System.out.println(list.get(choice).name + " successfully removed, returning to main menu.");
            list.remove(choice);
        } else {
            System.out.println(errMsg);
        }
    }

    /**
     * Gets input from the user. Ensures the input is proper.
     * @return the user's input as a double
     */
    private static double getOption() {
        boolean wentToCatch = false;
        double input = 0;
        String errMsg = "Enter a valid number.";
        do {
            if (scan.hasNextDouble()) {
                input = scan.nextDouble();
                wentToCatch = true;
            } else {
                scan.nextLine();
                System.out.println(errMsg);
            }
        } while(!wentToCatch);
        return input;
    }
}