package Command;

import Singleton.Logger;
import Singleton.Order;
import Singleton.OrderManager;

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
