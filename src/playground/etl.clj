(ns playground.etl
  (:require [clojure.java.io :as io]
            [clojure.data.json :as json]))


(defn gen-data
  "Generate sample data and write to file location"
  [floc]
  (letfn [(rand-data []
            (case (rand-int 3)
              0 {:type "number" :number (rand-int 1000)}
              1 {:type "string" :string (apply str (repeatedly 30 #(char (+ 33 (rand-int 90)))))}
              2 {:type "empty"}))]
    (with-open [f (io/writer floc)]
      (binding [*out* f]
        (dotimes [_ 100000]
          (println (json/write-str (rand-data))))))))


