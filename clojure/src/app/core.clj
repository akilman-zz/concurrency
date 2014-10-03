(ns app.core
  (:refer-clojure :exclude [map reduce into partition partition-by take merge])
  (:require [clojure.core.async :refer :all :as async]))

(let [c (chan 10)]
  (>!! c "hello")
  (println (<!! c))
  (close! c))