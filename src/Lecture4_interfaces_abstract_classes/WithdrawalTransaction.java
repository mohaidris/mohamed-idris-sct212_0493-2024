public class WithdrawalTransaction extends BaseTransaction {
    private boolean isReversed = false;
    private BankAccount associatedAccount;
    private double shortFallAmount = 0.0; // Tracks partial failure record

    public WithdrawalTransaction(double amount) {
        super(amount);
    }

    // Question 1 & 3: Overriding and using the 'throws' keyword
    @Override
    public void apply(BankAccount ba) throws InsufficientFundsException {
        this.associatedAccount = ba; // Cache reference for potential reversal
        if (ba.getBalance() < this.amount) {
            throw new InsufficientFundsException("Error: Insufficient funds to complete transaction of KSh " + this.amount);
        }
        ba.withdraw(this.amount);
        System.out.println("[Withdrawal] KSh " + amount + " successfully deducted from Account " + ba.getAccountNumber());
    }

    // Question 3: Overloaded apply() handling partial withdrawal using try-catch-finally
    public void apply(BankAccount ba, boolean allowPartial) {
        this.associatedAccount = ba;
        if (!allowPartial) {
            try {
                apply(ba);
            } catch (InsufficientFundsException e) {
                System.out.println("[Handled Exception] " + e.getMessage());
            }
            return;
        }

        // Partial withdrawal logic: 0 < balance < withdrawal amount
        try {
            if (ba.getBalance() <= 0) {
                throw new InsufficientFundsException("Account empty. Cannot perform partial withdrawal.");
            } else if (ba.getBalance() < this.amount) {
                double availableBalance = ba.getBalance();
                this.shortFallAmount = this.amount - availableBalance;
                
                ba.withdraw(availableBalance); // Clear the account balance
                System.out.println("[Partial Withdrawal] Only KSh " + availableBalance + " could be withdrawn.");
            } else {
                apply(ba); // Enough money exists normally
            }
        } catch (InsufficientFundsException e) {
            System.out.println("[Try-Catch Block Alert] " + e.getMessage());
        } finally {
            System.out.println("[Finally Block Executed] Record updated. Shortfall unfulfilled amount: KSh " + shortFallAmount);
        }
    }

    // Question 2: Reversal logic for withdrawals
    public boolean reverse() {
        if (isReversed) {
            System.out.println("Transaction already reversed.");
            return false;
        }
        if (associatedAccount != null) {
            double amountToRestore = this.amount - this.shortFallAmount;
            associatedAccount.deposit(amountToRestore);
            this.isReversed = true;
            System.out.println("[REVERSAL SUCCESS] Restored KSh " + amountToRestore + " to Account " + associatedAccount.getAccountNumber());
            return true;
        }
        System.out.println("[REVERSAL FAILED] No associated bank account found.");
        return false;
    }

    @Override
    public void printTransactionDetails() {
        super.printTransactionDetails();
        System.out.println("Type: WITHDRAWAL");
        System.out.println("Status: " + (isReversed ? "REVERSED" : "ACTIVE"));
        System.out.println("Shortfall Record: KSh " + shortFallAmount);
        System.out.println("---------------------------");
    }
}
