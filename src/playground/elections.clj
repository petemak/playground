(ns playground.elections
  (:require [clojure.java.io :as io]
            [incanter.core :as icore]
            [incanter.excel :as ixls]))


(defmulti load-data identity)

(defmethod load-data :uk
  [_]
  (-> (io/resource "UK2010.xls")
      (str)
      (ixls/read-xls)))


(defmethod load-data :ug
  [_]
  (-> (io/resource "Uganda2011.xls")
      (str)
      (ixls/read-xls)))


(defn cols
  [] 
  (icore/col-names (load-data :uk)))



