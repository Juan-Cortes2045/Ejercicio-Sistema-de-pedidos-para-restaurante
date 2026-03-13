/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Commad;

/**
 *
 * @author MERARI URBANO
 */
public class RemoveProductCommand implements ICommand {

    private final Order order;
    private final String productId;
    private OrderItem removedItem;

    public RemoveProductCommand(Order order, String productId) {
        this.order = order;
        this.productId = productId;
    }

    @Override
    public void execute() {
        removedItem = order.getItem(productId);  // save before removing
        order.removeItem(productId);
        Logger.getInstance().info("Product removed: " + productId);
    }

    @Override
    public void undo() {
        if (removedItem != null) {
            order.addItem(removedItem);
            Logger.getInstance().info("Product restored (undo): " + productId);
        }
    }

    @Override
    public String getDescription() {
        return "Remove product: " + productId;
    }
}
