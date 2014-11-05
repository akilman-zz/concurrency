package main

import (
		"time" 
		"fmt"
		"math/rand"
)

/**
 * Illustration of Go channels with select to choose input from multiple
 * 
 * Creates two go routines and two channels for generating and submitting deposits and withdrawls.
 * The main thread of execution uses a select construct in a loop to process each type of trasnaction
 * appropriately
 */
func main() {
    
	rand.Seed(42)

	var nDeposits int = rand.Intn(100)	
	var nWithdrawls int = rand.Intn(100)

	deposits := make(chan int)
	withdrawls := make(chan int)

	go func() {
		for i := 0; i < nDeposits; i++ {
			time.Sleep(time.Duration(rand.Intn(1000)))
			deposits <- 1
		}
	}()

	go func() { 
		for i := 0; i < nWithdrawls; i++ {
			time.Sleep(time.Duration(rand.Intn(1000)))
			withdrawls <- 1
		}
	}()

	var balance int = 0

	var nTransactions int = nDeposits + nWithdrawls

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
