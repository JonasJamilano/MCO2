/**
 * @author Jamilano and Silva [S12A] CCPROG3
 * MP Pair Group 8
 */

/**
 * The AddOn class represents an additional item that can be added to a purchase.
 * It contains information about the add-ons name and price.
 */
public class AddOn {

    private String name;
    private double price;

    /**
     * Constructs a new AddOn object with the specified name and price.
     *
     * @param name  The name of the add-on.
     * @param price The price of the add-on.
     */
    public AddOn(String name, double price) {
        this.name = name;
        this.price = price;
    }

    /**
     * Returns the name of the add-on.
     *
     * @return The name of the add-on.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the price of the add-on.
     *
     * @return The price of the add-on.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns a string representation of the add-on, including its name and price.
     *
     * @return A string representation of the add-on.
     */
    @Override
    public String toString() {
        return name + " " + price + "PhP";
    }
}