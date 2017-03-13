(ns spa-skeleton.start-devcards
  (:require [cljs.test :refer-macros [testing is]]
            [clojure.string :as str]
            [spa-skeleton.app :as app]
            [devcards.core :as dc :refer-macros [defcard deftest defcard-rg start-devcard-ui!]]))

(enable-console-print!)

(defcard-rg card-test
  [:div.mdl-grid
   [:div.mdl-cell.mdl-cell--6-col
    [app/card "Hello" "English"]]

   [:div.mdl-cell.mdl-cell--6-col
    [app/card "Hola" "Spanish"]]

   [:div.mdl-cell.mdl-cell--6-col
    [app/card "Ciao" "Italian"]]

   [:div.mdl-cell.mdl-cell--6-col
    [app/card "Bonjour" "French"]]])

(start-devcard-ui!)
