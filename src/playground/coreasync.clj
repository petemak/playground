(ns playground.coreasync
  (:require [clojure.core.async :as async]))


(def cha (async/chan 1))
(def chb (async/chan 1))

(async/pipeline 3 chb (filter even?) cha)

(defn fill-cha
  [data]
  (doseq [d data]
    (async/go (async/>! cha d))))

(defn read-chb!
  []
  (async/go-loop []
    (println (str "::=> " (async/<! chb)))
    (recur)))
