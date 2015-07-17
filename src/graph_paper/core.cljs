(ns ^:figwheel-always graph-paper.core
    (:require [reagent.core :as reagent :refer [atom]]
              [graph-paper.helpers :as h]))

(enable-console-print!)

(defonce app-state (atom {:text "Hello world!"}))

(defn set-font [canvas size font]
  (set! (.-font canvas) (str size "px " font)))

(defn make-canvas []
  (.createElement js/document "canvas"))

(defonce canvas (.appendChild (first (h/by-tag "body")) (make-canvas)))

(defn context [canvas] (.getContext canvas "2d"))
(defn clear [canvas]
  (.clearRect (context canvas) 0 0 (.-width canvas) (.-height canvas)))

(defn render-text [canvas text]
  (let [ctx (context canvas)]
    (set-font ctx 20 "Courier New")
    (.fillText ctx text 10 30)))

(clear canvas)
(render-text canvas (:text @app-state))

(defn graph-paper []
  [:h1 (:text @app-state)
   [:div
    [:svg {:width 200 :height 200 :style {:border "1px solid black" :font-family "Courier New" :font-size "14px"}}
    [:text { :x 0 :y 14 } "pizza"]]]
   ])

(reagent/render-component [graph-paper]
                          (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)

