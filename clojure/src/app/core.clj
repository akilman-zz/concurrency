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

    (sleep-for 5000)

    ;; print output
    (println (str "Remaining balance " @bank-account))))

;; onward!
(example)

;; required cleanup for threads in the background
(shutdown-agents)