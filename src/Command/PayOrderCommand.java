package Command;

import Singleton.Logger;
import Dominio.Bill;
import Dominio.Order;
import Dominio.OrderStatus;
import Strategy.PaymentContext;
import Strategy.PaymentStrategy;

public class PayOrderCommand implements ICommand {

    private final Order order;
    private final PaymentStrategy paymentStrategy;
    private Bill bill;
    private OrderStatus previousStatus;

    public PayOrderCommand(Order order, PaymentStrategy paymentStrategy) {
        this.order = order;
        this.paymentStrategy = paymentStrategy;
    }

    @Override
    public void execute() {
        previousStatus = order.getStatus();
        PaymentContext context = new PaymentContext(order);
        context.setStrategy(paymentStrategy);

        boolean success = context.executePayment();
        if (success) {
            order.setStatus(OrderStatus.PAID);
            bill = new Bill(order);
            bill.generate();
            Logger.getInstance().info("Payment successful: " + order.getOrderId());
        } else {
            Logger.getInstance().info("Payment failed: " + order.getOrderId());
        }
    }

    @Override
    public void undo() {
        order.setStatus(previousStatus);
        bill = null;
        Logger.getInstance().warning("Payment reversed: " + order.getOrderId());
    }

    @Override
    public String getDescription() {
        return "Pay order with " + paymentStrategy.getMethodName();
    }
    
}