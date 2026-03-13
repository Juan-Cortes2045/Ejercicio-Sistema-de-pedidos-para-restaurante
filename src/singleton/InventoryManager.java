/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Singleton;

/**
 *
 * @author Maicol
 */
import java.util.HashMap;
import java.util.Map;

public class InventoryManager {

private static InventoryManager instance;
private final Map<String, InventoryItem> inventory;

    private InventoryManager() {
        inventory = new HashMap<>();
    }

    public static InventoryManager getInstance() {
        if (instance == null) {
        instance = new InventoryManager();
        }
        return instance;
    }

    public void addProduct(String id, InventoryItem item) {
        inventory.put(id, item);
        Logger.getInstance().info("Product added to inventory: " + id);
    }

    public void removeProduct(String id) {
        if (inventory.containsKey(id)) {
            inventory.remove(id);
            Logger.getInstance().info("Product removed from inventory: " + id);
        } else {
            Logger.getInstance().warn("Product not found in inventory: " + id);
        }
    }

    public void updateStock(String id, int newQuantity) {
        InventoryItem item = inventory.get(id);
        if (item != null) {
            item.increase(newQuantity - item.getQuantity());  // adjust to new quantity
            Logger.getInstance().info("Stock updated for product: " + id + " to " + newQuantity);
        } else {
            Logger.getInstance().warn("Product not found in inventory: " + id);
        }
    }

    public InventoryItem getProduct(String id) {
        return inventory.get(id);
    }

    public void listInventory() {
        for (InventoryItem item : inventory.values()) {
            System.out.println(item);
        }
    }

    public Map<String, InventoryItem> getInventory() {
        return new HashMap<>(inventory);
    }
}