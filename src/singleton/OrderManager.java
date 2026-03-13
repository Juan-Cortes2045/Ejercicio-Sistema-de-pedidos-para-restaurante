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

import Dominio.Order;

public class OrderManager {

    private static OrderManager instance;
    private final Map<String, Order> orders;

    private OrderManager() {
        orders = new HashMap<>();
    }

    public static OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager();
        }
        return instance;
    }

    public Order createOrder() {
        Order order = new Order();
        orders.put(order.getOrderId(), order);
        Logger.getInstance().info("Order created: " + order.getOrderId());
        return order;
    }

    public Map<String, Order> getOrders() {
        return orders;
    }

    public Order getOrder(String id) {
        return orders.get(id);
    }

    public void listOrders() {
        for (Order order : orders.values()) {
            System.out.println(order);
        }
    }
}