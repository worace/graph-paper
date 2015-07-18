(ns graph-paper.helpers
  (:require [goog.dom :as dom]
            [dommy.core :refer-macros [sel sel1]]))

(defn abs [n]
  (.abs js/Math n))

(defn found-in [re div]
  (let [res (.-innerHTML div)]
    (if (re-find re res)
      true
      (do (println "Not found: " res)
          false))))
