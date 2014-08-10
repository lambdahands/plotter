(defproject plotter "0.1.0-SNAPSHOT"
  :description "Experimental d3 plotter for ClojureScript"
  :url "http://github.com/lambdahands/plotter"
  :license {:name "MIT"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2280"]
                 [figwheel "0.1.3-SNAPSHOT"]]
  :plugins [[lein-cljsbuild "1.0.3"]
            [lein-figwheel "0.1.3-SNAPSHOT"]
            [com.cemerick/austin "0.1.4"]]
  :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
  :figwheel {
    :http-server-root "public"
    :port 3449
    :css-dirs ["resources/public/css"]}
  :cljsbuild {
    :builds [{:id "dev"
              :source-paths ["src/plotter" "src/figwheel" "src/brepl"]
              :compiler {
                :output-to "resources/public/plotter.js"
                :output-dir "resources/public/out"
                :optimizations :none
                :source-map true}}]})
