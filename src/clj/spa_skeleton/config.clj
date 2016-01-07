(ns spa-skeleton.config
  (:require [nomad :refer [defconfig]]
            [clojure.java.io :as io]))

(defconfig config (io/resource "config.edn"))
