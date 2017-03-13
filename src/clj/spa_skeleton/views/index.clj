(ns spa-skeleton.views.index
  (:require [hiccup.page :refer [html5 include-css include-js]]))

(defn index
  ([] (index {:title "SPA Skeleton"
              :js-file "/cljs/main.js"
              :template? true}))
  ([{:keys [title js-file template?]}]
   (html5 {:lang "en"}
          [:head
           [:meta {:charset "utf-8"}]
           [:meta {:http-equiv "X-UA-Compatible"
                   :content "IE=edge"}]
           [:meta {:name "viewport"
                   :content "width=device-width, initial-scale=1"}]

           [:title title]
           [:link {:id "favicon"
                   :rel "shortcut icon"
                   :href "/images/favicon.png"
                   :sizes "16x16 32x32 48x48"
                   :type "image/png"}]

           (include-css "//storage.googleapis.com/code.getmdl.io/1.0.6/material.indigo-pink.min.css"
                        "//fonts.googleapis.com/icon?family=Material+Icons"
                        "/css/main.css")]
          [:body

           (when template?
             [:a.hidden-xs {:href "https://github.com/oliyh/spa-skeleton"}
              [:img {:style "position: absolute; top: 0; right: 0; border: 0;"
                     :src "https://camo.githubusercontent.com/652c5b9acfaddf3a9c326fa6bde407b87f7be0f4/68747470733a2f2f73332e616d617a6f6e6177732e636f6d2f6769746875622f726962626f6e732f666f726b6d655f72696768745f6f72616e67655f6666373630302e706e67"
                     :alt "Fork me on GitHub"
                     :data-canonical-src "https://s3.amazonaws.com/github/ribbons/forkme_right_orange_ff7600.png"}]])

           (when template?
             [:div#app
              [:div {:style "margin: 0 auto; border: 1px solid black;"}
               [:div
                [:h4 "SPA is loading..."]
                [:p "This is a modern website with modern requirements. If you cannot see anything, try upgrading your browser."]]]])

           (include-js js-file)])))

(def devcards-page
  (partial index {:title "Devcards page for SPA Skeleton"
                  :js-file "/cljs/main_devcards.js"
                  :template? false}))
