/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Observer;
import java.util.Scanner;
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

        while (true) {

            System.out.println(" MENU ");
            System.out.println("1. Add product");
            System.out.println("2. Remove product");
            System.out.println("3. Exit");

            int option = scanner.nextInt();

            switch (option) {

                case 1:
                    observable.notifyObservers("Product added");
                    break;

                case 2:
                    observable.notifyObservers("Product removed");
                    break;

                case 3:
                    System.exit(0);
            }

        }

    }

}