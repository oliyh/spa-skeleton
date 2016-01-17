(ns spa-skeleton.service
  (:require [clojure.core.async :as a]
            [io.pedestal.http :as bootstrap]
            [io.pedestal.http.body-params :as body-params]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.route.definition :refer [defroutes]]
            [io.pedestal.impl.interceptor :refer [terminate]]
            [io.pedestal.interceptor.helpers :refer [before handler]]
            [io.pedestal.interceptor :as interceptor]
            [pedestal.swagger.core :as swagger]
            [pedestal.swagger.doc :as sw.doc]
            [ring.util.codec :as codec]
            [ring.util.response :refer [response not-found created resource-response content-type status redirect]]
            [schema.core :as s]
            [spa-skeleton.views.index :as index]
            [clojure.tools.logging :as log]
            [clojure.string :as string]))

(def status-handler
  (sw.doc/annotate
   {:summary "Service status"
    :description "Describes the state of the service"}
   (handler
    ::status
    (fn [request]
      {:status 200
       :body {:foo "bar"}}))))

(s/with-fn-validation ;; Optional, but nice to have at compile time
  (swagger/defroutes api-routes
    {:info {:title "SPA Skeleton"
            :description "A skeleton project for a ClojureScript Single Page Application backed by a Swagger API"
            :externalDocs {:description "Find out more"
                           :url "https://github.com/oliyh/spa-skeleton"}
            :version "2.0"}}
    [[["/api" ^:interceptors [(swagger/body-params)
                              bootstrap/json-body
                              (swagger/coerce-request)
                              (swagger/validate-response)]

       ["/status"
        {:get status-handler}]

       ["/swagger.json" {:get [(swagger/swagger-json)]}]
       ["/*resource" {:get [(swagger/swagger-ui)]}]]]]))

(def home
  (handler ::home-handler
           (fn [request]
             (-> (response (index/index))
                 (content-type "text/html")))))

(def devcards
  (handler ::devcards-handler
           (fn [request]
             (-> (response (index/devcards-page))
                 (content-type "text/html")))))

(defroutes app-routes
  [[["/cards.html" {:get devcards}]
    ["/*route" {:get home}]]])

(def routes
  (concat api-routes app-routes))

(def service
  {:env :prod
   ::bootstrap/routes routes
   ::bootstrap/router :linear-search
   ::bootstrap/resource-path "/public"
   ::bootstrap/type :jetty})
