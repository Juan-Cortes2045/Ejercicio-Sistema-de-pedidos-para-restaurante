package Command;

import Singleton.Logger;
import Dominio.Order;
import Dominio.OrderStatus;

public class CancelOrderCommand implements ICommand {

    private final Order order;
    private OrderStatus previousStatus;

    public CancelOrderCommand(Order order) {
        this.order = order;
    }

    @Override
    public void execute() {
        previousStatus = order.getStatus();
        order.cancel();
        Logger.getInstance().info("Order cancelled: " + order.getOrderId());
    }

    @Override
    public void undo() {
        order.setStatus(previousStatus);
        Logger.getInstance().info("Cancellation undone: " + order.getOrderId());
    }

    @Override
    public String getDescription() {
        return "Cancel order: " + order.getOrderId();
    }
}
