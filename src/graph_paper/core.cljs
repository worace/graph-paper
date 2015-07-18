(ns ^:figwheel-always graph-paper.core
    (:require [reagent.core :as reagent :refer [atom]]
              [graph-paper.event-channels :as events]
              [graph-paper.helpers :as h]))

(enable-console-print!)

(defn grid-chars [width height]
  (into (sorted-map)
        (for [x (range 1 (+ 1 width))
              y (range 1 (+ 1 height))]
          {[x y] "."})))

(defonce app-state (atom {:text "welcome to graphpaper"
                          :drawing false
                          :target-coord []
                          :grid-chars (grid-chars 50 50)}))

(events/register-handler! :mouse-down
 (fn [event]
   (swap! app-state assoc-in [:drawing] true)))

(events/register-handler! :mouse-up
 (fn [event]
   (swap! app-state assoc-in [:drawing] false)))

(events/register-handler! :mouse-move
 (fn [event]
   (swap! app-state assoc-in [:target-coord] (event :coord))))

(defn node-renderer [font-size]
  (fn [[[x y] text]]
    ^{:key (str "pos-x-" x "-y-" y)}
    [:text
     {:x (* font-size x)
      :y (* font-size y)
      :on-mouse-over (fn [] (events/log {:type :mouse-move :coord [x y]}) nil)
      }
     text]))

(defn grid [width height chars]
  [:svg {:width width :height height :class "noselect graph-paper-grid"
         :on-mouse-up (fn [] (events/log {:type :mouse-up}) nil)
         :on-mouse-down (fn [] (events/log {:type :mouse-down}) nil)
         }
   (doall (map (node-renderer 14) chars))])

(defn status-bar [state]
  [:p (str "Drawing: " (state :drawing) " Current mouse: " (state :target-coord)) ])

(defn graph-paper []
  [:h1 (@app-state :text)
   [status-bar (select-keys @app-state [:drawing :target-coord])]
   [:div
    [grid 400 400 (@app-state :grid-chars)]]
   ])

(reagent/render-component [graph-paper]
                          (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
