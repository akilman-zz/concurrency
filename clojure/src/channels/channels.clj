(ns channels.core
  (:refer-clojure :exclude [map reduce into partition partition-by take merge])
  (:require [clojure.core.async :refer :all :as async]))

(defn applyTransaction [c n]
  (+ c n))

;;; Wrapper for Thread.sleep. Reads a bit better
(defn sleep-for [n]
  (Thread/sleep n))

;;; Example of using Clojure channels to simulate a bank account use case
;;;
;;; One transaction channel is established, and the bank account is represented as an
;;; atom. Two go blocks deposit/withdraw, while another processes transactions via
;;; bank-account atom updates
(defn example []

  (let [bank-account (atom 0)
        transactions (chan)
        nTransactions 100]

    ;; "deposits"
    (go
      (dotimes [n nTransactions]
        (>! transactions 5)))

    ;; "withdrawls"
    (go
      (dotimes [n (- nTransactions 1)]
        (>! transactions -5)))

    ;; transaction processing
    (go
      (dotimes [n (+ nTransactions (- nTransactions 1))]
        (let [amount (<! transactions)]
          (println (str "Amount: $" amount))
          (swap! bank-account applyTransaction amount))))

    (sleep-for 5000)

    (println (str "Remaining balance " @bank-account))))

(example)