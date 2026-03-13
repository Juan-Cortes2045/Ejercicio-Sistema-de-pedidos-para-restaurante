package Strategy;

public class CashPayment implements PaymentStrategy {

    private final double amountReceived;
    private double change;

    public CashPayment(double amountReceived) {
        this.amountReceived = amountReceived;
    }

    @Override
    public boolean pay(double amount) {
        if (!validate(amount)) {
            return false;
        }
        this.change = amountReceived - amount;
        return true;
    }

    @Override
    public String getMethodName() {
        return "Cash";
    }

    @Override
    public boolean validate(double amount) {
        return amountReceived >= amount && amount >= 0;
    }

    @Override
    public String generateReceipt() {
        return "Payment method: " + getMethodName()
                + ", amount received: " + amountReceived
                + ", change: " + change;
    }

    public double calculateChange() {
        return change;
    }
}
