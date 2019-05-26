(ns playground.reducers
  (:require [clojure.core.async :as async]))

;; Before transducers there were 3 ways of
;; map filter reduce
;; 1. necsted
(defn with-nested-calls
  [input-coll]
  (reduce + (take 5 (map inc (filter even? input-coll)))))

(comment 
  (reduce + (take 5 (map inc (filter even? (range 20))))))

;;2.  with threading macro
(defn with-threading-macro
  [input-coll]
  (->> input-coll
       (filter even?)
       (map inc)
       (reduce +)))

(comment 
  (->> (range 20)
       (filter even?)
       (map inc)
       (take 5)
       (reduce +)))


;; 3. with function composition
(defn with-composition
  [input-coll]
  (let [comp-fn (comp (take 5)
                      (partial map inc)
                      (partial filter even?))]
    (reduce + (comp-fn input-coll))))

(def xf (comp (partial take 5)
              (partial map inc)
              (partial filter even?)))
(comment 
  (reduce + (xf (range 20))))


;; With transduers
(defn with-transducers
  [input-coll]
  (let [xform (comp (filter even?)
                    (map inc))]
    (transduce xform + input-coll)))


(comment
  (def xform (comp (filter even?)
                   (map inc)
                   (take 5))) 

  (transduce xform + (range 20))

  (transduce xform conj [] (range 20)))




;; map filter reduce

;;
;; Lets implement map with reduce
;; 1. implement a function
;; that returs a maaping function
(defn map-fn
  [mapper]
  (fn [acc input]
    (conj acc (mapper input))))


;; 1b. run the function with reduce
(reduce (map-fn inc) [] (range 10))

;;2. Implement a function that returns a filtering
;; function given a predicate
(defn filter-fn
  [predicate]
  (fn [acc input]
    (if (predicate input)
      (conj acc input)
      acc)))

;; 3. transducing functions
;; redurns
(defn mapping-transducer
  [mapping-fn]
  (fn [reducing-fn]
    (fn [acc input]
      (reducing-fn acc (mapping-fn input)))))


(reduce ((mapping-transducer even?) conj) [] (range 20) )

(def xf
  (comp (filter even?)
        (map inc)
        (take 5)))

(comment 
  ;; Problem
  ;; map filter reduce  fucntions  expect one sequence and return a
  (->> (range 10)
       (filter even?)
       (map #(* % %))
       (map inc)
       (reduce +))


  ;; or reducing functions
  ;; whatever, input -> whatever
  (reduce + [1 2 -3])

  ;; What if there are more than once collection
  ;; a nested exception for example
  ;; The data doesn't have the righ shape
  ;; [[1 2 3] [4 5 6]]
  ;; This fails!!
  ;; 1. Unhandled java.lang.ClassCastException
  ;; clojure.lang.PersistentVector cannot be cast to java.lang.Number
  ;; (reduce + [[1 2 -3] [4 5 -6]])


  ;; Transduders change the algortihm not the input collection
  (transduce cat + [[1 2 -3] [4 5 -6]]))

;; allow to name transforms xforms
(def pos-vals
  (comp
   cat
   (filter pos?)))



;;
;; define a transuder that
;; filters postive values and 
(def even-vals
  (comp cat
        (filter even?)))


;; Apply xf on function +
(comment  
  (transduce even-vals + [(range 4) (range 4 8) (range 8 12)]))

;; Tranducers can be applied to different contexts
;; collections, channels
;;
(def xf (comp cat
              (filter even?)))

(def ch (async/chan 3 xf))
(async/>!! ch (range 5))

(async/<!! ch)
(async/<!! ch)
(async/<!! ch)

