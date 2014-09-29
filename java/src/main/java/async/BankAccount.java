package async;

/**
 * Representation for a bank account
 */
public class BankAccount {

    private int balance;

    public BankAccount(int balance) {
        this.balance = balance;
    }

    public synchronized void deposit(int amount) {
        balance += amount;
    }

    public synchronized void withdraw(int amount) {
        if (amount > balance) {
            System.out.println(String.format("Cannot withdraw [%s], insufficient funds", amount));
            return;
        }

        balance -= amount;
    }

    public int getBalance() {
        return balance;
    }
}
