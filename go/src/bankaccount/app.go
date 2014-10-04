package main

import (
		"time" 
		"fmt"
		"math/rand"
)

func main() {
    
	rand.Seed(42)

	var nDeposits int = rand.Intn(100)	
	var nWithdrawls int = rand.Intn(100)

	// Two channels for deposits and withdrawls, respective
	deposits := make(chan int)
	withdrawls := make(chan int)

	// go function for processing deposits
	go func() {
		for i := 0; i < nDeposits; i++ {
			time.Sleep(time.Duration(rand.Intn(1000)))
			deposits <- 1
		}
	}()

	// go function for processing withdrawls
	go func() { 
		for i := 0; i < nWithdrawls; i++ {
			time.Sleep(time.Duration(rand.Intn(1000)))
			withdrawls <- 1
		}
	}()

	// our "bank account"
	var balance int = 0

	var nTransactions int = nDeposits + nWithdrawls

	// loop through, use select construct to process transactions
	for i := 0; i < nTransactions; i++ {
		select {
		case deposit := <- deposits:
			balance += deposit
			fmt.Printf("Deposited $%v\n", deposit)

		case withdrawl := <- withdrawls:

			if (withdrawl > balance) {
				fmt.Printf("Unable to withdraw $%v. Insufficient funds!\n", withdrawl)
			} else {
				balance -= withdrawl
				fmt.Printf("Withdrew $%v\n", withdrawl)
			}
		}
	}

	fmt.Printf("Remaining balance: $%v\n", balance)
}
