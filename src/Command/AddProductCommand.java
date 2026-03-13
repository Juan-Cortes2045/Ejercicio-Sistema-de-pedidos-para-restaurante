package Command;

import Dominio.Order;
import Dominio.OrderItem;
import Dominio.Product;
import Singleton.Logger;

public class AddProductCommand implements ICommand {

    private final Order order;
    private final Product product;
    private final int quantity;
    private OrderItem addedItem;

    public AddProductCommand(Order order, Product product, int quantity) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
    }

    @Override
    public void execute() {
        addedItem = new OrderItem(product, quantity);
        order.addItem(addedItem);
        Logger.getInstance().info("Product added: " + product.getName());
    }

    @Override
    public void undo() {
        if (addedItem != null) {
            order.removeItem(product.getProductId());
            Logger.getInstance().info("Product removed (undo): " + product.getName());
        }
    }

    @Override
    public String getDescription() {
        return "Add product: " + product.getName() + " x" + quantity;
    }
}
