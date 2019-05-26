(ns playground.repl
  (:require [clojure.repl :as r]
            [com.hypirion.clj-xchart :as c]))


(def xy-data
  (c/xy-chart {"Expected" [(range 10) (range 10)]
               "Actual"   [(range 10) (map #(+ % (rand-int 5) -2)
                                           (range 10))]}))

(defn show-xychart
  [] 
  (c/view xy-data))



(def savings-expected
  [["Food" "Savings" "Rent"] [5.2 3.5 13.4]])

(def savings-actual
  [["Food" "Savings" "Rent" "Unexpected"] [5.5 2.5 13.4 1.0]])



(defn savings-data []
  (c/category-chart* {"Expected" savings-expected
                       "Actual"   savings-actual}))


(defn show-savings-category-chart
  []
  (c/view (savings-data)))



(def fruit-sales-data
  {"Apples"     {"Mon" 6 "Tue" 2  "Wed" 1  "Thur" 3  "Fri" 3}
   "bananas"    {"Mon" 1 "Tue" 3  "Wed" 5  "Thur" 0  "Fri" 1}
   "Mangos"     {"Mon" 0 "Tue" 2  "Wed" 0  "Thur" 1  "Fri" 1}
   "Pineapples" {"Mon" 0 "Tue" 0  "Wed" 1  "Thur" 0  "Fri" 2}
   "Pears"      {"Mon" 3 "Tue" 0  "Wed" 1  "Thur" 1  "Fri" 4}})

(def fruit-sales-chart-def
  {:title "Weakly Fruit sales"
   :width 640
   :height 500
   :stacked? true
   :x-axis {:order ["Mon" "Tue" "Wed" "Thur" "Fri"]}})


(defn show-fruit-sales-chart
  []
  (c/view (c/category-chart
           fruit-sales-data
           fruit-sales-chart-def)))


