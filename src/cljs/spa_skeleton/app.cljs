(ns spa-skeleton.app
  (:require [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]
            [martian.core :as martian]
            [martian.protocols :refer [url-for]]
            [reagent.dom :as r]
            [reagent.core :as reagent])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(def app (js/document.getElementById "app"))

(defn- event-source [url]
  (let [latest-event (reagent/atom nil)]
    (.addEventListener (js/EventSource. url)
                       "message"
                       (fn [e] (reset! latest-event (.-data e)))
                       false)
    latest-event))

(defn- welcome []
  [:div.mdl-card.mdl-shadow--2dp {:style {:margin "0 auto"
                                          :top "3em"}}
   [:div.mdl-card__title.mdl-card--expand
    {:style {:color "white"
             :background "url('/images/skeleton-inverted.png') bottom right 0% no-repeat black"
             :backgroundSize "80px 80px"
             :height "80px"}}
    [:h2.mdl-card__title-text "SPA Skeleton"]]
   [:div.mdl-card__supporting-text "The ClojureScript application has loaded successfully"]
   [:div.mdl-card__actions.mdl-card--border
    [:a.mdl-button.mdl-button--colored.mdl-js-button.mdl-js-ripple-effect
     {:href "/api/index.html"}
     "Check out the Swagger API"]]])

(defn- sse-demo [martian]
  (let [sse-events (event-source (url-for martian :events))]
    (fn []
      [:div.mdl-card.mdl-shadow--2dp {:style {:margin "0 auto" :top "3em"}}
       [:div.mdl-card__title.mdl-card--expand
        {:style {:color "white"
                 :background "url('/images/skeleton-inverted.png') bottom right 0% no-repeat black"
                 :backgroundSize "80px 80px"
                 :height "80px"}}
        [:h2.mdl-card__title-text "Server Sent Events"]]
       [:div.mdl-card__supporting-text "Random number from server: " [:h4 @sse-events]]])))

(defn- home [martian]
  [:div.mdl-grid
   [:div.mdl-cell.mdl-cell--6-col
    [welcome]]
   [:div.mdl-cell.mdl-cell--6-col
    [sse-demo martian]]])

(defn- init []
  (go
    (let [swagger-json (:body (<! (http/get "/api/swagger.json")))
          martian (martian/bootstrap-swagger "" swagger-json)]
      (r/render [home martian] app))))

(.addEventListener js/document "DOMContentLoaded" init)
