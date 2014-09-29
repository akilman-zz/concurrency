package async;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Test {@link BankAccount}
 */
public class BankAccountTest {

    @Test
    public void testDeposit() throws Exception {

        int balance = 100;
        BankAccount bankAccount = new BankAccount(balance);

        int toDeposit = 100;
        bankAccount.deposit(toDeposit);

        assertEquals(bankAccount.getBalance(), balance + toDeposit);
    }

    @Test
    public void testWithdraw() throws Exception {

        int balance = 100;
        BankAccount bankAccount = new BankAccount(balance);

        int toWithdraw = 100;
        bankAccount.withdraw(toWithdraw);

        assertEquals(bankAccount.getBalance(), 0);

        bankAccount.withdraw(toWithdraw);
        assertEquals(bankAccount.getBalance(), 0);
    }
}
