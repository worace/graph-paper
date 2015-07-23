(ns ^:figwheel-always graph-paper.core
    (:require [reagent.core :as reagent :refer [atom]]
              [graph-paper.event-channels :as events]
              [graph-paper.grid :as grid]
              [graph-paper.figures :as figs]
              [graph-paper.helpers :as h]))

(enable-console-print!)

;;Drawing lines
;;mouse down: set start point as current target
;;mouse up: set end point
;;generate line between start and end point
;;add it into the grid

(defonce app-state (atom {:text "welcome to graphpaper"
                          :drawing false
                          :target-coord []
                          :current-figure-start []
                          :pending-figure {}
                          :grid-chars (grid/generate 50 50)}))

;; Live Drawing
;; As mouse moves - need to generate "in progress"
;; shape based on coords so far
;; When rendering, need to include any in-progress
;; figures in the render
;; When mouse released; need to render final version of fig
;; remove in progress
;; and commit new one to the graph by merging into grid-chars

(events/register-handler! :mouse-down
 (fn [event]
   (swap! app-state assoc-in [:current-figure-start] (@app-state :target-coord))
   (swap! app-state assoc-in [:drawing] true)))

(events/register-handler! :mouse-up
 (fn [event]
   (swap! app-state
          assoc-in
          [:grid-chars]
          (merge (@app-state :grid-chars)
                 (figs/line (@app-state :current-figure-start)
                            (@app-state :target-coord))))
   (swap! app-state assoc-in [:pending-figure] {})
   (swap! app-state assoc-in [:drawing] false)))

(events/register-handler! :mouse-move
 (fn [event]
   (if (@app-state :drawing)
     (swap! app-state
            assoc-in
            [:pending-figure]
            (figs/line (@app-state :current-figure-start)
                       (@app-state :target-coord))))
   (swap! app-state assoc-in [:target-coord] (event :coord))))

(defn node-renderer [font-size]
  (fn [[[x y] text]]
    ^{:key (str "pos-x-" x "-y-" y)}
    [:text
     {:x (* font-size (inc x))
      :y (* font-size (inc y))
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
  [:p (str "Drawing: "
           (state :drawing)
           " Current mouse: "
           (state :target-coord)
           " Current fig start: "
           ".")])

(defn graph-paper []
  [:h1 (@app-state :text)
   [status-bar (select-keys @app-state [:drawing :target-coord :current-figure-start :pending-figure])]
   [:div
    [grid 400 400 (merge (@app-state :grid-chars) (@app-state :pending-figure))]]
   ])

(reagent/render-component [graph-paper]
                          (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
