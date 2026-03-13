/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maicol.restaurant.singleton;

/**
 *
 * @author Maicol
 */
import java.util.HashMap;
import java.util.Map;

public class InventoryManager {

private static InventoryManager instance;
private Map<String, InventoryItem> inventory;

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

    public InventoryItem getProduct(String id) {
        return inventory.get(id);
    }

    public void listInventory() {
        for (InventoryItem item : inventory.values()) {
            System.out.println(item);
        }
    }
}