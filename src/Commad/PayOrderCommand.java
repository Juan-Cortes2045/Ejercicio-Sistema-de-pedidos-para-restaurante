/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Commad;

/**
 *
 * @author MERARI URBANO
 */
public class PayOrderCommand implements ICommand {

    private final Order order;
    private final IPaymentStrategy paymentStrategy;
    private Invoice invoice;
    private OrderStatus previousStatus;

    public PayOrderCommand(Order order, IPaymentStrategy paymentStrategy) {
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
            invoice = new Invoice(order);
            invoice.generate();
            Logger.getInstance().info("Payment successful: " + order.getOrderId());
        } else {
            Logger.getInstance().error("Payment failed: " + order.getOrderId());
        }
    }

    @Override
    public void undo() {
        order.setStatus(previousStatus);
        invoice = null;
        Logger.getInstance().warning("Payment reversed: " + order.getOrderId());
    }

    @Override
    public String getDescription() {
        return "Pay order with " + paymentStrategy.getMethodName();
    }
    
}