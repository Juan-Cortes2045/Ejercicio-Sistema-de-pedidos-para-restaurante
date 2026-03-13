/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Commad;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author MERARI URBANO
 */
public class UpdateOrderCommand implements ICommand {

    private final Order order;
    private final List<OrderItem> newItems;
    private List<OrderItem> previousItems;

    public UpdateOrderCommand(Order order, List<OrderItem> newItems) {
        this.order = order;
        this.newItems = newItems;
    }

    @Override
    public void execute() {
        previousItems = new ArrayList<>(order.getItems());  // copy before changing
        order.setItems(newItems);
        Logger.getInstance().info("Order updated: " + order.getOrderId());
    }

    @Override
    public void undo() {
        order.setItems(previousItems);
        Logger.getInstance().info("Update undone: " + order.getOrderId());
    }

    @Override
    public String getDescription() {
        return "Update order items: " + order.getOrderId();
    }
}
