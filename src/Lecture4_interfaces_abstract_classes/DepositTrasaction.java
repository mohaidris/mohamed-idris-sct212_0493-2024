public class DepositTransaction extends BaseTransaction {

    public DepositTransaction(double amount) {
        super(amount);
    }

    // Question 1: Method Overriding
    @Override
    public void apply(BankAccount ba) {
        ba.deposit(this.amount);
        System.out.println("[Deposit] KSh " + amount + " successfully credited to Account " + ba.getAccountNumber());
    }

    @Override
    public void printTransactionDetails() {
        super.printTransactionDetails();
        System.out.println("Type: DEPOSIT (Irreversible)");
        System.out.println("---------------------------");
    }
}
