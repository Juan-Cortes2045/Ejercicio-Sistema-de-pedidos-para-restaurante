/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Observer;

import java.util.Scanner;
import Singleton.OrderManager;
import Singleton.CatalogManager;
import Singleton.InventoryManager;
import Singleton.Logger;
import Dominio.Order;
import Dominio.Product;
import Dominio.Bill;
import Singleton.InvoiceType;
import Singleton.InventoryItem;
import Command.CommandInvoker;
import Command.CreateOrderCommand;
import Command.AddProductCommand;
import Command.RemoveProductCommand;
import Command.ConfirmOrderCommand;
import Command.CancelOrderCommand;
import Command.UpdateOrderCommand;
import Command.PayOrderCommand;
import Strategy.PaymentContext;
import Strategy.CashPayment;
import Strategy.CardPayment;
import Strategy.TransferPayment;

/**
 *
 * @author JUAN CAMILO
 */

public class Menu {

    public static void main(String[] args) {

        Observers observable = new Observers();

        IObserver inventoryObserver = new InventoryObserver();
        IObserver loggerObserver = new LoggerObserver();

        observable.addObserver(inventoryObserver);
        observable.addObserver(loggerObserver);

        Scanner scanner = new Scanner(System.in);
        OrderManager orderManager = OrderManager.getInstance();
        CatalogManager catalog = CatalogManager.getInstance();
        InventoryManager inventory = InventoryManager.getInstance();
        CommandInvoker invoker = new CommandInvoker();

        Order currentOrder = null;

        while (true) {

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

            System.out.print("Choose an option: ");
            int option = scanner.nextInt();

            switch (option) {

                case 1:
                    // Create new order
                    CreateOrderCommand createCmd = new CreateOrderCommand(orderManager);
                    invoker.executeCommand(createCmd);
                    currentOrder = createCmd.getCreatedOrder();
                    observable.notifyObservers("New order created: " + currentOrder.getOrderId());
                    break;

                case 2:
                    // Add product to order
                    if (currentOrder == null) {
                        System.out.println("No active order. Create one first.");
                        break;
                    }
                    System.out.print("Enter product ID: ");
                    String productId = scanner.next();
                    Product product = catalog.getProduct(productId);
                    if (product == null) {
                        System.out.println("Product not found.");
                        break;
                    }
                    System.out.print("Enter quantity: ");
                    int quantity = scanner.nextInt();
                    AddProductCommand addCmd = new AddProductCommand(currentOrder, product, quantity);
                    invoker.executeCommand(addCmd);
                    observable.notifyObservers("Product added to order: " + product.getName());
                    break;

                case 3:
                    // Remove product from order
                    if (currentOrder == null) {
                        System.out.println("No active order.");
                        break;
                    }
                    System.out.print("Enter product ID to remove: ");
                    String removeId = scanner.next();
                    RemoveProductCommand removeCmd = new RemoveProductCommand(currentOrder, removeId);
                    invoker.executeCommand(removeCmd);
                    observable.notifyObservers("Product removed from order: " + removeId);
                    break;

                case 4:
                    // Update order
                    if (currentOrder == null) {
                        System.out.println("No active order.");
                        break;
                    }
                    System.out.print("Enter product ID to update: ");
                    String updateId = scanner.next();
                    OrderItem existingItem = currentOrder.getItem(updateId);
                    if (existingItem == null) {
                        System.out.println("Product not in order.");
                        break;
                    }
                    System.out.print("Enter new quantity: ");
                    int newQty = scanner.nextInt();
                    currentOrder.removeItem(updateId);
                    Product prod = existingItem.getProduct();
                    OrderItem newItem = new OrderItem(prod, newQty);
                    currentOrder.addItem(newItem);
                    observable.notifyObservers("Order updated: " + updateId + " to " + newQty);
                    break;

                case 5:
                    // View current order
                    if (currentOrder == null) {
                        System.out.println("No active order.");
                    } else {
                        System.out.println("Current Order: " + currentOrder);
                    }
                    break;

                case 6:
                    // Confirm order
                    if (currentOrder == null) {
                        System.out.println("No active order.");
                        break;
                    }
                    ConfirmOrderCommand confirmCmd = new ConfirmOrderCommand(currentOrder);
                    invoker.executeCommand(confirmCmd);
                    observable.notifyObservers("Order confirmed: " + currentOrder.getOrderId());
                    break;

                case 7:
                    // Cancel order
                    if (currentOrder == null) {
                        System.out.println("No active order.");
                        break;
                    }
                    CancelOrderCommand cancelCmd = new CancelOrderCommand(currentOrder);
                    invoker.executeCommand(cancelCmd);
                    observable.notifyObservers("Order cancelled: " + currentOrder.getOrderId());
                    currentOrder = null;
                    break;

                case 8:
                    // Pay order
                    if (currentOrder == null) {
                        System.out.println("No active order.");
                        break;
                    }
                    System.out.println("Choose payment method:");
                    System.out.println("1. Cash");
                    System.out.println("2. Card");
                    System.out.println("3. Transfer");
                    int paymentOption = scanner.nextInt();
                    PaymentContext paymentContext = new PaymentContext(currentOrder);
                    switch (paymentOption) {
                        case 1:
                            System.out.print("Enter amount received: ");
                            double amount = scanner.nextDouble();
                            paymentContext.setStrategy(new CashPayment(amount));
                            break;
                        case 2:
                            System.out.print("Enter card number: ");
                            String cardNum = scanner.next();
                            System.out.print("Enter card type: ");
                            String cardType = scanner.next();
                            paymentContext.setStrategy(new CardPayment(cardNum, cardType));
                            break;
                        case 3:
                            System.out.print("Enter bank account: ");
                            String account = scanner.next();
                            System.out.print("Enter bank name: ");
                            String bank = scanner.next();
                            System.out.print("Enter reference code: ");
                            String ref = scanner.next();
                            paymentContext.setStrategy(new TransferPayment(account, bank, ref));
                            break;
                        default:
                            System.out.println("Invalid option.");
                            continue;
                    }
                    PayOrderCommand payCmd = new PayOrderCommand(currentOrder, paymentContext.getStrategy());
                    invoker.executeCommand(payCmd);
                    observable.notifyObservers("Order paid: " + currentOrder.getOrderId());
                    currentOrder = null; // Reset for new order
                    break;

                case 9:
                    // Generate bill
                    if (currentOrder == null) {
                        System.out.println("No active order.");
                        break;
                    }
                    System.out.println("Choose bill type:");
                    System.out.println("1. Physical");
                    System.out.println("2. Digital");
                    int billType = scanner.nextInt();
                    InvoiceType type = billType == 1 ? InvoiceType.PHYSICAL : InvoiceType.DIGITAL;
                    Bill bill = new Bill(currentOrder, type);
                    bill.generate();
                    bill.print();
                    observable.notifyObservers("Bill generated for order: " + currentOrder.getOrderId());
                    break;

                case 10:
                    // Add product to inventory
                    System.out.print("Enter product ID from catalog: ");
                    String prodId = scanner.next();
                    Product p = catalog.getProduct(prodId);
                    if (p == null) {
                        System.out.println("Product not in catalog.");
                        break;
                    }
                    System.out.print("Enter quantity: ");
                    int qty = scanner.nextInt();
                    System.out.print("Enter min stock: ");
                    int minStock = scanner.nextInt();
                    InventoryItem item = new InventoryItem(p, qty, minStock);
                    inventory.addProduct(prodId, item);
                    observable.notifyObservers("Product added to inventory: " + prodId);
                    break;

                case 11:
                    // Remove product from inventory
                    System.out.print("Enter product ID to remove: ");
                    String remId = scanner.next();
                    inventory.removeProduct(remId);
                    observable.notifyObservers("Product removed from inventory: " + remId);
                    break;

                case 12:
                    // Update stock
                    System.out.print("Enter product ID: ");
                    String upId = scanner.next();
                    System.out.print("Enter new quantity: ");
                    int newQ = scanner.nextInt();
                    inventory.updateStock(upId, newQ);
                    observable.notifyObservers("Stock updated for: " + upId);
                    break;

                case 13:
                    // View inventory
                    System.out.println("Inventory: " + inventory.getInventory());
                    break;

                case 14:
                    // View catalog
                    System.out.println("Catalog: " + catalog.getProducts());
                    break;

                case 15:
                    // Undo last command
                    invoker.undoLast();
                    observable.notifyObservers("Last command undone");
                    break;

                case 16:
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid option.");
            }

        }

    }

}