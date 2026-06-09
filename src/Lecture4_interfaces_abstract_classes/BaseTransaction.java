import java.util.Calendar;
import java.util.UUID;

public class BaseTransaction implements TransactionInterface {
    protected double amount;
    protected Calendar date;
    protected String transactionID;

    public BaseTransaction(double amount) {
        this.amount = amount;
        this.date = Calendar.getInstance();
        this.transactionID = UUID.randomUUID().toString().substring(0, 8); // Unique short ID
    }

    @Override
    public double getAmount() { return this.amount; }

    @Override
    public Calendar getDate() { return this.date; }

    @Override
    public String getTransactionID() { return this.transactionID; }

    @Override
    public void printTransactionDetails() {
        System.out.println("--- Transaction Details ---");
        System.out.println("ID: " + transactionID);
        System.out.println("Date: " + date.getTime());
        System.out.println("Base Amount: KSh " + amount);
    }

    // Question 1: Base implementation differs substantially from subclasses
    @Override
    public void apply(BankAccount ba) throws InsufficientFundsException {
        System.out.println("[BaseTransaction] Checking system connectivity for account: " + ba.getAccountNumber());
        System.out.println("[BaseTransaction] No balance modifications done by the base layer abstraction.");
    }
}
