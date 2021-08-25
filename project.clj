(defproject todo-clj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [ring "1.9.3"]
                 [compojure "1.6.2"]
                 [environ "1.2.0"]]
  :plugins [[lein-environ "1.2.0"]]
  :repl-options {:init-ns todo-clj.core}
  :profiles
  {:dev {:dependencies [[prone "1.6.4"]]
         :env {:dev true}}})
