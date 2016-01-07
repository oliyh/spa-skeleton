(defproject spa-skeleton "0.1.0-SNAPSHOT"
  :description "A skeleton project for a ClojureScript Single Page Application backed by a Swagger API"
  :url "https://github.com/oliyh/spa-skeleton"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [frankiesardo/pedestal-swagger "0.4.4"]
                 [io.pedestal/pedestal.service "0.4.1"]
                 [io.pedestal/pedestal.jetty "0.4.1"]

                 [ch.qos.logback/logback-classic "1.1.3" :exclusions [org.slf4j/slf4j-api]]
                 [org.slf4j/jul-to-slf4j "1.7.13"]
                 [org.slf4j/jcl-over-slf4j "1.7.13"]
                 [org.slf4j/log4j-over-slf4j "1.7.13"]
                 [org.clojure/tools.logging "0.3.1"]

                 [jarohen/nomad "0.7.2"]
                 [hiccup "1.0.5"]

                 ;;cljs
                 [org.clojure/clojurescript "1.7.189"]
                 [secretary "1.2.3"]
                 [reagent "0.6.0-alpha"]
                 [cljs-ajax "0.5.2"]]
  :main ^:skip-aot spa_skeleton.server
  :target-path "target/%s"
  :source-paths ["src/clj"]
  :resource-paths ["resources"]
  :uberjar-name "spa-skeleton-standalone.jar"
  :profiles {:uberjar {:aot :all
                       :cljsbuild {:builds
                                   [{:source-paths ["src/cljs"]
                                     :jar true
                                     :compiler {:output-to "resources/public/cljs/main.js"
                                                :output-dir "resources/public/cljs/prod"
                                                :main "spa-skeleton.app"
                                                :asset-path "/cljs/prod"
                                                :optimizations :advanced}}]}
                       :prep-tasks ["javac" "compile" ["with-profile" "uberjar" "cljsbuild" "once"]]}
             :dev {:source-paths ["dev"]
                   :jvm-opts ["-Dnomad.env=dev"]
                   :dependencies [[org.clojure/tools.namespace "0.2.11"]

                                  ;; cljs
                                  [com.cemerick/piggieback "0.2.1"]
                                  [org.clojure/tools.nrepl "0.2.12"]
                                  [org.clojure/tools.reader "0.10.0"]
                                  [org.clojure/tools.trace "0.7.9"]]
                   :repl-options {:init-ns user
                                  :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
                   :plugins [[lein-cljsbuild "1.1.2"]
                             [lein-figwheel "0.5.0-2"]]
                   :cljsbuild {:builds [{:source-paths ["src/cljs"]
                                         :figwheel true
                                         :compiler {:output-to "resources/public/cljs/main.js"
                                                    :output-dir "resources/public/cljs/dev"
                                                    :source-map true
                                                    :main "spa-skeleton.app"
                                                    :asset-path "/cljs/dev"
                                                    :optimizations :none
                                                    :pretty-print true}}]}}})
