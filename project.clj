(defproject spa-skeleton "0.1.0-SNAPSHOT"
  :description "A skeleton project for a ClojureScript Single Page Application backed by a Swagger API"
  :url "https://github.com/oliyh/spa-skeleton"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [pedestal-api "0.3.1"]
                 [io.pedestal/pedestal.service "0.5.2"]
                 [io.pedestal/pedestal.jetty "0.5.2"]

                 [ch.qos.logback/logback-classic "1.1.9" :exclusions [org.slf4j/slf4j-api]]
                 [org.slf4j/jul-to-slf4j "1.7.22"]
                 [org.slf4j/jcl-over-slf4j "1.7.22"]
                 [org.slf4j/log4j-over-slf4j "1.7.22"]
                 [org.clojure/tools.logging "0.3.1"]

                 [jarohen/nomad "0.7.3"]
                 [hiccup "1.0.5"]

                 ;;cljs
                 [org.clojure/clojurescript "1.9.293"]
                 [secretary "1.2.3"]
                 [reagent "0.6.0"]
                 [cljs-http "0.1.42"]
                 [martian "0.1.4"]
                 [martian-cljs-http "0.1.4"]
                 [org.clojure/core.async "0.2.395"]
                 [devcards "0.2.2"]]
  :main ^:skip-aot spa_skeleton.server
  :target-path "target/%s"
  :source-paths ["src/clj"]
  :resource-paths ["resources"]
  :uberjar-name "spa-skeleton-standalone.jar"
  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src/cljs"]
                        :figwheel {:on-jsload "spa-skeleton.app/figwheel-reload"}
                        :compiler {:output-to "resources/public/cljs/main.js"
                                   :output-dir "resources/public/cljs/dev"
                                   :source-map true
                                   :main "spa-skeleton.app"
                                   :asset-path "/cljs/dev"
                                   :optimizations :none
                                   :pretty-print true}}
                       {:id "devcards"
                        :source-paths ["src/cljs"]
                        :figwheel {:devcards true}
                        :compiler {:output-to "resources/public/cljs/main_devcards.js"
                                   :output-dir "resources/public/cljs/devcards"
                                   :source-map true
                                   :main "spa-skeleton.start-devcards"
                                   :asset-path "/cljs/devcards"
                                   :optimizations :none
                                   :pretty-print true}}]}
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
             :dev {:source-paths ["dev" "src/cljs"]
                   :jvm-opts ["-Dnomad.env=dev"]
                   :dependencies [[org.clojure/tools.namespace "0.2.11"]

                                  ;; cljs
                                  [figwheel-sidecar "0.5.8"]
                                  [com.cemerick/piggieback "0.2.1"]
                                  [org.clojure/tools.nrepl "0.2.12"]
                                  [org.clojure/tools.reader "1.0.0-beta4"]
                                  [org.clojure/tools.trace "0.7.9"]]
                   :repl-options {:init-ns user
                                  :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
                   :plugins [[lein-cljsbuild "1.1.2"]
                             [lein-figwheel "0.5.0-2"]]
                   :sass {:source "resources/sass" :target "resources/public/css"}}})
