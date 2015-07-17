(ns ^:figwheel-always graph-paper.core
    (:require [reagent.core :as reagent :refer [atom]]
              [graph-paper.helpers :as h]))

(enable-console-print!)

(defonce app-state (atom {:text "Hello world!"}))

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

