package Observer;

import java.util.Scanner;
import Singleton.CatalogManager;
import Singleton.InventoryManager;
import Singleton.OrderManager;
import Command.CommandInvoker;

/**
 * Entry point for the restaurant application.
 *
 * This class keeps `main()` minimal and delegates the menu logic
 * to `Menu`, which is easier to test and maintain.
 */
public class App {

    public static void main(String[] args) {
        Observers observable = new Observers();
        observable.addObserver(new InventoryObserver());
        observable.addObserver(new LoggerObserver());

        Scanner scanner = new Scanner(System.in);
        OrderManager orderManager = OrderManager.getInstance();
        CatalogManager catalog = CatalogManager.getInstance();
        InventoryManager inventory = InventoryManager.getInstance();
        CommandInvoker invoker = new CommandInvoker();

        Menu menu = new Menu(scanner, orderManager, catalog, inventory, invoker, observable);
        menu.run();
    }
}
