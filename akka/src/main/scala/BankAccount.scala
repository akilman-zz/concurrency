import akka.actor.{ActorRef, Actor, ActorSystem, Props}

object App {

  def main(args: Array[String]) {

    // spawn actor system
    val system = ActorSystem("bank-account")

    val initialBalance = 100
    val bankAccount = system.actorOf(Props(new BankAccount(initialBalance)), name="bank-account")

    while (true) {
      bankAccount ! Deposit(10)
      Thread.sleep(1000)
    }
  }
}

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

trait Op
case class Deposit(val amount: Int) extends Op
case class Withdraw(val amount: Int) extends Op

