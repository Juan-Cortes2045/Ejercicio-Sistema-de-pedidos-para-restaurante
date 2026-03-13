package Command;

import Singleton.Logger;
import Dominio.Order;
import Dominio.OrderStatus;

public class ConfirmOrderCommand implements ICommand {

    private final Order order;
    private OrderStatus previousStatus;

    public ConfirmOrderCommand(Order order) {
        this.order = order;
    }

    @Override
    public void execute() {
        previousStatus = order.getStatus();
        order.confirm();
        Logger.getInstance().info("Order confirmed: " + order.getOrderId());
    }

    @Override
    public void undo() {
        order.setStatus(previousStatus);
        Logger.getInstance().info("Confirmation undone: " + order.getOrderId());
    }

    @Override
    public String getDescription() {
        return "Confirm order: " + order.getOrderId();
    }
}