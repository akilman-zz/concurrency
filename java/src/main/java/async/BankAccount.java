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
        System.out.println(String.format("Deposited $%d. Balance $%d", amount, balance));
    }

    public synchronized void withdraw(int amount) {
        if (amount > balance) {
            System.out.println(String.format("Cannot withdraw [%s], insufficient funds", amount));
            return;
        }

        balance -= amount;
        System.out.println(String.format("Withdrew $%d. Balance $%d", amount, balance));
    }

    public int getBalance() {
        return balance;
    }
}
