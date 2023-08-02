/**
 * @author Jamilano and Silva [S12A] CCPROG3
 * MP Pair Group 8
 */

import java.util.ArrayList;
import java.util.List;

/**
 * The VendingMachine class represents a generic vending machine that stores and manages items,
 * maintains the balance, keeps track of denominations, and records purchase history.
 */
public class VendingMachine {

    private List<Item> items;
    private double balance;
    public int numTens;
    public int numTwenties;
    public int numFifties;
    public int numHundreds;
    public List<String> purchaseHistory;

    /**
     * Constructs a new VendingMachine object with the specified capacity.
     *
     * @param capacity the capacity of the vending machine.
     */
    public VendingMachine(int capacity) {
        this.items = new ArrayList<>();
        this.balance = 0.00;
        this.numTens = 0;
        this.numTwenties = 0;
        this.numFifties = 0;
        this.numHundreds = 0;
        this.purchaseHistory = new ArrayList<>();
    }

    // Helper method to add items to the purchase history
    /**
     * Adds an item description to the purchase history.
     *
     * @param item The item description to be added to the purchase history.
     */
    protected void addToPurchaseHistory(String item) {
        purchaseHistory.add(item);
    }

    /**
     * Displays the purchase history of the vending machine.
     * If no items have been purchased yet, it prints a message indicating that.
     */
    public void displayPurchaseHistory() {
        System.out.println("\nPurchase History:");
        if (purchaseHistory.isEmpty()) {
            System.out.println("No items purchased yet.");
        } else {
            for (String item : purchaseHistory) {
                System.out.println(item);
            }
        }
    }

    /**
     * Adds a new item to the vending machine with the specified name, price, quantity, and calories.
     *
     * @param name     The name of the item to be added.
     * @param price    The price of the item in Philippine Pesos (PhP).
     * @param quantity The initial quantity of the item available in the vending machine.
     * @param calories The number of calories in the item.
     */
    public void addItem(String name, double price, int quantity, int calories) {
        Item item = new Item(name, price, quantity, calories);
        items.add(item);
    }

    /**
     * Returns the purchase history of the vending machine.
     *
     * @return the list of purchase history entries.
     */
    public List<String> getPurchaseHistory() {
        return purchaseHistory;
    }
    /**
     * Records a purchase in the purchase history.
     *
     * @param purchaseDetails the details of the purchase to be recorded.
     */
    public void recordPurchase(String purchaseDetails) {
        purchaseHistory.add(purchaseDetails);
    }

    /**
     * Retrieves the current balance of the vending machine.
     *
     * @return the current balance in Philippine Pesos (PhP).
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Retrieves a list of all items available in the vending machine.
     *
     * @return a list containing all the items available in the vending machine.
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * Returns a description of the vending machine.
     *
     * @return the description of the vending machine.
     */
    public void displayAvailableItemsWithSlots() {
        System.out.println("Available Items:");
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            System.out.printf("Slot [%d] %s - Price: PhP%.2f, Quantity: %d, Calories: %d%n",
                    i + 1, item.getName(), item.getPrice(), item.getQuantity(), item.getCalories());
        }
        System.out.println();
    }

    /**
     * Returns the description of the vending machine.
     *
     * @return The description of the vending machine.
     */
    public String getDescription() {
        return "Generic Vending Machine";
    }

    /**
     * Retrieves the item located at the specified slot number in the vending machine.
     *
     * @param slotNumber The slot number of the item to retrieve.
     * @return The item located at the specified slot number, or null if the slot number is out of range.
     */
    public Item getItemBySlotNumber(int slotNumber) {
        if (slotNumber < 1 || slotNumber > items.size()) {
            return null; // Slot number is out of range
        }

        return items.get(slotNumber - 1);
    }

    /**
     * Returns a description of the maintenance operations available for the vending machine.
     *
     * @return the description of maintenance operations.
     */
    public String getMaintenanceDescription() {
        return "[1] Check Inventory\n" +
                "[2] Add Inventory\n" +
                "[3] Load Money\n" +
                "[4] Collect Money\n" +
                "[5] Check Balance\n" +
                "[6] Purchase History\n" +
                "[7] Update Price\n" +
                "[8] Exit";
    }
    /**
     * Dispenses the specified item if available and decreases its quantity.
     *
     * @param item the item to dispense.
     */
    public void dispenseItem(Item item) {
        if (items.contains(item)) {
            if (item.getQuantity() > 0) {
                item.decreaseQuantity();
            } else {
                System.out.println("Item is out of stock: " + item.getName());
            }
        } else {
            System.out.println("Item not found in the vending machine.");
        }
    }

    /**
     * Displays the starting inventory of items in the vending machine.
     */
    public void displayStartingInventory() {
        System.out.println("Starting Inventory:");
        for (Item item : items) {
            System.out.println(item.getName() + " - Price: PhP" + item.getPrice() + ", Quantity: " + item.getQuantity());
        }
        System.out.println();
    }

    /**
     * Adds inventory to the vending machine for a specific item.
     *
     * @param itemName the name of the item to add inventory for.
     * @param quantity the quantity of items to add to the inventory.
     */
    public void addInventory(String itemName, int quantity) {
        for (Item item : items) {
            if (item.getName().equals(itemName)) {
                item.setQuantity(item.getQuantity() + quantity);
                System.out.println("Inventory added: " + itemName + ", Quantity: " + quantity);
                return;
            }
        }
        System.out.println("Item not found: " + itemName);
    }

    /**
     * Loads money into the vending machine and updates the balance and denominations.
     *
     * @param amount the amount of money to load.
     */
    public void loadMoney(double amount) {
        if (amount == 10) {
            numTens++;
        } else if (amount == 20) {
            numTwenties++;
        } else if (amount == 50) {
            numFifties++;
        } else if (amount == 100) {
            numHundreds++;
        }

        balance += amount;
        System.out.println("Money loaded: PhP" + amount);
    }

    /**
     * Displays the current balance and denominations of the vending machine.
     */
    public void checkBalance() {
        System.out.println("Current Balance: PhP" + this.balance);
        System.out.printf("Number of 10s: %d%n", numTens);
        System.out.printf("Number of 20s: %d%n", numTwenties);
        System.out.printf("Number of 50s: %d%n", numFifties);
        System.out.printf("Number of 100s: %d%n", numHundreds);
        double total = (numTens * 10) + (numTwenties * 20) + (numFifties * 50) + (numHundreds * 100);
        System.out.printf("Total Denomination: PhP%.2f%n", total);
    }
    /**
     * Deducts the specified amount from the vending machine balance, using the available denominations.
     *
     * @param amount the amount to deduct from the balance.
     */
    public void deductBalance(double amount) {
        balance -= amount;
        while (amount > 0) {
            if (numHundreds > 0 && amount >= 100) {
                amount -= 100;
                numHundreds--;
            } else if (numFifties > 0 && amount >= 50) {
                amount -= 50;
                numFifties--;
            } else if (numTwenties > 0 && amount >= 20) {
                amount -= 20;
                numTwenties--;
            } else if (numTens > 0 && amount >= 10) {
                amount -= 10;
                numTens--;
            } else {
                // If there are not enough denominations to deduct, break out of the loop.
                break;
            }
        }
        balanceToDenominations();
    }

    /**
     * Adds a purchase entry to the purchase history.
     *
     * @param name  the name of the purchased item.
     * @param price the price of the purchased item.
     */
    public void addToPurchaseHistory(String name, double price) {
        purchaseHistory.add(name + " - Price: PhP " + price);
    }

    /**
     * Decreases the quantity of the specified item and adds the purchase to the purchase history.
     *
     * @param slotNumber The slot number of the item to purchase.
     * @param amount     The amount of money entered by the user.
     * @return The name of the purchased item if successful, null otherwise.
     */
    public String makePurchase(int slotNumber,double amount) {
        // Adjust the slotNumber to be 0-based index
        int adjustedSlotNumber = slotNumber - 1;

        if (adjustedSlotNumber < 0 || adjustedSlotNumber >= items.size()) {
            // Invalid slot number
            return null;
        }

        Item item = items.get(adjustedSlotNumber);

        if (item.getPrice() > amount) {
            // Insufficient funds
            return null;
        }

        if (item.getQuantity() <= 0) {
            // Item is out of stock
            return null;
        }

        // Process the purchase
        double change = amount - item.getPrice();
        if (change > 0) {
            deductBalance(change); // Update the balance to return the change
        }

        item.setQuantity(item.getQuantity() - 1); // Decrease the quantity of the purchased item
        addToPurchaseHistory(item.getName(), item.getPrice()); // Add to purchase history

        return item.getName(); // Return the name of the purchased item for displaying in the GUI
    }
    /**
     * Updates the denominations after providing change for a purchase.
     *
     * @param change the change to be provided in cents.
     */
    protected void updateDenominations(double change) {
        int remainingChange = (int) (change * 100); // Convert to cents
        while (remainingChange > 0) {
            if (numHundreds > 0 && remainingChange >= 10000) {
                remainingChange -= 10000;
                numHundreds--;
            } else if (numFifties > 0 && remainingChange >= 5000) {
                remainingChange -= 5000;
                numFifties--;
            } else if (numTwenties > 0 && remainingChange >= 2000) {
                remainingChange -= 2000;
                numTwenties--;
            } else if (numTens > 0 && remainingChange >= 1000) {
                remainingChange -= 1000;
                numTens--;
            } else {
                // If there are not enough denominations to deduct, break out of the loop.
                break;
            }
        }
        balanceToDenominations();
    }
    /**
     * Converts the balance into denominations (number of 10s, 20s, 50s, and 100s).
     */
    public void balanceToDenominations() {
        numHundreds = (int) (balance / 100);
        double remainingBalance = balance % 100;
        numFifties = (int) (remainingBalance / 50);
        remainingBalance %= 50;
        numTwenties = (int) (remainingBalance / 20);
        remainingBalance %= 20;
        numTens = (int) (remainingBalance / 10);
    }

    /**
     * Updates the price of an item in the vending machine.
     *
     * @param itemNameToUpdate the name of the item to update the price for.
     * @param newPrice         the new price for the item.
     */
    public void updatePrice(String itemNameToUpdate, double newPrice) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemNameToUpdate)) {
                item.setPrice(newPrice);
                System.out.println("Price updated successfully.");
                return;
            }
        }
        System.out.println("Item not found. Price update failed.");
    }
    /**
     * Sets the balance of the vending machine to the specified value.
     *
     * @param newBalance the new balance value to set.
     */
    protected void setBalance(double newBalance) {
        this.balance = newBalance;
    }

    /**
     * Returns the number of 10-peso bills in the vending machine.
     *
     * @return the number of 10-peso bills.
     */
    public int getNumTens() {
        return numTens;
    }

    /**
     * Returns the number of 20-peso bills in the vending machine.
     *
     * @return the number of 20-peso bills.
     */
    public int getNumTwenties() {
        return numTwenties;
    }

    /**
     * Returns the number of 50-peso bills in the vending machine.
     *
     * @return the number of 50-peso bills.
     */
    public int getNumFifties() {
        return numFifties;
    }

    /**
     * Returns the number of 100-peso bills in the vending machine.
     *
     * @return the number of 100-peso bills.
     */
    public int getNumHundreds() {
        return numHundreds;
    }
}
