(ns app.core
  (:refer-clojure :exclude [map reduce into partition partition-by take merge])
  (:require [clojure.core.async :refer :all :as async]))

;;; "deposits" money into a given bank account
(defn deposit [c n]
  (println (str "Depositing $" n " dollars"))
  (+ c n))

;;; "withdraws" money from a given bank account
(defn withdraw [c n]
  (println (str "Withdrawing $" n " dollars"))
  (- c n))

;;; Wrapper for Thread.sleep. Reads a bit better
(defn sleep-for [n]
  (Thread/sleep n))

;;; Example of using Agents and the Clojure STM to simulate a bank account use case
;;;
;;; Two threads of execution are spawned: (1) deposits, (2) via withdrawls via futures.
;;; Each logical thread of execution sleeps for a short random internal and updates the
;;; bank account balance via the 'send' function
(defn example []

  ;; start with a bank account of balance zero
  (let [bank-account (agent 0)
        nTransactions 5]

    (future
      (dotimes [n nTransactions]
        (sleep-for (rand 500))
        (send bank-account deposit 5)))

    (future
      (dotimes [n (- nTransactions 1)]
        (sleep-for (rand 500))
        (send bank-account withdraw 5)))

    ;; futures are not guaranteed to execute immediately, thus the explicit wait
    (sleep-for 5000)

    ;; print output
    (println (str "Remaining balance " @bank-account))))

;; onward!
(example)

;; required cleanup for threads in the background
(shutdown-agents)