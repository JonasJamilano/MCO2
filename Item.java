/**
 * @author Jamilano and Silva [S12A] CCPROG3
 * MP Pair Group 8
 */

/**
 * The Item class represents an item that can be stored and sold in a vending machine.
 * It contains information about the item's name, price, quantity, and calories.
 */
public class Item {

    private String name;
    private double price;
    private int quantity;
    private int calories;

    /**
     * Constructs a new Item object with the specified name, price, quantity, and calories.
     *
     * @param name     The name of the item.
     * @param price    The price of the item.
     * @param quantity The quantity of the item.
     * @param calories The number of calories in the item.
     */
    public Item(String name, double price, int quantity, int calories) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.calories = calories;
    }

    /**
     * Returns the name of the item.
     *
     * @return The name of the item.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the price of the item.
     *
     * @return The price of the item.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the item.
     *
     * @param price The new price of the item.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Returns the quantity of the item.
     *
     * @return The quantity of the item.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the item.
     *
     * @param quantity The new quantity of the item.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Returns the number of calories in the item.
     *
     * @return The number of calories in the item.
     */
    public int getCalories() {
        return calories;
    }

    /**
     * Decreases the quantity of the item by 1.
     * If the quantity is already 0, it will remain unchanged.
     */
    public void decreaseQuantity() {
        if (quantity > 0) {
            quantity--;
        }
    }
}