/**
 * @author Jamilano and Silva [S12A] CCPROG3
 * MP Pair Group 8
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * The GUI class represents the Graphical User Interface for a Vending Machine Factory.
 * It provides buttons and options to create a vending machine, test a vending machine, and exit the application.
 */
public class GUI {
    private JFrame frame;
    private JButton createBtn;
    private JButton testBtn;
    private JButton exitBtn;
    private VendingMachine vendingMachine;

    /**
     * Constructs a new GUI object and initializes the frame with buttons and listeners.
     */
    public GUI() {
        frame = new JFrame("Vending Machine Factory");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new GridLayout(3, 1, 10, 10));

        createBtn = new JButton("Create a Vending Machine");
        testBtn = new JButton("Test a Vending Machine");
        exitBtn = new JButton("Exit");

        vendingMachine = null;

        createBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showCreateVendingMachineOptions();
            }
        });

        testBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (vendingMachine == null) {
                    JOptionPane.showMessageDialog(frame, "Please create a vending machine first.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    showTestVendingMachineOptions();
                }
            }
        });

        exitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        frame.add(createBtn);
        frame.add(testBtn);
        frame.add(exitBtn);

        frame.setVisible(true);
    }

    /**
     * Displays the options to create a vending machine, including RegularVendingMachine, SpecialVendingMachine, and adding initial items.
     */
    private void showCreateVendingMachineOptions() {
        Object[] options = {"Regular Vending Machine", "Special Vending Machine", "Add an Item"};
        int choice = JOptionPane.showOptionDialog(frame, "Select the type of Vending Machine to create:",
                "Create a Vending Machine", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);

        if (choice == JOptionPane.YES_OPTION) {
            // Code to create a RegularVendingMachine
            vendingMachine = new RegularVendingMachine(100); // You can adjust the capacity as needed
            JOptionPane.showMessageDialog(frame, "Regular Vending Machine created.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else if (choice == JOptionPane.NO_OPTION) {
            // Code to create a SpecialVendingMachine
            vendingMachine = new SpecialVendingMachine(100); // You can adjust the capacity as needed
            JOptionPane.showMessageDialog(frame, "Special Vending Machine created.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else if (choice == JOptionPane.CANCEL_OPTION) {
            // Code to add initial items to the vending machine
            addInitialItemsToVendingMachine();
        }
    }

    /**
     * Displays the options to add initial items to the vending machine.
     * The user can input the item name, price, and quantity for each item to be added.
     * The user can enter 'done' as the item name to finish adding items.
     * If the input is invalid, appropriate error messages are shown.
     */
    private void addInitialItemsToVendingMachine() {
        List<Item> initialItems = new ArrayList<>();
        while (true) {
            String itemName = JOptionPane.showInputDialog(frame, "Enter the item name (or type 'done' to finish):", "Add an Item", JOptionPane.PLAIN_MESSAGE);

            if (itemName == null || itemName.trim().equalsIgnoreCase("done")) {
                // User finished entering items
                break;
            }

            String priceStr = JOptionPane.showInputDialog(frame, "Enter the price for " + itemName + ":", "Add an Item", JOptionPane.PLAIN_MESSAGE);
            String quantityStr = JOptionPane.showInputDialog(frame, "Enter the quantity for " + itemName + ":", "Add an Item", JOptionPane.PLAIN_MESSAGE);

            if (priceStr == null || quantityStr == null) {
                // User canceled
                continue;
            }

            try {
                double price = Double.parseDouble(priceStr);
                int quantity = Integer.parseInt(quantityStr);
                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(frame, "Invalid quantity. Please enter a positive number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                } else {
                    initialItems.add(new Item(itemName, price, quantity, 0)); // Assuming calories are 0 for now
                    JOptionPane.showMessageDialog(frame, "Item added successfully: " + itemName + ", Quantity: " + quantity, "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (!initialItems.isEmpty()) {
            for (Item item : initialItems) {
                vendingMachine.addItem(item.getName(), item.getPrice(), item.getQuantity(), item.getCalories());
            }
            JOptionPane.showMessageDialog(frame, "Initial items added to the vending machine.", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Displays the testing options for the vending machine.
     * The user can choose from various options like making a purchase, checking inventory, adding inventory, etc.
     * Based on the selected option, respective methods are called to perform the action.
     * The loop continues until the user selects the "Exit" option.
     */
    private void showTestVendingMachineOptions() {
        Object[] options = {
                "Make a Purchase",
                "Check Inventory",
                "Add Inventory",
                "Load Money",
                "Collect Money",
                "Check Balance",
                "Purchase History",
                "Update Price",
                "Exit"
        };

        while (true) {
            int choice = JOptionPane.showOptionDialog(frame, "Select a testing option:", "Test a Vending Machine",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);

            if (choice == JOptionPane.CLOSED_OPTION || choice == 8) {
                // Exit the testing options
                break;
            }

            switch (choice) {
                case 0:
                    // Make a Purchase
                    makePurchase();
                    break;
                case 1:
                    // Check Inventory
                    displayStartingInventory();
                    break;
                case 2:
                    // Add Inventory
                    addInventory();
                    break;
                case 3:
                    // Load Money
                    loadMoney();
                    break;
                case 4:
                    // Collect Money
                    collectMoney();
                    break;
                case 5:
                    // Check Balance
                    checkBalance();
                    break;
                case 6:
                    // Purchase History
                    vendingMachine.displayPurchaseHistory();
                    break;
                case 7:
                    // Update Price
                    updatePrice();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Allows the user to make a purchase from the vending machine.
     * The user can choose from the available items, input the slot number and amount to pay,
     * and proceed with the purchase.
     * If the vending machine is a SpecialVendingMachine, the user can customize their purchase with add-ons.
     */
    private void makePurchase() {
        List<Item> items = vendingMachine.getItems();
        if (items.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No items available in the vending machine.", "Inventory Empty", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder itemListMessage = new StringBuilder("Available Items:\n");
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            itemListMessage.append("[").append(i + 1).append("] ").append(item.getName()).append(" - Price: PhP ").append(item.getPrice()).append(", Quantity: ").append(item.getQuantity()).append("\n");
        }

        String slotNumberInput = JOptionPane.showInputDialog(frame, itemListMessage.toString() + "\nEnter the slot number (1 to " + items.size() + "):");
        String amountInput = JOptionPane.showInputDialog(frame, "Enter the amount in PhP:");

        try {
            int slotNumber = Integer.parseInt(slotNumberInput);
            double amount = Double.parseDouble(amountInput);

            if (slotNumber >= 1 && slotNumber <= items.size()) {
                Item selectedItem = items.get(slotNumber - 1);

                String purchaseResult;
                if (vendingMachine instanceof SpecialVendingMachine) {
                    // For SpecialVendingMachine, we need to handle customization
                    purchaseResult = makeSpecialVendingMachinePurchase(selectedItem, amount);
                } else {
                    // For RegularVendingMachine, proceed with the standard purchase
                    purchaseResult = vendingMachine.makePurchase(slotNumber, amount);
                }

                if (purchaseResult != null) {
                    JOptionPane.showMessageDialog(frame, "Purchase Successful: " + purchaseResult, "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Purchase Failed. Please check the amount.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid slot number. Please enter a number between 1 and " + items.size(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a valid slot number and amount.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Processes a purchase for a SpecialVendingMachine by allowing the user to customize their drink with add-ons.
     *
     * @param selectedItem The selected item to be purchased from the vending machine.
     * @param amount The amount of money paid by the user for the purchase.
     * @return A message indicating the details of the purchase or null if the purchase was canceled.
     */
    private String makeSpecialVendingMachinePurchase(Item selectedItem, double amount) {
        if (selectedItem == null) {
            return null;
        }

        String itemName = selectedItem.getName();
        double itemPrice = selectedItem.getPrice();

        int selectedOption = JOptionPane.showOptionDialog(frame, itemName + " costs PhP" + itemPrice + ", are you sure you want to purchase this?", "Confirm Purchase", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

        if (selectedOption != JOptionPane.YES_OPTION) {
            return null; // User canceled the purchase
        }

        SpecialVendingMachine specialVendingMachine = (SpecialVendingMachine) vendingMachine;
        List<AddOn> addons = specialVendingMachine.getAddOns();

        List<AddOn> selectedAddons = new ArrayList<>();
        boolean addMoreAddons = true; // Flag to control the loop for adding add-ons
        while (addMoreAddons) {
            Object[] addonOptions = new Object[addons.size() + 1];
            int i;
            for (i = 0; i < addons.size(); i++) {
                AddOn addon = addons.get(i);
                addonOptions[i] = "[" + (i + 1) + "] " + addon.getName() + " " + addon.getPrice() + "PhP";
            }
            addonOptions[i] = "[" + (i + 1) + "] Done";

            int addonChoice = JOptionPane.showOptionDialog(frame, "Would you like to customize your drink?", "Customize Drink", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, addonOptions, null);

            if (addonChoice == JOptionPane.NO_OPTION || addonChoice == JOptionPane.CLOSED_OPTION || addonChoice == i) {
                addMoreAddons = false; // User canceled or selected "None," exit the loop
            } else {
                AddOn selectedAddon = addons.get(addonChoice);
                selectedAddons.add(selectedAddon);
            }
        }

        double totalAmount = itemPrice;
        for (AddOn addon : selectedAddons) {
            totalAmount += addon.getPrice();
        }

        if (totalAmount > amount) {
            JOptionPane.showMessageDialog(frame, "Insufficient funds. Please add more money.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        // Display selected add-ons message before the final confirmation
        StringBuilder selectedAddonsMessage = new StringBuilder("Selected Add-ons:\n");
        for (AddOn addon : selectedAddons) {
            selectedAddonsMessage.append(addon.getName()).append(" ").append(addon.getPrice()).append("PhP\n");
        }

        StringBuilder resultMessage = new StringBuilder();
        resultMessage.append(itemName).append(" ").append(itemPrice).append("PhP\n\n");
        resultMessage.append(selectedAddonsMessage).append("\n");
        resultMessage.append("Total: ").append(totalAmount).append("PhP\n");
        resultMessage.append("Enter money: ").append(amount).append("PhP\n");
        resultMessage.append("Change: ").append(amount - totalAmount).append("PhP\n");
        resultMessage.append("Dispensing the item: ").append(itemName).append("\n");

        selectedItem.setQuantity(selectedItem.getQuantity() - 1);

        // Deduct totalAmount from the user's balance
        specialVendingMachine.deductBalance(totalAmount);

        // Add the purchased item (including add-ons) to the purchase history
        String itemDispensed = "Item dispensed: " + itemName + ", Price: PhP" + totalAmount + " (incl. add-ons)";
        specialVendingMachine.addToPurchaseHistory(itemDispensed);
        for (AddOn addon : selectedAddons) {
            String addOnDispensed = addon.getName() + " " + String.format("%.2f", addon.getPrice()) + "PhP";
            specialVendingMachine.addToPurchaseHistory(addOnDispensed);
        }

        // Show the final confirmation
        int finalConfirmation = JOptionPane.showOptionDialog(frame, resultMessage.toString() + "Confirm Purchase?", "Final Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

        if (finalConfirmation == JOptionPane.YES_OPTION) {
            return resultMessage.toString();
        } else {
            // If the user cancels the final confirmation, return the money
            specialVendingMachine.setBalance(specialVendingMachine.getBalance() + totalAmount);
            JOptionPane.showMessageDialog(frame, "Purchase Canceled. Returning the money: PhP " + totalAmount, "Canceled", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }
    }

    /**
     * Displays the starting inventory of items in the vending machine.
     * If there are no items available, a message indicating an empty inventory will be shown.
     */
    private void displayStartingInventory() {
        java.util.List<Item> items = vendingMachine.getItems();

        if (items.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No items available in the vending machine.", "Inventory Empty", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder inventoryMessage = new StringBuilder("Starting Inventory:\n");
        for (Item item : items) {
            inventoryMessage.append(item.getName()).append(" - Price: PhP ").append(item.getPrice()).append(", Quantity: ").append(item.getQuantity()).append("\n");
        }

        JOptionPane.showMessageDialog(frame, inventoryMessage.toString(), "Starting Inventory", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Adds inventory to the vending machine for a specified item.
     * The user is prompted to enter the item name and the quantity to add.
     * If the input is invalid or the user cancels the operation, the method will return without adding inventory.
     */
    private void addInventory() {
        String itemName = JOptionPane.showInputDialog(frame, "Enter the item name:", "Add Inventory", JOptionPane.PLAIN_MESSAGE);

        if (itemName == null || itemName.trim().isEmpty()) {
            // User canceled or provided an empty input
            return;
        }

        String quantityStr = JOptionPane.showInputDialog(frame, "Enter the quantity to add:", "Add Inventory", JOptionPane.PLAIN_MESSAGE);

        if (quantityStr == null || quantityStr.trim().isEmpty()) {
            // User canceled or provided an empty input
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityStr);
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(frame, "Invalid quantity. Please enter a positive number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            vendingMachine.addInventory(itemName, quantity);
            JOptionPane.showMessageDialog(frame, "Inventory added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid quantity. Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Loads money into the vending machine by allowing the user to select a predefined amount to load.
     * The amount will be added to the current balance of the vending machine.
     */
    private void loadMoney() {
        Object[] options = {"PhP 10", "PhP 20", "PhP 50", "PhP 100"};
        int choice = JOptionPane.showOptionDialog(frame, "Select the amount to load:", "Load Money", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);

        if (choice == JOptionPane.CLOSED_OPTION) {
            // User canceled
            return;
        }

        double amount = 0.0;
        switch (choice) {
            case 0:
                amount = 10;
                break;
            case 1:
                amount = 20;
                break;
            case 2:
                amount = 50;
                break;
            case 3:
                amount = 100;
                break;
            default:
                break;
        }

        vendingMachine.loadMoney(amount);
        JOptionPane.showMessageDialog(frame, "Money loaded: PhP " + amount, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Collects money from the vending machine by deducting a specified amount from the current balance.
     * The user is prompted to enter the amount to collect.
     * If the input is invalid or the user cancels the operation, the method will return without collecting money.
     */
    private void collectMoney() {
        String input = JOptionPane.showInputDialog(frame, "Enter the amount to collect:", "Collect Money", JOptionPane.PLAIN_MESSAGE);

        if (input == null) {
            // User canceled
            return;
        }

        try {
            double amount = Double.parseDouble(input);
            if (amount > 0) {
                vendingMachine.deductBalance(amount); // Call the collectMoney() method from the VendingMachine class with the amount as an argument
                JOptionPane.showMessageDialog(frame, "Money collected: PhP " + amount, "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid amount. Please enter a positive value.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Displays the current balance and denominations (number of bills) in the vending machine.
     * The information includes the current balance and the counts of different denominations (PhP 10s, 20s, 50s, and 100s).
     */
    private void checkBalance() {
        String message = "Current Balance: PhP " + vendingMachine.getBalance() + "\n";
        message += "Number of 10s: " + vendingMachine.getNumTens() + "\n";
        message += "Number of 20s: " + vendingMachine.getNumTwenties() + "\n";
        message += "Number of 50s: " + vendingMachine.getNumFifties() + "\n";
        message += "Number of 100s: " + vendingMachine.getNumHundreds() + "\n";

        JOptionPane.showMessageDialog(frame, message, "Balance and Denominations", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Updates the price of an item in the vending machine.
     * The user is prompted to enter the item name and the new price for the item.
     * If the user cancels the input or the price format is invalid, the method will return without updating the price.
     */
    private void updatePrice() {
        String itemName = JOptionPane.showInputDialog(frame, "Enter the item name to update price:", "Update Price", JOptionPane.PLAIN_MESSAGE);

        if (itemName == null) {
            // User canceled the input
            return;
        }

        String priceInput = JOptionPane.showInputDialog(frame, "Enter the new price for " + itemName + ":", "Update Price", JOptionPane.PLAIN_MESSAGE);

        if (priceInput == null) {
            // User canceled the input
            return;
        }

        try {
            double newPrice = Double.parseDouble(priceInput);
            vendingMachine.updatePrice(itemName, newPrice);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid price format. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Displays the available items in the vending machine using a JOptionPane message dialog.
     */
    private void showAvailableItemsDialog() {
        List<Item> items = vendingMachine.getItems();
        if (items.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No items available in the vending machine.", "Inventory Empty", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder itemListMessage = new StringBuilder("Available Items:\n");
        for (Item item : items) {
            itemListMessage.append(item.getName()).append(" - Price: PhP ").append(item.getPrice()).append(", Quantity: ").append(item.getQuantity()).append("\n");
        }

        JOptionPane.showMessageDialog(frame, itemListMessage.toString(), "Available Items", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * The main method to launch the application and create the GUI.
     *
     * @param args The command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI();
            }
        });
    }
}
