package Strategy;

public class CardPayment implements PaymentStrategy {

    private final String cardNumber;
    private final String cardType;
    private String transactionId;

    public CardPayment(String cardNumber, String cardType) {
        this.cardNumber = cardNumber;
        this.cardType = cardType;
    }

    @Override
    public boolean pay(double amount) {
        if (!validate(amount)) {
            return false;
        }
        this.transactionId = processTransaction() ? "TX-" + System.currentTimeMillis() : null;
        return this.transactionId != null;
    }

    @Override
    public String getMethodName() {
        return "Card";
    }

    @Override
    public boolean validate(double amount) {
        return amount >= 0 && cardNumber != null && cardNumber.length() >= 8;
    }

    @Override
    public String generateReceipt() {
        return "Payment method: " + getMethodName()
                + ", cardType: " + cardType
                + ", transactionId: " + transactionId;
    }

    private boolean processTransaction() {
        return true;
    }
}

