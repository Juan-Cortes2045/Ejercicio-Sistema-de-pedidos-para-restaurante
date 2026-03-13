package Strategy;

public interface PaymentStrategy {
    boolean pay(double amount);
    String getMethodName();
    boolean validate(double amount);
    String generateReceipt();
}
