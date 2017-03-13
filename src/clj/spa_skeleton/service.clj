(ns spa-skeleton.service
  (:require [clojure.core.async :as a]
            [io.pedestal.http :as bootstrap]
            [io.pedestal.http.route.definition :refer [defroutes]]
            [io.pedestal.interceptor.chain :refer [terminate]]
            [io.pedestal.interceptor.helpers :refer [before handler]]
            [io.pedestal.interceptor :as interceptor]
            [pedestal-api.core :as api]
            [ring.util.codec :as codec]
            [ring.util.response :refer [response not-found created resource-response content-type status redirect]]
            [schema.core :as s]
            [spa-skeleton.views.index :as index]
            [clojure.tools.logging :as log]
            [clojure.string :as string]
            [io.pedestal.http.sse :as sse]))

(def status-handler
  (api/annotate
   {:summary "Service status"
    :description "Describes the state of the service"
    :parameters {}
    :responses {200 {:body {:foo s/Str}}}}
   (handler
    ::status
    (fn [request]
      {:status 200
       :body {:foo "bar"}}))))

(defn- initialise-stream [event-channel context]
  (a/go-loop []
    (a/>! event-channel {:data (rand-nth (range 10))})
    (a/<! (a/timeout 1000))
    (recur)))

(def events-handler
  (api/annotate
   {:summary "SSE event stream"
    :description "Broadcasts random numbers"
    :parameters {}
    :operationId :events}
   (sse/start-event-stream initialise-stream)))

(s/with-fn-validation ;; Optional, but nice to have at compile time
  (api/defroutes api-routes
    {:info {:title "SPA Skeleton"
            :description "A skeleton project for a ClojureScript Single Page Application backed by a Swagger API"
            :externalDocs {:description "Find out more"
                           :url "https://github.com/oliyh/spa-skeleton"}
            :version "2.0"}}
    [[["/api" ^:interceptors [api/error-responses
                              (api/negotiate-response)
                              (api/body-params)
                              api/common-body
                              (api/coerce-request)
                              (api/validate-response)]

       ["/status" {:get status-handler}]
       ["/events" {:get events-handler}]

       ["/swagger.json" {:get [api/swagger-json]}]
       ["/*resource" {:get [api/swagger-ui]}]]]]))

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
   ::bootstrap/type :jetty
   ::bootstrap/secure-headers {:content-security-policy-settings
                               {:script-src "'self' 'unsafe-inline' 'unsafe-eval'"}}})
