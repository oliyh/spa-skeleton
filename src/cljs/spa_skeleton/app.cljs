(ns spa-skeleton.app
  (:require [reagent.dom :as r]))

(def app (js/document.getElementById "app"))

(defn- welcome []
  [:div.mdl-card.mdl-shadow--2dp {:style {:margin "0 auto"
                                          :top "3em"}}
   [:div.mdl-card__title.mdl-card--expand
    {:style {:color "white"
             :background "url('/images/skeleton-inverted.png') bottom right 0% no-repeat black"
             :backgroundSize "80px 80px"}}
    [:h2.mdl-card__title-text "SPA Skeleton"]]
   [:div.mdl-card__supporting-text "The ClojureScript application has loaded successfully"]
   [:div.mdl-card__actions.mdl-card--border
    [:a.mdl-button.mdl-button--colored.mdl-js-button.mdl-js-ripple-effect
     {:href "/api/index.html"}
     "Check out the Swagger API"]]])

(r/render [welcome] app)
