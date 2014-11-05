import akka.actor.{Actor, ActorSystem, Props}

object App {

  /**
   * Simple local [[Actor]] example
   *
   * An [[ActorSystem]] is created, which contains a [[BankAccount]] actor representing a bank account. The main
   * thread simply fires a [[Deposit]] messages to the [[BankAccount]] actor once a second
   *
   * @param args main program arguments
   */
  def main(args: Array[String]) {

    val system = ActorSystem("bank-account")

    val initialBalance = 100
    val bankAccount = system.actorOf(Props(new BankAccount(initialBalance)), name="bank-account")

    while (true) {
      bankAccount ! Deposit(10)
      Thread.sleep(1000)
    }
  }
}

/**
 * [[Actor]] representing a bank account. Initialized with an initial balance, and maintains a variable running
 * balance
 *
 * @param initialBalance initial balance of the bank account
 */
class BankAccount(val initialBalance: Int) extends Actor {

  var balance = initialBalance

  def receive = {

    case Deposit(amount) =>

      balance = balance + amount
      println(s"Deposited $$$amount. Balance $$$balance")


    case Withdraw(amount) =>

      if (amount > balance)
        println(s"Cannot withdraw $amount. Insufficient funds")
      else {
        balance = balance - amount
        println(s"Withdrew $$$amount. Balance $$$balance")
      }
  }
}

/**
 * Messages representing [[BankAccount]] transactions
 */
trait Op
case class Deposit(val amount: Int) extends Op
case class Withdraw(val amount: Int) extends Op

