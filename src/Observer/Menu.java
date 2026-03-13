/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Observer;

import Command.AddProductCommand;
import Command.CancelOrderCommand;
import Command.CommandInvoker;
import Command.ConfirmOrderCommand;
import Command.CreateOrderCommand;
import Command.PayOrderCommand;
import Command.RemoveProductCommand;
import Dominio.Bill;
import Dominio.Order;
import Dominio.OrderItem;
import Dominio.Product;
import Singleton.CatalogManager;
import Singleton.InventoryItem;
import Singleton.InventoryManager;
import Singleton.InvoiceType;
import Singleton.OrderManager;
import Strategy.CardPayment;
import Strategy.CashPayment;
import Strategy.PaymentContext;
import Strategy.TransferPayment;
import java.util.Scanner;

/**
 *
 * @author JUAN CAMILO
 */

public class Menu {

    private final Scanner scanner;
    private final OrderManager orderManager;
    private final CatalogManager catalog;
    private final InventoryManager inventory;
    private final CommandInvoker invoker;
    private final Observers observable;

    // Current order is kept as state inside the menu controller.
    private Order currentOrder;

    public Menu(Scanner scanner,
                OrderManager orderManager,
                CatalogManager catalog,
                InventoryManager inventory,
                CommandInvoker invoker,
                Observers observable) {
        this.scanner = scanner;
        this.orderManager = orderManager;
        this.catalog = catalog;
        this.inventory = inventory;
        this.invoker = invoker;
        this.observable = observable;
    }

    /**
     * Runs the interactive menu loop.
     */
    public void run() {
        while (true) {
            showMenu();
            int option = readInt("Choose an option: ");
            handleOption(option);
        }
    }

    private void showMenu() {
        System.out.println("\n=== RESTAURANT MENU ===");
        System.out.println("1. Create new order");
        System.out.println("2. Add product to order");
        System.out.println("3. Remove product from order");
        System.out.println("4. Update order");
        System.out.println("5. View current order");
        System.out.println("6. Confirm order");
        System.out.println("7. Cancel order");
        System.out.println("8. Pay order");
        System.out.println("9. Generate bill");
        System.out.println("10. Add product to inventory");
        System.out.println("11. Remove product from inventory");
        System.out.println("12. Update stock");
        System.out.println("13. View inventory");
        System.out.println("14. View catalog");
        System.out.println("15. Undo last command");
        System.out.println("16. Exit");
    }

    private void handleOption(int option) {
        switch (option) {
            case 1:
                createOrder();
                break;
            case 2:
                addProductToOrder();
                break;
            case 3:
                removeProductFromOrder();
                break;
            case 4:
                updateOrder();
                break;
            case 5:
                viewCurrentOrder();
                break;
            case 6:
                confirmOrder();
                break;
            case 7:
                cancelOrder();
                break;
            case 8:
                payOrder();
                break;
            case 9:
                generateBill();
                break;
            case 10:
                addProductToInventory();
                break;
            case 11:
                removeProductFromInventory();
                break;
            case 12:
                updateStock();
                break;
            case 13:
                viewInventory();
                break;
            case 14:
                viewCatalog();
                break;
            case 15:
                undoLastCommand();
                break;
            case 16:
                exit();
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private void createOrder() {
        CreateOrderCommand createCmd = new CreateOrderCommand(orderManager);
        invoker.executeCommand(createCmd);
        currentOrder = createCmd.getCreatedOrder();
        observable.notifyObservers("New order created: " + currentOrder.getOrderId());
    }

    private void addProductToOrder() {
        if (currentOrder == null) {
            System.out.println("No active order. Create one first.");
            return;
        }

        String productId = readString("Enter product ID: ");
        Product product = catalog.getProduct(productId);
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        int quantity = readInt("Enter quantity: ");
        AddProductCommand addCmd = new AddProductCommand(currentOrder, product, quantity);
        invoker.executeCommand(addCmd);
        observable.notifyObservers("Product added to order: " + product.getName());
    }

    private void removeProductFromOrder() {
        if (currentOrder == null) {
            System.out.println("No active order.");
            return;
        }

        String removeId = readString("Enter product ID to remove: ");
        RemoveProductCommand removeCmd = new RemoveProductCommand(currentOrder, removeId);
        invoker.executeCommand(removeCmd);
        observable.notifyObservers("Product removed from order: " + removeId);
    }

    private void updateOrder() {
        if (currentOrder == null) {
            System.out.println("No active order.");
            return;
        }

        String updateId = readString("Enter product ID to update: ");
        OrderItem existingItem = currentOrder.getItem(updateId);
        if (existingItem == null) {
            System.out.println("Product not in order.");
            return;
        }

        int newQty = readInt("Enter new quantity: ");
        currentOrder.removeItem(updateId);
        Product prod = existingItem.getProduct();
        OrderItem newItem = new OrderItem(prod, newQty);
        currentOrder.addItem(newItem);
        observable.notifyObservers("Order updated: " + updateId + " to " + newQty);
    }

    private void viewCurrentOrder() {
        if (currentOrder == null) {
            System.out.println("No active order.");
        } else {
            System.out.println("Current Order: " + currentOrder);
        }
    }

    private void confirmOrder() {
        if (currentOrder == null) {
            System.out.println("No active order.");
            return;
        }

        ConfirmOrderCommand confirmCmd = new ConfirmOrderCommand(currentOrder);
        invoker.executeCommand(confirmCmd);
        observable.notifyObservers("Order confirmed: " + currentOrder.getOrderId());
    }

    private void cancelOrder() {
        if (currentOrder == null) {
            System.out.println("No active order.");
            return;
        }

        CancelOrderCommand cancelCmd = new CancelOrderCommand(currentOrder);
        invoker.executeCommand(cancelCmd);
        observable.notifyObservers("Order cancelled: " + currentOrder.getOrderId());
        currentOrder = null;
    }

    private void payOrder() {
        if (currentOrder == null) {
            System.out.println("No active order.");
            return;
        }

        System.out.println("Choose payment method:");
        System.out.println("1. Cash");
        System.out.println("2. Card");
        System.out.println("3. Transfer");

        int paymentOption = readInt("Payment option: ");
        PaymentContext paymentContext = new PaymentContext(currentOrder);

        switch (paymentOption) {
            case 1:
                double amount = readDouble("Enter amount received: ");
                paymentContext.setStrategy(new CashPayment(amount));
                break;
            case 2:
                String cardNum = readString("Enter card number: ");
                String cardType = readString("Enter card type: ");
                paymentContext.setStrategy(new CardPayment(cardNum, cardType));
                break;
            case 3:
                String account = readString("Enter bank account: ");
                String bank = readString("Enter bank name: ");
                String ref = readString("Enter reference code: ");
                paymentContext.setStrategy(new TransferPayment(account, bank, ref));
                break;
            default:
                System.out.println("Invalid option.");
                return;
        }

        PayOrderCommand payCmd = new PayOrderCommand(currentOrder, paymentContext.getStrategy());
        invoker.executeCommand(payCmd);
        observable.notifyObservers("Order paid: " + currentOrder.getOrderId());
        currentOrder = null; // Reset for new order
    }

    private void generateBill() {
        if (currentOrder == null) {
            System.out.println("No active order.");
            return;
        }

        System.out.println("Choose bill type:");
        System.out.println("1. Physical");
        System.out.println("2. Digital");
        int billType = readInt("Bill type: ");

        InvoiceType type = billType == 1 ? InvoiceType.PHYSICAL : InvoiceType.DIGITAL;
        Bill bill = new Bill(currentOrder, type);
        bill.generate();
        bill.print();
        observable.notifyObservers("Bill generated for order: " + currentOrder.getOrderId());
    }

    private void addProductToInventory() {
        String prodId = readString("Enter product ID from catalog: ");
        Product p = catalog.getProduct(prodId);
        if (p == null) {
            System.out.println("Product not in catalog.");
            return;
        }

        int qty = readInt("Enter quantity: ");
        int minStock = readInt("Enter min stock: ");
        InventoryItem item = new InventoryItem(p, qty, minStock);
        inventory.addProduct(prodId, item);
        observable.notifyObservers("Product added to inventory: " + prodId);
    }

    private void removeProductFromInventory() {
        String remId = readString("Enter product ID to remove: ");
        inventory.removeProduct(remId);
        observable.notifyObservers("Product removed from inventory: " + remId);
    }

    private void updateStock() {
        String upId = readString("Enter product ID: ");
        int newQ = readInt("Enter new quantity: ");
        inventory.updateStock(upId, newQ);
        observable.notifyObservers("Stock updated for: " + upId);
    }

    private void viewInventory() {
        System.out.println("Inventory: " + inventory.getInventory());
    }

    private void viewCatalog() {
        System.out.println("Catalog: " + catalog.getProducts());
    }

    private void undoLastCommand() {
        invoker.undoLast();
        observable.notifyObservers("Last command undone");
    }

    private void exit() {
        System.out.println("Exiting...");
        System.exit(0);
    }

    private int readInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print("Please enter a valid number: ");
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    private double readDouble(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            scanner.next();
            System.out.print("Please enter a valid number: ");
        }
        double value = scanner.nextDouble();
        scanner.nextLine();
        return value;
    }

    private String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
