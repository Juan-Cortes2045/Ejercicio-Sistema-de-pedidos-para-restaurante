package Strategy;

public class TransferPayment implements PaymentStrategy {

    private final String bankAccount;
    private final String referenceCode;
    private final String bankName;

    public TransferPayment(String bankAccount, String bankName, String referenceCode) {
        this.bankAccount = bankAccount;
        this.bankName = bankName;
        this.referenceCode = referenceCode;
    }

    @Override
    public boolean pay(double amount) {
        if (!validate(amount)) {
            return false;
        }
        return verifyTransfer();
    }

    @Override
    public String getMethodName() {
        return "Transfer";
    }

    @Override
    public boolean validate(double amount) {
        return amount >= 0 && bankAccount != null && !bankAccount.isEmpty();
    }

    @Override
    public String generateReceipt() {
        return "Payment method: " + getMethodName()
                + ", bankName: " + bankName
                + ", bankAccount: " + bankAccount
                + ", referenceCode: " + referenceCode;
    }

    private boolean verifyTransfer() {
        return true;
    }
}

