/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Commad;

/**
 *
 * @author MERARI URBANO
 */
public class CreateOrderCommand implements ICommand {

    private final OrderManager orderManager;
    private Order createdOrder;

    public CreateOrderCommand(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    @Override
    public void execute() {
        createdOrder = orderManager.createOrder();
        Logger.getInstance().info("Order created: " + createdOrder.getOrderId());
    }

    @Override
    public void undo() {
        if (createdOrder != null) {
            orderManager.getOrders().remove(createdOrder.getOrderId());
            Logger.getInstance().info("Order removed (undo): " + createdOrder.getOrderId());
        }
    }

    @Override
    public String getDescription() {
        return "Create new order";
    }
}
