package Strategy;

import Singleton.Order;

public class PaymentContext {

    private PaymentStrategy strategy;
    private final Order order;

    public PaymentContext(Order order) {
        this.order = order;
    }

    public void setStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean executePayment() {
        if (strategy == null || order == null) {
            return false;
        }
        double amount = order.calculateTotal();
        if (!strategy.validate(amount)) {
            return false;
        }
        return strategy.pay(amount);
    }

    public String getReceipt() {
        if (strategy == null) {
            return "";
        }
        return strategy.generateReceipt();
    }
}

