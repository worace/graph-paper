(ns ^:figwheel-load graph-paper.test-helpers
  (:require [goog.dom :as dom]))

(defn found-in [re div]
  (let [res (.-innerHTML div)]
    (if (re-find re res)
      true
      (do (println "Not found: " res)
          false))))

(defn by-id
  "Return the element with the passed id."
  [id]
  (dom/getElement (name id)))

(defn length [nodes] (. nodes -length))
(defn item [nodes n] (.item nodes n))
(defn as-seq [nodes]
  (for [i (range (length nodes))] (item nodes i)))

(defn by-tag [tag]
  (as-seq (.getElementsByTagName js/document (name tag))))


;(def rflush reagent/flush)
