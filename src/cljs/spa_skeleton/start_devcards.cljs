(ns spa-skeleton.start-devcards
  (:require [cljs.test :refer-macros [testing is]]
            [clojure.string :as str]
            [devcards.core :as dc :refer-macros [defcard deftest start-devcard-ui!]]))

(enable-console-print!)

(defcard
  "### Getting started with Devcards.

Call your components within `defcard` with your initial value.

See the [devcards](http://rigsomelight.com/devcards/) site for more examples.")

(deftest check-logic
  "### You can also produce test reports."
  (testing "arithmetic"
    (is (even? (* 1 2)))
    (is (= 9 (* 3 3)))
    (testing "strings"
      (is (= "hello" (str/lower-case "HeLlO"))))))

(start-devcard-ui!)
