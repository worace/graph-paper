(ns ^:figwheel-always graph-paper.core
    (:require [reagent.core :as reagent :refer [atom]]
              [graph-paper.helpers :as h]))

(enable-console-print!)

(defonce app-state (atom {:text "welcome to graphpaper"
                          :text-els ["pizza----"
                                     "........|"
                                     "........|"
                                     "........V"
                                     "......tacos"]}))

(defn mouse-down [] (println "mouse-down"))

;; auto-ticker for generating unique ids
(def ticker
  (let [tick (atom 0)]
    #(swap! tick inc)))
(defn tick [] (first (repeatedly ticker)))


(defn node-renderer [font-size]
  (let [c (atom 0)]
    (fn [text]
      (println "fetching new row offset " c)
      (swap! c inc)
      ^{:key (str text (tick))}
      [:text
       {:x 0
        :y (* font-size @c)
        }
       text])))

(defn grid [width height chars]
  [:svg {:width width
         :height height
         :on-mouse-down (partial println "mousedown")
         :on-mouse-up (partial println "mouseup")
         :style {:font-family "Courier New" :font-size "14px"}}
   (doall (map (node-renderer 14) chars))])

(defn graph-paper []
  [:h1 (:text @app-state)
   [:div
    [grid 400 400 (:text-els @app-state)]]
   ])

(reagent/render-component [graph-paper]
                          (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
