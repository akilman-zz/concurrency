(ns app.core
  (:refer-clojure :exclude [map reduce into partition partition-by take merge])
  (:require [clojure.core.async :refer :all :as async]))

; function to add some value 'c' to 'n'
(defn increment [c n] (+ c n))

(defn example []
  (let [
         ; start with a bank account of balance zero
         bank-account (agent 0)]

    (send bank-account increment 5)

    (Thread/sleep 1000)

    ; print output
    (println @bank-account)))

; onward!
(example)
(example)
(example)
(example)
(example)
(example)
