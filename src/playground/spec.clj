(ns playground.spec
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as g]
            [com.hypirion.clj-xchart :as xc]))




(defn xy-data
  "Generat two sets of x, y data"
  []
  (xc/xy-chart {"Expected" [(range 10) (range 10)]
                "Actual"   [(range 10) (map #(+ % (rand-int 5) -2) (range 10))]}))

;; Vieew as x-y chart
(defn view-xy
  []
  (xc/view (xy-data)))

(s/conform even? 3)
(s/valid? even? 3)
(s/valid? nil? 0)
(s/valid? #(> % 5) 6)
(s/valid? {:a 1 :b 2} :b)


;; Register specs
(s/def ::suit #{:club :daimond :heart :spade})

(s/valid? ::suit :spade)

;;
(s/def ::valid-args (s/and int? even? #(> % 10) #(< % 30)))

(s/conform ::valid-args 10)
(s/valid?  ::valid-args 11)
(s/valid? ::valid-args 12)

(s/explain ::valid-args 11)


(s/def ::id int?)
(s/def ::first-name string?)
(s/def ::last-name string?)
(s/def ::phone string?)
(s/def ::person (s/keys :req [::id ::first-name ::last-name]
                        :op [::phone]))


(def peter {::id 100
            ::first-name "Peter"
            ::last-name "Makumbi"
            ::email "abc@cde.com"})

(defn name
  [person]
  {:pre [(s/valid? ::person person)]
   :post [(s/valid? string? %)]}
  (str (::first-name person) " " (::last-name person)))

(s/conform ::person peter)
(s/valid? ::person peter)
(name peter)


(defn validate
  [spec x]
  (when-not (s/valid? spec x)
    {:status 400
     :body {:cause (s/explain-str spec x)}}))

;; Obtain a spec and generate a value
(g/generate  (s/gen string?))

;; Obtain a spec and generate a series of samples
(g/sample (s/gen string?))


(def sc (s/gen (s/cat :k keyword? :v number?)))

(g/generate sc)
(g/sample sc)

(def sc2 (s/cat :e even? :o odd?))
(s/conform sc2 [1 2])
(s/gen sc2)

(g/generate (s/gen (s/cat :e even? :o int?)) )


(-> (s/cat :e even? :o odd?)
    (s/gen)
    (g/generate))
