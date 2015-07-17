(ns ^:figwheel-always graph-paper.core
    (:require [reagent.core :as reagent :refer [atom]]
              [graph-paper.helpers :as h]))

(enable-console-print!)

(defonce app-state (atom {:text "Hello world!"}))

(defn make-canvas []
  (.createElement js/document "canvas"))

(defonce canvas (.appendChild (first (h/by-tag "body")) (make-canvas)))

(defn context [canvas] (.getContext canvas "2d"))
(defn clear [canvas]
  (.clearRect (context canvas) 0 0 (.-width canvas) (.-height canvas)))

(defn render-text [canvas text]
  (println "hi rendering text ", text)
  (.fillText (context canvas) text 10 30)
  )

(clear canvas)
(render-text canvas (:text @app-state))

(defn graph-paper []
  [:h1 (:text @app-state)])

(reagent/render-component [graph-paper]
                          (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)

