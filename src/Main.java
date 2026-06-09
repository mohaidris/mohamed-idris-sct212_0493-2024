import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // --- Simple School Registration System Segment --
        System.out.println("      JKUAT ADVANCED PROGRAMMING ASSIGNMENT      ");
        System.out.println("=================================================");
        System.out.print("Enter Student Full Name: ");
        String studentName = scanner.nextLine();
        System.out.print("Enter Registration Number: ");
        String regNo = scanner.nextLine();
        
        System.out.println("\n--- Student Verification Successful ---");
        System.out.println("Student: " + studentName.toUpperCase());
        System.out.println("Reg No:  " + regNo.toUpperCase());
        System.out.println("=================================================\n");

        // --- Question 4: Client Code and Polymorphism Testing ---
        BankAccount account = new BankAccount("CK-90210", 5000.0);
        System.out.println("Initial Account Balance: KSh " + account.getBalance() + "\n");

        // 1. Testing Subclass: Deposit
        DepositTransaction dep = new DepositTransaction(3000.0);
        dep.apply(account);
        dep.printTransactionDetails();
        System.out.println("New Balance: KSh " + account.getBalance() + "\n");

        // 2. Testing Subclass: Normal Withdrawal
        WithdrawalTransaction wit1 = new WithdrawalTransaction(2000.0);
        try {
            wit1.apply(account);
        } catch (InsufficientFundsException e) {
            System.out.println(e.getMessage());
        }
        wit1.printTransactionDetails();
        System.out.println("New Balance: KSh " + account.getBalance() + "\n");

        // 3. Testing Question 2: Reversal of Withdrawal
        System.out.println("Executing withdrawal reversal process...");
        wit1.reverse();
        System.out.println("Balance after reversal: KSh " + account.getBalance() + "\n");

        // 4. Testing Question 3: Throws Exception (Insufficient Funds)
        System.out.println("Testing Insufficient Funds standard exception handling...");
        WithdrawalTransaction massiveWithdrawal = new WithdrawalTransaction(25000.0);
        try {
            massiveWithdrawal.apply(account);
        } catch (InsufficientFundsException e) {
            System.err.println("[Caught Exception] " + e.getMessage());
        }
        System.out.println();

        // 5. Testing Question 3: Overloaded Try-Catch-Finally Partial Withdrawal
        System.out.println("Testing Overloaded Partial Withdrawal behavior...");
        WithdrawalTransaction partialWit = new WithdrawalTransaction(10000.0);
        // Balance is currently 8000. It should clean out the 8000 and keep a 2000 shortfall tracking record.
        partialWit.apply(account, true); 
        partialWit.printTransactionDetails();
        System.out.println("Final Account Balance: KSh " + account.getBalance() + "\n");

        // 6. Polymorphic Mapping & Type Casting Demonstration
        System.out.println("Testing Polymorphism and Type Casting to Base Object...");
        BaseTransaction basePolymorphicRef = (BaseTransaction) dep; // Explicit upward cast
        
        try {
            // Early vs Late Binding: Even though the reference variable is type BaseTransaction, 
            // JVM executes the Deposit class's custom apply logic due to late/dynamic binding.
            basePolymorphicRef.apply(account); 
        } catch (InsufficientFundsException e) {
            System.out.println(e.getMessage());
        }
        
        scanner.close();
    }
}
