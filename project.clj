(defproject playground "0.1.0-SNAPSHOT"
  :description "Playground project - addhoc exploration of ideas in code"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/data.csv "0.1.4"]
                 [org.clojure/data.json "0.2.6"]
                 [org.clojure/core.async "0.4.490"]
                 [org.clojure/spec.alpha "0.2.176"]
                 [hiccup "1.0.5"]                 
                 [incanter "1.9.3"]
                 [metasoarous/oz "1.6.0-alpha2"]
                 [com.hypirion/clj-xchart "0.2.0"]]
  :profiles {:dev {:dependencies [[org.clojure/test.check "0.9.0"]]}}
  :repl-options {:init-ns playground.core})
