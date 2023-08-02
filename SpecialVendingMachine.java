/**
 * @author Jamilano and Silva [S12A] CCPROG3
 * MP Pair Group 8
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The SpecialVendingMachine class represents a specialized vending machine that dispenses various items
 * along with customizable add-ons. It inherits from the VendingMachine class and allows the user to add
 * initial items, purchase items with add-ons, and perform maintenance operations on the machine.
 */
public class SpecialVendingMachine extends VendingMachine {

    private final String[] initialItemNames = {
            "Caffe Latte",
            "Vanilla Frappuccino",
            "Caramel Frappuccino",
            "Cold Brew Coffee",
            "Green Tea Matcha Latte",
            "Iced Caramel Macchiato",
            "Hazelnut Latte",
            "Sea Salt Latte"
    };

    private final int initialItemQuantity = 10;

    private final int[] initialItemCalories = {
            100, 150, 120, 180, 80, 140, 90, 110
    };

    private final List<AddOn> addOns;
    private final Scanner scanner;

    /**
     * Creates a new SpecialVendingMachine instance with the specified capacity.
     *
     * @param capacity The maximum number of slots for storing items in the vending machine.
     */
    public SpecialVendingMachine(int capacity) {
        super(capacity);
        addOns = new ArrayList<>();
        addOns.add(new AddOn("Whole Milk", 10.00));
        addOns.add(new AddOn("Non-Fat Milk", 10.00));
        addOns.add(new AddOn("Vanilla Syrup", 20.00));
        addOns.add(new AddOn("Caramel Syrup", 20.00));
        addOns.add(new AddOn("Soy Milk", 10.00));
        addOns.add(new AddOn("Oat Milk", 10.00));
        addOns.add(new AddOn("Caramel Drizzle", 30.00));
        addOns.add(new AddOn("Mocha Drizzle", 30.00));
        scanner = new Scanner(System.in);
        addInitialItems();
    }

    /**
     * Makes a purchase from the vending machine by selecting an item and adding add-ons if desired.
     *
     * @param slotNumber The slot number of the item to purchase.
     * @param amount     The amount of money entered by the user for the purchase.
     * @return The name of the purchased item if the purchase is successful, or null if the purchase fails.
     */
    public String makePurchase(int slotNumber, double amount) {
        Item item = getItemBySlot(slotNumber);
        if (item == null) {
            return null; // Invalid slot number
        }

        double itemPrice = item.getPrice();
        if (amount < itemPrice) {
            return null; // Insufficient funds
        }

        if (item.getQuantity() <= 0) {
            return null; // Item is out of stock
        }

        // Process the purchase
        double change = amount - itemPrice;
        if (change > 0) {
            deductBalance(change); // Update the balance to return the change
        }

        List<Integer> selectedAddOns = new ArrayList<>();
        dispenseSpecialItem(slotNumber, selectedAddOns);

        return item.getName(); // Return the name of the purchased item
    }

    /**
     * Retrieves the item located at the specified slot number.
     *
     * @param slotNumber The slot number of the item to retrieve.
     * @return The Item object located at the specified slot number, or null if the slot number is out of range.
     */
    private Item getItemBySlot(int slotNumber) {
        int adjustedSlotNumber = slotNumber - 1;
        if (adjustedSlotNumber >= 0 && adjustedSlotNumber < getItems().size()) {
            return getItems().get(adjustedSlotNumber);
        }
        return null;
    }

    /**
     * Adds the initial items to the vending machine by taking input for prices from the user.
     * This method prompts the user to enter the price for each initial item and adds them to the machine.
     */
    private void addInitialItems() {
        System.out.println("\nAdd Initial Items:");
        for (int i = 0; i < initialItemNames.length; i++) {
            System.out.print("Enter price for " + initialItemNames[i] + ": ");
            double newPrice = scanner.nextDouble();
            scanner.nextLine(); // Consume newline character
            addItem(initialItemNames[i], newPrice, initialItemQuantity, initialItemCalories[i]);
        }
        System.out.println("Initial items added successfully.");
        System.out.println("---------------------");
    }

    /**
     * Displays the list of available add-ons and their prices.
     */
    public void displayAddOns() {
        System.out.println("\nHere are the Add-ons:");
        for (int i = 0; i < addOns.size(); i++) {
            AddOn addOn = addOns.get(i);
            System.out.printf("[%d] %s %.2fPhP%n", i + 1, addOn.getName(), addOn.getPrice());
        }
    }

    /**
     * Calculates the total cost of an item along with selected add-ons.
     *
     * @param itemSlot       The slot number of the item to be purchased.
     * @param selectedAddOns A list of integers representing the indices of selected add-ons.
     * @return The total cost (including the item and selected add-ons) in PhP.
     */
    public double calculateTotal(int itemSlot, List<Integer> selectedAddOns) {
        double total = getItems().get(itemSlot).getPrice();
        for (int addOnIndex : selectedAddOns) {
            if (addOnIndex >= 1 && addOnIndex <= addOns.size()) {
                total += addOns.get(addOnIndex - 1).getPrice();
            }
        }
        return total;
    }

    /**
     * Dispenses a special item with selected add-ons and handles the transaction.
     *
     * @param slotNumber     The slot number of the item to be dispensed.
     * @param selectedAddOns A list of integers representing the indices of selected add-ons.
     */
    public void dispenseSpecialItem(int slotNumber, List<Integer> selectedAddOns) {
        Item item = super.getItems().get(slotNumber);
        System.out.println(item.getName() + " " + String.format("%.2f", item.getPrice()) + "PhP");

        Scanner scanner = new Scanner(System.in);

        // Ask the user to add add-ons
        while (true) {
            System.out.println("\nDo you want to add another add-ons?");
            System.out.println("[1] Yes");
            System.out.println("[2] No");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            if (choice == 1) {
                displayAddOns();
                System.out.print("Select an add-on (enter the number): ");
                int addOnNumber = scanner.nextInt();
                selectedAddOns.add(addOnNumber);
            } else if (choice == 2) {
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }

        double total = item.getPrice() + getAddOnsTotal(selectedAddOns);
        System.out.println("Total: " + String.format("%.2f", total) + "PhP");

        // Ask the user to enter money
        System.out.print("Enter money: ");
        double moneyEntered = scanner.nextDouble();

        // Calculate the change
        double change = moneyEntered - total;
        if (change < 0) {
            System.out.println("Insufficient money. Please try again.");
        } else {
            System.out.println("Change: " + String.format("%.2f", change) + "PhP");

            // Update the inventory after dispensing the item
            int remainingQuantity = item.getQuantity() - 1;
            if (remainingQuantity < 0) {
                System.out.println("Sorry, the item is out of stock.");
            } else {
                item.setQuantity(remainingQuantity);
                System.out.println("Dispensing the item: " + item.getName());
                System.out.println("Remaining quantity: " + remainingQuantity);
            }
            String itemDispensed = "Item dispensed: " + item.getName() + ", Price: PhP" + total + " (incl. add-ons)";
            addToPurchaseHistory(itemDispensed);

            // Display the selected add-ons
            System.out.println("Selected Add-ons:");
            for (int index : selectedAddOns) {
                AddOn addOn = getAddOns().get(index - 1);
                System.out.println(addOn.getName() + " " + String.format("%.2f", addOn.getPrice()) + "PhP");
            }
            for (int index : selectedAddOns) {
                AddOn addOn = getAddOns().get(index - 1);
                String addOnDispensed = addOn.getName() + " " + String.format("%.2f", addOn.getPrice()) + "PhP";
                addToPurchaseHistory(addOnDispensed);
            }
        }
    }

    /**
     * Calculates the total cost of selected add-ons.
     *
     * @param selectedAddOns A list of integers representing the indices of selected add-ons.
     * @return The total cost of selected add-ons in PhP.
     */
    public double getAddOnsTotal(List<Integer> selectedAddOns) {
        double total = 0;
        for (int index : selectedAddOns) {
            total += addOns.get(index - 1).getPrice();
        }
        return total;
    }

    /**
     * Retrieves the list of available add-ons in the vending machine.
     *
     * @return A list of AddOn objects representing the available add-ons.
     */
    public List<AddOn> getAddOns() {
        return addOns;
    }

    /**
     * Returns a description of the SpecialVendingMachine class.
     *
     * @return The description of the SpecialVendingMachine class.
     */
    @Override
    public String getDescription() {
        return "Special Vending Machine";
    }

    /**
     * Returns a description of the maintenance operations available for the vending machine.
     *
     * @return The description of the maintenance operations available for the vending machine.
     */
    @Override
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
}
