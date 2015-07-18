(ns ^:figwheel-always graph-paper.core
    (:require [reagent.core :as reagent :refer [atom]]
              [graph-paper.helpers :as h]))

(enable-console-print!)

(defn grid-chars [width height]
  (into (sorted-map)
        (for [x (range 1 (+ 1 width))
              y (range 1 (+ 1 height))]
          {[x y] "."})))

(defonce app-state (atom {:text "welcome to graphpaper"
                          :grid-chars (grid-chars 50 50)}))

(defn mouse-down [] (println "mouse-down"))

;; auto-ticker for generating unique ids
(def ticker
  (let [tick (atom 0)]
    #(swap! tick inc)))
(defn tick [] (first (repeatedly ticker)))


(defn node-renderer [font-size]
  (fn [[[x y] text]]
    ;; (println "got text: " text " and coords: " [x y])
    ^{:key (str "pos-x-" x "-y-" y)}
    [:text
     {:x (* font-size x)
      :y (* font-size y)
      :on-mouse-down (partial println "mousedown" " x: " x " y: " y)
      :on-mouse-up (partial println "mouseup" "x: " x " y: " y)
      }
     text]))

(defn grid [width height chars]
  [:svg {:width width
         :height height
         :user-select "none"
         :style {:font-family "Courier New" :font-size "14px"}}
   (doall (map (node-renderer 14) chars))])

(defn graph-paper []
  [:h1 (:text @app-state)
   [:div
    [grid 400 400 (:grid-chars @app-state)]]
   ])

(reagent/render-component [graph-paper]
                          (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
